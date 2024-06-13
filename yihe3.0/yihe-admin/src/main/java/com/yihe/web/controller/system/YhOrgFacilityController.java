/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhOrgFacility;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhOrgFacilityMapper;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 设备绑定
 * Created by dong on 2020/3/18.
 */
@Controller
@RequestMapping("/YhOrgFacilityController")
@Api(value = "YhOrgTree控制器", description = "YhOrgTree管理")
public class YhOrgFacilityController {

	@Autowired
    private YhOrgFacilityMapper yhOrgFacilityMapper;
	
	
	@RequestMapping(value = "/deleteData", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteData(Integer tid) {
		int deleteYhUserDevice = yhOrgFacilityMapper.deleteByPrimaryKey(tid);
		return Result.ok(deleteYhUserDevice);
	}
	
	@ApiOperation(value = "查询该仪器是否存在绑定")
    @RequestMapping(value = "/orgFacilityshowname", method = RequestMethod.POST)
	@ResponseBody
    public Result orgFacilityshowceid(String deviceid) {
		YhOrgFacility	info=yhOrgFacilityMapper.showOrgCodeBydeviceid(deviceid);
        return Result.ok(info);
    }
	
    
    @ApiOperation(value = "增加或者修改设备绑定")
    @RequestMapping(value = "/orgFacilityadd", method = RequestMethod.POST)
	@ResponseBody
    public Result orgFacilityadd(YhOrgFacility org) {
    	int info=0;
    	if(org.getId()==null || "".equals(org.getId())) {
    		info=yhOrgFacilityMapper.insertSelective(org);
    	}else {
    		info=yhOrgFacilityMapper.updateByPrimaryKeySelective(org);
    	}
    	
        return Result.ok(info);
    }
    
    

    @ApiOperation(value = "查询设备绑定")
    @RequestMapping(value = "/showOrgCodeList", method = RequestMethod.POST)
	@ResponseBody
    public Result orgFacilityadd(String orgCode) {
    	List<YhOrgFacility> info=yhOrgFacilityMapper.showOrgCodeList(orgCode);
        return Result.ok(info);
    }
    
    
    
	
}
