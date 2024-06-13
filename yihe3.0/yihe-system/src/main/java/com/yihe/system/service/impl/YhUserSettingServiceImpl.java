package com.yihe.system.service.impl;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhUserSettingMapper;
import com.yihe.system.service.YhUserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional

public class YhUserSettingServiceImpl  implements YhUserSettingService{
	@Autowired
	YhUserSettingMapper yhusersettingmapper;
	
	
	
	
	@Override
	public YhSysConfig selectyhjzsyssms() {
		// TODO Auto-generated method stub
		YhJzSysSms yhjzsyssms= yhusersettingmapper.selectyhjzsyssms();
		YhSysConfig sysconfig= yhusersettingmapper.selectyhsysconfig();
		sysconfig.setYhjzsyssms(yhjzsyssms);
		return sysconfig;
	}
	
	
	
	@Override
	public int insertsms(YhJzSysSms sms) {
		// TODO Auto-generated method stub
		if(null!=sms.getSmsId()) {
			return yhusersettingmapper.updatasms(sms);
		}else {
			return yhusersettingmapper.insertsms(sms);
		}
		
	}
	
	
	@Override
	public int insertsysconfig(YhSysConfig yhsysconfig) {
		// TODO Auto-generated method stub
		if(null!=yhsysconfig.getId()) {
			yhsysconfig.setValue(yhsysconfig.getValue());
			return yhusersettingmapper.updatasysconfig(yhsysconfig);
		}else {
			return yhusersettingmapper.insertsysconfig(yhsysconfig);
		}
	}
	
	
	@Override
	public List<YhOrgTree> selectyhorgtrry(String name) {
		// TODO Auto-generated method stub
			String orgcode=yhusersettingmapper.selectorgcode(name);
		//返回机构的集合
		List<YhOrgTree> yhorgtreeresut=yhusersettingmapper.yhorgtree(orgcode);
		System.out.println("-------------------------------------------------------------------"+orgcode);
		//全部机构集合
		List<YhOrgTree> yhorgtree=yhusersettingmapper.yhorgtreeparent();
		List<YhOrgTree> children=new ArrayList<YhOrgTree>();
		//return getorgbycode(yhorgtreeresut.get(0), yhorgtree, children);
		return yhorgtreeresut;
	}
	
	
	
	@Override
	public String selectbyusername(String name) {
		// TODO Auto-generated method stub
		return yhusersettingmapper.selectorgcode(name);
	}

	
	@Override
	public List<YhOrgTree> selectallorg() {
		// TODO Auto-generated method stub
		return yhusersettingmapper.yhorgtreeparent();
	}



	@Override
	public List<YhUserAdminInfo> selectalladmin() {
		// TODO Auto-generated method stub
		return yhusersettingmapper.selectalladmin();
	}



	@Override
	public List<YhMakeRegiCardInfo> selectrefincard(YhOrgTree yhorgtree,int page,int limit,String guishu,String yishi,String state,String name,String paixuming,String fangfa,String states) {
		// TODO Auto-generated method stub
		page=(page-1)*limit;
		if(paixuming.equals("beginTime")) {
			paixuming="card.Begin_Time";
		}
		if(paixuming.equals("endTime")) {
			paixuming="card.End_Time";
		}
		return yhusersettingmapper.selectrefincard(yhorgtree,page,limit,guishu,yishi,state,name,paixuming,fangfa,states);
	}

	@Override
	public List<YhUserReginfo> derivexlscard(YhOrgTree yhorgtree, String guishu, String yishi, String state, String name, String paixuming, String fangfa, String states) {
		if(paixuming.equals("beginTime")) {
			paixuming="card.Begin_Time";
		}
		if(paixuming.equals("endTime")) {
			paixuming="card.End_Time";
		}
		return yhusersettingmapper.derivexlscard(yhorgtree,guishu,yishi,state,name,paixuming,fangfa,states);
	}


	@Override
	public int selectcount(YhOrgTree yhorgtree,String guishu,String yishi,String state,String name,String states) {
		// TODO Auto-generated method stub
		return yhusersettingmapper.selectcount(yhorgtree,guishu,yishi,state,name,states);
	}



	@Override
	public List<YhMakeRegiCardInfo> selectalldoctor(YhOrgTree yhorgtree, String guishu, String name) {
		// TODO Auto-generated method stub
		return yhusersettingmapper.selectalldoctor(yhorgtree,guishu,name);
	}



	@Override
	public int fenpeiyishis(String id, String[] shuzu) {
		// TODO Auto-generated method stub
		int count=0;
		int s = Integer.parseInt(id);
		String doctorcode=yhusersettingmapper.selectdoctorcode(s);

		for (int i = 0; i < shuzu.length; i++) {
			String usercode=yhusersettingmapper.selectusercode(shuzu[i]);
			if(null!=usercode){
					int haveplan=yhusersettingmapper.selecttotalplan(usercode);
				if(haveplan>0){
					yhusersettingmapper.updatettotalplan(usercode,doctorcode);
					yhusersettingmapper.updatettotalplan1(usercode,doctorcode);
				}
			}

			count=yhusersettingmapper.fenpeiyishi(s, shuzu[i]);
		}
		return count;
	}



	@Override
	public int zhucefenpei(String id, String[] shuzu) throws ParseException {
		// TODO Auto-generated method stub
		int count=0;
		for (int i = 0; i < shuzu.length; i++) {
			//更改reg表的
			yhusersettingmapper.zhucefenpei2(id,shuzu[i]);
			//转移机构的注册卡

			//以前的时间
			String befortime=yhusersettingmapper.befortime(shuzu[i]);
			//要更改成的机构时间
			String nowtime=yhusersettingmapper.nowtime(id);
			if(nowtime==null){

			}else{
				if(befortime==null){
					yhusersettingmapper.changetime(shuzu[i], nowtime);
				}else{
					if(!compare(befortime,nowtime)){
						yhusersettingmapper.changetime(shuzu[i], nowtime);
					}
				}
			}
			count=yhusersettingmapper.zhucefenpei(id, shuzu[i]);
		}
		return count;
	}

	@Override
	public int cardIdActivation(String key, String enrolCardId) {
		return yhusersettingmapper.cardIdActivation(key,enrolCardId);
	}

	public boolean compare(String time1,String time2) throws ParseException {
		//如果想比较日期则写成"yyyy-MM-dd"就可以了
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//将字符串形式的时间转化为Date类型的时间
		Date a=sdf.parse(time1);
		Date b=sdf.parse(time2);

		if(a.before(b))
			return true;
		else
			return false;

	}



	@Override
	public String selectorgname() {
		// TODO Auto-generated method stub
		return yhusersettingmapper.selectorgname();
	}



	@Override
	public YhOrgTree selectorgname2() {
		// TODO Auto-generated method stub
		return yhusersettingmapper.selectorgname2();
	}



	@Override
	public int updateOrgName(String orgCode, String orgName) {
		// TODO Auto-generated method stub
		return yhusersettingmapper.updateOrgName(orgCode, orgName);
	}



}
