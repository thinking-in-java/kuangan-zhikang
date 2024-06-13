package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserCellDetectionWeb;
import com.yihe.system.domain.YhUserQuantumDetectionWeb;
import org.apache.ibatis.annotations.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface YhUserQuantumDetectionWebMapper {
    YhUserQuantumDetectionWeb findQuantumInfoList(String usercode);
    List<YhUserQuantumDetectionWeb> findByusercode(String usercode);
     int deleteByPrimaryKey(Integer qid);

    int insert(YhUserQuantumDetectionWeb record);

    int insertSelective(YhUserQuantumDetectionWeb record);

    YhUserQuantumDetectionWeb selectByPrimaryKey(Integer qid);

    List<YhUserQuantumDetectionWeb> selectByPrimaryKeyFindEntryTime(String usercode);

    List<YhUserQuantumDetectionWeb> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time, @Param("usercode")String usercode);


    YhUserQuantumDetectionWeb selectByPrimaryKeyNewInfo(String usercode);

    int updateByPrimaryKeySelective(YhUserQuantumDetectionWeb record);

    int updateByPrimaryKey(YhUserQuantumDetectionWeb record);
}