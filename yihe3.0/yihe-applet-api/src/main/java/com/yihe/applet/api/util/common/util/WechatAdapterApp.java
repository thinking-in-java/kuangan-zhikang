package com.yihe.applet.api.util.common.util;

import com.alibaba.fastjson.JSON;
 

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by codedrinker on 2018/11/24.
 */
 
public class WechatAdapterApp {
    private final Logger logger = LoggerFactory.getLogger(WechatAdapterApp.class);

    private String appid;

    private String secret;
    
    public WechatAdapterApp(String appid,String secret){
    	this.appid=appid;
    	this.secret=secret;
    }
 

	public SessionDTOApp jscode2session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, appid, secret, code))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                SessionDTOApp sessionDTO = JSON.parseObject(execute.body().string(), SessionDTOApp.class);
                logger.info("jscode2session get url -> {}, info -> {}", String.format(url, appid, secret, code), JSON.toJSONString(sessionDTO));
                return sessionDTO;
            } else {
                logger.error("jscode2session authorize error -> {}", code);
                
            }

        } catch (IOException e) {
            logger.error("jscode2session authorize error -> {}", code, e);
             
        }
		return null;
    }
}
