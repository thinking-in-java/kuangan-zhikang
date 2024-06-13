package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yihe.system.service.WeChatService;
import com.yihe.system.utils.RestTemplateUtil;

@Service
public class WeChatServiceImpl implements WeChatService {
	@Value("${wx.access_token_url}")
    private String accessTokenUrl;
 
    @Value("${wx.appId}")
    private String appId;
 
    @Value("${wx.secret}")
    private String secret;
    
    
	@Override
	public String getWeChatAccessTokenUrl() {
		// TODO Auto-generated method stub
		 try {
	            String url = accessTokenUrl.replace("APPID",appId).replace("APPSECRET",secret);
	            System.out.println("请求获取access_token的url:" + url);
	            return RestTemplateUtil.get(url);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "请求获取access_token出现异常";
	        }
	}

}
