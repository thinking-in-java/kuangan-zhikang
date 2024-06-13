package com.yihe.system.mapper;

import com.yihe.system.domain.HealthPlanVOJO;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserMakeCardExample;
import com.yihe.system.domain.doctorConsoleVOJO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhUserMakeCardMapper {
	int selectOrgSurplusCard(@Param("userCode")String userCode,@Param("orgCode")String orgCode);
	//习惯日记今天新增
	int findHabitDiaryNumByuserCode(@Param("usercode")String usercode);
	
	//习惯评估今天新增
	int findHabitDictNumByuserCode(@Param("usercode")String usercode);
	
	
	//习惯日记按日
	int findHealthyHabitDiaryByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	//习惯日记按月
	int findHealthyHabitDiaryByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//习惯评估次数按月
	int findHealthyHabitByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	//习惯评估次数按日
	int findHealthyHabitByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	
	long countByExample(YhUserMakeCardExample example);

	int deleteByExample(YhUserMakeCardExample example);

	int deleteByPrimaryKey(String enrolCardId);
	int findDoctorAlarmNum(@Param("usercode")String usercode);
	//医生下的用户
	List<HealthPlanVOJO> findUserInfoBydoctorForPlan(@Param("usercode")String usercode,@Param("orgid")String orgid,@Param("nameLike")String nameLike);
	List<HealthPlanVOJO> findUserInfoBydoctorForPlan2(@Param("usercode")String usercode,@Param("orgid")String orgid,@Param("nameLike")String nameLike);
	//健康计划档案记录
	List<HealthPlanVOJO> findUserInfoBydoctorForPlanByjilv(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("usernameLike")String usernameLike,@Param("planName")String planName,@Param("strTime")String strTime,@Param("endTime")String endTime);
	
	//根据医生查询他有多少张注册卡
	int findMakeCardNumByDoctor(@Param("usercode")String usercode);
	//饮食日记数
	int findFoodDiaryNumByUserCode(@Param("usercode")String usercode);
	//今日睡眠日记
	int findSleepUserDiaryToDayByUserCode(@Param("usercode")String usercode);
	//运动日记数
	int findSportDiaryNumByUserCode(@Param("usercode")String usercode);
	
	//根据时间查询之前注册人数 日
	int findRegInfoByTime(@Param("usercode")String usercode,@Param("time")String time);
	//查询压力评估按日
	int findPressureByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询压力评估按月
	int findPressureByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询睡眠评估按日
	int findSleepReportByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询睡眠评估按月
	int findSleepReportByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	//查询运动日记按日
	int findSportDiaryByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询运动日记按月
	int findSportDiaryByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	//查询饮食日记按日
	int findFoodDiaryByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询饮食日记按月
	int findFoodDiaryByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	//查询睡眠日记按日
	int findSleepDiaryByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询睡眠日记按月
	int findSleepDiaryByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	//查询健康计划按日
	int findHealthyPlanByDayAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//查询健康计划按月
	int findHealthyPlanByMonthAndDoctor(@Param("usercode")String usercode,@Param("time")String time);
	
	//根据时间查询之前健康评估人数 日
	int findAssessTotalByTime(@Param("usercode")String usercode,@Param("time")String time);
	
	//根据时间查询之前注册人数 月
	int findRegInfoByTimeMonth(@Param("usercode")String usercode,@Param("time")String time);
	
	//根据时间查询之前健康评估 月
	int findAssessTotalByMonth(@Param("usercode")String usercode,@Param("time")String time);
	
	//今日压力评估新增数
	int findPressureEvaLuateNumByuserCode(@Param("usercode")String usercode);
	
	//今日健康监测新增数
	int findHealtJianCeByUserCode(@Param("usercode")String usercode);
	
	//今日健康计划新增数
	int findUserHealthyPlanByDoctor(@Param("usercode")String usercode);
	
	//查询最近15天注册数
	List<doctorConsoleVOJO> findRegInfoBy15Day(@Param("usercode")String usercode);	
	
	//查询最近12个月注册数
	List<doctorConsoleVOJO> findRegInfoByuserCodeForYearInfo(@Param("usercode")String usercode);	
	
	//今日注册人数
	int findRegInfoToDayByUserCode(@Param("usercode")String usercode);
	
	//该医生下用户今天健康评估人数
	int findAssessTotalByToDayNum(@Param("usercode")String usercode);
	
	//医生已经注册的用户
	List<doctorConsoleVOJO> findUserInfoByDoctor(@Param("usercode")String usercode);
	//未评估人数
	int findNotAssessmentNum(@Param("usercode")String usercode);
	//评估人数
	int findAssessmentNum(@Param("usercode")String usercode);

	//没有已患
	int findNotYiHuanNumInfo(@Param("usercode")String usercode);
	//已患
	int findYiHuanNumInfo(@Param("usercode")String usercode);

	//其他人群
	int findNotGaofenxNumInfo(@Param("usercode")String usercode);
	//高风险人群
	int findGaofenxNumInfo(@Param("usercode")String usercode);

	int insert(YhUserMakeCard record);

	int insertSelective(YhUserMakeCard record);

	List<YhUserMakeCard> selectByExample(YhUserMakeCardExample example);

	List<YhUserMakeCard> selectByPrimaryKeyByuserCode(@Param("usercode") String usercode);

	YhUserMakeCard selectByPrimaryKey(String enrolCardId);

	int updateByExampleSelective(@Param("record") YhUserMakeCard record,
			@Param("example") YhUserMakeCardExample example);

	int updateByExample(@Param("record") YhUserMakeCard record, @Param("example") YhUserMakeCardExample example);

	int updateByPrimaryKeySelective(YhUserMakeCard record);

	int updateByPrimaryKey(YhUserMakeCard record);

	List<YhUserMakeCard> selectByOrgStuck(String orgId);

	public List<Map<String, Object>> selectYhUserMakeCardPageByParam(Map<String, Object> regMap);

	public int selectYhUserMakeCardCount(String userName);

	public int selectYhUserMakeCardEmploy(String userName);

	public int selectYhUserMakeCardSurplus(String userName);

	public List<YhUserMakeCard>  selectByOrgStuckByNameAndEvaAndOrgidAndDoctor(@Param("doctor") Integer doctor,@Param("orgid") String orgid, @Param("name") String name,
			@Param("eva") Integer eva, @Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);
	
	/**
	 * 导入数据
	 * @param list
	 * @return
	 */
	public int importUserData(List<YhUserMakeCard> list);

	int showMakeCardcount();
	
	int showMakeCardcountDispose();
	
	int doctorcard(String usercode);
	
	int doctorcardCount(String usercode);
	
	String selectcard();
	
	String selectcard1(String orgId);

	String selectcard2(@Param("orgId") String orgId, @Param("doctor") String doctor);
	
	public YhUserMakeCard selectOrgIdbyParam(String usercode);
	
	String showByCardId(String doctor);
	
	YhUserMakeCard showByOrgCardId(String orgId);
	
	YhUserMakeCard showByOrgIdMakeCar(String orgId);
	
	String selctByOrgDoctorCardId(String orgId);

	int showCountDoctor(@Param("doctor") String doctor,@Param("orgCode") String orgCode);
	
	YhUserMakeCard selectUnspentRegCard();
}