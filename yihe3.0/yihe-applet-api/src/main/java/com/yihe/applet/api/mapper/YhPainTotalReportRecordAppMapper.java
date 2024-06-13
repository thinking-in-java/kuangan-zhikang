package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhPainTotalReportRecordApp;

public interface YhPainTotalReportRecordAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhPainTotalReportRecordApp record);

    int insertSelective(YhPainTotalReportRecordApp record);

    YhPainTotalReportRecordApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhPainTotalReportRecordApp record);

    int updateByPrimaryKey(YhPainTotalReportRecordApp record);
}