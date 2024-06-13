package com.yihe.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 睡眠评估算法
 * 
 * @author lll
 * @Email:2982564698@qq.com
 * @phone:17608493351
 * @date 创建时间：2020年4月7日 下午12:38:55
 * @version 1.0
 */
public class SleepScoreUtil {
	public static int sq = 0;
	public static int st = 0;
	public static int hs = 0;
	public static int sd = 0;
	public static int cd = 0;
	public static int dd = 0;
	public static int se = 0;
	public static String endTime = null;
	public static String startTime = null;
	public static LinkedHashMap<String, Object> retmap = new LinkedHashMap<String, Object>();
	public static List<HashMap<String, Object>> changesList = new ArrayList<HashMap<String, Object>>();
	// PSQI总分
	public static int PSQI = 0;
	/**
	 * @Title: queryEvaluateScore @Description:
	 *         PSQI用于评定填写问卷者最近1个月的睡眠质量,共由7个成分 @param @param queList @param @param
	 *         type @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static LinkedHashMap<String, Object> querySleepScore(String queCheck) {
		sq = 0;
		st = 0;
		hs = 0;
		sd = 0;
		cd = 0;
		dd = 0;
		se = 0;
		endTime = null;
		startTime = null;
		retmap = new LinkedHashMap<String, Object>();
		changesList = new ArrayList<HashMap<String, Object>>();
		// PSQI总分
		PSQI = 0;
		String ques[] = queCheck.split(";");
		for (int i = 0; i < ques.length; i++) {
			if (StringUtils.isNotEmpty(ques[i])) {
				doCompute(i + 1, ques[i]);
			}
		}
		if (!isDate(endTime) || !isDate(startTime)) {
			retmap.put("code", "500");
			retmap.put("data", "时间格式错误");
			return retmap;
		}
		// 得到实际睡眠时长
		int differTime = (int) getDifferTime(endTime, startTime);
		int sw = 0;
		// 得到4睡眠效率分值
		switch (hs) {
		case 0:
			sw = (int) ((float) 7.5 / differTime * 100);
			break;
		case 1:
			sw = (int) ((float) 6.5 / differTime * 100);
			break;
		case 2:
			sw = (int) ((float) 5.5 / differTime * 100);
			break;
		case 3:
			sw = (int) ((float) 4 / differTime * 100);
			break;
		}
		// 判断睡眠效率得分
		se = doSleepWorkpiece(sw);
		sd = doSleepObstacle(sd);
		// PSQI总分
		PSQI = se + sq + st + sd + cd + dd + hs;
		// 计算睡眠质量评分 100 – PSQI得分 / 21 *100
		int score = (int) (100 - ((float) PSQI / 21) * 100);
		String sleepLevel = getSleepLevel(score);
		getSleepNote(sq);
		getFallSleepTime(st);
		getSleepTime(hs);
		getSleepWorkpiece(se);
		getSleepObstacle(sd);
		getHypnotic(cd);
		getDaytimeDysfunction(dd);
		if(changesList.size()==0) {
			ArrayList<SleepScore> sList = new ArrayList<SleepScore>();
			SleepScore s1 = new SleepScore();
			HashMap<String, Object> sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("");
			s1.setSleepContent("");
			sList.add(s1);
			sleepList.put("sleepExistingProblem", "总体来看睡眠状态和质量不错，优质的睡眠可以缓解身心疲劳，增强免疫；精进作息习惯及日常三餐，健康活力每一天");
			String json=JSON.toJSONString(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
		retmap.put("score", score);
		retmap.put("sleepLevel", sleepLevel);
		retmap.put("data", changesList);
		retmap.put("code", 0);
		return retmap;
	}

	// 获取
	public static void doCompute(int i, String value) {
		switch (i) {
		case 1:
			startTime = value;
			break;
		case 15:
			// 判断1睡眠质量分数
			sq = getint(value);
			break;
		case 2:
		case 5:
			// 判断2入睡时间分数
			st += getint(value);
			if (st == 0) {
				st = 0;
			} else if (st >= 1 && st <= 2) {
				st = 1;
			} else if (st >= 3 && st <= 4) {
				st = 2;
			} else {
				st = 3;
			}
			break;
		case 3:
			endTime = value;
			break;
		case 4:
			// 判断3睡眠时间分数
			hs = getint(value);
			break;
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			// 判断5睡眠障碍分数
			sd += getint(value);
			break;
		case 16:
			// 判断6催眠药物分数
			cd += getint(value);
			break;
		case 17:
		case 18:
			// 判断7日间功能障碍分数
			dd += getint(value);
			if (dd == 0) {
				dd = 0;
			} else if (dd >= 1 && dd <= 2) {
				dd = 1;
			} else if (dd >= 3 && dd <= 4) {
				dd = 2;
			} else {
				dd = 3;
			}
			break;
		}
	}

	/**
	 * @Description: 获取睡眠质量 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getSleepNote(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		SleepScore s2 = null;
		if (s_score >= 2) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			s2 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("助眠生活方式");
			s1.setSleepContent("调整睡眠姿势：左侧卧、俯卧等不合理的睡姿，易导致肩颈背部的疼痛，压迫内脏器官同时带来呼吸不畅的等问题。调整为仰卧或右侧卧，可有效改善睡眠质量。调节睡眠环境：睡眠的好坏与睡眠环境关系密切，一般在15至24度的温度中，可获得安睡。");
			s2.setSleepTitle("饮食营养建议");
			s2.setSleepContent("补充钙镁食物：钙、镁是深度睡眠的重要矿物质，镁可缓解肌肉紧张，促进褪黑素分泌，有助于进入深度睡眠状态；而钙缺乏则会干扰深度睡眠，降低睡眠质量。日常可选牛奶、豆腐等补充钙质，选择菠菜生菜等补充镁。");
			sList.add(s1);
			sList.add(s2);
			sleepList.put("sleepExistingProblem", "睡眠质量不佳");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}

	}

	/**
	 * @Description: 获取入睡时间 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getFallSleepTime(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		SleepScore s2 = null;
		SleepScore s3 = null;
		if (s_score > 1) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			s2 = new SleepScore();
			s3 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("助眠生活方式");
			s1.setSleepContent("冥想：冥想通过转移注意力和放松身体来让你放下那些纷乱的思绪，使大脑得到休息，从而减少焦虑和身体紧张对睡眠的影响。");
			s2.setSleepTitle("饮食营养建议");
			s2.setSleepContent("补充钙镁食物：钙、镁是深度睡眠的重要矿物质，镁可缓解肌肉紧张，促进褪黑素分泌，有助于进入深度睡眠状态；而钙缺乏则会干扰深度睡眠，降低睡眠质量。日常可选牛奶、豆腐等充不钙质，选择菠菜生菜等补充镁。");
			s3.setSleepTitle("睡眠节律调整");
			s3.setSleepContent("跟着呼吸节奏入睡：睡前深呼吸，帮助身体放松，大脑放空，调动人体的副交感神经");
			sList.add(s1);
			sList.add(s2);
			sList.add(s3);
			sleepList.put("sleepExistingProblem", "入睡困难");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
	}

	/**
	 * @Description: 获取睡眠时间 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getSleepTime(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		if (s_score > 1) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("睡眠节律调整");
			s1.setSleepContent("规律作息：身体需要通过夜间睡眠来进行调节，规律的入睡和起床时间不但可以让你获得充足的休息，还可以调节身体状态，提高工作效率，进入良性循环。黄金睡眠时段是22：00——07：00，如果条件允许的话，尽量按照这个时间吧");
			sList.add(s1);
			sleepList.put("sleepExistingProblem", "睡眠不足");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
	}

	/**
	 * @Description: 获取睡眠效率 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getSleepWorkpiece(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		if (s_score > 1) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("助眠生活方式");
			s1.setSleepContent("晒太阳：若白天接受日照不足，极易引起睡眠紊乱。强光能够有效地诱导体内的昼夜节律重新设定，帮助调节和恢复生物钟。晴天可以安排20分钟晒太阳");
			sList.add(s1);
			sleepList.put("sleepExistingProblem", "睡眠效率低");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
	}

	/**
	 * @Description: 获取睡眠障碍 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getSleepObstacle(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		if (s_score > 1) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("医生干预");
			s1.setSleepContent("需要注意去除引起睡眠障碍的基础疾病，可以在相关科室医生的指导下进行规范的治疗，减轻不适的症状，从而达到改善睡眠的目的。平时还需要注意放松心情，养成规律作息的习惯。");
			sList.add(s1);
			sleepList.put("sleepExistingProblem", "睡眠维持障碍");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
	}

	/**
	 * @Description: 获取催眠药物 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getHypnotic(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		if (s_score >= 2) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("医生干预");
			s1.setSleepContent("失眠多数是由心理问题引起的，可以多出去走走，放松压力，调整心态。当失眠症状持续三个月以上形成了慢性失眠障碍，这种情况一定要治疗，否则将会引起一系列的不良后果。");
			sList.add(s1);
			sleepList.put("sleepExistingProblem", "依赖药物");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
	}

	/**
	 * @Description: 获取日间功能障碍 @param @param s_score @param @return 设定文件 @return
	 *               Map<String, Object> 返回类型 @throws
	 */
	public static void getDaytimeDysfunction(int s_score) {
		List<SleepScore> sList = null;
		Map<String, Object> sleepList = null;
		SleepScore s1 = null;
		if (s_score >= 2) {
			sList = new ArrayList<SleepScore>();
			s1 = new SleepScore();
			sleepList = new HashMap<String, Object>();
			s1.setSleepTitle("助眠生活方式");
			s1.setSleepContent("午间充电：借助午休弥补由于夜间失眠造成的影响，每天午睡30分钟，在饭后半小时再入睡，不宜超过60分钟。午睡不仅能提高工作效率还能预防冠心病");
			sList.add(s1);
			sleepList.put("sleepExistingProblem", "日间精力不足");
			String json=JSON.toJSONString	(sList);
			sleepList.put("dataList", json);
			changesList.add((HashMap<String, Object>) sleepList);
		}
	}

