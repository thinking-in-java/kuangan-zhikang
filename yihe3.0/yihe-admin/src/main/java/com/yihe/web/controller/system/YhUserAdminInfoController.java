package com.yihe.web.controller.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.YhUserAdminInfo;
import com.yihe.system.domain.YhUserAdminInfoExample;
import com.yihe.system.domain.YhUserAdminInfoExample.Criteria;
import com.yihe.system.service.YhUserAdminInfoService;

import io.swagger.annotations.Api;

/**
 * YhUserAdminInfocontroller
 * Created by dyb on 2020/3/19.
 */
@Controller
@RequestMapping("/YhUserAdminInfo")
@Api(value = "YhUserAdminInfo控制器", description = "YhUserAdminInfo管理")
public class YhUserAdminInfoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserAdminInfoController.class);
	
	
}