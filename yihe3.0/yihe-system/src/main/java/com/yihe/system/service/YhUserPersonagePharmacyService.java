package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserPersonagePharmacy;

public interface YhUserPersonagePharmacyService {
	 int deleteByPrimaryKey(Integer id);
	 
	 int insert(YhUserPersonagePharmacy yhUserPersonagePharmacy);
	 
	 List<YhUserPersonagePharmacy>  selectByPrimaryKey(String userCode);
	 
	 int updateByPrimaryKey(YhUserPersonagePharmacy yhUserPersonagePharmacy);

}
