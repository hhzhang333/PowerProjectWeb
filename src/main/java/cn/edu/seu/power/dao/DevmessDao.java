package cn.edu.seu.power.dao;

import cn.edu.seu.power.domain.Devmess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhh on 2016/7/13.
 */
public interface DevmessDao {
    public List<Devmess> selectDevmessByName(@Param(value = "name") String name,
                                             @Param(value = "startTime") String startTime,
                                             @Param(value = "endTime") String endTime);


    public List<Devmess> selectDevmessByPages(@Param(value = "pretotal") String pretotal,
                                              @Param(value = "len") String len);

    public int modifyDevmessById(@Param(value = "id") String id,
                                 @Param(value = "name")String name,
                                 @Param(value = "value")String value,
                                 @Param(value = "time")String time,
                                 @Param(value = "quality")String quality);

    public Integer getDevmessTotalCount();

    public Integer getDevmessFilteredCount(@Param(value = "sumSqlWhere") String sumSqlWhere,
                                           @Param(value = "search")String search);


    public Integer getDevmessTimeFilteredCount(@Param(value = "startTime")String startTime,
                                               @Param(value = "endTime")String endTime);

    public List<Devmess> getDevmessByConditions(@Param(value = "start")String start,
                                                @Param(value = "end")String end,
                                                @Param(value = "search")String search,
                                                @Param(value = "orderSql")String orderSql,
                                                @Param(value = "sumSqlWhere")String sumSqlWhere);


    public List<Devmess> getDevmessByTimeConditions(@Param(value = "start")String start,
                                                    @Param(value = "end")String end,
                                                    @Param(value = "startTime")String startTime,
                                                    @Param(value = "endTime")String endTime,
                                                    @Param(value = "orderSql")String orderSql);
    public Devmess selectDevmessMax(@Param(value = "name") String name,
                                    @Param(value = "startTime") String startTime,
                                    @Param(value = "endTime") String endTime);

    public Devmess selectDevmessMin(@Param(value = "name") String name,
                                    @Param(value = "startTime") String startTime,
                                    @Param(value = "endTime") String endTime);

    public List<Devmess> getLatestDevmessByDeviceId(@Param(value = "deviceId")String deviceId);

    public Devmess getLatestDevmessByName(@Param(value = "name")String name);

    public int addDevmessItem(@Param(value = "name")String name,
                              @Param(value = "value")String value,
                              @Param(value = "time")String time,
                              @Param(value = "quality")String quality);
}
