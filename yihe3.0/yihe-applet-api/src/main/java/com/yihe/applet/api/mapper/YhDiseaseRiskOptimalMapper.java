package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHDiseaseRiskOptimal;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午6:05:07 
 *@version 1.0 
 */
public interface YhDiseaseRiskOptimalMapper {
	/**
     * 查询危险因素字典
     * 
     * @param id 危险因素字典ID
     * @return 危险因素字典
     */
	List<YHDiseaseRiskOptimal> selectYhDiseaseRiskOptimalById(@Param("isRadar")Integer isRadar,@Param("sex")String sex,@Param("diseaseid")String diseaseid) ;
}
