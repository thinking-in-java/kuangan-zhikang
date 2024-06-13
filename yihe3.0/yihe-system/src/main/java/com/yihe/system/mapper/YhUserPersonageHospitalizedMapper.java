package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserPersonageHospitalized;

public interface YhUserPersonageHospitalizedMapper {
    int deleteByPrimaryKey(Integer id);

    YhUserPersonageHospitalized selectHospitalized(String userCode);
    
    int insert(YhUserPersonageHospitalized yhUserPersonageHospitalized);

    int insertSelective(YhUserPersonageHospitalized record);

    List<YhUserPersonageHospitalized> selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageHospitalized yhUserPersonageHospitalized);

    int updateByPrimaryKey(YhUserPersonageHospitalized yhUserPersonageHospitalized);
    
    int deleteAll(String userCode);
}