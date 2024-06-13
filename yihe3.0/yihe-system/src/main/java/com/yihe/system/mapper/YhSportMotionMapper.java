package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSportMotion;

public interface YhSportMotionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportMotion record);

    int insertSelective(YhSportMotion record);

    YhSportMotion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportMotion record);

    int updateByPrimaryKey(YhSportMotion record);
    
    List<YhSportMotion> selectSport(Map<String, Object> map);
    
    int selectSportConut(Map<String, Object> map);
    
    int selctSportNmaeRepetition(Map<String, Object> map);
    
    List<YhSportMotion> selectByname(Map<String, Object> map);

    List<YhSportMotion> selectByhot(Map<String, Object> map);
    
    List<YhSportMotion> selectByhotCollect(String userCode);
    
    YhSportMotion sportShowName(String sportName);
    
    List<YhSportMotion> selectSportNameByParam(String sportName);
    
    List<YhSportMotion> selectNewestTendata();
    
    List<String> showsportformatMonth();
    
    List<String> showsportQujilu();
    
    List<String> showsportNianjilu();
}