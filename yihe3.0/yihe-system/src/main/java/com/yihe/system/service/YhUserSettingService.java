package com.yihe.system.service;

import com.yihe.system.domain.*;

import java.text.ParseException;
import java.util.List;

public interface YhUserSettingService{
	YhSysConfig selectyhjzsyssms();
	int insertsms(YhJzSysSms sms);
	int insertsysconfig(YhSysConfig yhsysconfig);
	List<YhOrgTree> selectyhorgtrry(String name);
	String selectbyusername(String name);
	List<YhOrgTree> selectallorg();
	List<YhUserAdminInfo> selectalladmin();
	List<YhMakeRegiCardInfo> selectrefincard(YhOrgTree yhorgtree,int page,int limit,String guishu,String yishi,String state,String name,String paixuming,String fangfa,String states);
	List<YhUserReginfo> derivexlscard(YhOrgTree yhorgtree,String guishu,String yishi,String state,String name,String paixuming,String fangfa,String states);
	int selectcount(YhOrgTree yhorgtree,String guishu,String yishi,String state,String name,String states);
	List<YhMakeRegiCardInfo> selectalldoctor(YhOrgTree yhorgtree,String guishu,String name);
	int fenpeiyishis(String id, String[] shuzu);
	int zhucefenpei(String id, String[] shuzu) throws ParseException;
	int cardIdActivation(String key,String enrolCardId);
	String selectorgname();
	YhOrgTree selectorgname2();
	int updateOrgName(String orgCode,String orgName);

	String cardIdActivationByDoctor(String enrolCardId);
}
