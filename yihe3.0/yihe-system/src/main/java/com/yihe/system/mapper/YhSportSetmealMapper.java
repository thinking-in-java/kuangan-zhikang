package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSportSetmeal;

public interface YhSportSetmealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportSetmeal record);

    int insertSelective(YhSportSetmeal record);

    YhSportSetmeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportSetmeal record);

    int updateByPrimaryKey(YhSportSetmeal record);
    
    List<YhSportSetmeal> selectSetmeal(Map<String, Object> map);
    
    int selectSetmealConut(Map<String, Object> map);
    
    List<YhSportSetmeal> selectSetmealshow(Map<String, Object> map);
    
    /*
    根据疾病id以及套餐id查询，默认oneself为1
     */
    YhSportSetmeal selectYhSportSetmeal(YhSportSetmeal yhSportSetmeal);
}