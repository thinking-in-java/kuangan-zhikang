package com.yihe.web.controller.system;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YhJzSysSms;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhForgotPasswordService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/forgot")
public class YhForgotPasswordController {
	@Autowired
	YhForgotPasswordService service;
	@Autowired
    private   RedisTemplate<String, String> stringTemplate;
	@ApiOperation(value = "查询用户是否存在")
    @RequestMapping(value = "/selectishave", method = RequestMethod.GET)
	@ResponseBody
	public Result selectishave(String name,String phone) {
 	
	 return Result.ok(service.selectcount(name, phone));
	}
	@ApiOperation(value = "发送短信")
    @RequestMapping(value = "/sendsms", method = RequestMethod.GET)
	@ResponseBody
	public String sendsms(String phone) {
		return sendsmsbegin(phone);
	}
	@ApiOperation(value = "重置密码")
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public Result reset(YhUserReginfo regin) {
		ValueOperations<String, String> valueTemplate = stringTemplate.opsForValue();
		String  code= valueTemplate.get(regin.getMobile());
		if(!regin.getUserCode().equals(code)) {
			return Result.error("验证码无效或已过期");
		}else {
			SysPasswordService sysPasswordService = new SysPasswordService();
			regin.setUserPwd(sysPasswordService.encryptPassword(regin.getUserName(), regin.getUserPwd()));;
			return Result.ok(service.updatepwd(regin));
		}
	}
	//存到reids
	public   void savetimekey(String random,String Phone) {
		ValueOperations<String, String> valueTemplate = stringTemplate.opsForValue();
		valueTemplate.set(Phone,random,70, TimeUnit.SECONDS);
	}
	//发送短信
	public  String sendsmsbegin(String phone) {
		YhJzSysSms sms=service.sms();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", sms.getAccesskey(), sms.getAccesskeysecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        String random=randomCode();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName",sms.getSignname());
        request.putQueryParameter("TemplateCode", sms.getTemplateId());
        request.putQueryParameter("TemplateParam", "{\"code\":\""+random+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject json = JSONObject.fromObject(response.getData());
            String code=json.getString("Code");
            if("OK".equals(code)) {
            	 savetimekey(random,phone);
            	 return "OK";
            }else {
            	return "failure";
            }
            
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
		return "failure";
    }
	
	//生成随机验证码
	public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
