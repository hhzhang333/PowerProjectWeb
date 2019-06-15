package cn.edu.seu.power.controller;

import cn.edu.seu.power.domain.GraphTime;
import cn.edu.seu.power.domain.Reporter;
import cn.edu.seu.power.service.DevmessService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by zhh on 2016/7/17.
 */
@Controller
public class DevmessController {
    private static Logger logger = LoggerFactory.getLogger(DevmessController.class);
    private static String monthLenth = null;
    private static String dayLenth =  null;
    private static String yearLength = null;

    @Autowired
    private DevmessService devmessService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "showGraph", method = RequestMethod.GET)
    public String showGraph(ModelMap modelMap,
                            HttpSession httpSession) {
        if(httpSession.getAttribute("authority") == null){
            return "index.ftl";
        }else{
            modelMap.addAttribute("url", "pages/powerManagement");
            return "container.ftl";
        }
    }

    @RequestMapping(value = "pages/showGraph/{graph}/{graphType}", method = RequestMethod.POST)
    public String showGraph(@PathVariable String graph,
                            @PathVariable String graphType,
                            @RequestBody GraphTime time,
                            ModelMap modelMap,
                            HttpSession httpSession) {
        try {
            if (httpSession.getAttribute("username") == null)
                return "index.ftl";
            init(time.getStart().getMonth(), time.getStart().getDay(), time.getStart().getHour());
            modelMap.addAttribute("title", time.getStart().getTitle());
            modelMap.addAttribute("subTitle", time.getStart().getSubTitle());
            modelMap.addAttribute("location", time.getStart().getLocation());
            if (graph.equals("pie")) {
                if (time.getStart().getShowType() != null && time.getStart().getShowType().equals("year")) {
                    modelMap.addAttribute("data", objectMapper.writeValueAsString(devmessService.getYearPowerUsedPie(time, graphType)));
                }
                if (time.getStart().getShowType() != null && time.getStart().getShowType().equals("month")) {
                    modelMap.addAttribute("data", objectMapper.writeValueAsString(devmessService.getMonthPowerUseddPie(time, graphType)));
                }
                if (time.getStart().getShowType() != null && time.getStart().getShowType().equals("day")) {
                    modelMap.addAttribute("data", objectMapper.writeValueAsString(devmessService.getDayPowerUsedPie(time, graphType)));
                }
                return "graph/pie.ftl";
            }

            if (graph.equals("line")) {
                if (time.getStart().getShowType() != null && time.getStart().getShowType().equals("year")) {
                    modelMap.addAttribute("xary", yearLength);
                    modelMap.addAttribute("data", objectMapper.writeValueAsString(devmessService.getYearPowerUsedLine(time, graphType)));
                }
                if (time.getStart().getShowType() != null && time.getStart().getShowType().equals("month")) {
                    modelMap.addAttribute("xary", monthLenth);
                    modelMap.addAttribute("data", objectMapper.writeValueAsString(devmessService.getMonthPowerUsedLine(time, graphType)));
                }
                if (time.getStart().getShowType() != null && time.getStart().getShowType().equals("day")) {
                    modelMap.addAttribute("xary", dayLenth);
                    modelMap.addAttribute("data", objectMapper.writeValueAsString(devmessService.getDayPowerUsedLine(time, graphType)));
                }
                return "graph/line.ftl";
            }
            return "index.ftl";
        } catch (Exception ex) {
            logger.error("Ex in showGraph, ex is ", ex);
            return "index.ftl";
        }
    }

    @RequestMapping(value = "admin/download", method = RequestMethod.POST)
    @ResponseBody
    public String downloadReporter(HttpSession session,
                                 HttpServletResponse response,
                                 @RequestBody GraphTime time) {
        try {
            if (session.getAttribute("authority") == null)
                return null;
            if (session.getAttribute("authority").equals("tech") || session.getAttribute("authority").equals("normal")) {
                response.setContentType("application/x-download");
                return devmessService.downLoadReporter(time);
            } else
                return null;
        } catch (Exception ex) {
            logger.error("error in downloadReporter, ex is ", ex);
            return null;
        }
    }

    @RequestMapping(value = "/devmess/getDeviceInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceInfo(@RequestParam(value = "deviceId")String deviceId){
        JSONObject ret = devmessService.getLatestDevmessByDeviceId(deviceId);
        return ret.toJSONString();
    }

    @RequestMapping(value = "devmess/addDevmessItem", method = RequestMethod.GET)
    @ResponseBody
    public String addDevmessItem(@RequestParam(value = "name")String name,
                                 @RequestParam(value = "value")String value,
                                 @RequestParam(value = "time")String time,
                                 @RequestParam(value = "quality")String quality){
        HashMap<String,String> devmessItem = new HashMap<String, String>();
        devmessItem.put("name",name);
        devmessItem.put("value",value);
        devmessItem.put("time",time);
        devmessItem.put("quality",quality);
        boolean ret = devmessService.addDevmessItem(devmessItem);
        if (ret)
            return "success";
        return "failed";
    }

    @RequestMapping("admin/csvUpload")
    @ResponseBody
    public String  csvUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        String path = null;
        boolean ret = false;
        long suffix = System.currentTimeMillis();
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter= multiRequest.getFileNames();

            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    path = System.getProperty("webappPath") + "csv\\" + suffix;
                    //上传
                    file.transferTo(new File(path));

                }

            }

        }
        System.out.println(path);
        ret = devmessService.addDevmessItems(path);
        if (ret)
            return "success";
        return "failed";

        //return "../csv/" + suffix;
    }

    @RequestMapping(value = "pages/powerManagement", method = RequestMethod.GET)
    public String showPowerManagementPage() {
        return "/pages/powerManagement.ftl";
    }

    @RequestMapping(value = "pages/cold", method = RequestMethod.GET)
    public String showCold(){
        return "pages/colddemo.ftl";
    }

    @RequestMapping(value = "admin/powerCompare", method = RequestMethod.GET)
    public String showPowerCompare(HttpSession session,
                                   ModelMap modelMap,
                                   HttpServletResponse response) {
        try {
            //if (session.getAttribute("authority") == null)
            //    response.sendRedirect("index");
            List<Reporter> reporterList = devmessService.getOverviewMessage();
            modelMap.addAttribute("reporters", reporterList);
            return "powerCompare.ftl";
        } catch (Exception ex) {
            logger.error("exception in showPowerCompare, ex is ", ex);
            return "index.ftl";
        }
    }

    private void init(int month, int day, int hour) throws IOException {
        ArrayList<String> monthLenth1 = new ArrayList<String>();
        ArrayList<String> dayLenth1 =  new ArrayList<String>();
        ArrayList<String> yearLength1 = new ArrayList<String>();
        for (int i = 1; i <= hour; i++) {
            dayLenth1.add(String.valueOf(i));
        }
        for (int i = 1; i < month; i++) {
            yearLength1.add(String.valueOf(i));
        }
        for (int i = 1; i < day; i++) {
            monthLenth1.add(String.valueOf(i));
        }
        monthLenth = objectMapper.writeValueAsString(monthLenth1);
        dayLenth = objectMapper.writeValueAsString(dayLenth1);
        yearLength = objectMapper.writeValueAsString(yearLength1);
    }
}
