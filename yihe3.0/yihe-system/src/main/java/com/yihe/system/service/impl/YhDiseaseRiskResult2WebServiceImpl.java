package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhDiseaseRiskResult2Web;
import com.yihe.system.mapper.YhDiseaseRiskResult2WebMapper;
import com.yihe.system.service.YhDiseaseRiskResult2WebService;
@Service
public class YhDiseaseRiskResult2WebServiceImpl implements YhDiseaseRiskResult2WebService{
	@Autowired
	YhDiseaseRiskResult2WebMapper yhDiseaseRiskResult2WebMapper;

	@Override
	public YhDiseaseRiskResult2Web selectUserDiseaseRiskByParam(String userCode) {
		// TODO Auto-generated method stub
		return yhDiseaseRiskResult2WebMapper.selectUserDiseaseRiskByParam(userCode);
	}
}
