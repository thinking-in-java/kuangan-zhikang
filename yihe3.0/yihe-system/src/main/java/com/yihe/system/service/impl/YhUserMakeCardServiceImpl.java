package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhUserMakeCardMapper;
import com.yihe.system.service.YhUserMakeCardService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * YhUserMakeCardService实现 Created by dyb on 2020/3/19.
 */
@Service
@Transactional

public class YhUserMakeCardServiceImpl implements YhUserMakeCardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserMakeCardServiceImpl.class);

	@Autowired
	YhUserMakeCardMapper yhUserMakeCardMapper;

	@Override
	public List<YhUserMakeCard> selectByPrimaryKeyByuserCode(String usercode) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectByPrimaryKeyByuserCode(usercode);
	}

	@Override
	public List<YhUserMakeCard> selectByOrgStuck(String orgId) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectByOrgStuck(orgId);
	}

	@Override
	public PageInfo<Map<String, Object>> queryYhUserMakeCardByCriteriaWithPage(Map<String, Object> regMap, int page,
			int limit) {
		// 取当前登录用户名
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		regMap.put("userCode", yhUserReginfo.getUserCode());

		// 设置分页参数
		PageHelper.startPage(page, limit);
		List<Map<String, Object>> list = yhUserMakeCardMapper.selectYhUserMakeCardPageByParam(regMap);
		return new PageInfo<Map<String, Object>>(list);

	}

	@Override
	public int selectYhUserMakeCardCount(String userName) {
		return yhUserMakeCardMapper.selectYhUserMakeCardCount(userName);
	}

	@Override
	public int selectYhUserMakeCardEmploy(String userName) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectYhUserMakeCardEmploy(userName);
	}

	@Override
	public int selectYhUserMakeCardSurplus(String userName) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectYhUserMakeCardSurplus(userName);
	}

	@Override
	public long countByExample(YhUserMakeCardExample example) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(YhUserMakeCardExample example) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String enrolCardId) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.deleteByPrimaryKey(enrolCardId);
	}

	@Override
	public int insert(YhUserMakeCard record) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.insert(record);
	}

	@Override
	public int insertSelective(YhUserMakeCard record) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.insertSelective(record);
	}

	@Override
	public List<YhUserMakeCard> selectByExample(YhUserMakeCardExample example) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectByExample(example);
	}

	@Override
	public YhUserMakeCard selectByPrimaryKey(String enrolCardId) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectByPrimaryKey(enrolCardId);
	}

	@Override
	public int updateByExampleSelective(YhUserMakeCard record, YhUserMakeCardExample example) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(YhUserMakeCard record, YhUserMakeCardExample example) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserMakeCard record) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhUserMakeCard record) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> selectYhUserMakeCardPageByParam(Map<String, Object> regMap) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectYhUserMakeCardPageByParam(regMap);
	}

	@Override
	public PageInfo<YhUserMakeCard> selectByOrgStuckByNameAndEvaAndOrgidAndDoctor(Integer doctor, String orgid,
			String name, Integer eva, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhUserMakeCard> list = yhUserMakeCardMapper.selectByOrgStuckByNameAndEvaAndOrgidAndDoctor(doctor, orgid,
				name, eva, pageNum, pageSize);

		return new PageInfo<YhUserMakeCard>(list);

	}
	

	/**
	 * 医生控制台控制层代码
	 */
	public doctorConsoleVOJO findDoctorAllInfo() {
		doctorConsoleVOJO info = new doctorConsoleVOJO();
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		// 根据医生查询他有多少张注册卡
		int findMakeCardNumByDoctor = yhUserMakeCardMapper.findMakeCardNumByDoctor(usercode);

		//告警数
		int findDoctorAlarmNum = yhUserMakeCardMapper.findDoctorAlarmNum(usercode);
		info.setHealthAlarmNum(findDoctorAlarmNum);
		// 医生下已经注册的用户
		List<doctorConsoleVOJO> findUserInfoByDoctor = yhUserMakeCardMapper.findUserInfoByDoctor(usercode);
		int cradSize = findUserInfoByDoctor.size();
		info.setSurplusCardNum(findMakeCardNumByDoctor - cradSize);// 剩余注册卡
		info.setUseCardNum(cradSize);// 已使用注册卡
		int man = 0;// 男数
		int woman = 0;// 女数
		int age25 = 0;
		int age2635 = 0;
		int age3645 = 0;
		int age4655 = 0;
		int age5560 = 0;
		int age60 = 0;
		//未评估人数
		info.setNotassessment(yhUserMakeCardMapper.findNotAssessmentNum(usercode));
		//评估人数
		info.setAssessment(yhUserMakeCardMapper.findAssessmentNum(usercode));
		//没有已患
		info.setBeillNum(yhUserMakeCardMapper.findNotYiHuanNumInfo(usercode));
		//已患人群
		info.setNotBeillNum(yhUserMakeCardMapper.findYiHuanNumInfo(usercode));
		//其他人群
		info.setOtherRisk(yhUserMakeCardMapper.findNotGaofenxNumInfo(usercode));
		//高风险人群
		info.setHighRisk(yhUserMakeCardMapper.findGaofenxNumInfo(usercode));
		for (doctorConsoleVOJO doctorConsoleVOJO : findUserInfoByDoctor) {
			int gender = doctorConsoleVOJO.getGender();
			if (gender == 1) {
				man++;
			} else {
				woman++;
			}
			Date birthDate = doctorConsoleVOJO.getBirthDate();
			if (null != birthDate) {
				int ageByBirth = getAgeByBirth(birthDate);
				if (ageByBirth <= 25) {
					age25++;
				} else if (ageByBirth > 25 && ageByBirth <= 35) {
					age2635++;
				} else if (ageByBirth > 35 && ageByBirth <= 45) {
					age3645++;
				} else if(ageByBirth > 46 && ageByBirth <= 55){
					age4655++;
				}else if(ageByBirth > 55 && ageByBirth <= 60){
					age5560++;
				}else{
					age60++;
				}

			}
		}
		info.setManNum(man);// 男
		info.setWomanNum(woman);// 女
		info.setAgeType1(age25);
		info.setAgeType2(age2635);
		info.setAgeType3(age3645);
		info.setAgeType4(age4655);
		info.setAgeType5(age5560);
		info.setAgeType6(age60);
		// 今日注册人数
		int findRegInfoToDayByUserCode = yhUserMakeCardMapper.findRegInfoToDayByUserCode(usercode);
		info.setRegisterNum(findRegInfoToDayByUserCode);

		// 今日健康评估人数
		int findAssessTotalByToDayNum = yhUserMakeCardMapper.findAssessTotalByToDayNum(usercode);
		info.setHealthAssessmentNum(findAssessTotalByToDayNum);

		// 今日健康监测人数
		int findHealtJianCeByUserCode = yhUserMakeCardMapper.findHealtJianCeByUserCode(usercode);
		info.setHealthMonitoringNum(findHealtJianCeByUserCode);

		// 今日压力评估人数
		int findPressureEvaLuateNumByuserCode = yhUserMakeCardMapper.findPressureEvaLuateNumByuserCode(usercode);
		info.setPressureAssessmentNum(findPressureEvaLuateNumByuserCode);
		//今日习惯日记
		int findHabitDiaryNumByuserCode = yhUserMakeCardMapper.findHabitDiaryNumByuserCode(usercode);
		info.setHabitDiaryNum(findHabitDiaryNumByuserCode);
		//今日习惯评估
		int findHabitDictNumByuserCode = yhUserMakeCardMapper.findHabitDictNumByuserCode(usercode);
		info.setHabitAssessmentNum(findHabitDictNumByuserCode);
		// 今日饮食日记人数
		int findFoodDiaryNumByUserCode = yhUserMakeCardMapper.findFoodDiaryNumByUserCode(usercode);
		info.setFoodDiaryNum(findFoodDiaryNumByUserCode);

		// 今日运动日记人数
		int findSportDiaryNumByUserCode = yhUserMakeCardMapper.findSportDiaryNumByUserCode(usercode);
		info.setSportDiaryNum(findSportDiaryNumByUserCode);
		
		//今日健康计划新增数
		int findUserHealthyPlanByDoctor = yhUserMakeCardMapper.findUserHealthyPlanByDoctor(usercode);
		info.setHealthPlanDiaryNum(findUserHealthyPlanByDoctor);
		 //今日睡眠日记新增数
		int findSleepUserDiaryToDayByUserCode = yhUserMakeCardMapper.findSleepUserDiaryToDayByUserCode(usercode);
		info.setSleepDiaryNum(findSleepUserDiaryToDayByUserCode);
		String[] timelist = new String[15];
		int[] datalist = new int[15];
		for (int i = 14,j=0; i >= 0; i--,j++) {
			Date today = new Date(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String yesterday = simpleDateFormat.format(today);// 获取昨天日期
			int findRegInfoByTime = yhUserMakeCardMapper.findRegInfoByTime(usercode, yesterday);
			timelist[j] = yesterday;
			datalist[j] = findRegInfoByTime;

		}
		info.setRegtimeList(timelist);
		info.setRegdataInfo(datalist);
		return info;
	}

	public static void main(String[] args) {
		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); String
		 * startTime = df.format(new Date().getTime()-15*24*60*60*1000); String
		 * endTime = df.format(new Date()); String[] date = getDate(startTime,
		 * endTime); for (String string : date) { System.out.println(string); }
		 */
		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d"); for(int i
		 * =0;i<15;i++){ String time = df.format(new
		 * Date().getTime()-i*24*60*60*1000); System.out.println(time); }
		 */
	/*	for (int i = 0; i < 15; i++) {
			Date today = new Date(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String yesterday = simpleDateFormat.format(today);// 获取昨天日期
			System.out.println(yesterday);
		}*/
		for (int i = 14; i >= 0; i--) {
			System.out.println(i);
		}
		 
		
	}
	public static String getLast12Months(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -i);
		c.set(Calendar.DATE, 1);
		Date m = c.getTime();
		return sdf.format(m);
	}

	public static String[] getDate(String beginDate, String endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -14);
		Date y = c.getTime();
		if (StringUtils.isEmpty(beginDate)) {
			beginDate = format.format(y);
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = format.format(new Date());
		}
		String[] dateArray = { beginDate, endDate };
		return dateArray;
	}

	/**
	 * 根据出生年月计算年龄
	 * 
	 * @param birthday
	 * @return
	 */
	private int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}

	@Override
	public doctorConsoleVOJO findDoctorAllInfoByTimeTypeAndDataType(String timetype, int datatype) {
		// TODO Auto-generated method stub
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		doctorConsoleVOJO info = new doctorConsoleVOJO();
		// 日
		if (timetype.equals("day")) {
			if (datatype == 1) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
				 
					int findRegInfoByTime = yhUserMakeCardMapper.findRegInfoByTime(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 2) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findAssessTotalByTime(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 3) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findPressureByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 4) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findSleepReportByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 5) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findSportDiaryByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 6) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findFoodDiaryByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 7) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findSleepDiaryByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 8) {
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findHealthyPlanByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 9) {
				//习惯评估次数
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findHealthyHabitByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 10) {
				//习惯日记数
				String[] timelist = new String[30];
				int[] datalist = new int[30];
				// 每日注册用户人数
				for (int i = 29,j=0; i >= 0; i--,j++) {
					 Calendar now = Calendar.getInstance();
				        now.add(Calendar.DAY_OF_MONTH, -i);
				        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
				        System.out.println(yesterday);
					int findRegInfoByTime = yhUserMakeCardMapper.findHealthyHabitDiaryByDayAndDoctor(usercode, yesterday);
					timelist[j] = yesterday;
					datalist[j] = findRegInfoByTime;

				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
		}
		 
		// 月
		if (timetype.equals("month")) {
			if (datatype == 1) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findRegInfoByTimeMonth(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
				 
			}
			if (datatype == 2) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findAssessTotalByMonth(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 3) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findPressureByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 4) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findSleepReportByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 5) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findSportDiaryByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 6) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findFoodDiaryByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 7) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findSleepDiaryByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 8) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findHealthyPlanByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 9) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findHealthyHabitByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
			if (datatype == 10) {
				String[] timelist = new String[12];
				int[] datalist = new int[12];
				for (int i = 10,j=0; i >= -1; i--,j++) {
					System.out.println(getLast12Months(i));
					String time = getLast12Months(i);
					int findRegInfoByTimeMonth = yhUserMakeCardMapper.findHealthyHabitDiaryByMonthAndDoctor(usercode, time);
					timelist[j] = getLast12Months(i+1).substring(0,time.length() - 3);
					datalist[j] = findRegInfoByTimeMonth;
				}
				info.setRegtimeList(timelist);
				info.setRegdataInfo(datalist);
			}
		}
		return info;
	}

 

	@Override
	public PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan(String usercode, String orgid,String nameLike,int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<HealthPlanVOJO> list = yhUserMakeCardMapper.findUserInfoBydoctorForPlan(usercode,orgid,nameLike);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan2(String usercode, String orgid,String nameLike,int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<HealthPlanVOJO> list = yhUserMakeCardMapper.findUserInfoBydoctorForPlan2(usercode,orgid,nameLike);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlanByjilv(String usercode, String nameLike,
			String usernameLike, String planName, String strTime, String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<HealthPlanVOJO> list =yhUserMakeCardMapper.findUserInfoBydoctorForPlanByjilv(usercode, nameLike, usernameLike, planName, strTime, endTime);
		return new PageInfo<>(list);
	}

	@Override
	public YhUserMakeCard selectOrgIdbyParam(String usercode) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectOrgIdbyParam(usercode);
	}

	@Override
	public int selectOrgSurplusCard(String userCode, String orgCode) {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectOrgSurplusCard(userCode, orgCode);
	}

	@Override
	public YhUserMakeCard selectUnspentRegCard() {
		// TODO Auto-generated method stub
		return yhUserMakeCardMapper.selectUnspentRegCard();
	}

}