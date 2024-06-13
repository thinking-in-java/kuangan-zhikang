package com.yihe.system.service;

import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.domain.YhManageTask;
import com.yihe.system.utils.Result;

public interface YhTaskServiceAPIService {
	Result	queryswitchbyid(Integer switchid,Integer typeid,String	childname);
	Result insertperson(YhManageTask task);
	int	selectbloodpressurecount(String Usercode);
	int selectbloodsuger(String Usercode);
	int selectbloodfat(String Usercode);
	int selectbmicount(String Usercode);
	
	YhJzServiceBodydataSwitch	queryswitchbyid1(Integer switchid,Integer typeid,String	childname);
}
