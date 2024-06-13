package com.yihe.system.domain;

import java.io.Serializable;

public class YhServicePage implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer state;
	private Integer page;
	private Integer servicetype;
	private Integer healthstate;
	private String name;
	private String usercode;
	private Integer beginpage;
	private Integer id;
	private Integer returnvisit;
	private String time;
	private String begintime;
	private String endtime;
	private String servicecountent;
	private String acceptsituation;
	private Integer satisfaction;
	private Integer number;
	private String Orgcode;
	private String userphone;
	private String chhooeorgcode;

	private Integer dengdai;
	private Integer zhengzai;
	private Integer wancheng;
	private Integer guanbi;
	private Integer allshuliang;

	private String groupid;
	private String tree;
	private String typeson;

	private String finishi_doctor;

	private String threshold_name;

	private Integer totaltype;

	private  String planname;


	//小程序站内信
	private Integer znx;
	//小程序订阅通知
	private Integer dytz;
	//公众号消息通知
	private Integer gzhxxtz;
	//短信通知
	private Integer dxtz;
	//电话回访
	private Integer dhhf;
	//上门回访
	private Integer smhf;

	public Integer getZnx() {
		return znx;
	}

	public void setZnx(Integer znx) {
		this.znx = znx;
	}

	public Integer getDytz() {
		return dytz;
	}

	public void setDytz(Integer dytz) {
		this.dytz = dytz;
	}

	public Integer getGzhxxtz() {
		return gzhxxtz;
	}

	public void setGzhxxtz(Integer gzhxxtz) {
		this.gzhxxtz = gzhxxtz;
	}

	public Integer getDxtz() {
		return dxtz;
	}

	public void setDxtz(Integer dxtz) {
		this.dxtz = dxtz;
	}

	public Integer getDhhf() {
		return dhhf;
	}

	public void setDhhf(Integer dhhf) {
		this.dhhf = dhhf;
	}

	public Integer getSmhf() {
		return smhf;
	}

	public void setSmhf(Integer smhf) {
		this.smhf = smhf;
	}

	public String getPlanname() {
		return planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public Integer getTotaltype() {
		return totaltype;
	}

	public void setTotaltype(Integer totaltype) {
		this.totaltype = totaltype;
	}

	public String getThreshold_name() {
		return threshold_name;
	}

	public void setThreshold_name(String threshold_name) {
		this.threshold_name = threshold_name;
	}

	public String getFinishi_doctor() {
		return finishi_doctor;
	}

	public void setFinishi_doctor(String finishi_doctor) {
		this.finishi_doctor = finishi_doctor;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

	public String getTypeson() {
		return typeson;
	}

	public void setTypeson(String typeson) {
		this.typeson = typeson;
	}

	public Integer getAllshuliang() {
		return allshuliang;
	}

	public void setAllshuliang(Integer allshuliang) {
		this.allshuliang = allshuliang;
	}

	public Integer getDengdai() {
		return dengdai;
	}

	public void setDengdai(Integer dengdai) {
		this.dengdai = dengdai;
	}

	public Integer getZhengzai() {
		return zhengzai;
	}

	public void setZhengzai(Integer zhengzai) {
		this.zhengzai = zhengzai;
	}

	public Integer getWancheng() {
		return wancheng;
	}

	public void setWancheng(Integer wancheng) {
		this.wancheng = wancheng;
	}

	public Integer getGuanbi() {
		return guanbi;
	}

	public void setGuanbi(Integer guanbi) {
		this.guanbi = guanbi;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getChhooeorgcode() {
		return chhooeorgcode;
	}

	public void setChhooeorgcode(String chhooeorgcode) {
		this.chhooeorgcode = chhooeorgcode;
	}

	public String getOrgcode() {
		return Orgcode;
	}

	public void setOrgcode(String orgcode) {
		Orgcode = orgcode;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(Integer satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getAcceptsituation() {
		return acceptsituation;
	}
	public void setAcceptsituation(String acceptsituation) {
		this.acceptsituation = acceptsituation;
	}
	public String getServicecountent() {
		return servicecountent;
	}
	public void setServicecountent(String servicecountent) {
		this.servicecountent = servicecountent;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getReturnvisit() {
		return returnvisit;
	}
	public void setReturnvisit(Integer returnvisit) {
		this.returnvisit = returnvisit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getServicetype() {
		return servicetype;
	}
	public void setServicetype(Integer servicetype) {
		this.servicetype = servicetype;
	}
	public Integer getHealthstate() {
		return healthstate;
	}
	public void setHealthstate(Integer healthstate) {
		this.healthstate = healthstate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public Integer getBeginpage() {
		return beginpage;
	}
	public void setBeginpage(Integer beginpage) {
		this.beginpage = beginpage;
	}
	
	@Override
	public String toString() {
		return "YhServicePage [state=" + state + ", page=" + page + ", servicetype=" + servicetype + ", healthstate="
				+ healthstate + ", name=" + name + ", usercode=" + usercode + ", beginpage=" + beginpage + ", id=" + id
				+ ", returnvisit=" + returnvisit + ", time=" + time + ", begintime=" + begintime + ", endtime="
				+ endtime + ", servicecountent=" + servicecountent + "]";
	}
	
}
