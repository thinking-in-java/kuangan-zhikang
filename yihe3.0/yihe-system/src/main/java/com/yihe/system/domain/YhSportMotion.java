package com.yihe.system.domain;

public class YhSportMotion {
	private Integer id;

    private Integer sportType;

    private Integer sportShow;

    private String sportName;

    private String sportSteps;

    private String sportFell;

    private String sportBreath;

    private String followSecond;

    private String sportImg;

    private String sportGif;

    private String sportVideo;

    private String sportContent;

    private String sportExplainImg;

    private Integer isDel;

    private String kcal;

    private Integer hot;

    private String standby;

    private String sportClassity;

    private String sportPlace;

    private String sportGoals;

    private Integer sportStrength;

    private String sportVideotime;

    private String sportTotalkcal;
    
    private int sky;
    
    private String minute;
    
    private String kilocalorie;

    private int whether;
    
    private String sportNumberduration;
    
    private int addnum;
    
    private String userCode;
    
    private int quanxian;
    
    
    
	public int getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(int quanxian) {
		this.quanxian = quanxian;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getAddnum() {
		return addnum;
	}

	public void setAddnum(Integer addnum) {
		this.addnum = addnum;
	}

	public String getSportNumberduration() {
		return sportNumberduration;
	}

	public void setSportNumberduration(String sportNumberduration) {
		this.sportNumberduration = sportNumberduration;
	}

	public Integer getSportShow() {
		return sportShow;
	}

	public void setSportShow(Integer sportShow) {
		this.sportShow = sportShow;
	}

	public String getSportGif() {
		return sportGif;
	}

	public void setSportGif(String sportGif) {
		this.sportGif = sportGif;
	}

	public String getSportContent() {
		return sportContent;
	}

	public void setSportContent(String sportContent) {
		this.sportContent = sportContent;
	}

	public String getSportExplainImg() {
		return sportExplainImg;
	}

	public void setSportExplainImg(String sportExplainImg) {
		this.sportExplainImg = sportExplainImg;
	}

	public String getSportClassity() {
		return sportClassity;
	}

	public void setSportClassity(String sportClassity) {
		this.sportClassity = sportClassity;
	}

	public String getSportPlace() {
		return sportPlace;
	}

	public void setSportPlace(String sportPlace) {
		this.sportPlace = sportPlace;
	}

	public String getSportGoals() {
		return sportGoals;
	}

	public void setSportGoals(String sportGoals) {
		this.sportGoals = sportGoals;
	}

	public Integer getSportStrength() {
		return sportStrength;
	}

	public void setSportStrength(Integer sportStrength) {
		this.sportStrength = sportStrength;
	}

	public String getSportVideotime() {
		return sportVideotime;
	}

	public void setSportVideotime(String sportVideotime) {
		this.sportVideotime = sportVideotime;
	}

	public String getSportTotalkcal() {
		return sportTotalkcal;
	}

	public void setSportTotalkcal(String sportTotalkcal) {
		this.sportTotalkcal = sportTotalkcal;
	}

	public int getWhether() {
		return whether;
	}

	public void setWhether(int whether) {
		this.whether = whether;
	}

	public int getSky() {
		return sky;
	}

	public void setSky(int sky) {
		this.sky = sky;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getKilocalorie() {
		return kilocalorie;
	}

	public void setKilocalorie(String kilocalorie) {
		this.kilocalorie = kilocalorie;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSportType() {
        return sportType;
    }

    public void setSportType(Integer sportType) {
        this.sportType = sportType;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName == null ? null : sportName.trim();
    }

    public String getSportSteps() {
        return sportSteps;
    }

    public void setSportSteps(String sportSteps) {
        this.sportSteps = sportSteps == null ? null : sportSteps.trim();
    }

    public String getSportFell() {
        return sportFell;
    }

    public void setSportFell(String sportFell) {
        this.sportFell = sportFell == null ? null : sportFell.trim();
    }

    public String getSportBreath() {
        return sportBreath;
    }

    public void setSportBreath(String sportBreath) {
        this.sportBreath = sportBreath == null ? null : sportBreath.trim();
    }

    public String getFollowSecond() {
        return followSecond;
    }

    public void setFollowSecond(String followSecond) {
        this.followSecond = followSecond == null ? null : followSecond.trim();
    }

    public String getSportImg() {
        return sportImg;
    }

    public void setSportImg(String sportImg) {
        this.sportImg = sportImg == null ? null : sportImg.trim();
    }

    public String getSportVideo() {
        return sportVideo;
    }

    public void setSportVideo(String sportVideo) {
        this.sportVideo = sportVideo == null ? null : sportVideo.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal == null ? null : kcal.trim();
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }
}