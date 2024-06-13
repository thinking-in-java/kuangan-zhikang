package com.yihe.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年3月30日 下午5:14:10 
 *@version 1.0 
 */
@Controller
public class ErrorController {
	
	@GetMapping("/error")
    public String error(HttpServletRequest request, HttpServletResponse response)
    {
        return "error/error-404";
    }

}
