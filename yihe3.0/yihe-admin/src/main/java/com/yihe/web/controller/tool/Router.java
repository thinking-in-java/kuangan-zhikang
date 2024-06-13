package com.yihe.web.controller.tool;


import com.yihe.system.domain.PatientSignContractChaozhou;
import com.yihe.system.utils.DataValidUtil;
import com.yihe.system.utils.DateTimeUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import com.szosen.http.contract.ContractDataHandler;

/**
 * 
 * @author terry.huang
 * 2017年11月6日 下午6:11:46
 */
public class Router {
		
	public static final Integer HANDLE_TYPE_TOTAL_APPLY = 2;
	public static final Integer HANDLE_TYPE_STOP_APPLY = 3; //停止命令，方便两边作处理，如告诉server端写入Excel
	public static final String PACKAGE_INFO_KEY_TOTALROWS = "totalRows";
	public static final String PACKAGE_INFO_KEY_PAGESIZE = "pageSize";
	public static final String PACKAGE_INFO_KEY_CURRENTPAGE = "currentPage";
	public static final String PACKAGE_INFO_KEY_JSON_PATIENT = "jsonPatient";
	
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL=11;//取得医院
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL_DEPART=12;//取得医生组
	public static final Integer COMMAND_ID_CHAOZHOU_GET_DOCTOR=13;//取得医生,作登录功能
	public static final Integer COMMAND_ID_CHAOZHOU_GET_DOCTOR_DEPARMENT=14;//取得医生所在医生组
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CROWD=15;//取得医院人群类型
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CROWD_SERVICE_PACK=16;//取得医院人群包
	public static final Integer COMMAND_ID_CHAOZHOU_GET_PATIENT88=17;//取得居民（病人），这个暂时未用
	public static final Integer COMMAND_ID_CHAOZHOU_GET_FAMILY=18;//取得家庭，这个暂时未用
	public static final Integer COMMAND_ID_CHAOZHOU_SET_PATIENT_SIGN_CONTRACT=19;//设置居民（病人）签约
	public static final Integer COMMAND_ID_CHAOZHOU_GET_PATIENT_SIGN_CONTRACT=20;//取得居民（病人）签约
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL_DOCTORS=21;//取得医院里所有医生
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL_DOCTORS_TEAMS=22;//取得医院里所有医生对应该的医生组

	public static final Integer COMMAND_ID_CHAOZHOU_SET2WEB_DEVICE_ONLINE_STATUS=24;//更新设备在线状态
	
	public static final Integer COMMAND_ID_CHAOZHOU_SET_PATIENT=27;//设置居民
	public static final Integer COMMAND_ID_CHAOZHOU_SET_HEALTH_RECORD=28;//保存健康档案
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HEALTH_RECORD=29;//取得健康档案
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIEYA=30;//保存血压列表
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIEYA_LIST=31;//取得血压列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_TIWEN=32;//保存体温
	public static final Integer COMMAND_ID_CHAOZHOU_GET_TIWEN_LIST=33;//取得体温列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_BMI_LIST=34;//保存体质列表
	public static final Integer COMMAND_ID_CHAOZHOU_GET_BMI_LIST=35;//取得体质列表
	
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XINDIAN=36;//保存心电
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XINDIAN_LIST=37;//取得心电列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIEYANG=38;//保存血氧
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIEYANG_LIST=39;//取得血氧列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_NIAOYE=40;//保存尿液
	public static final Integer COMMAND_ID_CHAOZHOU_GET_NIAOYE_LIST=41;//取得尿液列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIETANG=42;//保存血糖
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIETANG_LIST=43;//取得血糖列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIEZHI=44;//保存血脂
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIEZHI_LIST=45;//取得血脂列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_CAICHAO=46;//保存彩超
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CAICHAO_LIST=47;//取得彩超列表

	public static final Integer COMMAND_ID_CHAOZHOU_SET_BASEINFO=50;//保存体检基础信息
	public static final Integer COMMAND_ID_CHAOZHOU_GET_BASEINFO=51;//取得体检基础信息
	public static final Integer COMMAND_ID_CHAOZHOU_SET_LIFESTYLE=52;//保存生活方式
	public static final Integer COMMAND_ID_CHAOZHOU_GET_LIFESTYLE=53;//取得生活方式
	public static final Integer COMMAND_ID_CHAOZHOU_SET_ACCESSORY_EXAMINATION=54;//保存辅助检查
	public static final Integer COMMAND_ID_CHAOZHOU_GET_ACCESSORY_EXAMINATION=55;//取得辅助检查
	public static final Integer COMMAND_ID_CHAOZHOU_SET_CHECK_BODY=56;//保存体检查体
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CHECK_BODY=57;//取得体检查体列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_INHOSPITALSITUATION=58;//保存住院治疗情况
	public static final Integer COMMAND_ID_CHAOZHOU_GET_INHOSPITALSITUATION=59;//取得住院治疗情况列表

