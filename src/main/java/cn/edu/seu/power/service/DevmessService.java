package cn.edu.seu.power.service;

import cn.edu.seu.power.dao.AccountDao;
import cn.edu.seu.power.dao.DevmessDao;
import cn.edu.seu.power.domain.Devmess;
import cn.edu.seu.power.dao.PowerCalculateDao;
import cn.edu.seu.power.dao.ReporterDao;
import cn.edu.seu.power.domain.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhh on 2016/7/16.
 */
@Service
public class DevmessService {

    private static Logger logger = LoggerFactory.getLogger(DevmessService.class);

    private Map<String, String> powerLabel;

    @Value("#{appProp.projectName}")
    private String projectName;

    @Value("#{appProp.projectArea}")
    private int area;

    @Autowired
    private PowerCalculateDao powerCalculateDao;

    @Autowired
    private DevmessDao devmessDao;

    @Autowired
    private ReporterDao reporterDao;

    @Autowired
    private AccountDao accountDao;

    private final String project = "%s.%s.%s";

    SimpleDateFormat sdfcsv =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfsql =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000");

    //2016-07-15 20:31:02.000
    private static String queryTimeFormat = "%s-%s-%s %s:%s:%s.000";

    private static String reporterFormat = "时间,建筑ID,建筑名称,电,总用电,照明插座用电,空调用电,动力用电,特殊用电,照明与插座,走廊与应急,室外景观照明,冷热站,空调末端,全空气机组,新风机组,排风机组,风机盘管,分体式空调器,电梯,水泵,通风机,信息中心,洗衣房,厨房餐厅,游泳池,健身房,水,卫生间用水,管道燃气,集中供热量,集中供冷量,其它能源";

    public List<Reporter> getOverviewMessage() {
        GraphTime time = generateTime();
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), "01", "01", "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
        List<Reporter> reporterList = reporterDao.selectReporters(startTime, endTime, 2);

        HashMap<String, Reporter> reporterHashMap = new HashMap<String, Reporter>();
        for (Reporter tmp: reporterList) {
            if (reporterHashMap.containsKey(tmp.getBlockName())) {
                Reporter hashReporter = reporterHashMap.get(tmp.getBlockName());
                Reporter addedReporter = addReporter(tmp, hashReporter);
                reporterHashMap.put(tmp.getBlockName(), addedReporter);
            } else {
                reporterHashMap.put(tmp.getBlockName(), tmp);
            }
        }

