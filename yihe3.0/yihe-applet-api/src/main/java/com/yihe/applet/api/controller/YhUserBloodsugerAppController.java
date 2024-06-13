package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserBloodsugerApp;
import com.yihe.applet.api.service.YhUserBloodsugerAppService;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据监测血糖 Created by dyb on 2019/12/20.
 */
@Controller
@RequestMapping("/api/YhUserBloodsuger")
@Api(value = "YhUserBloodsuger控制器", description = "YhUserBloodsuger管理")
public class YhUserBloodsugerAppController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBloodsugerAppController.class);

	@Autowired
	YhUserBloodsugerAppService yhUserBloodsugerService;
	@Autowired
	YhHeThresholdService yhHeThresholdService;


	public String NowString() {
		SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	@RequestMapping(value = "/dataAnalysis")
	@ResponseBody
	public Result dataAnalysis(String usercode,Integer timeType,String time,String type) throws java.text.ParseException {
		Map<String, Object> map = new HashMap<>();
		Set<String> timeList = new HashSet<>();
		if(0==timeType) {
			String[] split = time.split("~");
			List<YhUserBloodsugerApp> list = yhUserBloodsugerService.findBloodsugerAllByUserCode(usercode,timeType,split[0],split[1],type);
			if(null!=list){
				double k=0;
				for (int i = 0; i < list.size(); i++) {
					YhUserBloodsugerApp yhUserBloodsugerApp = list.get(i);
					String entryTime = yhUserBloodsugerApp.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					Float bs = yhUserBloodsugerApp.getBs();
					String env = yhUserBloodsugerApp.getEnv();
					if("空腹".equals(env)) {
						if(bs>=3.9&&bs<6.1) {
							k++;
						}
					}else {
						if(bs<7.8) {
							k++;
						}
					}
				}
				map.put("dayNum", timeList.size());
				map.put("size", list.size());
				double j = k / list.size() * 100;
				map.put("avg", Math.round(j));
			}
		}else{
			String[] split = time.split("~");
			List<YhUserBloodsugerApp> list = yhUserBloodsugerService.findBloodsugerAllByUserCode(usercode,timeType,time,null,type);
			if(null!=list){
				double k=0;
				for (int i = 0; i < list.size(); i++) {
					YhUserBloodsugerApp yhUserBloodsugerApp = list.get(i);
					String entryTime = yhUserBloodsugerApp.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					Float bs = yhUserBloodsugerApp.getBs();
					String env = yhUserBloodsugerApp.getEnv();
					if("空腹".equals(env)) {
						if(bs>=3.9&&bs<6.1) {
							k++;
						}
					}else {
						if(bs<7.8) {
							k++;
						}
					}
				}
				map.put("dayNum", timeList.size());
				map.put("size", list.size());
				double j = k / list.size() * 100;
				map.put("avg", Math.round(j));
			}
		}

		return Result.ok(map);

	}
	/*
	 * 饼图
	 */
	@RequestMapping(value = "/pieChartAssignment")
	@ResponseBody
	public Result pieChartAssignment(String type,String usercode,Integer timetype,String time,Integer page) {
		if(StringUtil.isEmpty(usercode)){
			return Result.error();
		}
		if(0==timetype){
			String[] split = time.split("~");
			int dixuetang=0;
			int zhengchang=0;
			int qianqi=0;
			int gaoxuetang=0;
			List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid = yhUserBloodsugerService.selectByPrimaryKeyBYeid2(usercode,timetype,split[0],split[1],page,type);
			if(null!=selectByPrimaryKeyBYeid) {
				for (int i = 0; i < selectByPrimaryKeyBYeid.size(); i++) {
					YhUserBloodsugerApp yhUserBloodsugerApp = selectByPrimaryKeyBYeid.get(i);
					Float bs = yhUserBloodsugerApp.getBs();
					String env = yhUserBloodsugerApp.getEnv();
					if(bs<3.9) {
						dixuetang++;
					}
					if("空腹".equals(env)) {
						if(bs>=3.9&&bs<6.1) {
							zhengchang++;
						}
						if(bs>=6.1&&bs<7) {
							qianqi++;
						}
						if(bs>=7) {
							gaoxuetang++;
						}

					}else {
						if(bs<7.8) {
							zhengchang++;
						}
						if(bs>=7.8&&bs<11.1) {
							qianqi++;
						}
						if(bs>=11.1) {
							gaoxuetang++;
						}
					}



				}
			}

			Map<String, Object> map =new HashMap<String, Object>();
			map.put("dixuetang", dixuetang);
			map.put("zhengchang", zhengchang);
			map.put("qianqi", qianqi);
			map.put("gaoxuetang", gaoxuetang);
			return Result.ok(map);
		}else if(1==timetype){
			int dixuetang=0;
			int zhengchang=0;
			int qianqi=0;
			int gaoxuetang=0;
			List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid = yhUserBloodsugerService.selectByPrimaryKeyBYeid2(usercode,timetype,time,null,page,type);
			if(null!=selectByPrimaryKeyBYeid) {
				for (int i = 0; i < selectByPrimaryKeyBYeid.size(); i++) {
					YhUserBloodsugerApp yhUserBloodsugerApp = selectByPrimaryKeyBYeid.get(i);
					Float bs = yhUserBloodsugerApp.getBs();
					String env = yhUserBloodsugerApp.getEnv();
					if(bs<3.9) {
						dixuetang++;
					}
					if("空腹".equals(env)) {
						if(bs>=3.9&&bs<6.1) {
							zhengchang++;
						}
						if(bs>=6.1&&bs<7) {
							qianqi++;
						}
						if(bs>=7) {
							gaoxuetang++;
						}

					}else {
						if(bs<7.8) {
							zhengchang++;
						}
						if(bs>=7.8&&bs<11.1) {
							qianqi++;
						}
						if(bs>=11.1) {
							gaoxuetang++;
						}
					}



				}
			}

			Map<String, Object> map =new HashMap<String, Object>();
			map.put("dixuetang", dixuetang);
			map.put("zhengchang", zhengchang);
			map.put("qianqi", qianqi);
			map.put("gaoxuetang", gaoxuetang);
			return Result.ok(map);
		}else if(2==timetype){
			int dixuetang=0;
			int zhengchang=0;
			int qianqi=0;
			int gaoxuetang=0;
			List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid = yhUserBloodsugerService.selectByPrimaryKeyBYeid2(usercode,timetype,time,null,page,type);
			if(null!=selectByPrimaryKeyBYeid) {
				for (int i = 0; i < selectByPrimaryKeyBYeid.size(); i++) {
					YhUserBloodsugerApp yhUserBloodsugerApp = selectByPrimaryKeyBYeid.get(i);
					Float bs = yhUserBloodsugerApp.getBs();
					String env = yhUserBloodsugerApp.getEnv();
					if(bs<3.9) {
						dixuetang++;
					}
					if("空腹".equals(env)) {
						if(bs>=3.9&&bs<6.1) {
							zhengchang++;
						}
						if(bs>=6.1&&bs<7) {
							qianqi++;
						}
						if(bs>=7) {
							gaoxuetang++;
						}

					}else {
						if(bs<7.8) {
							zhengchang++;
						}
						if(bs>=7.8&&bs<11.1) {
							qianqi++;
						}
						if(bs>=11.1) {
							gaoxuetang++;
						}
					}



				}
			}

			Map<String, Object> map =new HashMap<String, Object>();
			map.put("dixuetang", dixuetang);
			map.put("zhengchang", zhengchang);
			map.put("qianqi", qianqi);
			map.put("gaoxuetang", gaoxuetang);
			return Result.ok(map);
		}

		return Result.error();


	 }
	/**
	 * 时间段分布
	 */
	@RequestMapping(value = "/timePeriodDistribution")
	@ResponseBody
	public Result timePeriodDistribution(String type,String usercode,Integer timeType,String time,Integer page) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error();
		}
		if(0==timeType){
			String[] split = time.split("~");
			return Result.ok(yhUserBloodsugerService.findbloodsugerTimeFenBuWeek(split[0],split[1],usercode,page,type));
		}else if(1==timeType){
			return Result.ok(yhUserBloodsugerService.findbloodsugerTimeFenBuMonth(time,usercode,page,type));
		}else{
			return Result.ok(yhUserBloodsugerService.findbloodsugerTimeFenBuYear(time,usercode,page,type));
		}
	}
	
	/**
	 * 根据血糖主键查询
	 */
	@RequestMapping(value = "/findInfoByKey")
	@ResponseBody
	public Result findInfoByKey(Integer id) {
		if (null == id) {
			Result.error();
		}
		YhUserBloodsugerApp selectByPrimaryKey = yhUserBloodsugerService.selectByPrimaryKey(id);
		return Result.ok(selectByPrimaryKey);
	}

	/**
	 * 查询已记录日期List
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/findTimeList")
	@ResponseBody
	public Result findTimeList(String usercode) throws ParseException{
		if (null == usercode) {
			Result.error();
		}
		List<String> selectByPrimaryKeyFindEntryTime = yhUserBloodsugerService
				.selectByPrimaryKeyFindEntryTime(usercode);
		if(null==selectByPrimaryKeyFindEntryTime){
			return Result.ok();
		}
		for (int i = 0; i < selectByPrimaryKeyFindEntryTime.size(); i++) {
			String entryTime = selectByPrimaryKeyFindEntryTime.get(i);
			String transferFormat = transferFormat2(entryTime);
			selectByPrimaryKeyFindEntryTime.set(i,transferFormat);

		}
		return Result.ok(selectByPrimaryKeyFindEntryTime);
	}

	/**
	 * 根据时间和用户查询当日信息
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/findByUsercodeAndTime")
	@ResponseBody
	public Result findByUsercodeAndTime(String usercode, String time) {
		if (null == usercode || null == time) {
			Result.error();
		}
		List<YhUserBloodsugerApp> list = yhUserBloodsugerService
				.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
		if(null!=list){
			for (int i = 0; i < list.size(); i++) {
				YhUserBloodsugerApp yhUserBloodsugerApp = list.get(i);
				yhUserBloodsugerApp.setOpenNo(false);
				if(i==0){
					yhUserBloodsugerApp.setOpenNo(true);
				}
				String type=yhUserBloodsugerApp.getEnv();
				Float bsInfo = yhUserBloodsugerApp.getBs();
				if ("空腹".equals(type)) {
					if (bsInfo < 3.9) {
						yhUserBloodsugerApp.setJieduInfo("您的血糖属于低血糖了，出现低血糖的时候一般会有心慌、胸闷、头晕、出冷汗等症状，建议及时补充糖分。");
						yhUserBloodsugerApp.setTiwenInfo("低血糖");
						yhUserBloodsugerApp.setProgressvalueinfo(12.5);
					} else if (bsInfo >= 3.9 && bsInfo < 6.1) {
						yhUserBloodsugerApp.setJieduInfo("您的血糖正常，请继续保持健康的生活习惯。");
						yhUserBloodsugerApp.setTiwenInfo("正常");
						yhUserBloodsugerApp.setProgressvalueinfo(37.5);
					} else if (bsInfo >= 6.1 && bsInfo < 7) {
						yhUserBloodsugerApp.setJieduInfo("您的血糖异常但未达到糖尿病诊断标准！建议您定期监测血糖，改善生活方式、饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
						yhUserBloodsugerApp.setTiwenInfo("糖尿病前期");
						yhUserBloodsugerApp.setProgressvalueinfo(62.5);
					} else {
						yhUserBloodsugerApp.setJieduInfo("您的血糖已经达到糖尿病诊断标准了！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
						yhUserBloodsugerApp.setTiwenInfo("高血糖");
						yhUserBloodsugerApp.setProgressvalueinfo(87.5);
					}

				} else {
					  if (bsInfo < 7.8) {
						  yhUserBloodsugerApp.setJieduInfo("您的血糖正常，请继续保持健康的生活习惯。");
						  yhUserBloodsugerApp.setTiwenInfo("正常");
						  yhUserBloodsugerApp.setProgressvalueinfo(37.5);
					} else if (bsInfo >= 7.8 && bsInfo < 11.1) {
						  yhUserBloodsugerApp.setJieduInfo("您的血糖异常但未达到糖尿病诊断标准！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
						  yhUserBloodsugerApp.setTiwenInfo("糖尿病前期");
						  yhUserBloodsugerApp.setProgressvalueinfo(62.5);

					} else{

						  yhUserBloodsugerApp.setJieduInfo("您的血糖已经达到糖尿病诊断标准了！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
						  yhUserBloodsugerApp.setTiwenInfo("高血糖");
						  yhUserBloodsugerApp.setProgressvalueinfo(87.5);
					}

				}
			}
		}
		return Result.ok(list);
	}

	/**
	 * 修改血压数据
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/modifyBmiInfo")
	@ResponseBody
	public Result modifyBmiInfo(YhUserBloodsugerApp record) {
		yhUserBloodsugerService.updateByPrimaryKeySelective(record);
		return Result.ok();
	}

	@RequestMapping(value = "/addInfo")
	@ResponseBody
	public Result addInfo(YhUserBloodsugerApp record, HttpServletRequest request) {
		if(StringUtil.isNotEmpty(record.getEnv())){
			if("空腹".equals(record.getEnv())){
				try {
					yhHeThresholdService.selectBythresType("blood_sugar", String.valueOf(record.geteUserId()), String.valueOf(record.getBs()), "空腹血糖",request);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if("餐后".equals(record.getEnv())){
				try {
					yhHeThresholdService.selectBythresType("blood_sugar2", String.valueOf(record.geteUserId()), String.valueOf(record.getBs()), "餐后血糖",request);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 修改
		if (null != record.getuBloodsugerId() && 0 != record.getuBloodsugerId()) {
			record.setEntryTime(null);
			int updateByExampleSelective = yhUserBloodsugerService.updateByPrimaryKeySelective(record);

			return Result.ok(updateByExampleSelective);
		} else {
			System.out.println(record);
			record.setIsDel(2);

			record.setEntryTime(record.getEntryTime() + NowString());

			int insertSelective = yhUserBloodsugerService.insertSelective(record);

			if (insertSelective > 0) {
				return Result.ok(insertSelective);
			}
			return Result.error();
		}

	}

	@RequestMapping(value = "/findInfo")
	@ResponseBody
	public Result findInfo(String eid) {
		System.out.println(eid);

		List<YhUserBloodsugerApp> selectByPrimaryKeyByUserIdLimit7 = yhUserBloodsugerService
				.selectByPrimaryKeyByEidLimit7(eid);

		if (null != selectByPrimaryKeyByUserIdLimit7) {
			return Result.ok(selectByPrimaryKeyByUserIdLimit7);
		}

		return Result.error();
	}

	/**
	 * 查询最新血糖信息
	 * 
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/findBloodsugerInfoNew")
	@ResponseBody
	public Result findBloodsugerInfoNew(String eid) {
		System.out.println(eid);

		YhUserBloodsugerApp selectByPrimaryKeyNewest3 = yhUserBloodsugerService.selectByPrimaryKeyNewest(eid);// 血糖

		return Result.ok(selectByPrimaryKeyNewest3);
	}

	@RequestMapping(value = "/findMonthInfo")
	@ResponseBody
	public Result findMonthInfo(String eid, String ye, String mo) {

		if (null == ye && null == mo) {
			ye = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			mo = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		}
		String shijian = ye + "-" + mo + "-01";
		System.out.println(eid + "" + shijian);
		List<YhUserBloodsugerApp> monthInfoList = yhUserBloodsugerService.selectByPrimaryKeyByMonthInfo(eid, shijian);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < monthInfoList.size(); i++) {
			YhUserBloodsugerApp yhUserBmi = monthInfoList.get(i);
			if (null != yhUserBmi) {
				if (null != yhUserBmi.getEntryTime()) {
					String entryTime = yhUserBmi.getEntryTime();
					try {
						Date date = sf.parse(entryTime);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						list.add(day);

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (null != list) {
			LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(list);

			ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);

			return Result.ok(listWithoutDuplicates);
		}

		return Result.error();
	}

	@RequestMapping(value = "/findInfoByeidAll")
	@ResponseBody
	public Result findInfoByeidAll(String type, String usercode, @RequestParam(required = false,defaultValue = "0") Integer timeType, String time, Integer page) throws ParseException {
		int pageSize=7;
		Map<String,Object> map=new HashMap<>();
		List<String> timeList=new ArrayList<>();
		List<String> dataList=new ArrayList<>();
		if(2==timeType){
			List<YhUserBloodsugerApp> list = yhUserBloodsugerService.findBloodsugerByYear(type,time, usercode, (page - 1) * pageSize, pageSize);
			if(null!=list){
				for (YhUserBloodsugerApp yhUserBloodsuger :  list) {
					String entryTime = yhUserBloodsuger.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					dataList.add(String.valueOf(yhUserBloodsuger.getBs()));
				}
			}
			map.put("timeList",timeList);
			map.put("dataList",dataList);
			return Result.ok(map);
		}else if(1==timeType){
			List<YhUserBloodsugerApp> list = yhUserBloodsugerService.findBloodsugerByMonth(type,time, usercode, (page - 1) * pageSize, pageSize);
			if(null!=list){
				for (YhUserBloodsugerApp yhUserBloodsuger :  list) {
					String entryTime = yhUserBloodsuger.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					dataList.add(String.valueOf(yhUserBloodsuger.getBs()));
				}
			}
			map.put("timeList",timeList);
			map.put("dataList",dataList);
			return Result.ok(map);
		}else  if(0==timeType){
			String[] split = time.split("~");
			List<YhUserBloodsugerApp> list = yhUserBloodsugerService.findBloodsugerByWeek(type,split[0],split[1], usercode, (page - 1) * pageSize, pageSize);
			if(null!=list){
				for (YhUserBloodsugerApp yhUserBloodsuger :  list) {
					String entryTime = yhUserBloodsuger.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					dataList.add(String.valueOf(yhUserBloodsuger.getBs()));
				}
			}
			map.put("timeList",timeList);
			map.put("dataList",dataList);
			return Result.ok(map);
		}
		return Result.error();
	}

	public static String transferFormat2(String inTime) throws ParseException {
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

		Date tempDate = null;
		String outTime = null;
		tempDate = s1.parse(inTime);
		outTime = s1.format(tempDate);
		return outTime;
	}

	public static String transferFormat(String inTime) throws ParseException {
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
		Date tempDate = null;
		String outTime = null;
		tempDate = s1.parse(inTime);
		outTime = s2.format(s2.parse(s1.format(tempDate)));
		return outTime;
	}

	public static void main(String[] args) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// try {
		// Date date = sf.parse("2015-12-01");
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		// System.out.println(calendar.get(Calendar.YEAR));
		// System.out.println(calendar.get(Calendar.MONTH) + 1);
		// System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		//
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));
	}

}