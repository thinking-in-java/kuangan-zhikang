package com.yihe.applet.api.controller;

import com.gargoylesoftware.css.parser.javacc.ParseException;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.service.*;
import com.yihe.system.domain.YhUserArtery;
import com.yihe.system.domain.YhUserCellDetectionWeb;
import com.yihe.system.domain.YhUserHemoglobin;
import com.yihe.system.domain.YhUserQuantumDetectionWeb;
import com.yihe.system.mapper.YhUserCellDetectionWebMapper;
import com.yihe.system.mapper.YhUserHemoglobinMapper;
import com.yihe.system.mapper.YhUserQuantumDetectionWebMapper;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.service.YhUserArteryService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据监测身高体重 Created by dyb on 2019/12/19.
 */
@Controller
@RequestMapping("/api/YhUserBmi")
@Api(value = "YhUserBmi控制器", description = "YhUserBmi管理")
public class YhUserBmiController {

    @Autowired
    YhUserBmiAppService yhUserBmiService;// bmi
    @Autowired
    YhUserBloodpressureAppService yhUserBloodpressureService;// 血压
    @Autowired
    YhUserBloodsugerAppService yhUserBloodsugerService;// 血糖
    @Autowired
    YhUserBodyFatAppService yhUserBodyFatService;// 体脂
    @Autowired
    YhUserBloodfatAppService yhUserBloodfatService;// 血脂
    @Autowired
    YhUserRenalfunctionAppService yhUserRenalfunctionService;// 肾功能
    @Autowired
    YhUserLiverfunctionAppService yhUserLiverfunctionService;// 肝功能
    @Autowired
    YhUserOtherfunctionAppService yhUserOtherfunctionService;// 其他功能
    @Autowired
    YhUserEcgAppService yhUserEcgService;// 心电图
    @Autowired
    YhUserUbaAppService yhUserUbaService;// 尿检
    @Autowired
    YhUserTemperatureAppService yhUserTemperatureService;// 体温
    @Autowired
    YhUserOxygenAppService yhUserOxygenService;// 血氧
    @Autowired
    YhUserHeartRateAppService yhUserHeartRateService;// 心率
    @Autowired
    YhUserReginfoAppService YhUserReginfoService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;
    @Autowired
    YhUserArteryService yhUserArteryService; //动脉硬化
    @Autowired
    YhUserCellDetectionWebMapper YhUserCellDetectionWebMapper;//细胞检测
    @Autowired
    YhUserQuantumDetectionWebMapper YhUserQuantumDetectionWebMapper;//量子检测
    @Autowired
    YhUserHemoglobinMapper YhUserHemoglobinMapper;


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public String NowString() {
        SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode, Integer timeType, String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();

        if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserBmiApp> list = yhUserBmiService.findBmiInfoAllByUsercode(usercode, timeType, split[0], split[1]);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserBmiApp yhUserBmiApp = list.get(i);
                    String entryTime = yhUserBmiApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float getuHeight = yhUserBmiApp.getuHeight();
                    Float getuWeight = yhUserBmiApp.getuWeight();
                    Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
                    if (bmi >= 18.5 && bmi < 24) {
                        k++;
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        } else {

            List<YhUserBmiApp> list = yhUserBmiService.findBmiInfoAllByUsercode(usercode, timeType, time, null);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserBmiApp yhUserBmiApp = list.get(i);
                    String entryTime = yhUserBmiApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float getuHeight = yhUserBmiApp.getuHeight();
                    Float getuWeight = yhUserBmiApp.getuWeight();
                    Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
                    if (bmi >= 18.5 && bmi < 24) {
                        k++;
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
     * 批量导入监测信息
     *
     * @param usercode
     * @return
     */
    @RequestMapping(value = "/addJianceInfo")
    @ResponseBody
    public Result addJianceInfo() {
        List<String> regList = YhUserReginfoService.findUserCodeByUserTypeFor5();
        String addtime = "2020-07-17 16:30:45";
        /**
         * 体温新增
         */
	/*	List<YhUserTemperatureApp> tlist = yhUserTemperatureService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=tlist&&tlist.size()>0){
			for (YhUserTemperatureApp yhUserTemperatureApp : tlist) {
				
				for (String string : regList) {
					YhUserTemperatureApp record=new YhUserTemperatureApp();
					record.seteUserId(string);
					record.setEntryTime(addtime);
					record.setTemperature(yhUserTemperatureApp.getTemperature());
					record.setIsDel(2);
					record.setDataSource("手动录入");
					yhUserTemperatureService.insertSelective(record);
				}
				
			}
		}*/

        /**
         * 血氧新增
         */
		/* List<YhUserOxygenApp> olist = yhUserOxygenService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=olist&&olist.size()>0){
			for (YhUserOxygenApp yhUserOxygenApp : olist) {
				for (String string : regList) {
					if("10083".equals(string)){
						continue;
					}
					YhUserOxygenApp record=new YhUserOxygenApp();
					record.seteUserId(Integer.valueOf(string));
					record.setOxygen(yhUserOxygenApp.getOxygen());
					record.setEntryTime(addtime);
					record.setIsDel(2);
					record.setTestHj("常规");
					record.setType("手动录入");
					yhUserOxygenService.insertSelective(record);
				}
			}
		}*/
        /**
         * 体重
         */
		/*List<YhUserBmiApp> blist = yhUserBmiService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=blist&&blist.size()>0){
			for (YhUserBmiApp yhUserBmiApp : blist) {
				for (String string : regList) {
					if("10083".equals(string)){
						continue;
					}
					YhUserBmiApp record=new YhUserBmiApp();
					record.setEntryTime(addtime);
					record.setuHeight(yhUserBmiApp.getuHeight());
					record.setuWeight(yhUserBmiApp.getuWeight());
					record.setIsDel(2);
					record.seteUserId(Integer.valueOf(string));
					record.setEnv("常规测量");
					record.setDataSource("手动录入");
					yhUserBmiService.insertSelective(record);
				}
			}
		}*/
        /**
         * 血压
         */
		/*List<YhUserBloodpressureApp> bplist = yhUserBloodpressureService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=bplist&&bplist.size()>0){
			 for (YhUserBloodpressureApp yhUserBloodpressureApp : bplist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserBloodpressureApp record=new YhUserBloodpressureApp();
					 record.setEntryTime(addtime);
					 record.seteUserId(Integer.valueOf(string));
					 record.setDbp(yhUserBloodpressureApp.getDbp());
					 record.setSbp(yhUserBloodpressureApp.getSbp());
					 record.setIsDel(2);
					 record.setType("手动录入");
					 record.setTestHj("常规测量");
					 yhUserBloodpressureService.insertSelective(record);
				 }
			}
		}*/
        /**
         * 血糖
         */
		/*List<YhUserBloodsugerApp> bslist = yhUserBloodsugerService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=bslist&&bslist.size()>0){
			for (YhUserBloodsugerApp yhUserBloodsugerApp : bslist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserBloodsugerApp record=new YhUserBloodsugerApp();
					 record.seteUserId(Integer.valueOf(string));
					 record.setEntryTime(addtime);
					 record.setIsDel(2);
					 record.setBs(yhUserBloodsugerApp.getBs());
					 record.setEnv("空腹");
					 record.setDataSource("手动录入");
					 yhUserBloodsugerService.insertSelective(record);
				 }
			}
		}*/
        /**
         * 心率
         */
		/*List<YhUserHeartRateApp> hlist = yhUserHeartRateService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=hlist&&hlist.size()>0){
			for (YhUserHeartRateApp yhUserHeartRateApp : hlist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserHeartRateApp record=new YhUserHeartRateApp();
					 record.setHeartRate(yhUserHeartRateApp.getHeartRate());
					 record.setEntryTime(addtime);
					 record.setIsDel(2);
					 record.setEnv("常规测量");
					 record.setDataSource("手动录入");
					 record.seteUserId(Integer.valueOf(string));
					 yhUserHeartRateService.insertSelective(record);
				 }
			}
		}*/
        /**
         * 体脂
         */
		/*List<YhUserBodyFatApp> bflist = yhUserBodyFatService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=bflist&&bflist.size()>0){
			for (YhUserBodyFatApp yhUserBodyFatApp : bflist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserBodyFatApp record=new YhUserBodyFatApp();
					 record.setFatRate(yhUserBodyFatApp.getFatRate());
					 record.setMuscle(yhUserBodyFatApp.getMuscle());
					 record.setBones(yhUserBodyFatApp.getBones());
					 record.setEntryTime(addtime);
					 record.setIsDel(2);
					 record.seteUserId(Integer.valueOf(string));
					 record.setType("手动录入");
					 yhUserBodyFatService.insertSelective(record);
				 }
			}
		}*/
	/*	List<YhUserBloodfatApp> bllist = yhUserBloodfatService.selectByPrimaryKeyFindEntryTimeAnduserid("",	"10083");
		if(null!=bllist&&bllist.size()>0){
			for (YhUserBloodfatApp yhUserBloodfatApp : bllist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserBloodfatApp record=new YhUserBloodfatApp();
					 record.setCholesterin(yhUserBloodfatApp.getCholesterin());
					 record.setTring(yhUserBloodfatApp.getTring());
					 record.setHdl(yhUserBloodfatApp.getHdl());
					 record.setLdl(yhUserBloodfatApp.getLdl());
					 record.setEntryTime(addtime);
					 record.setIsDel(2);
					 record.seteUserId(Integer.valueOf(string));
					 record.setDataSource("手动录入");
					 yhUserBloodfatService.insertSelective(record);
				 }
			}
		}*/
        /**
         * 肝功能
         */
		   
		/*  List<YhUserLiverfunctionApp> lflist = yhUserLiverfunctionService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		  if(null!=lflist&&lflist.size()>0){
			  for (YhUserLiverfunctionApp yhUserLiverfunctionApp : lflist) {
				  for (String string : regList) {
						 if("10083".equals(string)){
								continue;
							}
						 YhUserLiverfunctionApp record=new YhUserLiverfunctionApp();
						 record.setGuBing(yhUserLiverfunctionApp.getGuBing());
						 record.setMilletStraw(yhUserLiverfunctionApp.getMilletStraw());
						 record.setSerumAlbumin(yhUserLiverfunctionApp.getSerumAlbumin());
						 record.setSerumGlobulin(yhUserLiverfunctionApp.getSerumGlobulin());
						 record.setUserId(Integer.valueOf(string));
						 record.setInserttime(addtime);
						 record.setIsDel(2);
						 record.setDataSource("手动录入");
						 yhUserLiverfunctionService.insertSelective(record);
				  }
			}
		  }*/
        /**
         * 肾功能
         */
		/*  List<YhUserRenalfunctionApp> rflist = yhUserRenalfunctionService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		  if(null!=rflist&&rflist.size()>0){
			  for (YhUserRenalfunctionApp yhUserRenalfunctionApp : rflist) {
				  for (String string : regList) {
						 if("10083".equals(string)){
								continue;
							}
						 YhUserRenalfunctionApp record=new YhUserRenalfunctionApp();
						 record.setCreatinine(yhUserRenalfunctionApp.getCreatinine());
						 record.setUreaNitrogen(yhUserRenalfunctionApp.getUreaNitrogen());
						 record.setUricAcid(yhUserRenalfunctionApp.getUricAcid());
						 record.setUserId(Integer.valueOf(string));
						 record.setInerttime(addtime);
						 record.setDataSource("手动录入");
						 record.setIsDel(2);
						 yhUserRenalfunctionService.insertSelective(record);
				  }
			}
		  }*/
        /**
         * 尿检
         */
		/*List<YhUserUbaApp> ulist = yhUserUbaService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=ulist&&ulist.size()>0){
			for (YhUserUbaApp yhUserUbaApp : ulist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserUbaApp record=new YhUserUbaApp();
					 record.setAdddate(addtime);
					 record.setKetone(yhUserUbaApp.getKetone());
					 record.setRedbloodcell(yhUserUbaApp.getRedbloodcell());
					 record.setUrineprotein(yhUserUbaApp.getUrineprotein());
					 record.seteUserId(Integer.valueOf(string));
					 record.setDataSource("手动录入");
					 record.setIsDel(2);
					 yhUserUbaService.insertSelective(record);
				 }
			}
		}*/
        /**
         * 其他指标
         */
		/*List<YhUserOtherfunctionApp> olist = yhUserOtherfunctionService.selectByPrimaryKeyFindEntryTimeAnduserid("", "10083");
		if(null!=olist&&olist.size()>0){
			for (YhUserOtherfunctionApp yhUserOtherfunctionApp : olist) {
				 for (String string : regList) {
					 if("10083".equals(string)){
							continue;
						}
					 YhUserOtherfunctionApp record=new YhUserOtherfunctionApp();
					 record.setUrineMicroprotein(yhUserOtherfunctionApp.getUrineMicroprotein());
					 record.setGlycosylatedHemoglobin(yhUserOtherfunctionApp.getGlycosylatedHemoglobin());
					 record.setHcy(yhUserOtherfunctionApp.getHcy());
					 record.setUserId(string);
					 record.setInserttime(addtime);
					 record.setIsDel(2);
					 record.setDataSource("手动录入");
					 yhUserOtherfunctionService.insertSelective(record);
				 }
			}
		}*/
        return Result.ok();
    }

    /**
     * BMI饼图赋值 findUserBmiByUserCodeAll
     */
    /**
     * 体温饼图赋值
     */
    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time, Integer page) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timetype) {
            int xiaosohu = 0;
            int zhengchang = 0;
            int chaozhong = 0;
            int feipang = 0;
            String[] split = time.split("~");
            List<YhUserBmiApp> findUserBmiByUserCodeAll = yhUserBmiService.selectByPrimaryKeyBYeid(usercode, timetype, split[0], split[1], page);
            if (null != findUserBmiByUserCodeAll) {
                for (int i = 0; i < findUserBmiByUserCodeAll.size(); i++) {
                    YhUserBmiApp yhUserBmiApp = findUserBmiByUserCodeAll.get(i);
                    Float getuHeight = yhUserBmiApp.getuHeight();
                    Float getuWeight = yhUserBmiApp.getuWeight();
                    Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
                    if (bmi < 18.5) {
                        xiaosohu++;
                    }
                    if (18.5 <= bmi && bmi < 24) {
                        zhengchang++;
                    }
                    if (24 <= bmi && bmi < 28) {
                        chaozhong++;
                    }
                    if (bmi >= 28) {
                        feipang++;
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("xiaosohu", xiaosohu);
            map.put("zhengchang", zhengchang);
            map.put("chaozhong", chaozhong);
            map.put("feipang", feipang);
            return Result.ok(map);


        } else if (1 == timetype) {
            int xiaosohu = 0;
            int zhengchang = 0;
            int chaozhong = 0;
            int feipang = 0;
            List<YhUserBmiApp> findUserBmiByUserCodeAll = yhUserBmiService.selectByPrimaryKeyBYeid(usercode, timetype, time, null, page);
            if (null != findUserBmiByUserCodeAll) {
                for (int i = 0; i < findUserBmiByUserCodeAll.size(); i++) {
                    YhUserBmiApp yhUserBmiApp = findUserBmiByUserCodeAll.get(i);
                    Float getuHeight = yhUserBmiApp.getuHeight();
                    Float getuWeight = yhUserBmiApp.getuWeight();
                    Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
                    if (bmi < 18.5) {
                        xiaosohu++;
                    }
                    if (18.5 <= bmi && bmi < 24) {
                        zhengchang++;
                    }
                    if (24 <= bmi && bmi < 28) {
                        chaozhong++;
                    }
                    if (bmi >= 28) {
                        feipang++;
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("xiaosohu", xiaosohu);
            map.put("zhengchang", zhengchang);
            map.put("chaozhong", chaozhong);
            map.put("feipang", feipang);
            return Result.ok(map);


        } else {

            int xiaosohu = 0;
            int zhengchang = 0;
            int chaozhong = 0;
            int feipang = 0;

            List<YhUserBmiApp> findUserBmiByUserCodeAll = yhUserBmiService.selectByPrimaryKeyBYeid(usercode, timetype, time, null, page);
            if (null != findUserBmiByUserCodeAll) {
                for (int i = 0; i < findUserBmiByUserCodeAll.size(); i++) {
                    YhUserBmiApp yhUserBmiApp = findUserBmiByUserCodeAll.get(i);
                    Float getuHeight = yhUserBmiApp.getuHeight();
                    Float getuWeight = yhUserBmiApp.getuWeight();
                    Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
                    if (bmi < 18.5) {
                        xiaosohu++;
                    }
                    if (18.5 <= bmi && bmi < 24) {
                        zhengchang++;
                    }
                    if (24 <= bmi && bmi < 28) {
                        chaozhong++;
                    }
                    if (bmi >= 28) {
                        feipang++;
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("xiaosohu", xiaosohu);
            map.put("zhengchang", zhengchang);
            map.put("chaozhong", chaozhong);
            map.put("feipang", feipang);
            return Result.ok(map);


        }


    }

    /**
     * 时间段分布
     */
    @RequestMapping(value = "/timePeriodDistribution")
    @ResponseBody
    public Result timePeriodDistribution(String usercode, Integer timeType, String time, Integer page) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timeType) {
            String[] split = time.split("~");
            return Result.ok(yhUserBmiService.findWeightTimeFenBuWeek(split[0], split[1], usercode, page));
        } else if (1 == timeType) {
            return Result.ok(yhUserBmiService.findWeightTimeFenBuMonth(time, usercode, page));
        } else {
            return Result.ok(yhUserBmiService.findWeightTimeFenBuYear(time, usercode, page));
        }
    }


    /**
     * 根据用户查询已经记录的日期
     *
     * @param usercode
     * @return
     */

    @RequestMapping(value = "/findTimeInfoByUserCode")
    @ResponseBody
    public Result findTimeInfoByUserCode(String usercode) throws java.text.ParseException {
        if (null == usercode) {
            return Result.error();
        }
        List<String> selectByPrimaryKeyFindEntryTime = yhUserBmiService.selectByPrimaryKeyFindEntryTime(usercode);
        if (null == selectByPrimaryKeyFindEntryTime) {
            return Result.ok();
        }
        for (int i = 0; i < selectByPrimaryKeyFindEntryTime.size(); i++) {
            String entryTime = selectByPrimaryKeyFindEntryTime.get(i);
            String transferFormat = transferFormat2(entryTime);
            selectByPrimaryKeyFindEntryTime.set(i, transferFormat);

        }
        return Result.ok(selectByPrimaryKeyFindEntryTime);

    }

    /**
     * 根据日期和用户查询详情
     *
     * @param usercode
     * @return
     */
    @RequestMapping(value = "/findInfoByUserCodeAndTime")
    @ResponseBody
    public Result findInfoByUserCodeAndTime(String usercode, String time) {
        if (null == usercode || null == time) {
            Result.error();
        }
        List<YhUserBmiApp> list = yhUserBmiService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                YhUserBmiApp yhUserBmiApp = list.get(i);
                yhUserBmiApp.setOpenNo(false);
                if (i == 0) {
                    yhUserBmiApp.setOpenNo(true);
                }
                Float aFloat = yhUserBmiApp.getuWeight();
                Float aFloat1 = yhUserBmiApp.getuHeight() / 100;
                double bmi = (aFloat / (aFloat1 * aFloat1));
                bmi = new BigDecimal(bmi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                yhUserBmiApp.setBmi(bmi);
                if (bmi < 18.5) {
                    yhUserBmiApp.setJiedu("您的BMI显示过轻，建议您均衡饮食并渐进式的增加食欲，只有摄入的能量大于消耗的能量，体重才能增加。");
                    yhUserBmiApp.setTiwen("消瘦");
                    yhUserBmiApp.setProgressvalue(12.5);
                } else if (bmi >= 18.5 && bmi <= 23.9) {
                    yhUserBmiApp.setJiedu("您的BMI正常，非常棒，请持续控制体重。");
                    yhUserBmiApp.setTiwen("正常");
                    yhUserBmiApp.setProgressvalue(37.5);
                } else if (bmi >= 24 && bmi < 28) {
                    yhUserBmiApp.setJiedu("您已超重了，健康问题正随着您体重的增加而增加，患糖尿病、心脏病、胆固醇过高和高血压的风险正在逐渐加大。建议改变生活方式以起到控制甚至减轻体重的目的。");
                    yhUserBmiApp.setTiwen("超重");
                    yhUserBmiApp.setProgressvalue(62.5);
                } else {
                    yhUserBmiApp.setJiedu("您被封为肥胖“达人”了，需要减重。在均衡膳食的同时，应通过减少热量的摄入，改善饮食结构、较高频率的中等强度身体锻炼等方式减轻体重。");
                    yhUserBmiApp.setTiwen("肥胖");
                    yhUserBmiApp.setProgressvalue(87.5);
                }
            }
        }
        return Result.ok(list);

    }

    @RequestMapping(value = "/modifyBmiInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserBmiApp record) {
        if (null == record || null == record.getuBmiId()) {
            return Result.error();
        }
        int updateByExampleSelective = yhUserBmiService.updateByExampleSelective(record);
        System.out.println("修改返回：" + updateByExampleSelective);
        return Result.ok();

    }

    /**
     * 根据出生算年龄
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

    @RequestMapping(value = "/findMonitorToDay")
    @ResponseBody
    public Result findMonitorToDay(String eid) throws java.text.ParseException {
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(eid);

        MonitorToDayApp mtd = new MonitorToDayApp();
        int allzhengchang = 0;// 正常数
        int allyichang = 0;// 异常数
        List<String> zclist = new ArrayList<>();
        List<String> yclist = new ArrayList<>();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        // 获得最新血氧
        YhUserOxygenApp selectByPrimaryKeyBytime = yhUserOxygenService.selectByPrimaryKeyBytime(eid);

        if (null != selectByPrimaryKeyBytime) {
            Float oxygen = selectByPrimaryKeyBytime.getOxygen();
            if (null != oxygen) {
                if (oxygen >= 95) {
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setXueyangsituation(1);
                    zclist.add("血氧");
                } else {
                    mtd.setAllyichang(++allyichang);
                    mtd.setXueyangsituation(2);
                    yclist.add("血氧");
                }
                mtd.setOxygen(selectByPrimaryKeyBytime.getOxygen());
                try {
                    Date date = format.parse(selectByPrimaryKeyBytime.getEntryTime());
                    mtd.setOxygentime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setXueyangsituation(2);
                yclist.add("血氧");
                mtd.setOxygen(0f);
                try {
                    Date date = format.parse(selectByPrimaryKeyBytime.getEntryTime());
                    mtd.setOxygentime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }
        //获取最新动脉硬化
        YhUserArtery newInfoByUserCode = yhUserArteryService.findNewInfoByUserCode(eid);
        if (null != newInfoByUserCode) {
            int ageByBirth = getAgeByBirth(info.getBirthDate());
            Integer avi = newInfoByUserCode.getAvi();
            int aviStatus = 0;
            Integer api = newInfoByUserCode.getApi();
            int apiStatus = 0;
            int avizhibiao = 0;
            int apizhibiao = 0;

            if (null != avi && null != api) {
                mtd.setAvi(avi);
                mtd.setApi(api);
                mtd.setCsbp(newInfoByUserCode.getCsbp());
                mtd.setCapp(newInfoByUserCode.getCapp());
                if (ageByBirth <= 23) {
                    avizhibiao = 15;
                } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                    avizhibiao = 16;
                } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                    avizhibiao = 17;
                } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                    avizhibiao = 18;
                } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                    avizhibiao = 19;
                } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                    avizhibiao = 20;
                } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                    avizhibiao = 21;
                } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                    avizhibiao = 22;
                } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                    avizhibiao = 23;
                } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                    avizhibiao = 24;
                } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                    avizhibiao = 25;
                } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                    avizhibiao = 26;
                } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                    avizhibiao = 27;
                } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                    avizhibiao = 28;
                } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                    avizhibiao = 29;
                } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                    avizhibiao = 30;
                } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                    avizhibiao = 31;
                } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                    avizhibiao = 32;
                } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                    avizhibiao = 33;
                } else if (ageByBirth == 73) {
                    avizhibiao = 34;
                } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                    avizhibiao = 35;
                } else if (ageByBirth == 76) {
                    avizhibiao = 36;
                } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                    avizhibiao = 37;
                } else if (ageByBirth == 79) {
                    avizhibiao = 38;
                } else if (ageByBirth > 80) {
                    avizhibiao = 39;
                }

                if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                    aviStatus = 1;
                }
                if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                    aviStatus = 2;
                }
                if (avi > (avizhibiao * 0.5)) {
                    aviStatus = 3;
                }


                if (ageByBirth <= 26) {
                    apizhibiao = 22;
                } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                    apizhibiao = 23;
                } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                    apizhibiao = 24;
                } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                    apizhibiao = 25;
                } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                    apizhibiao = 26;
                } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                    apizhibiao = 27;
                } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                    apizhibiao = 28;
                } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                    apizhibiao = 29;
                } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                    apizhibiao = 30;
                } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                    apizhibiao = 31;
                } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                    apizhibiao = 32;
                } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                    apizhibiao = 33;
                } else if (ageByBirth >= 80) {
                    apizhibiao = 33;
                }
                if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                    apiStatus = 1;
                }
                if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                    apiStatus = 2;
                }
                if (api > (apizhibiao * 0.5)) {
                    apiStatus = 3;
                }

                if(apiStatus>0||aviStatus>0){
                    mtd.setAllyichang(++allyichang);
                    mtd.setDongmaisituation(2);
                    yclist.add("动脉硬化");
                }else{
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setDongmaisituation(1);
                    zclist.add("动脉硬化");
                }
                try {
                    Date date = format.parse(newInfoByUserCode.getTime());
                    mtd.setDongmaiTime(format(date));

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

        }

        YhUserTemperatureApp selectByPrimaryKeyOrderTime = yhUserTemperatureService.selectByPrimaryKeyOrderTime(eid);// 体温
        // 获取最新体温
        if (null != selectByPrimaryKeyOrderTime) {
            Float temperature = selectByPrimaryKeyOrderTime.getTemperature();
            if (null != temperature) {
                if (null != temperature) {
                    if (temperature >= 36 && temperature <= 37.2) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setTiwensituation(1);
                        zclist.add("体温");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setTiwensituation(2);
                        yclist.add("体温");
                    }
                }
                mtd.setTemperature(temperature);
                try {
                    Date date = format.parse(selectByPrimaryKeyOrderTime.getEntryTime());
                    mtd.setTemperatureTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setTiwensituation(2);
                yclist.add("体温");
                mtd.setTemperature(0f);
                try {
                    Date date = format.parse(selectByPrimaryKeyOrderTime.getEntryTime());
                    mtd.setTemperatureTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }


        }
        YhUserBmiApp selectByPrimaryKeyNewest = yhUserBmiService.selectByPrimaryKeyNewest(eid);// bmi
        // 获取最新bmi
        if (null != selectByPrimaryKeyNewest) {
            Float getuWeight = selectByPrimaryKeyNewest.getuWeight();
            Float getuHeight = selectByPrimaryKeyNewest.getuHeight();
            if (null != getuWeight && getuWeight > 0 && null != getuHeight && getuHeight > 0) {
                Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
                if (null != getuWeight && null != getuHeight) {
                    if (bmi >= 18.5 && bmi < 24) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setTizhongsituation(1);
                        zclist.add("bmi");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setTizhongsituation(2);
                        yclist.add("bmi");
                    }
                }
                mtd.setuWeight(selectByPrimaryKeyNewest.getuWeight());
                mtd.setuHeight(selectByPrimaryKeyNewest.getuHeight());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest.getEntryTime());
                    mtd.setBmiEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setTizhongsituation(2);
                yclist.add("bmi");
                if (null == getuWeight) {
                    mtd.setuWeight(0f);
                } else {
                    mtd.setuWeight(getuWeight);
                }
                if (null == getuHeight) {
                    mtd.setuHeight(0f);
                } else {
                    mtd.setuHeight(getuHeight);
                }

                try {
                    Date date = format.parse(selectByPrimaryKeyNewest.getEntryTime());
                    mtd.setBmiEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }
        YhUserBloodpressureApp selectByPrimaryKeyNewest2 = yhUserBloodpressureService.selectByPrimaryKeyNewest(eid);// 血压

        // 获取最新血压
        if (null != selectByPrimaryKeyNewest2) {
            Integer sbp = selectByPrimaryKeyNewest2.getSbp();
            Integer dbp = selectByPrimaryKeyNewest2.getDbp();
            if (null != sbp && null != dbp) {
                if (sbp < 90 && dbp < 60) {
                    mtd.setAllyichang(++allyichang);
                    mtd.setXueyasituation(2);
                    yclist.add("血压");
                } else if (sbp < 120 && dbp < 80) {
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setXueyasituation(1);
                    zclist.add("血压");
                } else {
                    mtd.setAllyichang(++allyichang);
                    mtd.setXueyasituation(2);
                    yclist.add("血压");
                }
                mtd.setSbp(selectByPrimaryKeyNewest2.getSbp());
                mtd.setDbp(selectByPrimaryKeyNewest2.getDbp());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest2.getEntryTime());
                    mtd.setBloodPressureEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }

            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setXueyasituation(2);
                yclist.add("血压");
                if (null == sbp) {
                    mtd.setSbp(0);
                } else {
                    mtd.setSbp(sbp);
                }
                if (null == dbp) {
                    mtd.setDbp(0);
                } else {
                    mtd.setDbp(dbp);
                }
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest2.getEntryTime());
                    mtd.setBloodPressureEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

        }
        // 心率
        YhUserHeartRateApp selectByPrimaryKeyOrderTime2 = yhUserHeartRateService.selectByPrimaryKeyOrderTime(eid);
        // 获取最新心率
        if (null != selectByPrimaryKeyOrderTime2) {
            Integer heartRate = selectByPrimaryKeyOrderTime2.getHeartRate();
            if (null != heartRate) {
                if (heartRate >= 60 && heartRate <= 100) {
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setXinlvsituation(1);
                    zclist.add("心率");
                } else {
                    mtd.setAllyichang(++allyichang);
                    mtd.setXinlvsituation(2);
                    yclist.add("心率");
                }
                mtd.setXinlv(selectByPrimaryKeyOrderTime2.getHeartRate());
                try {
                    Date datexl = format.parse(selectByPrimaryKeyOrderTime2.getEntryTime());
                    mtd.setXinlvEntryTime(format(datexl));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setXinlvsituation(2);
                yclist.add("心率");
                mtd.setXinlv(0);
                try {
                    Date datexl = format.parse(selectByPrimaryKeyOrderTime2.getEntryTime());
                    mtd.setXinlvEntryTime(format(datexl));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }


        }
        YhUserBloodsugerApp selectByPrimaryKeyNewest3 = yhUserBloodsugerService.selectByPrimaryKeyNewest(eid);// 血糖
        // 获取最新血糖数据
        if (null != selectByPrimaryKeyNewest3) {
            Float bs = selectByPrimaryKeyNewest3.getBs();
            if (null != bs) {
                String env = selectByPrimaryKeyNewest3.getEnv();
                if (env.contains("空腹")) {
                    if (bs < 6.1 && bs >= 3.9) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setXuetangsituation(1);
                        zclist.add("血糖");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setXuetangsituation(2);
                        yclist.add("血糖");
                    }
                } else {
                    if (bs < 7.8) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setXuetangsituation(1);
                        zclist.add("血糖");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setXuetangsituation(2);
                        yclist.add("血糖");
                    }
                }

                mtd.setBs(selectByPrimaryKeyNewest3.getBs());
                mtd.setBsType(selectByPrimaryKeyNewest3.getEnv());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest3.getEntryTime());
                    mtd.setBloodSugerEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setXuetangsituation(2);
                yclist.add("血糖");
                mtd.setBs(0f);
                mtd.setBsType(selectByPrimaryKeyNewest3.getEnv());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest3.getEntryTime());
                    mtd.setBloodSugerEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }


        }
        // 体脂
        YhUserBodyFatApp selectByPrimaryKeyNewest4 = yhUserBodyFatService.selectByPrimaryKeyNewest(eid);
        if (null != selectByPrimaryKeyNewest4) {
            Integer gender = info.getGender();
            Float fatRate = selectByPrimaryKeyNewest4.getFatRate();
            if (null != fatRate) {
                if (gender == 1) {
                    if (fatRate >= 15 && fatRate <= 18) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setTizhisituation(1);
                        zclist.add("体脂");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setTizhisituation(2);
                        yclist.add("体脂");
                    }
                } else {
                    if (fatRate >= 20 && fatRate <= 25) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setTizhisituation(1);
                        zclist.add("体脂");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setTizhisituation(2);
                        yclist.add("体脂");

                    }
                }
                mtd.setFatRate(selectByPrimaryKeyNewest4.getFatRate());
                mtd.setMuscle(selectByPrimaryKeyNewest4.getMuscle());
                mtd.setBones(selectByPrimaryKeyNewest4.getBones());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest4.getEntryTime());
                    mtd.setBodyFatEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }

            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setTizhisituation(2);
                yclist.add("体脂");
                mtd.setFatRate(selectByPrimaryKeyNewest4.getFatRate());
                mtd.setMuscle(selectByPrimaryKeyNewest4.getMuscle());
                mtd.setBones(selectByPrimaryKeyNewest4.getBones());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest4.getEntryTime());
                    mtd.setBodyFatEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }
        YhUserBloodfatApp selectByPrimaryKeyNewest5 = yhUserBloodfatService.selectByPrimaryKeyNewest(eid);// 血脂
        // 获取最新血脂
        if (null != selectByPrimaryKeyNewest5) {
            Float chol = selectByPrimaryKeyNewest5.getCholesterin();
            Float tring = selectByPrimaryKeyNewest5.getTring();
            Float hdl = selectByPrimaryKeyNewest5.getHdl();
            Float ldl = selectByPrimaryKeyNewest5.getLdl();
            if (null != chol && null != tring && null != hdl && null != ldl) {
                if (chol < 5.18 && tring < 1.7 && hdl >= 1.04 && ldl < 3.37) {
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setXuezhisituation(1);
                    zclist.add("血脂");
                } else {
                    mtd.setAllyichang(++allyichang);
                    mtd.setXuezhisituation(2);
                    yclist.add("血脂");
                }
                mtd.setCholesterin(selectByPrimaryKeyNewest5.getCholesterin());
                mtd.setTring(selectByPrimaryKeyNewest5.getTring());
                mtd.setHdl(selectByPrimaryKeyNewest5.getHdl());
                mtd.setLdl(selectByPrimaryKeyNewest5.getLdl());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest5.getEntryTime());
                    mtd.setBloodfatEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setXuezhisituation(2);
                yclist.add("血脂");
                mtd.setCholesterin(selectByPrimaryKeyNewest5.getCholesterin());
                mtd.setTring(selectByPrimaryKeyNewest5.getTring());
                mtd.setHdl(selectByPrimaryKeyNewest5.getHdl());
                mtd.setLdl(selectByPrimaryKeyNewest5.getLdl());
                try {
                    Date date = format.parse(selectByPrimaryKeyNewest5.getEntryTime());
                    mtd.setBloodfatEntryTime(format(date));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }
        YhUserRenalfunctionApp selectByPrimaryKeyNewest6 = yhUserRenalfunctionService.selectByPrimaryKeyNewest(eid);// 肾功能
        // 获取最新肾功能
        if (null != selectByPrimaryKeyNewest6) {
            mtd.setRenalfunctionType(selectByPrimaryKeyNewest6.getDataSource());
            String creatinine = selectByPrimaryKeyNewest6.getCreatinine();
            double creatinineDou = 0;
            if (StringUtil.isNotEmpty(creatinine)) {
                creatinineDou = Double.valueOf(creatinine);
            }
            String ureaNitrogen = selectByPrimaryKeyNewest6.getUreaNitrogen();
            double ureaNitrogenDou = 0;
            if (StringUtil.isNotEmpty(ureaNitrogen)) {
                ureaNitrogenDou = Double.valueOf(ureaNitrogen);
            }
            String uricAcid = selectByPrimaryKeyNewest6.getUricAcid();
            double uricAcidDou = 0;
            if (StringUtil.isNotEmpty(uricAcid)) {
                uricAcidDou = Double.valueOf(uricAcid);
            }
            Integer gender = info.getGender();
            String dataSource = selectByPrimaryKeyNewest6.getDataSource();
            if (dataSource.equals("一体机")  || dataSource.equals("随诊包")|| dataSource.equals("中科善益康S5")){
                if(StringUtil.isNotEmpty(uricAcid)){
                    if (gender== 1) {
                        if ( Double.valueOf(uricAcid)  < 210 ||  Double.valueOf(uricAcid)  > 420) {
                            mtd.setAllyichang(++allyichang);
                            mtd.setShengnsituation(2);
                            yclist.add("肾功能");
                        }else{
                            mtd.setAllzhengchang(++allzhengchang);
                            mtd.setShengnsituation(1);
                            zclist.add("肾功能");
                        }
                    }else{
                        if ( Double.valueOf(uricAcid)  < 150 ||  Double.valueOf(uricAcid)  > 360) {
                            mtd.setAllyichang(++allyichang);
                            mtd.setShengnsituation(2);
                            yclist.add("肾功能");
                        }else{
                            mtd.setAllzhengchang(++allzhengchang);
                            mtd.setShengnsituation(1);
                            zclist.add("肾功能");
                        }
                    }
                }else{
                    mtd.setAllyichang(++allyichang);
                    mtd.setShengnsituation(2);
                    yclist.add("肾功能");
                }
            }else{
                if (gender == 1) {
                    if (creatinineDou >= 54 && creatinineDou <= 106 && ureaNitrogenDou >= 2.9 && ureaNitrogenDou <= 7.5
                            && uricAcidDou >= 210 && uricAcidDou <= 420) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setShengnsituation(1);
                        zclist.add("肾功能");
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setShengnsituation(2);
                        yclist.add("肾功能");
                    }
                } else {
                    if (creatinineDou >= 44 && creatinineDou <= 97 && ureaNitrogenDou >= 2.9 && ureaNitrogenDou <= 7.5
                            && uricAcidDou >= 150 && uricAcidDou <= 360) {
                        mtd.setAllzhengchang(++allzhengchang);
                        mtd.setShengnsituation(1);
                    } else {
                        mtd.setAllyichang(++allyichang);
                        mtd.setShengnsituation(2);
                    }
                }
            }

            mtd.setCreatinine(selectByPrimaryKeyNewest6.getCreatinine());
            mtd.setUreaNitrogen(selectByPrimaryKeyNewest6.getUreaNitrogen());
            mtd.setUricAcid(selectByPrimaryKeyNewest6.getUricAcid());
            try {
                Date date = format.parse(selectByPrimaryKeyNewest6.getInerttime());
                mtd.setRenalEntryTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        YhUserLiverfunctionApp selectByPrimaryKeyNewest7 = yhUserLiverfunctionService.selectByPrimaryKeyNewest(eid);// 肝功能
        // 获取最新肝功能
        if (null != selectByPrimaryKeyNewest7) {
            String guBing = selectByPrimaryKeyNewest7.getGuBing();
            String milletStraw = selectByPrimaryKeyNewest7.getMilletStraw();
            String serumAlbumin = selectByPrimaryKeyNewest7.getSerumAlbumin();
            String serumGlobulin = selectByPrimaryKeyNewest7.getSerumGlobulin();
            if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw)
                    && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                double guBingDou = Double.valueOf(guBing);
                double milletStrawDou = Double.valueOf(milletStraw);
                double serumAlbuminDou = Double.valueOf(serumAlbumin);
                double serumGlobulinDou = Double.valueOf(serumGlobulin);
                if (guBingDou >= 0 && guBingDou <= 40 && milletStrawDou >= 0 && milletStrawDou <= 40
                        && serumAlbuminDou >= 35 && serumAlbuminDou <= 51 && serumGlobulinDou >= 20
                        && serumGlobulinDou <= 30) {
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setGangnsituation(1);
                    zclist.add("肝功能");
                } else {
                    mtd.setAllyichang(++allyichang);
                    mtd.setGangnsituation(2);
                    yclist.add("肝功能");
                }
            }

            mtd.setGuBing(selectByPrimaryKeyNewest7.getGuBing());
            mtd.setMilletStraw(selectByPrimaryKeyNewest7.getMilletStraw());
            mtd.setSerumAlbumin(selectByPrimaryKeyNewest7.getSerumAlbumin());
            mtd.setSerumGlobulin(selectByPrimaryKeyNewest7.getSerumGlobulin());
            try {
                Date date = format.parse(selectByPrimaryKeyNewest7.getInserttime());
                mtd.setLiverEntryTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        YhUserOtherfunctionApp selectByPrimaryKeyNewest8 = yhUserOtherfunctionService.selectByPrimaryKeyNewest(eid);// 其他功能
        // 获取最新其他功能数据
        if (null != selectByPrimaryKeyNewest8) {
            String urineMicroprotein = selectByPrimaryKeyNewest8.getUrineMicroprotein();
            String glycosylatedHemoglobin = selectByPrimaryKeyNewest8.getGlycosylatedHemoglobin();
            String hcy = selectByPrimaryKeyNewest8.getHcy();
            if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin)
                    && StringUtil.isNotEmpty(hcy)) {
                double urineMicroproteinDou = Double.valueOf(urineMicroprotein);
                double glycosylatedHemoglobinDou = Double.valueOf(glycosylatedHemoglobin);
                double hcyDou = Double.valueOf(hcy);
                if (urineMicroproteinDou >= 0.49 && urineMicroproteinDou <= 2.05 && glycosylatedHemoglobinDou >= 4 && glycosylatedHemoglobinDou <= 6 && hcyDou >= 5 && hcyDou <= 15) {
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setQitasituation(1);
                    zclist.add("其他功能");
                } else {
                    mtd.setAllyichang(++allyichang);
                    mtd.setQitasituation(2);
                    yclist.add("其他功能");
                }
            }

            mtd.setUrineMicroprotein(selectByPrimaryKeyNewest8.getUrineMicroprotein());
            mtd.setGlycosylatedHemoglobin(selectByPrimaryKeyNewest8.getGlycosylatedHemoglobin());
            mtd.setHcy(selectByPrimaryKeyNewest8.getHcy());
            try {
                Date date = format.parse(selectByPrimaryKeyNewest8.getInserttime());
                mtd.setOtherEntryTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        YhUserEcgApp selectByPrimaryKeyOrderByTimeLimit1 = yhUserEcgService.selectByPrimaryKeyOrderByTimeLimit1(eid);// 心电图
        if (null != selectByPrimaryKeyOrderByTimeLimit1) {
            mtd.setEcgImage(selectByPrimaryKeyOrderByTimeLimit1.getPigPath());
            mtd.setResurtText(selectByPrimaryKeyOrderByTimeLimit1.getResurtText());
            try {
                Date date = format.parse(selectByPrimaryKeyOrderByTimeLimit1.getEntryTime());
                mtd.setEcgEntryTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //细胞检测
        YhUserCellDetectionWeb yhUserCellDetectionWeb = YhUserCellDetectionWebMapper.selectByPrimaryKeyNewInfo(eid);
        if (null != yhUserCellDetectionWeb) {
            mtd.setCelldetection(yhUserCellDetectionWeb.getImg());
            try {
                Date date = format.parse(yhUserCellDetectionWeb.getCtime());
                mtd.setCelldetectionTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //量子检测
        YhUserQuantumDetectionWeb yhUserQuantumDetectionWeb = YhUserQuantumDetectionWebMapper.selectByPrimaryKeyNewInfo(eid);
        if (null != yhUserQuantumDetectionWeb) {
            mtd.setQuantumdetection(yhUserQuantumDetectionWeb.getImg());
            try {
                Date date = format.parse(yhUserQuantumDetectionWeb.getQtime());
                mtd.setQuantumdetectionTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //血红蛋白
        YhUserHemoglobin hemoglobinNewInfo = YhUserHemoglobinMapper.findHemoglobinNewInfo(eid);
        if (null != hemoglobinNewInfo) {
            Date heData = hemoglobinNewInfo.getHeData();
            Float heHb = hemoglobinNewInfo.getHeHb();
            mtd.setXhdbEntryTime(format(heData));
            Integer gender = info.getGender();
            mtd.setHemoglobin(heHb);
            //男
            if(gender==1){
                if(heHb>=13&&heHb<=18){
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setXhdbsituation(1);
                    zclist.add("血红蛋白");
                }else{
                    mtd.setAllyichang(++allyichang);
                    mtd.setXhdbsituation(2);
                    yclist.add("血红蛋白");
                }
            }else{
                if(heHb>=12&&heHb<=16){
                    mtd.setAllzhengchang(++allzhengchang);
                    mtd.setXhdbsituation(1);
                    zclist.add("血红蛋白");
                }else{
                    mtd.setAllyichang(++allyichang);
                    mtd.setXhdbsituation(2);
                    yclist.add("血红蛋白");
                }
            }
        }
        // 尿检
        YhUserUbaApp selectByPrimaryKeyNewest9 = yhUserUbaService.selectByPrimaryKeyNewest(eid);
        if (null != selectByPrimaryKeyNewest9) {
            String whitebloodcell = selectByPrimaryKeyNewest9.getWhitebloodcell();
            String urineprotein = selectByPrimaryKeyNewest9.getUrineprotein();
            String ketone = selectByPrimaryKeyNewest9.getKetone();
              String redbloodcell = selectByPrimaryKeyNewest9.getRedbloodcell();
            if ("-".equals(whitebloodcell) && "-".equals(urineprotein) && "-".equals(ketone)&& "-".equals(redbloodcell)) {
                mtd.setAllzhengchang(++allzhengchang);
                mtd.setNiaojiansituation(1);
                zclist.add("尿检");
            } else {
                mtd.setAllyichang(++allyichang);
                mtd.setNiaojiansituation(2);
                yclist.add("尿检");
            }

            mtd.setWhitebloodcell(selectByPrimaryKeyNewest9.getWhitebloodcell());
            mtd.setUrineprotein(selectByPrimaryKeyNewest9.getUrineprotein());
            mtd.setKetone(selectByPrimaryKeyNewest9.getKetone());
            mtd.setRedbloodcell(selectByPrimaryKeyNewest9.getRedbloodcell());
            try {
                Date date = format.parse(selectByPrimaryKeyNewest9.getAdddate());
                mtd.setUbaEntryTime(format(date));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        for (int i = 0; i < zclist.size(); i++) {
            System.out.println("正常" + zclist.get(i));
        }
        for (int i = 0; i < yclist.size(); i++) {
            System.out.println("异常" + yclist.get(i));
        }
        if (null != mtd) {
            return Result.ok(mtd);
        }

        return Result.error();
    }

    /**
     * 根据主键查询bmi信息
     *
     * @param id
     * @return
     */

    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        YhUserBmiApp selectByPrimaryKey = yhUserBmiService.selectByPrimaryKey(id);
        return Result.ok(selectByPrimaryKey);
    }

    /**
     * 增删改
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserBmiApp record, HttpServletRequest request) {
        if (null == record || null == record.getuBmiId()) {
            return Result.error();
        }
        // 修改
        if (null != record.getuBmiId() && 0 != record.getuBmiId()) {
            record.setEntryTime(null);

            int updateByExampleSelective = yhUserBmiService.updateByPrimaryKeySelective(record);

            try {
                Float v = record.getuWeight() / ((record.getuHeight() / 100) * (record.getuHeight() / 100));

                yhHeThresholdService.selectBythresType("BMI", String.valueOf(record.geteUserId()), String.format("%.1f", v), "BMI", request);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            System.out.println(record);
            record.setIsDel(2);
            if (record.getEnv() == null) {
                record.setEnv("常规测量");
            }
            record.setEntryTime(record.getEntryTime() + NowString());

            int insertSelective = yhUserBmiService.insertSelective(record);
            try {
                Float v = record.getuWeight() / ((record.getuHeight() / 100) * (record.getuHeight() / 100));
                yhHeThresholdService.selectBythresType("BMI", String.valueOf(record.geteUserId()), String.format("%.1f", v), "BMI", request);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
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

        List<YhUserBmiApp> selectByPrimaryKeyByUserIdLimit7 = yhUserBmiService.selectByPrimaryKeyByUserIdLimit7(eid);

        if (null != selectByPrimaryKeyByUserIdLimit7) {
            return Result.ok(selectByPrimaryKeyByUserIdLimit7);
        }

        return Result.error();
    }

    @RequestMapping(value = "/findInfoUserBmiByEid")
    @ResponseBody
    public Result findInfoUserBmiByEid(String eid) {
        System.out.println(eid);

        YhUserBmiApp selectByPrimaryKeyNewest = yhUserBmiService.selectByPrimaryKeyNewest(eid);// bmi

        return Result.ok(selectByPrimaryKeyNewest);
    }

    @RequestMapping(value = "/findMonthInfo")
    @ResponseBody
    public Result findMonthInfo(String eid, String ye, String mo) throws java.text.ParseException {

        if (null == ye && null == mo) {
            ye = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            mo = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        }
        String shijian = ye + "-" + mo + "-01";
        System.out.println(eid + "" + shijian);
        List<YhUserBmiApp> monthInfoList = yhUserBmiService.selectByPrimaryKeyByMonthInfo(eid, shijian);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < monthInfoList.size(); i++) {
            YhUserBmiApp yhUserBmi = monthInfoList.get(i);
            if (null != yhUserBmi) {
                if (null != yhUserBmi.getEntryTime()) {
                    String entryTime = yhUserBmi.getEntryTime();
                    Date date = sf.parse(entryTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    list.add(day);
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
    public Result findInfoByeidAll(String usercode, @RequestParam(required = false, defaultValue = "0") Integer timeType, String time, Integer page) throws ParseException, java.text.ParseException {
        int pageSize = 7;
        Map<String, Object> map = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        if (2 == timeType) {
            List<YhUserBmiApp> list = yhUserBmiService.findWeightByYear(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserBmiApp yhUserBmi : list) {
                    String entryTime = yhUserBmi.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserBmi.getuWeight()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        } else if (1 == timeType) {
            List<YhUserBmiApp> list = yhUserBmiService.findWeightByMonth(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserBmiApp yhUserBmi : list) {
                    String entryTime = yhUserBmi.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserBmi.getuWeight()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        } else if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserBmiApp> list = yhUserBmiService.findWeightByWeek(split[0], split[1], usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserBmiApp yhUserBmi : list) {
                    String entryTime = yhUserBmi.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserBmi.getuWeight()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        }

        return Result.error();
    }

    public static String transferFormat2(String inTime) throws java.text.ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s1.format(tempDate);
        return outTime;
    }

    public static String transferFormat(String inTime) throws java.text.ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s2.format(s2.parse(s1.format(tempDate)));
        return outTime;
    }

    public static void main(String[] args) throws java.text.ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2019-12-26 15:35:35");
        System.out.println(format(date));
    }

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

}