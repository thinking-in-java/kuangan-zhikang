package com.yihe.system.domain;

import java.text.ParseException;
public class YHUserEcg {
    private Integer uEcgId;

    private String testHj;

    private Integer sr;

    private Integer tr;

    private Integer pulse;

    private Integer resultcode;

    private String xdValue;

    private String entryTime;

    private Integer isDel;

    private Integer eUserId;

    private String pigPath;

    private String resurtText;

    private String dataSource;
    
    public YHUserEcg(Integer uEcgId, String testHj, Integer sr, Integer tr, Integer pulse, Integer resultcode,
			String xdValue, String entryTime, Integer isDel, Integer eUserId, String pigPath, String resurtText,
			String dataSource) {
		
		this.uEcgId = uEcgId;
		this.testHj = testHj;
		this.sr = sr;
		this.tr = tr;
		this.pulse = pulse;
		this.resultcode = resultcode;
		this.xdValue = xdValue;
		this.entryTime = entryTime;
		this.isDel = isDel;
		this.eUserId = eUserId;
		this.pigPath = pigPath;
		this.resurtText = resurtText;
		this.dataSource = dataSource;
	}
	
	public YHUserEcg() {
		 
	}

	public YHUserEcg(Integer tr2, Integer sr2, String measureTime, Integer is_Del, Integer e_User_Id,
			Integer pulses, String test_hj, String deviceType, Integer resultCode2, String xd_Value, String imageurl,
			String resurt_text) throws ParseException {
		this.testHj = test_hj;
		this.sr = sr2;
		this.tr = tr2;
		this.pulse = pulses;
		this.resultcode = resultCode2;
		this.xdValue = xd_Value;
		 
		this.entryTime = measureTime;
		
		this.isDel = is_Del;
		this.eUserId = e_User_Id;
		this.pigPath = imageurl;
		this.resurtText = resurt_text;
		this.dataSource = deviceType;
	}
	public YHUserEcg(Integer tr2, Integer sr2, String measureTime, Integer is_Del, Integer e_User_Id,
			 String test_hj, String deviceType, Integer resultCode2, String xd_Value, String imageurl,
			String resurt_text) throws ParseException {
		this.testHj = test_hj;
		this.sr = sr2;
		this.tr = tr2;
		this.resultcode = resultCode2;
		this.xdValue = xd_Value;
		 
		this.entryTime = measureTime;
		
		this.isDel = is_Del;
		this.eUserId = e_User_Id;
		this.pigPath = imageurl;
		this.resurtText = resurt_text;
		this.dataSource = deviceType;
	}

	

    public Integer getuEcgId() {
        return uEcgId;
    }

    public void setuEcgId(Integer uEcgId) {
        this.uEcgId = uEcgId;
    }

    public String getTestHj() {
        return testHj;
    }

    public void setTestHj(String testHj) {
        this.testHj = testHj == null ? null : testHj.trim();
    }

    public Integer getSr() {
        return sr;
    }

    public void setSr(Integer sr) {
        this.sr = sr;
    }

    public Integer getTr() {
        return tr;
    }

    public void setTr(Integer tr) {
        this.tr = tr;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Integer getResultcode() {
        return resultcode;
    }

    public void setResultcode(Integer resultcode) {
        this.resultcode = resultcode;
    }

    public String getXdValue() {
        return xdValue;
    }

    public void setXdValue(String xdValue) {
        this.xdValue = xdValue == null ? null : xdValue.trim();
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

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getPigPath() {
        return pigPath;
    }

    public void setPigPath(String pigPath) {
        this.pigPath = pigPath == null ? null : pigPath.trim();
    }

    public String getResurtText() {
        return resurtText;
    }

    public void setResurtText(String resurtText) {
        this.resurtText = resurtText == null ? null : resurtText.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }
}