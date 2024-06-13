package com.yihe.web.controller.system;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.service.YhUserApiService;

/**
 * 后台controller
 * Created  by lll.
 */
@Controller
@RequestMapping("/manage")
public class ManageController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManageController.class);


	@Autowired
    private YhUserApiService yhUserApiService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
			// 当前登录用户权限
			Subject subject = SecurityUtils.getSubject();
			String username = (String) subject.getPrincipal();
			YhUserReginfo upmsUser = yhUserApiService.selectUpmsUserByUsername(username);
			List<YhUserPermission> upmsPermissions = yhUserApiService.selectUserPermissionByUpmsUserId(upmsUser.getUserCode());
			List<YhUserRole> yhuserrole=yhUserApiService.yhuserrole(upmsUser.getUserCode());
			//循环权限找出父节点
	        for(int i=0;i<upmsPermissions.size();i++){
	            if(upmsPermissions.get(i).getPid()==-1) {
	            	//如果父节点有子节点标记为true
	            	for(int j=0;j<upmsPermissions.size();j++) {
	            		if(upmsPermissions.get(i).getPermissionId()==upmsPermissions.get(j).getPid()) {
	            			upmsPermissions.get(i).setHavechild(true);
	            		}
	            	}
	            }
	        }
			modelMap.put("upmsPermissions", upmsPermissions);
			modelMap.put("yhuserrole", yhuserrole);
			
			return "/manage/newindex.jsp";
	}

}