	public static final Integer COMMAND_ID_CHAOZHOU_SET_MEDICATION_SITUATION=60;//保存主要用药情况
	public static final Integer COMMAND_ID_CHAOZHOU_GET_MEDICATION_SITUATION=61;//取得主要用药情况
	public static final Integer COMMAND_ID_CHAOZHOU_SET_NONIMMUNE_INOCULATION=62;//保存接种疫苗
	public static final Integer COMMAND_ID_CHAOZHOU_GET_NONIMMUNE_INOCULATION=63;//取得接种疫苗
	public static final Integer COMMAND_ID_CHAOZHOU_SET_HEALTH_ASSESSMENT=64;//保存健康评价与指导
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HEALTH_ASSESSMENT=65;//取得健康评价与指导

	public static final Integer COMMAND_ID_CHAOZHOU_SET_VISIT_HYPERTENSION=70;//保存高血压随访
	public static final Integer COMMAND_ID_CHAOZHOU_GET_VISIT_HYPERTENSION=71;//取得高血压随访
	public static final Integer COMMAND_ID_CHAOZHOU_SET_VISIT_DIABETES=72;//保存高血糖
	public static final Integer COMMAND_ID_CHAOZHOU_GET_VISIT_DIABETES=73;//取得高血糖

	public static final Integer COMMAND_ID_CHAOZHOU_SET_IFCQ_RESULT=80;//保存老年人中医药健康管理
	public static final Integer COMMAND_ID_CHAOZHOU_GET_IFCQ_RESULT=81;//取得老年人中医药健康管理
	public static final Integer COMMAND_ID_CHAOZHOU_SET_OLDPEOPLE_SELFCARE=82;//保存老年人生活自理评估
	public static final Integer COMMAND_ID_CHAOZHOU_GET_OLDPEOPLE_SELFCARE=83;//取得老年人生活自理评估
	
	public static final Integer COMMAND_ID_CHAOZHOU_SET_HEALTH_RECORD_FEN=128;//分工软件保存健康档案
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HEALTH_RECORD_FEN=129;//分工软件取得健康档案
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIEYA_FEN=130;//分工软件保存血压列表
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIEYA_LIST_FEN=131;//分工软件取得血压列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_TIWEN_FEN=132;//分工软件保存体温
	public static final Integer COMMAND_ID_CHAOZHOU_GET_TIWEN_LIST_FEN=133;//分工软件取得体温列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_BMI_LIST_FEN=134;//分工软件保存体质列表
	public static final Integer COMMAND_ID_CHAOZHOU_GET_BMI_LIST_FEN=135;//分工软件取得体质列表
	
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XINDIAN_FEN=136;//分工软件保存心电
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XINDIAN_LIST_FEN=137;//分工软件取得心电列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIEYANG_FEN=138;//分工软件保存血氧
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIEYANG_LIST_FEN=139;//分工软件取得血氧列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_NIAOYE_FEN=140;//分工软件保存尿液
	public static final Integer COMMAND_ID_CHAOZHOU_GET_NIAOYE_LIST_FEN=141;//分工软件取得尿液列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIETANG_FEN=142;//分工软件保存血糖
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIETANG_LIST_FEN=143;//分工软件取得血糖列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_XIEZHI_FEN=144;//分工软件保存血脂
	public static final Integer COMMAND_ID_CHAOZHOU_GET_XIEZHI_LIST_FEN=145;//分工软件取得血脂列表

