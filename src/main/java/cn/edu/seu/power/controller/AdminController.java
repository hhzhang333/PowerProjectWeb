package cn.edu.seu.power.controller;

import cn.edu.seu.power.domain.Account;
import cn.edu.seu.power.domain.Devmess;
import cn.edu.seu.power.domain.PowerCalculate;
import cn.edu.seu.power.service.AccountService;
import cn.edu.seu.power.service.DevmessService;
import cn.edu.seu.power.service.PowerCalculateService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dingwenjiang on 2016/7/18.
 */
@Controller
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private DevmessService devmessService;

    @Autowired
    private PowerCalculateService powerCalculateService;

    @Value("#{appProp.projectName}")
    private String projectName;

    /*api*/
    @RequestMapping(value = "admin/getAllUsers",method = RequestMethod.GET)
    @ResponseBody
    public String getAllUsers(){
        List<Account> accounts = accountService.getAllUser();
        JSONArray jsonArray = new JSONArray();
        /*
        for (int i = 0; i < accounts.size(); i++) {
            //jsonArray.add(accounts.get(i));
            JSONObject temp = new JSONObject();
            temp.put("id",accounts.get(i).getId());
            temp.put("username",accounts.get(i).getUsername());
            temp.put("password",accounts.get(i).getPassword());
            temp.put("authority",accounts.get(i).getAuthority());
            jsonArray.add(temp);
        }*/
        for (Account a:accounts) {
            JSONObject temp = new JSONObject();
            temp.put("id",a.getId());
            temp.put("username",a.getUsername());
            temp.put("password",a.getPassword());
            temp.put("authority",a.getAuthority());
            jsonArray.add(temp);
        }
        return jsonArray.toJSONString();
    }

    @RequestMapping(value = "admin/deleteUser",method = RequestMethod.GET)
    @ResponseBody
    public String deleteUserById(@RequestParam String userId){
        if(accountService.deleteUserById(userId)){
            return "success";
        }
        return "failed";
    }




    @RequestMapping(value = "admin/modifyUserById",method = RequestMethod.POST)
    @ResponseBody
    public String modifyUserById(@RequestParam("") String userinfo){
//        List<Account> accounts = accountService.getAllUser();
//        System.out.println(URLDecoder.decode(userinfo));
        JSONParser parser = new JSONParser();
        boolean ret = false;
        try {
            Object obj = parser.parse(userinfo);
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.get("id"));
            System.out.println(jsonObject.get("username"));
            System.out.println(jsonObject.get("password"));
            System.out.println(jsonObject.get("authority"));
            HashMap<String,String> uinfo = new HashMap<String, String>();
            uinfo.put("id", String.valueOf(jsonObject.get("id")) );
            uinfo.put("username", String.valueOf(jsonObject.get("username")));
            uinfo.put("password", String.valueOf(jsonObject.get("password")));
            uinfo.put("authority", String.valueOf(jsonObject.get("authority")));
            ret = accountService.modifyUserById(uinfo);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(ret){
            return "success";
        }else{
            return "fail";
        }
    }

    @RequestMapping(value = "admin/getDevmessByPages",method = RequestMethod.GET)
    @ResponseBody
    public String getDevmessByPages(@RequestParam("") String pageNum,@RequestParam("") String pageSize){

        JSONParser parser = new JSONParser();
        System.out.println(Integer.parseInt(pageNum));
        System.out.println(Integer.parseInt(pageSize));
        List<Devmess> devmess = devmessService.selectDevmessByPages(Integer.parseInt(pageNum),Integer.parseInt(pageSize));


        JSONArray jsonArray = new JSONArray();
        for (Devmess d:devmess) {
            JSONArray temp = new JSONArray();
            temp.add(d.getId());
            temp.add(d.getName());
            temp.add(d.getValue());
            temp.add(d.getTime().getTime());
            temp.add(d.getQuality());
            jsonArray.add(temp);
        }

        JSONObject ret = new JSONObject();
        ret.put("data",jsonArray);
        return ret.toJSONString();
    }



    @RequestMapping(value = "admin/dtServerProccess", method = RequestMethod.GET)
    @ResponseBody
    public String dtServerProcess(HttpServletRequest request){
        String draw = request.getParameter("draw");
        //分页
        String start = request.getParameter("start");//从多少开始
        String length = request.getParameter("length");//数据长度

        //排序
        String order_column = request.getParameter("order[0][column]");//根据哪一列排序，从0开始
        String order_dir = request.getParameter("order[0][dir]");//asc desc 升序或者降序

        //搜索
        String search = request.getParameter("search[value]");//获取前台传过来的过滤条件
        String customvalue = request.getParameter("search[customvalue]");//获取前台传过来的过滤条件
        JSONParser parser = new JSONParser();
        JSONObject customObj = null;
        try {
            customObj = (JSONObject) parser.parse(customvalue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String paramType = (String) customObj.get("type");


        List<Devmess> data = null;
        int recordsTotal = devmessService.getDevmessTotalCount();
        int recordsFiltered = 0;


        if(paramType.equals("1")){
            String param = (String) customObj.get("param");
            System.out.println("param\t"+param);
            if(param == null)
                param = "";
            data = devmessService.getDevmessByConditions(order_column, order_dir, param, start, length);
            //recordsTotal = devmessService.getDevmessTotalCount();
            recordsFiltered = devmessService.getDevmessFilteredCount(param);


        }else if(paramType.equals("2")){
            String firstParam = (String) customObj.get("firstParam");
            String secondParam = (String) customObj.get("secondParam");
            System.out.println("type\t"+paramType);
            System.out.println("firstParam\t"+firstParam);
            System.out.println("secondParam\t"+secondParam);

            data = devmessService.getDevmessByTimeConditions(order_column, order_dir, firstParam, secondParam, start, length);
            recordsFiltered = devmessService.getDevmessTimeFilteredCount(firstParam,secondParam);
        }


        JSONObject ret = new JSONObject();
        ret.put("draw",draw);
        ret.put("recordsTotal",recordsTotal);
        ret.put("recordsFiltered",recordsFiltered);
        JSONArray jsonArray = new JSONArray();
        for (Devmess d : data) {
            JSONArray temp = new JSONArray();
            temp.add(d.getId());
            temp.add(d.getName());
            temp.add(d.getValue());
            temp.add(d.getTime().getTime());
            temp.add(d.getQuality());
            jsonArray.add(temp);
        }
        ret.put("data",jsonArray);
        return ret.toJSONString();
        /*
        System.out.println("order_column\t"+ order_column);
        System.out.println("order_dir\t"+order_dir);
        System.out.println("search\t"+search+search.isEmpty()+search.equals("''"));
        System.out.println("start\t"+start);
        System.out.println("length\t"+length);
        */
        /*
        System.out.println("===================================>");
        System.out.println(recordsTotal);
        System.out.println(recordsFiltered);
        System.out.println(search == null);
        System.out.println("<===================================");
        */
        /*
        if(search == null)
            search = "";


        System.out.println("===================================>");
        System.out.println("customvalue\t"+customvalue);
        System.out.println("draw\t"+draw);
        System.out.println("order_column\t"+order_column);
        System.out.println("order_dir\t"+order_dir);
        System.out.println("search\t"+search);
        System.out.println("start\t"+start);
        System.out.println("length\t"+length);
        System.out.println("<===================================");


        List<Devmess> data = devmessService.getDevmessByConditions(order_column,order_dir,search,start,length);
        int recordsTotal = devmessService.getDevmessTotalCount();
        int recordsFiltered = devmessService.getDevmessFilteredCount(search);


        JSONObject ret = new JSONObject();
        ret.put("draw",draw);
        ret.put("recordsTotal",recordsTotal);
        ret.put("recordsFiltered",recordsFiltered);
        JSONArray jsonArray = new JSONArray();
        for (Devmess d : data) {
            JSONArray temp = new JSONArray();
            temp.add(d.getId());
            temp.add(d.getName());
            temp.add(d.getValue());
            temp.add(d.getTime().getTime());
            temp.add(d.getQuality());
            jsonArray.add(temp);
        }
        ret.put("data",jsonArray);
        //返回值
        //条件过滤后记录数 必要
        //String recordsFiltered = "0";
        //表的总记录数 必要
        //String recordsTotal = "0";
        return ret.toJSONString();
        */
    }

    @RequestMapping(value = "admin/modifyDevmessItemById",method = RequestMethod.GET)
    @ResponseBody
    public String modifyDevmessItemById(@RequestParam("") String id,
                                        @RequestParam("") String name,
                                        @RequestParam("") String value,
                                        @RequestParam("") String time,
                                        @RequestParam("") String quality){

        JSONParser parser = new JSONParser();
        System.out.println(id);
        System.out.println(name);
        System.out.println(value);
        System.out.println(time);
        System.out.println(quality);
        HashMap<String,String> dataItem = new HashMap<String, String>();
        dataItem.put("id",id);
        dataItem.put("name",name);
        dataItem.put("value",value);
        dataItem.put("time",time);
        dataItem.put("quality",quality);
        if(devmessService.modifyDevmessById(dataItem)){
            return "success";
        }
        return "fail";
    }




    /*ftl*/
    @RequestMapping(value = "admin/powerMonitor",method = RequestMethod.GET)
    public String adminPowerMonitor(ModelMap modelMap,
                                   HttpSession httpSession){
        //if(httpSession.getAttribute("authority") == null){
        //    return "index.ftl";
        //}else {
            //modelMap.addAttribute("authority",httpSession.getAttribute("authority"));
            modelMap.addAttribute("authority","admin");
            modelMap.addAttribute("projectName",projectName);
            return "adminPowerMonitor.ftl";
        //}
    }


    @RequestMapping(value = "admin/manageUsers",method = RequestMethod.GET)
    public String adminManageUsers(ModelMap modelMap,
                                   HttpSession httpSession){
        //if(httpSession.getAttribute("authority") == null){
        //    return "index.ftl";
        //}else {
            //modelMap.addAttribute("authority",httpSession.getAttribute("authority"));
            modelMap.addAttribute("authority","admin");
            modelMap.addAttribute("projectName",projectName);
            return "adminManageUsers.ftl";
        //}
    }

    @RequestMapping(value = "admin/manageData",method = RequestMethod.GET)
    public String adminManageData(ModelMap modelMap,
                                  HttpSession httpSession){
        //if(httpSession.getAttribute("authority") == null){
        //    return "index.ftl";
        //}else {
            //modelMap.addAttribute("authority",httpSession.getAttribute("authority"));
            modelMap.addAttribute("authority","admin");
            return "adminManageData.ftl";
        //}
    }

    @RequestMapping(value = "admin/downloadReporter", method = RequestMethod.GET)
    public String downloadReporter(ModelMap modelMap,HttpSession httpSession) {
        //if (httpSession.getAttribute("authority") == null)
        //    return "index.ftl";
        //else
            modelMap.addAttribute("authority","admin");
            return "adminDownloadReporter.ftl";
    }

    @RequestMapping(value = "admin/picConfig",method = RequestMethod.GET)
    public String adminPicConfig(HttpServletRequest request,
                                 ModelMap modelMap,
                                 HttpSession httpSession){
        //System.out.println(System.getProperty("webappPath"));
        //System.out.println(request.getContextPath());

        //if(httpSession.getAttribute("authority") == null){
        //    return "index.ftl";
        //}else {
            //modelMap.addAttribute("authority",httpSession.getAttribute("authority"));
            modelMap.addAttribute("authority","admin");
            return "adminPicConfig.ftl";
        //}
    }

    @RequestMapping(value = "admin/formulaConfig",method = RequestMethod.GET)
    public String adminFormulaConfig(ModelMap modelMap,
                                     HttpSession httpSession){

        //if(httpSession.getAttribute("authority") == null){
        //    return "index.ftl";
        //}else {
            List<PowerCalculate> powerCalculateList = powerCalculateService.getAllFormula();
            modelMap.addAttribute("formulas", powerCalculateList);
            //modelMap.addAttribute("authority",httpSession.getAttribute("authority"));
            modelMap.addAttribute("authority","admin");
            return "adminFormulaConfig.ftl";
        //}

        //return "adminFormulaConfig.ftl";
    }

    @RequestMapping(value = "admin/updateCalculate", method = RequestMethod.POST)
    @ResponseBody
    public String savePowerCalculate(@RequestParam int id,
                                     @RequestParam String calculate,
                                     @RequestParam String code,
                                     @RequestParam String name) {
        try {
            powerCalculateService.saveFormula(id, name, calculate, code);
            return "success";
        } catch (Exception ex) {
            logger.error("exception in savePowerCalculate");
            return "error";
        }
    }

    /*file upload*/
    @RequestMapping("admin/picUpload")
    @ResponseBody
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
        //long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        String path;
        long suffix = 0;
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
                    //pic
                    //String path="E:/springUpload"+file.getOriginalFilename();
                    suffix = System.currentTimeMillis();
                    path = System.getProperty("webappPath") + "pic\\" + suffix;
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        //long  endTime=System.currentTimeMillis();
        //System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "../pic/" + suffix;
    }
//    @RequestMapping(value = "/pages/powerManagement", method = RequestMethod.GET)
//    public String showPowerManagementPage() {
//        return "/pages/powerManagement.ftl";
//    }

    @RequestMapping(value = "admin/index",method = RequestMethod.GET)
    public String renderAdmin(ModelMap modelMap, HttpSession httpSession, @RequestParam String type){
        if(httpSession.getAttribute("authority") == null){
            return "index.ftl";
        }else{
            if(type.equals("tech")||type.equals("admin")){
                modelMap.addAttribute("authority",type);
                return "adminContainer.ftl";
            }else{
                return "index.ftl";
            }
        }
    }
}
