package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhInstrumentRecordApp;

public interface YhInstrumentRecordAppMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(YhInstrumentRecordApp record);

    int insertSelective(YhInstrumentRecordApp record);

    YhInstrumentRecordApp selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(YhInstrumentRecordApp record);

    int updateByPrimaryKey(YhInstrumentRecordApp record);
    
    int countData(String type);
}