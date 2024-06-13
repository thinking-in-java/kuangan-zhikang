package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserOtherfunction;
import com.yihe.system.mapper.YHUserOtherfunctionMapper;
import com.yihe.system.service.YHUserOtherfunctionService;

@Service
public class YHUserOtherfunctionServiceImpl implements YHUserOtherfunctionService {
	@Autowired
	YHUserOtherfunctionMapper YHUserOtherfunctionMapper;

	@Override
	public YHUserOtherfunction selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserOtherfunctionMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserOtherfunction(String userCode) {
		// TODO Auto-generated method stub
		return YHUserOtherfunctionMapper.selectYHUserOtherfunction(userCode);
	}

	@Override
	public PageInfo<YHUserOtherfunction> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	    //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserOtherfunction> temperatureList = YHUserOtherfunctionMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserOtherfunction>(temperatureList);
	}

}
