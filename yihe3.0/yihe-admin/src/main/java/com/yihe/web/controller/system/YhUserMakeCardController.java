package com.yihe.web.controller.system;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhOrgTreeExample;
import com.yihe.system.domain.YhOrgTreeVojo;
import com.yihe.system.domain.YhUserAdminInfo;
import com.yihe.system.domain.YhUserAdminInfoExample;
import com.yihe.system.domain.YhUserAdminInfoExample.Criteria;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.doctorConsoleVOJO;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserAdminInfoService;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.YhUserSettingService;
import com.yihe.system.utils.FileUtils;
import com.yihe.system.utils.MenuTreeVojo;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * YhUserMakeCardcontroller
 * Created by dyb on 2020/3/19.
 */

@Controller
@RequestMapping("/YhUserMakeCard")
@Api(value = "YhUserMakeCard控制器", description = "YhUserMakeCard管理")
public class YhUserMakeCardController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserMakeCardController.class);
    
    
   	@Autowired
   	YhUserMakeCardService yhUserMakeCardService;
   	@Autowired
   	YhOrgTreeService yhOrgTreeService;
   	@Autowired
     private YhOrgTreeService YhOrgTreeService;
   	@Autowired
   	private YhUserAdminInfoService YhUserAdminInfoService; 
   	@Autowired
   	private YhUserApiService YhUserApiService;
   	 @Autowired
   	 YhUserSettingService yhusersettingservice;
	
   	 /**
   	  * @param page
   	  * @param limit
   	  * @param params
   	  * @return
   	  */
    @ApiOperation(value = "分页查询注册管理")
    @GetMapping(value = "/page")
	@ResponseBody
	public Map<String, Object> queryYhUserMakeCardByCriteriaWithPage(@RequestParam Map<String, Object> params,
			@RequestParam(value = "page",required = true) int page,
			@RequestParam(value = "limit",required = true) int limit) {
    	
    	if(params.get("orgCode")!=null && StringUtil.isNotEmpty(params.get("orgCode").toString())) {
    		List<YhOrgTree> list = yhOrgTreeService.selectBYLikeCode(params.get("orgCode").toString());
    		//取出list中所有的orgId，转成新的list
    		List<String> orgCodes = list.stream().map(YhOrgTree::getOrgCode).collect(Collectors.toList());
    		orgCodes.add(params.get("orgCode").toString());
    		params.put("orgCodes", orgCodes);
 		}
    	
		PageInfo<Map<String, Object>> data = yhUserMakeCardService.queryYhUserMakeCardByCriteriaWithPage(params,page,limit);
		for (Map<String, Object> item : data.getList()) {
			if(item.get("Org_Code")!= null) {
				item.put("Org_Name", yhOrgTreeService.selectFullOrgName(item.get("Org_Code").toString(),new String()));
			}
		}
		
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("data", data.getList());
		result.put("count", data.getTotal());
		result.put("msg", "");
		result.put("code",20000);
		return result;
	}
   
	
	
	@ApiOperation(value = "机构下拉框树结构查询")
    @RequestMapping(value = "/showPullDownTree", method = RequestMethod.GET)
	@ResponseBody
    public List<YhOrgTreeVojo> showpulldown() {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo username = (YhUserReginfo) subject.getPrincipal();
		  
		String selectChildnodeMax = YhOrgTreeService.selectChildnodeMax("0");
		 
		String userCode = username.getUserCode();
		if(null==userCode) {
			return null;
		}
		//根据userCode查询YhUserAdminInfo表
		YhUserAdminInfoExample example=new YhUserAdminInfoExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserCodeEqualTo(userCode);
		List<YhUserAdminInfo> selectByExample = YhUserAdminInfoService.selectByExample(example);
		if(null==selectByExample||selectByExample.size()==0) {
			return null;
		}
		YhUserAdminInfo yhUserAdminInfo = selectByExample.get(0);
		Integer id = yhUserAdminInfo.getId();
		
        List<YhOrgTreeVojo> upmsPermissionst=YhOrgTreeService.selectalltreeOrg2(String.valueOf(id));
        //建立树形结构
        List<YhOrgTreeVojo> sum = sum(upmsPermissionst);
        ArrayList<YhOrgTreeVojo> removeDuplicateUser = removeDuplicateUser(sum);
        int i=0;
        for (YhOrgTreeVojo yhOrgTreeVojo : removeDuplicateUser) {
        	i++;
			if(i==1) {
				yhOrgTreeVojo.setOpen(true);
			}else {
				yhOrgTreeVojo.setOpen(false);
			}
		}
        MenuTreeVojo menuTree1 =new MenuTreeVojo(removeDuplicateUser);
        upmsPermissionst=menuTree1.builTree(selectChildnodeMax);
        return upmsPermissionst;
    }
	public static ArrayList<YhOrgTreeVojo> removeDuplicateUser(List<YhOrgTreeVojo> users) {
		Set<YhOrgTreeVojo> personSet = new TreeSet<YhOrgTreeVojo>(new Comparator<YhOrgTreeVojo>() {
			public int compare(YhOrgTreeVojo o1, YhOrgTreeVojo o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		personSet.addAll(users);
		return new ArrayList<YhOrgTreeVojo>(personSet);
	}
	
	public static void removeDuplicate(List list) {

		for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {

		for ( int j = list.size() - 1 ; j > i; j -- ) {

		if (list.get(j).equals(list.get(i))) {

		list.remove(j);}}}

		System.out.println(list);}


	public  List<YhOrgTreeVojo> sum(List<YhOrgTreeVojo> upmsPermissionst) {
		int size = upmsPermissionst.size();
		for (int i = 0; i < size; i++) {
			if("0".equals(upmsPermissionst.get(i).getSuperOrgCode())){
				continue;
			}
			List<YhOrgTreeVojo> list=new ArrayList<YhOrgTreeVojo>();
			List<YhOrgTreeVojo> sum2 = sum2(upmsPermissionst.get(i),list);
			upmsPermissionst.addAll(sum2);
		}
		
		 
    	
    	return upmsPermissionst;
   }
	public  List<YhOrgTreeVojo> sum2(YhOrgTreeVojo upmsPermissionst,List<YhOrgTreeVojo> list) {
		
		String superOrgCode = upmsPermissionst.getSuperOrgCode();
		YhOrgTreeVojo selectalltreeOrg3 = YhOrgTreeService.selectalltreeOrg3(superOrgCode);
		if("0".equals(selectalltreeOrg3.getSuperOrgCode())){
			list.add(selectalltreeOrg3);
			return list;
		}
		list.add(selectalltreeOrg3);
		return sum2(selectalltreeOrg3,list);
	}
	
	
	@RequestMapping(value = "/regInfoByTimeType", method = RequestMethod.POST)
	@ResponseBody
	public Result regInfoByTimeType(@RequestParam(required = false) String timetype,@RequestParam(required = false) Integer datatype) {
		
		doctorConsoleVOJO findDoctorAllInfoByTimeTypeAndDataType = yhUserMakeCardService.findDoctorAllInfoByTimeTypeAndDataType(timetype, datatype);
		return Result.ok(findDoctorAllInfoByTimeTypeAndDataType);
	}
	@RequestMapping(value = "/orgSurplusCard", method = RequestMethod.POST)
	@ResponseBody
	public Result selectOrgSurplusCard(String orgCode) {
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		int orgSurplusCard = yhUserMakeCardService.selectOrgSurplusCard(yhUserReginfo.getUserCode(), orgCode);
		return Result.ok(orgSurplusCard);
		
	}
    
}