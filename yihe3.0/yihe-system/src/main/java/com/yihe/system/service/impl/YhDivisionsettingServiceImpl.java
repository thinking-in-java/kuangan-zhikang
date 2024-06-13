package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhJzService;
import com.yihe.system.domain.YhJzServiceAssessmentSwitch;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.mapper.YhDivisionsettingMapper;
import com.yihe.system.service.YhDivisionsettingService;

@Service
public class YhDivisionsettingServiceImpl implements YhDivisionsettingService{
	@Autowired
	YhDivisionsettingMapper yhdivsionsettingmapper;

	@Override
	public List<YhJzService> selectallservice() {
		// TODO Auto-generated method stub
		return yhdivsionsettingmapper.selectallservice();
	}

	@Override
	public int updateservie(List<YhJzService> YhJzService) {
		// TODO Auto-generated method stub
		int j=0;
		for (int i = 0; i < YhJzService.size(); i++) {
			j+=yhdivsionsettingmapper.updateservice(YhJzService.get(i));
		}
		return j;
	}

	@Override
	public List<YhJzServiceBodydataSwitch> selectbodyswitch() {
		// TODO Auto-generated method stub
		return yhdivsionsettingmapper.selectbodyswitch();
	}

	@Override
	public int updatebodyswitch(List<YhJzServiceBodydataSwitch> YhJzServiceBodydataSwitch) {
		// TODO Auto-generated method stub
		int j=0;
		for (int i = 0; i < YhJzServiceBodydataSwitch.size(); i++) {
			j+=yhdivsionsettingmapper.updatebodyswitch(YhJzServiceBodydataSwitch.get(i));
		}
		return j;
	}

	@Override
	public List<YhJzServiceAssessmentSwitch> selectassessmentswitch() {
		// TODO Auto-generated method stub
		return yhdivsionsettingmapper.selectassessmentswitch();
	}

	@Override
	public int updateassessmentswitch(List<YhJzServiceAssessmentSwitch> YhJzServiceAssessmentSwitch) {
		// TODO Auto-generated method stub
		int j=0;
		for (int i = 0; i < YhJzServiceAssessmentSwitch.size(); i++) {
			j+=yhdivsionsettingmapper.updateassessmentswitch(YhJzServiceAssessmentSwitch.get(i));
		}
		return j;
	}
}
