package com.yihe.system.domain;

public class YhExportRenalFunction {
    private Integer renalid;
    private String user_id;
    private String creatinine;
    private String urea_nitrogen;
    private String uric_acid;
    private String inertTime;
    private String Org_Name;
    private String name;
    private Integer gender;
    private String User_Code;
    private String Entry_Time;

    @Override
    public String toString() {
        return "YhExportRenalFunction{" +
                "renalid=" + renalid +
                ", user_id='" + user_id + '\'' +
                ", creatinine='" + creatinine + '\'' +
                ", urea_nitrogen='" + urea_nitrogen + '\'' +
                ", uric_acid='" + uric_acid + '\'' +
                ", inertTime='" + inertTime + '\'' +
                ", Org_Name='" + Org_Name + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", User_Code='" + User_Code + '\'' +
                ", Entry_Time='" + Entry_Time + '\'' +
                '}';
    }


    public Integer getRenalid() {
        return renalid;
    }

    public void setRenalid(Integer renalid) {
        this.renalid = renalid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine;
    }

    public String getUrea_nitrogen() {
        return urea_nitrogen;
    }

    public void setUrea_nitrogen(String urea_nitrogen) {
        this.urea_nitrogen = urea_nitrogen;
    }

    public String getUric_acid() {
        return uric_acid;
    }

    public void setUric_acid(String uric_acid) {
        this.uric_acid = uric_acid;
    }

    public String getInertTime() {
        return inertTime;
    }

    public void setInertTime(String inertTime) {
        this.inertTime = inertTime;
    }

    public String getOrg_Name() {
        return Org_Name;
    }

    public void setOrg_Name(String org_Name) {
        Org_Name = org_Name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUser_Code() {
        return User_Code;
    }

    public void setUser_Code(String user_Code) {
        User_Code = user_Code;
    }

    public String getEntry_Time() {
        return Entry_Time;
    }

    public void setEntry_Time(String entry_Time) {
        Entry_Time = entry_Time;
    }
}
