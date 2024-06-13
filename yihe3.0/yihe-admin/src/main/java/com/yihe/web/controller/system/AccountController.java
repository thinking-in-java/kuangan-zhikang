/**
 * 
 */
package com.yihe.web.controller.system;

import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhUserAdminInfo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRolePower;
import com.yihe.system.mapper.YhUserAdminInfoMapper;
import com.yihe.system.mapper.YhUserMakeCardMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserRolePowerService;
import com.yihe.system.service.YhUserRoleService;
import com.yihe.system.service.YhUserSettingService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号管理
 * @author dongs
 *
 */
@Controller
@RequestMapping("/AccountController")
public class AccountController {
	
	@Autowired
	YhUserReginfoMapper yhUserReginfoMapper;
	
	@Autowired
	YhUserSettingService yhusersettingservice;
	
	@Autowired
   	YhOrgTreeService yhOrgTreeService;
	
	@Autowired
	YhUserMakeCardMapper yhUserMakeCardMapper;
	
	@Autowired
	YhUserRoleService  yhUserRoleService;
	
	@Autowired
	YhUserRolePowerService yhUserRolePowerService;
	
	@Autowired
	YhUserAdminInfoMapper yhUserAdminInfoMapper;
	
	
	@ApiOperation(value = "查询用户名和手机号")
	@RequestMapping(value = "/selectMobileAndName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectMobileAndName(String userName, String mobile,String userCode) {
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("mobile", mobile);
		map1.put("userCode", userCode);
		System.out.println(userName+"--"+mobile);
		Map<String, Object> map = new HashMap<String, Object>();
		
		int yhUserReginfo2 = yhUserReginfoMapper.showMobileFalse(map1);
		if (yhUserReginfo2 == 1) {
			map.put("code", 100);
			map.put("msg", "手机号码已被其他用户使用");
			return map;
		}

		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("userName", userName);
		map2.put("userCode", userCode);
		int yhUserReginfo = yhUserReginfoMapper.showuserNameFalse(map2);
		if (yhUserReginfo==1) {
			map.put("code", 100);
			map.put("msg", "登录账号已存在，不能重名创建");
			return map;
		}
		map.put("code", 200);
		return map;

	}
	
	
	@ApiOperation(value = "查询用户手机号")
	@RequestMapping(value = "/selectMobile", method = RequestMethod.GET)
	@ResponseBody
	public int selectMobile( String mobile,String userCode) {
		System.out.println("--"+mobile);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("userCode", userCode);
		int show = yhUserReginfoMapper.showMobileFalse(map);
		return show;
	}
	
	@ApiOperation(value = "查询用户名")
	@RequestMapping(value = "/selectName", method = RequestMethod.GET)
	@ResponseBody
	public int selectName( String userName,String userCode) {
		System.out.println("--"+userName);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("userCode", userCode);
		int yhUserReginfo = yhUserReginfoMapper.showuserNameFalse(map);
		return yhUserReginfo;
	}
	
	
	public static void main(String[] args) {
		YhUserReginfo yhUserReginfo=new YhUserReginfo();
		String userCode="100058";
		
		 System.out.println(userCode);
	}
	
