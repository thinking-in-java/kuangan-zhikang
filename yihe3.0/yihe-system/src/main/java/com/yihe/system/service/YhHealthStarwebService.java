package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHealthStarweb;

import java.util.List;
 
public interface YhHealthStarwebService {
	int insertSelective(YhHealthStarweb record);
	  int  deleteByPrimaryKey(Integer healthstarId);
	    YhHealthStarweb selectByPrimaryKey(Integer healthstarId);
	    List<YhHealthStarweb> selectUserAllIntegral(String userCode);
	    int updateByPrimaryKeySelective(YhHealthStarweb record);
	    List<YhHealthStarweb> selectAllUserRecord (String userCode);
	    public PageInfo<YhHealthStarweb> selectAllUserRecordPage (String userCode,String qtstrTime,String qtendTime,String validity,int page,int limit);
	    public PageInfo<YhHealthStarweb>  queryUserByCriteriaWithPage(String orgCode,String name,String priceMin,String priceMax,int page,int limit);
	    public PageInfo<YhHealthStarweb>  queryUserByCriteriaWithPage2(Integer usertype,String orgCode,String orgCode1,String name,String priceMin,String priceMax,int page,int limit);
}
