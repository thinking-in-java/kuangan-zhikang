package com.yihe.system.service;

import com.yihe.system.domain.YhDiseaseRiskResult2;

/**
* YhDiseaseRiskResultService接口
* Created by dyb on 2020/3/24.
*/
public interface YhDiseaseRiskResultService{
	YhDiseaseRiskResult2 selectRiskResulByRequestNum(String RequestNum);

}