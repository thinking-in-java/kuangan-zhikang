package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserEcg;
import com.yihe.system.mapper.YHUserEcgMapper;
import com.yihe.system.service.YHUserEcgService;

@Service
public class YHUserEcgServiceImpl implements YHUserEcgService {
	@Autowired
	YHUserEcgMapper YHUserEcgMapper;

	@Override
	public YHUserEcg selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserEcgMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserEcg(String userCode) {
		// TODO Auto-generated method stub
		return YHUserEcgMapper.selectYHUserEcg(userCode);
	}

	@Override
	public PageInfo<YHUserEcg> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	     //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserEcg> ecgList = YHUserEcgMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserEcg>(ecgList);
	}



}
