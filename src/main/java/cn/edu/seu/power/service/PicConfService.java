package cn.edu.seu.power.service;

import cn.edu.seu.power.dao.PicConfDao;
import cn.edu.seu.power.domain.PicConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by dingwenjiang on 2016/7/22.
 */
@Service
public class PicConfService {
    @Autowired
    PicConfDao picConfDao;

    @Value("#{appProp.projectName}")
    private String projectName;

    public boolean addPicConfItem(String level3name, String url, String conf){
        int ret = picConfDao.insertPicConfItem(level3name,url,conf);
        if(ret == 1){
            return true;
        }
        return false;
    }

    public PicConf getPicConfItem(String id){
        PicConf picConf = picConfDao.getPicConfItemById(id);
        return picConf;
    }

    public PicConf getPicConfItemByName(String name){
        PicConf picConf = picConfDao.getPicConfItemByName(name);
        return picConf;
    }
}
