package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhWxSetting;

/**
* YhWxSettingService接口
* Created by dong on 2020/2/26.
*/
public interface YhWxSettingService {
	  
	  YhWxSetting selectByPrimaryKey(Integer id);
	  
}