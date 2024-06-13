package com.yihe.web.controller.system;

import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.service.YhRoleManageService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/YhUserManage")
@Api(value = "角色管理", description = "角色管理")
public class YhRoleManageController {
	@Autowired
	YhRoleManageService yhrolemanageservice;
	@Autowired
	private com.yihe.system.service.YhUserAdminInfoService YhUserAdminInfoService;


	@ApiOperation(value = "查找所有管理员角色")
	@RequestMapping(value = "/selectallrole", method = RequestMethod.GET)
	@ResponseBody
	public Result selectallrole(Integer roletype) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		return Result.ok(yhrolemanageservice.selectrolebycode(roletype,usercode));
	}
	
	
	@ApiOperation(value = "查找所有管理员角色")
	@RequestMapping(value = "/selectallrole1", method = RequestMethod.GET)
	@ResponseBody
	public Result selectallrole1(Integer roletype) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		return Result.ok(yhrolemanageservice.selectrolebycode1(roletype,usercode));
	}

	@ApiOperation(value = "查找所有管理员创建的医师角色")
	@RequestMapping(value = "/selecdoctorrole", method = RequestMethod.GET)
	@ResponseBody
	public Result selecdoctorrole(Integer roletype) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		return Result.ok(yhrolemanageservice.selecdoctorrole(roletype,usercode));
	}

	@ApiOperation(value = "角色添加")
	@RequestMapping(value = "/addallrole", method = RequestMethod.GET)
	@ResponseBody
	public Result addallrole(YhUserRole yhuerrole) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Date dateTime = null;
		try {
			dateTime = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int count=yhrolemanageservice.selectrolenamecount(yhuerrole);
		if(count!=0){
			return 	 Result.error(500,"角色名已被占用");
		}
		yhuerrole.setRoleCtime(dateTime);
		yhuerrole.setOrders(Long.valueOf(yhrolemanageservice.maxorder()) );
		if(yhuerrole.getRoletype()==1 ||yhuerrole.getRoletype()==2){
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			yhuerrole.setPid(yhrolemanageservice.selectpidbyusercode(principal.getUserCode()));
		}
		return Result.ok(yhrolemanageservice.insert(yhuerrole));
	}

	@ApiOperation(value = "角色修改")
	@RequestMapping(value = "/updateallrole", method = RequestMethod.GET)
	@ResponseBody
	public Result updateallrole(YhUserRole yhuerrole) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Date dateTime = null;
		try {
			dateTime = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		yhuerrole.setRoleCtime(dateTime);
		int count=yhrolemanageservice.selectrolenamecount(yhuerrole);
		if(count!=0){
			return 	 Result.error(500,"角色名已被占用");
		}
		return Result.ok(yhrolemanageservice.updateByPrimaryKey(yhuerrole));
	}

	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "/deleterole", method = RequestMethod.GET)
	@ResponseBody
	public Result deleterole(YhUserRole yhuerrole) {
		// 先查询是否有用户是该角色，若没有则删除角色
		int result = yhrolemanageservice.selectuserrolepowerbyroleid(yhuerrole.getRoleId());
		System.out.println(result);
		if (result != 0) {
			return Result.error(400, "有用户是该角色！删除失败");
		} else {
			yhrolemanageservice.deleterolepermission(yhuerrole.getRoleId());
			return Result.ok(yhrolemanageservice.deleteByPrimaryKey(yhuerrole.getRoleId()));
		}

	}

	@ApiOperation(value = "查询角色权限")
	@RequestMapping(value = "/selectrolepower", method = RequestMethod.GET)
	@ResponseBody
	public Result selectrolepower(YhUserRole yhuerrole) {
		//yhrolemanageservice.yhuserpermission(yhuerrole.getRoleId());
		return Result.ok(yhrolemanageservice.yhuserpermission(yhuerrole.getRoleId()));
	}

	@ApiOperation(value = "查询医生角色权限")
	@RequestMapping(value = "/selectdoctorrolepower", method = RequestMethod.GET)
	@ResponseBody
	public Result selectdoctorrolepower(YhUserRole yhuerrole) {
		//yhrolemanageservice.yhuserpermission(yhuerrole.getRoleId());
		return Result.ok(yhrolemanageservice.selectdoctorrolepower(yhuerrole.getRoleId()));
	}

	@ApiOperation(value = "更改角色权限")
	@RequestMapping(value = "/changerolepowerbyroleid", method = RequestMethod.GET)
	@ResponseBody
	public Result changerolepowerbyroleid(@RequestParam(name = "authIds", required = false) String authIds,
			@RequestParam Integer roleId) {
		yhrolemanageservice.changerolepowerbyroleid(authIds, roleId);
		return Result.ok(200);
	}


	@ApiOperation(value = "查询该角色可以分配什么机构")
	@RequestMapping(value = "/changerolepowerbyroleids", method = RequestMethod.POST)
	@ResponseBody
	public Result selectmanagerbyrole(Integer roleid) {
		List<YhUserRole> role=YhUserAdminInfoService.selectRole(roleid);
		return Result.ok(role);
	}

	@ApiOperation(value = "为该角色分配机构")
	@RequestMapping(value = "/changemanager", method = RequestMethod.POST)
	@ResponseBody
	public Result changemanager(Integer roleid,Integer[] arry) {

		YhUserAdminInfoService.changemanager(roleid,arry);
		return Result.ok(1);
	}

	@ApiOperation(value = "更改用户角色")
	@RequestMapping(value = "/changeusermanager", method = RequestMethod.POST)
	@ResponseBody
	public Result changeusermanager(Integer roleid,String[] userCodes) {

		YhUserAdminInfoService.changeusermanager(roleid,userCodes);
		return Result.ok(1);
	}
	
}
