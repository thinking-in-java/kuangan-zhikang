package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhDicIntroduction;

public interface YhUserPageSettingService{
	List<YhDicIntroduction> selectallduction();
	int updateyhdicintroduction(YhDicIntroduction yhdicintroduction);
	String getimg(Integer id);
	String getOrgNameInfo(String orgName);
}
