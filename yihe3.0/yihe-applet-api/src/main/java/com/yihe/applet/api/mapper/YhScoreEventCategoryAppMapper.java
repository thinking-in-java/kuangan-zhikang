package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhScoreEventCategoryApp;
import org.apache.ibatis.annotations.Param;

public interface YhScoreEventCategoryAppMapper {
    int deleteByPrimaryKey(Integer scoreeventId);

    int insert(YhScoreEventCategoryApp record);

    int insertSelective(YhScoreEventCategoryApp record);

    YhScoreEventCategoryApp selectByPrimaryKey(Integer scoreeventId);

    int updateByPrimaryKeySelective(YhScoreEventCategoryApp record);

    int updateByPrimaryKey(YhScoreEventCategoryApp record);
    
    YhScoreEventCategoryApp selectEventType(@Param("eventType") String eventType,@Param("orgCode")  String orgCode);

	List<Map<String, Object>> selectIntegralStatus(Map<String, Object> map);

    String seleventType(String eventName);
}