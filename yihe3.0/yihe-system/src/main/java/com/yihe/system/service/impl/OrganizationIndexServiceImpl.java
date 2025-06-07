package com.yihe.system.service.impl;

import com.yihe.system.mapper.YHUserHeartRateMapper;
import com.yihe.system.service.OrganizationIndexService;
import com.yihe.system.utils.StringUtil;
import com.yihe.system.vo.AddNewLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationIndexServiceImpl implements OrganizationIndexService {
    @Autowired
    YHUserHeartRateMapper yur;

    @Override
    public Map<String, Object> bigDataCenterRightNew(String orgId) {
        //心率监测次数:num 平均心率值:avg
        Map<String, Object> heartRate = yur.checkHeartRateStatisticsByOrg(orgId);

        //血氧监测次数:num  平均血氧值:avg
        Map<String, Object> bloodOxygen = yur.checkOxygenStatisticsByOrg(orgId);

        //血压 平均收缩血压:sbp_avg
        //平均舒张血压:dbp_avg
        Map<String, Object> bloodGate = yur.checkBloodGateStatisticsByOrg(orgId);

        Map<String, Object> bloodSugar = yur.checkBloodSugarStatisticsByOrg(orgId);
        Map<String, Object> bodyFat = yur.checkBodyFatStatisticsByOrg(orgId);


        Map<String, Object> sleep = yur.checkSleepStatisticsByOrg(orgId);


        List<String> spo = yur.checkExerciseStatistics(orgId);
        BigDecimal step = new BigDecimal("0");
        BigDecimal heat = new BigDecimal("0");

        if (null != spo) {
            int size = spo.size();

            for (int i = 0; i < size; i++) {
                String s = spo.get(i);
                if (StringUtil.isNotEmpty(s) && s.contains("=")) {
                    String split1 = s.split("=")[0];
                    String split2 = s.split("=")[1];
                    step = step.add(new BigDecimal(split1));
                    heat = heat.add(new BigDecimal(split2));
                }
            }
        }
        Map<String, Object> sport=new HashMap<>();
        sport.put("step",step.setScale(2,BigDecimal.ROUND_HALF_UP));
        sport.put("heat",heat.setScale(2,BigDecimal.ROUND_HALF_UP));


        Map<String, Object> merged = new HashMap<>();
        merged.putAll(heartRate);
        merged.putAll(bloodOxygen);
        merged.putAll(bloodGate);
        merged.putAll(bloodSugar);
        merged.putAll(bodyFat);
        merged.putAll(sleep);
        merged.putAll(sport);

        return merged;
    }

    @Override
    public List<AddNewLogDTO> queryTheNewMonitoringLogs(String orgId) {

        return yur.queryTheNewMonitoringLogs(orgId);
    }
}
