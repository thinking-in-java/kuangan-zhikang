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
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.mapper.YhFoodCookBookMapper;
import com.yihe.system.mapper.YhFoodSrecipeMapper;
import com.yihe.system.service.YhFoodSrecipeService;

/**
 * 食物套餐实现
 * @author dong
 *
 */
@Service
@Transactional
public class YhFoodSrecipeServiceImpl implements YhFoodSrecipeService{

	 @Autowired
	 YhFoodSrecipeMapper yhFoodSrecipeMapper;
	 
	@Override
	public List<YhFoodSrecipe> selectSrecipe(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.selectSrecipe(map);
	}

	@Override
	public int selectSrecipeConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.selectSrecipeConut(map);
	}

	@Override
	public YhFoodSrecipe findYhFoodSrecipeByDtypeAndSexAndNumAndDis(Integer type, Integer sex, Integer number,
			String diseaseid) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.findYhFoodSrecipeByDtypeAndSexAndNumAndDis(type, sex, number, diseaseid);
	}

	@Override
	public YhFoodSrecipe selectByPrimaryKey(Integer fsId) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.selectByPrimaryKey(fsId);
	}

	@Override
	public YhFoodSrecipe findFoodSrecipeByFsId(Integer fsId) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.findFoodSrecipeByFsId(fsId);
	}

	@Override
	public YhFoodSrecipe findYhFoodSrecipeByDtypeAndSexAndNumAndDisNew(Integer type, Integer sex, Integer number,
			String diseaseid, int isUse) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.findYhFoodSrecipeByDtypeAndSexAndNumAndDisNew(type, sex, number, diseaseid, isUse);
	}

	@Override
	public List<YhFoodSrecipe> findSrecipeAllInfo() {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.findSrecipeAllInfo();
	}

	@Override
	public int insertAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.insertAll(map);
	}

	@Override
	public PageInfo<YhFoodSrecipe> findFoodPackageManagement(Integer oneself, String usercode, Integer ftype,
			Integer elevel, Integer diseaseid, String fsDescribe, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhFoodSrecipe> list = yhFoodSrecipeMapper.findFoodPackageManagement(oneself, usercode, ftype, elevel, diseaseid, fsDescribe);
		return new PageInfo<>(list);
	}

	@Override
	public int updateByPrimaryKeySelective(YhFoodSrecipe record) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeMapper.updateByPrimaryKeySelective(record);
	}

}
