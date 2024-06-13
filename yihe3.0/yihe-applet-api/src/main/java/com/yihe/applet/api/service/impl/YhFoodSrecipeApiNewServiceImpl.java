package com.yihe.applet.api.service.impl;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import com.ibm.icu.math.BigDecimal;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.YhFoodCookedMapper;
import com.yihe.applet.api.mapper.YhUserReginfoAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.mapper.YhFoodSrecipeApiNewMapper;
import com.yihe.applet.api.mapper.YhFoodSrecipeRecordAppMapper;
import com.yihe.applet.api.service.YhFoodSrecipeApiNewService;
import com.yihe.applet.api.util.common.util.DateUtil;
import com.yihe.system.domain.YhFoodUserDiary;
import com.yihe.system.domain.YhFoodUserScrecipe;
import com.yihe.system.domain.YhUserFoodGuide;
import com.yihe.system.domain.YhUserFoodWeeklyWeb;
import com.yihe.system.domain.YhUserNotifyVo;
import com.yihe.system.mapper.YhFoodSrecipeRecordMapper;
import com.yihe.system.mapper.YhFoodUserDiaryMapper;
import com.yihe.system.mapper.YhFoodUserScrecipeMapper;
import com.yihe.system.mapper.YhUserFoodGuideMapper;
import com.yihe.system.mapper.YhUserFoodWeeklyWebMapper;
import com.yihe.system.mapper.YhUserNotifyMapper;
import com.yihe.system.service.impl.YhFoodUserDiaryServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

@Service
public class YhFoodSrecipeApiNewServiceImpl implements YhFoodSrecipeApiNewService {
	@Autowired
	YhFoodSrecipeApiNewMapper YhFoodSrecipeApiNewMapper;
	@Autowired
	YhFoodSrecipeRecordAppMapper YhFoodSrecipeRecordAppMapper;
	@Autowired
	private YhFoodSrecipeRecordMapper YhFoodSrecipeRecordMapper;
	@Autowired
	private YhFoodUserDiaryMapper yhFoodUserDiaryMapper;
	@Autowired
	YhFoodUserDiaryServiceImpl YhFoodUserDiaryServiceImpl;

	@Autowired
	YhFoodSrecipeRecordAppServiceImpl YhFoodSrecipeRecordAppServiceImpl;
	@Autowired
	private YhFoodCookbook2ServiceImpl YhFoodCookbook2ServiceImpl;

	@Autowired
	private YhUserFoodWeeklyWebMapper YhUserFoodWeeklyWebMapper;

	@Autowired
	YhUserFoodGuideMapper YhUserFoodGuideMapper;

	@Autowired
	YhUserNotifyMapper YhUserNotifyMapper;

	public static Date parseTimeString2Date(String timeString) {
		if ((timeString == null) || (timeString.equals(""))) {
			return null;
		}
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = new Date(dateFormat.parse(timeString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String convertDate2String(Date date, String pattern) {
		if (date == null)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public static int getYear(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd");
		return Integer.parseInt(timeStr.substring(0, 4));
	}

	public static int getMonth(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd");
		return Integer.parseInt(timeStr.substring(5, 7));
	}

	public static int getDay(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd");
		return Integer.parseInt(timeStr.substring(8, 10));
	}

	public static void main(String[] args) throws ParseException {
		String str="91201";
		String[] s = str.split("_");
		System.out.println(s);
		System.out.println(s.length);

	}

	public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.setFirstDayOfWeek(firstDayOfWeek);// 设置一星期的第一天是哪一天
		cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);// 指示一个星期中的某天
		cal.set(Calendar.HOUR_OF_DAY, 0);// 指示一天中的小时。HOUR_OF_DAY 用于 24
											// 小时制时钟。例如，在 10:04:15.250 PM
											// 这一时刻，HOUR_OF_DAY 为 22。
		cal.set(Calendar.MINUTE, 0);// 指示一小时中的分钟。例如，在 10:04:15.250 PM
									// 这一时刻，MINUTE 为 4。
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取上周天时间
	 */
	public static Date lastMonday() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -1);
		}
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int offset = 1 - dayOfWeek;
		return getFirstDayOfWeek(calendar.getTime(), 1);
	}

	private static String convertWeekByDate(String time) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 注意月份是MM
		Date date = simpleDateFormat.parse(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeBegin;

	}

	private static String convertWeekByDate2(String time) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 注意月份是MM
		Date date = simpleDateFormat.parse(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeEnd;

	}

	@Autowired
	private YhFoodUserScrecipeMapper yhFoodUserScrecipeMapper;

	public YhFoodUserScrecipe findFoodTuiJian(String mealId, int dayNum) {
		YhFoodSrecipeApiNew selectByPrimaryKey = YhFoodSrecipeApiNewMapper.selectByPrimaryKey(Long.parseLong(mealId));
		String getfElements = selectByPrimaryKey.getfElements();
		String[] split = getfElements.split(";");
		if (dayNum > split.length) {
			dayNum = 1;
		}
		String string = split[dayNum - 1];
		YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
				.findFoodUserScrecipeBySrecipeId(Integer.valueOf(string));
		return findFoodUserScrecipeBySrecipeId;
	}

	public YhFoodSrecipeApiNew selectByPrimaryKey(Long mealId) {
		return YhFoodSrecipeApiNewMapper.selectByPrimaryKey(mealId);
	}

	public YhFoodSrecipeApiNew findNewFoodSrecipeForSex(String usercode, int sex) {
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
				.findFoodSrecipeRecordByUsercodeForNow(usercode);
		if (null != findFoodSrecipeRecordByUsercodeForNow) {
			return null;
		}
		if (1 == sex) {
			List<YhFoodSrecipeApiNew> findFoodSrecipeInfoByMan = YhFoodSrecipeApiNewMapper.findFoodSrecipeInfoByMan();
			Random random = new Random();
			int n = random.nextInt(findFoodSrecipeInfoByMan.size());
			YhFoodSrecipeApiNew yhFoodSrecipeApiNew = findFoodSrecipeInfoByMan.get(n);
			YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp = new YhFoodSrecipeRecordApp();
			yhFoodSrecipeRecordApp.setRecordData(new Date());
			yhFoodSrecipeRecordApp.setRecordName("首次默认饮食推荐");
			yhFoodSrecipeRecordApp.setRecordKay(Long.valueOf(yhFoodSrecipeApiNew.geteLevel()));
			yhFoodSrecipeRecordApp.setRecordMode("系统自动");
			yhFoodSrecipeRecordApp.setUserCode(usercode);
			yhFoodSrecipeRecordApp.setMealId(String.valueOf(yhFoodSrecipeApiNew.getId()));
			yhFoodSrecipeRecordApp.setIllnessId(String.valueOf(yhFoodSrecipeApiNew.getDiseaseid()));

			YhFoodSrecipeRecordAppServiceImpl.insertYhFoodSrecipeRecord(yhFoodSrecipeRecordApp);
			return yhFoodSrecipeApiNew;

		} else {
			List<YhFoodSrecipeApiNew> findFoodSrecipeInfoByMan = YhFoodSrecipeApiNewMapper.findFoodSrecipeInfoByWuman();
			Random random = new Random();
			int n = random.nextInt(findFoodSrecipeInfoByMan.size());
			YhFoodSrecipeApiNew yhFoodSrecipeApiNew = findFoodSrecipeInfoByMan.get(n);
			YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp = new YhFoodSrecipeRecordApp();
			yhFoodSrecipeRecordApp.setRecordData(new Date());
			yhFoodSrecipeRecordApp.setRecordName("首次默认饮食推荐");
			yhFoodSrecipeRecordApp.setRecordKay(Long.valueOf(yhFoodSrecipeApiNew.geteLevel()));
			yhFoodSrecipeRecordApp.setRecordMode("系统自动");
			yhFoodSrecipeRecordApp.setUserCode(usercode);
			yhFoodSrecipeRecordApp.setMealId(String.valueOf(yhFoodSrecipeApiNew.getId()));
			yhFoodSrecipeRecordApp.setIllnessId(String.valueOf(yhFoodSrecipeApiNew.getDiseaseid()));

			YhFoodSrecipeRecordAppServiceImpl.insertYhFoodSrecipeRecord(yhFoodSrecipeRecordApp);
			return yhFoodSrecipeApiNew;
		}
	}

