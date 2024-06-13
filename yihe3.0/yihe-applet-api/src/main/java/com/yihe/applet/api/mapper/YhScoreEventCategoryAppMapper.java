package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhScoreEventCategoryApp;

public interface YhScoreEventCategoryAppMapper {
    int deleteByPrimaryKey(Integer scoreeventId);

    int insert(YhScoreEventCategoryApp record);

    int insertSelective(YhScoreEventCategoryApp record);

    YhScoreEventCategoryApp selectByPrimaryKey(Integer scoreeventId);

    int updateByPrimaryKeySelective(YhScoreEventCategoryApp record);

    int updateByPrimaryKey(YhScoreEventCategoryApp record);
    
    YhScoreEventCategoryApp selectEventType(String eventType);

	List<Map<String, Object>> selectIntegralStatus(Map<String, Object> map);

    String seleventType(String eventName);
}