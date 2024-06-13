package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhManageTotalTask;
import com.yihe.system.domain.YhServicePage;

public interface YhManageTaskService {
	List<YhManageTotalTask> selectlist(YhServicePage Yhservicepage);
	int updatestate(YhServicePage Yhservicepage);
	List<YhManageTotalTask> selectunread(YhServicePage Yhservicepage);
	YhManageTotalTask selectservicecount(YhServicePage Yhservicepage);
	int selectcountpage(YhServicePage Yhservicepage);
	YhManageTotalTask selectditailbyid(YhServicePage Yhservicepage);
	YhServicePage selectdoctorname(YhServicePage Yhservicepage);
	int updateisrede(YhServicePage Yhservicepage);
	
	int closeUpdatestate(String[] serviceid, String acceptsituation);
}
