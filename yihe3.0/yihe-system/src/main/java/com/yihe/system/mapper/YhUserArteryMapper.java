package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhUserArtery;
import org.apache.ibatis.annotations.Param;

public interface YhUserArteryMapper {

  YhUserArtery selectByPrimaryKeyNewest(String usercode);

  List<YhUserArtery> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time")String time,@Param("usercode")String usercode);

  List<YhUserArtery> selectByPrimaryKeyFindEntryTime(String usercode);

  List<YhUserArtery>  findArteryAllByUserCode(@Param("usercode")String usercode, @Param("timetype")Integer timetype, @Param("strTime")String strTime, @Param("endTime")String endTime);

    YhUserArtery  findNewInfoByUserCode(String usercode);

    int deleteByPrimaryKey(Integer id);

    int insert(YhUserArtery record);

    int insertSelective(YhUserArtery record);

    YhUserArtery selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserArtery record);

    int updateByPrimaryKey(YhUserArtery record);
    
    YhUserArtery selectUserYhUserArteryByLimitParm(String userCode);
    
    List<YhUserArtery> selectAll(String userCode);
}