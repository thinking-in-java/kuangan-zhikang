package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserLiverfunction;
import com.yihe.system.mapper.YHUserLiverfunctionMapper;
import com.yihe.system.service.YHUserLiverfunctionService;

@Service
public class YHUserLiverfunctionServiceImpl implements YHUserLiverfunctionService {
	@Autowired
	YHUserLiverfunctionMapper YHUserLiverfunctionMapper;

	@Override
	public YHUserLiverfunction selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserLiverfunctionMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserLiverfunction(String userCode) {
		// TODO Auto-generated method stub
		return YHUserLiverfunctionMapper.selectYHUserLiverfunction(userCode);
	}

	@Override
	public PageInfo<YHUserLiverfunction> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	      //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserLiverfunction> liverfunctionList = YHUserLiverfunctionMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserLiverfunction>(liverfunctionList);
	}

}
