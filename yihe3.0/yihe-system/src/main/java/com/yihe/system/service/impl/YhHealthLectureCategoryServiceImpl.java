package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhHealthLectureCategory;
import com.yihe.system.mapper.YhHealthLectureCategoryMapper;
import com.yihe.system.service.YhHealthLectureCategoryService;

/**
* YhHealthLectureCategoryService实现
* Created by dong on 2020/3/26.
*/
@Service
public class YhHealthLectureCategoryServiceImpl  implements YhHealthLectureCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureCategoryServiceImpl.class);

    @Autowired
    YhHealthLectureCategoryMapper yhHealthLectureCategoryMapper;

	@Override
	public List<YhHealthLectureCategory> showcategory() {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.showcategory();
	}

	@Override
	public int deleteByparentId(String parentId) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.deleteByparentId(parentId);
	}

	@Override
	public List<YhHealthLectureCategory> showcategoryparentId(String parentId) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.showcategoryparentId(parentId);
	}

	@Override
	public int updateByPrimaryKey(YhHealthLectureCategory ory) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.updateByPrimaryKey(ory);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(YhHealthLectureCategory cate) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.insertSelective(cate);
	}

	@Override
	public List<YhHealthLectureCategory> showpaid() {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.showpaid();
	}

	@Override
	public int updateByPrimaryKeySelective(YhHealthLectureCategory record) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhHealthLectureCategory> showcategoryyiji() {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.showcategoryyiji();
	}

	@Override
	public YhHealthLectureCategory selectByPrimaryKey(Integer categoryid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCategoryMapper.selectByPrimaryKey(categoryid);
	}

}