	public static final Integer COMMAND_ID_CHAOZHOU_SET_BASEINFO_FEN=150;//分工软件保存体检基础信息
	public static final Integer COMMAND_ID_CHAOZHOU_GET_BASEINFO_FEN=151;//分工软件取得体检基础信息
	public static final Integer COMMAND_ID_CHAOZHOU_SET_LIFESTYLE_FEN=152;//分工软件保存生活方式
	public static final Integer COMMAND_ID_CHAOZHOU_GET_LIFESTYLE_FEN=153;//分工软件取得生活方式
	public static final Integer COMMAND_ID_CHAOZHOU_SET_ACCESSORY_EXAMINATION_FEN=154;//分工软件保存辅助检查
	public static final Integer COMMAND_ID_CHAOZHOU_GET_ACCESSORY_EXAMINATION_FEN=155;//分工软件取得辅助检查
	public static final Integer COMMAND_ID_CHAOZHOU_SET_CHECK_BODY_FEN=156;//分工软件保存体检查体
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CHECK_BODY_FEN=157;//分工软件取得体检查体列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_INHOSPITALSITUATION_FEN=158;//分工软件保存住院治疗情况
	public static final Integer COMMAND_ID_CHAOZHOU_GET_INHOSPITALSITUATION_FEN=159;//分工软件取得住院治疗情况列表

	public static final Integer COMMAND_ID_CHAOZHOU_SET_MEDICATION_SITUATION_FEN=160;//分工软件保存主要用药情况
	public static final Integer COMMAND_ID_CHAOZHOU_GET_MEDICATION_SITUATION_FEN=161;//分工软件取得主要用药情况
	public static final Integer COMMAND_ID_CHAOZHOU_SET_NONIMMUNE_INOCULATION_FEN=162;//分工软件保存接种疫苗
	public static final Integer COMMAND_ID_CHAOZHOU_GET_NONIMMUNE_INOCULATION_FEN=163;//分工软件取得接种疫苗
	public static final Integer COMMAND_ID_CHAOZHOU_SET_HEALTH_ASSESSMENT_FEN=164;//分工软件保存健康评价与指导
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HEALTH_ASSESSMENT_FEN=165;//分工软件取得健康评价与指导

	public static final Integer COMMAND_ID_CHAOZHOU_SET_VISIT_HYPERTENSION_FEN=170;//分工软件保存高血压随访
	public static final Integer COMMAND_ID_CHAOZHOU_GET_VISIT_HYPERTENSION_FEN=171;//分工软件取得高血压随访
	public static final Integer COMMAND_ID_CHAOZHOU_SET_VISIT_DIABETES_FEN=172;//分工软件保存高血糖
	public static final Integer COMMAND_ID_CHAOZHOU_GET_VISIT_DIABETES_FEN=173;//分工软件取得高血糖

	public static final Integer COMMAND_ID_CHAOZHOU_SET_IFCQ_RESULT_FEN=180;//分工软件保存老年人中医药健康管理
	public static final Integer COMMAND_ID_CHAOZHOU_GET_IFCQ_RESULT_FEN=181;//分工软件取得老年人中医药健康管理
	public static final Integer COMMAND_ID_CHAOZHOU_SET_OLDPEOPLE_SELFCARE_FEN=182;//分工软件保存老年人生活自理评估
	public static final Integer COMMAND_ID_CHAOZHOU_GET_OLDPEOPLE_SELFCARE_FEN=183;//分工软件取得老年人生活自理评估
	
	public static final String PACKAGE_INFO_HOSPITAL_NO = "hospitalNo";	
	public static final String PACKAGE_INFO_KEY_JSON_PATIENTSIGN_CONDITION = "jsonPatientSignCondition";
	public static final String PACKAGE_INFO_KEY_JSON_VISITDIABETES_CONDITION = "jsonVisitDiabetesCondition";
	public static final String PACKAGE_INFO_KEY_JSON_VISITHYPERTENSION_CONDITION = "jsonVisitHypertensionCondition";
	public static final String PACKAGE_INFO_KEY_JSON_XIEYA_CONDITION = "jsonXieYaCondition";
	public static final String PACKAGE_INFO_KEY_JSON_XIEYANG_CONDITION = "jsonXieYangCondition";
	public static final String PACKAGE_INFO_KEY_JSON_TIWEN_CONDITION = "jsonTiWenCondition";
	public static final String PACKAGE_INFO_KEY_JSON_XINDIAN_CONDITION = "jsonXinDianCondition";
	public static final String PACKAGE_INFO_KEY_JSON_NIAOYE_CONDITION = "jsonNiaoYeCondition";
	public static final String PACKAGE_INFO_KEY_JSON_XIETANG_CONDITION = "jsonXieTangCondition";
	public static final String PACKAGE_INFO_KEY_JSON_XIEZHI_CONDITION = "jsonXieZhiCondition";
	public static final String PACKAGE_INFO_KEY_JSON_IFCQRESULT_CONDITION = "jsonIFCQResultCondition";
	public static final String PACKAGE_INFO_KEY_JSON_OLDPEOPLESELFCARE_CONDITION = "jsonOldPeopleSelfcareCondition";
	public static final String PACKAGE_INFO_KEY_JSON_DEVICE_CONDITION = "jsonDeviceCondition";
	
