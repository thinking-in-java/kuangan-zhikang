package com.yihe.applet.api.mapper;
/**
 * @version: shayu3.0
 * @Description: 睡眠问题评估mapper
 * @author: lhh
 * @date: 2020年4月15日 下午4:45:22
 */


import com.yihe.applet.api.domain.YhSleepQuestionApp;

import java.util.List;

public interface YhSleepQuestionAppMapper {
	/*
	 * 查询所有问题
	 */
	public List<YhSleepQuestionApp> selAllQuestion();
}
