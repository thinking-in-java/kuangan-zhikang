package com.yihe.system.domain;

public class YHUserLiverfunction {
    private Integer liverid;

    private String guBing;

    private String milletStraw;

    private String serumAlbumin;

    private String serumGlobulin;

    private Integer userId;

    private String inserttime;

    private Integer isDel;

    private String dataSource;

    public Integer getLiverid() {
        return liverid;
    }

    public void setLiverid(Integer liverid) {
        this.liverid = liverid;
    }

    public String getGuBing() {
        return guBing;
    }

    public void setGuBing(String guBing) {
        this.guBing = guBing == null ? null : guBing.trim();
    }

    public String getMilletStraw() {
        return milletStraw;
    }

    public void setMilletStraw(String milletStraw) {
        this.milletStraw = milletStraw == null ? null : milletStraw.trim();
    }

    public String getSerumAlbumin() {
        return serumAlbumin;
    }

    public void setSerumAlbumin(String serumAlbumin) {
        this.serumAlbumin = serumAlbumin == null ? null : serumAlbumin.trim();
    }

    public String getSerumGlobulin() {
        return serumGlobulin;
    }

    public void setSerumGlobulin(String serumGlobulin) {
        this.serumGlobulin = serumGlobulin == null ? null : serumGlobulin.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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