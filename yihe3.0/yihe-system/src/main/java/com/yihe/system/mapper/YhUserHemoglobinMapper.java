package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserHemoglobin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhUserHemoglobinMapper {

    YhUserHemoglobin  selectByPrimaryKeyNewest(String usercode);

    List<YhUserHemoglobin> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
                                                                    @Param("usercode") String usercode);

    List<String> selectByPrimaryKeyFindEntryTime(String usercode);

    List<YhUserHemoglobin> findhemoglobinAllByUserCode(@Param("usercode")String usercode, @Param("timetype")Integer timetype, @Param("strTime")String strTime, @Param("endTime")String endTime);

     int deleteByPrimaryKey(Integer id);

    int insert(YhUserHemoglobin record);

    int insertSelective(YhUserHemoglobin record);

    YhUserHemoglobin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserHemoglobin record);

    int updateByPrimaryKey(YhUserHemoglobin record);

    YhUserHemoglobin findHemoglobinNewInfo(String usercode);

    //查询用户最新血红蛋白信息
    Map<String,Object> selectUserHemoglobin(String userCode);
    //分页查询
    List<YhUserHemoglobin> selectPageByParam(String userCode);
    List<YhUserHemoglobin> selectHemoglobin(String userCode);

    YhUserHemoglobin selectByPrimaryKeyByUsercodeLimit(String userCode);
}