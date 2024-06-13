package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserBloodPressure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodsuger;
import com.yihe.system.mapper.YHUserBloodsugerMapper;
import com.yihe.system.service.YHUserBloodsugerService;

@Service
public class YHUserBloodsugerServiceImpl implements YHUserBloodsugerService {
	@Autowired
	YHUserBloodsugerMapper YHUserBloodsugerMapper;

	@Override
	public YHUserBloodsuger selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserBloodsugerMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserBloodsuger(String userCode) {
		// TODO Auto-generated method stub
		return YHUserBloodsugerMapper.selectYHUserBloodsuger(userCode);
	}

	@Override
	public PageInfo<YHUserBloodsuger> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
		   //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserBloodsuger> bloodsugerList = YHUserBloodsugerMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserBloodsuger>(bloodsugerList);
	}

	@Override
	public PageInfo<YHUserBloodsuger> selectBloodsuger(Map<String, Object> map, int page, int limit) {
	    //设置分页参数
        PageHelper.startPage(page,limit);
        List<YHUserBloodsuger> list = YHUserBloodsugerMapper.selectBloodsuger(map);
           return new PageInfo<YHUserBloodsuger>(list);
	}

	@Override
	public int insertSelective(YHUserBloodsuger record) {
		return YHUserBloodsugerMapper.insertSelective(record);
	}
}
