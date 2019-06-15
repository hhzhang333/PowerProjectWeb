package cn.edu.seu.power.dao;

import cn.edu.seu.power.domain.PicConf;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dingwenjiang on 2016/7/22.
 */

public interface PicConfDao {
    public int insertPicConfItem(@Param(value = "level3name")String level3name,
                                 @Param(value = "url")String url,
                                 @Param(value = "conf")String conf);

    public PicConf getPicConfItemById(@Param(value = "id")String id);

    public PicConf getPicConfItemByName(@Param(value = "name")String name);
}
