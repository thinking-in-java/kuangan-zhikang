package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportApp;
import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportNewApp;

/**
 * Description:
 * Created by Administrator on 2020/5/27 9:49
 */
public interface YhDiseaseAssessTotalReportAppMapper {
    /*
          插入总表数据
     */
    int insertIntoTotalReport(YhDiseaseAssessTotalReportApp yhDiseaseAssessTotalReportApp);
    
    YhDiseaseAssessTotalReportApp selectTotalReportByuserCode(String userCode);
    
    YhDiseaseAssessTotalReportApp selectTotalReportByuserCodeByNewest(String userCode);

    YhDiseaseAssessTotalReportApp queryOaUserAssessInfo(String userCode);

    int insertIntoTotalReportNew(YhDiseaseAssessTotalReportNewApp record);
}
