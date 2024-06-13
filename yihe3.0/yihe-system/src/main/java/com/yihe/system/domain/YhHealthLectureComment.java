package com.yihe.system.domain;

import java.io.Serializable;

public class YhHealthLectureComment implements Serializable {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer commentid;

    /**
     * 回复楼中楼编号
     *
     * @mbg.generated
     */
    private Integer pid;

    /**
     * 文章编号
     *
     * @mbg.generated
     */
    private Integer lectureinfoid;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    private String usercode;

    /**
     *类型(-1:健康课堂,2:健康习惯)
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private String commenttime;

    /**
     * 评论图片
     *
     * @mbg.generated
     */
    private String comimg;

    /**
     * 评论内容
     *
     * @mbg.generated
     */
    private String commentinfo;
    
    private String title;
    
    private String userName;
    
    private int number;
    
    private String orgName;
    
    private String mobile;
    

    private static final long serialVersionUID = 1L;

    
    
    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
        this.commenttime = commenttime;
    }

    public String getComimg() {
        return comimg;
    }

    public void setComimg(String comimg) {
        this.comimg = comimg;
    }

    public String getCommentinfo() {
        return commentinfo;
    }

    public void setCommentinfo(String commentinfo) {
        this.commentinfo = commentinfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentid=").append(commentid);
        sb.append(", pid=").append(pid);
        sb.append(", lectureinfoid=").append(lectureinfoid);
        sb.append(", usercode=").append(usercode);
        sb.append(", status=").append(status);
        sb.append(", commenttime=").append(commenttime);
        sb.append(", comimg=").append(comimg);
        sb.append(", commentinfo=").append(commentinfo);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        YhHealthLectureComment other = (YhHealthLectureComment) that;
        return (this.getCommentid() == null ? other.getCommentid() == null : this.getCommentid().equals(other.getCommentid()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getLectureinfoid() == null ? other.getLectureinfoid() == null : this.getLectureinfoid().equals(other.getLectureinfoid()))
            && (this.getUsercode() == null ? other.getUsercode() == null : this.getUsercode().equals(other.getUsercode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCommenttime() == null ? other.getCommenttime() == null : this.getCommenttime().equals(other.getCommenttime()))
            && (this.getComimg() == null ? other.getComimg() == null : this.getComimg().equals(other.getComimg()))
            && (this.getCommentinfo() == null ? other.getCommentinfo() == null : this.getCommentinfo().equals(other.getCommentinfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCommentid() == null) ? 0 : getCommentid().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getLectureinfoid() == null) ? 0 : getLectureinfoid().hashCode());
        result = prime * result + ((getUsercode() == null) ? 0 : getUsercode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCommenttime() == null) ? 0 : getCommenttime().hashCode());
        result = prime * result + ((getComimg() == null) ? 0 : getComimg().hashCode());
        result = prime * result + ((getCommentinfo() == null) ? 0 : getCommentinfo().hashCode());
        return result;
    }
}