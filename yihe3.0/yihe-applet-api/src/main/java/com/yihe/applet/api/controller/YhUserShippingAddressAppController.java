package com.yihe.applet.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserShippingAddressApp;
import com.yihe.applet.api.service.YhUserShippingAddressAppService;
/**
 * 用户地址信息控制器
 * @author 阳雄
 *@date 2021年1月13日09:59:06
 */
import com.yihe.system.utils.Result;

@Controller
@RequestMapping("/api/YhUserShippingAddressApp")
public class YhUserShippingAddressAppController {
	@Autowired
	YhUserShippingAddressAppService yhUserShippingAddressAppService;

	@RequestMapping(value = "/redactYhUserShippingAddress")
	@ResponseBody
	public Result redactYhUserShippingAddress(YhUserShippingAddressApp yhUserShippingAddressApp) {
		int row = 0;
		if(yhUserShippingAddressApp.getWhetherDefault() == 1) {
			yhUserShippingAddressAppService.updateUserAddressStatus(yhUserShippingAddressApp.getUserCode());
		}
		yhUserShippingAddressApp.setAddDate(new Date());
		if(yhUserShippingAddressApp.getSiteId() == null || "".equals(yhUserShippingAddressApp.getSiteId())) {
			row = yhUserShippingAddressAppService.insertSelective(yhUserShippingAddressApp);
		}else {
			yhUserShippingAddressAppService.updateByPrimaryKeySelective(yhUserShippingAddressApp);
		}
		
		return Result.ok(row);

	}

	/**
	 * 查询最新的地址
	 * 
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/selectNewestShippingAddress")
	@ResponseBody
	public Result redactYhUserShippingAddress(String userCode) {
		YhUserShippingAddressApp address = yhUserShippingAddressAppService.selectNewestShippingAddress(userCode);
		return Result.ok(address);

	}

	/**
	 * 查询用户所有的地址
	 * 
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/selectAllShippingAddress")
	@ResponseBody
	public Result selectAllShippingAddress(String userCode) {
		List<YhUserShippingAddressApp> list = yhUserShippingAddressAppService.selectAllShippingAddress(userCode);
		return Result.ok(list);

	}

	@RequestMapping(value = "/deleteShippingAddress")
	@ResponseBody
	public Result deleteShippingAddress(Integer siteId) {
		int deleteByPrimaryKey = yhUserShippingAddressAppService.deleteByPrimaryKey(siteId);
		return Result.ok(deleteByPrimaryKey);

	}
	
	@RequestMapping(value = "/selectAddress")
	@ResponseBody
	public Result selectAddress(Integer siteId) {
		 YhUserShippingAddressApp addressApp = yhUserShippingAddressAppService.selectByPrimaryKey(siteId);
		return Result.ok(addressApp);

	}
}
