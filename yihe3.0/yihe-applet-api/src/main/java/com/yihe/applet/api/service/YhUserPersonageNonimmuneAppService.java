package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserPersonageNonimmuneApp;

public interface YhUserPersonageNonimmuneAppService {
	int deleteByPrimaryKey(Integer id);

	    int insertSelective(YhUserPersonageNonimmuneApp record);

	    YhUserPersonageNonimmuneApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonageNonimmuneApp record);

	    List<YhUserPersonageNonimmuneApp> shownonimmune(String userCode);
}
