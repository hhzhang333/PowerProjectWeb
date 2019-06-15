package cn.edu.seu.power.dao;

import cn.edu.seu.power.domain.Reporter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zhh on 2016/7/19.
 */
public interface ReporterDao {
    public void insertReporter(@Param(value = "time") Date time,
                               @Param(value = "blockId") String blockId,
                               @Param(value = "blockName") String blockName,
                               @Param(value = "power") String power,
                               @Param(value = "totalPower") String totalPower,
                               @Param(value = "lightSocket") String lightSocket,
                               @Param(value = "aircondition") String aircondition,
                               @Param(value = "dfPower") String dfPower,
                               @Param(value = "special") String special,
                               @Param(value = "lightAndSocket") String lightAndSocket,
                               @Param(value = "corridorAndEmergency") String corridorAndEmergency,
                               @Param(value = "outdoorLighting") String outdoorLighting,
                               @Param(value = "acTail") String acTail,
                               @Param(value = "hvapf") String hvapf,
                               @Param(value = "muau") String muau,
                               @Param(value = "eahu") String eahu,
                               @Param(value = "fancoil") String fancoil,
                               @Param(value = "splitACU") String splitACU,
                               @Param(value = "elevator") String elevator,
                               @Param(value = "waterpump") String waterpump,
                               @Param(value = "fanner") String fanner,
                               @Param(value = "datacenter") String datacenter,
                               @Param(value = "laundry") String laundry,
                               @Param(value = "kitchen") String kitchen,
                               @Param(value = "swimpool") String swimpool,
                               @Param(value = "gym") String gym,
                               @Param(value = "water") String water,
                               @Param(value = "toiletWater") String toiletWater,
                               @Param(value = "piplinegas") String piplinegas,
                               @Param(value = "centralheating") String centralheating,
                               @Param(value = "centralcold") String centralcold,
                               @Param(value = "other") String other,
                               @Param(value = "chstation") String chstation,
                               @Param(value = "type") int type,
                               @Param(value = "area") int area);

    public void insertReporterByObj(Reporter reporter);

    public List<Reporter> selectReporters(@Param(value = "start") String start,
                                          @Param(value = "end") String end,
                                          @Param(value = "type") int type);

    public void deleteAll();
}
