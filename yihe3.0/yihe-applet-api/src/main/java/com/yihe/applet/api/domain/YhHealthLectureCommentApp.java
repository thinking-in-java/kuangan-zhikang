package com.yihe.applet.api.domain;

import java.util.Date;
import java.util.List;

public class YhHealthLectureCommentApp {
	private  Long prid;

    private Integer commentid;
    private Integer id;
    private Integer pid;
    private Integer gender;
    private Integer lectureinfoid;
    private Integer typeid;
    private Date sendDate;
    	
    private String usercode;

    private Byte status;

    private String commenttime;

    private String comimg;

    private String commentinfo;
    
    private String defaultImg;
    
    private Boolean isRead;
    
    private Integer toServiceId; 
    
    private Integer stop_smoking_day;

    private Integer like_num;

    private Integer is_like;
    
    
    private String dotnum;
    
    
    private boolean  whetherwo;

	public Long getPrid() {
		return prid;
	}

	public void setPrid(Long prid) {
		this.prid = prid;
	}

	public String getDotnum() {
		return dotnum;
	}

	public void setDotnum(String dotnum) {
		this.dotnum = dotnum;
	}

	public boolean isWhetherwo() {
		return whetherwo;
	}

	public void setWhetherwo(boolean whetherwo) {
		this.whetherwo = whetherwo;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getIs_like() {
		return is_like;
	}

	public void setIs_like(Integer is_like) {
		this.is_like = is_like;
	}

	public Integer getStop_smoking_day() {
		return stop_smoking_day;
	}

	public void setStop_smoking_day(Integer stop_smoking_day) {
		this.stop_smoking_day = stop_smoking_day;
	}

	public Integer getLike_num() {
		return like_num;
	}

	public void setLike_num(Integer like_num) {
		this.like_num = like_num;
	}

	public Integer getToServiceId() {
		return toServiceId;
	}

	public void setToServiceId(Integer toServiceId) {
		this.toServiceId = toServiceId;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public String getDefaultImg() {
		return defaultImg;
	}

	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * 楼中楼
	 */
	private List<YhHealthLectureCommentApp> comsList;
	
	/**
     * 多久之前
     */
    private String addinfotimeStr;

	/**
	 * 楼中楼数量大于3
	 * 
	 * @return
	 */
	private Boolean isExceed;

	private String name;

	private String headImg;
	
	
	

    public String getAddinfotimeStr() {
		return addinfotimeStr;
	}

	public void setAddinfotimeStr(String addinfotimeStr) {
		this.addinfotimeStr = addinfotimeStr;
	}

	public List<YhHealthLectureCommentApp> getComsList() {
		return comsList;
	}

	public void setComsList(List<YhHealthLectureCommentApp> comsList) {
		this.comsList = comsList;
	}

	public Boolean getIsExceed() {
		return isExceed;
	}

	public void setIsExceed(Boolean isExceed) {
		this.isExceed = isExceed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLectureinfoid() {
        return lectureinfoid;
    }

    public void setLectureinfoid(Integer lectureinfoid) {
        this.lectureinfoid = lectureinfoid;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime == null ? null : commenttime.trim();
    }

    public String getComimg() {
        return comimg;
    }

    public void setComimg(String comimg) {
        this.comimg = comimg == null ? null : comimg.trim();
    }

    public String getCommentinfo() {
        return commentinfo;
    }

    public void setCommentinfo(String commentinfo) {
        this.commentinfo = commentinfo == null ? null : commentinfo.trim();
    }
}