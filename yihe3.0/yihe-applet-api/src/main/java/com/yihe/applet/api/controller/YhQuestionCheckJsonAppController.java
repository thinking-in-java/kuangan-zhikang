package com.yihe.applet.api.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.yihe.applet.api.domain.YhQuestionCheckJsonApp;
import com.yihe.applet.api.domain.YhUserBloodfatApp;
import com.yihe.applet.api.domain.YhUserBloodpressureApp;
import com.yihe.applet.api.domain.YhUserBloodsugerApp;
import com.yihe.applet.api.domain.YhUserBmiApp;
import com.yihe.applet.api.domain.YhUserRenalfunctionApp;
import com.yihe.applet.api.service.YhQuestionCheckJsonAppService;
import com.yihe.applet.api.service.YhUserBloodfatAppService;
import com.yihe.applet.api.service.YhUserBloodpressureAppService;
import com.yihe.applet.api.service.YhUserBloodsugerAppService;
import com.yihe.applet.api.service.YhUserBmiAppService;
import com.yihe.applet.api.service.YhUserBodyFatAppService;
import com.yihe.applet.api.service.YhUserRenalfunctionAppService;
import com.yihe.system.utils.Result;

/**
 * Description:
 * Created by Administrator on 2020/5/28 16:49
 */
@RestController
@RequestMapping("/api/YhQuestionCheckJsonApp")
public class YhQuestionCheckJsonAppController {
    @Resource
    YhQuestionCheckJsonAppService yhQuestionCheckJsonAppService;
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
	YhUserRenalfunctionAppService yhUserRenalfunctionAppService; //血尿酸
	
	/**
	 * 查询测评次数
	 * @return
	 */
	 @RequestMapping("/selectYhQuestionCheckCount")
	public Result selectYhQuestionCheckCount() {
		 int count = yhQuestionCheckJsonAppService.selectYhQuestionCheckCount();
		return Result.ok(count);
		
	}
	
	/**
	 * 查询是否评估
	 * @return
	 */
	 @RequestMapping("/selectYhQuestionCheckJsonByUserCode")
	 @ResponseBody
	public Result selectYhQuestionCheckJsonByUserCode(String userCode) {
		YhQuestionCheckJsonApp questionCheckJsonApp = yhQuestionCheckJsonAppService.selectYhQuestionCheckJsonByUserCode(userCode, 1);
		return Result.ok(questionCheckJsonApp);
		
	}
	
    /* 得到问卷缓存 */
    @RequestMapping("/getCheckJsonInformation")
    public Result getCheckJsonInformation(String userCode){
        if(StringUtil.isNotEmpty(userCode)){
	    	YhQuestionCheckJsonApp jsonApp = yhQuestionCheckJsonAppService.selectYhQuestionCheckJsonByUserCode(userCode,1);
	    	if(jsonApp == null){
				jsonApp = yhQuestionCheckJsonAppService.selectYhQuestionCheckJsonByUserCode("system", 1);
			}
			getJson(jsonApp, userCode);
			return Result.ok(jsonApp);
        }
        return Result.ok(null);

    }

	/**
	 * 查询暂存问卷
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/seltemporaryJson")
    public Result seltemporaryJson(String userCode){
		if(StringUtil.isNotEmpty(userCode)) {
			YhQuestionCheckJsonApp jsonApp = yhQuestionCheckJsonAppService.selectYhQuestionCheckJsonByUserCode(userCode, 2);
			if(jsonApp != null){
				getJson(jsonApp, userCode);
				return Result.ok(null);
			}
		}
		return Result.ok(null);
	}

	/**
	 * 暂存问卷
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/instemporaryJson")
	public Result instemporaryJson(@RequestParam("queestionData") String queestionData,
								   @RequestParam("userCode") String userCode,
								   @RequestParam("curIndex") Long curIndex){
		YhQuestionCheckJsonApp checkJsonApp = new YhQuestionCheckJsonApp();
		checkJsonApp.setUserCode(userCode);
		checkJsonApp.setUserCheckAnswer(queestionData.toString());
		checkJsonApp.setUserCheckIndex(curIndex);
		checkJsonApp.setUserCheckTs(2L);
		checkJsonApp.setUserCheckTime(new Date());
		return Result.ok(yhQuestionCheckJsonAppService.insertYhQuestionCheckJson(checkJsonApp)) ;
	}

	/**
	 * 删除暂存问卷
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/deltemporaryJson")
	public Result deltemporaryJson(String userCode){
    	return Result.ok(yhQuestionCheckJsonAppService.delYhQuestionCheckJson(userCode));
	}

	private void getJson(YhQuestionCheckJsonApp jsonApp, String userCode){
		YhUserBmiApp bmiApp = yhUserBmiService.selectByPrimaryKeyNewest(userCode);// bmi
		YhUserBloodpressureApp bloodpressureApp = yhUserBloodpressureService.selectByPrimaryKeyNewest(userCode);// 血压
		List<YhUserBloodsugerApp> bloodsugerApps = yhUserBloodsugerService.selectByPrimaryKeyBYeid(userCode, "空腹");// 血糖
		YhUserBloodfatApp bloodfatApp = yhUserBloodfatService.selectByPrimaryKeyNewest(userCode);// 血脂
		YhUserRenalfunctionApp renalfunctionApp = yhUserRenalfunctionAppService.selectByPrimaryKeyNewest(userCode);//血尿酸
		JSONObject jSONObject = JSONObject.parseObject(jsonApp.getUserCheckAnswer());
		if (null != bmiApp) {
			Float getuWeight = bmiApp.getuWeight();
			Float getuHeight = bmiApp.getuHeight();
			if (null != getuWeight) {
				jSONObject.put("weight", getuWeight);
			}
			if (null != getuHeight) {
				jSONObject.put("height", getuHeight);
			}
		}
		// 获取最新血脂
		if (null != bloodfatApp) {
			Float chol = bloodfatApp.getCholesterin();
			Float tring = bloodfatApp.getTring();
			Float hdl = bloodfatApp.getHdl();
			Float ldl = bloodfatApp.getLdl();

			if(null != chol){
				jSONObject.put("cholesterol", chol);
			}
			if(null != tring){
				jSONObject.put("tg", tring);
			}
			if(null != hdl){
				jSONObject.put("hdlc", hdl);
			}
			if(null != ldl){
				jSONObject.put("ldlc", ldl);
			}

		}
		// 获取最新血压
		if (null != bloodpressureApp) {
			Integer sbp = bloodpressureApp.getSbp();
			Integer dbp = bloodpressureApp.getDbp();
			if(null != sbp){
				jSONObject.put("sbp", sbp);
			}
			if(null != dbp){
				jSONObject.put("dbp", dbp);
			}


		}
		// 获取最新空腹血糖数据
		if (null != bloodsugerApps) {
			if(bloodsugerApps.size()>0){
				Float bs = bloodsugerApps.get(bloodsugerApps.size()-1).getBs();
				if(null != bs){
					jSONObject.put("fbg", bs);
				}

			}
		}
		// 获取最新肾功能数据
		if (null != renalfunctionApp) {
			String crea = renalfunctionApp.getCreatinine();
			String ua = renalfunctionApp.getUricAcid();
			if(null != crea){
				jSONObject.put("serum_creatinine", crea);
			}
			if(null != ua){
				jSONObject.put("bua", ua);
			}
		}
		String jsonString = jSONObject.toJSONString();
		jsonApp.setUserCheckAnswer(jsonString);
	}
}
