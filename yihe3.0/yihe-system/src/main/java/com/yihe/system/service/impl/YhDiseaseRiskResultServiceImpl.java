package com.yihe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhDiseaseRiskResult2;
import com.yihe.system.mapper.YhDiseaseRiskResultMapper;
import com.yihe.system.service.YhDiseaseRiskResultService;

/**
* YhDiseaseRiskResultService实现
* Created by dyb on 2020/3/24.
*/
@Service
@Transactional
public class YhDiseaseRiskResultServiceImpl implements YhDiseaseRiskResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhDiseaseRiskResultServiceImpl.class);

    @Autowired
    YhDiseaseRiskResultMapper yhDiseaseRiskResultMapper;

	@Override
	public YhDiseaseRiskResult2 selectRiskResulByRequestNum(String RequestNum) {
		// TODO Auto-generated method stub
		return yhDiseaseRiskResultMapper.selectRiskResulByRequestNum(RequestNum);
	}

}