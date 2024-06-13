package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserPersonageHospitalizedApp;

public interface YhUserPersonageHospitalizedAppService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(YhUserPersonageHospitalizedApp record);

    YhUserPersonageHospitalizedApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageHospitalizedApp record);
    
    List<YhUserPersonageHospitalizedApp> showhospitalized(String userCode);
}
