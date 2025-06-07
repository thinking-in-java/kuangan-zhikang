package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.yihe.common.config.Global;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserPageSettingService;
import com.yihe.web.controller.tool.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年3月27日 上午9:45:48 
 *@version 1.0 
 */
@Controller
public class SysIndexController extends BaseController{
	
	/* // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
    	YhUserReginfo user = ShiroUtils.getSysUser();
        m
        return "index";
    }*/

	@Autowired
    private YhUserApiService yhUserApiService;
	@Autowired
	private YhUserPageSettingService yhUserPageSettingService;
	@Autowired
	private RedisUtils redisUtils;

    @GetMapping("/register")
    public String register(ModelMap modelMap)
    {
		String getimg = null;
		String orgCode = redisUtils.get("ORG_CODE");
		if(StringUtils.isNotEmpty(orgCode)){
			getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
		}
		String topimg, leftimg, name;
		if(StringUtils.isNotEmpty(getimg)){
			JSONObject jsonObject = JSONObject.parseObject(getimg);
			topimg = jsonObject.getString("21");
			leftimg = jsonObject.getString("20");
			name = jsonObject.getString("name1");
		}else{
			topimg = yhUserPageSettingService.getimg(21);
			leftimg = yhUserPageSettingService.getimg(20);
			name = "谷宜健康";
		}
		modelMap.put("topimg", topimg);
		modelMap.put("leftimg", leftimg);
		modelMap.put("name", name);
            // 当前登录用户权限
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
			if(yh==null) {
				return "sso/login";
			}
			if(yh.getLockFlag()==1){
				modelMap.put("prompt", "您的账号已被锁定");
				return "sso/login";
			}
			List<YhUserPermission> upmsPermissions = yhUserApiService.selectUserPermissionByUpmsUserId(yh.getUserCode());
			List<YhUserRole> yhuserrole=yhUserApiService.yhuserrole(yh.getUserCode());
			upmsPermissions=buildPermissionTree(upmsPermissions);
			//循环权限找出父节点
//	        for(int i=0;i<upmsPermissions.size();i++){
//	            if(upmsPermissions.get(i).getPid()==-1) {
//	            	//如果父节点有子节点标记为true
//	            	for(int j=0;j<upmsPermissions.size();j++) {
//	            		if(upmsPermissions.get(i).getPermissionId().intValue() == upmsPermissions.get(j).getPid().intValue() && upmsPermissions.get(j).getType().intValue()!=3) {
//	            			upmsPermissions.get(i).setHavechild(true);
//	            			break;
//	            		}
//	            	}
//	            }
//	        }

			if(upmsPermissions.size()==0) {
				modelMap.put("prompt", "您没有权限登录");
				return "sso/login";
			}
		
			
			if(upmsPermissions.size()>0){
				modelMap.put("isuri", upmsPermissions.get(0).getUri());
			}else{
				modelMap.put("isuri","/job/task");
			}
			modelMap.put("upmsPermissions", upmsPermissions);
			modelMap.put("yhuserrole", yhuserrole);
			modelMap.put("yh", yh);
		modelMap.put("globalName", Global.getName());
		modelMap.put("doctor", yh.getUserCode());

        return "index";
    }

	public List<YhUserPermission> buildPermissionTree(List<YhUserPermission> upmsPermissions) {
		List<YhUserPermission> rootPermissions = new ArrayList<>();

		// 找出所有根节点 (pid == -1)
		for (YhUserPermission permission : upmsPermissions) {
			if (permission.getPid() == -1) {
				rootPermissions.add(permission);
			}
		}

		// 为每个根节点递归找到其子节点
		for (YhUserPermission rootPermission : rootPermissions) {
			populateChildren(rootPermission, upmsPermissions);
		}

		return rootPermissions;
	}

	private void populateChildren(YhUserPermission parentPermission, List<YhUserPermission> allPermissions) {
		// 找到 parentPermission 的直接子节点
		List<YhUserPermission> children = allPermissions.stream()
				.filter(permission -> permission.getPid().intValue() == parentPermission.getPermissionId().intValue() && permission.getType().intValue() != 3)
				.collect(Collectors.toList());

		// 设置子节点到父节点的 children 列表中
		parentPermission.setChildren(children);

		// 递归查找每个子节点的子节点
		for (YhUserPermission childPermission : children) {
			populateChildren(childPermission, allPermissions);
		}

		// 如果父节点有子节点，标记为 true
		if (!children.isEmpty()) {
			parentPermission.setHavechild(true);
		}
	}
    
    //系统介绍
    @GetMapping("/console")
    public String main(ModelMap mmap)
    {
        return "console/console";
    }
}
