package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserOrderWeb;
import com.yihe.system.mapper.YhUserOrderWebMapper;
import com.yihe.system.service.YhUserOrderWebService;
@Service
public class YhUserOrderWebServiceImpl implements YhUserOrderWebService{
	@Autowired
	YhUserOrderWebMapper yhUserOrderWebMapper;
	@Override
	public PageInfo<YhUserOrderWeb> queryUserByCriteriaWithPage(String orgCode,Integer merchandiseType, Integer groupingId,
			Integer orderStatus, String qtstrTime, String qtendTime, String merchandiseName, String name,int page, int limit) {
		PageHelper.startPage(page, limit);
		List<YhUserOrderWeb> list = yhUserOrderWebMapper.selectAllYhUserOrderWebPage(orgCode,merchandiseType, groupingId, orderStatus, qtstrTime, 
				qtendTime, merchandiseName, name);
		return new PageInfo<YhUserOrderWeb>(list);
	}
	@Override
	public int deleteByPrimaryKey(Integer orderId) {
		// TODO Auto-generated method stub
		return yhUserOrderWebMapper.deleteByPrimaryKey(orderId);
	}
	@Override
	public int updateByPrimaryKeySelective(YhUserOrderWeb record) {
		// TODO Auto-generated method stub
		return yhUserOrderWebMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<YhUserOrderWeb> selectAllYhUserOrderExport() {
		// TODO Auto-generated method stub
		return yhUserOrderWebMapper.selectAllYhUserOrderExport();
	}
	@Override
	public PageInfo<YhUserOrderWeb> queryUserByCriteriaWithPage2(String orgCode, Integer merchandiseType,
			Integer groupingId, Integer orderStatus, String qtstrTime, String qtendTime, String merchandiseName,
			String name, int page, int limit) {
		PageHelper.startPage(page, limit);
		List<YhUserOrderWeb> list = yhUserOrderWebMapper.selectAllYhUserOrderWebPage2(orgCode,merchandiseType, groupingId, orderStatus, qtstrTime, 
				qtendTime, merchandiseName, name);
		return new PageInfo<YhUserOrderWeb>(list);
		
	}

}