	@ApiOperation(value = "注册提交")
	@RequestMapping(value = "/regSubmit", method = RequestMethod.POST)
	@ResponseBody
	public int addYhUserReginfo(YhUserReginfo yhUserReginfo) throws ParseException {
		Date date=new Date();
		if(yhUserReginfo.getBirthtime()==null) {
			yhUserReginfo.setBirthDate(date);
		}else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateStr = simpleDateFormat.parse(yhUserReginfo.getBirthtime());
			yhUserReginfo.setBirthDate(dateStr);
		}
		int addReg=0;
		String userCode="";
		if(yhUserReginfo.getUserCode()==null || yhUserReginfo.getUserCode().equals("") || yhUserReginfo.getUserCode().equals("0")) {
			if(yhUserReginfo.getUserType()==4) {
			userCode = yhUserReginfoMapper.selectMaxUserCodeDa(yhUserReginfo.getUserType());  
			 yhUserReginfo.newUserCode(userCode);	
			 String intUserCode = "1";
			 if (!userCode.equals("0"))
			       intUserCode = Integer.toString(Integer.parseInt(userCode.substring(2)) + 1);
			 userCode=yhUserReginfo.getDrCode(intUserCode);
			}else if(yhUserReginfo.getUserType()==5){
			userCode = yhUserReginfoMapper.selectMaxUserCode(yhUserReginfo.getUserType());  
				
	  		String userCode1 = String.valueOf(Integer.valueOf(userCode) + 1);
	  		yhUserReginfo.setUserCode(userCode1);
	  		
	  	 }
			yhUserReginfo.setRegTime(date);
		  	String userName = yhUserReginfo.getUserName()+"医生";
			String userPwd = yhUserReginfo.getUserPwd();
			SysPasswordService sysPasswordService = new SysPasswordService();
			sysPasswordService.encryptPassword(userName, userPwd);
			String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
			yhUserReginfo.setUserCode(userCode);
			yhUserReginfo.setUserPwd(nameAndPwd);
			yhUserReginfo.setUserName(userName);
			addReg = yhUserReginfoMapper.insertSelective(yhUserReginfo);
			Integer roleId = 0;
			if(yhUserReginfo.getModules()==null || yhUserReginfo.getModules().equals("")) {
				//查询角色表
				roleId = yhUserRoleService.selectRoleId("医师端");
			}else {
				roleId=Integer.valueOf(yhUserReginfo.getModules());
			}
			
			//用户角色关联表插入id和code
			YhUserRolePower yhUserRolePower = new YhUserRolePower();
			yhUserRolePower.setRoleid(roleId);
			yhUserRolePower.setUserid(userCode);
			yhUserRolePowerService.inssert(yhUserRolePower);
			
			//管理人员信息表
			YhUserAdminInfo admin=new YhUserAdminInfo();
			admin.setUserName(yhUserReginfo.getName());
			admin.setOrgCode(yhUserReginfo.getOrgCode());
			admin.setUserCode(userCode);
			admin.setGender(1);
			admin.setUserType(3);
			admin.setMobile(yhUserReginfo.getMobile());
			admin.setRegtime(date);
			yhUserAdminInfoMapper.insertSelective(admin);
		}else {
			
			YhUserRolePower power = yhUserRolePowerService.selectByPowerId(yhUserReginfo.getUserCode());
			if(power.getRoleid()!=Integer.valueOf(yhUserReginfo.getModules())) {
				power.setRoleid(Integer.valueOf(yhUserReginfo.getModules()));
				yhUserRolePowerService.updateByPrimaryKeySelective(power);
			}
			
			
			//管理人员信息表
			YhUserAdminInfo admin=new YhUserAdminInfo();
			admin.setUserName(yhUserReginfo.getName());
			admin.setOrgCode(yhUserReginfo.getOrgCode());
			admin.setUserCode(yhUserReginfo.getUserCode());
			admin.setMobile(yhUserReginfo.getMobile());
			admin.setRegtime(date);
			yhUserAdminInfoMapper.updateByPrimaryKeySelectiveUser(admin);
			
			addReg=yhUserReginfoMapper.updateByPrimaryKeySelective(yhUserReginfo);
		}
	  
		
		return addReg;
  	

}
	
	
	
	
	@ApiOperation(value = "查询当前机构下的医生")
    @RequestMapping(value = "/showOrgCodedoctor", method = RequestMethod.POST)
	@ResponseBody
	public Result showOrgCodedoctor(){
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		String orgCode = yh.getOrgCode();//取到当前用户orgcode
		List<YhUserReginfo> list= yhUserReginfoMapper.showOrgCodedoctor(orgCode);
		return Result.ok(list);
	}
	
	
	@ApiOperation(value = "修改用户锁定")
    @RequestMapping(value = "/amendlock", method = RequestMethod.POST)
	@ResponseBody
	public Result amendlock(String userCode,int lockFlag){
		YhUserReginfo info=new YhUserReginfo();
		info.setUserCode(userCode);
		info.setLockFlag(lockFlag);
		return Result.ok(yhUserReginfoMapper.updateByPrimaryKeySelective(info));
	}
	
	/**
	 *根据机构查询医生账号
	 * @param page
	 * @param limit
	 * @return
	 */
     @ApiOperation(value = "根据机构查询医生账号")
     @RequestMapping(value = "/showDoctor", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> showDoctor(Integer page,Integer limit,String mobile,String name,String lockFlag,String userName,String orgCode,Integer roleid){
    	 if(orgCode==null || orgCode=="") {
    		 Subject subject = SecurityUtils.getSubject();
    			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
    			orgCode = yh.getOrgCode();//取到当前用户orgcode
    	 }
    	
			
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("orgCode", orgCode);
 		map1.put("name", name);
 		map1.put("lockFlag", lockFlag);
 		map1.put("mobile", mobile);
 		map1.put("userName", userName);
		map1.put("roleid", roleid);
 		List<YhUserReginfo> list = yhUserReginfoMapper.showDoctor(map1);
 		for (YhUserReginfo yhUserReginfo : list) {
 			String orgName = "";	
        	if(yhUserReginfo.getOrgCode() != null) {
        		orgName = yhOrgTreeService.selectFullOrgName(yhUserReginfo.getOrgCode().toString(),orgName);
    			}
        	yhUserReginfo.setOrgname(orgName);
        	int doctorcard=yhUserMakeCardMapper.doctorcard(yhUserReginfo.getUserCode());//查询已用多少注册卡
        	int doctorcardCount=yhUserMakeCardMapper.doctorcardCount(yhUserReginfo.getUserCode());//总共多少注册卡
        	yhUserReginfo.setEnrolCardId(doctorcard+"/"+doctorcardCount);
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhUserReginfoMapper.showDoctorCount(map1));
 		map.put("data", list);
 		return map;
 	}
     
     /**
 	 *根据机构查询管理员账号
 	 * @param page
 	 * @param limit
 	 * @return
 	 */
      @ApiOperation(value = "根据机构查询管理员账号")
      @RequestMapping(value = "/showSport", method = RequestMethod.POST)
  	@ResponseBody
  	public Map<String,Object> showSport(Integer page,Integer limit,String mobile,String lockFlag,String userName,String orgCode,Integer roleid){
     	 if(orgCode==null || orgCode=="") {
     		 Subject subject = SecurityUtils.getSubject();
     			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
     			orgCode = yh.getOrgCode();//取到当前用户orgcode
     	 }
     	
 			
  		Map<String,Object> map1 = new HashMap<String,Object>();
  		Pages pa = new Pages();
  		pa.setCurPage(page);
  		pa.setMaxResult(limit);
  		map1.put("page", pa.getFirstRows());
  		map1.put("limit", pa.getMaxResult());
  		map1.put("orgCode", orgCode);
  		map1.put("lockFlag", lockFlag);
  		map1.put("mobile", mobile);
		map1.put("roleid",roleid);
  		map1.put("userName", userName);
  		List<YhUserReginfo> list = yhUserReginfoMapper.showAdministrator(map1);
  		for (YhUserReginfo yhUserReginfo : list) {
  			String orgName = "";	
         	if(yhUserReginfo.getOrgCode() != null) {
         		orgName = yhOrgTreeService.selectFullOrgName(yhUserReginfo.getOrgCode().toString(),orgName);
     			}
         	yhUserReginfo.setOrgname(orgName);
 		}
  		Map<String,Object> map = new HashMap<String,Object>();
  		map.put("code", 20000);
  		map.put("msg", "");
  		map.put("count",yhUserReginfoMapper.showAdministratorCount(map1));
  		map.put("data", list);
  		return map;
  	}
      
      
      /**
   	 *根据机构查询用户账号
   	 * @param page
   	 * @param limit
   	 * @return
   	 */
        @ApiOperation(value = "根据机构查询用户账号")
        @RequestMapping(value = "/showUser", method = RequestMethod.POST)
    	@ResponseBody
    	public Map<String,Object> showUser(Integer page,Integer limit,String mobile,String name,String lockFlag,String userName,String orgCode,String doctors){
       	 if(orgCode==null || orgCode=="") {
       		 Subject subject = SecurityUtils.getSubject();
       			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
       			orgCode = yh.getOrgCode();//取到当前用户orgcode
       	 }
       	
   			
    		Map<String,Object> map1 = new HashMap<String,Object>();
    		Pages pa = new Pages();
    		pa.setCurPage(page);
    		pa.setMaxResult(limit);
    		map1.put("page", pa.getFirstRows());
    		map1.put("limit", pa.getMaxResult());
    		map1.put("orgCode", orgCode);
    		map1.put("lockFlag", lockFlag);
    		map1.put("name", name);
    		map1.put("mobile", mobile);
    		map1.put("userName", userName);
    		map1.put("doctor", doctors);
    		List<YhUserReginfo> list = yhUserReginfoMapper.showUser(map1);
    		for (YhUserReginfo yhUserReginfo : list) {
    			String orgName = "";	
           	if(yhUserReginfo.getOrgCode() != null) {
           		orgName = yhOrgTreeService.selectFullOrgName(yhUserReginfo.getOrgCode().toString(),orgName);
       			}
           	yhUserReginfo.setOrgname(orgName);
           	if(yhUserReginfo.getTreedate()==null){
				yhUserReginfo.setTreedate("--");
			}
           	
           	YhOrgTree tree=yhOrgTreeService.selectByPrimaryKey(yhUserReginfo.getUserCode());
           	if(tree!=null && tree.getAddDate()!=null) {
           	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           	 String dateString = formatter.format(tree.getAddDate());
           	//yhUserReginfo.setTreedate(dateString);
           	}else {
           		//yhUserReginfo.setTreedate("--");
           	}
   		}
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("code", 20000);
    		map.put("msg", "");
    		map.put("count",yhUserReginfoMapper.showUserCount(map1));
    		map.put("data", list);
    		return map;
    	}
   

}
