package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserCommodityDataApp;
import com.yihe.applet.api.mapper.YhUserCommodityDataAppMapper;
import com.yihe.applet.api.service.YhUserCommodityDataAppService;

@Service
public class YhUserCommodityDataAppServiceImpl implements YhUserCommodityDataAppService{
	@Autowired
	YhUserCommodityDataAppMapper yhUserCommodityDataAppMapper;
	@Override
	public List<YhUserCommodityDataApp> selectCommodityDataByParam(Integer groupingId,Integer variable,String userCode,Integer page,Integer pageSize) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataAppMapper.selectCommodityDataByParam(groupingId,variable,userCode,page,pageSize);
	}
	@Override
	public YhUserCommodityDataApp selectByPrimaryKey(Integer merchandiseId) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataAppMapper.selectByPrimaryKey(merchandiseId);
	}
	@Override
	public int updateByPrimaryKeySelective(YhUserCommodityDataApp record) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataAppMapper.updateByPrimaryKeySelective(record);
	}

}