	public static final Integer COMMAND_ID_CHAOZHOU_SET_SYSTEM_AUZ=500;//授权
	public static final Integer COMMAND_ID_CHAOZHOU_GET_SYSTEM_AUZ=501;//查询授权
	public static final Integer COMMAND_ID_CHAOZHOU_SAVE_QUERYED_SYSTEM_AUZ=502;//保存查询到的client端授权
	public static final String PACKAGE_INFO_KEY_JSON_SYSTEM_AUZ = "jsonSystemAuz";
	
	public static void main(String[] args) {

		BizPackage pack = new BizPackage();
		pack.setHandleType(1);
		pack.setCommandId(19);
		pack.setResult(1);

		List<PatientSignContractChaozhou> list = new ArrayList();
		PatientSignContractChaozhou model = new PatientSignContractChaozhou();
		// model.setPatientSignContractId();
		model.setIdCard("452526196005062589");
		model.setTime((new Date()).getTime() + "");
		model.setPatientChaozhouId(1);
		model.setHospitalNo("HP00001");
		model.setSignHospitalDepartNo("DP01BBL01NKX");
		model.setPersonName("张南");
		model.setSexCode("0");
		model.setWhetherInsured(1);
		model.setProvinceNo("440000");
		model.setCityNo("440300");
		model.setCountyNo("440306");
		model.setStreetNo("440306003000");
		model.setCommitteeNo("440306003033");
		model.setHomeAddress("深圳市宝安区西乡固戍");
		model.setContactAddress("深圳市宝安区西乡固戍");
		model.setMobileNumber("15682666780");
		model.setPhoneNumber("");
		model.setPopulationTotal(3);
		model.setAboveYear65Total(1);
		model.setServiceResult("");
		model.setCrowdType("4");
		model.setCrowdTypeMore("~4~");
		model.setSignType(0);// 1自助签约类型，0为默认
		model.setSignCreator("13682666783");
		model.setSignStatus(1);
		// 如果户主来签约,私有，即不是家庭签约
		model.setIsJustHolder(2);
		model.setUpPatientSignContractId(-1);

		String signStr = DataValidUtil.handleJsStr("2017-10-12");
		Date signDate = null;
		if (DataValidUtil.validateStrToDate(signStr,
				DateTimeUtil.AISAT_DATE_FORMAT_DAY)) {
			signDate = DataValidUtil.setStrToDate(signStr,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
			model.setSignDate(signDate);
		}
		model.setSignDateStr(signStr);
		String startStr = DataValidUtil.handleJsStr("2017-10-12");
		Date startDate = null;
		if (DataValidUtil.validateStrToDate(startStr,
				DateTimeUtil.AISAT_DATE_FORMAT_DAY)) {
			startDate = DataValidUtil.setStrToDate(startStr,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
			model.setStartDate(startDate);
			// System.out.println("Test date : " + date);
		}
		model.setStartDateStr(startStr);
		String endStr = DataValidUtil.handleJsStr("2017-10-12");
		Date endDate = null;
		if (DataValidUtil.validateStrToDate(endStr,
				DateTimeUtil.AISAT_DATE_FORMAT_DAY)) {
			endDate = DataValidUtil.setStrToDate(endStr,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
			model.setEndDate(endDate);
			// System.out.println("Test date : " + date);
		}
		model.setEndDateStr(endStr);
		// HttpSession session = req.getSession();
		// Object obj = session.getAttribute("loginUser");
		// if (obj != null) {
		// UserInterface userIf = (UserInterface) obj;
		// model.setSignCreator(userIf.getUserId() + "");
		// }
		model.setCreateDate(new Date());
		model.setUpdateDate(new Date());
		list.add(model);

		pack.setDataList(list);
		
		pack.getJsonObject();
		System.out.print(pack.getJsonObject().toString());
		String abc="{\"handleType\":0,\"commandId\":19,\"date\":{\"doctor\":{\"address\":\"深圳\",\"birthday\":null,\"confirmPassword\":null,\"createDateStr\":null,\"email\":\"444@qq.com\",\"endDateStr\":null,\"hospitalDepartInfo\":null,\"hospitalNo\":null,\"lastLoginStr\":null,\"name\":null,\"operation\":null,\"password\":\"123456\",\"period\":null,\"profile\":null,\"startDateStr\":null,\"telephone\":null,\"time\":null,\"createDate\":null,\"doctorCode\":\"\",\"doctorId\":null,\"endDate\":null,\"fixedTelephone\":null,\"headPortraitPath\":null,\"hospitalDepartNo\":null,\"hospitalId\":null,\"idCard\":\"446687198711236666\",\"isOsen\":null,\"isValid\":null,\"jobNumber\":null,\"joinOsenDate\":null,\"lastLogin\":\"777\",\"lifePicturePath\":null,\"loginCount\":null,\"osenHospitalDepartNo\":null,\"osenHospitalId\":null,\"privateDoctorAuzStatus\":null,\"professionalTitle\":null,\"recommendPatients\":null,\"servicePatients\":null,\"sexCode\":null,\"signIcon\":null,\"startDate\":null,\"updateDate\":null,\"verifyCode\":null},\"hospitalDepart\":{\"address\":null,\"creator\":null,\"description\":null,\"email\":null,\"leader\":null,\"level\":null,\"telephone\":null,\"time\":\"\",\"businessLevel\":null,\"businessType\":1,\"createDate\":null,\"departAbbr\":null,\"departLogoPath\":null,\"departName\":null,\"departNo\":null,\"displayOrder\":null,\"hospitalDepartId\":null,\"hospitalId\":null,\"hospitalNo\":null,\"leaderDoctorId\":null,\"upDepartNo\":null,\"updateDate\":null},\"patientChaozhous\":[{\"birthday\":\"1987-11-11\",\"creator\":\"\",\"email\":\"\",\"password\":\"\",\"period\":0,\"time\":\"\",\"cityNo\":\"110100\",\"committeeNo\":\"110101001001\",\"contactAddress\":\"深圳市\",\"countyNo\":\"110101\",\"createName\":null,\"createTime\":\"\",\"crowdType\":\"4\",\"crowdTypeMore\":\"\",\"endDate\":\"\",\"familyName\":\"\",\"familyNumber\":\"\",\"familyRelation\":0,\"guardianIdCard\":\"\",\"guardianName\":\"\",\"headPortraitpath\":\"\",\"homeAddress\":\"深圳市\",\"idCard\":\"444444198711112222\",\"isHouseHolder\":1,\"isOsen\":0,\"isValid\":false,\"joinOsenDate\":\"\",\"lastLogin\":\"\",\"lifePicturePath\":\"\",\"loginCount\":0,\"mobileNumber\":\"11114444\",\"myHospitalId\":\"\",\"myHospitalNo\":null,\"patientChaozhouId\":1510559282169,\"patientCode\":\"\",\"personName\":\"看看\",\"phoneNumber\":\"222\",\"populationTotal\":2,\"privateDoctorAuzStatus\":0,\"provinceNo\":\"110000\",\"recommendDoctors\":\"\",\"serviceDoctors\":\"\",\"sexCode\":\"2\",\"startDate\":\"\",\"streetNo\":\"110101001000\",\"upPatientChaozhouId\":0,\"updateTime\":\"\",\"verifyCode\":\"\",\"whetherInsured\":1},{\"birthday\":\"1988-11-11\",\"creator\":\"\",\"email\":\"\",\"password\":\"\",\"period\":0,\"time\":\"\",\"cityNo\":\"440300\",\"committeeNo\":\"440305005005\",\"contactAddress\":\"深圳市\",\"countyNo\":\"440305\",\"createName\":null,\"createTime\":\"\",\"crowdType\":\"\",\"crowdTypeMore\":\"\",\"endDate\":\"\",\"familyName\":\"\",\"familyNumber\":\"\",\"familyRelation\":8,\"guardianIdCard\":\"\",\"guardianName\":\"\",\"headPortraitpath\":\"\",\"homeAddress\":\"深圳市\",\"idCard\":\"111111198811112233\",\"isHouseHolder\":0,\"isOsen\":0,\"isValid\":false,\"joinOsenDate\":\"\",\"lastLogin\":\"\",\"lifePicturePath\":\"\",\"loginCount\":0,\"mobileNumber\":\"111\",\"myHospitalId\":\"\",\"myHospitalNo\":null,\"patientChaozhouId\":1510559327698,\"patientCode\":\"\",\"personName\":\"44\",\"phoneNumber\":\"222\",\"populationTotal\":2,\"privateDoctorAuzStatus\":0,\"provinceNo\":\"440000\",\"recommendDoctors\":\"\",\"serviceDoctors\":\"\",\"sexCode\":\"1\",\"startDate\":\"\",\"streetNo\":\"440305005000\",\"upPatientChaozhouId\":0,\"updateTime\":\"\",\"verifyCode\":\"\",\"whetherInsured\":0}],\"patientServicePacks\":[{\"time\":null,\"crowdType\":4,\"patientServicePackId\":null,\"patientSignContractId\":null,\"serviceCrowdPackId\":null}],\"patientSignContracts\":[{\"creator\":\"\",\"time\":\"\",\"aboveYear65Total\":null,\"contactAddress\":\"深圳市\",\"createDate\":\"\",\"crowdTypeMore\":null,\"crowdTypes\":\"\",\"endDate\":\"2018-11-13\",\"homeAddress\":\"深圳市\",\"hospitalId\":null,\"hospitalNo\":null,\"idCard\":\"444444198711112222\",\"isJustHolder\":0,\"isUsing\":null,\"mobileNumber\":\"11114444\",\"patientChaozhouId\":1510559282169,\"patientSignContractId\":1510559282169,\"personName\":\"看看\",\"phoneNumber\":\"222\",\"populationTotal\":null,\"serviceResult\":\"\",\"sexCode\":\"2\",\"signCreator\":\"\",\"signDateStr\":\"2017-11-13\",\"signHospitalDepartId\":null,\"signHospitalDepartNo\":null,\"signIcon\":\"\",\"signStatus\":null,\"signType\":0,\"startDate\":\"2017-11-13\",\"terminateDate\":\"\",\"terminateHospitalDepartId\":null,\"terminateReason\":\"\",\"upPatientSignContractId\":0,\"updateDate\":\"\",\"whetherInsured\":1},{\"creator\":\"\",\"time\":\"\",\"aboveYear65Total\":null,\"contactAddress\":\"深圳市\",\"createDate\":\"\",\"crowdTypeMore\":null,\"crowdTypes\":\"\",\"endDate\":\"2018-11-13\",\"homeAddress\":\"深圳市\",\"hospitalId\":null,\"hospitalNo\":null,\"idCard\":\"111111198811112233\",\"isJustHolder\":0,\"isUsing\":null,\"mobileNumber\":\"\",\"patientChaozhouId\":null,\"patientSignContractId\":1510559327698,\"personName\":\"44\",\"phoneNumber\":\"222\",\"populationTotal\":null,\"serviceResult\":\"\",\"sexCode\":\"1\",\"signCreator\":\"\",\"signDateStr\":\"\",\"signHospitalDepartId\":null,\"signHospitalDepartNo\":null,\"signIcon\":\"\",\"signStatus\":null,\"signType\":0,\"startDate\":\"2017-11-13\",\"terminateDate\":\"\",\"terminateHospitalDepartId\":null,\"terminateReason\":\"\",\"upPatientSignContractId\":0,\"updateDate\":\"\",\"whetherInsured\":0}]}}接收到要处理的信息如下：{\"handleType\":0,\"commandId\":19,\"date\":{\"doctor\":{\"address\":\"深圳\",\"birthday\":null,\"confirmPassword\":null,\"createDateStr\":null,\"email\":\"444@qq.com\",\"endDateStr\":null,\"hospitalDepartInfo\":null,\"hospitalNo\":null,\"lastLoginStr\":null,\"name\":null,\"operation\":null,\"password\":\"123456\",\"period\":null,\"profile\":null,\"startDateStr\":null,\"telephone\":null,\"time\":null,\"createDate\":null,\"doctorCode\":\"\",\"doctorId\":null,\"endDate\":null,\"fixedTelephone\":null,\"headPortraitPath\":null,\"hospitalDepartNo\":null,\"hospitalId\":null,\"idCard\":\"446687198711236666\",\"isOsen\":null,\"isValid\":null,\"jobNumber\":null,\"joinOsenDate\":null,\"lastLogin\":\"777\",\"lifePicturePath\":null,\"loginCount\":null,\"osenHospitalDepartNo\":null,\"osenHospitalId\":null,\"privateDoctorAuzStatus\":null,\"professionalTitle\":null,\"recommendPatients\":null,\"servicePatients\":null,\"sexCode\":null,\"signIcon\":null,\"startDate\":null,\"updateDate\":null,\"verifyCode\":null},\"hospitalDepart\":{\"address\":null,\"creator\":null,\"description\":null,\"email\":null,\"leader\":null,\"level\":null,\"telephone\":null,\"time\":\"\",\"businessLevel\":null,\"businessType\":1,\"createDate\":null,\"departAbbr\":null,\"departLogoPath\":null,\"departName\":null,\"departNo\":null,\"displayOrder\":null,\"hospitalDepartId\":null,\"hospitalId\":null,\"hospitalNo\":null,\"leaderDoctorId\":null,\"upDepartNo\":null,\"updateDate\":null},\"patientChaozhous\":[{\"birthday\":\"1987-11-11\",\"creator\":\"\",\"email\":\"\",\"password\":\"\",\"period\":0,\"time\":\"\",\"cityNo\":\"110100\",\"committeeNo\":\"110101001001\",\"contactAddress\":\"深圳市\",\"countyNo\":\"110101\",\"createName\":null,\"createTime\":\"\",\"crowdType\":\"4\",\"crowdTypeMore\":\"\",\"endDate\":\"\",\"familyName\":\"\",\"familyNumber\":\"\",\"familyRelation\":0,\"guardianIdCard\":\"\",\"guardianName\":\"\",\"headPortraitpath\":\"\",\"homeAddress\":\"深圳市\",\"idCard\":\"444444198711112222\",\"isHouseHolder\":1,\"isOsen\":0,\"isValid\":false,\"joinOsenDate\":\"\",\"lastLogin\":\"\",\"lifePicturePath\":\"\",\"loginCount\":0,\"mobileNumber\":\"11114444\",\"myHospitalId\":\"\",\"myHospitalNo\":null,\"patientChaozhouId\":1510559282169,\"patientCode\":\"\",\"personName\":\"看看\",\"phoneNumber\":\"222\",\"populationTotal\":2,\"privateDoctorAuzStatus\":0,\"provinceNo\":\"110000\",\"recommendDoctors\":\"\",\"serviceDoctors\":\"\",\"sexCode\":\"2\",\"startDate\":\"\",\"streetNo\":\"110101001000\",\"upPatientChaozhouId\":0,\"updateTime\":\"\",\"verifyCode\":\"\",\"whetherInsured\":1},{\"birthday\":\"1988-11-11\",\"creator\":\"\",\"email\":\"\",\"password\":\"\",\"period\":0,\"time\":\"\",\"cityNo\":\"440300\",\"committeeNo\":\"440305005005\",\"contactAddress\":\"深圳市\",\"countyNo\":\"440305\",\"createName\":null,\"createTime\":\"\",\"crowdType\":\"\",\"crowdTypeMore\":\"\",\"endDate\":\"\",\"familyName\":\"\",\"familyNumber\":\"\",\"familyRelation\":8,\"guardianIdCard\":\"\",\"guardianName\":\"\",\"headPortraitpath\":\"\",\"homeAddress\":\"深圳市\",\"idCard\":\"111111198811112233\",\"isHouseHolder\":0,\"isOsen\":0,\"isValid\":false,\"joinOsenDate\":\"\",\"lastLogin\":\"\",\"lifePicturePath\":\"\",\"loginCount\":0,\"mobileNumber\":\"111\",\"myHospitalId\":\"\",\"myHospitalNo\":null,\"patientChaozhouId\":1510559327698,\"patientCode\":\"\",\"personName\":\"44\",\"phoneNumber\":\"222\",\"populationTotal\":2,\"privateDoctorAuzStatus\":0,\"provinceNo\":\"440000\",\"recommendDoctors\":\"\",\"serviceDoctors\":\"\",\"sexCode\":\"1\",\"startDate\":\"\",\"streetNo\":\"440305005000\",\"upPatientChaozhouId\":0,\"updateTime\":\"\",\"verifyCode\":\"\",\"whetherInsured\":0}],\"patientServicePacks\":[{\"time\":null,\"crowdType\":4,\"patientServicePackId\":null,\"patientSignContractId\":null,\"serviceCrowdPackId\":null}],\"patientSignContracts\":[{\"creator\":\"\",\"time\":\"\",\"aboveYear65Total\":null,\"contactAddress\":\"深圳市\",\"createDate\":\"\",\"crowdTypeMore\":null,\"crowdTypes\":\"\",\"endDate\":\"2018-11-13\",\"homeAddress\":\"深圳市\",\"hospitalId\":null,\"hospitalNo\":null,\"idCard\":\"444444198711112222\",\"isJustHolder\":0,\"isUsing\":null,\"mobileNumber\":\"11114444\",\"patientChaozhouId\":1510559282169,\"patientSignContractId\":1510559282169,\"personName\":\"看看\",\"phoneNumber\":\"222\",\"populationTotal\":null,\"serviceResult\":\"\",\"sexCode\":\"2\",\"signCreator\":\"\",\"signDateStr\":\"2017-11-13\",\"signHospitalDepartId\":null,\"signHospitalDepartNo\":null,\"signIcon\":\"\",\"signStatus\":null,\"signType\":0,\"startDate\":\"2017-11-13\",\"terminateDate\":\"\",\"terminateHospitalDepartId\":null,\"terminateReason\":\"\",\"upPatientSignContractId\":0,\"updateDate\":\"\",\"whetherInsured\":1},{\"creator\":\"\",\"time\":\"\",\"aboveYear65Total\":null,\"contactAddress\":\"深圳市\",\"createDate\":\"\",\"crowdTypeMore\":null,\"crowdTypes\":\"\",\"endDate\":\"2018-11-13\",\"homeAddress\":\"深圳市\",\"hospitalId\":null,\"hospitalNo\":null,\"idCard\":\"111111198811112233\",\"isJustHolder\":0,\"isUsing\":null,\"mobileNumber\":\"\",\"patientChaozhouId\":null,\"patientSignContractId\":1510559327698,\"personName\":\"44\",\"phoneNumber\":\"222\",\"populationTotal\":null,\"serviceResult\":\"\",\"sexCode\":\"1\",\"signCreator\":\"\",\"signDateStr\":\"\",\"signHospitalDepartId\":null,\"signHospitalDepartNo\":null,\"signIcon\":\"\",\"signStatus\":null,\"signType\":0,\"startDate\":\"2017-11-13\",\"terminateDate\":\"\",\"terminateHospitalDepartId\":null,\"terminateReason\":\"\",\"upPatientSignContractId\":0,\"updateDate\":\"\",\"whetherInsured\":0}]}}";
//		ContractDataHandler dh=new ContractDataHandler();
//		dh.handle(abc);//pack.getJsonObject().toString()
	}

	/**
	 * 解析Biz路由信息
	 * 
	 * @param jObject
	 * @return
	 */
	public static BizPackage parseBizRouterInfo(JSONObject jObject) {
		BizPackage pack = new BizPackage();

		int handleType = jObject
				.getInt(HttpConstants.PACKAGE_INFO_KEY_HANDLETYPE);
		int commandId = jObject
				.getInt(HttpConstants.PACKAGE_INFO_KEY_COMMANDID);
		int totalRows= 0;
		//System.out.println(jObject.get(PACKAGE_INFO_KEY_TOTALROWS).toString());

		if(jObject.isNull(PACKAGE_INFO_KEY_TOTALROWS)==false&& !"null".equals(jObject.get(PACKAGE_INFO_KEY_TOTALROWS).toString()) && !"".equals(jObject.get(PACKAGE_INFO_KEY_TOTALROWS).toString())){	
			totalRows=jObject.getInt(PACKAGE_INFO_KEY_TOTALROWS);
		}
		int pageSize= 0;
		if(jObject.isNull(PACKAGE_INFO_KEY_PAGESIZE)==false && !"null".equals(jObject.get(PACKAGE_INFO_KEY_PAGESIZE).toString()) && !"".equals(jObject.get(PACKAGE_INFO_KEY_PAGESIZE).toString())){	
			pageSize=jObject.getInt(PACKAGE_INFO_KEY_PAGESIZE);
		}
		int currentPage= 0;
		if(jObject.isNull(PACKAGE_INFO_KEY_CURRENTPAGE)==false&& !"null".equals(jObject.get(PACKAGE_INFO_KEY_CURRENTPAGE).toString()) && !"".equals(jObject.get(PACKAGE_INFO_KEY_CURRENTPAGE).toString())){	
			currentPage=jObject.getInt(PACKAGE_INFO_KEY_CURRENTPAGE);
		}

		pack.setHandleType(handleType);
		pack.setCommandId(commandId);
		pack.setTotalRows(totalRows);
		pack.setPageSize(pageSize);
		pack.setCurrentPage(currentPage);

		return pack;
	}
}
