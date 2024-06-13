package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageExistingApp;

public interface YhUserPersonageExistingAppService {
	    int insertSelective(YhUserPersonageExistingApp record);

	    YhUserPersonageExistingApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonageExistingApp record);
	    
	    YhUserPersonageExistingApp showexisting(String userCode);
}
