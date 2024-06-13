package com.yihe.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhJzService;
import com.yihe.system.domain.YhJzServiceAssessmentSwitch;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.domain.YhManageTask;

public interface YhTaskServiceAPIMapper {
	YhJzServiceAssessmentSwitch	queryassessmentswitchbyid(@Param("childname")String childname);
	YhJzServiceBodydataSwitch querybodydataswitchbyid(@Param("childname")String childname);
	int selecttotalperson(YhManageTask task);
	String selectdoctorid(YhManageTask task);
	int inserttotalperson(YhManageTask task);
	int insertyhjzserviceperson(YhManageTask task);
	YhJzService mainswitch(Integer switchid);
	int	selectbloodpressurecount(String Usercode);
	int selectbloodsuger(String Usercode);
	int selectbloodfat(String Usercode);
	int selectbmicount(String Usercode);
}
