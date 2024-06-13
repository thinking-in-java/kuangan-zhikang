package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.*;
import com.yihe.system.service.YhUserAdminInfoService;
import com.yihe.system.service.impl.*;
import com.yihe.system.utils.MonitoringDataStandard;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户干预管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/testIng")
public class YHUserTestIngController {
	@Autowired
	YhUserReginfoServiceImpl yhUserReginfoServiceImpl;
	@Autowired
	YHUserBloodPressureServiceImpl YHUserBloodPressureServiceImpl;
	@Autowired
	YhDiseaseAssessTotalReportServiceImpl YhDiseaseAssessTotalReportServiceImpl;
	@Autowired
	YhUserEnrolinfoServiceImpl YhUserEnrolinfoServiceImpl;
	 
	@Autowired
	private YhUserAdminInfoService YhUserAdminInfoService;

	
	
	@Autowired
	YHUserOxygenServiceImpl YHUserOxygenServiceImpl;
	
	@Autowired
	YHUserBmiServiceImpl YHUserBmiServiceImpl;
	
	
	@Autowired
	YHUserTemperatureServiceImpl yHUserTemperatureServiceImpl;
	
	@Autowired
	YHUserHeartRateServiceImpl YHUserHeartRateServiceImpl;
	
	@Autowired
	YHUserBloodsugerServiceImpl YHUserBloodsugerServiceImpl;
	@Autowired
	YHUserBodyFatServiceImpl YHUserBodyFatServiceImpl;
	@Autowired
	YHUserBloodfatServiceImpl YHUserBloodfatServiceImpl;
	@Autowired
	YHUserRenalfunctionServiceImpl YHUserRenalfunctionServiceImpl;
	@Autowired
	YHUserLiverfunctionServiceImpl YHUserLiverfunctionServiceImpl;
	@Autowired
	YHUserEcgServiceImpl YHUserEcgServiceImpl;
	@Autowired
	YHUserUbaServiceImpl YHUserUbaServiceImpl;
	@Autowired
	YHUserOtherfunctionServiceImpl YHUserOtherfunctionServiceImpl;
	@Autowired
	YhUserArteryServiceImpl yhUserArteryServiceImpl;
	@Autowired
	yHUserHemoglobinServiceImpl yHUserHemoglobinServiceImpl;
	@RequestMapping(value = "/findTestingListInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result findTestingListInfo(@RequestParam(required = false) Integer orgid,
			  @RequestParam(required = false) String username,
			@RequestParam(required = false) String usernameLike,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit,
	@RequestParam(required = false) String field,
									  @RequestParam(required = false, defaultValue = "desc") String  order
	) {

		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

		String doctorUsercode = principal.getUserCode();
		YhUserAdminInfo selectByPrimaryKeyByusercode = YhUserAdminInfoService.selectByPrimaryKeyByusercode(doctorUsercode);
		if(null==selectByPrimaryKeyByusercode){
			return Result.error();
		}
		Integer id = selectByPrimaryKeyByusercode.getId();
		PageInfo<TestingVOJO> selectByPrimaryKeyByDoctor = YhUserAdminInfoService.selectByPrimaryKeyByDoctor(id,orgid,username,usernameLike,order, page, limit);
		//医生所管理的所有用户信息
		List<TestingVOJO> list = selectByPrimaryKeyByDoctor.getList();
		MonitoringDataStandard mvojo=new MonitoringDataStandard();
		for (TestingVOJO testingVOJO : list) {
			String usercode = testingVOJO.getUsercode();
			//检测项
			int jcnum=0;
			//异常数
			int ycnum=0;
			//异常信息
			String ycsTr="";
			 List<String> timeList=new ArrayList<String>();
			//查询用户体温监测
			YHUserTemperature selectByPrimaryKeyByUsercodeLimit = yHUserTemperatureServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit.getEntryTime());
				}
				if(null!=selectByPrimaryKeyByUsercodeLimit.getTemperature()){
					MonitoringDataStandardVOJO temperature = mvojo.temperature(selectByPrimaryKeyByUsercodeLimit.getTemperature());
					if(temperature.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"体温;";
					}
				}

			}
			//查询用户血氧
			YHUserOxygen selectByPrimaryKeyByUsercodeLimit2 = YHUserOxygenServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit2){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit2.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit2.getEntryTime());
				}
				if(null!=selectByPrimaryKeyByUsercodeLimit2.getOxygen()){
					MonitoringDataStandardVOJO temperature = mvojo.bloodOxygen(selectByPrimaryKeyByUsercodeLimit2.getOxygen());
					if(temperature.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"血氧;";
					}
				}

			}
			//查询用户bmi
			YHUserBmi selectByPrimaryKeyByUsercodeLimit3 = YHUserBmiServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit3){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit3.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit3.getEntryTime());
				}

				if(null!=selectByPrimaryKeyByUsercodeLimit3.getuWeight()&&null!=selectByPrimaryKeyByUsercodeLimit3.getuHeight()){
					Float getuWeight = selectByPrimaryKeyByUsercodeLimit3.getuWeight();
					Float getuHeight = selectByPrimaryKeyByUsercodeLimit3.getuHeight();

					Float bmi=getuWeight/((getuHeight/100)*(getuHeight/100));
					MonitoringDataStandardVOJO weight = mvojo.weight(bmi);
					if(weight.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"BMI;";
					}
				}


			}
			//查询用户血压
			YHUserBloodPressure selectByPrimaryKeyByUsercodeLimit4 = YHUserBloodPressureServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit4){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit4.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit4.getEntryTime());
				}
				if(null!=selectByPrimaryKeyByUsercodeLimit4.getSbp()&&null!=selectByPrimaryKeyByUsercodeLimit4.getDbp()){
					Integer sbp = selectByPrimaryKeyByUsercodeLimit4.getSbp();
					Integer dbp = selectByPrimaryKeyByUsercodeLimit4.getDbp();
					MonitoringDataStandardVOJO bloodPressure = mvojo.bloodPressure(sbp, dbp);
					if(bloodPressure.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"血压;";
					}
				}

			}
			//查询用户血糖
			YHUserBloodsuger selectByPrimaryKeyByUsercodeLimit5 = YHUserBloodsugerServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit5){
				jcnum++;

				if(null!=selectByPrimaryKeyByUsercodeLimit5.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit5.getEntryTime());
				}
				if(null!=selectByPrimaryKeyByUsercodeLimit5.getBs()&&null!=selectByPrimaryKeyByUsercodeLimit5.getEnv()){
					String env = selectByPrimaryKeyByUsercodeLimit5.getEnv();

					if(env.contains("空腹")){
						MonitoringDataStandardVOJO bloodSugar = mvojo.bloodSugar(selectByPrimaryKeyByUsercodeLimit5.getBs(), 1);
						if(bloodSugar.getAbnormal()!=0){
							ycnum++;
							ycsTr=ycsTr+"血糖;";
						}
					}else{
						MonitoringDataStandardVOJO bloodSugar = mvojo.bloodSugar(selectByPrimaryKeyByUsercodeLimit5.getBs(), 2);
						if(bloodSugar.getAbnormal()!=0){
							ycnum++;
							ycsTr=ycsTr+"血糖;";
						}
					}

				}
			}
			//查询用户心率
			YHUserHeartRate selectByPrimaryKeyByUsercodeLimit6 = YHUserHeartRateServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit6){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit6.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit6.getEntryTime());
				}

				if(null!=selectByPrimaryKeyByUsercodeLimit6.getHeartRate()){

					MonitoringDataStandardVOJO heartRate = mvojo.heartRate(selectByPrimaryKeyByUsercodeLimit6.getHeartRate());
					if(heartRate.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"心率;";
					}
				}

			}
			//查询用户体脂
			YHUserBodyFat selectByPrimaryKeyByUsercodeLimit7 = YHUserBodyFatServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit7){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit7.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit7.getEntryTime());
				}

				if(null!=selectByPrimaryKeyByUsercodeLimit7.getFatRate()){
					String sex = testingVOJO.getSex();
					if(sex.equals("男")){
						MonitoringDataStandardVOJO bodyFat = mvojo.bodyFat(selectByPrimaryKeyByUsercodeLimit7.getFatRate(), 1);
						if(bodyFat.getAbnormal()!=0){
							ycnum++;
							ycsTr=ycsTr+"体脂;";
						}
					}else{
						MonitoringDataStandardVOJO bodyFat = mvojo.bodyFat(selectByPrimaryKeyByUsercodeLimit7.getFatRate(), 2);
						if(bodyFat.getAbnormal()!=0){
							ycnum++;
							ycsTr=ycsTr+"体脂;";
						}
					}

				}

			}
			//查询用户血脂
			YHUserBloodfat selectByPrimaryKeyByUsercodeLimit8 = YHUserBloodfatServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit8){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit8.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit8.getEntryTime());
				}

				if(null!=selectByPrimaryKeyByUsercodeLimit8.getCholesterin()&&null!=selectByPrimaryKeyByUsercodeLimit8.getTring()&&null!=selectByPrimaryKeyByUsercodeLimit8.getHdl()&&null!=selectByPrimaryKeyByUsercodeLimit8.getLdl()){
					MonitoringDataStandardVOJO bloodFat = mvojo.bloodFat(selectByPrimaryKeyByUsercodeLimit8.getCholesterin(), selectByPrimaryKeyByUsercodeLimit8.getTring(), selectByPrimaryKeyByUsercodeLimit8.getHdl(), selectByPrimaryKeyByUsercodeLimit8.getLdl());
					if(bloodFat.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"血脂四项;";
					}
				}


			}
			//查询肾功能
			YHUserRenalfunction selectByPrimaryKeyByUsercodeLimit9 = YHUserRenalfunctionServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit9){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit9.getInerttime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit9.getInerttime());
				}

				if(null!=selectByPrimaryKeyByUsercodeLimit9.getCreatinine()&&null!=selectByPrimaryKeyByUsercodeLimit9.getUreaNitrogen()&&null!=selectByPrimaryKeyByUsercodeLimit9.getUricAcid()){
					String sex = testingVOJO.getSex();
					if(StringUtil.isEmpty(selectByPrimaryKeyByUsercodeLimit9.getCreatinine())||StringUtil.isEmpty(selectByPrimaryKeyByUsercodeLimit9.getUreaNitrogen())
							||StringUtil.isEmpty(selectByPrimaryKeyByUsercodeLimit9.getUricAcid())){
						ycnum++;
						ycsTr=ycsTr+"肾功能;";
					}else{

					if(sex.equals("男")){
						MonitoringDataStandardVOJO renalFunction = mvojo.renalFunction(Double.valueOf(selectByPrimaryKeyByUsercodeLimit9.getCreatinine()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit9.getUreaNitrogen()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit9.getUricAcid()), 1);
						if(renalFunction.getAbnormal()!=0){
							ycnum++;
							ycsTr=ycsTr+"肾功能;";
						}
					}else{
						MonitoringDataStandardVOJO renalFunction =mvojo.renalFunction(Double.valueOf(selectByPrimaryKeyByUsercodeLimit9.getCreatinine()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit9.getUreaNitrogen()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit9.getUricAcid()), 2);
						if(renalFunction.getAbnormal()!=0){
							ycnum++;
							ycsTr=ycsTr+"肾功能;";
						}
					}
				}
				}


			}
			//查询肝功能
			YHUserLiverfunction selectByPrimaryKeyByUsercodeLimit10 = YHUserLiverfunctionServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit10){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit10.getInserttime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit10.getInserttime());
				}
				if(null!=selectByPrimaryKeyByUsercodeLimit10.getGuBing()&&null!=selectByPrimaryKeyByUsercodeLimit10.getMilletStraw()&&null!=selectByPrimaryKeyByUsercodeLimit10.getSerumAlbumin()&&null!=selectByPrimaryKeyByUsercodeLimit10.getSerumGlobulin()){
					MonitoringDataStandardVOJO liverFunction = mvojo.liverFunction(Double.valueOf(selectByPrimaryKeyByUsercodeLimit10.getGuBing()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit10.getMilletStraw()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit10.getSerumAlbumin()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit10.getSerumGlobulin()));
					if(liverFunction.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"肝功能;";
					}
				}
			}

			//查询动脉硬化
			YhUserArtery yhUserArtery = yhUserArteryServiceImpl.selectByPrimaryKeyNewest(usercode);
			if(null!=yhUserArtery){
				jcnum++;
				if(null!=yhUserArtery.getTime()){

					timeList.add(yhUserArtery.getTime());
				}
				if(null!=yhUserArtery.getAvi()&&null!=yhUserArtery.getApi()){
					int ageByBirth = getAgeByBirth(principal.getBirthDate());
					MonitoringDataStandardVOJO artery = mvojo.artery(yhUserArtery.getAvi(), yhUserArtery.getApi(), ageByBirth);
					if(artery.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"动脉硬化;";
					}

				}
			}

			//查询心电图
			YHUserEcg selectByPrimaryKeyByUsercodeLimit11 = YHUserEcgServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit11){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit11.getEntryTime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit11.getEntryTime());
				}

			}
			//查询用户尿检
			YHUserUba selectByPrimaryKeyByUsercodeLimit12 = YHUserUbaServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit12){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit12.getAdddate()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit12.getAdddate());
				}

					MonitoringDataStandardVOJO urinalysis = mvojo.urinalysis(selectByPrimaryKeyByUsercodeLimit12.getWhitebloodcell(), selectByPrimaryKeyByUsercodeLimit12.getUrineprotein(), selectByPrimaryKeyByUsercodeLimit12.getKetone());
					if(urinalysis.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"尿检;";
					}

			}

			//查询其他功能
			YHUserOtherfunction selectByPrimaryKeyByUsercodeLimit13 = YHUserOtherfunctionServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			if(null!=selectByPrimaryKeyByUsercodeLimit13){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit13.getInserttime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit13.getInserttime());
				}
				if(null!=selectByPrimaryKeyByUsercodeLimit13.getUrineMicroprotein()&&null!=selectByPrimaryKeyByUsercodeLimit13.getGlycosylatedHemoglobin()&&null!=selectByPrimaryKeyByUsercodeLimit13.getHcy()&&!"".equals(selectByPrimaryKeyByUsercodeLimit13.getHcy())
						&&!"".equals(selectByPrimaryKeyByUsercodeLimit13.getUrineMicroprotein())&&!"".equals(selectByPrimaryKeyByUsercodeLimit13.getGlycosylatedHemoglobin())){
					MonitoringDataStandardVOJO otherIndicators = mvojo.otherIndicators(Double.valueOf(selectByPrimaryKeyByUsercodeLimit13.getUrineMicroprotein()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit13.getGlycosylatedHemoglobin()), Double.valueOf(selectByPrimaryKeyByUsercodeLimit13.getHcy()));
					if(otherIndicators.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"其他功能;";
					}
				}



			}
			//查询用户血红蛋白
			YhUserHemoglobin selectByPrimaryKeyByUsercodeLimit14 = yHUserHemoglobinServiceImpl.selectByPrimaryKeyByUsercodeLimit(usercode);
			String sex = testingVOJO.getSex();
			if(null!=selectByPrimaryKeyByUsercodeLimit14){
				jcnum++;
				if(null!=selectByPrimaryKeyByUsercodeLimit14.getInserttime()){
					timeList.add(selectByPrimaryKeyByUsercodeLimit14.getInserttime());
				}
				if(sex.equals('男')){
					MonitoringDataStandardVOJO hemoglobin = mvojo.hemoglobin(selectByPrimaryKeyByUsercodeLimit14.getHeHb(),1);
					if(hemoglobin.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"血红蛋白;";
					}
				}else{
					MonitoringDataStandardVOJO hemoglobin = mvojo.hemoglobin(selectByPrimaryKeyByUsercodeLimit14.getHeHb(),2);
					if(hemoglobin.getAbnormal()!=0){
						ycnum++;
						ycsTr=ycsTr+"血红蛋白;";
					}
				}

			}

			if(timeList.size()>0){
				ListSort(timeList);
				String string = timeList.get(timeList.size()-1);
				testingVOJO.setTestTime(string);
			}
			
			testingVOJO.setYcnum(ycnum);
			testingVOJO.setYcStr(ycsTr);
			testingVOJO.setTestNum(String.valueOf(jcnum));
		}
		Map<String,Object> rsMap = new HashMap<String,Object>();
        rsMap.put("count", selectByPrimaryKeyByDoctor.getTotal());
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", list);
		return Result.ok(rsMap);

	}
	
	
	
	@RequestMapping(value = "/findTestingListRecord", method = RequestMethod.GET)
	@ResponseBody
	public Result findTestingListRecord(@RequestParam(required = false) String typename,
			  @RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String usernameLike,
			@RequestParam(required = false) String infoTime,
			 @RequestParam(required = false) String strTime,
				@RequestParam(required = false) String endTime,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

		String doctorUsercode = principal.getUserCode();
		if(StringUtil.isNotEmpty(infoTime)){
			String[] split = infoTime.split(" - ");
			strTime=split[0];
			endTime=split[1];
		}
		if(StringUtil.isEmpty(typename)){
			typename="体温";
		}
		PageInfo<TestingVOJO> selectByPrimaryKeyForInfoName = yHUserTemperatureServiceImpl.selectByPrimaryKeyForInfoNameTiwen(doctorUsercode, nameLike, usernameLike, endTime, strTime, typename, page, limit);
		
		List<TestingVOJO> list = selectByPrimaryKeyForInfoName.getList();
		if(null!=list&&list.size()>0){
			MonitoringDataStandard minfo=new MonitoringDataStandard();
			for (TestingVOJO testingVOJO : list) {
				String infoName = testingVOJO.getInfoName();
				if("体温".equals(infoName)){
					if(null!=testingVOJO.getNum1()){
						MonitoringDataStandardVOJO temperature = minfo.temperature(Double.valueOf(testingVOJO.getNum1()));
						String state = temperature.getState();
						testingVOJO.setResult(state);
					} 
					
				}
				
				if("血氧".equals(infoName)){
					if(null!=testingVOJO.getNum1()){
						MonitoringDataStandardVOJO temperature = minfo.bloodOxygen(Double.valueOf(testingVOJO.getNum1()));
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				if("BMI".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&null!=testingVOJO.getNum2()){
						double num1 = Double.valueOf(testingVOJO.getNum1());
						double num2 = Double.valueOf(testingVOJO.getNum2());
						double num3=num2/((num1/100)*(num1/100));
						
						MonitoringDataStandardVOJO temperature = minfo.weight(num3);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				if("血压".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&null!=testingVOJO.getNum2()){
						Integer num1 = Integer.valueOf(testingVOJO.getNum1());
						Integer num2 = Integer.valueOf(testingVOJO.getNum2());
						 
						
						MonitoringDataStandardVOJO temperature = minfo.bloodPressure(num1,num2);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				if("血糖".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&StringUtil.isNotEmpty(testingVOJO.getNum2())){
						double num1 = Double.valueOf(testingVOJO.getNum1());
						String num2 = testingVOJO.getNum2();
						 int type=0;
						 if("空腹".equals(num2)){
							 type=1;
						 }else{
							 type=2;
						 }
						
						MonitoringDataStandardVOJO temperature = minfo.bloodSugar(num1,type);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				

				if("心率".equals(infoName)){
					if(null!=testingVOJO.getNum1()){
						MonitoringDataStandardVOJO temperature = minfo.heartRate(Integer.valueOf(testingVOJO.getNum1()));
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				if("体脂".equals(infoName)){
					if(null!=testingVOJO.getNum1()){
						String sex = testingVOJO.getSex();
						MonitoringDataStandardVOJO temperature = minfo.bodyFat(Double.valueOf(testingVOJO.getNum1()),"男".equals(sex)?1:2);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}

				if("血红蛋白".equals(infoName)){
					if(null!=testingVOJO.getNum1()){
						String sex = testingVOJO.getSex();
						MonitoringDataStandardVOJO temperature = minfo.hemoglobin(Double.valueOf(testingVOJO.getNum1()),"男".equals(sex)?1:2);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
				}
				
				if("血脂".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&null!=testingVOJO.getNum2()&&null!=testingVOJO.getNum3()&&null!=testingVOJO.getNum4()){
						double num1 = Double.valueOf(testingVOJO.getNum1());
						double num2 = Double.valueOf(testingVOJO.getNum2());
						double num3 = Double.valueOf(testingVOJO.getNum3());
						double num4 = Double.valueOf(testingVOJO.getNum4());
						 
						
						MonitoringDataStandardVOJO temperature = minfo.bloodFat(num1,num2,num3,num4);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				

				if("肾功能".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&null!=testingVOJO.getNum2()&&null!=testingVOJO.getNum3()){
						double num1 = Double.valueOf(testingVOJO.getNum1());
						double num2 = Double.valueOf(testingVOJO.getNum2());
						double num3 = Double.valueOf(testingVOJO.getNum3());
						String sex = testingVOJO.getSex();
						 
						
						MonitoringDataStandardVOJO temperature = minfo.renalFunction(num1,num2,num3,"男".equals(sex)?1:2);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}
				
				if("肝功能".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&null!=testingVOJO.getNum2()&&null!=testingVOJO.getNum3()&&null!=testingVOJO.getNum4()){
						double num1 = Double.valueOf(testingVOJO.getNum1());
						double num2 = Double.valueOf(testingVOJO.getNum2());
						double num3 = Double.valueOf(testingVOJO.getNum3());
						double num4 = Double.valueOf(testingVOJO.getNum4());
						MonitoringDataStandardVOJO temperature = minfo.liverFunction(num1,num2,num3,num4);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					}
					
				}

				if("动脉硬化".equals(infoName)){
					if(null!=testingVOJO.getNum1()&&null!=testingVOJO.getNum2()){

						int ageByBirth = getAgeByBirth(principal.getBirthDate());
						MonitoringDataStandardVOJO artery = minfo.artery(Integer.valueOf(testingVOJO.getNum1()),Integer.valueOf(testingVOJO.getNum2()),ageByBirth);
						String state = artery.getState();
						testingVOJO.setResult(state);
					}

				}
				
				
				if("尿检".equals(infoName)){
						String num1 = testingVOJO.getNum1();
						String num2 = testingVOJO.getNum2();
						String num3 = testingVOJO.getNum3();
						 
						
						MonitoringDataStandardVOJO temperature = minfo.urinalysis(num1,num2,num3);
						String state = temperature.getState();
						testingVOJO.setResult(state);
					
				}
				

				if("其他功能".equals(infoName)){
					double num1 =0;
					if(null!=testingVOJO.getNum1()){
						num1 = Double.valueOf(testingVOJO.getNum1());
					}
					double num2 = 0;
					if(null!=testingVOJO.getNum2()){
						num2 = Double.valueOf(testingVOJO.getNum2());
					}
					double num3 = 0;
					if(null!=testingVOJO.getNum3()){
						num3 = Double.valueOf(testingVOJO.getNum3());
					}
					
					MonitoringDataStandardVOJO temperature = minfo.otherIndicators(num1,num2,num3);
					String state = temperature.getState();
					testingVOJO.setResult(state);
				}
				
				
				 
				
				





			}
		}
		Map<String,Object> rsMap = new HashMap<String,Object>();
        rsMap.put("count", selectByPrimaryKeyForInfoName.getTotal());
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", list);
		return Result.ok(rsMap);
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
	
	
	
	
	public static void main(String[] args) {
		/*String beginTime = "2018-07-29 12:26:32";
		String endTime = "2018-07-29 12:26:32";
		 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		try {
			Date date1 = format.parse(beginTime);
			Date date2 = format.parse(endTime);
			//date1小于date2返回-1，date1大于date2返回1，相等返回0
			int compareTo = date1.compareTo(date2);
			
			System.out.println(compareTo);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
	/*	String beginTime = "2018-07-29 12:26:34";
		String endTime = "2018-07-29 12:26:33";
		String endTime2 = "2018-07-29 12:26:31";
		String endTime3 = "2018-07-24 12:26:33";
		
		 List<String> list=new ArrayList<String>();
		 list.add(beginTime);
		 list.add(endTime);
		 list.add(endTime2);
		 list.add(endTime3);
		 for (String string : list) {
			System.out.println(string);
		}
		 ListSort(list);
		 for (String string : list) {
				System.out.println(string);
			}*/
		 
	}
	
	private static void ListSort(List<String> list) {
		{    //排序方法
			Collections.sort(list, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
                        // format.format(o1.getTime()) 表示 date转string类型 如果是string类型就不要转换了
						Date dt1 = format.parse(o1);
						Date dt2 = format.parse(o2);
                        // 这是由大向小排序   如果要由小向大转换比较符号就可以
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
}
