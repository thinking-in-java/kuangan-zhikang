package com.yihe.applet.api.domain;

public class YhPressureAssessmentApp {
    private Integer paId;

    private String paQue;

    private Integer paDel;

    public Integer getPaId() {
        return paId;
    }

    public void setPaId(Integer paId) {
        this.paId = paId;
    }

    public String getPaQue() {
        return paQue;
    }

    public void setPaQue(String paQue) {
        this.paQue = paQue == null ? null : paQue.trim();
    }

    public Integer getPaDel() {
        return paDel;
    }

    public void setPaDel(Integer paDel) {
        this.paDel = paDel;
    }
}