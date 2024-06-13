package com.yihe.system.domain;

public class YhUserPersonageSymptom {
    private Integer id;

    private String personageName;

    private String userCode;

    private String standby;
    private String addtime;
    private String updatetime;
    

    public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonageName() {
        return personageName;
    }

    public void setPersonageName(String personageName) {
        this.personageName = personageName == null ? null : personageName.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }
}