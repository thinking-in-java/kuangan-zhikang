package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.NewCommodityData;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.NewCommodityDataMapper;
import com.yihe.system.service.YhUserSettingService;
import org.apache.poi.util.PackageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserCommodityData;
import com.yihe.system.mapper.YhUserCommodityDataMapper;
import com.yihe.system.service.YhUserCommodityDataService;

import javax.annotation.Resource;

@Service
public class YhUserCommodityDataServiceImpl implements YhUserCommodityDataService{
	@Autowired
	YhUserCommodityDataMapper yhUserCommodityDataMapper;
	@Resource
	NewCommodityDataMapper newCommodityDataMapper;

	@Resource
	YhUserSettingService yhusersettingservice;
	@Override
	public int insertSelective(YhUserCommodityData record) {
		// TODO Auto-generated method stub
	  int count=yhUserCommodityDataMapper.insertSelective(record);
		int i=0;
		for (String  item: record.getOrgCodes()) {
			NewCommodityData newCommodityData=new NewCommodityData();
			newCommodityData.setOrgCode(item);
			newCommodityData.setInventory(record.getIntegrals().get(i));
			newCommodityData.setMerchandiseId(record.getMerchandiseId());
			newCommodityDataMapper.insert(newCommodityData);
			i++;
		}
		return count;
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserCommodityData record) {
		// TODO Auto-generated method stub
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		String username = yh.getUserName();
		String orgcode = yhusersettingservice.selectbyusername(username);
		newCommodityDataMapper.deletByIdAndCode(orgcode,record.getMerchandiseId());
		int i=0;
		for (String  item: record.getOrgCodes()) {
			NewCommodityData newCommodityData=new NewCommodityData();
			newCommodityData.setOrgCode(item);
			newCommodityData.setInventory(record.getIntegrals().get(i));
			newCommodityData.setMerchandiseId(record.getMerchandiseId());
			newCommodityDataMapper.insert(newCommodityData);
			i++;
		}

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
