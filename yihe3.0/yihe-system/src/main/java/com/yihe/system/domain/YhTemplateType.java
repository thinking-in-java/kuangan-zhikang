package com.yihe.system.domain;

public class YhTemplateType {
    private Integer tId;

    private Integer tType;

    private String tClassify;

    private String tTemcontent;

    private String tTemplateId;

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public String gettClassify() {
        return tClassify;
    }

    public void settClassify(String tClassify) {
        this.tClassify = tClassify == null ? null : tClassify.trim();
    }

    public String gettTemcontent() {
        return tTemcontent;
    }

    public void settTemcontent(String tTemcontent) {
        this.tTemcontent = tTemcontent == null ? null : tTemcontent.trim();
    }

    public String gettTemplateId() {
        return tTemplateId;
    }

    public void settTemplateId(String tTemplateId) {
        this.tTemplateId = tTemplateId == null ? null : tTemplateId.trim();
    }
}