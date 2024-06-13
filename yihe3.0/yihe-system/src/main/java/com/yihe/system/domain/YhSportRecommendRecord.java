package com.yihe.system.domain;

import java.util.Date;

public class YhSportRecommendRecord {
    private Integer id;

    private Date recordData;

    private String userCode;

    private String diseaseid;

    private String setmealid;

    private String number;

    private String finishid;

    private Integer recordType;

    private Integer recordKay;

    private String recordMode;

    private String standby;
    
    private String name;
    
    private String evolve;
    
    private int oneself;
    
    private String sportName;
    

    public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public int getOneself() {
		return oneself;
	}

	public void setOneself(int oneself) {
		this.oneself = oneself;
	}

	public String getEvolve() {
		return evolve;
	}

	public void setEvolve(String evolve) {
		this.evolve = evolve;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRecordData() {
        return recordData;
    }

    public void setRecordData(Date recordData) {
        this.recordData = recordData;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(String diseaseid) {
        this.diseaseid = diseaseid == null ? null : diseaseid.trim();
    }

    public String getSetmealid() {
        return setmealid;
    }

    public void setSetmealid(String setmealid) {
        this.setmealid = setmealid == null ? null : setmealid.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getFinishid() {
        return finishid;
    }

    public void setFinishid(String finishid) {
        this.finishid = finishid == null ? null : finishid.trim();
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getRecordKay() {
        return recordKay;
    }

    public void setRecordKay(Integer recordKay) {
        this.recordKay = recordKay;
    }

    public String getRecordMode() {
        return recordMode;
    }

    public void setRecordMode(String recordMode) {
        this.recordMode = recordMode == null ? null : recordMode.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }
}