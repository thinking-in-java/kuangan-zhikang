package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhScoreEventCategoryWeb;

public interface YhScoreEventCategoryWebMapper {
    int deleteByPrimaryKey(Integer scoreeventId);

    int insert(YhScoreEventCategoryWeb record);

    int insertSelective(YhScoreEventCategoryWeb record);

    YhScoreEventCategoryWeb selectByPrimaryKey(Integer scoreeventId);

    int updateByPrimaryKeySelective(YhScoreEventCategoryWeb record);

    int updateByPrimaryKey(YhScoreEventCategoryWeb record);
    
    List<YhScoreEventCategoryWeb> selectYhScoreEventCategoryPage(@Param("type") Integer type,@Param("putaway") Integer putaway,@Param("eventName") String eventName,@Param("orgCode") String orgCode);

    YhScoreEventCategoryWeb selectEventType(@Param("eventType")String eventType,@Param("orgCode") String orgCode);
}