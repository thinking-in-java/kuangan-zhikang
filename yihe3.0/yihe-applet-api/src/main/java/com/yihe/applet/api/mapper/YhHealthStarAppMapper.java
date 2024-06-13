package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhHealthStarApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhHealthStarAppMapper {
    int deleteByPrimaryKey(Integer healthstarId);

    int insert(YhHealthStarApp record);

    int insertSelective(YhHealthStarApp record);

    YhHealthStarApp selectByPrimaryKey(Integer healthstarId);

    int updateByPrimaryKeySelective(YhHealthStarApp record);

    int updateByPrimaryKey(YhHealthStarApp record);
    //查询用户连续记录天数
    int selectUserContinuousRecord(@Param("userCode") String userCode,@Param("healthstarsEvents") String healthstarsEvents);
    
    //查询用户昨天的记录
    YhHealthStarApp selectNewestStar(@Param("userCode") String userCode,@Param("healthstarsEvents") String healthstarsEvents);
    //分页查询积分记录
    List<YhHealthStarApp> selectYhHealthStarAppParam(@Param("page") Integer page, @Param("pageSize") Integer pageSize,
    		@Param("userCode") String userCode,@Param("type") Integer type);
    
    List<YhHealthStarApp> selectUserYhHealthStar(@Param("userCode") String userCode,@Param("type") Integer type);

    Integer selectUserYhHealthStarSum(@Param("userCode") String userCode,@Param("type") Integer type);

  //根据event_type,userCode 和时间（每日任务为当前时间，每月任务为本月）查询积分记录表
    String selectRecordtypeAndTime (Map<String, Object> map); 
    
    String selectRecordtypeAndTime2 (Map<String, Object> map); 
}