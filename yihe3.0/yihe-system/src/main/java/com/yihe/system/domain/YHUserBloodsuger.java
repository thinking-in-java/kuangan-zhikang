package com.yihe.system.domain;

public class YHUserBloodsuger {
    private Integer uBloodsugerId;

    private Float bs;

    private String entryTime;

    private Integer isDel;

    private String eUserId;

    private String env;

    private String dataSource;

    public Integer getuBloodsugerId() {
        return uBloodsugerId;
    }

    public void setuBloodsugerId(Integer uBloodsugerId) {
        this.uBloodsugerId = uBloodsugerId;
    }

    public Float getBs() {
        return bs;
    }

    public void setBs(Float bs) {
        this.bs = bs;
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

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }
}