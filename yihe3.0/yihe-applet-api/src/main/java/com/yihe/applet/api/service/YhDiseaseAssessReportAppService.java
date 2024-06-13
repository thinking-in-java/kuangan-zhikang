package com.yihe.applet.api.service;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhDiseaseAssessReport;

/**
 * Description:
 * Created by Administrator on 2020/5/26 15:13
 */
public interface YhDiseaseAssessReportAppService {
    /*
        添加数据
         */
    int insertYhDiseaseAssessReport(YhDiseaseAssessReport yhDiseaseAssessReportApp);
    /*
             查询分表评估次数
     */
    int selMyAssessCount(String userCode);
    
    YhDiseaseAssessReport selectAssessReport(@Param("diseaseid") Integer diseaseid, @Param("requestNum") String requestNum);
}
