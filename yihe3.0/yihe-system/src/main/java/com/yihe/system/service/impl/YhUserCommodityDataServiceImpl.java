package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.util.PackageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserCommodityData;
import com.yihe.system.mapper.YhUserCommodityDataMapper;
import com.yihe.system.service.YhUserCommodityDataService;
@Service
public class YhUserCommodityDataServiceImpl implements YhUserCommodityDataService{
	@Autowired
	YhUserCommodityDataMapper yhUserCommodityDataMapper;
	@Override
	public int insertSelective(YhUserCommodityData record) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserCommodityData record) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserCommodityData selectByPrimaryKey(Integer merchandiseId) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.selectByPrimaryKey(merchandiseId);
	}

	@Override
	public int deleteByPrimaryKey(Integer merchandiseId) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.deleteByPrimaryKey(merchandiseId);
	}

	@Override
	public PageInfo<YhUserCommodityData> queryUserByCriteriaWithPage(Integer merchandiseType, Integer groupingId, Integer putaway,
			Integer conversion, String merchandiseName, String orgCode,int page, int limit) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, limit);
		List<YhUserCommodityData> list = yhUserCommodityDataMapper.selectAllCommodityData(merchandiseType, groupingId, putaway, conversion, merchandiseName,orgCode);
		return new PageInfo<YhUserCommodityData>(list);
	}

	@Override
	public int batchUpdateYhUserCommodityData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.batchUpdateYhUserCommodityData(map);
	}

	@Override
	public int batchDeleteYhUserCommodityData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.batchDeleteYhUserCommodityData(map);
	}

	@Override
	public List<YhUserCommodityData> selectAll() {
		// TODO Auto-generated method stub
		return yhUserCommodityDataMapper.selectAll();
	}

}
