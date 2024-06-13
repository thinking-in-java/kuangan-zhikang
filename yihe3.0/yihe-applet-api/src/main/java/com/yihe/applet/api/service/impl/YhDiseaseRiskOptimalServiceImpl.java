package com.yihe.applet.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.mapper.YhDiseaseRiskOptimalMapper;
import com.yihe.applet.api.service.YhDiseaseRiskOptimalService;
import com.yihe.system.domain.YHDiseaseRiskOptimal;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午6:04:10 
 *@version 1.0 
 */
@Service
public class YhDiseaseRiskOptimalServiceImpl implements YhDiseaseRiskOptimalService{

	@Resource
    private YhDiseaseRiskOptimalMapper yhDiseaseRiskOptimalMapper;
	
	@Override
	public List<YHDiseaseRiskOptimal> selectYhDiseaseRiskOptimalById(@Param("isRadar")Integer isRadar,@Param("sex")String sex,@Param("diseaseid")String diseaseid) {
		// TODO Auto-generated method stub
		return yhDiseaseRiskOptimalMapper.selectYhDiseaseRiskOptimalById(isRadar, sex, diseaseid);
	}

}
