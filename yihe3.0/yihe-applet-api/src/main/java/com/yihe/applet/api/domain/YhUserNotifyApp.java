package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserNotifyApp {
    private Long id;

    private String title;

    private String content;
    
    private String name;

    private String userId;

    private Boolean isRead;

    private Date sendDate;

    private Date readDate;

    private Boolean type;

    private Integer toServiceId;

    private Integer newsId;

    private Integer lectureinfoid;

    private String standby1;

    private Integer standby2;
    
    private Integer tyepe;

    
    

  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTyepe() {
		return tyepe;
	}

	public void setTyepe(Integer tyepe) {
		this.tyepe = tyepe;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getToServiceId() {
        return toServiceId;
    }

    public void setToServiceId(Integer toServiceId) {
        this.toServiceId = toServiceId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getLectureinfoid() {
        return lectureinfoid;
    }

    public void setLectureinfoid(Integer lectureinfoid) {
        this.lectureinfoid = lectureinfoid;
    }

    public String getStandby1() {
        return standby1;
    }

    public void setStandby1(String standby1) {
        this.standby1 = standby1 == null ? null : standby1.trim();
    }

    public Integer getStandby2() {
        return standby2;
    }

    public void setStandby2(Integer standby2) {
        this.standby2 = standby2;
    }

    @Override
    public String toString() {
        return "YhUserNotifyApp{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", isRead=" + isRead +
                ", sendDate=" + sendDate +
                ", readDate=" + readDate +
                ", type=" + type +
                ", toServiceId=" + toServiceId +
                ", newsId=" + newsId +
                ", lectureinfoid=" + lectureinfoid +
                ", standby1='" + standby1 + '\'' +
                ", standby2=" + standby2 +
                ", tyepe=" + tyepe +
                '}';
    }
}