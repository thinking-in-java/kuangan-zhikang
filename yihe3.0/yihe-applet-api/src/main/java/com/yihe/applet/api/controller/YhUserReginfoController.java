package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yihe.applet.api.domain.YhJzSysSmsApp;
import com.yihe.applet.api.domain.YhUserPersonageConditionApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhWxUser;
import com.yihe.applet.api.service.YhJzSysSmsAppService;
import com.yihe.applet.api.service.YhUserPersonageConditionAppService;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.applet.api.service.YhWxUserAppService;
import com.yihe.common.config.Global;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.RestfulDemo;
import com.yihe.common.utils.StringUtils;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRolePower;
import com.yihe.system.service.*;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * YhUserReginfocontroller Created by dyb on 2020/2/25.
 */ 
@Controller
@RequestMapping("/api/YhUserReginfo")
@Api(value = "YhUserReginfo控制器", description = "YhUserReginfo管理")
public class YhUserReginfoController{

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserReginfoController.class);
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired
	YhUserReginfoAppService YhUserReginfoService;
	@Autowired
	YhJzSysSmsAppService yhJzSysSmsAppService;
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	@Autowired
	YhUserMakeCardService yhUserMakeCardService;
	@Autowired
	YhUserRoleService yhUserRoleService;
	@Autowired
	YhUserRolePowerService yhUserRolePowerService;
	@Autowired
	YhWxUserAppService YhWxUserService;
	@Autowired
	YhOrgTreeService yhOrgTreeService;

	@Autowired
	YhUserPersonageConditionAppService yhUserPersonageConditionService;
	// 以下为测试代码，随机生成验证码
	private static int newcode;

	public static int getNewcode() {
		return newcode;
	}


	public static void setNewcode() {
		newcode = (int) ((Math.random() * 9 + 1) * 100000); // 每次调用生成一次6位数的随机数
	}

	/**
	 * 查询用户是否填写基本信息
	 */
	@RequestMapping(value = "/checkBasicRegistrationInformation")
	@ResponseBody
	public Result checkBasicRegistrationInformation(String usercode){
		/**
		 * 是否激活码激活的
		 */
		YhUserMakeCard yhUserMakeCard = yhUserMakeCardService.selectOrgIdbyParam(usercode);
		if(null==yhUserMakeCard){
			return Result.ok();
		}

		String key = yhUserMakeCard.getKey();

		if(StringUtil.isEmpty(key)){
				return Result.ok();
		}

		/**
		 * 查询用户身高体重
		 */
		YhUserPersonageConditionApp yhUserPersonageConditionApp = yhUserPersonageConditionService.selectByHeightAndWeight(usercode);
		if(null==yhUserPersonageConditionApp){
				return Result.error();
		}
		String stature = yhUserPersonageConditionApp.getStature();
		String weight = yhUserPersonageConditionApp.getWeight();
		if(StringUtil.isEmpty(stature)||StringUtil.isEmpty(weight)){
			return Result.error();
		}
		/**
		 * 查询用户真实姓名、出生年月、性别
		 */
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(usercode);

		if(null==yhUserReginfo){
			return Result.error();
		}
		String name = yhUserReginfo.getName();
		Date birthDate = yhUserReginfo.getBirthDate();
		Integer gender = yhUserReginfo.getGender();
		if (StringUtil.isEmpty(name)||null==birthDate||null==gender) {
			return Result.error();
		}


		return Result.ok();
	}


	/**
	 * 注册基本信息
	 */
	@RequestMapping(value = "/basicRegistrationInformation")
	@ResponseBody
	@Transactional
	public Result basicRegistrationInformation(String usercode,String name,Integer sex,String time,Double height,Double weight){
		if(StringUtil.isEmpty(usercode)||StringUtil.isEmpty(name)||null==sex||StringUtil.isEmpty(time)||null==height||null==weight){
			return Result.error();
		}


		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(time, pos);
		YhUserReginfo record=new YhUserReginfo();
		record.setUserCode(usercode);
		record.setBirthDate(strtodate);
		record.setGender(sex);
		record.setName(name);

		yhUserReginfoService.updateByPrimaryKeySelectives(record);
		YhUserReginfo reginfo = yhUserReginfoService.selectByPrimaryKey(usercode);
		JSONArray jsonArray = new JSONArray();
		JSONObject map = new JSONObject();

		JSONObject operationinfo = new JSONObject();
		operationinfo.put("operator", "1");
		map.put("operationinfo", operationinfo);

		JSONObject mainTable = new JSONObject();
		mainTable.put("xm", reginfo.getName());
		if(reginfo.getGender() != null){
			mainTable.put("xb", reginfo.getGender() - 1);
		}
		if(reginfo.getBirthDate() != null){
			mainTable.put("csrq", DateUtils.dateTime(reginfo.getBirthDate()));
		}
		mainTable.put("brdh", reginfo.getMobile());
		mainTable.put("sfzh", reginfo.getCardidentity());
		//查询客户绑定id
		String oaTreeId = yhOrgTreeService.queryOaTreeId2(reginfo.getEnrolCardId());
		if (StringUtils.isNotEmpty(oaTreeId)) {
			mainTable.put("gzdw", oaTreeId);
		}
		map.put("mainTable", mainTable);
		jsonArray.add(map);
		String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/sysncPersionInfo", jsonArray);

		YhUserPersonageConditionApp cond=new YhUserPersonageConditionApp();
		cond.setUserCode(usercode);
		cond.setStature(String.valueOf(height));
		cond.setWeight(String.valueOf(weight));
		YhUserPersonageConditionApp list = yhUserPersonageConditionService.showcondition(cond.getUserCode());
		if (list == null) {
			  yhUserPersonageConditionService.insertSelective(cond);
		} else {
			cond.setId(list.getId());
			yhUserPersonageConditionService.updateByPrimaryKeySelective(cond);
		}
		return Result.ok(reginfo);
	}


	/**
	 * 激活账号activation
	 */
	@RequestMapping(value = "/activationUser")
	@ResponseBody
	@Transactional
	public Result activationUser(String code,String user,String phone,String pwd,String pwd2,String msgCode,String msgCode2,String cardidentity) {
		if(StringUtil.isEmpty(code)||
				StringUtil.isEmpty(user)||
				StringUtil.isEmpty(phone)||
				StringUtil.isEmpty(pwd)||
				StringUtil.isEmpty(pwd2)||
				StringUtil.isEmpty(msgCode)||
				StringUtil.isEmpty(msgCode2)||
				StringUtil.isEmpty(cardidentity)
		){
			return Result.error();
		}


		if(!pwd.equals(pwd2)){
			return Result.error("两次密码不相等");
		}

		if(!msgCode.equals(msgCode2)){
			return Result.error("验证码错误");
		}
		if(phone.length()!=11){
			return Result.error("手机号格式不对");
		}

		/**
		 * 激活码是否使用
		 */
		YhUserMakeCard sc = yhUserReginfoService.selectActivationCardId(code);
		if(null==sc){
			return Result.error("激活码错误");
		}

		if(StringUtil.isNotEmpty(sc.getUserCode())){
			return Result.error("激活码被使用");
		}
		YhUserReginfo yhUserReginfo2 = yhUserReginfoService.selectMobileAndName2(phone);
		if (yhUserReginfo2 != null) {
			return Result.error("手机号码已被其他用户使用");
		}
		YhUserReginfo yhUserReginfo3 = yhUserReginfoService.selectMobileAndName(user);
		if (yhUserReginfo3 != null) {
			return Result.error("登录账号已存在，不能重名创建");
		}



		YhUserReginfo yhUserReginfo=new YhUserReginfo();
		yhUserReginfo.setMobile(phone);
		yhUserReginfo.setOrgCode(sc.getOrgId());
		yhUserReginfo.setUserType(5);
		yhUserReginfo.setCardidentity(cardidentity);
		yhUserReginfo.setEnrolCardId(sc.getEnrolCardId());
		String userCode="";
		synchronized (this){
			//开始注册
			 userCode = yhUserReginfoService.selectMaxUserCode(5);
		}
		if(StringUtil.isEmpty(userCode)){
			return Result.error("注册失败");
		}
		String userCode1 = String.valueOf(Integer.valueOf(userCode) + 1);
		yhUserReginfo.setUserCode(userCode1);
		 yhUserReginfo.setUserName(user);
		SysPasswordService sysPasswordService = new SysPasswordService();
		sysPasswordService.encryptPassword(user, pwd);
		String nameAndPwd = new Md5Hash(user + pwd).toHex();
		yhUserReginfo.setUserPwd(nameAndPwd);
		int addReg = yhUserReginfoService.regSubmitActivation(yhUserReginfo);


		// 添加注册卡表的userCode
		YhUserMakeCard record = new YhUserMakeCard();
		record.setEnrolCardId(yhUserReginfo.getEnrolCardId());
		record.setUserCode(yhUserReginfo.getUserCode());
		yhUserMakeCardService.updateByPrimaryKeySelective(record);
		// 往用户信息扩展表插入一条数据 userCode Evaluateflag
		YhUserEnrolinfo enrolinfo = yhUserEnrolinfoService.selectByPrimaryKeyByUsercode(yhUserReginfo.getUserCode());
		if (enrolinfo == null) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setUserCode(yhUserReginfo.getUserCode());
			yhUserEnrolinfo.setEvaluateflag(0);
			yhUserEnrolinfoService.insertSelective(yhUserEnrolinfo);
		}

		// 查询角色表
		Integer roleId = yhUserRoleService.selectRoleId("用户");
		// 用户角色关联表插入id和code
		YhUserRolePower yhUserRolePower = new YhUserRolePower();
		yhUserRolePower.setRoleid(roleId);
		yhUserRolePower.setUserid(yhUserReginfo.getUserCode());
		yhUserRolePowerService.inssert(yhUserRolePower);
		return Result.ok("注册成功",yhUserReginfo);
	}




	@ApiOperation(value = "查询默认机构，医生下未用的注册卡")
	@RequestMapping(value = "/selectUnspentRegCard", method = RequestMethod.POST)
	@ResponseBody
	public Result selectUnspentRegCard() {
		YhUserMakeCard yhUserMakeCard = yhUserMakeCardService.selectUnspentRegCard();
		return Result.ok(yhUserMakeCard);
	}
	
	
	@ApiOperation(value = "查询用户手机号")
	@RequestMapping(value = "/selectMobile", method = RequestMethod.POST)
	@ResponseBody
	public YhUserReginfo selectMobile(String mobile) {
		System.out.println("--" + mobile);
		YhUserReginfo yhUserReginfo2 = yhUserReginfoService.selectMobileAndName2(mobile);
		return yhUserReginfo2;
	}

	@ApiOperation(value = "查询用户名")
	@RequestMapping(value = "/selectName", method = RequestMethod.POST)
	@ResponseBody
	public YhUserReginfo selectName(String userName) {
		System.out.println("--" + userName);
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectMobileAndName(userName);
		return yhUserReginfo;
	}
	
	@ApiOperation(value = "注册提交")
	@RequestMapping(value = "/regSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Result addYhUserReginfo(YhUserReginfo yhUserReginfo,String yanzhengma, String code) throws ParseException {
		LOGGER.info("yhUserReginfo={}", yhUserReginfo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取String类型的时间
		if (!code.equals(yanzhengma)) {
			return Result.error("验证码错误");
		}
		// 16674674756
		String userCode = yhUserReginfoService.selectMaxUserCode(yhUserReginfo.getUserType());
		/*
		 * if(yhUserReginfo.getLinetime().equals("")){ yhUserReginfo.setLinetime(null);
		 * }
		 */
		YhUserMakeCard yhUserMakeCard = yhUserMakeCardService.selectUnspentRegCard();
		yhUserReginfo.setUserType(5);
		String userCode1 = String.valueOf(Integer.valueOf(userCode) + 1);
		yhUserReginfo.setUserCode(userCode1);
		yhUserReginfo.setRegTime(new Date());
		Date date = sdf.parse(yhUserReginfo.getCreatedate());
		yhUserReginfo.setBirthDate(date);
		yhUserReginfo.setEnrolCardId(yhUserMakeCard.getEnrolCardId());
		yhUserReginfo.setOrgCode(yhUserMakeCard.getOrgId());
		String userName = yhUserReginfo.getUserName();
		String userPwd = yhUserReginfo.getUserPwd();
		SysPasswordService sysPasswordService = new SysPasswordService();
		sysPasswordService.encryptPassword(userName, userPwd);
		String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
		if (yhUserReginfo.getGender() == 1) {
			yhUserReginfo.setPostCode("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
		}
		if (yhUserReginfo.getGender() == 2) {
			yhUserReginfo.setPostCode("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
		}

		yhUserReginfo.setUserPwd(nameAndPwd);
		int addReg = yhUserReginfoService.insertSelective(yhUserReginfo);
		// 添加注册卡表的userCode
		
		YhUserMakeCard record = new YhUserMakeCard();
		record.setEnrolCardId(yhUserMakeCard.getEnrolCardId());
		record.setUserCode(yhUserReginfo.getUserCode());
		yhUserMakeCardService.updateByPrimaryKeySelective(record);
		// 往用户信息扩展表插入一条数据 userCode Evaluateflag
		YhUserEnrolinfo enrolinfo = yhUserEnrolinfoService.selectByPrimaryKeyByUsercode(yhUserReginfo.getUserCode());
		if (enrolinfo == null) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setUserCode(yhUserReginfo.getUserCode());
			yhUserEnrolinfo.setEvaluateflag(0);
			yhUserEnrolinfoService.insertSelective(yhUserEnrolinfo);
		}
		// 查询角色表
		Integer roleId = yhUserRoleService.selectRoleId("用户");
		// 用户角色关联表插入id和code
		YhUserRolePower yhUserRolePower = new YhUserRolePower();
		yhUserRolePower.setRoleid(roleId);
		yhUserRolePower.setUserid(yhUserReginfo.getUserCode());
		yhUserRolePowerService.inssert(yhUserRolePower);

		JSONArray jsonArray = new JSONArray();
		JSONObject map = new JSONObject();

		JSONObject operationinfo = new JSONObject();
		operationinfo.put("operator", "1");
		map.put("operationinfo", operationinfo);

		JSONObject mainTable = new JSONObject();
		mainTable.put("xm", yhUserReginfo.getName());
		mainTable.put("xb", yhUserReginfo.getGender() - 1);
		mainTable.put("csrq", DateUtils.dateTime(yhUserReginfo.getBirthDate()));
		mainTable.put("brdh", yhUserReginfo.getMobile());
		mainTable.put("sfzh", yhUserReginfo.getCardidentity());
		//查询客户绑定id
		String oaTreeId = yhOrgTreeService.queryOaTreeId2(yhUserReginfo.getEnrolCardId());
		if (StringUtils.isNotEmpty(oaTreeId)) {
			mainTable.put("gzdw", oaTreeId);
		}
		map.put("mainTable", mainTable);
		jsonArray.add(map);
		String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/sysncPersionInfo", jsonArray);
		return Result.ok(addReg);

	}

	@RequestMapping(value = "/updateRegImg")
	@ResponseBody
	public Result updateRegImg(YhUserReginfoApp record) {
		int keySelective = YhUserReginfoService.updateByPrimaryKeySelective(record);
		return Result.ok(keySelective);
		
	}
	
	@RequestMapping(value = "/selectYhJzSysSmsAppBytype")
	@ResponseBody
	public Result selectYhJzSysSmsAppBytype() {
		 List<YhJzSysSmsApp> appBytype = yhJzSysSmsAppService.selectYhJzSysSmsAppBytype();
		return Result.ok(appBytype);
		
	}

	/**
	 * 根据userCode查询密码
	 * 
	 * @param userCode
	 * @param oldPassWord
	 * @return
	 */
	@RequestMapping(value = "/findPwd")
	@ResponseBody
	public Result selectYhUserReginfoPwd(String userCode, String userPwd) {
		YhUserReginfoApp yhUserReginfo = YhUserReginfoService.findYhUserReginfo(userCode);
		String userName = yhUserReginfo.getUserName();
		String md5Pwd = new Md5Hash(userName + userPwd).toHex();
		YhUserReginfoApp yhUserReginfo2 = YhUserReginfoService.selectUserPwd(userCode, md5Pwd);
		return Result.ok(yhUserReginfo2);

	}
	
	/**
	 * 根据userCode查询医生头像
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/finddoctorHderer")
	@ResponseBody
	public Result finddoctorHderer(String userCode) {
		YhUserReginfoApp yhUserReginfo = YhUserReginfoService.finddoctorHderer(userCode);
		return Result.ok(yhUserReginfo);

	}


	/**
	 * 根据userCode修改密码
	 * 
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/updatePwd")
	@ResponseBody
	public Result updatePassword(YhUserReginfoApp yhUserReginfo) {
		String userCode = yhUserReginfo.getUserCode();
		YhUserReginfoApp yhUserReginfo2 = YhUserReginfoService.findYhUserReginfo(userCode);
		String userName = yhUserReginfo2.getUserName();
		String userPwd = yhUserReginfo.getUserPwd();
		String md5Pwd = new Md5Hash(userName + userPwd).toHex();
		yhUserReginfo.setUserPwd(md5Pwd);
		int pwd = YhUserReginfoService.updatePwd(yhUserReginfo);
		return Result.ok(pwd);
	}

	/**
	 * 根据userCode查询个人信息
	 * 
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/findYhUserReginfo")
	@ResponseBody
	public Result selectYhUserReginfo(String userCode) {
		YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(userCode);
		return Result.ok(info);
	}
	public static void main2(String[] args) {
		String oneHoursAgoTime="";
        Date dt = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        //rightNow.add(Calendar.DATE, -1);
        rightNow.add(Calendar.HOUR, -1);
        Date dt1=rightNow.getTime();
        oneHoursAgoTime = sdf.format(dt1);
        System.out.println(oneHoursAgoTime+"时间"+dt1);
	}

	@RequestMapping(value = "/edityhUserReginfo")
	@ResponseBody
	public Result saveYhUserReginfo(YhUserReginfoApp yhUserReginfo, String birthDate1) throws ParseException {
		// 给定模式(这里给定的模式须与给定日期字符串格式匹配)
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		if(birthDate1!=null) {
			Date d2 = sdf2.parse(birthDate1);
			System.out.println(d2);
			yhUserReginfo.setBirthDate(d2);
		}
		
		int row = 0;
		if (yhUserReginfo.getUserCode() == null || "".equals(yhUserReginfo.getUserCode())) {
			row = YhUserReginfoService.insertSelective(yhUserReginfo);
		} else {
			yhUserEnrolinfo.setUserCode(yhUserReginfo.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			YhUserReginfoService.updateByPrimaryKeySelective(yhUserReginfo);
		}
		return Result.ok(row);

	}

	public static void main(String[] args) {
		System.out.println(new Md5Hash("807083" + "123456").toHex());
		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
	}

	/**
	 * 登录api
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Result login(String userName, String userPwd) {
		int findUserNameExist = YhUserReginfoService.findUserNameExist(userName);
		if(findUserNameExist==0){
			return Result.error("账号不存在");
		}


		
		String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
		 
		YhUserReginfoApp selectByExample = YhUserReginfoService.selectByUserNameAndUserPwd(userName, nameAndPwd);

		if (null != selectByExample) {
			int findUserCodeByOrgIsExist = YhUserReginfoService.findUserCodeByOrgIsExist(selectByExample.getUserCode());
			if(findUserCodeByOrgIsExist>0){
				return Result.error("账号已过期");
			}
			Integer userRole = YhUserReginfoService.findUserRole(selectByExample.getUserCode());
			if(null==userRole){
				return Result.error("登录权限错误");
			}
			if(17!=userRole){
				return Result.error("请使用个人端账号登录");
			}
			YhUserReginfoApp record = new YhUserReginfoApp();
			record.setLoginTime(new Date());
			if (null == selectByExample.getLoginTimes()) {
				record.setLoginTimes(1);
			} else {
				record.setLoginTimes(selectByExample.getLoginTimes() + 1);
			}
			record.setUserCode(selectByExample.getUserCode());
			YhUserReginfoService.updateByUsercodeAndLoginTime(record);
			YhWxUser yhWxUser = YhWxUserService.selectHeadImgbyParam(selectByExample.getUserCode());
			selectByExample.setYhWxUser(yhWxUser);
			return Result.ok(selectByExample);
		}
		 
		
		return Result.error("账号密码错误");
	}

	/**
	 * 根据主键查询用户信息
	 */
	
	@RequestMapping(value = "/findUserInfo")
	@ResponseBody
	public Result login(String usercode) {
		if (null == usercode) {
			Result.error();
		}
		 String oneHoursAgoTime="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH");
		YhUserReginfoApp selectByExample = YhUserReginfoService.findYhUserReginfo(usercode);
		 if(null!=selectByExample.getBirthDate()){

			 Calendar rightNow = Calendar.getInstance();
			 rightNow.setTime(selectByExample.getBirthDate());
			 rightNow.add(Calendar.HOUR, +1);
			 Date dt1=rightNow.getTime();
			 oneHoursAgoTime = sdf.format(dt1);
			 selectByExample.setBirthtime(oneHoursAgoTime);
			 System.out.println("时间"+oneHoursAgoTime+"---------"+dt1);

		 }
		int findUserCodeByOrgIsExist = YhUserReginfoService.findUserCodeByOrgIsExist(selectByExample.getUserCode());
		if(findUserCodeByOrgIsExist>0){
			selectByExample.setExpire(true);

		}else{
			selectByExample.setExpire(false);
		}
		return Result.ok(selectByExample);
	}


	/**
	 * 发注册送验证码
	 */
	@RequestMapping(value = "/sendVerificationCode")
	@ResponseBody
	public Result sendVerificationCode(    String shouji) throws ClientException {
		setNewcode();

		String code = Integer.toString(getNewcode());

		YhJzSysSmsApp sms = yhJzSysSmsAppService.selectById(14);

		SendSmsResponse sendSms = sendSms(shouji, code, sms.getAccesskey(), sms.getAccesskeysecret(),
				sms.getTemplateId(), sms.getSignname());// 填写你需要测试的手机号码

		if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
			return Result.ok(code);
		} else   {
			String s = convertCodeToMsg(sendSms);
			return Result.error(s);
		}

	}

	
	
	/**
	 * 获取验证码
	 * 
	 * @throws ClientException
	 */
	@RequestMapping(value = "/changepassword")
	@ResponseBody
	public Result changepassword(HttpSession session, String zhangh, String shouji, String pwd1, String pwd2,
			String yanzhengma,Integer type) throws ClientException {
		if(type != 1) {
			// 查询账号是否存在
			YhUserReginfoApp selectByshoujiAndUsername = YhUserReginfoService.selectByshoujiAndUsername(zhangh, "");
			if (null == selectByshoujiAndUsername) {
				return Result.error("用户名不存在");
			}
			// 查询手机号是否存在
			YhUserReginfoApp selectByshoujiAndUsername2 = YhUserReginfoService.selectByshoujiAndUsername("", shouji);
			if (null == selectByshoujiAndUsername2) {
				return Result.error("手机号不存在");
			}

			// 查询手机号是否与账号匹配
			YhUserReginfoApp selectByshoujiAndUsername3 = YhUserReginfoService.selectByshoujiAndUsername(zhangh, shouji);
			if (null == selectByshoujiAndUsername3) {
				return Result.error("手机号与账号不匹配");
			}
		}
		


		setNewcode();

		String code = Integer.toString(getNewcode());

		YhJzSysSmsApp sms = yhJzSysSmsAppService.selectById(1);

		SendSmsResponse sendSms = sendSms(shouji, code, sms.getAccesskey(), sms.getAccesskeysecret(),
				sms.getTemplateId(), sms.getSignname());// 填写你需要测试的手机号码

		if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
			return Result.ok(code);
		} else   {
			String s = convertCodeToMsg(sendSms);
			return Result.error(s);
		}

	}
	/**
	 * 转换code为错误信息
	 * @param sendSmsResponse
	 * @return
	 * OK  请求成功
	 * isp.RAM_PERMISSION_DENY    RAM权限DENY
	 * isv.OUT_OF_SERVICE  业务停机
	 * isv.PRODUCT_UN_SUBSCRIPT    未开通云通信产品的阿里云客户
	 * isv.PRODUCT_UNSUBSCRIBE 产品未开通
	 * isv.ACCOUNT_NOT_EXISTS  账户不存在
	 * isv.ACCOUNT_ABNORMAL    账户异常
	 * isv.SMS_TEMPLATE_ILLEGAL    短信模板不合法
	 * isv.SMS_SIGNATURE_ILLEGAL   短信签名不合法
	 * isv.INVALID_PARAMETERS  参数异常
	 * isp.SYSTEM_ERROR    系统错误
	 * isv.MOBILE_NUMBER_ILLEGAL   非法手机号
	 * isv.MOBILE_COUNT_OVER_LIMIT 手机号码数量超过限制
	 * isv.TEMPLATE_MISSING_PARAMETERS 模板缺少变量
	 * isv.BUSINESS_LIMIT_CONTROL  业务限流
	 * isv.INVALID_JSON_PARAM  JSON参数不合法，只接受字符串值
	 * isv.BLACK_KEY_CONTROL_LIMIT 黑名单管控
	 * isv.PARAM_LENGTH_LIMIT  参数超出长度限制
	 * isv.PARAM_NOT_SUPPORT_URL   不支持URL
	 * isv.AMOUNT_NOT_ENOUGH   账户余额不足findFoodDiaryinfoNewTime
	 */
	public static String convertCodeToMsg(SendSmsResponse sendSmsResponse) {
		if ("error".equals(sendSmsResponse.getCode())) {
			return "调用报错";
		}else if ("isp.RAM_PERMISSION_DENY".equals(sendSmsResponse.getCode())) {
			return "RAM权限DENY";
		}else if ("isv.OUT_OF_SERVICE".equals(sendSmsResponse.getCode())) {
			return "业务停机";
		}else if ("isv.PRODUCT_UN_SUBSCRIPT".equals(sendSmsResponse.getCode())) {
			return "未开通云通信产品的阿里云客户";
		}else if ("isv.PRODUCT_UNSUBSCRIBE".equals(sendSmsResponse.getCode())) {
			return "产品未开通";
		}else if ("isv.ACCOUNT_NOT_EXISTS".equals(sendSmsResponse.getCode())) {
			return "账户不存在";
		}else if ("isv.ACCOUNT_ABNORMAL".equals(sendSmsResponse.getCode())) {
			return "账户异常";
		}else if ("isp.SMS_TEMPLATE_ILLEGAL".equals(sendSmsResponse.getCode())) {
			return "短信模板不合法";
		}else if ("isp.SMS_SIGNATURE_ILLEGAL".equals(sendSmsResponse.getCode())) {
			return "短信签名不合法";
		}else if ("isp.INVALID_PARAMETERS".equals(sendSmsResponse.getCode())) {
			return "参数异常";
		}else if ("isp.SYSTEM_ERROR".equals(sendSmsResponse.getCode())) {
			return "系统错误";
		}else if ("isp.MOBILE_NUMBER_ILLEGAL".equals(sendSmsResponse.getCode())) {
			return "非法手机号";
		}else if ("isp.MOBILE_COUNT_OVER_LIMIT".equals(sendSmsResponse.getCode())) {
			return "手机号码数量超过限制";
		}else if ("isp.TEMPLATE_MISSING_PARAMETERS".equals(sendSmsResponse.getCode())) {
			return "模板缺少变量";
		}else if ("isp.BUSINESS_LIMIT_CONTROL".equals(sendSmsResponse.getCode())) {
			return "业务限流";
		}else if ("isp.INVALID_JSON_PARAM".equals(sendSmsResponse.getCode())) {
			return "JSON参数不合法，只接受字符串值";
		}else if ("isp.BLACK_KEY_CONTROL_LIMIT".equals(sendSmsResponse.getCode())) {
			return "黑名单管控";
		}else if ("isp.PARAM_LENGTH_LIMIT".equals(sendSmsResponse.getCode())) {
			return "参数超出长度限制";
		}else if ("isp.PARAM_NOT_SUPPORT_URL".equals(sendSmsResponse.getCode())) {
			return "不支持URL";
		}else if ("isp.AMOUNT_NOT_ENOUGH".equals(sendSmsResponse.getCode())) {
			return "账户余额不足";
		}
		return "未知错误";
	}

	/**
	 * 修改用户密码
	 */
	@RequestMapping(value = "/updUserInfo")
	@ResponseBody
	public Result updUserInfo(HttpSession session, String zhangh, String shouji, String pwd1, String pwd2,
			String yanzhengma, String code) {
		// 查询账号是否存在
		YhUserReginfoApp selectByshoujiAndUsername = YhUserReginfoService.selectByshoujiAndUsername(zhangh, "");
		if (null == selectByshoujiAndUsername) {
			return Result.error("用户名不存在");
		}
		// 查询手机号是否存在
		YhUserReginfoApp selectByshoujiAndUsername2 = YhUserReginfoService.selectByshoujiAndUsername("", shouji);
		if (null == selectByshoujiAndUsername2) {
			return Result.error("手机号不存在");
		}

		// 查询手机号是否与账号匹配
		YhUserReginfoApp selectByshoujiAndUsername3 = YhUserReginfoService.selectByshoujiAndUsername(zhangh, shouji);
		if (null == selectByshoujiAndUsername3) {
			return Result.error("手机号与账号不匹配");
		}

		String userPwd = selectByshoujiAndUsername3.getUserPwd();
		if (pwd1.equals(userPwd)) {
			return Result.error("新密码不能与之前一致");
		}
		if (null == code) {
			return Result.error("code is NULL");
		}
		if (!code.equals(yanzhengma)) {
			return Result.error("验证码错误");
		}
		String nameAndPwd = new Md5Hash(zhangh + pwd1).toHex();
		// 修改用户密码
		int updateByUsernameAndMobile = YhUserReginfoService.updateByUsernameAndMobile(zhangh, nameAndPwd, shouji);

		return Result.ok(updateByUsernameAndMobile);
	}

	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	/**
	 * 
	 * @param telephone
	 *            手机号码
	 * @param code
	 *            信息例如：验证码为：${code}，您正在注册成为平台会员，感谢您的支持！
	 * @param accessKeyId
	 *            此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	 * @param accessKeySecret
	 *            此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	 * @param TemplateCode
	 *            必填:短信模板-可在短信控制台中找到
	 * @param SignName
	 *            必填:短信签名-可在短信控制台中找到
	 * @return
	 * @throws ClientException
	 */
	public static SendSmsResponse sendSms(String telephone, String code, String accessKeyId, String accessKeySecret,
			String TemplateCode, String SignName) throws ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(telephone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(SignName); // TODO 改这里
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(TemplateCode); // TODO 改这里
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"code\":\"" + code + "\"}");

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			System.out.println("短信发送成功！");
		} else {

			System.out.println("短信发送失败！");
		}
		return sendSmsResponse;
	}

	/**
	 * 查询年龄
	 */
	@RequestMapping(value = "/getAge")
	@ResponseBody
	public Result getAge(String userCode) {
		return Result.ok(YhUserReginfoService.findUserInfo(userCode));
	}
}