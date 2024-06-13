package com.yihe.applet.api.domain;

import java.util.Date;
import java.util.List;

public class YhHealthLectureInfoApp {
    private Integer infoid;

    private String tips;

    private Integer categoryId;

    private Integer typeid;

    private String thumbnailUrl;

    private String listboxUrl;

    private String detailContentUrl;

    private String videoUrl;

    private String audioUrl;

    private Date updateDate;

    private String title;

    private String author;

    private String instruction;

    private Integer readCount;

    private String addinfotime;

    private String usercode;

    private Integer state;
    
    private Integer looker;
    
    private Integer sleeps;
    
    
    
    public Integer getSleeps() {
		return sleeps;
	}

	public void setSleeps(Integer sleeps) {
		this.sleeps = sleeps;
	}

	public Integer getLooker() {
		return looker;
	}

	public void setLooker(Integer looker) {
		this.looker = looker;
	}

	/**
     * 评论集合
     */
    private List<YhHealthLectureCommentApp>  comList;
    
    /**
     * 评论集合大于3条
     */
    private Boolean isExceed;
    
    /**
     * 关键字转化为数组
     */
    private String[] tipsStr;
    /**
     * 类型ID，1为每日一课，2为活动
     *
     * @mbg.generated
     */
    private Integer type;
    
    /**
     * 多久之前
     */
    private String addinfotimeStr;
    
    
    
    

    public String getAddinfotimeStr() {
		return addinfotimeStr;
	}

	public void setAddinfotimeStr(String addinfotimeStr) {
		this.addinfotimeStr = addinfotimeStr;
	}

	public List<YhHealthLectureCommentApp> getComList() {
		return comList;
	}

	public void setComList(List<YhHealthLectureCommentApp> comList) {
		this.comList = comList;
	}

	public Boolean getIsExceed() {
		return isExceed;
	}

	public void setIsExceed(Boolean isExceed) {
		this.isExceed = isExceed;
	}

	public String[] getTipsStr() {
		return tipsStr;
	}

	public void setTipsStr(String[] tipsStr) {
		this.tipsStr = tipsStr;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl == null ? null : thumbnailUrl.trim();
    }

    public String getListboxUrl() {
        return listboxUrl;
    }

    public void setListboxUrl(String listboxUrl) {
        this.listboxUrl = listboxUrl == null ? null : listboxUrl.trim();
    }

    public String getDetailContentUrl() {
        return detailContentUrl;
    }

    public void setDetailContentUrl(String detailContentUrl) {
        this.detailContentUrl = detailContentUrl == null ? null : detailContentUrl.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl == null ? null : audioUrl.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction == null ? null : instruction.trim();
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getAddinfotime() {
        return addinfotime;
    }

    public void setAddinfotime(String addinfotime) {
        this.addinfotime = addinfotime == null ? null : addinfotime.trim();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	
}