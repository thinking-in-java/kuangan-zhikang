package com.yihe.system.domain;

public class YHUserBloodfat {
    private Integer uBloodfatId;

    private Float cholesterin;

    private Float tring;

    private Float hdl;

    private Float ldl;

    private String entryTime;

    private Integer isDel;

    private Integer eUserId;

    private String dataSource;

    public Integer getuBloodfatId() {
        return uBloodfatId;
    }

    public void setuBloodfatId(Integer uBloodfatId) {
        this.uBloodfatId = uBloodfatId;
    }

    public Float getCholesterin() {
        return cholesterin;
    }

    public void setCholesterin(Float cholesterin) {
        this.cholesterin = cholesterin;
    }

    public Float getTring() {
        return tring;
    }

    public void setTring(Float tring) {
        this.tring = tring;
    }

    public Float getHdl() {
        return hdl;
    }

    public void setHdl(Float hdl) {
        this.hdl = hdl;
    }

    public Float getLdl() {
        return ldl;
    }

    public void setLdl(Float ldl) {
        this.ldl = ldl;
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }
}