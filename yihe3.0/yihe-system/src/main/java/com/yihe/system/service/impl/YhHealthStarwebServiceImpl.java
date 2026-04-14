package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.YhHealthStarweb;
import com.yihe.system.mapper.YhHealthStarwebMapper;
import com.yihe.system.service.YhHealthStarwebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
	public List<YhHealthStarweb> selectAllUserRecord(String userCode, String times) {
		// TODO Auto-generated method stub
		String startTime=null;
		String endTime=null;
		if(StringUtils.isNotEmpty(times)){
			startTime=times.substring(0,10);
			endTime=times.substring(12);
		}
		return yhHealthStarwebMapper.selectAllUserRecord(userCode,startTime,endTime);
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
	public PageInfo<YhHealthStarweb> queryUserByCriteriaWithPage2(Integer usertype, String orgCode, String orgCode1, String name, String priceMin,
                                                                  String priceMax, int page, int limit, String times) {
		 PageHelper.startPage(page,limit);
		 String startTime=null;
		 String endTime=null;
		 if(StringUtils.isNotEmpty(times)){
			 startTime=times.substring(0,10);
			 endTime=times.substring(12);
		 }


	     List<YhHealthStarweb> list = yhHealthStarwebMapper.selectDoctorUserAllIntegral(usertype,orgCode,orgCode1, name, priceMin, priceMax,startTime,endTime);
		 return new PageInfo<YhHealthStarweb>(list);
	}
	@Override
	public String selectRecordtypeAndTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.selectRecordtypeAndTime(map);
	}

	@Override
	public YhHealthStarweb selectRecordtypeAndTimeMinus(Map<String, Object> map) {
		return yhHealthStarwebMapper.selectRecordtypeAndTimeMinus(map);
	}

	@Override
	public String selectRecordtypeAndTime2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.selectRecordtypeAndTime2(map);
	}

	@Override
	public String selectRecordtypeAndTime3(Map<String, Object> map) {
		return yhHealthStarwebMapper.selectRecordtypeAndTime3(map);
	}

	@Override
	public YhHealthStarweb selectNewestStar(String userCode, String eventName) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.selectNewestStar(userCode, eventName);
	}

	@Override
	public int selectUserContinuousRecord(String userCode, String eventName) {
		// TODO Auto-generated method stub
		return yhHealthStarwebMapper.selectUserContinuousRecord(userCode, eventName);
	}
}
