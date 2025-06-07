package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhHealthStarApp;
import com.yihe.applet.api.domain.YhScoreEventCategoryApp;
import com.yihe.applet.api.domain.YhUserStepApp;
import com.yihe.applet.api.service.YhHealthStarAppService;
import com.yihe.applet.api.service.YhScoreEventCategoryAppService;
import com.yihe.applet.api.service.YhUserStepAppService;
import com.yihe.applet.api.util.common.util.HttpRequestor;
import com.yihe.applet.api.util.common.util.RedisUtils;
import com.yihe.system.mapper.YhOrgTreeMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.mapper.YhUserRoleManageMapper;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhScoreEventCategoryWebService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhScoreEventCategorycontroller Created by yx on 2020/5/12.
 */
@Controller
@RequestMapping("/api/YhScoreEventCategory")
@Api(value = "YhScoreEventCategory控制器", description = "YhScoreEventCategory管理")
public class YhScoreEventCategoryController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhScoreEventCategoryController.class);

	@Autowired
	// 积分规则
	YhScoreEventCategoryAppService yhScoreEventCategoryService;
	@Autowired
	// 总积分
	YhUserStepAppService yhUserStepService;
	// 积分记录
	@Autowired
	YhHealthStarAppService yhHealthStarService;
	@Autowired
	YhUserReginfoMapper yhUserReginfoMapper;



	/**
	 * 查询积分类型名
	 * @param eventName
	 * @return
	 */
	@RequestMapping(value = "/seleventType")
	@ResponseBody
	public Result seleventType(String eventName){
		return Result.ok(yhScoreEventCategoryService.seleventType(eventName));
	}
	/**
	 * 分页查询积分记录
	 * @param page
	 * @param pageSize
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/pageYhHealthStar")
	@ResponseBody
	public Result selectYhHealthStarAppParam(Integer page,Integer pageSize, String userCode,Integer type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		List<YhHealthStarApp> starAppParam = yhHealthStarService.selectYhHealthStarAppParam((page-1)*pageSize, pageSize, userCode,type);
		Integer total = yhHealthStarService.selectUserYhHealthStarSum(userCode,type);
		for (int i = 0; i < starAppParam.size(); i++) {
			Date eventsTime = starAppParam.get(i).getEventsTime();
			String writeTime = sdf.format(eventsTime);
			starAppParam.get(i).setWriteTime(writeTime);
		}

		map.put("starAppParam", starAppParam);
		map.put("total", total);
		return Result.ok(map);
		
	}
	
	/**
	 * 查询所有积分完成状态
	 * @param userCode
	 * @param eventType
	 * @return
	 */
	@RequestMapping(value = "/integralStatus")
	@ResponseBody
	public Result selectIntegralStatus(String userCode, String eventType,Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("eventType", eventType);
		map.put("userCode", userCode);
		map.put("type", type);
		List<Map<String, Object>> selectIntegralStatus = yhScoreEventCategoryService.selectIntegralStatus(map);
		for (int i = 0; i < selectIntegralStatus.size(); i++) {
			Map<String, Object> stringObjectMap = selectIntegralStatus.get(i);
			String etype= (String) stringObjectMap.get("event_type");
			String to="";
			if(Objects.equals(etype, "food_log")){

				to="../../../dietaryManagement/pages/diet/record/record";

			}
			if(Objects.equals(etype, "sport_log")){

				to="../../../motionManagement/pages/record/record";
			}
			if(Objects.equals(etype, "sport_step_6000")){

				to="/motionManagement/pages/index/homepage";
			}
			if(Objects.equals(etype, "sport_step_8000")){

				to="/motionManagement/pages/index/homepage";
			}
			if(Objects.equals(etype, "sport_step_10000")){

				to="/motionManagement/pages/index/homepage";
			}
			if(Objects.equals(etype, "record_sleep")){
				to= "../../../healthManagement/pages/sleepModule/index/index";
			}
			if(Objects.equals(etype, "record_habits")){
				to= "../../../pages/healthHabits/homepage";
			}
			if(Objects.equals(etype, "publish_classroom")){
				to= "../../../healthManagement/pages/classroom/classroom";
			}
			if(Objects.equals(etype, "publish_habit")){
				to= "../../../pages/healthHabits/homepage";
			}

			if(Objects.equals(etype, "health_assess")){
				to= "/healthyHManagement/pages/riskAssessment/basic/basic?title=健康风险评估&type=3&num=7375";
			}

			if(Objects.equals(etype, "habit_assess")){
				to= "../../../pages/healthHabits/homepage";
			}

			if(Objects.equals(etype, "sleep_assess")){
				to=  "../../../healthManagement/pages/sleepModule/sleepQuestion/sleepQuestion";
			}

			if(Objects.equals(etype, "pressure_assess")){
				to=   "/healthManagement/pages/stressManagementQuestionnaire/stressManagementQuestionnaire?tabcur=1";
			}

			if(Objects.equals(etype, "symptom_assess")){
				to=   "/subhealthAssessMent/pages/subhealthassess/subhealthassess";
			}
			if(Objects.equals(etype, "psychology_assess")){
				to=   "/healthManagement/pages/stressManagementQuestionnaire/stressManagementQuestionnaire?tabcur=2";
			}

			if (Objects.equals(etype, "blood_pressure")) {
				to = "/subhealthAssessMent/ec-canvas/pages/healthManagement/statistics/statistics";
			}

			if (Objects.equals(etype, "blood_sugar")) {
				to = "/subhealthAssessMent/ec-canvas/pages/healthManagement/bloodSugarStatistics/bloodSugarStatistics";
			}

			if (Objects.equals(etype, "blood_fat")) {
				to = "/healthManagement/pages/lipidsFourDetails/lipidsFourDetails";
			}

			if (Objects.equals(etype, "uric_acid")) {
				to = "/healthManagement/pages/renalFunctionDetails/renalFunctionDetails";
			}

			if (Objects.equals(etype, "weight_log")) {
				to = "/subhealthAssessMent/ec-canvas/pages/healthManagement/weightStatistics/weightStatistics";
			}

			if (Objects.equals(etype, "heart_rate")) {
				to = "/subhealthAssessMent/ec-canvas/pages/healthManagement/heartRateStatistics/heartRateStatistics";
			}

			selectIntegralStatus.get(i).put("to",to);
		}
		System.out.println(selectIntegralStatus+"----------");
		return Result.ok(selectIntegralStatus);


	}

	/**
	 * 是否注册
	 */
	@RequestMapping(value = "/isItRegistered")
	@ResponseBody
	public Result isItRegistered(String phoneNumber) {
		String userCode = yhUserReginfoMapper.findByMoblie(phoneNumber);
		if(StringUtil.isEmpty(userCode)){
			return Result.error();
		}
		return Result.ok();
	}

	/**
	 * 二次开发获取微分销access_token
	 */
	public void getToken() throws Exception{
		String url="/token";
		String shop_id="?shop_id=123";
		String app_key="&app_key=123";
		String secret="&secret=123";
		String doget = new HttpRequestor().doGet(url + shop_id + app_key + secret);
		if(StringUtil.isNotEmpty(doget)){
			//存redis
			RedisUtils redis=new RedisUtils();
			redis.set("shop_id","123",60);
		}
		return;
	}

	/**
	 * 商城积分加减
	 */
	public void changePoints(){

		return;
	}





	/**
	 * 积分商城接口
	 */
	@RequestMapping(value = "/addPoints")
	@ResponseBody
	@Transactional
	public Result addPoints(String phoneNumber,Integer score,Integer type) {

		String userCode = yhUserReginfoMapper.findByMoblie(phoneNumber);
		if(StringUtil.isEmpty(userCode)){
			return Result.error("根据手机号未得到用户信息");
		}
		if(StringUtil.isEmpty(userCode)||null==score||null==type){
			return Result.error("参数异常");
		}
		if(!(0==type||1==type)){
			return  Result.error("参数异常");
		}
		if(!(score>0)){
			return  Result.error("参数异常");
		}
		//查询总积分
		YhUserStepApp YhUserStep = yhUserStepService.selectAggregate(userCode);
		//如果是支出就查询是否够积分
		if(1==type){
			if(null==YhUserStep){
				//无积分
				return Result.error("积分为0");
			}else{
				//总积分
				Integer healthstars = YhUserStep.getHealthstars();
				if(healthstars<score){
					return Result.error("积分小于支出");
				}
			}

		}

		// 添加一条积分记录
		YhHealthStarApp yhHealthStar = new YhHealthStarApp();

		//没有开启连续奖励
			yhHealthStar.setUserCode(userCode);
			yhHealthStar.setUserType(type==0?5:4);
			yhHealthStar.setHealthstars(score);
			yhHealthStar.setHealthstarsEvents("积分商城");
			yhHealthStar.setEventsTime(new Date());
		int insertRecord = yhHealthStarService.insertSelective(yhHealthStar);




		YhUserStepApp UserStep = new YhUserStepApp();
		if (insertRecord > 0) {
			//没有总积分数据进行添加 反之修改
			if (YhUserStep == null) {
				UserStep.setUserCode(userCode);
				UserStep.setUserType(5);
				UserStep.setHealthlevelstars(score);
				UserStep.setHealthstars(score);
				int insertSelective = yhUserStepService.insertSelective(UserStep);
				if (insertSelective > 0) {
					return Result.ok(insertSelective);
				}
			}

		}
		// 修改总积分
		YhUserStep.setHealthstars(YhUserStep.getHealthstars() + score);
		YhUserStep.setHealthlevelstars(YhUserStep.getHealthlevelstars() + score);
		int updateAggregateScore = yhUserStepService.updateByPrimaryKey(YhUserStep);
		if (updateAggregateScore > 0) {
			return Result.ok(updateAggregateScore);
		}


		return Result.error();
	}
	

	@Resource
	YhOrgTreeService yhOrgTreeService;
	@Resource
	YhUserRoleManageMapper yhUserRoleManageMapper;
	@Autowired
	YhScoreEventCategoryWebService scoreEventCategoryWebService;

	/**
	 * 积分任务完成接口
	 * 
	 * @param eventType
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/integralAccomplish")
	@ResponseBody
	@Transactional
	public Result integralCompletioStatus(String eventType,String userCode,Integer type) {

		scoreEventCategoryWebService.saveMark(eventType, userCode);
		return Result.ok();
	}
	



}