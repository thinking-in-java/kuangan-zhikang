package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHealthStarweb;
import com.yihe.system.mapper.YhHealthStarwebMapper;
import com.yihe.system.service.YhHealthStarwebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhHealthStarwebServiceImpl implements YhHealthStarwebService{
	@Autowired 
	YhHealthStarwebMapper yhHealthStarwebMapper;
  


	@Override
	public int deleteByPrimaryKey(Integer healthstarId) {
		// TODO Auto-generated metho d stub
		return yhHealthStarwebMapper.deleteByPrimaryKey(healthstarId);
	}

	@Override
	public YhHealthStarweb selectByPrimaryKey(Integer healthstarId) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.selectByPrimaryKey(healthstarId);
	}



	@Override
	public int updateByPrimaryKeySelective(YhHealthStarweb record) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public PageInfo<YhHealthStarweb> queryUserByCriteriaWithPage(String orgCode, String name,String priceMin,String priceMax,int page, int limit) {
		//设置分页参数
        PageHelper.startPage(page,limit);
        List<YhHealthStarweb> list = yhHealthStarwebMapper.selectUserAllIntegral(orgCode,name,priceMin,priceMax);
		return new PageInfo<YhHealthStarweb>(list);
	}

	@Override
	public List<YhHealthStarweb> selectAllUserRecord(String userCode) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.selectAllUserRecord(userCode);
	}

	@Override
	public List<YhHealthStarweb> selectUserAllIntegral(String userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<YhHealthStarweb> selectAllUserRecordPage(String userCode,String qtstrTime,String qtendTime,String validity, int page, int limit) {
		//设置分页参数
        PageHelper.startPage(page,limit);
        List<YhHealthStarweb> list = yhHealthStarwebMapper.selectAllUserRecordPage(userCode, qtstrTime, qtendTime, validity);
		return new PageInfo<YhHealthStarweb>(list);
	}

	@Override
	public int insertSelective(YhHealthStarweb record) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.insertSelective(record);
	}

	@Override
	public PageInfo<YhHealthStarweb> queryUserByCriteriaWithPage2(Integer usertype,String orgCode,String orgCode1, String name, String priceMin,
			String priceMax, int page, int limit) {
		 PageHelper.startPage(page,limit);
	        List<YhHealthStarweb> list = yhHealthStarwebMapper.selectDoctorUserAllIntegral(usertype,orgCode,orgCode1, name, priceMin, priceMax);
			return new PageInfo<YhHealthStarweb>(list);
	}
}
