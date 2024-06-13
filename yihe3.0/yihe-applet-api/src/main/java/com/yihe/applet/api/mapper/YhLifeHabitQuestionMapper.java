package com.yihe.applet.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhLifeHabitQuestion;

public interface YhLifeHabitQuestionMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitQuestion record);

    int insertSelective(YhLifeHabitQuestion record);

    List<YhLifeHabitQuestion> selectByExample();

    YhLifeHabitQuestion selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(YhLifeHabitQuestion record);

    int updateByPrimaryKey(YhLifeHabitQuestion record);
}