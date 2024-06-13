package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserUba;
import com.yihe.system.mapper.YHUserUbaMapper;
import com.yihe.system.service.YHUserUbaService;

@Service
public class YHUserUbaServiceImpl implements YHUserUbaService {
	@Autowired
	YHUserUbaMapper YHUserUbaMapper;

	@Override
	public YHUserUba selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserUbaMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserUba(String userCode) {
		// TODO Auto-generated method stub
		return YHUserUbaMapper.selectYHUserUba(userCode);
	}

	@Override
	public PageInfo<YHUserUba> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
		  PageHelper.startPage(page,limit);
          List<YHUserUba> ubaList = YHUserUbaMapper.selectPageByParam(userCode);
          return new PageInfo<YHUserUba>(ubaList);
	}

}
