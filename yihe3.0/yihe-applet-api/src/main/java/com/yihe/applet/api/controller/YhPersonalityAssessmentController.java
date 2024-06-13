package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.WellPressureEvaluateApp;
import com.yihe.applet.api.domain.YhPersonalityAssessmentApp;
import com.yihe.applet.api.domain.YhPressureEvaluateApp;
import com.yihe.applet.api.service.YhPersonalityAssessmentAppService;
import com.yihe.applet.api.service.YhPressureEvaluateAppService;
import com.yihe.applet.api.util.common.util.DateUtil;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.domain.YhManageTask;
import com.yihe.system.service.YhTaskServiceAPIService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * YhPersonalityAssessmentcontroller Created by dyb on 2020/2/20.
 */
@Controller  
@RequestMapping("/api/YhPersonalityAssessment")
@Api(value = "YhPersonalityAssessment控制器", description = "YhPersonalityAssessment管理")
public class YhPersonalityAssessmentController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhPersonalityAssessmentController.class);
 
	@Autowired
	YhPersonalityAssessmentAppService yhPersonalityAssessmentService;
	@Autowired
	 YhPressureEvaluateAppService  yhPressureEvaluateAppService;
	
	@Resource
    YhTaskServiceAPIService yhtaskservuceapiservice;     

	@RequestMapping(value = "/findInfoAll")
	@ResponseBody
	public Result findInfoAll() {
		List<YhPersonalityAssessmentApp> selectByExample = yhPersonalityAssessmentService.selectAll();
		return Result.ok(selectByExample);
	}
	
	@RequestMapping(value = "/findcount")
	@ResponseBody
	public Result findcount(String userCode) {
		List<YhPressureEvaluateApp> selectCount = yhPressureEvaluateAppService.selectCount(userCode);
		return Result.ok(selectCount);
	}

	@RequestMapping(value = "/addPressureEvaluate")
	@ResponseBody
	public Result addPressureEvaluate(Integer type, String list,String select, String userCode) throws ParseException {
		//压力评估实体类
		WellPressureEvaluateApp wellPressureEvaluate = new WellPressureEvaluateApp();
		System.err.println(list);
		String queryEvaluateScore = wellPressureEvaluate.queryEvaluateScore(list, type);
		YhPressureEvaluateApp record = new YhPressureEvaluateApp();

		record.setEvaluateData(list);
		record.setEvaluateDate(DateUtil.getNowTime());
		record.setEvaluateScore(queryEvaluateScore);
		record.setEvaluateSelect(select);
		record.setEvaluateType(type);
		record.setIsDel(2);
		record.setUserCode(userCode);
		int insertSelective = yhPressureEvaluateAppService.insertSelective(record);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createdate = sdf.format(date);
        YhJzServiceBodydataSwitch ok=null;
        if(type==1) {
        	ok =yhtaskservuceapiservice.queryswitchbyid1(7,2, "A型性格评估");
        }
        if(type==2) {
        	ok =yhtaskservuceapiservice.queryswitchbyid1(7,2, "PSTRI压力评估");
        }
        if(type==3) {
        	ok =yhtaskservuceapiservice.queryswitchbyid1(7,2, "心理健康评估");
        }
        if(ok.getEvery_switch()==1) {
            YhManageTask task=new YhManageTask();
            task.setUsercode(userCode);
            Date d = null;
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sd.parse(createdate);
            long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); 
            String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
            task.setService_waiting_time(newtime);
            task.setService_type(7);
            task.setService_content(ok.getService_content());
            
            if(type==1) {
            	 task.setService_record("完成A型性格评估");
            	
            }
            if(type==2) {
            	task.setService_record("完成PSTRI压力评估");
            	
            }
            if(type==3) {
            	task.setService_record("完成心理健康评估");
            	
            }
            
            
            task.setService_record("完成压力评估");
            task.setReturnvisit(ok.getReturnvisit());
            task.setChildnodes(ok.getId());
            task.setChildtag("2");
            yhtaskservuceapiservice.insertperson(task);
        }
		
		return Result.ok(record);
	}

	public static boolean checkStr(String str) {
		return str != null && !"".equals(str);
	}

	@RequestMapping(value = "/findPersonalityAssessmentByType")
	@ResponseBody
	public Result findPersonalityAssessmentByType(Integer type, String userCode) {
		YhPressureEvaluateApp yhPressureEvaluateApp = new YhPressureEvaluateApp();
		/*createCriteria.andEvaluateTypeEqualTo(type);
		createCriteria.andUserCodeEqualTo(userCode);*/
	 List<YhPressureEvaluateApp> selectAlList = yhPressureEvaluateAppService.selectAlList(type, userCode);
		Collections.reverse(selectAlList);// 反转List列表中元素的顺序
		if (1 == type) {
			for (int i = 0; i < selectAlList.size(); i++) {
				String xgtype = "";
				String evaluateScore = selectAlList.get(i).getEvaluateScore();
				// 获取评估分数
				String score[] = evaluateScore.split(";");
				int int_score[] = new int[3];
				int step = 0;
				for (int j = 0; j < score.length; j++)
					if (checkStr(score[j])) {
						int_score[step] = Integer.parseInt(score[j]);
						step++;
					}
				// 计算L量表得分
				Integer l_score = Integer.valueOf(int_score[0]);
				// 计算性格评估量表得分
				Integer s_score = Integer.valueOf(int_score[0] + int_score[1] + int_score[2]);
				if (s_score < 20) {
					xgtype = "B型";
					selectAlList.get(i).setXgtype(xgtype);
				} else if (s_score < 27) {
					xgtype = "中间型偏B型";
					selectAlList.get(i).setXgtype(xgtype);
				}else if(s_score < 30){
					xgtype = "中间型";
					selectAlList.get(i).setXgtype(xgtype);
				}else if(s_score < 37){
					xgtype = "中间型偏A型";
					selectAlList.get(i).setXgtype(xgtype);
				}
				else{
					xgtype = "A型";
					selectAlList.get(i).setXgtype(xgtype);
				}
				
			}
		}
		if(2==type){
			for (int i = 0; i < selectAlList.size(); i++) {
				String xgtype = "";
				String evaluateScore = selectAlList.get(i).getEvaluateScore();
				Integer score = Integer.valueOf(evaluateScore);
				if(score < 43){
					xgtype = "压力过低";
					selectAlList.get(i).setXgtype(xgtype);
				}else if(score <= 65){
					xgtype = "压力适中";
					selectAlList.get(i).setXgtype(xgtype);
				}else{
					xgtype = "压力过高";
					selectAlList.get(i).setXgtype(xgtype);
				}
			}
		}
		if(3==type){
			for (int i = 0; i < selectAlList.size(); i++) {
				String xgtype = "";
				String evaluateScore = selectAlList.get(i).getEvaluateScore();
				Integer score = Integer.valueOf(evaluateScore);
				//获得用户性别
				String gender="男";
				switch (gender) {
				case "女":
					if(score < 25){
						xgtype = "疑似心理疾患";
						selectAlList.get(i).setXgtype(xgtype);
					}else if(score <= 45){
						xgtype = "橙色预警";
						selectAlList.get(i).setXgtype(xgtype);
					}else{
						xgtype = "正常";
						selectAlList.get(i).setXgtype(xgtype);
					}
					break;

				default:
					if(score < 10){
						xgtype = "疑似心理疾患";
						selectAlList.get(i).setXgtype(xgtype);
					}else if(score <= 65){
						xgtype = "橙色预警";
						selectAlList.get(i).setXgtype(xgtype);
					}else{
						xgtype = "正常";
						selectAlList.get(i).setXgtype(xgtype);
					}
					break;
				}	
			}
		}
		return Result.ok(selectAlList);
	}
	/**
	 * 根据id查询压力评估报告详情
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findPEInfoById")
	@ResponseBody
	public Result findPEInfoById(Integer type, Integer id) {
		if(null==type||null==id){
			Result.error();
		}
		 YhPressureEvaluateApp selectByPrimaryKey = yhPressureEvaluateAppService.selectByPrimaryKey(id);
		if(1==type){
			String evaluateScore = selectByPrimaryKey.getEvaluateScore();
			// 获取评估分数
			String score[] = evaluateScore.split(";");
			int int_score[] = new int[3];
			int step = 0;
			for (int j = 0; j < score.length; j++)
				if (checkStr(score[j])) {
					int_score[step] = Integer.parseInt(score[j]);
					step++;
				}
			// 计算L量表得分
			Integer l_score = Integer.valueOf(int_score[0]);
			// 计算性格评估量表得分
			Integer s_score = Integer.valueOf(int_score[0] + int_score[1] + int_score[2]);
			
			selectByPrimaryKey.setL_score(l_score);
			selectByPrimaryKey.setS_score(s_score);
			
		}
		
		if(2==type){
			String evaluateScore = selectByPrimaryKey.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			selectByPrimaryKey.setS_score(score);
		}
		if(3==type){
			String evaluateScore = selectByPrimaryKey.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			selectByPrimaryKey.setS_score(score);
		}
		return Result.ok(selectByPrimaryKey);
	}

	/**
	 * 查询评估用户的数量
	 * @return
	 */
	@RequestMapping(value = "/assessCount")
	@ResponseBody
	public Result assessCount(int type) {
		int count=yhPressureEvaluateAppService.assessCount(type);
		return Result.ok(count);
	}
	/**
	 * 查询用户最新评估记录
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/findUserRecord")
	@ResponseBody
	public Result findUserRecord(@Param("userCode") String userCode, @Param("evaluateType") Integer evaluateType) {
		YhPressureEvaluateApp yh=yhPressureEvaluateAppService.findUserRecord(evaluateType,userCode);
		//获取评估类型
		Integer type=null;
		if (yh==null){
			return Result.ok();
		}else{
			type=yh.getEvaluateType();
		}
		if(1==type){
			String evaluateScore = yh.getEvaluateScore();
			// 获取评估分数
			String score[] = evaluateScore.split(";");
			int int_score[] = new int[3];
			int step = 0;
			for (int j = 0; j < score.length; j++)
				if (checkStr(score[j])) {
					int_score[step] = Integer.parseInt(score[j]);
					step++;
				}
			// 计算L量表得分
			Integer l_score = Integer.valueOf(int_score[0]);
			// 计算性格评估量表得分
			Integer s_score = Integer.valueOf(int_score[0] + int_score[1] + int_score[2]);

			yh.setL_score(l_score);
			yh.setS_score(s_score);

		}

		if(2==type){
			String evaluateScore = yh.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			yh.setS_score(score);
		}
		if(3==type){
			String evaluateScore = yh.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			yh.setS_score(score);
		}
		if(0==type){
			String evaluateScore = yh.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			yh.setS_score(score);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = yh.getEvaluateDate();
		Date date = new Date();
		//获取String类型的时间
		String  endTime= sdf.format(date);
		yh.setEvaluateDate(dateDiff(startTime,endTime));
		return Result.ok(yh);
	}

	public static String dateDiff(String startTime,String endTime) {

		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long between = 0;
		try {
			java.util.Date begin = dfs.parse(startTime);
			java.util.Date end = dfs.parse(endTime);
			between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
		if(day>0) {
			return day + "天前";
		}
		if(day <= 0 && hour > 0) {
			return hour + "小时前";
		}
		if(hour <= 0 && min > 0) {
			return min + "分钟前";
		}
		if(s < 60) {
			return s + "秒前";
		}

		return "";
	}
}