package com.yihe.system.domain;

public class Interventiongrouping {
    //用户code
    private String user_code;
    //用户名字
    private String name;
    private String user_name;
    //生日
    private String birth_date;
    //机构code
    private String Org_Code;
    //机构名字
    private String Org_name;
    //分组id
    private Integer U_Docgroup_Id;
    //分组名字id
    private String Flag_Dgroup;
    //分组名字
    private String Docgroup;
    //干预计划
    private String Interventionprograms;
    //性别
    private Integer gender;

    //年龄
    private Integer agemin;
    private Integer agemax;

    //BMI
    private Integer bmimin;
    private Integer bmimax;
    //每页多少行
    private Integer limit;
    //开始行数
    private Integer start;
    //总共多少数据
    private Integer count;
    //计划数
    private Integer plan_count;
    //最后时间
    private String last_plan_time;
    //第几页
    private Integer page;
    //父机构;
    private String Super_Org_Code;

    //干预分组id

    private String dis;

    //管理等级

    private String manageli;

    //大于年龄
    private String minage;

    private String maxage;

    //是否有干预计划

    private Integer isplan;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getIsplan() {
        return isplan;
    }

    public void setIsplan(Integer isplan) {
        this.isplan = isplan;
    }

    public String getMinage() {
        return minage;
    }

    public void setMinage(String minage) {
        this.minage = minage;
    }

    public String getMaxage() {
        return maxage;
    }

    public void setMaxage(String maxage) {
        this.maxage = maxage;
    }

    public String getManageli() {
        return manageli;
    }

    public void setManageli(String manageli) {
        this.manageli = manageli;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getSuper_Org_Code() {
        return Super_Org_Code;
    }

    public void setSuper_Org_Code(String super_Org_Code) {
        Super_Org_Code = super_Org_Code;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPlan_count() {
        return plan_count;
    }

    public void setPlan_count(Integer plan_count) {
        this.plan_count = plan_count;
    }

    public String getLast_plan_time() {
        return last_plan_time;
    }

    public void setLast_plan_time(String last_plan_time) {
        this.last_plan_time = last_plan_time;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
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

    public String getOrg_Code() {
        return Org_Code;
    }

    public void setOrg_Code(String org_Code) {
        Org_Code = org_Code;
    }

    public String getOrg_name() {
        return Org_name;
    }

    public void setOrg_name(String org_name) {
        Org_name = org_name;
    }

    public Integer getU_Docgroup_Id() {
        return U_Docgroup_Id;
    }

    public void setU_Docgroup_Id(Integer u_Docgroup_Id) {
        U_Docgroup_Id = u_Docgroup_Id;
    }

    public String getFlag_Dgroup() {
        return Flag_Dgroup;
    }

    public void setFlag_Dgroup(String flag_Dgroup) {
        Flag_Dgroup = flag_Dgroup;
    }

    public String getDocgroup() {
        return Docgroup;
    }

    public void setDocgroup(String docgroup) {
        Docgroup = docgroup;
    }

    public String getInterventionprograms() {
        return Interventionprograms;
    }

    public void setInterventionprograms(String interventionprograms) {
        Interventionprograms = interventionprograms;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAgemin() {
        return agemin;
    }

    public void setAgemin(Integer agemin) {
        this.agemin = agemin;
    }

    public Integer getAgemax() {
        return agemax;
    }

    public void setAgemax(Integer agemax) {
        this.agemax = agemax;
    }

    public Integer getBmimin() {
        return bmimin;
    }

    public void setBmimin(Integer bmimin) {
        this.bmimin = bmimin;
    }

    public Integer getBmimax() {
        return bmimax;
    }

    public void setBmimax(Integer bmimax) {
        this.bmimax = bmimax;
    }
}
