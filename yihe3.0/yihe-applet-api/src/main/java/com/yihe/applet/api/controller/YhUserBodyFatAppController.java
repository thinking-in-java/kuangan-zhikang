package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.yihe.applet.api.domain.YhUserHeartRateApp;
import com.yihe.applet.api.domain.YhUserTemperatureApp;
import com.yihe.system.service.YhHeThresholdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserBodyFatApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.service.YhUserBodyFatAppService;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;

/**
 * 体脂的数据监测 Created by dyb on 2019/12/23.
 */
@Controller
@RequestMapping("/api/YhUserBodyFat")
@Api(value = "YhUserBodyFat控制器", description = "YhUserBodyFat管理")
public class YhUserBodyFatAppController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBodyFatAppController.class);

	@Autowired
	YhUserBodyFatAppService yhUserBodyFatService;
	@Autowired
	YhUserReginfoAppService YhUserReginfoService;

	@RequestMapping(value = "/dataAnalysis")
	@ResponseBody
	public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
		Map<String, Object> map = new HashMap<>();
		Set<String> timeList = new HashSet<>();
		YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
		if (null == findYhUserReginfo) {
			return Result.error("参数异常");
		}
		Integer gender = findYhUserReginfo.getGender();
		if(0==timeType) {
			String[] split = time.split("~");
			List<YhUserBodyFatApp> list = yhUserBodyFatService.findBodyFatAllByUserCode(usercode,timeType,split[0],split[1]);
			if(null!=list){
				double k=0;
				for (int i = 0; i < list.size(); i++) {
					YhUserBodyFatApp yhUserBodyFatApp = list.get(i);
					String entryTime = yhUserBodyFatApp.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					Float fatRate = yhUserBodyFatApp.getFatRate();
					if(gender==1){
						if(fatRate>=15&&fatRate<=18){
							k++;
						}
					}else{
						if(fatRate>=20&&fatRate<=25){
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
			List<YhUserBodyFatApp> list = yhUserBodyFatService.findBodyFatAllByUserCode(usercode,timeType,time,null);
			if(null!=list){
				double k=0;
				for (int i = 0; i < list.size(); i++) {
					YhUserBodyFatApp yhUserBodyFatApp = list.get(i);
					String entryTime = yhUserBodyFatApp.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					Float fatRate = yhUserBodyFatApp.getFatRate();
					if(gender==1){
						if(fatRate>=15&&fatRate<=18){
							k++;
						}
					}else{
						if(fatRate>=20&&fatRate<=25){
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

	/**
	 * 体脂称对接添加
	 * @param bodyfat
	 * @return
	 */
	@RequestMapping(value = "/bodyfatinsertSelective")
	@ResponseBody
	public Result bodyfatinsertSelective(YhUserBodyFatApp bodyfat) {
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		bodyfat.setEntryTime(df.format(day));
		bodyfat.setType("有品体脂称");
		bodyfat.setSource(2);
		int i=yhUserBodyFatService.insertSelective(bodyfat);
		return Result.ok(i);
	}
	
	
	/*
	 * 饼图
	 */
	@RequestMapping(value = "/pieChartAssignment")
	@ResponseBody
	public Result pieChartAssignment(String usercode,Integer timetype,String time,Integer page) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error();
		}
		if(0==timetype){
			String[] split = time.split("~");
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
			if(null==findYhUserReginfo) {
				return Result.error("参数异常");
			}
			Integer gender = findYhUserReginfo.getGender();
			int piandi=0;
			int zhengchang=0;
			int piangao=0;
			List<YhUserBodyFatApp> selectByPrimaryKeyByEidLimit7 = yhUserBodyFatService.selectByPrimaryKeyByEidLimit7(usercode,timetype,split[0],split[1],page);
			if(null!=selectByPrimaryKeyByEidLimit7) {
				for (int i = 0; i < selectByPrimaryKeyByEidLimit7.size(); i++) {
					YhUserBodyFatApp yhUserBodyFatApp = selectByPrimaryKeyByEidLimit7.get(i);
					Float fatRate = yhUserBodyFatApp.getFatRate();
					if(gender==1) {
						if(fatRate<15) {
							piandi++;
						}
						if(fatRate>=15&&fatRate<=18) {
							zhengchang++;
						}
						if(fatRate>18) {
							piangao++;
						}
					}else {
						if(fatRate<20) {
							piandi++;
						}
						if(fatRate>=20&&fatRate<=25) {
							zhengchang++;
						}
						if(fatRate>25) {
							piangao++;
						}
					}
				}
			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("piandi", piandi);
			map.put("zhengchang", zhengchang);
			map.put("piangao", piangao);
			return Result.ok(map);
		}else if(1==timetype){
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
			if(null==findYhUserReginfo) {
				return Result.error("参数异常");
			}
			Integer gender = findYhUserReginfo.getGender();
			int piandi=0;
			int zhengchang=0;
			int piangao=0;
			List<YhUserBodyFatApp> selectByPrimaryKeyByEidLimit7 = yhUserBodyFatService.selectByPrimaryKeyByEidLimit7(usercode,timetype,time,null,page);
			if(null!=selectByPrimaryKeyByEidLimit7) {
				for (int i = 0; i < selectByPrimaryKeyByEidLimit7.size(); i++) {
					YhUserBodyFatApp yhUserBodyFatApp = selectByPrimaryKeyByEidLimit7.get(i);
					Float fatRate = yhUserBodyFatApp.getFatRate();
					if(gender==1) {
						if(fatRate<15) {
							piandi++;
						}
						if(fatRate>=15&&fatRate<=18) {
							zhengchang++;
						}
						if(fatRate>18) {
							piangao++;
						}
					}else {
						if(fatRate<20) {
							piandi++;
						}
						if(fatRate>=20&&fatRate<=25) {
							zhengchang++;
						}
						if(fatRate>25) {
							piangao++;
						}
					}
				}
			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("piandi", piandi);
			map.put("zhengchang", zhengchang);
			map.put("piangao", piangao);
			return Result.ok(map);
		}else if(2==timetype){
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
			if(null==findYhUserReginfo) {
				return Result.error("参数异常");
			}
			Integer gender = findYhUserReginfo.getGender();
			int piandi=0;
			int zhengchang=0;
			int piangao=0;
			List<YhUserBodyFatApp> selectByPrimaryKeyByEidLimit7 = yhUserBodyFatService.selectByPrimaryKeyByEidLimit7(usercode,timetype,time,null,page);
			if(null!=selectByPrimaryKeyByEidLimit7) {
				for (int i = 0; i < selectByPrimaryKeyByEidLimit7.size(); i++) {
					YhUserBodyFatApp yhUserBodyFatApp = selectByPrimaryKeyByEidLimit7.get(i);
					Float fatRate = yhUserBodyFatApp.getFatRate();
					if(gender==1) {
						if(fatRate<15) {
							piandi++;
						}
						if(fatRate>=15&&fatRate<=18) {
							zhengchang++;
						}
						if(fatRate>18) {
							piangao++;
						}
					}else {
						if(fatRate<20) {
							piandi++;
						}
						if(fatRate>=20&&fatRate<=25) {
							zhengchang++;
						}
						if(fatRate>25) {
							piangao++;
						}
					}
				}
			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("piandi", piandi);
			map.put("zhengchang", zhengchang);
			map.put("piangao", piangao);
			return Result.ok(map);
		}
		return Result.error();
	}
	
	/**
	 * 时间段分布
	 */
	@RequestMapping(value = "/timePeriodDistribution")
	@ResponseBody
	public Result timePeriodDistribution(String usercode) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error();
		}
		return Result.ok(yhUserBodyFatService.findtemperatureTimeFenBu(usercode));
	}
	
	/**
	 * 根据主键查询体脂信息
	 */
	@RequestMapping(value = "/findInfoByKey")
	@ResponseBody
	public Result findInfoByKey(Integer id) {
		if (null == id) {
			return Result.error();
		}
		return Result.ok(yhUserBodyFatService.selectByPrimaryKey(id));
	}

	/**
	 * 查询最新体脂
	 */
	@RequestMapping(value = "/findBodyFatInfoNew")
	@ResponseBody
	public Result findBodyFatInfoNew(String eid) {
		if (null == eid) {
			return Result.error();
		}
		YhUserBodyFatApp selectByPrimaryKeyNewest4 = yhUserBodyFatService.selectByPrimaryKeyNewest(eid);// 体脂
		return Result.ok(selectByPrimaryKeyNewest4);
	}

	/**
	 * 根据时间和用户查询当日的信息
	 */

	@RequestMapping(value = "/findYhUserBodyFatByUserCodeAndTime")
	@ResponseBody
	public Result findYhUserBodyFatByUserCodeAndTime(String usercode, String time) {
		if (null == usercode || null == time) {
			Result.error();
		}
		List<YhUserBodyFatApp> list = yhUserBodyFatService
				.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
		YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
		if (null == findYhUserReginfo) {
			return Result.error("参数异常");
		}
		Integer gender = findYhUserReginfo.getGender();

		if(null!=list) {
			for (int i = 0; i < list.size(); i++) {
				YhUserBodyFatApp yhUserBodyFatApp = list.get(i);
				Float fatRate = yhUserBodyFatApp.getFatRate();
				yhUserBodyFatApp.setOpenNo(false);
				if (i==0){
					yhUserBodyFatApp.setOpenNo(true);
				}
				if(null!=yhUserBodyFatApp.getSource()&&yhUserBodyFatApp.getSource()==2){
					yhUserBodyFatApp.setTiwen("设备上传");
				}else{
					if (gender == 1) {
						if (fatRate < 15) {
							yhUserBodyFatApp.setJiedu("您的体脂偏低，请合理膳食结构，增强锻炼。");
							yhUserBodyFatApp.setTiwen("偏低");
							yhUserBodyFatApp.setProgressvalue(16.5);
						} else if (fatRate >= 15 && fatRate <= 18) {
							yhUserBodyFatApp.setJiedu("您的体脂正常，请继续保持健康的生活习惯。");
							yhUserBodyFatApp.setTiwen("正常");
							yhUserBodyFatApp.setProgressvalue(49.5);
						} else {
							yhUserBodyFatApp.setJiedu("您的体脂偏高，当身体每天摄入的热量小于消耗的热量，就会瘦下来了。你需要主动消耗体内的脂肪。饮食方面，要减少高油脂食物，多吃高纤维蔬菜。运动你可以选择跳绳、跳舞、爬山等运动。例如如果每天慢跑30分钟，一周你就可能减掉1公斤。结合饮食，一个月也可能降低2%-3%的体脂率。");
							yhUserBodyFatApp.setTiwen("偏高");
							yhUserBodyFatApp.setProgressvalue(82.5);
						}
					} else {
						if (fatRate < 20) {
							yhUserBodyFatApp.setJiedu("您的体脂偏低，请合理膳食结构，增强锻炼。");
							yhUserBodyFatApp.setTiwen("偏低");
							yhUserBodyFatApp.setProgressvalue(16.5);
						} else if (fatRate >= 20 && fatRate <= 25) {
							yhUserBodyFatApp.setJiedu("您的体脂正常，请继续保持健康的生活习惯。");
							yhUserBodyFatApp.setTiwen("正常");
							yhUserBodyFatApp.setProgressvalue(49.5);
						} else {
							yhUserBodyFatApp.setJiedu("您的体脂偏高，当身体每天摄入的热量小于消耗的热量，就会瘦下来了。你需要主动消耗体内的脂肪。饮食方面，要减少高油脂食物，多吃高纤维蔬菜。运动你可以选择跳绳、跳舞、爬山等运动。例如如果每天慢跑30分钟，一周你就可能减掉1公斤。结合饮食，一个月也可能降低2%-3%的体脂率。");
							yhUserBodyFatApp.setTiwen("偏高");
							yhUserBodyFatApp.setProgressvalue(82.5);
						}
					}
				}
			}
		}
		return Result.ok(list);
	}

	/**
	 * 修改体脂数据
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/modifyBmiInfo")
	@ResponseBody
	public Result modifyBmiInfo(YhUserBodyFatApp record) {
		yhUserBodyFatService.updateByPrimaryKeySelective(record);
		return Result.ok();
	}

	/**
	 * 获取当前系统时间String类型返回值
	 * 
	 * @return
	 */
	public String NowString() {
		SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	@RequestMapping(value = "/addInfo")
	@ResponseBody
	public Result addInfo(YhUserBodyFatApp record) {

		if (null != record.getWubfId() && 0 != record.getWubfId()) {
			record.setEntryTime(null);
			int updateByExampleSelective = yhUserBodyFatService.updateByPrimaryKeySelective(record);
			return Result.ok(updateByExampleSelective);
		} else {
			System.out.println(record);
			record.setIsDel(2);
			record.setEntryTime(record.getEntryTime() + NowString());

			int insertSelective = yhUserBodyFatService.insertSelective(record);
			if (insertSelective > 0) {
				return Result.ok();
			}

			return Result.error();
		}

	}
	/**
	 * 查询已经存在数据的日期
	 */
	@RequestMapping(value = "/findTimeInfo")
	@ResponseBody
	public Result findTimeInfo(String usercode) throws ParseException{
		if (StringUtil.isNotEmpty(usercode)) {
			Result.error();
		}
		List<String> selectByPrimaryKeyFindEntryTime = yhUserBodyFatService.selectByPrimaryKeyFindEntryTime(usercode);
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
	 * 根据eid查询最新7条体脂记录
	 * 
	 * @param eid
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/findInfo")
	@ResponseBody
	public Result findInfo(String usercode, @RequestParam(required = false,defaultValue = "0") Integer timeType, String time, Integer page) throws ParseException {
		int pageSize=7;
		Map<String,Object> map=new HashMap<>();
		List<String> timeList=new ArrayList<>();
		List<String> dataListFatRate=new ArrayList<>();
		List<String> dataListMuscle=new ArrayList<>();
		List<String> dataListBones=new ArrayList<>();
		if(2==timeType){
			List<YhUserBodyFatApp> list = yhUserBodyFatService.findBodyFatByYear(time, usercode, (page-1)*pageSize, pageSize);
			if(null!=list){
				for (YhUserBodyFatApp yhUserBodyFat : list) {
					String entryTime = yhUserBodyFat.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					dataListFatRate.add(String.valueOf(yhUserBodyFat.getFatRate()));
					dataListMuscle.add(String.valueOf(yhUserBodyFat.getMuscle()));
					dataListBones.add(String.valueOf(yhUserBodyFat.getBones()));
				}
			}
			map.put("timeList",timeList);
			map.put("dataListFatRate",dataListFatRate);
			map.put("dataListMuscle",dataListMuscle);
			map.put("dataListBones",dataListBones);
			return Result.ok(map);
		}else if(1==timeType){
			List<YhUserBodyFatApp> list = yhUserBodyFatService.findBodyFatByMonth(time, usercode, (page-1)*pageSize, pageSize);
			if(null!=list){
				for (YhUserBodyFatApp yhUserBodyFat : list) {
					String entryTime = yhUserBodyFat.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					dataListFatRate.add(String.valueOf(yhUserBodyFat.getFatRate()));
					dataListMuscle.add(String.valueOf(yhUserBodyFat.getMuscle()));
					dataListBones.add(String.valueOf(yhUserBodyFat.getBones()));
				}
			}
			map.put("timeList",timeList);
			map.put("dataListFatRate",dataListFatRate);
			map.put("dataListMuscle",dataListMuscle);
			map.put("dataListBones",dataListBones);
			return Result.ok(map);
		}else if(0==timeType){
			String[] split = time.split("~");
			List<YhUserBodyFatApp> list = yhUserBodyFatService.findBodyFatByWeek(split[0],split[1], usercode, (page-1)*pageSize, pageSize);
			if(null!=list){
				for (YhUserBodyFatApp yhUserBodyFat : list) {
					String entryTime = yhUserBodyFat.getEntryTime();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					dataListFatRate.add(String.valueOf(yhUserBodyFat.getFatRate()));
					dataListMuscle.add(String.valueOf(yhUserBodyFat.getMuscle()));
					dataListBones.add(String.valueOf(yhUserBodyFat.getBones()));
				}
			}
			map.put("timeList",timeList);
			map.put("dataListFatRate",dataListFatRate);
			map.put("dataListMuscle",dataListMuscle);
			map.put("dataListBones",dataListBones);
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

	/**
	 * 按照年月查询数据 填充前段日历插件
	 * 
	 * @param eid
	 * @param ye
	 * @param mo
	 * @return
	 */
	@RequestMapping(value = "/findMonthInfo")
	@ResponseBody
	public Result findMonthInfo(String eid, String ye, String mo) {

		if (null == ye && null == mo) {
			ye = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			mo = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		}
		String shijian = ye + "-" + mo + "-01";
		System.out.println(eid + "" + shijian);
		List<YhUserBodyFatApp> monthInfoList = yhUserBodyFatService.selectByPrimaryKeyByMonthInfo(eid, shijian);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < monthInfoList.size(); i++) {
			YhUserBodyFatApp yhUserBloodpressure = monthInfoList.get(i);
			if (null != yhUserBloodpressure) {
				if (null != yhUserBloodpressure.getEntryTime()) {
					String entryTime = yhUserBloodpressure.getEntryTime();
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

	/**
	 * 根据eid查询全部历史信息
	 * 
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/findInfoByeidAll")
	@ResponseBody
	public Result findInfoByeidAll(String eid) {
		System.out.println(eid);

		List<YhUserBodyFatApp> selectByPrimaryKeyByUserIdLimit7 = yhUserBodyFatService.selectByPrimaryKeyBYeid(eid);

		if (null != selectByPrimaryKeyByUserIdLimit7) {
			return Result.ok(selectByPrimaryKeyByUserIdLimit7);
		}

		return Result.error();
	}
}