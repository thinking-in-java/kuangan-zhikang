package com.yihe.system.mapper;

import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhUserSettingMapper {
	YhJzSysSms selectyhjzsyssms();
	YhSysConfig selectyhsysconfig();
	int insertsms(YhJzSysSms sms);
	int updatasms(YhJzSysSms sms);
	int insertsysconfig(YhSysConfig yhsysconfig);
	int updatasysconfig(YhSysConfig yhsysconfig);
	String selectorgcode(String name);
	List<YhOrgTree> yhorgtree(String OrgCode);
	List<YhOrgTree> yhorgtreeparent();
	List<YhUserAdminInfo> selectalladmin();
	List<YhMakeRegiCardInfo> selectrefincard(@Param("yhorgtree") YhOrgTree yhorgtree,@Param("page")int page,@Param("limits")int limits,@Param("guishu")String guishu,@Param("yishi")String yishi,@Param("state")String state,@Param("name")String name,@Param("paixuming")String paixuming,@Param("fangfa")String fangfa,@Param("states")String states);
	List<YhUserReginfo> derivexlscard(@Param("yhorgtree") YhOrgTree yhorgtree,@Param("guishu")String guishu,@Param("yishi")String yishi,@Param("state")String state,@Param("name")String name,@Param("paixuming")String paixuming,@Param("fangfa")String fangfa,@Param("states")String states);
	int selectcount(@Param("yhorgtree") YhOrgTree yhorgtree,@Param("guishu")String guishu,@Param("yishi")String yishi,@Param("state")String state,@Param("name")String name,@Param("states")String states);
	List<YhMakeRegiCardInfo> selectalldoctor(@Param("yhorgtree") YhOrgTree yhorgtree,@Param("guishu")String guishu,@Param("name")String name);
	int fenpeiyishi(@Param("id")Integer id,@Param("name")String name);
	int zhucefenpei(@Param("id")String id,@Param("name") String name);
	int cardIdActivation(@Param("key")String key,@Param("enrolCardId") String enrolCardId);
	int zhucefenpei2(@Param("id")String id,@Param("name") String name);
	String selectdoctorcode(@Param("id")Integer id);
	String selectorgname();
	
	YhOrgTree selectorgname2();
	int updateOrgName(@Param("orgCode") String orgCode,@Param("orgName") String orgName);
	String befortime(String cardid);
	String nowtime(String orgcode);
	int selecttotalplan(@Param("usercode")String usercode);
	String selectusercode(@Param("id")String id);
	int updatettotalplan(@Param("usercode")String usercode,@Param("docotrcode")String docotrcode);
	int updatettotalplan1(@Param("usercode")String usercode,@Param("docotrcode")String docotrcode);
	Integer changetime(@Param("cardid")String cardid,@Param("time") String time);
}
