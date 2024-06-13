package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserPersonageHospitalized;
import com.yihe.system.domain.YhUserPersonageSymptom;

public interface YhUserPersonageHospitalizedService {
	 int deleteByPrimaryKey(Integer id);
	 int insert(YhUserPersonageHospitalized yhUserPersonageHospitalized);
	  int updateByPrimaryKey(YhUserPersonageHospitalized yhUserPersonageHospitalized);
	  List<YhUserPersonageHospitalized> selectByPrimaryKey(String userCode);
	  int deleteAll(String userCode);
	  YhUserPersonageHospitalized selectHospitalized(String userCode);

}