        List<Reporter> reporters = new ArrayList<Reporter>();
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        for (Reporter tmp: reporterHashMap.values()) {
            double energy = Double.parseDouble(tmp.getPiplinegas()) * 12143 / 10000 +
                    Double.parseDouble(tmp.getTotalPower()) * 1229 / 10000 / 1000 +
                    Double.parseDouble(tmp.getCentralheating()) * 1229 / 1000000;
            tmp.setPowerUsed(Double.parseDouble(decimalFormat.format(energy)));
            reporters.add(tmp);
        }
        return reporters;
    }

    public String downLoadReporter(GraphTime time) throws IOException {
        String startTime = null;
        String endTime = null;
        int queryType = 0;
        if (time.getStart().getShowType().equals("year")) {
            startTime = String.format(queryTimeFormat, time.getStart().getYear(), "01", "01", "00", "00", "01");
            endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
            queryType = 2;
        }
        if (time.getStart().getShowType().equals("month")) {
            startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), "01", "00", "00", "01");
            endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
            queryType = 1;
        }
        if (time.getStart().getShowType().equals("day")) {
            startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), time.getStart().getDay(), "00", "00", "01");
            endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), time.getEnd().getHour(), time.getEnd().getMinute(), time.getEnd().getSecond());
            queryType = 0;
        }
        if (time.getStart().getShowType().equals("hour")) {
            startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), time.getStart().getDay(), time.getStart().getHour(), time.getStart().getMinute(), time.getStart().getSecond());
            endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), time.getEnd().getHour(), time.getEnd().getMinute(), time.getEnd().getSecond());
            queryType = 0;
        }
        return downLoadReporterStream(startTime, endTime, queryType);
    }

    public List<Double> getDayPowerUsedLine(GraphTime time, String graphType) {
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), time.getStart().getDay(), "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), time.getEnd().getHour(), time.getEnd().getMinute(), time.getEnd().getSecond());
        List<Double> tmp = getLineGraphByReporter(startTime, endTime, graphType, 0);
        if (!tmp.isEmpty()) {
            return tmp;
        }
        if (graphType.equals("ac"))
            return getAirConditionPower(time);
        if (graphType.equals("total"))
            return getAllPower(time);
        if (graphType.equals("light"))
            return getLightingPower(time);
        if (graphType.equals("elevator"))
            return getElevatorPower(time);
        return null;
    }

    public List<Map<String, Object>> getDayPowerUsedPie(GraphTime time, String graphType) {
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), time.getStart().getDay(), "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), time.getEnd().getHour(), time.getEnd().getMinute(), time.getEnd().getSecond());
        List<Map<String, Object>> result = getPieGraphByReporter(startTime, endTime, graphType, 0);
        if (!result.isEmpty())
            return result;
        if (graphType.equals("total"))
            return getAllDividePower(time);
        if (graphType.equals("ac"))
            return getAirConditionDividePower(time);
        return null;
    }

    public List<Map<String, Object>> getMonthPowerUseddPie(GraphTime time, String graphType) {
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), "01", "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
        return getPieGraphByReporter(startTime, endTime, graphType, 1);
    }

    public List<Map<String, Object>> getYearPowerUsedPie(GraphTime time, String graphType) {
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), "01", "01", "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
        return getPieGraphByReporter(startTime, endTime, graphType, 2);
    }

    public List<Double> getMonthPowerUsedLine(GraphTime time, String graphType) {
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), time.getStart().getMonth(), "01", "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
        return getLineGraphByReporter(startTime, endTime, graphType, 1);
    }

    public List<Double> getYearPowerUsedLine(GraphTime time, String graphType){
        String startTime = String.format(queryTimeFormat, time.getStart().getYear(), "01", "01", "00", "00", "01");
        String endTime = String.format(queryTimeFormat, time.getEnd().getYear(), time.getEnd().getMonth(), time.getEnd().getDay(), "23", "59", "59");
        return getLineGraphByReporter(startTime, endTime, graphType, 2);
    }

    private List<Map<String, Object>> getPieGraphByReporter(String startTime, String endTime, String graphType, int queryType) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Reporter> reporterList = reporterDao.selectReporters(startTime, endTime, queryType);
        for (int i = 0; i < reporterList.size(); i++) {
            if (!reporterList.get(i).getBlockName().contains(projectName))
                reporterList.remove(i);
        }
        if (graphType.equals("total")) {
            Map<String, Object> entry1 = new HashMap<String, Object>();
            Map<String, Object> entry2 = new HashMap<String, Object>();
            Map<String, Object> entry3 = new HashMap<String, Object>();
            Map<String, Object> entry4 = new HashMap<String, Object>();

            List<Double> ac = new ArrayList<Double>();
            List<Double> lp = new ArrayList<Double>();
            List<Double> ep = new ArrayList<Double>();
            List<Double> other = new ArrayList<Double>();

            for (Reporter tmp: reporterList) {
                if (!tmp.getBlockName().contains(projectName))
                    continue;
                ac.add(Double.parseDouble(tmp.getAircondition()));
                lp.add(Double.parseDouble(tmp.getLightSocket()));
                ep.add(Double.parseDouble(tmp.getDfPower()));
                other.add(Double.parseDouble(tmp.getSpecial()));
            }

            entry1.put("name", "空调用电");
            entry1.put("y", getTotalPowerPerType(ac));
            result.add(entry1);
            entry2.put("name", "照明用电");
            entry2.put("y", getTotalPowerPerType(lp));
            result.add(entry2);
            entry3.put("name", "动力用电");
            entry3.put("y", getTotalPowerPerType(ep));
            result.add(entry3);
            entry4.put("name", "特殊用电");
            entry4.put("y", getTotalPowerPerType(other));
            result.add(entry4);
        }

        if (graphType.equals("ac")) {
            Map<String, Object> entry1 = new HashMap<String, Object>();
            Map<String, Object> entry2 = new HashMap<String, Object>();
            List<Double> ch = new ArrayList<Double>();
            List<Double> act = new ArrayList<Double>();

            for (Reporter tmp: reporterList) {
                ch.add(Double.parseDouble(tmp.getChstation()));
                act.add(Double.parseDouble(tmp.getAcTail()));
            }

            entry1.put("name", "冷热站");
            entry1.put("y", getTotalPowerPerType(ch));
            result.add(entry1);
            entry2.put("name", "空调末端");
            entry2.put("y", getTotalPowerPerType(act));
            result.add(entry2);
        }

        return result;
    }

    private List<Double> getLineGraphByReporter(String startTime, String endTime, String graphType , int queryType) {
        List<Double> result = new ArrayList<Double>();
        List<Reporter> reporterList = reporterDao.selectReporters(startTime, endTime, queryType);
        for (int i = 0; i < reporterList.size(); i++)
            if (!reporterList.get(i).getBlockName().contains(projectName))
                reporterList.remove(i);
        Calendar calendar = Calendar.getInstance();
        if (queryType == 0) {
            int dayLength = Integer.parseInt(endTime.split(" ")[1].split(":")[0]);
            for (int i = 0; i < dayLength; i++)
                result.add(0.0);
        }
        if (queryType == 1) {
            int monthLength = Integer.parseInt(endTime.split(" ")[0].split("-")[2]);
            for (int i = 0; i < monthLength - 1; i++)
                result.add(0.0);
        }
        if (queryType == 2) {
            int yearLength = Integer.parseInt(endTime.split(" ")[0].split("-")[1]);
            for (int i = 0; i < yearLength - 1; i++)
                result.add(0.0);
        }
        if (graphType.equals("ac")) {
            for (Reporter tmp: reporterList) {
                if (!tmp.getBlockName().contains(projectName))
                    continue;
                calendar.setTime(tmp.getTime());
                if (queryType == 0) {
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    result.set(hour, Double.parseDouble(tmp.getAircondition()));
                }
                if (queryType == 1) {
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    result.set(day-1, Double.parseDouble(tmp.getAircondition()));
                }
                if (queryType == 2) {
                    int month = calendar.get(Calendar.MONTH);
                    result.set(month, Double.parseDouble(tmp.getAircondition()));
                }
            }
        }
        if (graphType.equals("total")) {
            for (Reporter tmp: reporterList) {
                if (!tmp.getBlockName().contains(projectName))
                    continue;
                calendar.setTime(tmp.getTime());
                if (queryType == 0) {
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    result.set(hour, Double.parseDouble(tmp.getTotalPower()));
                }
                if (queryType == 1) {
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    result.set(day-1, Double.parseDouble(tmp.getTotalPower()));
                }
                if (queryType == 2) {
                    int month = calendar.get(Calendar.MONTH);
                    result.set(month, Double.parseDouble(tmp.getTotalPower()));
                }
            }
        }
        if (graphType.equals("light")) {
            for (Reporter tmp: reporterList) {
                if (!tmp.getBlockName().contains(projectName))
                    continue;
                calendar.setTime(tmp.getTime());
                if (queryType == 0) {
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    result.set(hour, Double.parseDouble(tmp.getLightAndSocket()) + Double.parseDouble(tmp.getCorridorAndEmergency()) + Double.parseDouble(tmp.getOutdoorLighting()));
                }
                if (queryType == 1) {
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    result.set(day-1, Double.parseDouble(tmp.getLightAndSocket()) + Double.parseDouble(tmp.getCorridorAndEmergency()) + Double.parseDouble(tmp.getOutdoorLighting()));
                }
                if (queryType == 2) {
                    int month = calendar.get(Calendar.MONTH);
                    result.set(month, Double.parseDouble(tmp.getLightAndSocket()) + Double.parseDouble(tmp.getCorridorAndEmergency()) + Double.parseDouble(tmp.getOutdoorLighting()));
                }
            }
        }
        if (graphType.equals("elevator")) {
            for (Reporter tmp: reporterList) {
                if (!tmp.getBlockName().contains(projectName))
                    continue;
                calendar.setTime(tmp.getTime());
                if (queryType == 0) {
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    result.set(hour, Double.parseDouble(tmp.getElevator()));
                }
                if (queryType == 1) {
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    result.set(day-1, Double.parseDouble(tmp.getElevator()));
                }
                if (queryType == 2) {
                    int month = calendar.get(Calendar.MONTH);
                    result.set(month, Double.parseDouble(tmp.getElevator()));
                }
            }
        }
        return result;
    }

    private List<Double> getAirConditionPower(GraphTime graphTime) {
        List<Double> result = new ArrayList<Double>();
        List<Double> typeB1 = getPowerPerType(graphTime.getEnd(), "B1");
        List<Double> typeB2 = getPowerPerType(graphTime.getEnd(), "B2");

        for (int i = 0; i < typeB1.size(); i++) {
            result.add(0.0);
        }

        for (int i = 0; i < typeB1.size(); i++) {
            result.set(i, result.get(i) + typeB1.get(i) + typeB2.get(i));
        }
        return result;
    }

    private List<Double> getLightingPower(GraphTime graphTime) {
        List<Double> result = new ArrayList<Double>();
        List<Double> typeA1 = getPowerPerType(graphTime.getEnd(), "A1");
        List<Double> typeA2 = getPowerPerType(graphTime.getEnd(), "A2");
        List<Double> typeA3 = getPowerPerType(graphTime.getEnd(), "A3");

        for (int i = 0; i < typeA1.size(); i++) {
            result.add(0.0);
        }

        for (int i = 0; i < typeA1.size(); i++) {
            result.set(i, result.get(i) + typeA1.get(i) + typeA2.get(i) + typeA3.get(i));
        }
        return result;
    }

    private List<Double> getElevatorPower(GraphTime graphTime) {
        return getPowerPerType(graphTime.getEnd(), "C1");
    }

    private List<Double> getdfPower(GraphTime graphTime) {
        List<Double> result = new ArrayList<Double>();
        List<Double> typeC1 = getPowerPerType(graphTime.getEnd(), "C1");
        List<Double> typeC2 = getPowerPerType(graphTime.getEnd(), "C2");
        List<Double> typeC3 = getPowerPerType(graphTime.getEnd(), "C3");

        for (int i = 0; i < typeC1.size(); i++) {
            result.add(0.0);
        }

        for (int i = 0; i < typeC1.size(); i++) {
            result.set(i, result.get(i) + typeC1.get(i) + typeC2.get(i) + typeC3.get(i));
        }
        return result;
    }

    private List<Double> getSpecialUsePower(GraphTime graphTime) {
        List<Double> result = new ArrayList<Double>();
        List<Double> typeD1 = getPowerPerType(graphTime.getEnd(), "D1");
        List<Double> typeD2 = getPowerPerType(graphTime.getEnd(), "D2");
        List<Double> typeD3 = getPowerPerType(graphTime.getEnd(), "D3");
        List<Double> typeD4 = getPowerPerType(graphTime.getEnd(), "D4");
        List<Double> typeD5 = getPowerPerType(graphTime.getEnd(), "D5");
        List<Double> typeD6 = getPowerPerType(graphTime.getEnd(), "D6");

        for (int i = 0; i < typeD1.size(); i++) {
            result.add(0.0);
        }

        for (int i = 0; i < typeD1.size(); i++) {
            result.set(i, result.get(i) + typeD1.get(i) + typeD2.get(i) + typeD3.get(i) + typeD4.get(i) + typeD5.get(i) + typeD6.get(i));
        }
        return result;
    }

    private List<Double> getAllPower(GraphTime graphTime) {
        List<Double> result = new ArrayList<Double>();
        List<Double> ac = getAirConditionPower(graphTime);
        List<Double> lp = getLightingPower(graphTime);
        List<Double> ep = getAirConditionPower(graphTime);
        List<Double> sp = getSpecialUsePower(graphTime);
        for (int i = 0; i < ac.size(); i++) {
            result.add(0.0);
        }
        for (int i = 0; i < ac.size(); i++) {
            result.set(i, result.get(i) + ac.get(i) + lp.get(i) + ep.get(i) + sp.get(i));
        }
        return result;
    }

    private List<Map<String, Object>> getAllDividePower(GraphTime graphTime) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> entry1 = new HashMap<String, Object>();
        Map<String, Object> entry2 = new HashMap<String, Object>();
        Map<String, Object> entry3 = new HashMap<String, Object>();
        Map<String, Object> entry4 = new HashMap<String, Object>();
        List<Double> ac = getAirConditionPower(graphTime);
        List<Double> lp = getLightingPower(graphTime);
        List<Double> ep = getdfPower(graphTime);
        List<Double> other = getSpecialUsePower(graphTime);
        entry1.put("name", "空调用电");
        entry1.put("y", getTotalPowerPerType(ac));
        result.add(entry1);
        entry2.put("name", "照明用电");
        entry2.put("y", getTotalPowerPerType(lp));
        result.add(entry2);
        entry3.put("name", "电梯用电");
        entry3.put("y", getTotalPowerPerType(ep));
        result.add(entry3);
        entry4.put("name", "特殊用电");
        entry4.put("y", getTotalPowerPerType(other));
        result.add(entry4);
        return result;
    }

    private List<Map<String, Object>> getAirConditionDividePower(GraphTime graphTime) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> entry1 = new HashMap<String, Object>();
        Map<String, Object> entry2 = new HashMap<String, Object>();
        List<Double> typeB1 = getPowerPerType(graphTime.getEnd(), "B1");
        List<Double> typeB2 = getPowerPerType(graphTime.getEnd(), "B2");
        entry1.put("name", "冷热站");
        entry1.put("y", getTotalPowerPerType(typeB1));
        result.add(entry1);
        entry2.put("name", "空调末端");
        entry2.put("y", getTotalPowerPerType(typeB2));
        result.add(entry2);
        return result;
    }

    public void intervalTimeWorkPerHour() {
//        Date time = new Date();
//        GraphTime graphTime = generateTime();
//        try {
//            List<Double> typeA1 = getPowerPerTypeForReporter(graphTime.getEnd(), "A1");
//            List<Double> typeA2 = getPowerPerTypeForReporter(graphTime.getEnd(), "A2");
//            List<Double> typeA3 = getPowerPerTypeForReporter(graphTime.getEnd(), "A3");
//            List<Double> typeB1 = getPowerPerTypeForReporter(graphTime.getEnd(), "B1");
//            List<Double> typeB2 = getPowerPerTypeForReporter(graphTime.getEnd(), "B2");
//            List<Double> typeC1 = getPowerPerTypeForReporter(graphTime.getEnd(), "C1");
//            List<Double> typeC2 = getPowerPerTypeForReporter(graphTime.getEnd(), "C2");
//            List<Double> typeC3 = getPowerPerTypeForReporter(graphTime.getEnd(), "C3");
//            List<Double> dfPower = new ArrayList<Double>();
//            List<Double> typeD1 = getPowerPerTypeForReporter(graphTime.getEnd(), "D1");
//            List<Double> typeD2 = getPowerPerTypeForReporter(graphTime.getEnd(), "D2");
//            List<Double> typeD3 = getPowerPerTypeForReporter(graphTime.getEnd(), "D3");
//            List<Double> typeD4 = getPowerPerTypeForReporter(graphTime.getEnd(), "D4");
//            List<Double> typeD5 = getPowerPerTypeForReporter(graphTime.getEnd(), "D5");
//            List<Double> typeD6 = getPowerPerTypeForReporter(graphTime.getEnd(), "D6");
//            List<Double> specialUsed = new ArrayList<Double>();
//            List<Double> total = new ArrayList<Double>();
//            List<Double> ac = new ArrayList<Double>();
//            List<Double> lp = new ArrayList<Double>();
//
//            for (int i = 0; i < typeC1.size(); i++) {
//                dfPower.add(0.0);
//            }
//
//            for (int i = 0; i < typeC1.size(); i++) {
//                Double c1tmp = 0.0;
//                Double c2tmp = 0.0;
//                Double c3tmp = 0.0;
//                if (typeC1.size() != 0)
//                    c1tmp = typeC1.get(i);
//                if (typeC2.size() != 0)
//                    c2tmp = typeC2.get(i);
//                if (typeC3.size() != 0)
//                    c3tmp = typeC3.get(i);
//                dfPower.set(i, dfPower.get(i) + c1tmp + c2tmp + c3tmp);
//            }
//
//            for (int i = 0; i < typeA1.size(); i++) {
//                lp.add(0.0);
//            }
//
//            for (int i = 0; i < typeA1.size(); i++) {
//                Double a1tmp = 0.0;
//                Double a2tmp = 0.0;
//                Double a3tmp = 0.0;
//                if (typeA1.size() != 0)
//                    a1tmp = typeA1.get(i);
//                if (typeA2.size() != 0)
//                    a2tmp = typeA2.get(i);
//                if (typeA3.size() != 0)
//                    a3tmp = typeA3.get(i);
//                lp.set(i, lp.get(i) + a1tmp + a2tmp + a3tmp);
//            }
//
//            for (int i = 0; i < typeB2.size(); i++) {
//                ac.add(0.0);
//            }
//
//            for (int i = 0; i < typeB2.size(); i++) {
//                Double b1tmp = 0.0;
//                Double b2tmp = 0.0;
//                if (typeB2.size() != 0)
//                    b2tmp = typeB2.get(i);
//                if (typeB1.size() != 0)
//                    b1tmp = typeB1.get(i);
//                ac.set(i, ac.get(i) + b1tmp + b2tmp);
//            }
//
//            for (int i = 0; i < typeD1.size(); i++) {
//                specialUsed.add(0.0);
//            }
//
//            for (int i = 0; i < typeD1.size(); i++) {
//                Double d1tmp = 0.0;
//                Double d2tmp = 0.0;
//                Double d3tmp = 0.0;
//                Double d4tmp = 0.0;
//                Double d5tmp = 0.0;
//                Double d6tmp = 0.0;
//                if (typeD1.size() != 0)
//                    d1tmp = typeD1.get(i);
//                if (typeD2.size() != 0)
//                    d2tmp = typeD2.get(i);
//                if (typeD3.size() != 0)
//                    d3tmp = typeD3.get(i);
//                if (typeD4.size() != 0)
//                    d4tmp = typeD4.get(i);
//                if (typeD5.size() != 0)
//                    d5tmp = typeD5.get(i);
//                if (typeD6.size() != 0)
//                    d6tmp = typeD6.get(i);
//                specialUsed.set(i, specialUsed.get(i) + d1tmp + d2tmp + d3tmp + d4tmp + d5tmp + d6tmp);
//            }
//
//            for (int i = 0; i < ac.size(); i++) {
//                total.add(0.0);
//            }
//            for (int i = 0; i < ac.size(); i++) {
//                total.set(i, total.get(i) + ac.get(i) + lp.get(i) + specialUsed.get(i) + dfPower.get(i));
//            }
//
//            int last = total.size() - 1;
////        String currentTime = String.format(queryTimeFormat, graphTime.getEnd().getYear(), graphTime.getEnd().getMonth(), graphTime.getEnd().getDay(),
////                graphTime.getEnd().getHour(), graphTime.getEnd().getMinute(), graphTime.getEnd().getSecond());
//            reporterDao.insertReporter(time, "0", projectName, total.get(last).toString(), total.get(last).toString(), lp.get(last).toString(), ac.get(last).toString(), dfPower.get(last).toString(),
//                    specialUsed.get(last).toString(), typeA1.get(last).toString(), typeA2.get(last).toString(), typeA3.get(last).toString(), typeB2.get(last).toString(), "0.0", "0.0", "0.0", "0.0", "0.0", typeC1.get(last).toString(),
//                    typeC2.get(last).toString(), typeC3.get(last).toString(), typeD1.get(last).toString(), typeD2.get(last).toString(), typeD3.get(last).toString(), typeD4.get(last).toString(), typeD5.get(last).toString(),
//                    "0.0", "0.0", "0.0", "0.0", "0.0", typeD6.get(last).toString(), typeB1.get(last).toString(), 0, area);
//        } catch (Exception ex) {
//            logger.error("error in intervalTimeWorkPerHour,ex: ", ex);
//        }


        initAllData();
    }

    public void intervalTimeWorkPerDay() {
//        GraphTime graphTime = generateTime();
//        String startTime = String.format(queryTimeFormat, graphTime.getStart().getYear(), graphTime.getStart().getMonth(), graphTime.getStart().getDay(),
//                "00", "00", "00");
//        String endTime = String.format(queryTimeFormat, graphTime.getEnd().getYear(), graphTime.getEnd().getMonth(), graphTime.getEnd().getDay(),
//                "23", "59", "30");
//        reporterSave(startTime, endTime, 0, 1);
        initAllData();
    }

    public void intervalTimeWorkPerMonth() {
//        GraphTime graphTime = generateTime();
//        String startTime = String.format(queryTimeFormat, graphTime.getStart().getYear(), graphTime.getStart().getMonth(), "01",
//                "00", "00", "00");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.DATE, -1);
//        int maxDate = calendar.get(Calendar.DATE);
//        String endTime = String.format(queryTimeFormat, graphTime.getEnd().getYear(), graphTime.getEnd().getMonth(), maxDate,
//                "23", "59", "30");
//        reporterSave(startTime, endTime, 1, 2);
        initAllData();
    }

    public void initAllData() {
        reporterDao.deleteAll();
        Date date = new Date();
        //hour
        this.initResult(0, date.getHours()-1, 0);
        this.initResult(1, date.getDate()-1, 1);
        this.initResult(1, date.getMonth(), 2);
    }

    public void intervalTimeWorkPerYear() {
        GraphTime graphTime = generateTime();
        String startTime = String.format(queryTimeFormat, graphTime.getStart().getYear(), "01", "01",
                "00", "01", "01");
        String endTime = String.format(queryTimeFormat, graphTime.getEnd().getYear(), "12", "31",
                "23", "59", "45");
        reporterSave(startTime, endTime, 2, 3);
    }

    private void reporterSave(String startTime, String endTime, int queryType, int saveType) {
        Date time = new Date();
        List<Reporter> reporterList = reporterDao.selectReporters(startTime, endTime, queryType);
        Reporter result = new Reporter();
        for (Reporter tmp: reporterList) {
            if (!tmp.getBlockName().contains(projectName))
                continue;
            result = addReporter(tmp, result);
            result.setBlockId(tmp.getBlockId());
            result.setBlockName(tmp.getBlockName());
        }
        result.setTime(time);
        result.setType(saveType);
        reporterDao.insertReporterByObj(result);
    }

    private String downLoadReporterStream(String startTime, String endTime, int queryType) throws IOException {
        long suffix = System.currentTimeMillis();
        String filePath = suffix + ".csv";
        String root = System.getProperty("webappPath");
        if (StringUtils.isEmpty(root))
            root = "C:\\server\\kjgb\\webapps\\ROOT\\";
        String path = root + "pic\\" + filePath;
        File file = new File(path);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "gbk");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(reporterFormat);
        bufferedWriter.newLine();
        List<Reporter> reporterList = reporterDao.selectReporters(startTime, endTime, queryType);
        for (Reporter tmp: reporterList) {
            if (!tmp.getBlockName().contains(projectName))
                continue;
            bufferedWriter.write(tmp.toString());
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStreamWriter.close();
        return filePath;
    }

    private Reporter addReporter(Reporter src, Reporter dest) {
        dest.setPower(String.valueOf(Double.parseDouble(src.getPower()) + Double.parseDouble(dest.getPower())));
        dest.setTotalPower(String.valueOf(Double.parseDouble(src.getTotalPower()) + Double.parseDouble(dest.getTotalPower())));
        dest.setLightSocket(String.valueOf(Double.parseDouble(src.getLightSocket()) + Double.parseDouble(dest.getLightSocket())));
        dest.setAircondition(String.valueOf(Double.parseDouble(src.getAircondition()) + Double.parseDouble(dest.getAircondition())));
        dest.setDfPower(String.valueOf(Double.parseDouble(src.getDfPower()) + Double.parseDouble(dest.getDfPower())));
        dest.setSpecial(String.valueOf(Double.parseDouble(src.getSpecial()) + Double.parseDouble(dest.getSpecial())));
        dest.setLightAndSocket(String.valueOf(Double.parseDouble(src.getLightAndSocket()) + Double.parseDouble(dest.getLightAndSocket())));
        dest.setCorridorAndEmergency(String.valueOf(Double.parseDouble(src.getCorridorAndEmergency()) + Double.parseDouble(dest.getCorridorAndEmergency())));
        dest.setOutdoorLighting(String.valueOf(Double.parseDouble(src.getOutdoorLighting()) + Double.parseDouble(dest.getOutdoorLighting())));
        dest.setAcTail(String.valueOf(Double.parseDouble(src.getAcTail()) + Double.parseDouble(dest.getAcTail())));
        dest.setHvapf(String.valueOf(Double.parseDouble(src.getHvapf()) + Double.parseDouble(dest.getHvapf())));
        dest.setMuau(String.valueOf(Double.parseDouble(src.getMuau()) + Double.parseDouble(dest.getMuau())));
        dest.setEahu(String.valueOf(Double.parseDouble(src.getEahu()) + Double.parseDouble(dest.getEahu())));
        dest.setFancoil(String.valueOf(Double.parseDouble(src.getFancoil()) + Double.parseDouble(dest.getFancoil())));
        dest.setSplitACU(String.valueOf(Double.parseDouble(src.getSplitACU()) + Double.parseDouble(dest.getSplitACU())));
        dest.setElevator(String.valueOf(Double.parseDouble(src.getElevator()) + Double.parseDouble(dest.getElevator())));
        dest.setWaterpump(String.valueOf(Double.parseDouble(src.getWaterpump()) + Double.parseDouble(dest.getWaterpump())));
        dest.setFanner(String.valueOf(Double.parseDouble(src.getFanner()) + Double.parseDouble(dest.getFanner())));
        dest.setDatacenter(String.valueOf(Double.parseDouble(src.getDatacenter()) + Double.parseDouble(dest.getDatacenter())));
        dest.setLaundry(String.valueOf(Double.parseDouble(src.getLaundry()) + Double.parseDouble(dest.getLaundry())));
        dest.setKitchen(String.valueOf(Double.parseDouble(src.getKitchen()) + Double.parseDouble(dest.getKitchen())));
        dest.setSwimpool(String.valueOf(Double.parseDouble(src.getSwimpool()) + Double.parseDouble(dest.getSwimpool())));
        dest.setGym(String.valueOf(Double.parseDouble(src.getGym()) + Double.parseDouble(dest.getGym())));
        dest.setWater(String.valueOf(Double.parseDouble(src.getWater()) + Double.parseDouble(dest.getWater())));
        dest.setToiletWater(String.valueOf(Double.parseDouble(src.getToiletWater()) + Double.parseDouble(dest.getToiletWater())));
        dest.setPiplinegas(String.valueOf(Double.parseDouble(src.getPiplinegas()) + Double.parseDouble(dest.getPiplinegas())));
        dest.setCentralheating(String.valueOf(Double.parseDouble(src.getCentralheating()) + Double.parseDouble(dest.getCentralheating())));
        dest.setCentralcold(String.valueOf(Double.parseDouble(src.getCentralcold()) + Double.parseDouble(dest.getCentralcold())));
        dest.setOther(String.valueOf(Double.parseDouble(src.getOther()) + Double.parseDouble(dest.getOther())));
        dest.setArea(src.getArea());
        return dest;
    }

    private GraphTime generateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        GraphParam graphParam = new GraphParam();
        graphParam.setDay(day);
        graphParam.setHour(hour);
        graphParam.setMinute(0);
        graphParam.setSecond(0);
        graphParam.setYear(year);
        graphParam.setMonth(month);
        GraphTime graphTime = new GraphTime();
        graphTime.setEnd(graphParam);
        graphTime.setStart(graphParam);
        return graphTime;
    }

    private double getTotalPowerPerType(List<Double> doubleList) {
        Double result = 0.0;
        for (double tmp: doubleList) {
            result += tmp;
        }
        return result;
    }

    private List<Double> getPowerPerType(GraphParam end, String type) {
        List<Double> result = new ArrayList<Double>();
        List<String> devices = parseCalculateDevice(type);
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        for (String device: devices) {
            List<Double> value = getPowerUsedPerHour(end, device);
            if (result.isEmpty())
                for (int i = 0; i < value.size(); i++)
                    result.add(0.0);
            for (int i = 0; i < value.size(); i++) {
                //保留两位小数
                result.set(i, Double.parseDouble(decimalFormat.format(result.get(i) + value.get(i))));
            }
        }
        return result;
    }

    private List<Double> getPowerPerTypeForReporter(GraphParam end, String type) {
        List<Double> result = new ArrayList<Double>();
        try {
            List<String> devices = parseCalculateDevice(type);
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            for (String device: devices) {
                List<Double> value = getPowerUsedPerHourForReporter(end, device);
                if (result.isEmpty())
                    for (int i = 0; i < value.size(); i++)
                        result.add(0.0);
                for (int i = 0; i < value.size(); i++) {
                    //保留一位小数
                    result.set(i, Double.parseDouble(decimalFormat.format(result.get(i) + value.get(i))));
                }
            }
        } catch (Exception ex) {
            logger.error("type: " + type + "exception in getPowerPerTypeForReporter, ex: ", ex);
        }
        if (result.size() == 0 )
            result.add(0.0);
        return result;
    }

    private List<Double> getPowerUsedPerHourForReporter(GraphParam end, String device) {
        List<Double> result = new ArrayList<Double>();
        try {
            for (int i = end.getHour() ; i <= end.getHour(); i++) {
                String startTime = String.format(queryTimeFormat, end.getYear(), end.getMonth(), end.getDay(), i, 0, 0);
                String endTime = String.format(queryTimeFormat, end.getYear(), end.getMonth(), end.getDay(), i , 59, 0);
                Devmess minValue = devmessDao.selectDevmessMin(device, startTime, endTime);
                Devmess maxValue = devmessDao.selectDevmessMax(device, startTime, endTime);
                if (minValue == null || maxValue == null){
                    result.add(0.0);
                    continue;
                }
                if (Double.parseDouble(minValue.getValue()) >= Double.parseDouble(maxValue.getValue()))
                    continue;
                double kwh = Double.parseDouble(maxValue.getValue()) - Double.parseDouble(minValue.getValue());
                result.add(kwh);
            }
        } catch (Exception ex) {
            logger.error("Exception in getPowerUserPerHourForReporter, ex: ", ex);
        }
        return result;
    }

    private List<Double> getPowerUsedPerHour(GraphParam end, String device) {
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i <= end.getHour(); i++) {
            String startTime = String.format(queryTimeFormat, end.getYear(), end.getMonth(), end.getDay(), i, 0, 0);
            String endTime = String.format(queryTimeFormat, end.getYear(), end.getMonth(), end.getDay(), i , 59, 0);
            Devmess minValue = devmessDao.selectDevmessMin(device, startTime, endTime);
            Devmess maxValue = devmessDao.selectDevmessMax(device, startTime, endTime);
            if (minValue == null || maxValue == null){
                result.add(0.0);
                continue;
            }
            if (Double.parseDouble(minValue.getValue()) >= Double.parseDouble(maxValue.getValue()))
                continue;
            double kwh = Double.parseDouble(maxValue.getValue()) - Double.parseDouble(minValue.getValue());
            result.add(kwh);
        }
        return result;
    }

    private List<String> parseCalculateDevice(String queryType) {
        List<PowerCalculate> powerCalculateList = powerCalculateDao.selectFormula(projectName + "." + queryType);
        if (powerCalculateList.isEmpty())
            return null;
        List<String> stringList = new ArrayList<String>();
        for (PowerCalculate powerCalculate: powerCalculateList) {
            String[] strings = powerCalculate.getCalculate().split("\\+");
            for (String tmp: strings) {
                String result = String.format(project, projectName, tmp, "Kwh");
                stringList.add(result);
            }
        }
        return stringList;
    }

    private Map<String, String> getPowerLabel() {
        return powerLabel;
    }

    public void setPowerLabel(Map<String, String> powerLabel) {
        this.powerLabel = powerLabel;
    }

    public DevmessDao getDevmessDao() {
        return devmessDao;
    }

    public void setDevmessDao(DevmessDao devmessDao) {
        this.devmessDao = devmessDao;
    }

    public List<Devmess> selectDevmessByPages(int pageNum, int pageSize) {
        int pretotal = (pageNum - 1) * pageSize;
        int len = pageSize;
        List<Devmess> ret = devmessDao.selectDevmessByPages(String.valueOf(pretotal), String.valueOf(len));
        return ret;
    }

    public boolean modifyDevmessById(HashMap<String, String> devmessItem) {
        int ret = devmessDao.modifyDevmessById(devmessItem.get("id"), devmessItem.get("name"), devmessItem.get("value"), devmessItem.get("time"), devmessItem.get("quality"));
        if (ret == 1) {
            return true;
        }
        return false;
    }

    public int getDevmessTotalCount() {
        return devmessDao.getDevmessTotalCount();
    }

    public int getDevmessFilteredCount(String search) {

        String sumSqlWhere = "";
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
        //System.out.println(search);
        //System.out.println(search.length());

        if (search.length() > 0 && !search.equals("''")) {
            //System.out.println("exec here");
            sumSqlWhere = "WHERE id LIKE '%'+#{search}+'%' OR name LIKE '%'+#{search}+'%' OR value LIKE '%'+#{search}+'%' OR time LIKE '%'+#{search}+'%' OR quality LIKE '%'+#{search}+'%'";
        }
        //System.out.println(sumSqlWhere);
        //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<");
        return devmessDao.getDevmessFilteredCount(sumSqlWhere,search);
    }

    public int getDevmessTimeFilteredCount(String startTime,String endTime){
        return devmessDao.getDevmessTimeFilteredCount(startTime,endTime);
    }

    public List<Devmess> getDevmessByConditions(String order_column, String order_dir, String search, String start, String length) {
        //$totalResultSql = "SELECT first_name,last_name,position,office,start_date,salary FROM DATATABLES_DEMO";
        //SELECT id,name,value,quality,time FROM ( SELECT id,name,value,quality,time, ROW_NUMBER() OVER (ORDER BY id) as row FROM  devmess) a WHERE row > 5 and row <= 10
        //String totalResultSql = "SELECT id,name,value,quality,time FROM ( SELECT id,name,value,quality,time, ROW_NUMBER() OVER (ORDER BY id) as row FROM  devmess) a WHERE row >= 5 and row < 10";

        /*
        String limitSql = "";
        if(start.length()>0 && length.length()>0){
            limitSql = " LIMIT " + Integer.parseInt(start)+","+Integer.parseInt(length);
        }
        */
        //SELECT top ${len} id, name, time, value, quality FROM devmess WHERE (id NOT IN (SELECT top ${pretotal} id FROM devmess ORDER BY id)) ORDER BY id
        String orderSql = "";
        switch (Integer.parseInt(order_column)) {
            case 0:
                orderSql = " order by id " + order_dir;
                break;
            case 1:
                orderSql = " order by name " + order_dir;
                break;
            case 2:
                orderSql = " order by value " + order_dir;
                break;
            case 3:
                orderSql = " order by time " + order_dir;
                break;
            case 4:
                orderSql = " order by quality " + order_dir;
                break;
            default:
                orderSql = " order by id asc ";
        }

        String sumSqlWhere = "";
        if (search.length() > 0 && !search.equals("''")) {
            sumSqlWhere = "WHERE id LIKE '%'+#{search}+'%' OR name LIKE '%'+#{search}+'%' OR value LIKE '%'+#{search}+'%' OR time LIKE '%'+#{search}+'%' OR quality LIKE '%'+#{search}+'%'";
        }
        int end = Integer.parseInt(start) + Integer.parseInt(length);
        List<Devmess> ret = devmessDao.getDevmessByConditions(start, String.valueOf(end), search, orderSql, sumSqlWhere);
        return ret;
    }


    public List<Devmess> getDevmessByTimeConditions(String order_column, String order_dir, String startTime,String endTime, String start, String length) {
        String orderSql = "";
        switch (Integer.parseInt(order_column)) {
            case 0:
                orderSql = " order by id " + order_dir;
                break;
            case 1:
                orderSql = " order by name " + order_dir;
                break;
            case 2:
                orderSql = " order by value " + order_dir;
                break;
            case 3:
                orderSql = " order by time " + order_dir;
                break;
            case 4:
                orderSql = " order by quality " + order_dir;
                break;
            default:
                orderSql = " order by id asc ";
        }

        int end = Integer.parseInt(start) + Integer.parseInt(length);
        List<Devmess> ret = devmessDao.getDevmessByTimeConditions(start, String.valueOf(end), startTime, endTime, orderSql);
        return ret;
    }

    public JSONObject getLatestDevmessByDeviceId(String deviceID){
        /*
        String projectDeviceId = getProjectName()+"-"+deviceID;//;
        List<Devmess> devmesses = devmessDao.getLatestDevmessByDeviceId(deviceID);

        JSONObject ret = new JSONObject();
        for (Devmess d : devmesses) {
            String name = d.getName();
            int dotIndex = name.lastIndexOf(".");
            if(dotIndex!=-1){
                String key = name.substring(dotIndex+1);
                try {
                    ret.put(URLEncoder.encode(powerLabel.get(key),"utf-8"),d.getValue());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
        */
        /*
        *
                <entry key="CT" value="电流变比"/>
                <entry key="Hz" value="频率"/>
                <entry key="Ia" value="A相电流"/>
                <entry key="Ib" value="B相电流"/>
                <entry key="Ic" value="C相电流"/>
                <entry key="Kvar" value="无功电能"/>
                <entry key="Kwh" value="有功电能"/>
                <entry key="PF" value="功率因素"/>
                <entry key="Ps" value="有功功率"/>
                <entry key="PT" value="电压变比"/>
                <entry key="Qs" value="无功功率"/>
                <entry key="Ss" value="视在功率"/>
                <entry key="Ua" value="A相电压"/>
                <entry key="Ub" value="B相电压"/>
                <entry key="Uc" value="C相电压"/>
        */
        JSONObject ret = new JSONObject();
        //String projectDeviceId = projectName+"."+deviceID;
        String projectDeviceId = projectName+"."+deviceID;
        ArrayList<String> suffixs =new ArrayList<String>(Arrays.asList("CT","Hz","Ia","Ib","Ic","Kvarh","Kwh","PF","Ps","PT","Qs","Ss","Ua","Ub","Uc"));
        for (String suffix:suffixs) {
            Devmess d = devmessDao.getLatestDevmessByName(projectDeviceId+"."+suffix);
            if(d == null){
                continue;
            }
            String name = d.getName();
            int dotIndex = name.lastIndexOf(".");
            if(dotIndex!=-1){
                String key = name.substring(dotIndex+1);
                try {
                    ret.put(URLEncoder.encode(powerLabel.get(key),"utf-8"),d.getValue());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public void init() {
        if (accountDao.selectUserByUsername("admin") == null)
            accountDao.insertUser("admin", "power", "admin", "tmp@qq.com");
        if (!powerCalculateDao.selectFormula(projectName + "." + "A1").isEmpty())
            return;
        powerCalculateDao.insert(projectName + "." + "A1", "", "照明与插座", "");
        powerCalculateDao.insert(projectName + "." + "A2", "", "走廊与应急", "");
        powerCalculateDao.insert(projectName + "." + "A3", "", "室外景观照明", "");
        powerCalculateDao.insert(projectName + "." + "B1", "", "冷热站", "");
        powerCalculateDao.insert(projectName + "." + "B2", "", "空调末端", "");
        powerCalculateDao.insert(projectName + "." + "C1", "", "电梯", "");
        powerCalculateDao.insert(projectName + "." + "C2", "", "水泵", "");
        powerCalculateDao.insert(projectName + "." + "C3", "", "通风机", "");
        powerCalculateDao.insert(projectName + "." + "D1", "", "信息中心", "");
        powerCalculateDao.insert(projectName + "." + "D2", "", "洗衣房", "");
        powerCalculateDao.insert(projectName + "." + "D3", "", "厨房餐厅", "");
        powerCalculateDao.insert(projectName + "." + "D4", "", "游泳池", "");
        powerCalculateDao.insert(projectName + "." + "D5", "", "健身房", "");
        powerCalculateDao.insert(projectName + "." + "D6", "", "其它", "");
    }

    public boolean addDevmessItem(HashMap<String,String> devmessItem){
        int ret =  devmessDao.addDevmessItem(devmessItem.get("name"),devmessItem.get("value"),devmessItem.get("time"),devmessItem.get("quality"));
        System.out.print(ret);
        if(ret == 1)
            return true;
        return false;
    }

    public boolean addDevmessItems(String filePath){
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String temp = null;
            int line = 1;
            while((temp = reader.readLine())!=null){
                if(line > 1){
                    HashMap<String,String> devmessItem = new HashMap<String, String>();
                    System.out.println(temp);
                    String[] itemArr = temp.split(",");
                    devmessItem.put("name",itemArr[0]);
                    devmessItem.put("value",itemArr[1]);

                    Date date =  sdfcsv.parse(itemArr[2]);
                    devmessItem.put("time",sdfsql.format(date));
                    devmessItem.put("quality",itemArr[3]);

                    boolean ret = addDevmessItem(devmessItem);
                    if(!ret)
                        return false;
                }
                line++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void initResult(int start, int end, int type) {
        for (int i = start; i <= end; i++) {
            Random random = new Random(System.currentTimeMillis());

            ArrayList<String> re = new ArrayList<String>();

            for (int j=0; j < 37; j++) {
                int tmp = random.nextInt(100) + 1;
                re.add(String.valueOf(tmp));
            }

            Date time = new Date();
            if (type == 0) {
                time.setHours(i);
            }
            if (type == 1) {
                time.setDate(i);
            }
            if (type == 2)
                time.setMonth(i-1);

            reporterDao.insertReporter(time, "2", "test", re.get(29), re.get(30),
                    re.get(5), re.get(16), re.get(17), re.get(24), re.get(31),
                    re.get(6), re.get(15), re.get(18), re.get(25), re.get(32),
                    re.get(7), re.get(14), re.get(19), re.get(26), re.get(33),
                    re.get(8), re.get(13), re.get(20), re.get(27), re.get(34),
                    re.get(9), re.get(12), re.get(21), re.get(28), re.get(35),
                    re.get(10), re.get(11), re.get(22), type, 2
            );
        }
    }
}