	/**
	 * @Description: 根据分数计算睡眠评估等级 @param @param s_score @param @return 设定文件 @return
	 *               String 返回类型 @throws
	 */
	public static String getSleepLevel(int s_score) {
		String sleepflag = null;
		if (s_score < 60) {
			sleepflag = "差";
		} else if (s_score >= 60 && s_score < 80) {
			sleepflag = "一般";
		} else {
			sleepflag = "良好";
		}
		return sleepflag;
	}

	/**
	 * @Description: 根据分数计算睡眠障碍 @param @param s_score @param @return 设定文件 @return
	 *               String 返回类型 @throws
	 */
	public static int doSleepObstacle(int sw) {
		if (sw == 0) {
			sd = 0;
		} else if (sw >= 1 && sw < 9) {
			sd = 1;
		} else if (sw >= 10 && sw < 18) {
			sd = 2;
		} else {
			sd = 3;
		}
		return sd;
	}

	/**
	 * @Description: 根据分数计算睡眠效率 @param @param s_score @param @return 设定文件 @return
	 *               String 返回类型 @throws
	 */
	public static int doSleepWorkpiece(int sw) {
		if (sw > 85) {
			se = 0;
		} else if (sw >= 75 && sw < 85) {
			se = 1;
		} else if (sw >= 65 && sw < 75) {
			se = 2;
		} else {
			se = 3;
		}
		return se;
	}

	public static int getint(String s) {
		int i = 0;
		if (s == null)
			return 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
		return i;
	}

	/**
	 * @Title: isDate @Description: 判断日期格式是否正确 @param @param strDate @param @return
	 *         设定文件 @return boolean 返回类型 @throws
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile("^([0-1]?[0-9]|2[0-3]):([0-5][0-9])$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算差多少个小时
	 */
	public static long getDifferTime(String endTime, String nowTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");// yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
		Date endDate = null;
		Date nowDate = null;
		try {
			endDate = sdf.parse(endTime);
			nowDate = sdf.parse(nowTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少小时
		long hour = diff % nd / nh;
		if (hour < 0) {
			hour = 24 + hour;
		}
		return hour;
	}

	public static void main(String[] args) {
		String queCheck = "22:00;0;7:00;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0";
		String json=JSON.toJSONString	(querySleepScore(queCheck));
		System.out.println(json);
		/*
		 * DecimalFormat df = new DecimalFormat("0.00");//格式化小数 String format =
		 * df.format((float)7/8*100);//返回的是String类型 System.out.println((int)((float)7.5
		 * / 8*100));
		 */
	}
}
