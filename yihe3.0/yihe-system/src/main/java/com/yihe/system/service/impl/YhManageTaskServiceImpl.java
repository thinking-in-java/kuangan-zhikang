package com.yihe.system.service.impl;

import com.yihe.system.domain.YhManageTotalTask;
import com.yihe.system.domain.YhServicePage;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhManageTaskMapper;
import com.yihe.system.service.YhManageTaskService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class YhManageTaskServiceImpl implements YhManageTaskService{
	@Autowired
	YhManageTaskMapper Yhmanagetaskmapper;

	@Override
	public List<YhManageTotalTask> selectlist(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		Yhservicepage.setBeginpage((Yhservicepage.getPage()-1)*20);
		if(Yhservicepage.getTime()!="" && Yhservicepage.getTime()!=null) {
			Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
			Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");
		}
		List<YhManageTotalTask> managetask=Yhmanagetaskmapper.selectlist(Yhservicepage);
		return managetask;
	}
	@Override
	public int updatestate(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		if(Yhservicepage.getState()==1) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Yhservicepage.setBegintime(df.format(new Date()));
		}else if(Yhservicepage.getState()==3 || Yhservicepage.getState()==2){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Yhservicepage.setEndtime(df.format(new Date()));
			int planid=Yhmanagetaskmapper.selectplanid(Yhservicepage);
			//当状态等于3时，修改审核通过，当为2时，删除记录
			if(Yhservicepage.getState()==3){
				Yhmanagetaskmapper.updateyhUserHealthyPlan(planid);
			}
			if(Yhservicepage.getState()==2){
				Yhmanagetaskmapper.deleteyhUserHealthyPlan(planid);
			}
		}
		return Yhmanagetaskmapper.updatestate(Yhservicepage);
	}
	
	
	
	@Override
	public int closeUpdatestate(String[] serviceid, String acceptsituation) {
		YhServicePage Yhservicepage=new YhServicePage();
		Yhservicepage.setAcceptsituation(acceptsituation);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
		Yhservicepage.setUsercode(reginfo.getUserCode());
		Yhservicepage.setEndtime("132");
		int ret=0;
		for (int i = 0; i < serviceid.length; i++) {
			Yhservicepage.setId(Integer.valueOf(serviceid[i]));
			Yhservicepage.setState(2);
			ret=ret+Yhmanagetaskmapper.updatestates(Yhservicepage);
		}
		return ret;
	}
	
	
	
	@Override
	public List<YhManageTotalTask> selectunread(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		return Yhmanagetaskmapper.selectunread(Yhservicepage);
	}

	@Override
	public List<Integer> selectAll(YhServicePage Yhservicepage) {
		Yhservicepage.setBeginpage((Yhservicepage.getPage()-1)*20);
		if(Yhservicepage.getTime()!="" && Yhservicepage.getTime()!=null) {
			Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
			Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");
		}
		List<Integer> managetask=Yhmanagetaskmapper.selectAll(Yhservicepage);
		return managetask;
	}

	@Override
	public YhManageTotalTask selectservicecount(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		if(Yhservicepage.getTime()!=null && Yhservicepage.getTime()!="") {
			Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
			Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");

		}
		return Yhmanagetaskmapper.selectservicecount(Yhservicepage);
	}
	@Override
	public int selectcountpage(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		if(Yhservicepage.getTime()!=null && Yhservicepage.getTime()!="") {
		Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
		Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");
		}
		return Yhmanagetaskmapper.selectcountpage(Yhservicepage);
	}
	@Override
	public YhManageTotalTask selectditailbyid(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		Yhmanagetaskmapper.updateisredebyid(Yhservicepage);
		return Yhmanagetaskmapper.selectditailbyid(Yhservicepage);
	}
	@Override
	public YhServicePage selectdoctorname(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		return Yhmanagetaskmapper.selectdoctorname(Yhservicepage);
	}
	@Override
	public int updateisrede(YhServicePage Yhservicepage) {
		// TODO Auto-generated method stub
		return Yhmanagetaskmapper.updateisrede(Yhservicepage);
	}
	

}
