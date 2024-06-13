package com.yihe.applet.api.domain;

public class YhUserPersonagePharmacyApp {
    private Integer id;

    private String userCode;

    private String drugname;

    private String usagefa;

    private String dosage;

    private String timedata;

    private String comply;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname == null ? null : drugname.trim();
    }

    public String getUsagefa() {
        return usagefa;
    }

    public void setUsagefa(String usagefa) {
        this.usagefa = usagefa == null ? null : usagefa.trim();
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage == null ? null : dosage.trim();
    }

    public String getTimedata() {
        return timedata;
    }

    public void setTimedata(String timedata) {
        this.timedata = timedata == null ? null : timedata.trim();
    }

    public String getComply() {
        return comply;
    }

    public void setComply(String comply) {
        this.comply = comply == null ? null : comply.trim();
    }
}