package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHealthStarweb;

import java.util.List;
import java.util.Map;

public interface YhHealthStarwebService {
	int insertSelective(YhHealthStarweb record);
	  int  deleteByPrimaryKey(Integer healthstarId);
	    YhHealthStarweb selectByPrimaryKey(Integer healthstarId);
	    List<YhHealthStarweb> selectUserAllIntegral(String userCode);
	    int updateByPrimaryKeySelective(YhHealthStarweb record);
	    List<YhHealthStarweb> selectAllUserRecord (String userCode,String times);
	    public PageInfo<YhHealthStarweb> selectAllUserRecordPage (String userCode,String qtstrTime,String qtendTime,String validity,int page,int limit);
	    public PageInfo<YhHealthStarweb>  queryUserByCriteriaWithPage(String orgCode,String name,String priceMin,String priceMax,int page,int limit);
	    public PageInfo<YhHealthStarweb>  queryUserByCriteriaWithPage2(Integer usertype,String orgCode,String orgCode1,String name,String priceMin,String priceMax,int page,int limit,String times);

	String selectRecordtypeAndTime(Map<String, Object> map);

	YhHealthStarweb selectRecordtypeAndTimeMinus(Map<String, Object> map);

	String selectRecordtypeAndTime2(Map<String, Object> map);

	String selectRecordtypeAndTime3(Map<String, Object> map);

	YhHealthStarweb selectNewestStar(String userCode, String eventName);

	int selectUserContinuousRecord(String userCode, String eventName);
}
