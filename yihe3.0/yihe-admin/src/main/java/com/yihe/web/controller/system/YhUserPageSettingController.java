package com.yihe.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhDicIntroduction;
import com.yihe.system.service.YhUserPageSettingService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 页面信息配置
 * @author Royiwen
 *
 */
@Controller
@RequestMapping("/pagesetting")
@Api(value = "页面信息配置", description = "页面信息配置")
public class YhUserPageSettingController {
	@Autowired
	YhUserPageSettingService yhuserpagesettingservice;
	
	@ApiOperation(value = "修改高级配置页面信息显示")
    @RequestMapping(value = "/updatatalldicintro", method = RequestMethod.POST)
	@ResponseBody
	public Result updatatalldicintro(YhDicIntroduction yhdicintroduction) {
		
		return Result.ok(yhuserpagesettingservice.updateyhdicintroduction(yhdicintroduction));
	}

	@ApiOperation(value = "上传图片")
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadImg(YhDicIntroduction yhdicintroduction,@RequestParam("file") MultipartFile file) {
		System.out.println(yhdicintroduction);
		return Result.ok(yhuserpagesettingservice.updateyhdicintroduction(yhdicintroduction));
	}
}
