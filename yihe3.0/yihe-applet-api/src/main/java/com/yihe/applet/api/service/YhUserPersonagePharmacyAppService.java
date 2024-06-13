package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserPersonagePharmacyApp;

public interface YhUserPersonagePharmacyAppService {
	 	int deleteByPrimaryKey(Integer id);

	    int insertSelective(YhUserPersonagePharmacyApp record);

	    YhUserPersonagePharmacyApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonagePharmacyApp record);
	    
	    List<YhUserPersonagePharmacyApp> showpharmacy(String userCode);
}
