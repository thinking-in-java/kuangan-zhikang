package com.yihe.web.controller.system;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.YhHealthSetting;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * YhHeThresholdcontroller
 * Created by royiwen on 2020/3/27.
 */
@Controller
@RequestMapping("/YhHeThreshold")
@Api(value = "YhHeThreshold控制器", description = "YhHeThreshold管理")
public class YhHeThresholdController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHeThresholdController.class);
		@Autowired
		YhHeThresholdService thresservice;
		//健康检测告警阈值
  		@RequestMapping(value = "/selectallheath",method=RequestMethod.GET)
  		@ResponseBody
  		public Result selectallheath() {
			
			return Result.ok(thresservice.selectallheath());		
		}
  		
  		@RequestMapping(value = "/changedata")
  		@ResponseBody
  		public Result changedata(YhHealthSetting yhhealthsetting) {
			return Result.ok(thresservice.updatasetting(yhhealthsetting));		
		}
}