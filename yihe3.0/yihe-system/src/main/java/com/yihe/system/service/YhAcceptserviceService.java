package com.yihe.system.service;

import com.yihe.system.domain.YhJzServicePerson;
import com.yihe.system.domain.YhManageTotalTask;
import com.yihe.system.domain.YhServicePage;
import com.yihe.system.domain.YhUserReginfo;

import java.util.List;

public interface YhAcceptserviceService {
	YhManageTotalTask selectservicecountbyusercode(YhServicePage page);
	YhManageTotalTask	selectservicecountconditions(YhServicePage page);
	YhManageTotalTask selectservicebyusercode(YhServicePage page);
	int acceptservice(String[] serviceid,String Usercode);
	double selectCooperate(String usercode);
	List<YhUserReginfo> selectusername(String usercode);
	int insertserviceplan(YhJzServicePerson yhJzServicePerson);
}
