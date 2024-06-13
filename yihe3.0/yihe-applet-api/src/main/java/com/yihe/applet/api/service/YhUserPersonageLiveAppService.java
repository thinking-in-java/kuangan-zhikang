package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageLiveApp;

public interface YhUserPersonageLiveAppService {

	    int insertSelective(YhUserPersonageLiveApp record);

	    YhUserPersonageLiveApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonageLiveApp record);
	    YhUserPersonageLiveApp showlive(String usercode);
}
