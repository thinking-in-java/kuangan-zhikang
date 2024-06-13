package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhPressureEvaluateApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhPressureEvaluateAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhPressureEvaluateApp record);

    int insertSelective(YhPressureEvaluateApp record);

    YhPressureEvaluateApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhPressureEvaluateApp record);

    int updateByPrimaryKey(YhPressureEvaluateApp record);
    
    List<YhPressureEvaluateApp> selectAlList(@Param("type") Integer type,@Param("userCode") String userCode);
    
    List<YhPressureEvaluateApp> selectCount(String userCode);

    int assessCount(@Param("type") Integer type);

    YhPressureEvaluateApp findUserRecord(@Param("type") Integer type,@Param("userCode") String userCode);
}