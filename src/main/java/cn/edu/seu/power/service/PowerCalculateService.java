package cn.edu.seu.power.service;

import cn.edu.seu.power.dao.PowerCalculateDao;
import cn.edu.seu.power.domain.PowerCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhh on 2016/7/22.
 */
@Service
public class PowerCalculateService {

    @Value("#{appProp.projectName}")
    private String projectName;

    @Autowired
    private PowerCalculateDao powerCalculateDao;

    public List<PowerCalculate> getAllFormula() {
        List<PowerCalculate> powerCalculateList = new ArrayList<PowerCalculate>();
        List<PowerCalculate> queryResult =  powerCalculateDao.selectAll();
        for (PowerCalculate powerCalculate: queryResult) {
            if (!powerCalculate.getName().contains(projectName))
                continue;
            String[] splits = powerCalculate.getName().split("\\.");
            powerCalculate.setName(splits[splits.length - 1]);
            powerCalculateList.add(powerCalculate);
        }
        return powerCalculateList;
    }

    public void saveFormula(int id, String name, String calculate, String code) {
        powerCalculateDao.updateFormula(id, projectName + "." + name, calculate, code);
    }
}
