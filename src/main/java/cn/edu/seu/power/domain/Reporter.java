package cn.edu.seu.power.domain;

import java.util.Date;

/**
 * Created by zhh on 2016/7/18.
 */
public class Reporter {
    private int id;
    private Date time;
    private String blockId;
    private String blockName;
    private String power;
    private String totalPower;
    private String lightSocket;
    private String aircondition;
    private String dfPower;
    private String special;
    private String lightAndSocket;
    private String corridorAndEmergency;
    private String outdoorLighting;
    private String acTail;
    private String hvapf;
    private String muau;
    private String eahu;
    private String fancoil;
    private String splitACU;
    private String elevator;
    private String waterpump;
    private String fanner;
    private String datacenter;
    private String laundry;
    private String kitchen;
    private String swimpool;
    private String gym;
    private String water;
    private String toiletWater;
    private String piplinegas;
    private String centralheating;
    private String centralcold;
    private String other;
    private String chstation;
    private int type;
    private int area;

    private double powerUsed;

    public Reporter() {
        this.type = 0;
        this.chstation = "0.0";
        this.other = "0.0";
        this.centralcold = "0.0";
        this.centralheating = "0.0";
        this.toiletWater = "0.0";
        this.piplinegas = "0.0";
        this.water = "0.0";
        this.gym = "0.0";
        this.swimpool = "0.0";
        this.kitchen = "0.0";
        this.laundry = "0.0";
        this.fanner = "0.0";
        this.datacenter = "0.0";
        this.waterpump = "0.0";
        this.elevator = "0.0";
        this.splitACU = "0.0";
        this.fancoil = "0.0";
        this.muau = "0.0";
        this.eahu = "0.0";
        this.blockName = "0.0";
        this.power = "0.0";
        this.totalPower = "0.0";
        this.lightSocket = "0.0";
        this.aircondition = "0.0";
        this.dfPower = "0.0";
        this.special = "0.0";
        this.lightAndSocket = "0.0";
        this.outdoorLighting = "0.0";
        this.corridorAndEmergency = "0.0";
        this.acTail = "0.0";
        this.hvapf = "0.0";
        this.area = 0;
    }


    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public double getPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed(double powerUsed) {
        this.powerUsed = powerUsed;
    }

    public String getChstation() {
        return chstation;
    }

    public void setChstation(String chstation) {
        this.chstation = chstation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getLightSocket() {
        return lightSocket;
    }

    public void setLightSocket(String lightSocket) {
        this.lightSocket = lightSocket;
    }

    public String getAircondition() {
        return aircondition;
    }

    public void setAircondition(String aircondition) {
        this.aircondition = aircondition;
    }

    public String getDfPower() {
        return dfPower;
    }

    public void setDfPower(String dfPower) {
        this.dfPower = dfPower;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getLightAndSocket() {
        return lightAndSocket;
    }

    public void setLightAndSocket(String lightAndSocket) {
        this.lightAndSocket = lightAndSocket;
    }

    public String getCorridorAndEmergency() {
        return corridorAndEmergency;
    }

    public void setCorridorAndEmergency(String corridorAndEmergency) {
        this.corridorAndEmergency = corridorAndEmergency;
    }

    public String getOutdoorLighting() {
        return outdoorLighting;
    }

    public void setOutdoorLighting(String outdoorLighting) {
        this.outdoorLighting = outdoorLighting;
    }

    public String getAcTail() {
        return acTail;
    }

    public void setAcTail(String acTail) {
        this.acTail = acTail;
    }

    public String getHvapf() {
        return hvapf;
    }

    public void setHvapf(String hvapf) {
        this.hvapf = hvapf;
    }

    public String getMuau() {
        return muau;
    }

    public void setMuau(String muau) {
        this.muau = muau;
    }

    public String getEahu() {
        return eahu;
    }

    public void setEahu(String eahu) {
        this.eahu = eahu;
    }

    public String getFancoil() {
        return fancoil;
    }

    public void setFancoil(String fancoil) {
        this.fancoil = fancoil;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getSplitACU() {
        return splitACU;
    }

    public void setSplitACU(String splitACU) {
        this.splitACU = splitACU;
    }

    public String getWaterpump() {
        return waterpump;
    }

    public void setWaterpump(String waterpump) {
        this.waterpump = waterpump;
    }

    public String getFanner() {
        return fanner;
    }

    public void setFanner(String fanner) {
        this.fanner = fanner;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public String getLaundry() {
        return laundry;
    }

    public void setLaundry(String laundry) {
        this.laundry = laundry;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getSwimpool() {
        return swimpool;
    }

    public void setSwimpool(String swimpool) {
        this.swimpool = swimpool;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getToiletWater() {
        return toiletWater;
    }

    public void setToiletWater(String toiletWater) {
        this.toiletWater = toiletWater;
    }

    public String getPiplinegas() {
        return piplinegas;
    }

    public void setPiplinegas(String piplinegas) {
        this.piplinegas = piplinegas;
    }

    public String getCentralheating() {
        return centralheating;
    }

    public void setCentralheating(String centralheating) {
        this.centralheating = centralheating;
    }

    public String getCentralcold() {
        return centralcold;
    }

    public void setCentralcold(String centralcold) {
        this.centralcold = centralcold;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getTime()).append(",").append(getBlockId()).append(",").append(getBlockName()).append(",")
                .append(getPower()).append(",").append(getTotalPower()).append(",").append(getLightSocket()).append(",")
                .append(getAircondition()).append(",").append(getDfPower()).append(",").append(getSpecial()).append(",")
                .append(getLightAndSocket()).append(",").append(getCorridorAndEmergency()).append(",").append(getOutdoorLighting())
                .append(",").append(getChstation()).append(",").append(getAcTail()).append(",").append(getHvapf()).append(",").append(getMuau()).append(",")
                .append(getEahu()).append(",").append(getFancoil()).append(",").append(getSplitACU()).append(",").append(getElevator())
                .append(",").append(getWaterpump()).append(",").append(getFanner()).append(",").append(getDatacenter()).append(",")
                .append(getLaundry()).append(",").append(getKitchen()).append(",").append(getSwimpool()).append(",").append(getGym())
                .append(",").append(getWater()).append(",").append(getToiletWater()).append(",").append(getPiplinegas()).append(",")
                .append(getCentralheating()).append(",").append(getCentralcold()).append(",").append("0.0");
        return stringBuffer.toString();
    }
}