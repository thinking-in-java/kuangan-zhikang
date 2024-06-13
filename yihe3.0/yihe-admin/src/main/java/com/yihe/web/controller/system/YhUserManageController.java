package com.yihe.web.controller.system;	

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhFoodSrecipeRecord;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhUserManageService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/manage/user")
@Api(value = "用户管理", description = "用户管理")
public class YhUserManageController {
	@Autowired
	YhUserManageService yhusermanageservice;
	
	@ApiOperation(value = "用户查找")
	@RequestMapping(value = "/selectuser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> selectuser(YhUserReginfo regin) {
		List<YhUserReginfo> yhuserreginfo=new ArrayList<YhUserReginfo>();
		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhUserReginfo> list = yhusermanageservice.selectuseranrole(regin);
 		int allcount=yhusermanageservice.selectcount();
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",allcount);
 		map.put("data", list);
		  return map;
	}
	
	@ApiOperation(value = "删除用户")
	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteuserbyid(YhUserReginfo yhuserreginfo) {
		yhusermanageservice.deleterolepowerbyusercode(yhuserreginfo.getUserCode());
		return Result.ok(yhusermanageservice.deleteuserbyid(yhuserreginfo.getUserCode()));
	}
	
	@ApiOperation(value = "修改用户")
	@RequestMapping(value = "/updateuser", method = RequestMethod.GET)
	@ResponseBody
	public Result updateuserbyid(YhUserReginfo yhuserreginfo) {
		return Result.ok(yhusermanageservice.updateuserbyid(yhuserreginfo));
	}
}
