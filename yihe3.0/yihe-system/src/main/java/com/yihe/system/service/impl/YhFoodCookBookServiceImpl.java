/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.mapper.YhEventEnrollMapper;
import com.yihe.system.mapper.YhFoodCookBookMapper;
import com.yihe.system.service.YhFoodCookBookService;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class YhFoodCookBookServiceImpl implements YhFoodCookBookService{

	 @Autowired
	 YhFoodCookBookMapper yhFoodCookBookMapper;
	 
	@Override
	public List<YhFoodCookBook> selectCookBook(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.selectCookBook(map);
	}

	@Override
	public int selectCookBookConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.selectCookBookConut(map);
	}

	@Override
	public int insertSelective(YhFoodCookBook record) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer fCId) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.deleteByPrimaryKey(fCId);
	}

	@Override
	public YhFoodCookBook selectByPrimaryKey(Integer fCId) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.selectByPrimaryKey(fCId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhFoodCookBook record) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int selctFcNameRepetition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.selctFcNameRepetition(map);
	}

	@Override
	public YhFoodCookBook selectByPrimaryKeyOld(Integer fCId) {
		// TODO Auto-generated method stub
		return yhFoodCookBookMapper.selectByPrimaryKeyOld(fCId);
	}

	@Override
	public PageInfo<YhFoodCookBook> findCookBookInfoByType(Integer ftype,String val,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhFoodCookBook> list = yhFoodCookBookMapper.findCookBookInfoByType(ftype,val);
		return new PageInfo<YhFoodCookBook>(list);
	}

	@Override
	public List<String> findDoctorInfo(String org) {
		return yhFoodCookBookMapper.findDoctorInfo(org);
	}

	@Override
	public List<String> findjigouInfo(String org) {
		return yhFoodCookBookMapper.findjigouInfo(org);
	}

	@Override
	public PageInfo<YhFoodCookBook> findFoodCookBookByNo10(String val,Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhFoodCookBook> list = yhFoodCookBookMapper.findFoodCookBookByNo10(val);
		return new PageInfo<YhFoodCookBook>(list);
	}

	 

}
