package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhJzService;
import com.yihe.system.domain.YhJzServiceAssessmentSwitch;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;

public interface YhDivisionsettingMapper {
	List<YhJzService> selectallservice();
	int updateservice(YhJzService service);
	List<YhJzServiceBodydataSwitch> selectbodyswitch();
	int updatebodyswitch(YhJzServiceBodydataSwitch YhJzServiceBodydataSwitch);
	List<YhJzServiceAssessmentSwitch>  selectassessmentswitch();
	int updateassessmentswitch(YhJzServiceAssessmentSwitch YhJzServiceAssessmentSwitch);
}
