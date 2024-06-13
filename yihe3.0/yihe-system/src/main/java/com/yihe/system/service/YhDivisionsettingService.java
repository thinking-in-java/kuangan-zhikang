package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhJzService;
import com.yihe.system.domain.YhJzServiceAssessmentSwitch;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;

public interface YhDivisionsettingService {
	List<YhJzService> selectallservice();
	List<YhJzServiceBodydataSwitch> selectbodyswitch();
	int updateservie(List<YhJzService> YhJzService);
	int updatebodyswitch(List<YhJzServiceBodydataSwitch> YhJzServiceBodydataSwitch);
	List<YhJzServiceAssessmentSwitch>  selectassessmentswitch();
	int updateassessmentswitch(List<YhJzServiceAssessmentSwitch> YhJzServiceAssessmentSwitch);
}
