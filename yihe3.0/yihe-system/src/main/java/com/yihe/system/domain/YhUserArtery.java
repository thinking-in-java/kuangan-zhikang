package com.yihe.system.domain;

import java.util.Date;

public class YhUserArtery {
    private Integer id;

    private Integer sbp;

    private Integer dbp;

    private Integer pulse;

    private Integer avi;

    private Integer api;

    private Integer csbp;

    private Integer capp;

    private String usesrCode;

    private String time;

    private String name;

    private Integer gender;
    private Integer insertcount;
    private String Org_Name;
    private String createdate;

    private Integer isDel;

    private String inserttime;

    private  String dataSource;


    private boolean openNo;

    private String jiedu;

    private String tiwen;

    public String getJiedu() {
        return jiedu;
    }

    public void setJiedu(String jiedu) {
        this.jiedu = jiedu;
    }

    public String getTiwen() {
        return tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getInsertcount() {
		return insertcount;
	}

	public void setInsertcount(Integer insertcount) {
		this.insertcount = insertcount;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getOrg_Name() {
        return Org_Name;
    }

    public void setOrg_Name(String org_Name) {
        Org_Name = org_Name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Integer getAvi() {
        return avi;
    }

    public void setAvi(Integer avi) {
        this.avi = avi;
    }

    public Integer getApi() {
        return api;
    }

    public void setApi(Integer api) {
        this.api = api;
    }

    public Integer getCsbp() {
        return csbp;
    }

    public void setCsbp(Integer csbp) {
        this.csbp = csbp;
    }

    public Integer getCapp() {
        return capp;
    }

    public void setCapp(Integer capp) {
        this.capp = capp;
    }

    public String getUsesrCode() {
        return usesrCode;
    }

    public void setUsesrCode(String usesrCode) {
        this.usesrCode = usesrCode == null ? null : usesrCode.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}