package com.yihe.applet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserDeviceApp;
import com.yihe.applet.api.domain.YhUserEcgApp;
import com.yihe.applet.api.service.impl.YhUserDeviceAppServiceImpl;
import com.yihe.applet.api.service.impl.YhdvdevicetypeAppServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api/device")
@Api(value = "设备管理", description = "设备")
public class YhUserDeviceAppController {
	@Autowired
	YhUserDeviceAppServiceImpl YhUserDeviceServiceImpl;
	@Autowired
	YhdvdevicetypeAppServiceImpl YhdvdevicetypeAppServiceImpl;
	/**
	 * 新增记录
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/addInfo")
	@ResponseBody
	public Result addInfo(YhUserDeviceApp record) {
		System.out.println(record);
		if(null==record){
			return Result.error("参数异常");
		}
		if(null==record.getUsercode()||null==record.getTerminalid()){
			return Result.error("参数异常");
		}
		
		int findExistenceDevice = YhUserDeviceServiceImpl.findExistenceDevice(record.getTerminalid());
		if(findExistenceDevice>0){
			return Result.error("序列号已被其他用户绑定");
		}
		int insertSelective = YhUserDeviceServiceImpl.insertSelective(record);

		if (insertSelective > 0) {
			return Result.ok();
		}

		return Result.error();
	}

	/**
	 * 删除记录
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/delInfo")
	@ResponseBody
	public Result delInfo(Integer tid) {
		int deleteByPrimaryKey = YhUserDeviceServiceImpl.deleteByPrimaryKey(tid);
		if (deleteByPrimaryKey > 0) {
			return Result.ok();
		}

		return Result.error();
	}

	/**
	 * 查询集合
	 */
	@RequestMapping(value = "/findInfo")
	@ResponseBody
	public Result findInfo(String usercode) {
		if(StringUtil.isEmpty(usercode)){
			return Result.error();
		}
		return Result.ok(YhUserDeviceServiceImpl.findDeviceAllByUsercode(usercode));
	}
	
	/**
	 * 查询设备类型列表
	 */
	@RequestMapping(value = "/findTypeInfo")
	@ResponseBody
	public Result findTypeInfo(String usercode) {
		if(StringUtil.isEmpty(usercode)){
			return Result.error();
		}
		return Result.ok(YhdvdevicetypeAppServiceImpl.findDeviceTypeListInfo());
	}
}
