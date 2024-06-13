package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.HealthPlanVOJO;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserMakeCardExample;
import com.yihe.system.domain.doctorConsoleVOJO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * YhUserMakeCardService接口 Created by dyb on 2020/3/19.
 */
public interface YhUserMakeCardService {
	List<YhUserMakeCard> selectByPrimaryKeyByuserCode(String usercode);
	int selectOrgSurplusCard(String userCode,String orgCode);
	List<YhUserMakeCard> selectByOrgStuck(String orgId);

	public PageInfo<Map<String, Object>> queryYhUserMakeCardByCriteriaWithPage(Map<String, Object> regMap,int page,int limit);

	public int selectYhUserMakeCardCount(String userName);

	public int selectYhUserMakeCardEmploy(String userName);

	public int selectYhUserMakeCardSurplus(String userName);

	long countByExample(YhUserMakeCardExample example);

	int deleteByExample(YhUserMakeCardExample example);

	int deleteByPrimaryKey(String enrolCardId);

	int insert(YhUserMakeCard record);
	YhUserMakeCard selectUnspentRegCard();
	int insertSelective(YhUserMakeCard record);

	List<YhUserMakeCard> selectByExample(YhUserMakeCardExample example);

	YhUserMakeCard selectByPrimaryKey(String enrolCardId);

	int updateByExampleSelective(YhUserMakeCard record, YhUserMakeCardExample example);

	int updateByExample(YhUserMakeCard record, YhUserMakeCardExample example);

	int updateByPrimaryKeySelective(YhUserMakeCard record);

	int updateByPrimaryKey(YhUserMakeCard record);

	public List<Map<String, Object>> selectYhUserMakeCardPageByParam(Map<String, Object> regMap);
	
	public PageInfo<YhUserMakeCard>  selectByOrgStuckByNameAndEvaAndOrgidAndDoctor(Integer doctor,String orgid,  String name,
				Integer eva, Integer pageNum, Integer pageSize);
	
	public doctorConsoleVOJO findDoctorAllInfo();
	
	public doctorConsoleVOJO findDoctorAllInfoByTimeTypeAndDataType(String timetype,int datatype);
	
	//医生下所有用户
	PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan(String usercode,String orgid,String nameLike,int pageNum,int pageSize);

	//医生下所有用户
	PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan2(String usercode,String orgid,String nameLike,int pageNum,int pageSize);
	//健康计划档案记录
	PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlanByjilv(String usercode,String nameLike,String usernameLike,String planName,String strTime,String endTime,int pageNum,int pageSize);
	
	public YhUserMakeCard selectOrgIdbyParam(String usercode);
}