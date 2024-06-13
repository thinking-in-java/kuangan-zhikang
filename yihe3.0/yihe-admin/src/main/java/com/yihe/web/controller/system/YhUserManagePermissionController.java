package com.yihe.web.controller.system;

import java.util.Calendar;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserPermissionService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/***
 * 用户权限管理
 * @author Royiwen
 *
 */
@Controller
@RequestMapping("/YhUserManagePermission")
@Api(value = "用户权限管理", description = "用户权限管理")
public class YhUserManagePermissionController {

	@Autowired
    private YhUserApiService yhUserApiService;
	@Autowired
	private YhUserPermissionService yhuserpermissionservice;
	

	
	
	@ApiOperation(value = "权限查找")
    @RequestMapping(value = "/selectpermission", method = RequestMethod.GET)
	@ResponseBody
    public Result permissionindex() {
		Subject subject = SecurityUtils.getSubject();
	
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		List<YhUserPermission> upmsPermissions = yhUserApiService.selectUserPermissionByUpmsUserId(yh.getUserCode());
        return Result.ok(upmsPermissions);
    }
	
	
	@ApiOperation(value = "权限添加")
    @RequestMapping(value = "/addtpermission")
	@ResponseBody
	public Result addpermission(YhUserPermission yhuserpermission) {
		int result=0;
		yhuserpermission.setSystemId(0);
		yhuserpermission.setStatus((byte) 1);
		yhuserpermission.setCtime(Calendar.getInstance().getTimeInMillis());
		result=yhuserpermissionservice.insert(yhuserpermission);
		if(result!=0) {
			return Result.ok(200);
		}
		return Result.error();
	}
	
	@ApiOperation(value = "权限添加")
    @RequestMapping(value = "/updatepermission")
	@ResponseBody
	public Result updatepermission(YhUserPermission yhuserpermission) {
		yhuserpermission.setSystemId(0);
		yhuserpermission.setStatus((byte) 1);
		yhuserpermission.setCtime(Calendar.getInstance().getTimeInMillis());
		int result=yhuserpermissionservice.updateByPrimaryKey(yhuserpermission);
		if(result!=0) {
			return Result.ok(200);
		}
		return Result.error();
	}
	
	
	@ApiOperation(value = "权限删除")
    @RequestMapping(value = "/deletepermission")
	@ResponseBody
	public Result deletepermission(Integer permissionId) {
		int result=0;
		//删除角色权限
		yhuserpermissionservice.deleteuserrolepermissionbypid(permissionId);
		//删除权限
		result=yhuserpermissionservice.deleteByPrimaryKey(permissionId);
		if(result!=0) {
			return Result.ok(200);
		}
		return Result.error();
	}
}
