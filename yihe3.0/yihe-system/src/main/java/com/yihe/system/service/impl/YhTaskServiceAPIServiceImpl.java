package com.yihe.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhJzService;
import com.yihe.system.domain.YhJzServiceAssessmentSwitch;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.domain.YhManageTask;
import com.yihe.system.mapper.YhTaskServiceAPIMapper;
import com.yihe.system.service.YhTaskServiceAPIService;
import com.yihe.system.utils.Result;
@Service
public class YhTaskServiceAPIServiceImpl implements YhTaskServiceAPIService{
	@Autowired
	YhTaskServiceAPIMapper yhtaskserviceapimapper;
	
	
	@Override
	public YhJzServiceBodydataSwitch queryswitchbyid1(Integer switchid,Integer typeid, String childname) {
		// TODO Auto-generated method stub
		YhJzService swich=yhtaskserviceapimapper.mainswitch(switchid);
		
		YhJzServiceBodydataSwitch body=new YhJzServiceBodydataSwitch();
		YhJzServiceAssessmentSwitch bodys=new YhJzServiceAssessmentSwitch();
		if(swich.getIsopen()==1) {
		if(typeid!=null && typeid!=0 && typeid==1 || typeid==2) {
			if(typeid==1){
				body=yhtaskserviceapimapper.querybodydataswitchbyid( childname);
			}
			if( typeid==2){
				bodys=yhtaskserviceapimapper.queryassessmentswitchbyid( childname);
			}
			
			if(body.getId()!=null) {

				body.setService_content(swich.getService_content());
				body.setService_date(swich.getService_date());
				body.setReturnvisit(swich.getReturn_id());

			}
			if(bodys.getId()!=null){
				body.setEvery_switch(bodys.getEvery_switch());
				body.setId(bodys.getId());
				body.setService_content(swich.getService_content());
				body.setService_date(swich.getService_date());
				body.setReturnvisit(swich.getReturn_id());

			}
			
		}
	}else{
			body.setEvery_switch(2);
		}
		return body;	
	}
	
	
	@Override
	public Result queryswitchbyid(Integer switchid,Integer typeid, String childname) {
		// TODO Auto-generated method stub
		YhJzService swich=yhtaskserviceapimapper.mainswitch(switchid);
		
		if(swich.getIsopen()==1) {
		if(typeid!=null && typeid!=0 && typeid==1) {
			YhJzServiceBodydataSwitch body=yhtaskserviceapimapper.querybodydataswitchbyid( childname);
			
			if(body!=null) {
				body.setService_content(swich.getService_content());
				body.setService_date(swich.getService_date());
				body.setReturnvisit(swich.getReturn_id());
				return Result.ok(body);		
			}else {
				return Result.error("错误");	
			}
			
		}else if(typeid!=null && typeid!=0 && typeid==2){
			YhJzServiceAssessmentSwitch assessment=yhtaskserviceapimapper.queryassessmentswitchbyid(childname);
			if(assessment!=null) {
				assessment.setService_content(swich.getService_content());
				assessment.setService_date(swich.getService_date());
				assessment.setReturnvisit(swich.getReturn_id());
				return Result.ok(assessment);		
			}else {
				return Result.error("错误");	
			}
		}
	}
		return Result.error("错误");
	}
	@Override
	public Result insertperson(YhManageTask task) {
		// TODO Auto-generated method stub
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 task.setTime(df.format(new Date()));
		int count= yhtaskserviceapimapper.selecttotalperson(task);
		if(count==0) {
			String doctorid=yhtaskserviceapimapper.selectdoctorid(task);
			if(!doctorid.equals("0")) {
				task.setDoctorid(doctorid);
				yhtaskserviceapimapper.inserttotalperson(task);
				yhtaskserviceapimapper.insertyhjzserviceperson(task);
			}else {
				return null;
			}
			
		}else {
			yhtaskserviceapimapper.insertyhjzserviceperson(task);
		}
		return null;
	}
	@Override
	public int selectbloodpressurecount(String Usercode) {
		// TODO Auto-generated method stub
		return yhtaskserviceapimapper.selectbloodpressurecount(Usercode);
	}
	@Override
	public int selectbloodsuger(String Usercode) {
		// TODO Auto-generated method stub
		
		return yhtaskserviceapimapper.selectbloodsuger(Usercode);
	}
	@Override
	public int selectbloodfat(String Usercode) {
		// TODO Auto-generated method stub
		return yhtaskserviceapimapper.selectbloodfat(Usercode);
	}
	@Override
	public int selectbmicount(String Usercode) {
		// TODO Auto-generated method stub
		return yhtaskserviceapimapper.selectbmicount(Usercode);
	}

}
