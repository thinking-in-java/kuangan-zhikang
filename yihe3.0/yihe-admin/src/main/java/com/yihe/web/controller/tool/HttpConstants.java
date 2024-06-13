package com.yihe.web.controller.tool;

public class HttpConstants {
	public static final String PACKAGE_INFO_KEY_VERSION = "version";
	public static final String PACKAGE_INFO_KEY_HANDLETYPE = "handleType";
	public static final String PACKAGE_INFO_KEY_COMMANDID = "commandId";
	public static final String PACKAGE_INFO_KEY_ENCRYPTEDMODE = "encryptedMode";
	public static final Integer HANDLE_TYPE_APPLY = 0;
	public static final Integer HANDLE_TYPE_RESULT = 1;
	public static final String PACKAGE_INFO_KEY_SUBCOMMANDIDS = "subCommandIds";
	public static final String PACKAGE_INFO_PATIENT_SIGN_CONTRACTS = "patientSignContracts";
	public static final String PACKAGE_INFO_PATIENT_CHAOZHOUS = "patientChaozhous";
	public static final String PACKAGE_INFO_DOCTOR = "doctor";
	public static final String PACKAGE_INFO_HOSPITAL_DEPART = "hospitalDepart";
	public static final String PACKAGE_INFO_PATIENT_SERVICE_PACKS="patientServicePacks";
	public static final String PACKAGE_INFO_KEY_DATE = "date";
	public static final String PACKAGE_INFO_KEY_OTHERDATE = "otherDate";
	public static final String PACKAGE_INFO_KEY_RESULT = "result";
	public static final String PACKAGE_INFO_KEY_RESULTINFO = "resultInfo";
	public static final Integer RESULT_INFO_OK = 0;
	public static final Integer RESULT_INFO_ERROR = 1;
	
	//shared status
	public static final Integer SERVER_STATUS_NOTSETUP = -1;
	public static final Integer SERVER_STATUS_NOTHING = 0;
	public static final Integer SERVER_STATUS_OFFLINE = 3;
	public static final Integer SERVER_STATUS_ONLINE = 4;	
	
	//COMMAND_ID 0到10系统保留
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL=11;//取得医院
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL_DEPART=12;//取得医生组
	public static final Integer COMMAND_ID_CHAOZHOU_GET_DOCTOR=13;//取得医生,作登录功能
	public static final Integer COMMAND_ID_CHAOZHOU_GET_DOCTOR_DEPARMENT=14;//取得医生所在医生组
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CROWD=15;//取得医院人群类型
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CROWD_SERVICE_PACK=16;//取得医院人群包
	public static final Integer COMMAND_ID_CHAOZHOU_GET_PATIENT88=17;//取得居民（病人），这个暂时未用
	public static final Integer COMMAND_ID_CHAOZHOU_GET_FAMILY=18;//取得家庭，这个暂时未用
	public static final Integer COMMAND_ID_CHAOZHOU_SET_PATIENT_SIGN_CONTRACT=19;//设置居民（病人）签约
	public static final Integer COMMAND_ID_CHAOZHOU_GET_PATIENT_SIGN_CONTRACT=20;//取得居民（病人）签约，这个暂时未用
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL_DOCTORS=21;//取得医院里所有医生
	public static final Integer COMMAND_ID_CHAOZHOU_GET_HOSPITAL_DOCTORS_TEAMS=22;//取得医院里所有医生对应该的医生组
	
	public static final Integer COMMAND_ID_CHAOZHOU_SET_BLOODPRESSURE=30;//保存血压列表
	public static final Integer COMMAND_ID_CHAOZHOU_GET_BLOODPRESSURE_LIST=31;//取得血压列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_TEMPERATURE=33;//保存温度
	public static final Integer COMMAND_ID_CHAOZHOU_GET_TEMPERATURE_LIST=34;//取得温度列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_CARDIOGRAM=36;//保存心电
	public static final Integer COMMAND_ID_CHAOZHOU_GET_CARDIOGRAM_LIST=37;//取得心电列表
		
	public static final Integer COMMAND_ID_CHAOZHOU_GET_PATIENT=111;//取得居民
	public static final Integer COMMAND_ID_CHAOZHOU_SET_TEMP_LIST=112;//保存温度列表
	public static final Integer COMMAND_ID_CHAOZHOU_GET_TEMP_LIST=113;//取得温度列表
	public static final Integer COMMAND_ID_CHAOZHOU_SET_DEVICE=114;//保存设备
	
}
