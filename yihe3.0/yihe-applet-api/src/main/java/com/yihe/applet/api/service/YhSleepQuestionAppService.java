package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSleepQuestionApp;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 睡眠问卷调查service
 * @author: lhh
 * @date: 2020年4月15日 下午5:05:55
 */
public interface YhSleepQuestionAppService {
	/*
	 * 查询所有问题
	 */
	public List<YhSleepQuestionApp> selAllQuestion();
}
