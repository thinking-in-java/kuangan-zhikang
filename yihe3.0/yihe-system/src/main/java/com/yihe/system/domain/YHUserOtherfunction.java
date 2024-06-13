package com.yihe.system.domain;

public class YHUserOtherfunction {
    private Integer otherid;

    private String urineMicroprotein;

    private String glycosylatedHemoglobin;

    private String hcy;

    private String userId;

    private String inserttime;

    private Integer isDel;

    private String dataSource;

    public Integer getOtherid() {
        return otherid;
    }

    public void setOtherid(Integer otherid) {
        this.otherid = otherid;
    }

    public String getUrineMicroprotein() {
        return urineMicroprotein;
    }

    public void setUrineMicroprotein(String urineMicroprotein) {
        this.urineMicroprotein = urineMicroprotein == null ? null : urineMicroprotein.trim();
    }

    public String getGlycosylatedHemoglobin() {
        return glycosylatedHemoglobin;
    }

    public void setGlycosylatedHemoglobin(String glycosylatedHemoglobin) {
        this.glycosylatedHemoglobin = glycosylatedHemoglobin == null ? null : glycosylatedHemoglobin.trim();
    }

    public String getHcy() {
        return hcy;
    }

    public void setHcy(String hcy) {
        this.hcy = hcy == null ? null : hcy.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime == null ? null : inserttime.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }
}