package com.yihe.applet.api.domain;

public class YhPainResolveApp {
    private Integer resolveId;

    private Byte painId;

    private Integer resolveType;

    private String resolveEstimate;

    private String teachVideo;

    public Integer getResolveId() {
        return resolveId;
    }

    public void setResolveId(Integer resolveId) {
        this.resolveId = resolveId;
    }

    public Byte getPainId() {
        return painId;
    }

    public void setPainId(Byte painId) {
        this.painId = painId;
    }

    public Integer getResolveType() {
        return resolveType;
    }

    public void setResolveType(Integer resolveType) {
        this.resolveType = resolveType;
    }

    public String getResolveEstimate() {
        return resolveEstimate;
    }

    public void setResolveEstimate(String resolveEstimate) {
        this.resolveEstimate = resolveEstimate == null ? null : resolveEstimate.trim();
    }

    public String getTeachVideo() {
        return teachVideo;
    }

    public void setTeachVideo(String teachVideo) {
        this.teachVideo = teachVideo == null ? null : teachVideo.trim();
    }
}