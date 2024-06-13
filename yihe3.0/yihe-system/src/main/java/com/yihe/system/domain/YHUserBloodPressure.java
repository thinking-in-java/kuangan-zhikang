package com.yihe.system.domain;

public class YHUserBloodPressure {
    private Integer uBloodpressureId;

    private Integer sbp;

    private Integer dbp;

    private String entryTime;

    private Integer isDel;

    private String eUserId;

    private Integer xinlv;

    private String testHj;

    private String type;

	public YHUserBloodPressure() {
	}
	
    public YHUserBloodPressure(Integer sbp, Integer dbp, String date1, Integer isDel,
    		String eUserId, Integer xinlv, String testHj, String type) {
		super();
		this.sbp = sbp;
		this.dbp = dbp;
		this.entryTime = date1;
		this.isDel = isDel;
		this.eUserId = eUserId;
		this.xinlv = xinlv;
		this.testHj = testHj;
		this.type = type;
	}

    
    public Integer getuBloodpressureId() {
        return uBloodpressureId;
    }

    public void setuBloodpressureId(Integer uBloodpressureId) {
        this.uBloodpressureId = uBloodpressureId;
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

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime == null ? null : entryTime.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String geteUserId() {
        return eUserId;
    }

    public void seteUserId(String eUserId) {
        this.eUserId = eUserId;
    }

    public Integer getXinlv() {
        return xinlv;
    }

    public void setXinlv(Integer xinlv) {
        this.xinlv = xinlv;
    }

    public String getTestHj() {
        return testHj;
    }

    public void setTestHj(String testHj) {
        this.testHj = testHj == null ? null : testHj.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}