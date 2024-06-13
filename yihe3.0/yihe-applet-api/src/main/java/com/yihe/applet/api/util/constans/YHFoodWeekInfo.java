package com.yihe.applet.api.util.constans;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.yihe.applet.api.service.impl.YhFoodSrecipeApiNewServiceImpl;

//1.主要用于标记配置类，兼备Component的效果。
@Configuration 
// 2.开启定时任务     
@EnableScheduling 
public class YHFoodWeekInfo {
	@Autowired
	YhFoodSrecipeApiNewServiceImpl YhFoodSrecipeApiNewServiceImpl;
	//0 0/3 * * * ? 每周日23点50  0 50 23 ? * 1     0 50 23 ? * SUN
	@Scheduled(cron = "0 30 23 ? * SUN")
	 private void configureTasks() throws ParseException {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        YhFoodSrecipeApiNewServiceImpl.addYhUserFoodWeekly();
    }


}
