package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhPainTotalReportApp;
 
public interface YhPainTotalReportAppMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(YhPainTotalReportApp record);

    int insertSelective(YhPainTotalReportApp record);

    YhPainTotalReportApp selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(YhPainTotalReportApp record);

    int updateByPrimaryKey(YhPainTotalReportApp record);
    
    YhPainTotalReportApp selectYhPainResolveLimitByParam(String userCode);



    int YhPainTotalReportAppCount(String userCode);
}