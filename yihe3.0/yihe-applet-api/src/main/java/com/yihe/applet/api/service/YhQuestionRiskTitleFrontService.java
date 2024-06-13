package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhQuestionRiskTitleFront;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午5:00:24 
 *@version 1.0 
 */
public interface YhQuestionRiskTitleFrontService {
	List<YhQuestionRiskTitleFront> selRiskQuestionByDisId(int diseaseId);
}
