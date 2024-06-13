/**
 * 
 */
package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhSportFrequencyWeb;
import com.yihe.system.mapper.YhSportFrequencyWebMapper;
import com.yihe.system.service.YhSportFrequencyWebService;

/**
 * @author dong
 *
 */
@Service
public class YhSportFrequencyWebServiceImpl implements YhSportFrequencyWebService{
	@Autowired
	private YhSportFrequencyWebMapper yhSportFrequencyWebMapper;

	@Override
	public YhSportFrequencyWeb showUserFrequency(String userCode) {
		// TODO Auto-generated method stub
		return yhSportFrequencyWebMapper.showUserFrequency(userCode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhSportFrequencyWeb record) {
		// TODO Auto-generated method stub
		return yhSportFrequencyWebMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(YhSportFrequencyWeb record) {
		// TODO Auto-generated method stub
		return yhSportFrequencyWebMapper.insertSelective(record);
	}
	
	
	
}
