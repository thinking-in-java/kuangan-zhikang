package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserUbaApp;
import com.yihe.applet.api.service.YhUserUbaAppService;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 尿检 Created by dyb on 2019/12/25.
 */
@Controller
@RequestMapping("/api/YhUserUba")
@Api(value = "YhUserUba控制器", description = "YhUserUba管理")
public class YhUserUbaAppController   {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserUbaAppController.class);

	@Autowired
	YhUserUbaAppService yhUserUbaService;
	@Autowired
	YhHeThresholdService yhHeThresholdService;
	@RequestMapping(value = "/dataAnalysis")
	@ResponseBody
	public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
		Map<String, Object> map = new HashMap<>();
		Set<String> timeList = new HashSet<>();
		if(0==timeType) {
			String[] split = time.split("~");
			List<YhUserUbaApp> list = yhUserUbaService.findUserUbaAllByUserCode(usercode,timeType,split[0],split[1]);

			if (null != list) {
				double k = 0;
				for (int i = 0; i < list.size(); i++) {
					YhUserUbaApp yhUserUbaApp = list.get(i);
					String entryTime = yhUserUbaApp.getAdddate();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					String whitebloodcell = yhUserUbaApp.getWhitebloodcell();
					String ketone = yhUserUbaApp.getKetone();
					String urineprotein = yhUserUbaApp.getUrineprotein();
					if("-".equals(whitebloodcell)&&"-".equals(ketone)&&"-".equals(urineprotein)){
						k++;
					}else{

					}


				}
				map.put("dayNum", timeList.size());
				map.put("size", list.size());
				double j = k / list.size() * 100;
				map.put("avg", Math.round(j));
			}
		}else{
			List<YhUserUbaApp> list = yhUserUbaService.findUserUbaAllByUserCode(usercode,timeType,time,null);

			if (null != list) {
				double k = 0;
				for (int i = 0; i < list.size(); i++) {
					YhUserUbaApp yhUserUbaApp = list.get(i);
					String entryTime = yhUserUbaApp.getAdddate();
					String transferFormat = transferFormat2(entryTime);
					timeList.add(transferFormat);
					String whitebloodcell = yhUserUbaApp.getWhitebloodcell();
					String ketone = yhUserUbaApp.getKetone();
					String urineprotein = yhUserUbaApp.getUrineprotein();
					if("-".equals(whitebloodcell)&&"-".equals(ketone)&&"-".equals(urineprotein)){
						k++;
					}else{

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

	public static String transferFormat2(String inTime) throws ParseException {
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

		Date tempDate = null;
		String outTime = null;
		tempDate = s1.parse(inTime);
		outTime = s1.format(tempDate);
		return outTime;
	}

	@RequestMapping(value = "/pieChartAssignment")
	@ResponseBody
	public Result pieChartAssignment(String usercode, Integer timetype, String time) {
		if (0 == timetype) {
			String[] split = time.split("~");
			int zhengchang = 0;
			int yichang = 0;
			List<YhUserUbaApp> list = yhUserUbaService.findUbaInfoByTimeType(usercode, timetype, split[0], split[1]);
			if(null!=list){
				for (int i = 0; i < list.size(); i++) {
					YhUserUbaApp yhUserUbaApp = list.get(i);
					String whitebloodcell = yhUserUbaApp.getWhitebloodcell();
					String ketone = yhUserUbaApp.getKetone();
					String urineprotein = yhUserUbaApp.getUrineprotein();
					if("-".equals(whitebloodcell)&&"-".equals(ketone)&&"-".equals(urineprotein)){
						zhengchang++;
					}else{
						yichang++;
					}

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yichang", yichang);
			map.put("zhengchang", zhengchang);
			return Result.ok(map);
		}else if(1 == timetype){
			int zhengchang = 0;
			int yichang = 0;
			List<YhUserUbaApp> list = yhUserUbaService.findUbaInfoByTimeType(usercode, timetype, time, null);
			if(null!=list){
				for (int i = 0; i < list.size(); i++) {
					YhUserUbaApp yhUserUbaApp = list.get(i);
					String whitebloodcell = yhUserUbaApp.getWhitebloodcell();
					String ketone = yhUserUbaApp.getKetone();
					String urineprotein = yhUserUbaApp.getUrineprotein();
					if("-".equals(whitebloodcell)&&"-".equals(ketone)&&"-".equals(urineprotein)){
						zhengchang++;
					}else{
						yichang++;
					}

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yichang", yichang);
			map.put("zhengchang", zhengchang);
			return Result.ok(map);
		}else if(2 == timetype){
			int zhengchang = 0;
			int yichang = 0;
			List<YhUserUbaApp> list = yhUserUbaService.findUbaInfoByTimeType(usercode, timetype, time, null);
			if(null!=list){
				for (int i = 0; i < list.size(); i++) {
					YhUserUbaApp yhUserUbaApp = list.get(i);
					String whitebloodcell = yhUserUbaApp.getWhitebloodcell();
					String ketone = yhUserUbaApp.getKetone();
					String urineprotein = yhUserUbaApp.getUrineprotein();
					if("-".equals(whitebloodcell)&&"-".equals(ketone)&&"-".equals(urineprotein)){
						zhengchang++;
					}else{
						yichang++;
					}

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yichang", yichang);
			map.put("zhengchang", zhengchang);
			return Result.ok(map);
		}
		return Result.error();
	}
	/**
	 * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
	 */

	@RequestMapping(value = "/selectByPrimaryKeyFindEntryTime")
	@ResponseBody
	public Result selectByPrimaryKeyFindEntryTime(String usercode) {
		if (null == usercode) {
			Result.error();
		}

		List<YhUserUbaApp> selectByPrimaryKeyFindEntryTime = yhUserUbaService.selectByPrimaryKeyFindEntryTime(usercode);

		return Result.ok(selectByPrimaryKeyFindEntryTime);
	}

	/**
	 * 查询最新尿检信息
	 */

	@RequestMapping(value = "/findUbaInfoNew")
	@ResponseBody
	public Result findUbaInfoNew(String eid) {
		if (null == eid) {
			Result.error();
		}

		YhUserUbaApp selectByPrimaryKeyNewest9 = yhUserUbaService.selectByPrimaryKeyNewest(eid);// 尿检

		return Result.ok(selectByPrimaryKeyNewest9);
	}

	/**
	 * 查询当前日记录
	 */
	@RequestMapping(value = "/selectByPrimaryKeyFindEntryTimeAnduserid")
	@ResponseBody
	public Result selectByPrimaryKeyFindEntryTimeAnduserid(String usercode, String time) {
		if (null == usercode || null == time) {
			Result.error();
		}

		List<YhUserUbaApp> list = yhUserUbaService
				.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
		if(null!=list){
			for (int i = 0; i < list.size(); i++) {
				YhUserUbaApp yhUserUbaApp = list.get(i);
				yhUserUbaApp.setOpenNo(false);
				if(i==0){
					yhUserUbaApp.setOpenNo(true);
				}
				yhUserUbaApp.setTiwen("正常");
				String whitebloodcell = yhUserUbaApp.getWhitebloodcell();
				String ketone = yhUserUbaApp.getKetone();
				String urineprotein = yhUserUbaApp.getUrineprotein();
				String redbloodcell = yhUserUbaApp.getRedbloodcell();

				if (!"-".equals(whitebloodcell)) {
					yhUserUbaApp.setTiwen("异常");

				}
				if (!"-".equals(ketone)) {
					yhUserUbaApp.setTiwen("异常");
				}
				if (!"-".equals(urineprotein)) {
					yhUserUbaApp.setTiwen("异常");
				}
				if (!"-".equals(redbloodcell)) {
					yhUserUbaApp.setTiwen("异常");
				}

				if ("异常".equals(yhUserUbaApp.getTiwen())) {
					yhUserUbaApp.setJiedu("可能发生泌尿系统感染，结石，肾脏功能异常，可以根据医生建议进一步进行筛查!");
				} else {
					yhUserUbaApp.setJiedu("您的尿检正常，请继续保持健康的生活习惯。");
				}

			}
		}

		return Result.ok(list);
	}

	/**
	 * 修改尿检数据
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/modifyBmiInfo")
	@ResponseBody
	public Result modifyBmiInfo(YhUserUbaApp record) {
		yhUserUbaService.updateByPrimaryKeySelective(record);
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

		List<YhUserUbaApp> selectByPrimaryKeyByUserIdLimit7 = yhUserUbaService.selectByPrimaryKeyBYeid(eid);

		if (null != selectByPrimaryKeyByUserIdLimit7) {
			return Result.ok(selectByPrimaryKeyByUserIdLimit7);
		}

		return Result.error();
	}

	/**
	 * 根据主键查询尿检信息
	 */
	@RequestMapping(value = "/findInfoByKey")
	@ResponseBody
	public Result findInfoByKey(Integer id) {
		if (null == id) {
			return Result.error();
		}
		return Result.ok(yhUserUbaService.selectByPrimaryKey(id));
	}

	/**
	 * 新增记录
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/addInfo")
	@ResponseBody
	public Result addInfo(YhUserUbaApp record, HttpServletRequest request) {
		System.out.println(record);
		// 修改
		if (null != record.getUbaId() && 0 != record.getUbaId()) {
			record.setAdddate(null);


			int updateByExampleSelective = yhUserUbaService.updateByPrimaryKeySelective(record);
			try {
				yhHeThresholdService.selectBythresType("the_ketone_body", String.valueOf(record.geteUserId()), record.getKetone(), "酮体",request);
				yhHeThresholdService.selectBythresType("urinalysis_erythrocyte", String.valueOf(record.geteUserId()), record.getWhitebloodcell(), "白细胞",request);
				yhHeThresholdService.selectBythresType("urine_protein", String.valueOf(record.geteUserId()), record.getUrineprotein(), "尿蛋白",request);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			return Result.ok(updateByExampleSelective);
		} else {
			record.setIsDel(2);
			record.setAdddate(record.getAdddate() + NowString());
			int insertSelective = yhUserUbaService.insertSelective(record);
			try {
				yhHeThresholdService.selectBythresType("the_ketone_body", String.valueOf(record.geteUserId()), record.getKetone(), "酮体",request);
				yhHeThresholdService.selectBythresType("urinalysis_erythrocyte", String.valueOf(record.geteUserId()), record.getWhitebloodcell(), "白细胞",request);
				yhHeThresholdService.selectBythresType("urine_protein", String.valueOf(record.geteUserId()), record.getUrineprotein(), "尿蛋白",request);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (insertSelective > 0) {
				return Result.ok(insertSelective);
			}
			return Result.error();
		}

	}

}