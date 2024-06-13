package com.yihe.system.mapper;

import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDicAssessmentScreenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhDicAssessmentScreenMapper {
    long countByExample(YhDicAssessmentScreenExample example);

    int deleteByExample(YhDicAssessmentScreenExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(YhDicAssessmentScreen record);

    int insertSelective(YhDicAssessmentScreen record);

    List<YhDicAssessmentScreen> selectByExample(YhDicAssessmentScreenExample example);

    YhDicAssessmentScreen selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") YhDicAssessmentScreen record, @Param("example") YhDicAssessmentScreenExample example);

    int updateByExample(@Param("record") YhDicAssessmentScreen record, @Param("example") YhDicAssessmentScreenExample example);

    int updateByPrimaryKeySelective(YhDicAssessmentScreen record);

    int updateByPrimaryKey(YhDicAssessmentScreen record);
}