package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSleepQuestionApp;
import com.yihe.applet.api.mapper.YhSleepQuestionAppMapper;
import com.yihe.applet.api.service.YhSleepQuestionAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 睡眠问卷调查service实线层
 * @author: lhh
 * @date: 2020年4月15日 下午5:16:35
 */
@Service
@Transactional
public class YhSleepQuestionAppServiceImpl implements YhSleepQuestionAppService {
	
	@Autowired
	private YhSleepQuestionAppMapper yhSleepQuestionAppMapper;

	@Override
	public List<YhSleepQuestionApp> selAllQuestion() {
		// TODO Auto-generated method stub
		return yhSleepQuestionAppMapper.selAllQuestion();
	}
	
}
