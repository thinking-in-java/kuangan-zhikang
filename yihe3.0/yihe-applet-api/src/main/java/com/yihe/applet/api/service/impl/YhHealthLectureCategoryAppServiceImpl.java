package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhHealthLectureCategoryApp;
import com.yihe.applet.api.mapper.YhHealthLectureCategoryAppMapper;
import com.yihe.applet.api.service.YhHealthLectureCategoryAppService;

@Service
public class YhHealthLectureCategoryAppServiceImpl implements YhHealthLectureCategoryAppService{
	@Autowired
	YhHealthLectureCategoryAppMapper yhHealthLectureCategoryAppMapper;

	@Override
	public YhHealthLectureCategoryApp selectByPrimaryKey(Integer categoryid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryAppMapper.selectByPrimaryKey(categoryid);
	}

	@Override
	public int findFabulousByInfoId(Integer categoryId) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryAppMapper.findFabulousByInfoId(categoryId);
	}

	@Override
	public int insertFabulous(Integer categoryId, String usercode) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryAppMapper.insertFabulous(categoryId, usercode);
	}

	@Override
	public int deleteFabulous(Integer categoryId, String usercode) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryAppMapper.deleteFabulous(categoryId, usercode);
	}

	@Override
	public int findFabulousExistence(Integer categoryId, String usercode) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryAppMapper.findFabulousExistence(categoryId, usercode);
	}

	@Override
	public List<YhHealthLectureCategoryApp> findInfoAllByParent(YhHealthLectureCategoryApp yhHealthLectureCategoryApp) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryAppMapper.findInfoAllByParent(yhHealthLectureCategoryApp);
	}
}
