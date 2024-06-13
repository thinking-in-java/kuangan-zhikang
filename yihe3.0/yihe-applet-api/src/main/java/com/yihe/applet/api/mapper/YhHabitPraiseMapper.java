package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhHabitPraise;

import java.util.List;
import java.util.Map;

public interface YhHabitPraiseMapper {
    int deleteByPrimaryKey(Map<String, Object> map);

    int insert(YhHabitPraise record);

    int insertSelective(YhHabitPraise record);

    YhHabitPraise selectByPrimaryKey(Integer prId);

    int updateByPrimaryKeySelective(YhHabitPraise record);

    int updateByPrimaryKey(YhHabitPraise record);
    
    int showCountParise(String commentid);
    
    int showCountPariseCode(Map<String, Object> map);

    List<YhHabitPraise> selectCodeByCommentId(int id);

    YhHabitPraise selectprId(YhHabitPraise record);
}