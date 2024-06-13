package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodfat;
import com.yihe.system.mapper.YHUserBloodfatMapper;
import com.yihe.system.service.YHUserBloodfatService;

@Service
public class YHUserBloodfatServiceImpl implements YHUserBloodfatService {
	@Autowired
	YHUserBloodfatMapper YHUserBloodfatMapper;

	@Override
	public YHUserBloodfat selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserBloodfatMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserBloodfat(String userCode) {
		// TODO Auto-generated method stub
		return YHUserBloodfatMapper.selectYHUserBloodfat(userCode);
	}

	@Override
	public PageInfo<YHUserBloodfat> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
	     //设置分页参数
        PageHelper.startPage(page,limit);
        List<YHUserBloodfat> bloodfatList = YHUserBloodfatMapper.selectPageByParam(userCode);
        return new PageInfo<YHUserBloodfat>(bloodfatList);
	}

	@Override
	public int insertSelective(YHUserBloodfat record) {
		return YHUserBloodfatMapper.insertSelective(record);
	}
}
