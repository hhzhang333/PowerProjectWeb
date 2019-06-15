package cn.edu.seu.power.dao;

import cn.edu.seu.power.domain.PowerCalculate;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by zhh on 2016/7/18.
 */
public interface PowerCalculateDao {
    public List<PowerCalculate> selectFormula(@Param(value = "selectType") String type);

    public void updateFormula(@Param(value = "id") int id,
                              @Param(value = "name") String name,
                              @Param(value = "calculate") String calculate,
                              @Param(value = "code") String code);

    public List<PowerCalculate> selectAll();

    public void insert(@Param(value = "name") String name,
                       @Param(value = "calculate") String calculate,
                       @Param(value = "description") String description,
                       @Param(value = "code") String code);
}
