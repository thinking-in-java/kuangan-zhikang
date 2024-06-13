package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.*;
import com.yihe.system.domain.YhUserAdminInfoExample.Criteria;
import com.yihe.system.service.YhUserAdminInfoService;
import com.yihe.system.service.impl.*;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户干预管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/intervene")
public class interveneController {
	@Autowired
	YhUserReginfoServiceImpl yhUserReginfoServiceImpl;
	@Autowired
	YhSleepUserDiaryServiceImpl YhSleepUserDiaryServiceImpl;
	@Autowired
	YHUserBloodPressureServiceImpl YHUserBloodPressureServiceImpl;
	@Autowired
	YhDiseaseAssessTotalReportServiceImpl YhDiseaseAssessTotalReportServiceImpl;
	@Autowired
	YhUserEnrolinfoServiceImpl YhUserEnrolinfoServiceImpl;
	@Autowired
	YHDocGroupServiceImpl YHDocGroupServiceImpl;
	@Autowired
	private YhUserAdminInfoService YhUserAdminInfoService;
	

	@RequestMapping(value = "/findInterveneListInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result healthAssessment2(@RequestParam(required = false) String fenzu,
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String strTime,
			@RequestParam(required = false) String endTime, @RequestParam(required = false) String usernameLike,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}
		// List<interveneVOJO> list = new ArrayList<interveneVOJO>();
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		// interveneVOJO vojo = new interveneVOJO();
		/*
		 * PageInfo<YhUserReginfo> pagelist =
		 * yhUserReginfoServiceImpl.selectByPrimaryKeyBydoctor(principal.
		 * getUserCode(), page, limit);
		 */
		if (StringUtil.isNotEmpty(fenzu)) {
			fenzu = fenzu + ",";
		}
		PageInfo<InterveneVOJO> pagelist = yhUserReginfoServiceImpl.selectByPrimaryKeyBydoctorNew(
				principal.getUserCode(), strTime, endTime, fenzu, usernameLike, page, limit);
		// 该医生名下用户已经干预的.
		List<InterveneVOJO> list2 = pagelist.getList();

		if (null == list2 || list2.size() == 0) {
			Map<String, Object> rsMap = new HashMap<String, Object>();
			rsMap.put("count", pagelist.getTotal());
			rsMap.put("code", 0);
			rsMap.put("msg", "ok");
			rsMap.put("data", list2);
			return Result.ok(rsMap);
		}

