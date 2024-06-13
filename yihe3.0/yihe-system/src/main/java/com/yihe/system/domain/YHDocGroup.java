package com.yihe.system.domain;

public class YHDocGroup {
    private Integer uDocgroupId;

    private Integer docid;

    private String gname;
    private String establishTime;

    private Integer userCount;

    private Integer sort;

    //拼接过后的id;
    private String uDocgroupIdJoining;
    //分页
    private Integer limit;
    private Integer page;
    private Integer start;
    private String Usercode;
    private String[] names;
    private String ids;
    //管理等级
    private String leves;
    private String Orgcode;
    private Integer sex;
    private Integer isplan;
    private String maxage;
    private String minage;
    private String name;

    public YHDocGroup(Integer uDocgroupId) {
        this.uDocgroupId = uDocgroupId;
    }

    public YHDocGroup() {
    }

    public String getOrgcode() {
        return Orgcode;
    }

    public void setOrgcode(String orgcode) {
        Orgcode = orgcode;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsplan() {
        return isplan;
    }

    public void setIsplan(Integer isplan) {
        this.isplan = isplan;
    }

    public String getMaxage() {
        return maxage;
    }

    public void setMaxage(String maxage) {
        this.maxage = maxage;
    }

    public String getMinage() {
        return minage;
    }

    public void setMinage(String minage) {
        this.minage = minage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeves() {
        return leves;
    }

    public void setLeves(String leves) {
        this.leves = leves;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUsercode() {
        return Usercode;
    }

    public void setUsercode(String usercode) {
        Usercode = usercode;
    }

    public String getuDocgroupIdJoining() {
        return uDocgroupIdJoining;
    }

    public void setuDocgroupIdJoining(String uDocgroupIdJoining) {
        this.uDocgroupIdJoining = uDocgroupIdJoining;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getEstablishTime() {
		return establishTime;
	}

	public void setEstablishTime(String establishTime) {
		this.establishTime = establishTime;
	}

	public Integer getuDocgroupId() {
        return uDocgroupId;
    }

    public void setuDocgroupId(Integer uDocgroupId) {
        this.uDocgroupId = uDocgroupId;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname == null ? null : gname.trim();
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}