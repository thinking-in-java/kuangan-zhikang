package com.yihe.applet.api.domain;

import java.util.Date;
import java.util.List;

public class YhUserSportChallengeApp {
    private Integer chId;

    private String userCode;

    private Date chBeginTime;

    private Date chFiaishTime;

    private String chAverageStep;

    private String chWhether;

    private String chReach;

    private String backups;

    private String backups1;

    private String backups2;

    private String name;
    
    private String bfb;
    
    private String postCode;
    
    private List<SportEncouraeImgVojo> img;
    
    private int gender;
    
    
    public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getBfb() {
		return bfb;
	}

	public void setBfb(String bfb) {
		this.bfb = bfb;
	}

	public List<SportEncouraeImgVojo> getImg() {
		return img;
	}

	public void setImg(List<SportEncouraeImgVojo> img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getChId() {
        return chId;
    }

    public void setChId(Integer chId) {
        this.chId = chId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Date getChBeginTime() {
        return chBeginTime;
    }

    public void setChBeginTime(Date chBeginTime) {
        this.chBeginTime = chBeginTime;
    }

    public Date getChFiaishTime() {
        return chFiaishTime;
    }

    public void setChFiaishTime(Date chFiaishTime) {
        this.chFiaishTime = chFiaishTime;
    }

    public String getChAverageStep() {
        return chAverageStep;
    }

    public void setChAverageStep(String chAverageStep) {
        this.chAverageStep = chAverageStep == null ? null : chAverageStep.trim();
    }

    public String getChWhether() {
        return chWhether;
    }

    public void setChWhether(String chWhether) {
        this.chWhether = chWhether == null ? null : chWhether.trim();
    }

    public String getChReach() {
        return chReach;
    }

    public void setChReach(String chReach) {
        this.chReach = chReach == null ? null : chReach.trim();
    }

    public String getBackups() {
        return backups;
    }

    public void setBackups(String backups) {
        this.backups = backups == null ? null : backups.trim();
    }

    public String getBackups1() {
        return backups1;
    }

    public void setBackups1(String backups1) {
        this.backups1 = backups1 == null ? null : backups1.trim();
    }

    public String getBackups2() {
        return backups2;
    }

    public void setBackups2(String backups2) {
        this.backups2 = backups2 == null ? null : backups2.trim();
    }
}