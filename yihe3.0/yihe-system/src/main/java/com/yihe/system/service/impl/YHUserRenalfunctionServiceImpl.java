package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserRenalfunction;
import com.yihe.system.mapper.YHUserRenalfunctionMapper;
import com.yihe.system.service.YHUserRenalfunctionService;

@Service
public class YHUserRenalfunctionServiceImpl implements YHUserRenalfunctionService {
	@Autowired
	YHUserRenalfunctionMapper YHUserRenalfunctionMapper;

	@Override
	public YHUserRenalfunction selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserRenalfunctionMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserRenalfunction(String userCode) {
		// TODO Auto-generated method stub
		return YHUserRenalfunctionMapper.selectYHUserRenalfunction(userCode);
	}

	@Override
	public PageInfo<YHUserRenalfunction> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	    //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserRenalfunction> renalfunctionList = YHUserRenalfunctionMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserRenalfunction>(renalfunctionList);
	}

	@Override
	public int insertSelective(YHUserRenalfunction record) {
		return YHUserRenalfunctionMapper.insertSelective(record);
	}
}