		for (InterveneVOJO ivojo : list2) {
			// 医生所管理用户的usercode

			List<String> timeList = new ArrayList<String>();
			String bmiTime = ivojo.getBmiTime();// 体重数据录入最新时间
			if (StringUtil.isNotEmpty(bmiTime)) {
				timeList.add(bmiTime);
			}
			String bloodPreTime = ivojo.getBloodPreTime();// 血压数据录入最新时间
			if (StringUtil.isNotEmpty(bloodPreTime)) {
				timeList.add(bloodPreTime);
			}
			String bsTime = ivojo.getBsTime();// 血糖数据录入最新时间
			if (StringUtil.isNotEmpty(bsTime)) {
				timeList.add(bsTime);
			}
			if (timeList.size() > 0) {
				ListSort(timeList);
				String string = timeList.get(timeList.size() - 1);
				ivojo.setMonitorTime(string);
			} else {
				// ivojo.setMonitorTime("无数据");
			}

			
			// 最新评估时间
			String selectByPrimaryKeyByusercodeForQtime = YhDiseaseAssessTotalReportServiceImpl
					.selectByPrimaryKeyByusercodeForQtime(ivojo.getUsercode());
			if (null != selectByPrimaryKeyByusercodeForQtime) {
				ivojo.setAssess(selectByPrimaryKeyByusercodeForQtime);
			}

			// 饮食日记时间
			String selectByPrimaryKeyByusercodeForCtime = YhDiseaseAssessTotalReportServiceImpl
					.selectByPrimaryKeyByusercodeForCtime(ivojo.getUsercode());
			if (null != selectByPrimaryKeyByusercodeForCtime) {
				ivojo.setFoodTime(selectByPrimaryKeyByusercodeForCtime);
			}

			// 运动日记最新时间
			String selectByPrimaryKeyByusercodeForSporttime = YhDiseaseAssessTotalReportServiceImpl
					.selectByPrimaryKeyByusercodeForSporttime(ivojo.getUsercode());
			if (null != selectByPrimaryKeyByusercodeForSporttime) {
				ivojo.setSportTime(selectByPrimaryKeyByusercodeForSporttime);
			}

			// 睡眠日记最新时间
			String findSleepUserDiaryByUserCodeForNew = YhSleepUserDiaryServiceImpl
					.findSleepUserDiaryByUserCodeForNew(ivojo.getUsercode());
			if (null != findSleepUserDiaryByUserCodeForNew) {
				ivojo.setSleepNewTime(findSleepUserDiaryByUserCodeForNew);
			}
			
		
			// 查询用户信息拓展
			if (StringUtil.isNotEmpty(ivojo.getEvaTime())) {
				// 干预周期
				String xiangcha = xiangcha(ivojo.getEvaTime());
				ivojo.setInterveneTime(xiangcha);
			}
			if (StringUtil.isNotEmpty(ivojo.getGroupInfo()) && !"0".equals(ivojo.getGroupInfo())) {
				String flagDgroup = ivojo.getGroupInfo();
				ivojo.setGroupingStr(flagDgroup);// 干预分组

				String groupName = "";
				String[] split = flagDgroup.split(",");
				for (int i = 0; i < split.length; i++) {
					String id = split[i];
					YHDocGroup selectByPrimaryKey = YHDocGroupServiceImpl.selectByPrimaryKey(Integer.valueOf(id));
					if (null != selectByPrimaryKey) {
						groupName = groupName + selectByPrimaryKey.getGname() + ",";
					} else {
						ivojo.setGrouping("无分组");
					}

				}
				ivojo.setGrouping(groupName);

			} else {
				ivojo.setGrouping("无分组");
			}

		}

		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", pagelist.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list2);
		return Result.ok(rsMap);
	}

	private static void ListSort(List<String> list) {
		{ // 排序方法啊啊
			Collections.sort(list, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						// format.format(o1.getTime()) 表示 date转string类型
						// 如果是string类型就不要转换了
						Date dt1 = format.parse(o1);
						Date dt2 = format.parse(o2);
						// 这是由大向小排序 如果要由小向大转换比较符号就可以
						int compareTo = dt1.compareTo(dt2);
						return compareTo;
					} catch (Exception e) {
						e.printStackTrace();
					}
					return 0;
				}

			});
		}
	}

	@RequestMapping(value = "/findGroupListInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result findGroupListInfo(@RequestParam(required = false) Integer fenzu,
			@RequestParam(required = false) String strtime, @RequestParam(required = false) String endtime,@RequestParam(required = false) String nikename,
			@RequestParam(required = false) String usernameLike,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit, Model model) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String userCode = principal.getUserCode();
		if (null == userCode) {
			return null;
		} 

		PageInfo<YHDocGroup> selectByPrimaryKeyByDoc = YHDocGroupServiceImpl.selectByPrimaryKeyByDoc(userCode, page,
				limit,nikename);

		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", selectByPrimaryKeyByDoc.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", selectByPrimaryKeyByDoc.getList());
		return Result.ok(rsMap);
	}

	/**
	 * 获取当前系统时间String类型返回值
	 * 
	 * @return
	 */
	public String NowString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间 
	}

	@RequestMapping(value = "/cancelIntervention", method = RequestMethod.POST)
	@ResponseBody
	public Result cancelIntervention(@RequestBody List<InterveneVOJO> record) {
		for (InterveneVOJO interveneVOJO : record) {
			System.out.println(interveneVOJO.getUsercode());

			// 修改用户拓展表 干预
			String usercode = interveneVOJO.getUsercode();
			YhUserEnrolinfo eninfo = new YhUserEnrolinfo();
			eninfo.setUserCode(usercode);
			YhUserEnrolinfoServiceImpl.modifyInfoByEva(eninfo);
			// 修改分组内人数-1
			String groupingStr = interveneVOJO.getGroupingStr();
			if (StringUtil.isNotEmpty(groupingStr)) {
				String[] split = groupingStr.split(",");
				for (int i = 0; i < split.length; i++) {
					String id = split[i];
					YHDocGroup dg = new YHDocGroup();
					dg.setuDocgroupId(Integer.valueOf(id));
					YHDocGroupServiceImpl.updateByPrimaryKeyByUserCount(dg);
				}
			}

		}
		return Result.ok();
	}

	/**
	 * 批量分组
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/batchGrouping", method = RequestMethod.POST)
	@ResponseBody
	public Result batchGrouping(@RequestBody InterveneVOJO2 v2) {
		System.out.println(v2);
		List<InterveneVOJO> record = v2.getRecord();// 选中的人
		InterveneVOJO city = v2.getCity();// 选中的分组
		String groupingStr = city.getGroupingStr();
		if (null != record && record.size() > 0) {
			for (InterveneVOJO interveneVOJO : record) {
				String usercode = interveneVOJO.getUsercode();
				YhUserEnrolinfo selectByPrimaryKeyByUsercode = YhUserEnrolinfoServiceImpl
						.selectByPrimaryKeyByUsercode(usercode);
				String flagDgroup = selectByPrimaryKeyByUsercode.getFlagDgroup();// 已经在的分组
				if ("0".equals(flagDgroup)) {
					flagDgroup = "";
				}
				if (groupingStr.equals(flagDgroup)) {
					continue;
				}
				// 是否存在
				/*
				 * if (StringUtil.isNotEmpty(flagDgroup)) { flagDgroup =
				 * flagDgroup + ","; }
				 */
				String[] split2 = flagDgroup.split(",");
				if (StringUtil.isNotEmpty(groupingStr)) {
					String[] split = groupingStr.split(",");
					for (int i = 0; i < split.length; i++) {
						String groupingStrlist = split[i];// 刚刚批量分组下拉框每个所选分组
						// 是否存在
						Boolean flag = true;
						for (int j = 0; j < split2.length; j++) {
							String flagDgrouplist = split2[j];// 用户存在的分组
							if (groupingStrlist.equals(flagDgrouplist)) {
								flag = false;
							}
						}
						if (flag) {
							System.out.println(groupingStrlist);
							YHDocGroup upInfo = new YHDocGroup();
							upInfo.setuDocgroupId(Integer.valueOf(groupingStrlist));
							YHDocGroupServiceImpl.updateByPrimaryKeyByUserCountUp(upInfo);
							// 没有加入的分组

							flagDgroup = flagDgroup + groupingStrlist + "_0,";
						}
					}
				}

				Map<String, String> map = new HashMap<String, String>();
				// 分组不等于空 追加前加上逗号
				/*
				 * if (flagDgroup.endsWith(",")) { flagDgroup =
				 * flagDgroup.substring(0, flagDgroup.length() - 1); }
				 */
				map.put("flagDgroup", flagDgroup);
				map.put("usercode", usercode);
				YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);

			}
		}
		return Result.ok();
	}

	/**
	 * 批量分组
	 * 
	 * @param record 
	 * @return
	 */
	@RequestMapping(value = "/batchGroupingNew", method = RequestMethod.POST)
	@ResponseBody
	public Result batchGroupingNew(@RequestBody InterveneVOJO2 v2) {

		List<String> record = v2.getUserList();// 选中的人
		if (null == record || record.size() == 0) {
			return Result.ok();
		}
		InterveneVOJO city = v2.getCity();// 选中的分组
		// 224,225
		String groupingStr = city.getGroupingStr();
		String groupingStrNew = "";
		// 变成系统格式化f224_g0;f225_g0;
		if (StringUtil.isEmpty(groupingStr)) {
			return Result.ok();
		} else {
			// 224
			String[] split = groupingStr.split(",");
			for (String string : split) {
				String info = "f" + string + "_g0;";
				groupingStrNew = groupingStrNew + info;
			}
		}
		if (null != record && record.size() > 0) {
			// 循环所以选中的人
			for (String usercode : record) {
				// 获得usercode
				// 查询用户拓展表获得分组信息
				YhUserEnrolinfo selectByPrimaryKeyByUsercode =null;
				  selectByPrimaryKeyByUsercode = YhUserEnrolinfoServiceImpl
						.selectByPrimaryKeyByUsercode(usercode);
				if(null==selectByPrimaryKeyByUsercode){
					YhUserEnrolinfo recordEnro=new YhUserEnrolinfo();
					recordEnro.setUserCode(usercode);
					YhUserEnrolinfoServiceImpl.insertSelective(recordEnro);
					 selectByPrimaryKeyByUsercode = YhUserEnrolinfoServiceImpl
								.selectByPrimaryKeyByUsercode(usercode);
				}
				// 获得已经分的组 可能没分组 为null “” 0
				String flagDgroup = selectByPrimaryKeyByUsercode.getFlagDgroup();
				// 用户原来没有分组直接把格式化的分组放入数据库
				if (StringUtil.isEmpty(flagDgroup) || "0".equals(flagDgroup)) {
					if (!"".equals(groupingStrNew)) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("flagDgroup", groupingStrNew);
						map.put("usercode", usercode);
						YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);
					}

				} else {
					// 需要加入的分组f224_g0;f226_g0;f221_g0;
					String[] split3 = groupingStrNew.split(";");
					List<String> list1 = Arrays.asList(split3);// 将数组转换为list
					List<String> arrList = new ArrayList<String>(list1);
					// 原来存在分组 需要拼接好在加入到数据库
					// 先查询已经存在的分组
					// 分组去重 f224_g1;f226_g2;f221_g5;
					String[] split = flagDgroup.split(";");

					for (String string : split) {
						// f224_g1
						String[] split2 = string.split("_");
						// f224
						string = split2[0];

						// 需要加入的分组f224_g0
						for (String string2 : split3) {
							// f224
							String[] split4 = string2.split("_");
							// f224
							String string3 = split4[0];
							if (string.equals(string3)) {
								arrList.remove(string3 + "_g0");
								break;
							}
						}
					}
					String fenzuInfo = "";
					for (String string : arrList) {
						fenzuInfo = fenzuInfo + string + ";";
						String substring = string.substring(1, string.length() - 3);
						YHDocGroupServiceImpl.updateByPrimaryKeyByUserCountUp(new YHDocGroup(Integer.valueOf(substring)));
					}

					Map<String, String> map = new HashMap<String, String>();
					map.put("flagDgroup", flagDgroup + fenzuInfo);
					map.put("usercode", usercode);
					YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);
				}

			}
		}
		return Result.ok();
	}

	/**
	 * 批量移除
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/removeGroupingNew", method = RequestMethod.POST)
	@ResponseBody
	public Result removeGroupingNew(@RequestBody InterveneVOJO2 v2) {
		List<String> record = v2.getUserList();// 选中的人
		if (record.size() == 0) {
			return Result.ok();
		}
		InterveneVOJO city = v2.getCity();// 选中的分组
		// 224,225
		String groupingStr = city.getGroupingStr();
		if (StringUtil.isEmpty(groupingStr)) {
			return Result.ok();
		}
		String groupingStrNew = "";
		// 变成系统格式化f224_g0;f225_g0;
		if (StringUtil.isEmpty(groupingStr)) {
			return Result.ok();
		} else {
			// 224
			String[] split = groupingStr.split(",");
			for (String string : split) {
				String info = "f" + string + "_g;";
				groupingStrNew = groupingStrNew + info;
			}
		}
		if (null != record && record.size() > 0) {
			for (String usercode : record) {
				 
				YhUserEnrolinfo selectByPrimaryKeyByUsercode = YhUserEnrolinfoServiceImpl
						.selectByPrimaryKeyByUsercode(usercode);
				// f224_g1;f226_g2;f221_g5;
				// 已经在的分组
				String flagDgroup = selectByPrimaryKeyByUsercode.getFlagDgroup();
				// 用户原来没有分组直接把格式化的分组放入数据库
				if (StringUtil.isEmpty(flagDgroup) || "0".equals(flagDgroup)) {
					continue;
				} else {
					// 有分组 把原有的分组变成list f224_g1;
					String[] split3 = flagDgroup.split(";");
					if (split3.length == 0) {
						continue;
					}
					String[] split = groupingStrNew.split(";");
					List<String> list1 = Arrays.asList(split3);// 将数组转换为list
					List<String> arrList = new ArrayList<String>(list1);
					List<String> list2 = Arrays.asList(split);// 将数组转换为list
					List<String> arrList2 = new ArrayList<String>(list2);
					// 循环匹配本次需要删除的分组
					Iterator<String> iterator = arrList.iterator();
					while (iterator.hasNext()) {
						String str = iterator.next();
						for (String string2 : arrList2) {
							if (str.contains(string2)) {
								iterator.remove();
							}
						}
					}
					/*
					 * for (String string : arrList) { for (String string2 :
					 * arrList2) { if(string.contains(string2)){
					 * arrList.remove(string); } } }
					 */

					String grouInfo = "";
					if (arrList.size() == 0) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("flagDgroup", "");
						map.put("usercode", usercode);
						YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);
					} else {
						for (String string : arrList) {
							grouInfo = grouInfo + string + ";";
						}
						Map<String, String> map = new HashMap<String, String>();
						map.put("flagDgroup", grouInfo);
						map.put("usercode", usercode);
						YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);
					}

				}
			}
		}
		return Result.ok();
	}

	/**
	 * 智能分组
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/intelligentGrouping", method = RequestMethod.POST)
	@ResponseBody
	public Result intelligentGrouping(@RequestBody InterveneVOJO2 v2) {
		System.out.println(v2);
		List<String> record = v2.getUserList();// 选中的人
		if (null == record || record.size() == 0) {
			return Result.error();
		}
		InterveneVOJO city = v2.getCity();// 是否保留原有干预计划0保留1清除
		if (null == city) {
			return Result.error();
		}
		int num=0;
		// 智能分组条件
		List<InterveneIntelligentGroupingVOJO> intelligence = v2.getIntelligence();
		if (null == intelligence || intelligence.size() == 0) {
			return Result.error();
		}

		// 遍历所有选中人员
		if (null != record && record.size() > 0) {
			for (String usercode : record) {
				// 每个人的usercode
				// 查询用户拓展表获得分组信息
				YhUserEnrolinfo selectByPrimaryKeyByUsercode = YhUserEnrolinfoServiceImpl
						.selectByPrimaryKeyByUsercode(usercode);
				// 重点 智能算出需要分的组
				YhDiseaseAssessReportVOJO findAssessTotalByUsercodeNewInfo = YhDiseaseAssessTotalReportServiceImpl
						.findAssessTotalByUsercodeNewInfo(usercode);
				// 如果没要评估跳过改用户
				if (null == findAssessTotalByUsercodeNewInfo) {
					continue;
				}
				// 获得已经分的组 可能没分组 为null “” 0
				String flagDgroup = selectByPrimaryKeyByUsercode.getFlagDgroup();
				
				//需要加入的分组ginfo
				String ginfo = "";
				for (InterveneIntelligentGroupingVOJO info : intelligence) {
					
					
					
					
					
					String gid = info.getGid();
					// 疑似已患或异常 一级
					if ("1".equals(gid)) {
						// 患或异常疾病 4;5;6
						String sknames = findAssessTotalByUsercodeNewInfo.getSknames();
						// bmi
						float bmi = findAssessTotalByUsercodeNewInfo.getBmi();

						if (StringUtil.isEmpty(sknames)) {
							continue;
						} else {

							// 4
							String[] split = sknames.split(";");
							for (String string : split) {
								Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode(string);
								if (null != findGroupInfoByDisCode) {
									ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g1;";

								}
							}
							// 消瘦
							if (bmi < 18.5) {
								Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode("消瘦");
								if (null != findGroupInfoByDisCode) {
									ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g1;";
								}
							}
							// 肥胖
							if (bmi >= 28) {
								Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode("肥胖症");
								if (null != findGroupInfoByDisCode) {
									ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g1;";
								}
							}

						 
							 
						}
					}

					// 疑似已患或异常 二级
					if ("2".equals(gid)) {
						// 患或异常疾病 1;2;3;10
						String bfnames = findAssessTotalByUsercodeNewInfo.getBfnames();
						// bmi
						float bmi = findAssessTotalByUsercodeNewInfo.getBmi();

						if (StringUtil.isEmpty(bfnames)) {
							continue;
						}
						// 1
						String[] split = bfnames.split(";");
						for (String string : split) {
							Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode(string);
							if (null != findGroupInfoByDisCode) {
								ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g2;";

							}
						}
						// 超重
						/*if (bmi >= 24 && bmi < 28) {
							Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode("11");
							if (null != findGroupInfoByDisCode) {
								ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g2;";
							}
						}*/

						// 将智能分组加入用户分组
						 
					

					}

					// 疑似已患或异常 三级
					if ("3".equals(gid)) {
						// 患或异常疾病 1;2;3;10
						String ldnames = findAssessTotalByUsercodeNewInfo.getLdnames();
						if (StringUtil.isEmpty(ldnames)) {
							continue;
						}
						// 1
						String[] split = ldnames.split(";");
						for (String string : split) {
							Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode(string);
							if (null != findGroupInfoByDisCode) {
								ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g3;";

							}
						}

						// 将智能分组加入用户分组
						 
						// 用户原来没有分组直接把格式化的分组放入数据库
						

					}

					// 疑似已患或异常 四级
					if ("4".equals(gid)) {
						// 患或异常疾病 1;2;3;10
						String lrnames = findAssessTotalByUsercodeNewInfo.getLrnames();
						if (StringUtil.isEmpty(lrnames)) {
							continue;
						}
						
						// 1
						String[] split = lrnames.split(";");
						for (String string : split) {
							Integer findGroupInfoByDisCode = YHDocGroupServiceImpl.findGroupInfoByDisCode(string);
							if (null != findGroupInfoByDisCode) {
								ginfo = ginfo + "f" + findGroupInfoByDisCode + "_g4;";

							}
						}

						// 将智能分组加入用户分组
						 
						// 用户原来没有分组直接把格式化的分组放入数据库
						

					}
					
				
					

				}
				
				// 用户原来没有分组直接把格式化的分组放入数据库
				if (StringUtil.isEmpty(flagDgroup) || "0".equals(flagDgroup)) {
					if (!"".equals(ginfo)) {
						System.out.println(ginfo);
						Map<String, String> map = new HashMap<String, String>();
						map.put("flagDgroup", ginfo);
						map.put("usercode", usercode);
						num=num+ YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);
					}

				}else{
					
					//需要加入的分组
					String[] split5 = ginfo.split(";");
					List<String> list1 = Arrays.asList(split5);
					List<String> arrList = new ArrayList<String>(list1);
																		
																		
					// 已存在的分组
					String[] splitold = flagDgroup.split(";");
					List<String> list2 = Arrays.asList(splitold);
					List<String> arrList2 = new ArrayList<String>(list2);
					
					
					for (int i = 0; i < splitold.length; i++) {
						String[] split1 = splitold[i].split("_");
						String info1=split1[0];
						 for (int j = 0; j < split5.length; j++) {
							 String[] split2 = split5[j].split("_");
								String info2=split2[0];
									if(info1.equals(info2)){
										remove(split5, j);
									}
						}
					}
									
					System.out.println("已存在的分组-------------++++++*************");
					for (int i = 0; i < splitold.length; i++) {
						
						System.out.println(splitold[i]);
					}
					System.out.println("新要加入的分组-------------++++++*************");
					String newGinfo="";
					for (int i = 0; i < split5.length; i++) {
						System.out.println(split5[i]+";");
						if("".equals(split5[i])){
							continue;
						}
						String info=split5[i]+";";
						newGinfo=newGinfo+info;
					}
					
																			
					Map<String, String> map = new HashMap<String, String>();
					map.put("flagDgroup", flagDgroup+newGinfo);
					map.put("usercode", usercode);
					num=num+ YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);
					
					
				}
				

			}
		}
		return Result.ok(num);
	}
	/**
	 * 删除数组中指定下标元素的内容
	 * 
	 * @param arr 源数据数组，int类类型
	 * @param index 指定删除的下标位置
	 * 
	 */
	public static void remove(String[] arr, int index) {

		 //   从删除位置开始，到数组的最后一个有效元素位置结束 
		for (int i = index; i < arr.length - 1; i++) {
			arr[i] = arr[i + 1];
		}	
		// 最后一位数据赋值为0，占位
		arr[arr.length - 1] = "";	
	}
	/**
	 * 批量移除
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/removeGrouping", method = RequestMethod.POST)
	@ResponseBody
	public Result removeGrouping(@RequestBody InterveneVOJO2 v2) {
		System.out.println(v2);
		List<InterveneVOJO> record = v2.getRecord();// 选中的人
		InterveneVOJO city = v2.getCity();// 选中的分组
		// 2,122
		String groupingStr = city.getGroupingStr();
		if (null != record && record.size() > 0) {
			for (InterveneVOJO interveneVOJO : record) {
				String usercode = interveneVOJO.getUsercode();
				YhUserEnrolinfo selectByPrimaryKeyByUsercode = YhUserEnrolinfoServiceImpl
						.selectByPrimaryKeyByUsercode(usercode);
				// 1,149
				String flagDgroup = selectByPrimaryKeyByUsercode.getFlagDgroup();// 已经在的分组
				if ("0".equals(flagDgroup)) {
					flagDgroup = "";
				}
				String[] doChinFilters = null;
				String[] split2 = flagDgroup.split(",");
				if (StringUtil.isNotEmpty(groupingStr)) {
					String[] split = groupingStr.split(",");
					for (int i = 0; i < split.length; i++) {
						String groupingStrlist = split[i];// 需要移除的分组

						// 是否存在
						Boolean flag = false;
						for (int j = 0; j < split2.length; j++) {
							String flagDgrouplist = split2[j];// 用户存在的分组
							if (groupingStrlist.equals(flagDgrouplist)) {
								flag = true;
							}
						}
						if (flag) {
							doChinFilters = doChinFilters(split2, groupingStrlist);
							split2 = doChinFilters;
							System.out.println(groupingStrlist);
							YHDocGroup upInfo = new YHDocGroup();
							upInfo.setuDocgroupId(Integer.valueOf(groupingStrlist));
							YHDocGroupServiceImpl.updateByPrimaryKeyByUserCount(upInfo);

						}
					}
					String goupInfo = "";
					if (null == doChinFilters) {
						continue;
					}
					for (int i = 0; i < doChinFilters.length; i++) {
						goupInfo = goupInfo + doChinFilters[i] + ",";
					}
					Map<String, String> map = new HashMap<String, String>();
					/*
					 * if (goupInfo.endsWith(",")) { goupInfo =
					 * goupInfo.substring(0, goupInfo.length() - 1); }
					 */
					map.put("flagDgroup", goupInfo);
					map.put("usercode", usercode);
					YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);

				}

			}
		}
		return Result.ok();
	}

	@RequestMapping(value = "/addGroupInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result addGroupInfo(@RequestBody YHDocGroup record) {
		if (null == record) {
			return Result.error();
		}
		if (null == record.getuDocgroupId()) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			String userCode = principal.getUserCode();
			if (null == userCode) {
				return null;
			}
			// 根据userCode查询YhUserAdminInfo表
			YhUserAdminInfoExample example = new YhUserAdminInfoExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andUserCodeEqualTo(userCode);
			List<YhUserAdminInfo> selectByExample = YhUserAdminInfoService.selectByExample(example);
			if (null == selectByExample || selectByExample.size() == 0) {
				return null;
			}
			YhUserAdminInfo yhUserAdminInfo = selectByExample.get(0);
			Integer id = yhUserAdminInfo.getId();
			record.setDocid(id);
			record.setEstablishTime(NowString());
			YHDocGroupServiceImpl.insertSelective(record);
		} else {
			YHDocGroupServiceImpl.updateByPrimaryKeySelective(record);
		}
		return Result.ok();
	}

	/**
	 * 删除分组
	 * 
	 * @param uDocgroupId
	 * @return
	 */
	@RequestMapping(value = "/delGroupInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result delGroupInfo(Integer uDocgroupId) {
		if (null == uDocgroupId) {
			return Result.error();
		}
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String userCode = principal.getUserCode();
		YHDocGroupServiceImpl.deleteByPrimaryKey(uDocgroupId);
		String ginfo = "f" + uDocgroupId + "_g";
		List<YhUserEnrolinfo> findUserEnrolinfoByDoctorInfo = YhUserEnrolinfoServiceImpl
				.findUserEnrolinfoByDoctorInfo(userCode, ginfo);
		if (null != findUserEnrolinfoByDoctorInfo || findUserEnrolinfoByDoctorInfo.size() > 0) {
			for (YhUserEnrolinfo yhUserEnrolinfo : findUserEnrolinfoByDoctorInfo) {

				// 分组信息
				String flagDgroup = yhUserEnrolinfo.getFlagDgroup();
				String[] splitold = flagDgroup.split(";");
				List<String> list2 = Arrays.asList(splitold);// 将数组转换为list
				List<String> arrList2 = new ArrayList<String>(list2); 
				
				Iterator<String> iterator = arrList2.iterator();
				while (iterator.hasNext()) {
					String str = iterator.next();
					if (str.contains(ginfo)) {
						iterator.remove();
					}
				}
				
				String fenzuInfo = "";
				for (String string : arrList2) {
					fenzuInfo = fenzuInfo + string + ";";
				}
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("flagDgroup", fenzuInfo);
				map.put("usercode", yhUserEnrolinfo.getUserCode());
				YhUserEnrolinfoServiceImpl.updateByExampleSelectiveByusercode(map);

				
			}
		}

		return Result.ok(uDocgroupId);
	}

	private String xiangcha(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = df.format(new Date());
		try {
			long from = df.parse(time).getTime();
			long to = df.parse(dateTime).getTime();
			System.out.println("相差周数为：" + (to - from) / (1000 * 3600 * 24 * 7));
			long num = (to - from) / (1000 * 3600 * 24 * 7);
			if (num < 1) {
				System.out.println("小于一周计算天数");

				Date now = df.parse(dateTime);// 当前时间
				Date date = df.parse(time);// 过去
				long l = now.getTime() - date.getTime();
				long day = l / (24 * 60 * 60 * 1000);
				if (day == 0) {
					return "1天";
				}
				return day + "天";
			} else {
				System.out.println("大于一周");
				return num + "周";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws ParseException {
		/*String time1 = "2019-01-03 00:03:00";
		String time2 = "2019-01-03 00:02:00";

		List<String> list = new ArrayList<>();
		list.add(time1);
		list.add(time2);
		for (String string : list) {
			System.out.println(string);
		}
		ListSort(list);
		for (String string : list) {
			System.out.println(string);
		}*/
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		  System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	}

	/**
	 * 删除数组中的指定值 或者数组中的元素包含指定值
	 * 
	 * @param filters
	 *            数组
	 * @param target
	 *            指定值
	 * @return
	 */
	public String[] doChinFilters(String[] filters, String target) {
		String[] res = null;
		if (filters.length > 0) {
			List<String> tempList = Arrays.asList(filters);
			// Arrays.asList(filters) 迭代器实现类 不支持remove() 删除，多一步转化
			List<String> arrList = new ArrayList<String>(tempList);
			Iterator<String> it = arrList.iterator();
			while (it.hasNext()) {
				String x = it.next();
				if (x.equals(target)) {
					it.remove();
				}
			}
			res = new String[arrList.size()];
			arrList.toArray(res);
		}
		return res;
	}

}
