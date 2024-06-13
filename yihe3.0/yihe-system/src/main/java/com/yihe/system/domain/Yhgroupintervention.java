package com.yihe.system.domain;

public class Yhgroupintervention {
    private Integer E_User_Id;
    private String Flag_Dgroup;
    private String name;
    private String birth_date;
    private Integer gender;
    private String last_plan_time;
    private Integer plan_count;
    private String user_code;
    private String execute_date;

    public Integer getE_User_Id() {
        return E_User_Id;
    }

    public void setE_User_Id(Integer e_User_Id) {
        E_User_Id = e_User_Id;
    }

    public String getFlag_Dgroup() {
        return Flag_Dgroup;
    }

    public void setFlag_Dgroup(String flag_Dgroup) {
        Flag_Dgroup = flag_Dgroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLast_plan_time() {
        return last_plan_time;
    }

    public void setLast_plan_time(String last_plan_time) {
        this.last_plan_time = last_plan_time;
    }

    public Integer getPlan_count() {
        return plan_count;
    }

    public void setPlan_count(Integer plan_count) {
        this.plan_count = plan_count;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getExecute_date() {
        return execute_date;
    }

    public void setExecute_date(String execute_date) {
        this.execute_date = execute_date;
    }
}
