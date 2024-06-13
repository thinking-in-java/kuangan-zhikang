package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageVisceraApp;

public interface YhUserPersonageVisceraAppService {
	
	    int insertSelective(YhUserPersonageVisceraApp record);

	    YhUserPersonageVisceraApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonageVisceraApp record);

	    YhUserPersonageVisceraApp showviscera(String userCode);
}
