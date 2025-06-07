package com.yihe.system.mapper;

import com.yihe.system.domain.YhHealthStarweb;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface YhHealthStarwebMapper {
    int deleteByPrimaryKey(Integer healthstarId);

    int insert(YhHealthStarweb record);

    int insertSelective(YhHealthStarweb record);

    YhHealthStarweb selectByPrimaryKey(Integer healthstarId);
    List<YhHealthStarweb> selectAllUserRecordPage (@Param("userCode") String userCode,@Param("qtstrTime") String qtstrTime,@Param("qtendTime") String qtendTime,@Param("validity") String validity);
    int updateByPrimaryKeySelective(YhHealthStarweb record);

    int updateByPrimaryKey(YhHealthStarweb record);
    List<YhHealthStarweb> selectAllUserRecord (@Param("userCode")String userCode,@Param("startTime") String startTime,@Param("endTime") String  endTime);
    List<YhHealthStarweb> selectUserAllIntegral(@Param("orgCode") String orgCode,@Param("name") String name, @Param("priceMin") String priceMin,@Param("priceMax") String priceMax);
    List<YhHealthStarweb> selectDoctorUserAllIntegral(@Param("usertype") Integer usertype,@Param("orgCode") String orgCode,@Param("orgCode1") String orgCode1,@Param("name") String name, @Param("priceMin") String priceMin,@Param("priceMax") String priceMax,@Param("startTime") String startTime,@Param("endTime") String  endTime);

    String selectRecordtypeAndTime(Map<String, Object> map);

    String selectRecordtypeAndTime2(Map<String, Object> map);
    String selectRecordtypeAndTime3(Map<String, Object> map);

    YhHealthStarweb selectNewestStar(@Param("userCode") String userCode,@Param("eventName")  String eventName);

    int selectUserContinuousRecord(@Param("userCode") String userCode,@Param("eventName")  String eventName);
}