package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhQuestionRiskTitleFront;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午5:02:54 
 *@version 1.0 
 */
public interface YhQuestionRiskTitleFrontMapper {
	/**
	 * 根据疾病id查询危险因素
	 * @param diseaseId
	 * @return
	 */
	List<YhQuestionRiskTitleFront> selRiskQuestionByDisId(@Param("diseaseId")int diseaseId);
}