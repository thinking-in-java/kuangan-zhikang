package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserCellDetectionWeb;
import com.yihe.system.domain.YhUserQuantumDetectionWeb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhUserCellDetectionWebMapper {
    YhUserCellDetectionWeb findCellDeteInfoList(String usercode);

    List<YhUserCellDetectionWeb> findByusercode(String usercode);
    int deleteByPrimaryKey(Integer cid);

    int insert(YhUserCellDetectionWeb record);

    int insertSelective(YhUserCellDetectionWeb record);

    YhUserCellDetectionWeb selectByPrimaryKey(Integer cid);

    List<YhUserCellDetectionWeb> selectByPrimaryKeyFindEntryTime(String usercode);

    List<YhUserCellDetectionWeb> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time, @Param("usercode")String usercode);

    YhUserCellDetectionWeb selectByPrimaryKeyNewInfo(String usercode);

    int updateByPrimaryKeySelective(YhUserCellDetectionWeb record);

    int updateByPrimaryKey(YhUserCellDetectionWeb record);
}