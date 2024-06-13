package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhQuestionRiskTitleFront;
import com.yihe.applet.api.mapper.YhQuestionRiskTitleFrontMapper;
import com.yihe.applet.api.service.YhQuestionRiskTitleFrontService;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午5:01:27 
 *@version 1.0 
 */
@Service
public class YhQuestionRiskTitleFrontServiceImpl implements YhQuestionRiskTitleFrontService{
	@Autowired
	private YhQuestionRiskTitleFrontMapper yhQuestionRiskTitleFrontMapper;
	
	@Override
	public List<YhQuestionRiskTitleFront> selRiskQuestionByDisId(int diseaseId) {
		// TODO Auto-generated method stub
		return yhQuestionRiskTitleFrontMapper.selRiskQuestionByDisId(diseaseId);
	}

}
