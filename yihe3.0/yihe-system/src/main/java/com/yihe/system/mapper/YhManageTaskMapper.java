package com.yihe.system.mapper;

import com.yihe.system.domain.YhManageTotalTask;
import com.yihe.system.domain.YhServicePage;

import java.util.List;

public interface YhManageTaskMapper {
	List<YhManageTotalTask> selectlist(YhServicePage Yhservicepage);

	List<Integer> selectAll(YhServicePage Yhservicepage);
	int updatestate(YhServicePage Yhservicepage);
	int updatestates(YhServicePage Yhservicepage);
	List<YhManageTotalTask> selectunread(YhServicePage Yhservicepage);
	YhManageTotalTask selectservicecount(YhServicePage Yhservicepage);
	int selectcountpage(YhServicePage Yhservicepage);
	YhManageTotalTask selectditailbyid(YhServicePage Yhservicepage);
	YhServicePage selectdoctorname(YhServicePage Yhservicepage);
	int updateisrede(YhServicePage Yhservicepage);
	int updateisredebyid(YhServicePage Yhservicepage);
	int selectplanid(YhServicePage Yhservicepage);
	int updateyhUserHealthyPlan(Integer planid);
	int deleteyhUserHealthyPlan(Integer planid);
	String selectmobilebyusercode(String usercode);
	String selectplanusercodeid(int id);
	
	String selectplanCode(int id);
}
