package com.yihe.system.utils;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年9月1日 上午10:09:50 
 *@version 1.0 
 */
@Component
public class AsyncTask {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Async("getAsyncExecutor")
	public Future<String> doManyDMRisk(String url) throws InterruptedException{
		logger.info("评估开始.");
		long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        logger.info("评估耗时.", end-start);
        return new AsyncResult<>("评估成功!");
	}
	

}

