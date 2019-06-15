package cn.edu.seu.power.controller;

import cn.edu.seu.power.domain.PicConf;
import cn.edu.seu.power.service.PicConfService;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dingwenjiang on 2016/7/22.
 */
@Controller
public class PicController {
    @Autowired
    PicConfService picConfService;


    @RequestMapping(value = "admin/addPicConfItem", method = RequestMethod.GET)
    @ResponseBody
    public String addPicConfItem(@RequestParam(value = "level3name") String level3name,
                                 @RequestParam(value = "url")String url,
                                 @RequestParam(value = "conf")String conf)
    {
        boolean ret = false;
        ret = picConfService.addPicConfItem(level3name,url,conf);
        if(ret){
            return "success";
        }
        return "fail";
    }

    @RequestMapping(value = "admin/getPicConfItem", method = RequestMethod.GET)
    @ResponseBody
    public String getPicConfItem(@RequestParam(value = "picConfId") String picConfId){
        PicConf picConf = picConfService.getPicConfItem(picConfId);
        JSONObject ret = new JSONObject();
        if(picConf!=null){
            ret.put("id",picConf.getId());
            ret.put("level3name",picConf.getLevel3name());
            ret.put("url",picConf.getUrl());
            ret.put("conf", picConf.getConf());
            return ret.toJSONString();
        }else {
            return "failed";
        }
    }


    @RequestMapping(value = "admin/getPicConfItemByName", method = RequestMethod.GET)
    @ResponseBody
    public String getPicConfItemByName(@RequestParam(value = "picConfName") String picConfName){
        PicConf picConf = picConfService.getPicConfItemByName(picConfName);
        JSONObject ret = new JSONObject();
        if(picConf!=null){
            ret.put("id",picConf.getId());
            ret.put("level3name",URLEncoder.encode(picConf.getLevel3name()));
            ret.put("url",picConf.getUrl());
            ret.put("conf", picConf.getConf());
            return ret.toJSONString();
        }else {
            return "failed";
        }
    }
}
