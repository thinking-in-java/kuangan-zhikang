package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBmi;
import com.yihe.system.mapper.YHUserBmiMapper;
import com.yihe.system.service.YHUserBmiService;

@Service
public class YHUserBmiServiceImpl implements YHUserBmiService {
	@Autowired
	YHUserBmiMapper yHUserBmiMapper;

	@Override
	public int saveBmi(YHUserBmi bmi) {
		return yHUserBmiMapper.insertSelective(bmi);
	}

	@Override
	public YHUserBmi selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return yHUserBmiMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserBmi(String userCode) {
		// TODO Auto-generated method stub
		return yHUserBmiMapper.selectYHUserBmi(userCode);
	}

	@Override
	public PageInfo<YHUserBmi> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	       //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserBmi> bimList = yHUserBmiMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserBmi>(bimList);
	}

	@Override
	public PageInfo<YHUserBmi> selectBmiPage(String userCode, int page, int limit) {
		  // TODO Auto-generated method stub
        //设置分页参数
	     PageHelper.startPage(page,limit);
	     List<YHUserBmi> list = yHUserBmiMapper.selectBmiPage(userCode);
        return new PageInfo<YHUserBmi>(list);
	}

	 

}
