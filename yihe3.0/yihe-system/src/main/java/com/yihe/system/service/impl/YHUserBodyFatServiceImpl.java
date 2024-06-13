package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBodyFat;
import com.yihe.system.mapper.YHUserBodyFatMapper;
import com.yihe.system.service.YHUserBodyFatService;

@Service
public class YHUserBodyFatServiceImpl implements YHUserBodyFatService {
	@Autowired
	YHUserBodyFatMapper YHUserBodyFatMapper;

	@Override
	public YHUserBodyFat selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserBodyFatMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserBodyFat(String userCode) {
		// TODO Auto-generated method stub
		return YHUserBodyFatMapper.selectYHUserBodyFat(userCode);
	}

	@Override
	public PageInfo<YHUserBodyFat> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	    //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserBodyFat> bodyFatList = YHUserBodyFatMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserBodyFat>(bodyFatList);
	}

	@Override
	public PageInfo<YHUserBodyFat> selectBodyFat(String userCode, int page, int limit) {
		   //设置分页参数
        PageHelper.startPage(page,limit);
        List<YHUserBodyFat> list = YHUserBodyFatMapper.selectBodyFat(userCode);
           return new PageInfo<YHUserBodyFat>(list);
	}

	@Override
	public PageInfo<YHUserBodyFat> queryUserByCriteriaWithPage1(String userCode, int page, int limit) {
		 //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserBodyFat> bodyFatList = YHUserBodyFatMapper.selectPageByParam1(userCode);
            return new PageInfo<YHUserBodyFat>(bodyFatList);
	}


	@Override
	public int insertSelective(YHUserBodyFat record) {
		return YHUserBodyFatMapper.insertSelective(record);
	}
}
