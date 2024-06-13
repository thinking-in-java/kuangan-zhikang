package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhWxUserWeb;

public interface YhWxUserWebMapper {
    int deleteByPrimaryKey(String userId);

    int insert(YhWxUserWeb record);

    int insertSelective(YhWxUserWeb record);

    YhWxUserWeb selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(YhWxUserWeb record);

    int updateByPrimaryKey(YhWxUserWeb record);
    
    List<String> findUseroppenIdByDoctorAll(@Param("usercode")String usercode,@Param("groupid")String groupid);
    
    YhWxUserWeb showRegInfoByWx(String oppenId);
}