	/**
	 * 定时任务调用（生成用户饮食周报）
	 * 
	 * @throws ParseException
	 */
	public int addYhUserFoodWeekly() throws ParseException {

		// 上周记录了的用户集合
		List<String> findWeekInfoForUserCode = yhFoodUserDiaryMapper.findWeekInfoForUserCode();
		int insertSelective = 0;
		for (String string : findWeekInfoForUserCode) {
			// 每个用户上周的日记集合
			List<YhFoodUserDiary> findWeekInfo = yhFoodUserDiaryMapper.findWeekInfo(string);
			YhUserFoodWeeklyWeb yhUserFoodWeekly = new YhUserFoodWeeklyWeb();
			yhUserFoodWeekly.setUserCode(string);
			// 本周一时间
			yhUserFoodWeekly.setStarttime(weekBegin());
			// 本周末时间
			yhUserFoodWeekly.setEndtime(weekEnd());
			// 记录天数
			yhUserFoodWeekly.setEcorddays(findWeekInfo.size());
			// 定义超标天数
			int exceedingDays = 0;
			// 定义不重复食材数
			HashSet<String> classSet = new HashSet<String>();
			// 全部摄入
			BigDecimal energyKcalAll = new BigDecimal("0");
			// 全部蛋白质
			BigDecimal proteinAll = new BigDecimal("0");
			// 全部脂肪
			BigDecimal fat = new BigDecimal("0");
			// 全部碳水化合物
			BigDecimal carbohydrate =new BigDecimal("0");

			// 早餐热量
			BigDecimal zaoZaoKcal = new BigDecimal("0");
			// 午餐热量
			BigDecimal zaoWuKcal = new BigDecimal("0");
			// 晚餐热量
			BigDecimal zaoWanKcal = new BigDecimal("0");
			// 加餐热量
			BigDecimal zaoJiaKcal = new BigDecimal("0");

			/*
			 * 谷薯及杂豆类 蛋类 奶类及制品 蔬菜和菌藻 坚果、大豆及制品 饮料 食用油、油脂及制品 调味品 零食、点心、冷饮 菜肴 其它
			 * 畜禽肉类 水果 水产品 罐头
			 */

			BigDecimal type1Kacl = new BigDecimal("0");
			BigDecimal type2Kacl = new BigDecimal("0");
			BigDecimal type3Kacl = new BigDecimal("0");
			BigDecimal type4Kacl = new BigDecimal("0");
			BigDecimal type5Kacl = new BigDecimal("0");
			BigDecimal type6Kacl = new BigDecimal("0");
			BigDecimal type7Kacl = new BigDecimal("0");
			BigDecimal type8Kacl = new BigDecimal("0");
			BigDecimal type9Kacl = new BigDecimal("0");
			BigDecimal type10Kacl =new BigDecimal("0");
			BigDecimal type11Kacl =new BigDecimal("0");
			BigDecimal type12Kacl =new BigDecimal("0");
			BigDecimal type13Kacl =new BigDecimal("0");
			BigDecimal type14Kacl =new BigDecimal("0");
			BigDecimal type15Kacl =new BigDecimal("0");

			// 本周推荐摄入与实际摄入
			String recommend = "";

			for (YhFoodUserDiary yhFoodUserDiary : findWeekInfo) {
				if (StringUtil.isEmpty(yhFoodUserDiary.getfElements())) {
					continue;
				}

				Long energyLevel = yhFoodUserDiary.getEnergyLevel();
				BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
				recommend = recommend + yhFoodUserDiary.getcTime() + "," + energyLevel + "," + energyKcal + ";";
				proteinAll = proteinAll.add(new BigDecimal(yhFoodUserDiary.getProtein()));

				fat = fat.add(new BigDecimal(yhFoodUserDiary.getFat()));

				carbohydrate = carbohydrate.add(new BigDecimal(yhFoodUserDiary.getCho()));

				energyKcalAll = energyKcalAll.add(energyKcal);
				if (energyKcal.compareTo(new BigDecimal(energyLevel))==1) {
					exceedingDays++;
				}
				String getfElements = yhFoodUserDiary.getfElements();
				String[] split = getfElements.split(";");
				for (String string2 : split) {
					String[] split2 = string2.split("_");
					String string5 = split2[0];
					// 餐次类型
					String string4 = split2[1];
					// 食物id
					String string3 = split2[2];
					// 克数
					String string7 = "";
					if ("10".equals(string5)) {
						String[] split3 = string2.split(",");
						for (int i = 1; i < split3.length; i++) {
							// 90354_47.5
							String string6 = split3[i];
							String[] split4 = string6.split("_");
							// foodid
							String string8 = split4[0];
							String string9 = split4[1];
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(string8));
							if (null == foodInfo) {
								continue;
							}

							// 食物类型
							String getfType = foodInfo.getfType();

							// 食物每100克热量
							BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());


							BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string9));

							if ("1".equals(getfType)) {
								type1Kacl = type1Kacl.add(rijiKacl);
							}
							if ("2".equals(getfType)) {
								type2Kacl = type2Kacl.add(rijiKacl);
							}
							if ("3".equals(getfType)) {
								type3Kacl = type3Kacl.add(rijiKacl);
							}
							if ("4".equals(getfType)) {
								type4Kacl = type4Kacl.add(rijiKacl);
							}
							if ("5".equals(getfType)) {
								type5Kacl = type5Kacl.add(rijiKacl);
							}
							if ("6".equals(getfType)) {
								type6Kacl = type6Kacl.add(rijiKacl);
							}
							if ("7".equals(getfType)) {
								type7Kacl = type7Kacl.add(rijiKacl);
							}
							if ("8".equals(getfType)) {
								type8Kacl = type8Kacl.add(rijiKacl);
							}
							if ("9".equals(getfType)) {
								type9Kacl = type9Kacl.add(rijiKacl);
							}
							if ("10".equals(getfType)) {
								type10Kacl = type10Kacl.add(rijiKacl);
							}
							if ("11".equals(getfType)) {
								type11Kacl = type11Kacl.add(rijiKacl);
							}
							if ("12".equals(getfType)) {
								type12Kacl = type12Kacl.add(rijiKacl);
							}
							if ("13".equals(getfType)) {
								type13Kacl = type13Kacl.add(rijiKacl);
							}
							if ("14".equals(getfType)) {
								type14Kacl = type14Kacl.add(rijiKacl);
							}
							if ("15".equals(getfType)) {
								type15Kacl = type15Kacl.add(rijiKacl);
							}

							if ("1".equals(string4)) {
								zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
							}
							if ("2".equals(string4)) {
								zaoWuKcal = zaoWuKcal.add(rijiKacl);
							}
							if ("3".equals(string4)) {
								zaoWanKcal = zaoWanKcal.add(rijiKacl);
							}
							if ("4".equals(string4)) {
								zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
							}

						}
						String string6 = split3[0];
						String[] split4 = string6.split("_");
						// 克数
						string7 = split4[3];

					} else {
						// 克数
						string7 = split2[3];
						YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
								.selectByPrimaryKey(Integer.valueOf(string3));
						if (null == foodInfo) {
							continue;
						}

						// 食物类型
						String getfType = foodInfo.getfType();

						// 食物每100克热量
						BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());


						BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string7));

						if ("1".equals(getfType)) {
							type1Kacl = type1Kacl.add(rijiKacl);
						}
						if ("2".equals(getfType)) {
							type2Kacl = type2Kacl.add(rijiKacl);
						}
						if ("3".equals(getfType)) {
							type3Kacl = type3Kacl.add(rijiKacl);
						}
						if ("4".equals(getfType)) {
							type4Kacl = type4Kacl.add(rijiKacl);
						}
						if ("5".equals(getfType)) {
							type5Kacl = type5Kacl.add(rijiKacl);
						}
						if ("6".equals(getfType)) {
							type6Kacl = type6Kacl.add(rijiKacl);
						}
						if ("7".equals(getfType)) {
							type7Kacl = type7Kacl.add(rijiKacl);
						}
						if ("8".equals(getfType)) {
							type8Kacl = type8Kacl.add(rijiKacl);
						}
						if ("9".equals(getfType)) {
							type9Kacl = type9Kacl.add(rijiKacl);
						}
						if ("10".equals(getfType)) {
							type10Kacl = type10Kacl.add(rijiKacl);
						}
						if ("11".equals(getfType)) {
							type11Kacl = type11Kacl.add(rijiKacl);
						}
						if ("12".equals(getfType)) {
							type12Kacl = type12Kacl.add(rijiKacl);
						}
						if ("13".equals(getfType)) {
							type13Kacl = type13Kacl.add(rijiKacl);
						}
						if ("14".equals(getfType)) {
							type14Kacl = type14Kacl.add(rijiKacl);
						}
						if ("15".equals(getfType)) {
							type15Kacl = type15Kacl.add(rijiKacl);
						}

						if ("1".equals(string4)) {
							zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
						}
						if ("2".equals(string4)) {
							zaoWuKcal = zaoWuKcal.add(rijiKacl);
						}
						if ("3".equals(string4)) {
							zaoWanKcal = zaoWanKcal.add(rijiKacl);
						}
						if ("4".equals(string4)) {
							zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
						}


					}

					classSet.add(string3);

				}

			}

			yhUserFoodWeekly.setRecommend(recommend);
			// 超标天数
			yhUserFoodWeekly.setExceedingdays(exceedingDays);
			// 不重复食材
			yhUserFoodWeekly.setNorepetition(classSet.size());
			List<String> weekList = weekList();

			BigDecimal weekValue1 = new BigDecimal("0");

			for (String string2 : weekList) {
				String tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTimeAndType2(string, string2);
				if (StringUtil.isEmpty(tuijianNum)) {
					tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTime(string);
					if (StringUtil.isEmpty(tuijianNum)) {
						tuijianNum = YhFoodSrecipeRecordAppServiceImpl.tuijianNum(string);
					}
				}
				weekValue1 = weekValue1.add(new BigDecimal(tuijianNum));
			}

			// 7天应该平均摄入
			yhUserFoodWeekly.setAverageintake7(weekValue1.divide(new BigDecimal(7),2,BigDecimal.ROUND_HALF_UP).toString());
			// 实际平均
			yhUserFoodWeekly.setAverageintake(energyKcalAll.divide(new BigDecimal(findWeekInfo.size()),2,BigDecimal.ROUND_HALF_UP).toString());
			yhUserFoodWeekly.setProtein(proteinAll.toString());
			yhUserFoodWeekly.setFat(fat.toString());
			yhUserFoodWeekly.setCarbohydrate(carbohydrate.toString());

			yhUserFoodWeekly.setZao(zaoZaoKcal.toString());
			yhUserFoodWeekly.setWu(zaoWuKcal.toString());
			yhUserFoodWeekly.setWan(zaoWanKcal.toString());
			yhUserFoodWeekly.setJia(zaoJiaKcal.toString());
			yhUserFoodWeekly.setType1(type1Kacl.toString());
			yhUserFoodWeekly.setType2(type2Kacl.toString());
			yhUserFoodWeekly.setType3(type3Kacl.toString());
			yhUserFoodWeekly.setType4(type4Kacl.toString());
			yhUserFoodWeekly.setType5(type5Kacl.toString());
			yhUserFoodWeekly.setType6(type6Kacl.toString());
			yhUserFoodWeekly.setType7(type7Kacl.toString());
			yhUserFoodWeekly.setType8(type8Kacl.toString());
			yhUserFoodWeekly.setType9(type9Kacl.toString());
			yhUserFoodWeekly.setType10(type10Kacl.toString());
			yhUserFoodWeekly.setType11(type11Kacl.toString());
			yhUserFoodWeekly.setType12(type12Kacl.toString());
			yhUserFoodWeekly.setType13(type13Kacl.toString());
			yhUserFoodWeekly.setType14(type14Kacl.toString());
			yhUserFoodWeekly.setType15(type15Kacl.toString());
			yhUserFoodWeekly.setActual(DateUtil.changeDateTOStr(new Date()));
			int findFoodWeekISNot = YhUserFoodWeeklyWebMapper.findFoodWeekISNot(string, weekBegin(), weekEnd());
			if (findFoodWeekISNot > 0) {
				continue;
			}
			insertSelective = insertSelective + YhUserFoodWeeklyWebMapper.insertSelective(yhUserFoodWeekly);
			Date date = new SimpleDateFormat("yy-MM-dd").parse(weekEnd());

			YhUserNotifyVo tify = new YhUserNotifyVo();
			tify.setTitle("膳食报告");
			tify.setContent("您的膳食报告，已生成立即查看详情>>");
			tify.setUserId(string);
			tify.setSendDate(date);
			tify.setToServiceId(2);
			tify.setNewsId(yhUserFoodWeekly.getWid());
			YhUserNotifyMapper.insertSelective(tify);
		}

		return insertSelective;
	}

	public String qidata() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(hehe);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 7);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		System.out.println(dayBefore);
		return dayBefore;
	}

	@Autowired
	YhUserReginfoAppMapper YhUserReginfoAppMapper;
	public int addYhUserFoodWeekly5(String time,int index){
		//需要生成数据的用户集合
		List<YhUserReginfoApp> da000087 = YhUserReginfoAppMapper.findDoctorForUserInfo("DA000087");
		for (int i = 0; i < da000087.size(); i++) {
			YhUserReginfoApp yhUserReginfoApp = da000087.get(i);
			String userCode = yhUserReginfoApp.getUserCode();
			YhFoodSrecipeRecordApp foodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);
			if(null!=foodSrecipeRecordByUsercodeForNow){
				String mealId = foodSrecipeRecordByUsercodeForNow.getMealId();
				YhFoodSrecipeApiNew selectByPrimaryKey = YhFoodSrecipeApiNewMapper.selectByPrimaryKey(Long.parseLong(mealId));
				//961;962;963;964;965;966;967;968;969;970;971;972;973;974;975;976;977;978;979;980;981;982;983;984;985;986;987;988;989;990;
				String s = selectByPrimaryKey.getfElements();
				String[] split1 = s.split(";");
				if(index+1>=split1.length){
					index=index+1-split1.length;
				}

				YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
						.findFoodUserScrecipeBySrecipeId(Integer.valueOf(split1[index]));
				String s3 = findFoodUserScrecipeBySrecipeId.getfElements();
				String[] split = s3.split(";");
				for (int j = 0; j < split.length; j++) {
					String s1 = split[j];
					String[] s2 = s1.split("_");
					String type = s2[0];
					String foodid = s2[1];
					String num = s2[2];
					addFoodDiaryInfo(userCode,time,Integer.valueOf(type),Integer.valueOf(foodid),Integer.valueOf(num));
				}
			}

		}


		return 0;
	}
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	@Autowired
	com.yihe.applet.api.mapper.YhFoodCookedMapper YhFoodCookedMapper;
	public Result addFoodDiaryInfo(String usercode, String time, int type, int foodid, int num) {
		if (StringUtil.isEmpty(usercode) || type == 0 || StringUtil.isEmpty(time) || num == 0 || foodid == 0) {
			return Result.error("参数异常");
		}
		if (!isInteger(String.valueOf(type))) {
			return Result.error("参数异常,早中晚加餐类型为数字1，2，3，4----而不是" + type);
		}
		if (!isInteger(String.valueOf(foodid))) {
			return Result.error("食物id异常：" + foodid);
		}
		if (!isInteger(String.valueOf(num))) {
			return Result.error("食物克数异常，该是正整数：" + num);
		}
		// 根据时间查询用户是否已经存在膳食日记
		YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, time);

		// 根据食材id查询食材的详情
		YhFoodCookbook2 selectByPrimaryKey = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(foodid);

		if (null == selectByPrimaryKey||null==selectByPrimaryKey.getfType()) {

			return Result.error("食物id不存在为: " + foodid);
		}
		/*食物类型_早中晚餐类型_食物id_食物克数
		 10_1_1514_100,451_55,542_45;1_1_515_200;*/

		// 查询饮食推荐能量
		String newElements =selectByPrimaryKey.getfType()+"_"+ type + "_" + foodid + "_" + num;
		if("10".equals(selectByPrimaryKey.getfType())){
			YhFoodCooked selectByPrimaryKey2 = YhFoodCookedMapper.selectByPrimaryKey(selectByPrimaryKey.getfCId());
			String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
			//90178_0.3;90034_0.7;
			String[] split = getfCookedelements.split(";");
			for (String string : split) {
				String[] split2 = string.split("_");
				if(split2.length<2){
					continue;
				}
				String foodidCaiYao=split2[0];
				String foodratio=split2[1];
				double ratiadoule=Double.valueOf(foodratio)*num;
				BigDecimal b = new BigDecimal(ratiadoule);
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				String dishElements=","+foodidCaiYao+"_"+f1;
				newElements=newElements+dishElements;
			}

		}
		newElements=newElements+ ";";
		YhFoodUserDiary yhFoodUserDiary = new YhFoodUserDiary();
		yhFoodUserDiary.setUserCode(usercode);
		yhFoodUserDiary.setcTime(time);

		int returnNum = 0;
		// 为空则新增膳食日记
		if (null == selectByUserCodeAndTime) {
			Long tuijianNum = tuijianNum(usercode);
			yhFoodUserDiary.setEnergyLevel(tuijianNum);
			yhFoodUserDiary.setfElements(newElements);
			// 热量
			BigDecimal energyKcal = new BigDecimal("0");
			if (null != selectByPrimaryKey.getEnergyKcal()) {

				energyKcal = new BigDecimal(selectByPrimaryKey.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 该食材该重量所含碳水化合物
			BigDecimal cho = new BigDecimal("0");
			if (null != selectByPrimaryKey.getCho()) {
				cho = new BigDecimal(selectByPrimaryKey.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}
			// 脂肪
			BigDecimal fat = new BigDecimal("0");
			if (null != selectByPrimaryKey.getFat()) {
				fat =new BigDecimal(selectByPrimaryKey.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 蛋白质
			BigDecimal protein = new BigDecimal("0");
			if (null != selectByPrimaryKey.getProtein()) {
				protein = new BigDecimal(selectByPrimaryKey.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 纤维素
			BigDecimal cellulose =new BigDecimal("0");
			if (null != selectByPrimaryKey.getCellulose()) {
				cellulose =new BigDecimal(selectByPrimaryKey.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}
			yhFoodUserDiary.setCho(cho.toString());
			yhFoodUserDiary.setFat(fat.toString());
			yhFoodUserDiary.setEnergyKcal(energyKcal.toString());
			yhFoodUserDiary.setProtein(protein.toString());
			yhFoodUserDiary.setCellulose(cellulose.toString());
			// 新增膳食日记
			returnNum = YhFoodUserDiaryServiceImpl.insertYhFoodUserDiary(yhFoodUserDiary);

		} else {
			// 不为空则修改
			// 之前的日记内容
			String getfElements = selectByUserCodeAndTime.getfElements();
			getfElements = getfElements + newElements;
			yhFoodUserDiary.setfElements(getfElements);
			// 之前的碳水化合物
			BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
			// 之前的脂肪
			BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
			// 之前的热量
			BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
			// 之前的蛋白
			BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
			// 之前的纤维素
			BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());

			// 热量
			BigDecimal energyKcal = new BigDecimal("0");
			if (null != selectByPrimaryKey.getEnergyKcal()) {

				energyKcal = new BigDecimal(selectByPrimaryKey.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 该食材该重量所含碳水化合物
			BigDecimal cho = new BigDecimal("0");
			if (null != selectByPrimaryKey.getCho()) {
				cho = new BigDecimal(selectByPrimaryKey.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}
			// 脂肪
			BigDecimal fat = new BigDecimal("0");
			if (null != selectByPrimaryKey.getFat()) {
				fat = new BigDecimal(selectByPrimaryKey.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 蛋白质
			BigDecimal protein = new BigDecimal("0");
			if (null != selectByPrimaryKey.getProtein()) {
				protein = new BigDecimal(selectByPrimaryKey.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 纤维素
			BigDecimal cellulose =new BigDecimal("0");
			if (null != selectByPrimaryKey.getCellulose()) {
				cellulose =  new BigDecimal(selectByPrimaryKey.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			yhFoodUserDiary.setCho(cho.add(cho2).toString());
			yhFoodUserDiary.setFat(fat.add(fat2).toString());
			yhFoodUserDiary.setEnergyKcal(energyKcal.add(energyKcal2).toString());
			yhFoodUserDiary.setProtein(protein.add(protein2).toString());
			yhFoodUserDiary.setCellulose(cellulose.add(cellulose2).toString());
			returnNum = YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(yhFoodUserDiary);

		}
		// 食物常用+1
		YhFoodCookbook2 record = new YhFoodCookbook2();
		record.setfCId(foodid);
		if (null == selectByPrimaryKey.getCommon()) {
			record.setCommon(1);
		} else {
			record.setCommon(selectByPrimaryKey.getCommon() + 1);
		}
		YhFoodCookbook2ServiceImpl.updateByPrimaryKeySelective(record);
		if (returnNum == 0) {
			return Result.error();
		} else {
			return Result.ok();
		}
	}
	@Autowired
	YhUserReginfoAppServiceImpl YhUserReginfoAppServiceImpl;
	@Autowired
	YhFoodSrecipeApiNewServiceImpl YhFoodSrecipeApiNewServiceImpl;

	public Long tuijianNum(String usercode) {
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
				.findFoodSrecipeRecordByUsercodeForNow(usercode);
		// 推荐摄入量
		Long recordKay = 1733l;
		if (null != findFoodSrecipeRecordByUsercodeForNow
				&& null != findFoodSrecipeRecordByUsercodeForNow.getRecordKay()) {
			recordKay = findFoodSrecipeRecordByUsercodeForNow.getRecordKay();
		} else {
			// 如果等于空则获取 初始化膳食热量评估 3.1等膳食推荐数据库做好改（新用户为评估的默认膳食推荐能量）
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppServiceImpl.findYhUserReginfo(usercode);
			if(null!=findYhUserReginfo){
				Integer gender = findYhUserReginfo.getGender();

				YhFoodSrecipeApiNew findNewFoodSrecipeForSex = YhFoodSrecipeApiNewServiceImpl.findNewFoodSrecipeForSex(usercode,gender);
				recordKay = Long.valueOf(findNewFoodSrecipeForSex.geteLevel());
			}

		}
		return recordKay;
	}

	public int addYhUserFoodWeekly4() throws ParseException {
		List<YhFoodUserDiary> findFoodDiaryAll = yhFoodUserDiaryMapper.findFoodDiaryAll();
		for (YhFoodUserDiary yhFoodUserDiary : findFoodDiaryAll) {
			BigDecimal kar =new BigDecimal("0");
			BigDecimal fatKar =new BigDecimal("0");
			BigDecimal choKar=new BigDecimal("0");
			BigDecimal proteinKar =new BigDecimal("0");
			BigDecimal celluloseKar =new BigDecimal("0");
			// 1_1_3340_300;1_1_3840_50;10_1_85640_100,90344_80.0,90440_20.0;
			String getfElements = yhFoodUserDiary.getfElements();
			if (StringUtil.isNotEmpty(getfElements)) {
				// 1_1_3340_300
				String[] split = getfElements.split(";");
				for (int i = 0; i < split.length; i++) {
					String fElement = split[i];
					String[] split2 = fElement.split("_");
					String type = split2[0];
					if ("10".equals(type)) {
						String[] split3 = fElement.split(",");
						for (int j = 1; j < split3.length; j++) {
							//90344_80.0
							String string = split3[j];
							String[] split4 = string.split("_");
							String foodid = split4[0];
							Float keshu = Float.valueOf(split4[1]);
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(foodid));
							if(null==foodInfo){
								continue;
							}
							BigDecimal energyKcal = new BigDecimal(foodInfo.getEnergyKcal());
							BigDecimal fat = new BigDecimal(foodInfo.getFat());
							BigDecimal cho = new BigDecimal(foodInfo.getCho());
							BigDecimal protein = new BigDecimal(foodInfo.getProtein());
							BigDecimal cellulose = new BigDecimal(foodInfo.getCellulose());

							kar = kar.add(energyKcal.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));

							fatKar = fatKar.add(fat.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


							choKar = choKar.add(cho.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


							proteinKar = proteinKar.add(protein.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


							celluloseKar = celluloseKar.add(cellulose.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));
							
							
						}
					} else {

							// 非菜肴
							String foodid = split2[2];
							Float keshu = Float.valueOf(split2[3]);
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(foodid));
							if(null==foodInfo){
								continue;
							}
						BigDecimal energyKcal = new BigDecimal(foodInfo.getEnergyKcal());
						BigDecimal fat = new BigDecimal(foodInfo.getFat());
						BigDecimal cho = new BigDecimal(foodInfo.getCho());
						BigDecimal protein = new BigDecimal(foodInfo.getProtein());
						BigDecimal cellulose = new BigDecimal(foodInfo.getCellulose());
						kar = kar.add(energyKcal.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));

						fatKar = fatKar.add(fat.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


						choKar = choKar.add(cho.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


						proteinKar = proteinKar.add(protein.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


						celluloseKar = celluloseKar.add(cellulose.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));

					}

				}
			}
			System.out.println(kar);
			YhFoodUserDiary yhFoodUserDiary2 = new YhFoodUserDiary();
			yhFoodUserDiary2.setuFdId(yhFoodUserDiary.getuFdId());
			yhFoodUserDiary2.setEnergyKcal(kar.toString());
			yhFoodUserDiary2.setFat(fatKar.toString());
			yhFoodUserDiary2.setCho(choKar.toString());
			yhFoodUserDiary2.setProtein(proteinKar.toString());
			yhFoodUserDiary2.setCellulose(celluloseKar.toString());
			yhFoodUserDiaryMapper.updateYhFoodUserDiary(yhFoodUserDiary2);
		}
		return 0;
	}

	public int addYhUserFoodWeekly6() throws ParseException {
		List<String> strTime = new ArrayList<>();
		List<String> endTime = new ArrayList<>();
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] weeks = dfs.getWeekdays();

		// 2020-10-31

		c_begin.set(2020, 7, 31); // Calendar的月从0-11，所以4月是3.

		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

		Date d = lastMonday();
		System.out.println(smf.format(d));
		String format = smf.format(d);
		c_end.set(2020, 9, 4); // Calendar的月从0-11，所以5月是4.

		int count = 1;

		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

		while (c_begin.before(c_end)) {
			if ("星期一".equals(weeks[c_begin.get(Calendar.DAY_OF_WEEK)])) {
				System.out.println("第" + count + "周  日期：" + new java.sql.Date(c_begin.getTime().getTime()) + ","
						+ weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
				strTime.add("" + new java.sql.Date(c_begin.getTime().getTime()));
			}
			if ("星期日".equals(weeks[c_begin.get(Calendar.DAY_OF_WEEK)])) {
				System.out.println("第" + count + "周  日期：" + new java.sql.Date(c_begin.getTime().getTime()) + ","
						+ weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
				endTime.add("" + new java.sql.Date(c_begin.getTime().getTime()));
			}

			if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				count++;
			}
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
		}

		List<YhUserReginfoApp> da000087 = YhUserReginfoAppMapper.findDoctorForUserInfo("DA000087");
		System.out.println(da000087.toString());
		for (int m = 0; m < da000087.size(); m++) {
			for (int i = 0; i < strTime.size(); i++) {

				List<YhFoodUserDiary> findFoodDiaryList = yhFoodUserDiaryMapper.findFoodDiaryList(strTime.get(i),
						endTime.get(i), da000087.get(m).getUserCode());
				System.out.println(findFoodDiaryList.toString());
				if (null == findFoodDiaryList || findFoodDiaryList.size() == 0) {
					continue;
				}
				// 生成周报
				YhUserFoodWeeklyWeb yhUserFoodWeekly = new YhUserFoodWeeklyWeb();
				yhUserFoodWeekly.setUserCode(findFoodDiaryList.get(0).getUserCode());
				// 本周一时间
				yhUserFoodWeekly.setStarttime(strTime.get(i));
				// 本周末时间
				yhUserFoodWeekly.setEndtime(endTime.get(i));
				// 记录天数
				yhUserFoodWeekly.setEcorddays(findFoodDiaryList.size());
				// 定义超标天数
				int exceedingDays = 0;
				// 定义不重复食材数
				HashSet<String> classSet = new HashSet<String>();
				// 全部摄入
				BigDecimal energyKcalAll = new BigDecimal("0");
				// 全部蛋白质
				BigDecimal proteinAll = new BigDecimal("0");
				// 全部脂肪
				BigDecimal fat = new BigDecimal("0");
				// 全部碳水化合物
				BigDecimal carbohydrate = new BigDecimal("0");

				// 早餐热量
				BigDecimal zaoZaoKcal = new BigDecimal("0");
				// 午餐热量
				BigDecimal zaoWuKcal = new BigDecimal("0");
				// 晚餐热量
				BigDecimal zaoWanKcal = new BigDecimal("0");
				// 加餐热量
				BigDecimal zaoJiaKcal = new BigDecimal("0");

				/*
				 * 谷薯及杂豆类 蛋类 奶类及制品 蔬菜和菌藻 坚果、大豆及制品 饮料 食用油、油脂及制品 调味品 零食、点心、冷饮 菜肴
				 * 其它 畜禽肉类 水果 水产品 罐头
				 */

				BigDecimal type1Kacl = new BigDecimal("0");
				BigDecimal type2Kacl = new BigDecimal("0");
				BigDecimal type3Kacl = new BigDecimal("0");
				BigDecimal type4Kacl = new BigDecimal("0");
				BigDecimal type5Kacl = new BigDecimal("0");
				BigDecimal type6Kacl = new BigDecimal("0");
				BigDecimal type7Kacl = new BigDecimal("0");
				BigDecimal type8Kacl = new BigDecimal("0");
				BigDecimal type9Kacl = new BigDecimal("0");
				BigDecimal type10Kacl = new BigDecimal("0");
				BigDecimal type11Kacl = new BigDecimal("0");
				BigDecimal type12Kacl = new BigDecimal("0");
				BigDecimal type13Kacl = new BigDecimal("0");
				BigDecimal type14Kacl = new BigDecimal("0");
				BigDecimal type15Kacl = new BigDecimal("0");

				// 本周推荐摄入与实际摄入
				String recommend = "";

				for (YhFoodUserDiary yhFoodUserDiary : findFoodDiaryList) {
					String usercode = yhFoodUserDiary.getUserCode();
					String getfElements = yhFoodUserDiary.getfElements();
					String newFelements = "";

					getfElements = newFelements;

					if (StringUtil.isEmpty(getfElements)) {
						continue;
					}
					/// ********************
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					recommend = recommend + yhFoodUserDiary.getcTime() + "," + energyLevel + "," + energyKcal + ";";

					proteinAll = proteinAll.add(new BigDecimal(yhFoodUserDiary.getProtein()));

					fat = fat.add(new BigDecimal(yhFoodUserDiary.getFat()));

					carbohydrate = carbohydrate.add(new BigDecimal(yhFoodUserDiary.getCho()));
					energyKcalAll = energyKcalAll.add(energyKcal);
					if (energyKcal.compareTo(new BigDecimal(energyLevel))==1) {
						exceedingDays++;
					}
					String[] split = getfElements.split(";");
					for (String string2 : split) {
						String[] split2 = string2.split("_");
						String string5 = split2[0];
						// 餐次类型
						String string4 = split2[1];
						// 食物id
						String string3 = split2[2];
						// 克数
						String string7 = "";
						if ("10".equals(string5)) {
							String[] split3 = string2.split(",");
							for (int j = 1; j < split3.length; j++) {
								// 90354_47.5
								String string6 = split3[j];
								String[] split4 = string6.split("_");
								// foodid
								String string8 = split4[0];
								String string9 = split4[1];
								YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
										.selectByPrimaryKey(Integer.valueOf(string8));
								if (null == foodInfo) {
									continue;
								}

								// 食物类型
								String getfType = foodInfo.getfType();

								// 食物每100克热量
								BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());

								BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string9));

								if ("1".equals(getfType)) {
									type1Kacl = type1Kacl.add(rijiKacl);
								}
								if ("2".equals(getfType)) {
									type2Kacl = type2Kacl.add(rijiKacl);
								}
								if ("3".equals(getfType)) {
									type3Kacl = type3Kacl.add(rijiKacl);
								}
								if ("4".equals(getfType)) {
									type4Kacl = type4Kacl.add(rijiKacl);
								}
								if ("5".equals(getfType)) {
									type5Kacl = type5Kacl.add(rijiKacl);
								}
								if ("6".equals(getfType)) {
									type6Kacl = type6Kacl.add(rijiKacl);
								}
								if ("7".equals(getfType)) {
									type7Kacl = type7Kacl.add(rijiKacl);
								}
								if ("8".equals(getfType)) {
									type8Kacl = type8Kacl.add(rijiKacl);
								}
								if ("9".equals(getfType)) {
									type9Kacl = type9Kacl.add(rijiKacl);
								}
								if ("10".equals(getfType)) {
									type10Kacl = type10Kacl.add(rijiKacl);
								}
								if ("11".equals(getfType)) {
									type11Kacl = type11Kacl.add(rijiKacl);
								}
								if ("12".equals(getfType)) {
									type12Kacl = type12Kacl.add(rijiKacl);
								}
								if ("13".equals(getfType)) {
									type13Kacl = type13Kacl.add(rijiKacl);
								}
								if ("14".equals(getfType)) {
									type14Kacl = type14Kacl.add(rijiKacl);
								}
								if ("15".equals(getfType)) {
									type15Kacl = type15Kacl.add(rijiKacl);
								}


								if ("1".equals(string4)) {
									zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
								}
								if ("2".equals(string4)) {
									zaoWuKcal = zaoWuKcal.add(rijiKacl);
								}
								if ("3".equals(string4)) {
									zaoWanKcal = zaoWanKcal.add(rijiKacl);
								}
								if ("4".equals(string4)) {
									zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
								}

							}
							String string6 = split3[0];
							String[] split4 = string6.split("_");
							// 克数
							string7 = split4[3];

						} else {
							// 克数
							string7 = split2[3];
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(string3));
							if (null == foodInfo) {
								continue;
							}

							// 食物类型
							String getfType = foodInfo.getfType();

							// 食物每100克热量
							BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());

							BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string7));

							if ("1".equals(getfType)) {
								type1Kacl = type1Kacl.add(rijiKacl);
							}
							if ("2".equals(getfType)) {
								type2Kacl = type2Kacl.add(rijiKacl);
							}
							if ("3".equals(getfType)) {
								type3Kacl = type3Kacl.add(rijiKacl);
							}
							if ("4".equals(getfType)) {
								type4Kacl = type4Kacl.add(rijiKacl);
							}
							if ("5".equals(getfType)) {
								type5Kacl = type5Kacl.add(rijiKacl);
							}
							if ("6".equals(getfType)) {
								type6Kacl = type6Kacl.add(rijiKacl);
							}
							if ("7".equals(getfType)) {
								type7Kacl = type7Kacl.add(rijiKacl);
							}
							if ("8".equals(getfType)) {
								type8Kacl = type8Kacl.add(rijiKacl);
							}
							if ("9".equals(getfType)) {
								type9Kacl = type9Kacl.add(rijiKacl);
							}
							if ("10".equals(getfType)) {
								type10Kacl = type10Kacl.add(rijiKacl);
							}
							if ("11".equals(getfType)) {
								type11Kacl = type11Kacl.add(rijiKacl);
							}
							if ("12".equals(getfType)) {
								type12Kacl = type12Kacl.add(rijiKacl);
							}
							if ("13".equals(getfType)) {
								type13Kacl = type13Kacl.add(rijiKacl);
							}
							if ("14".equals(getfType)) {
								type14Kacl = type14Kacl.add(rijiKacl);
							}
							if ("15".equals(getfType)) {
								type15Kacl = type15Kacl.add(rijiKacl);
							}


							if ("1".equals(string4)) {
								zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
							}
							if ("2".equals(string4)) {
								zaoWuKcal = zaoWuKcal.add(rijiKacl);
							}
							if ("3".equals(string4)) {
								zaoWanKcal = zaoWanKcal.add(rijiKacl);
							}
							if ("4".equals(string4)) {
								zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
							}


						}

						classSet.add(string3);

					}

				}

				yhUserFoodWeekly.setRecommend(recommend);
				// 超标天数
				yhUserFoodWeekly.setExceedingdays(exceedingDays);
				// 不重复食材
				yhUserFoodWeekly.setNorepetition(classSet.size());
				List<String> weekList = weekList();


				BigDecimal weekValue1 = new BigDecimal("0");

				for (String string2 : weekList) {
					String tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTimeAndType2(findFoodDiaryList.get(0).getUserCode(), string2);
					if (StringUtil.isEmpty(tuijianNum)) {
						tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTime(findFoodDiaryList.get(0).getUserCode());
						if (StringUtil.isEmpty(tuijianNum)) {
							tuijianNum = YhFoodSrecipeRecordAppServiceImpl.tuijianNum(findFoodDiaryList.get(0).getUserCode());
						}
					}
					weekValue1 = weekValue1.add(new BigDecimal(tuijianNum));
				}

				// 7天应该平均摄入
				yhUserFoodWeekly.setAverageintake7(weekValue1.divide(new BigDecimal(7),2,BigDecimal.ROUND_HALF_UP).toString());
				// 实际平均
				yhUserFoodWeekly.setAverageintake(energyKcalAll.divide(new BigDecimal(findFoodDiaryList.size()),2,BigDecimal.ROUND_HALF_UP).toString());
				yhUserFoodWeekly.setProtein(proteinAll.toString());
				yhUserFoodWeekly.setFat(fat.toString());
				yhUserFoodWeekly.setCarbohydrate(carbohydrate.toString());

				yhUserFoodWeekly.setZao(zaoZaoKcal.toString());
				yhUserFoodWeekly.setWu(zaoWuKcal.toString());
				yhUserFoodWeekly.setWan(zaoWanKcal.toString());
				yhUserFoodWeekly.setJia(zaoJiaKcal.toString());
				yhUserFoodWeekly.setType1(type1Kacl.toString());
				yhUserFoodWeekly.setType2(type2Kacl.toString());
				yhUserFoodWeekly.setType3(type3Kacl.toString());
				yhUserFoodWeekly.setType4(type4Kacl.toString());
				yhUserFoodWeekly.setType5(type5Kacl.toString());
				yhUserFoodWeekly.setType6(type6Kacl.toString());
				yhUserFoodWeekly.setType7(type7Kacl.toString());
				yhUserFoodWeekly.setType8(type8Kacl.toString());
				yhUserFoodWeekly.setType9(type9Kacl.toString());
				yhUserFoodWeekly.setType10(type10Kacl.toString());
				yhUserFoodWeekly.setType11(type11Kacl.toString());
				yhUserFoodWeekly.setType12(type12Kacl.toString());
				yhUserFoodWeekly.setType13(type13Kacl.toString());
				yhUserFoodWeekly.setType14(type14Kacl.toString());
				yhUserFoodWeekly.setType15(type15Kacl.toString());
				yhUserFoodWeekly.setActual(DateUtil.changeDateTOStr(new Date()));
				YhUserFoodWeeklyWebMapper.insertSelective(yhUserFoodWeekly);
				Date date = new SimpleDateFormat("yy-MM-dd").parse(endTime.get(i));

				YhUserNotifyVo tify = new YhUserNotifyVo();
				tify.setTitle("膳食报告");
				tify.setContent("您的膳食报告，已生成立即查看详情>>");
				tify.setUserId(findFoodDiaryList.get(0).getUserCode());
				tify.setSendDate(date);
				tify.setToServiceId(2);
				tify.setNewsId(yhUserFoodWeekly.getWid());
				YhUserNotifyMapper.insertSelective(tify);

			}
		}

		return 1;

	}


	public int addYhUserFoodWeekly3() throws ParseException {
		List<String> strTime = new ArrayList<>();
		List<String> endTime = new ArrayList<>();
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] weeks = dfs.getWeekdays();
		// 2020-10-31
		String findFoodDiaryLimit1Time = yhFoodUserDiaryMapper.findFoodDiaryLimit1Time();
		if (StringUtil.isEmpty(findFoodDiaryLimit1Time)) {
			return 0;
		}

		String convertWeekByDate = convertWeekByDate(findFoodDiaryLimit1Time);
		System.out.println("输出年:" + getYear(convertWeekByDate));
		System.out.println("输出月:" + getMonth(convertWeekByDate));
		System.out.println("输出日:" + getDay(convertWeekByDate));
		c_begin.set(getYear(convertWeekByDate), getMonth(convertWeekByDate) - 1, getDay(convertWeekByDate)); // Calendar的月从0-11，所以4月是3.

		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

		Date d = lastMonday();
		System.out.println(smf.format(d));
		String format = smf.format(d);
		c_end.set(getYear(format), getMonth(format) - 1, getDay(format)); // Calendar的月从0-11，所以5月是4.

		int count = 1;

		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

		while (c_begin.before(c_end)) {
			if ("星期一".equals(weeks[c_begin.get(Calendar.DAY_OF_WEEK)])) {
				System.out.println("第" + count + "周  日期：" + new java.sql.Date(c_begin.getTime().getTime()) + ","
						+ weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
				strTime.add("" + new java.sql.Date(c_begin.getTime().getTime()));
			}
			if ("星期日".equals(weeks[c_begin.get(Calendar.DAY_OF_WEEK)])) {
				System.out.println("第" + count + "周  日期：" + new java.sql.Date(c_begin.getTime().getTime()) + ","
						+ weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
				endTime.add("" + new java.sql.Date(c_begin.getTime().getTime()));
			}

			if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				count++;
			}
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
		}

		List<String> findFoodDiaryListUser = yhFoodUserDiaryMapper.findFoodDiaryListUser();
		if (null == findFoodDiaryListUser) {
			return 0;
		}
		for (int m = 0; m < findFoodDiaryListUser.size(); m++) {
			for (int i = 0; i < strTime.size(); i++) {

				List<YhFoodUserDiary> findFoodDiaryList = yhFoodUserDiaryMapper.findFoodDiaryList(strTime.get(i),
						endTime.get(i), findFoodDiaryListUser.get(m));
				if (null == findFoodDiaryList || findFoodDiaryList.size() == 0) {
					continue;
				}
				// 生成周报
				YhUserFoodWeeklyWeb yhUserFoodWeekly = new YhUserFoodWeeklyWeb();
				yhUserFoodWeekly.setUserCode(findFoodDiaryList.get(0).getUserCode());
				// 本周一时间
				yhUserFoodWeekly.setStarttime(strTime.get(i));
				// 本周末时间
				yhUserFoodWeekly.setEndtime(endTime.get(i));
				// 记录天数
				yhUserFoodWeekly.setEcorddays(findFoodDiaryList.size());
				// 定义超标天数
				int exceedingDays = 0;
				// 定义不重复食材数
				HashSet<String> classSet = new HashSet<String>();
				// 全部摄入
				BigDecimal energyKcalAll = new BigDecimal("0");
				// 全部蛋白质
				BigDecimal proteinAll = new BigDecimal("0");
				// 全部脂肪
				BigDecimal fat = new BigDecimal("0");
				// 全部碳水化合物
				BigDecimal carbohydrate =new BigDecimal("0");

				// 早餐热量
				BigDecimal zaoZaoKcal =new BigDecimal("0");
				// 午餐热量
				BigDecimal zaoWuKcal = new BigDecimal("0");
				// 晚餐热量
				BigDecimal zaoWanKcal = new BigDecimal("0");
				// 加餐热量
				BigDecimal zaoJiaKcal = new BigDecimal("0");

				/*
				 * 谷薯及杂豆类 蛋类 奶类及制品 蔬菜和菌藻 坚果、大豆及制品 饮料 食用油、油脂及制品 调味品 零食、点心、冷饮 菜肴
				 * 其它 畜禽肉类 水果 水产品 罐头
				 */

				BigDecimal type1Kacl = new BigDecimal("0");
				BigDecimal type2Kacl = new BigDecimal("0");
				BigDecimal type3Kacl = new BigDecimal("0");
				BigDecimal type4Kacl = new BigDecimal("0");
				BigDecimal type5Kacl = new BigDecimal("0");
				BigDecimal type6Kacl = new BigDecimal("0");
				BigDecimal type7Kacl = new BigDecimal("0");
				BigDecimal type8Kacl = new BigDecimal("0");
				BigDecimal type9Kacl = new BigDecimal("0");
				BigDecimal type10Kacl =new BigDecimal("0");
				BigDecimal type11Kacl =new BigDecimal("0");
				BigDecimal type12Kacl =new BigDecimal("0");
				BigDecimal type13Kacl =new BigDecimal("0");
				BigDecimal type14Kacl =new BigDecimal("0");
				BigDecimal type15Kacl =new BigDecimal("0");


				// 本周推荐摄入与实际摄入
				String recommend = "";

				for (YhFoodUserDiary yhFoodUserDiary : findFoodDiaryList) {
					String usercode = yhFoodUserDiary.getUserCode();
					String getfElements = yhFoodUserDiary.getfElements();
					String newFelements = "";
					if (StringUtil.isNotEmpty(getfElements)) {
						String[] split = getfElements.split(";");

						for (String string : split) {
							String[] split2 = string.split("_");
							if (!"10".equals(split2[0])) {
								if (split2.length < 4) {
									string = "1_" + string;
								}
							}
							newFelements = newFelements + string + ";";
						}
						if (!newFelements.equals(getfElements)) {
							YhFoodUserDiary yhFoodUserDiary2 = new YhFoodUserDiary();
							yhFoodUserDiary2.setuFdId(yhFoodUserDiary.getuFdId());
							yhFoodUserDiary2.setfElements(newFelements);
							yhFoodUserDiaryMapper.updateYhFoodUserDiary2(yhFoodUserDiary2);
						}

					}
					getfElements = newFelements;

					if (StringUtil.isEmpty(getfElements)) {
						continue;
					}
					/// ********************
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					recommend = recommend + yhFoodUserDiary.getcTime() + "," + energyLevel + "," + energyKcal + ";";
					proteinAll = proteinAll.add(new BigDecimal(yhFoodUserDiary.getProtein()));

					fat = fat.add(new BigDecimal(yhFoodUserDiary.getFat()));

					carbohydrate = carbohydrate.add(new BigDecimal(yhFoodUserDiary.getCho()));

					energyKcalAll = energyKcalAll.add(energyKcal);
					if (energyKcal.compareTo(new BigDecimal(energyLevel))==1) {
						exceedingDays++;
					}
					String[] split = getfElements.split(";");
					for (String string2 : split) {
						String[] split2 = string2.split("_");
						String string5 = split2[0];
						// 餐次类型
						String string4 = split2[1];
						// 食物id
						String string3 = split2[2];
						// 克数
						String string7 = "";
						if ("10".equals(string5)) {
							String[] split3 = string2.split(",");
							for (int j = 1; j < split3.length; j++) {
								// 90354_47.5
								String string6 = split3[j];
								String[] split4 = string6.split("_");
								// foodid
								String string8 = split4[0];
								String string9 = split4[1];
								YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
										.selectByPrimaryKey(Integer.valueOf(string8));
								if (null == foodInfo) {
									continue;
								}

								// 食物类型
								String getfType = foodInfo.getfType();

								// 食物每100克热量
								BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());


								BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string9));

								if ("1".equals(getfType)) {
									type1Kacl = type1Kacl.add(rijiKacl);
								}
								if ("2".equals(getfType)) {
									type2Kacl = type2Kacl.add(rijiKacl);
								}
								if ("3".equals(getfType)) {
									type3Kacl = type3Kacl.add(rijiKacl);
								}
								if ("4".equals(getfType)) {
									type4Kacl = type4Kacl.add(rijiKacl);
								}
								if ("5".equals(getfType)) {
									type5Kacl = type5Kacl.add(rijiKacl);
								}
								if ("6".equals(getfType)) {
									type6Kacl = type6Kacl.add(rijiKacl);
								}
								if ("7".equals(getfType)) {
									type7Kacl = type7Kacl.add(rijiKacl);
								}
								if ("8".equals(getfType)) {
									type8Kacl = type8Kacl.add(rijiKacl);
								}
								if ("9".equals(getfType)) {
									type9Kacl = type9Kacl.add(rijiKacl);
								}
								if ("10".equals(getfType)) {
									type10Kacl = type10Kacl.add(rijiKacl);
								}
								if ("11".equals(getfType)) {
									type11Kacl = type11Kacl.add(rijiKacl);
								}
								if ("12".equals(getfType)) {
									type12Kacl = type12Kacl.add(rijiKacl);
								}
								if ("13".equals(getfType)) {
									type13Kacl = type13Kacl.add(rijiKacl);
								}
								if ("14".equals(getfType)) {
									type14Kacl = type14Kacl.add(rijiKacl);
								}
								if ("15".equals(getfType)) {
									type15Kacl = type15Kacl.add(rijiKacl);
								}

								if ("1".equals(string4)) {
									zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
								}
								if ("2".equals(string4)) {
									zaoWuKcal = zaoWuKcal.add(rijiKacl);
								}
								if ("3".equals(string4)) {
									zaoWanKcal = zaoWanKcal.add(rijiKacl);
								}
								if ("4".equals(string4)) {
									zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
								}
							}
							String string6 = split3[0];
							String[] split4 = string6.split("_");
							// 克数
							string7 = split4[3];

						} else {
							// 克数
							string7 = split2[3];
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(string3));
							if (null == foodInfo) {
								continue;
							}

							// 食物类型
							String getfType = foodInfo.getfType();

							// 食物每100克热量
							BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());


							BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string7));

							if ("1".equals(getfType)) {
								type1Kacl = type1Kacl.add(rijiKacl);
							}
							if ("2".equals(getfType)) {
								type2Kacl = type2Kacl.add(rijiKacl);
							}
							if ("3".equals(getfType)) {
								type3Kacl = type3Kacl.add(rijiKacl);
							}
							if ("4".equals(getfType)) {
								type4Kacl = type4Kacl.add(rijiKacl);
							}
							if ("5".equals(getfType)) {
								type5Kacl = type5Kacl.add(rijiKacl);
							}
							if ("6".equals(getfType)) {
								type6Kacl = type6Kacl.add(rijiKacl);
							}
							if ("7".equals(getfType)) {
								type7Kacl = type7Kacl.add(rijiKacl);
							}
							if ("8".equals(getfType)) {
								type8Kacl = type8Kacl.add(rijiKacl);
							}
							if ("9".equals(getfType)) {
								type9Kacl = type9Kacl.add(rijiKacl);
							}
							if ("10".equals(getfType)) {
								type10Kacl = type10Kacl.add(rijiKacl);
							}
							if ("11".equals(getfType)) {
								type11Kacl = type11Kacl.add(rijiKacl);
							}
							if ("12".equals(getfType)) {
								type12Kacl = type12Kacl.add(rijiKacl);
							}
							if ("13".equals(getfType)) {
								type13Kacl = type13Kacl.add(rijiKacl);
							}
							if ("14".equals(getfType)) {
								type14Kacl = type14Kacl.add(rijiKacl);
							}
							if ("15".equals(getfType)) {
								type15Kacl = type15Kacl.add(rijiKacl);
							}

							if ("1".equals(string4)) {
								zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
							}
							if ("2".equals(string4)) {
								zaoWuKcal = zaoWuKcal.add(rijiKacl);
							}
							if ("3".equals(string4)) {
								zaoWanKcal = zaoWanKcal.add(rijiKacl);
							}
							if ("4".equals(string4)) {
								zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
							}

						}

						classSet.add(string3);

					}

				}

				yhUserFoodWeekly.setRecommend(recommend);
				// 超标天数
				yhUserFoodWeekly.setExceedingdays(exceedingDays);
				// 不重复食材
				yhUserFoodWeekly.setNorepetition(classSet.size());
				List<String> weekList = weekList();



				// 7天应该平均摄入
				BigDecimal weekValue1 = new BigDecimal("0");

				for (String string2 : weekList) {
					String tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTimeAndType2(findFoodDiaryList.get(0).getUserCode(), string2);
					if (StringUtil.isEmpty(tuijianNum)) {
						tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTime(findFoodDiaryList.get(0).getUserCode());
						if (StringUtil.isEmpty(tuijianNum)) {
							tuijianNum = YhFoodSrecipeRecordAppServiceImpl.tuijianNum(findFoodDiaryList.get(0).getUserCode());
						}
					}
					weekValue1 = weekValue1.add(new BigDecimal(tuijianNum));
				}

				// 7天应该平均摄入
				yhUserFoodWeekly.setAverageintake7(weekValue1.divide(new BigDecimal(7),2,BigDecimal.ROUND_HALF_UP).toString());
				// 实际平均
				yhUserFoodWeekly.setAverageintake(energyKcalAll.divide(new BigDecimal(findFoodDiaryList.size()),2,BigDecimal.ROUND_HALF_UP).toString());
				yhUserFoodWeekly.setProtein(proteinAll.toString());
				yhUserFoodWeekly.setFat(fat.toString());
				yhUserFoodWeekly.setCarbohydrate(carbohydrate.toString());

				yhUserFoodWeekly.setZao(zaoZaoKcal.toString());
				yhUserFoodWeekly.setWu(zaoWuKcal.toString());
				yhUserFoodWeekly.setWan(zaoWanKcal.toString());
				yhUserFoodWeekly.setJia(zaoJiaKcal.toString());
				yhUserFoodWeekly.setType1(type1Kacl.toString());
				yhUserFoodWeekly.setType2(type2Kacl.toString());
				yhUserFoodWeekly.setType3(type3Kacl.toString());
				yhUserFoodWeekly.setType4(type4Kacl.toString());
				yhUserFoodWeekly.setType5(type5Kacl.toString());
				yhUserFoodWeekly.setType6(type6Kacl.toString());
				yhUserFoodWeekly.setType7(type7Kacl.toString());
				yhUserFoodWeekly.setType8(type8Kacl.toString());
				yhUserFoodWeekly.setType9(type9Kacl.toString());
				yhUserFoodWeekly.setType10(type10Kacl.toString());
				yhUserFoodWeekly.setType11(type11Kacl.toString());
				yhUserFoodWeekly.setType12(type12Kacl.toString());
				yhUserFoodWeekly.setType13(type13Kacl.toString());
				yhUserFoodWeekly.setType14(type14Kacl.toString());
				yhUserFoodWeekly.setType15(type15Kacl.toString());
				yhUserFoodWeekly.setActual(DateUtil.changeDateTOStr(new Date()));
				YhUserFoodWeeklyWebMapper.insertSelective(yhUserFoodWeekly);
				Date date = new SimpleDateFormat("yy-MM-dd").parse(endTime.get(i));

				YhUserNotifyVo tify = new YhUserNotifyVo();
				tify.setTitle("膳食报告");
				tify.setContent("您的膳食报告，已生成立即查看详情>>");
				tify.setUserId(findFoodDiaryList.get(0).getUserCode());
				tify.setSendDate(date);
				tify.setToServiceId(2);
				tify.setNewsId(yhUserFoodWeekly.getWid());
				YhUserNotifyMapper.insertSelective(tify);

			}
		}

		return 1;

	}

	public int addYhUserFoodWeekly2() {

		// 上周记录了的用户集合
		// List<String> findWeekInfoForUserCode =
		// yhFoodUserDiaryMapper.findWeekInfoForUserCode();
		List<YhUserFoodGuide> findAllInfo = YhUserFoodGuideMapper.findAllInfo();

		int insertSelective = 0;
		for (YhUserFoodGuide yhUserFoodGuide : findAllInfo) {
			String string = String.valueOf(yhUserFoodGuide.getUserCode());
			// 每个用户上周的日记集合
			// List<YhFoodUserDiary> findWeekInfo =
			// yhFoodUserDiaryMapper.findWeekInfo(string);
			List<YhFoodUserDiary> findWeekInfo = yhFoodUserDiaryMapper.findWeekInfoByTimeAndUsercode(string,
					yhUserFoodGuide.getStartTime(), yhUserFoodGuide.getEndTime());
			if (null == findWeekInfo || findWeekInfo.size() == 0) {
				continue;
			}
			YhUserFoodWeeklyWeb yhUserFoodWeekly = new YhUserFoodWeeklyWeb();
			yhUserFoodWeekly.setUserCode(string);
			String time;
			SimpleDateFormat formater = new SimpleDateFormat();
			formater.applyPattern("yyyy-MM-dd");
			time = formater.format(yhUserFoodGuide.getStartTime());
			// 本周一时间
			yhUserFoodWeekly.setStarttime(time);
			String time2;
			SimpleDateFormat formater2 = new SimpleDateFormat();
			formater2.applyPattern("yyyy-MM-dd");
			time2 = formater.format(yhUserFoodGuide.getEndTime());
			// 本周末时间
			yhUserFoodWeekly.setEndtime(time2);
			// 记录天数
			yhUserFoodWeekly.setEcorddays(findWeekInfo.size());
			// 定义超标天数
			int exceedingDays = 0;
			// 定义不重复食材数
			HashSet<String> classSet = new HashSet<String>();
			// 全部摄入
			BigDecimal energyKcalAll = new BigDecimal("0");
			// 全部蛋白质
			BigDecimal proteinAll = new BigDecimal("0");
			// 全部脂肪
			BigDecimal fat = new BigDecimal("0");
			// 全部碳水化合物
			BigDecimal carbohydrate = new BigDecimal("0");

			// 早餐热量
			BigDecimal zaoZaoKcal = new BigDecimal("0");
			// 午餐热量
			BigDecimal zaoWuKcal = new BigDecimal("0");
			// 晚餐热量
			BigDecimal zaoWanKcal = new BigDecimal("0");
			// 加餐热量
			BigDecimal zaoJiaKcal = new BigDecimal("0");

			/*
			 * 谷薯及杂豆类 蛋类 奶类及制品 蔬菜和菌藻 坚果、大豆及制品 饮料 食用油、油脂及制品 调味品 零食、点心、冷饮 菜肴 其它
			 * 畜禽肉类 水果 水产品 罐头
			 */

			BigDecimal type1Kacl = new BigDecimal("0");
			BigDecimal type2Kacl = new BigDecimal("0");
			BigDecimal type3Kacl = new BigDecimal("0");
			BigDecimal type4Kacl = new BigDecimal("0");
			BigDecimal type5Kacl = new BigDecimal("0");
			BigDecimal type6Kacl = new BigDecimal("0");
			BigDecimal type7Kacl = new BigDecimal("0");
			BigDecimal type8Kacl = new BigDecimal("0");
			BigDecimal type9Kacl = new BigDecimal("0");
			BigDecimal type10Kacl =new BigDecimal("0");
			BigDecimal type11Kacl =new BigDecimal("0");
			BigDecimal type12Kacl =new BigDecimal("0");
			BigDecimal type13Kacl =new BigDecimal("0");
			BigDecimal type14Kacl =new BigDecimal("0");
			BigDecimal type15Kacl =new BigDecimal("0");

			// 本周推荐摄入与实际摄入
			String recommend = "";

			for (YhFoodUserDiary yhFoodUserDiary : findWeekInfo) {
				if (StringUtil.isEmpty(yhFoodUserDiary.getfElements())) {

					continue;
				}

				Long energyLevel = yhFoodUserDiary.getEnergyLevel();
				BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
				recommend = recommend + yhFoodUserDiary.getcTime() + "," + energyLevel + "," + energyKcal + ";";
				proteinAll = proteinAll.add(new BigDecimal(yhFoodUserDiary.getProtein()));

				fat = fat.add(new BigDecimal(yhFoodUserDiary.getFat()));

				carbohydrate = carbohydrate.add(new BigDecimal(yhFoodUserDiary.getCho()));

				energyKcalAll = energyKcalAll.add(energyKcal);
				if (energyKcal.compareTo(new BigDecimal(energyLevel))==1) {
					exceedingDays++;
				}
				String getfElements = yhFoodUserDiary.getfElements();
				String[] split = getfElements.split(";");
				for (String string2 : split) {
					String[] split2 = string2.split("_");
					if (split2.length == 3) {
						continue;
					}
					String string5 = split2[0];
					// 餐次类型
					String string4 = split2[1];
					// 食物id
					String string3 = split2[2];
					// 克数
					String string7 = "";
					if ("10".equals(string5)) {
						String[] split3 = string2.split(",");
						String string6 = split3[0];
						String[] split4 = string6.split("_");
						// 克数
						string7 = split4[3];
					} else {
						// 克数
						string7 = split2[3];
					}

					YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(string3));
					if (null == foodInfo) {
						continue;
					}

					// 食物类型
					String getfType = foodInfo.getfType();

					// 食物每100克热量
					BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());


					BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string7));

					if ("1".equals(getfType)) {
						type1Kacl = type1Kacl.add(rijiKacl);
					}
					if ("2".equals(getfType)) {
						type2Kacl = type2Kacl.add(rijiKacl);
					}
					if ("3".equals(getfType)) {
						type3Kacl = type3Kacl.add(rijiKacl);
					}
					if ("4".equals(getfType)) {
						type4Kacl = type4Kacl.add(rijiKacl);
					}
					if ("5".equals(getfType)) {
						type5Kacl = type5Kacl.add(rijiKacl);
					}
					if ("6".equals(getfType)) {
						type6Kacl = type6Kacl.add(rijiKacl);
					}
					if ("7".equals(getfType)) {
						type7Kacl = type7Kacl.add(rijiKacl);
					}
					if ("8".equals(getfType)) {
						type8Kacl = type8Kacl.add(rijiKacl);
					}
					if ("9".equals(getfType)) {
						type9Kacl = type9Kacl.add(rijiKacl);
					}
					if ("10".equals(getfType)) {
						type10Kacl = type10Kacl.add(rijiKacl);
					}
					if ("11".equals(getfType)) {
						type11Kacl = type11Kacl.add(rijiKacl);
					}
					if ("12".equals(getfType)) {
						type12Kacl = type12Kacl.add(rijiKacl);
					}
					if ("13".equals(getfType)) {
						type13Kacl = type13Kacl.add(rijiKacl);
					}
					if ("14".equals(getfType)) {
						type14Kacl = type14Kacl.add(rijiKacl);
					}
					if ("15".equals(getfType)) {
						type15Kacl = type15Kacl.add(rijiKacl);
					}

					if ("1".equals(string4)) {
						zaoZaoKcal = zaoZaoKcal.add(rijiKacl);
					}
					if ("2".equals(string4)) {
						zaoWuKcal = zaoWuKcal.add(rijiKacl);
					}
					if ("3".equals(string4)) {
						zaoWanKcal = zaoWanKcal.add(rijiKacl);
					}
					if ("4".equals(string4)) {
						zaoJiaKcal = zaoJiaKcal.add(rijiKacl);
					}

					classSet.add(string3);

				}

			}

			yhUserFoodWeekly.setRecommend(recommend);
			// 超标天数
			yhUserFoodWeekly.setExceedingdays(exceedingDays);
			// 不重复食材
			yhUserFoodWeekly.setNorepetition(classSet.size());
			List<String> weekList = weekList();

			BigDecimal weekValue1 = new BigDecimal("0");

			for (String string2 : weekList) {
				String tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTimeAndType2(string, string2);
				if (StringUtil.isEmpty(tuijianNum)) {
					tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTime(string);
					if (StringUtil.isEmpty(tuijianNum)) {
						tuijianNum = YhFoodSrecipeRecordAppServiceImpl.tuijianNum(string);
					}
				}
				weekValue1 = weekValue1.add(new BigDecimal(tuijianNum));
			}

			// 7天应该平均摄入
			yhUserFoodWeekly.setAverageintake7(weekValue1.divide(new BigDecimal(7),2,BigDecimal.ROUND_HALF_UP).toString());
			// 实际平均
			yhUserFoodWeekly.setAverageintake(energyKcalAll.divide(new BigDecimal(findWeekInfo.size()),2,BigDecimal.ROUND_HALF_UP).toString());
			yhUserFoodWeekly.setProtein(proteinAll.toString());
			yhUserFoodWeekly.setFat(fat.toString());
			yhUserFoodWeekly.setCarbohydrate(carbohydrate.toString());

			yhUserFoodWeekly.setZao(zaoZaoKcal.toString());
			yhUserFoodWeekly.setWu(zaoWuKcal.toString());
			yhUserFoodWeekly.setWan(zaoWanKcal.toString());
			yhUserFoodWeekly.setJia(zaoJiaKcal.toString());
			yhUserFoodWeekly.setType1(type1Kacl.toString());
			yhUserFoodWeekly.setType2(type2Kacl.toString());
			yhUserFoodWeekly.setType3(type3Kacl.toString());
			yhUserFoodWeekly.setType4(type4Kacl.toString());
			yhUserFoodWeekly.setType5(type5Kacl.toString());
			yhUserFoodWeekly.setType6(type6Kacl.toString());
			yhUserFoodWeekly.setType7(type7Kacl.toString());
			yhUserFoodWeekly.setType8(type8Kacl.toString());
			yhUserFoodWeekly.setType9(type9Kacl.toString());
			yhUserFoodWeekly.setType10(type10Kacl.toString());
			yhUserFoodWeekly.setType11(type11Kacl.toString());
			yhUserFoodWeekly.setType12(type12Kacl.toString());
			yhUserFoodWeekly.setType13(type13Kacl.toString());
			yhUserFoodWeekly.setType14(type14Kacl.toString());
			yhUserFoodWeekly.setType15(type15Kacl.toString());

			insertSelective = insertSelective + YhUserFoodWeeklyWebMapper.insertSelective(yhUserFoodWeekly);
			System.out.println(yhUserFoodWeekly.getWid());
			YhUserNotifyVo record = new YhUserNotifyVo();
			record.setTitle("膳食报告");
			record.setContent("您的膳食报告，已生成立即查看详情>>");
			record.setUserId(yhUserFoodWeekly.getUserCode());
			record.setIsRead(false);
			record.setSendDate(new Date());
			record.setToServiceId(2);
			record.setNewsId(yhUserFoodWeekly.getWid());
			YhUserNotifyMapper.insertSelective(record);
		}

		return insertSelective;
	}

	public List<String> weekList() {
		List<String> list = new ArrayList<String>();
		String week1 = weekBegin();
		String week2 = getTuesday();
		String week3 = getWednesday();
		String week4 = getThursday();
		String week5 = getFriday();
		String week6 = getSaturday();
		String week7 = weekEnd();
		list.add(week1);
		list.add(week2);
		list.add(week3);
		list.add(week4);
		list.add(week5);
		list.add(week6);
		list.add(week7);
		return list;
	}

	public static String weekBegin() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayWeek == 1) {
			dayWeek = 8;
		}

		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
		Date mondayDate = cal.getTime();
		String weekBegin = sdf.format(mondayDate);
		System.out.println("所在周星期一的日期：" + weekBegin);
		return weekBegin;
	}

	// 周二
	public String getTuesday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);// 周二
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周三
	 * 
	 * @return
	 */
	public String getWednesday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);// 周三
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周四
	 * 
	 * @return
	 */
	public String getThursday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);// 周四
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周五
	 * 
	 * @return
	 */
	public String getFriday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);// 周五
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周六
	 * 
	 * @return
	 */
	public String getSaturday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);// 周六
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	public static String weekEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayWeek == 1) {
			dayWeek = 8;
		}
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		Date mondayDate = cal.getTime();
		String weekBegin = sdf.format(mondayDate);

		cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
		Date sundayDate = cal.getTime();
		String weekEnd = sdf.format(sundayDate);

		System.out.println("所在周星期日的日期：" + weekEnd);
		return weekEnd;

	}

	/**
	 * 获取当前时间所在周的周一和周日的日期时间
	 * 
	 * @return
	 */
	public static Map<String, String> getWeekDate() {
		Map<String, String> map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayWeek == 1) {
			dayWeek = 8;
		}

		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		Date mondayDate = cal.getTime();
		String weekBegin = sdf.format(mondayDate);

		cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
		Date sundayDate = cal.getTime();
		String weekEnd = sdf.format(sundayDate);
		System.out.println(weekBegin);
		System.out.println(weekEnd);
		map.put("mondayDate", weekBegin);
		map.put("sundayDate", weekEnd);
		return map;
	}

}
