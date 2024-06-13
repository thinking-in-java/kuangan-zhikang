package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserPersonageHospitalizedApp;

public interface YhUserPersonageHospitalizedAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageHospitalizedApp record);

    int insertSelective(YhUserPersonageHospitalizedApp record);

    YhUserPersonageHospitalizedApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageHospitalizedApp record);

    int updateByPrimaryKey(YhUserPersonageHospitalizedApp record);
    
    List<YhUserPersonageHospitalizedApp> showhospitalized(String userCode);
}