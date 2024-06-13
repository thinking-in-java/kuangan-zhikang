package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhHealthLectureInfo implements Serializable {
    private Integer infoid;

    /**
     * 关键词，多个用分号隔开
     *
     * @mbg.generated
     */
    private String tips;

    /**
     * 类别ID
     *
     * @mbg.generated
     */
    private Integer categoryId;

    /**
     * 类型ID,如果type为2，这个就是活动的id
     *
     * @mbg.generated
     */
    private Integer typeid;
    /**
     * 缩略图URL
     *
     * @mbg.generated
     */
    private String thumbnailUrl;

    /**
     * 列表图
     *
     * @mbg.generated
     */
    private String listboxUrl;

    /**
     * 详情地址
     *
     * @mbg.generated
     */
    private String detailContentUrl;

    /**
     * 视频播放地址
     *
     * @mbg.generated
     */
    private String videoUrl;

    /**
     * 语音播放地址
     *
     * @mbg.generated
     */
    private String audioUrl;

    /**
     * 开播日期
     *
     * @mbg.generated
     */
    private Date updateDate;

    /**
     * 标题
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 作者
     *
     * @mbg.generated
     */
    private String author;

    /**
     * 概要
     *
     * @mbg.generated
     */
    private String instruction;

    /**
     * 点击数
     *
     * @mbg.generated
     */
    private Integer readCount;

    /**
     * 新增时间
     *
     * @mbg.generated
     */
    private String addinfotime;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    private String usercode;
    
    /**
     * (1,发布2，草稿)
     *
     * @mbg.generated
     */
    private Integer state;
    

    private int num;
    
    private String name;
    
    private String typeName;

    private String update;
    
    private String categoryIdda;
    
    private String categoryIdxiao;
    
    private String threelevel;
    
    private String parentId;
    
    private int watch;
    
    private String speaker;
    
    
    private String instructionmd;
    
    
    private int quanxian;
    
    private String userCode;
    
    private static final long serialVersionUID = 1L;

    
    
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

	public String getThreelevel() {
		return threelevel;
	}

	public void setThreelevel(String threelevel) {
		this.threelevel = threelevel;
	}

	public String getInstructionmd() {
		return instructionmd;
	}

	public void setInstructionmd(String instructionmd) {
		this.instructionmd = instructionmd;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public int getWatch() {
		return watch;
	}

	public void setWatch(int watch) {
		this.watch = watch;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCategoryIdda() {
		return categoryIdda;
	}

	public void setCategoryIdda(String categoryIdda) {
		this.categoryIdda = categoryIdda;
	}

	public String getCategoryIdxiao() {
		return categoryIdxiao;
	}

	public void setCategoryIdxiao(String categoryIdxiao) {
		this.categoryIdxiao = categoryIdxiao;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
        this.tips = tips;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

  

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getListboxUrl() {
        return listboxUrl;
    }

    public void setListboxUrl(String listboxUrl) {
        this.listboxUrl = listboxUrl;
    }

    public String getDetailContentUrl() {
        return detailContentUrl;
    }

    public void setDetailContentUrl(String detailContentUrl) {
        this.detailContentUrl = detailContentUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
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
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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
        this.addinfotime = addinfotime;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", infoid=").append(infoid);
        sb.append(", tips=").append(tips);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", type=").append(typeid);
        sb.append(", thumbnailUrl=").append(thumbnailUrl);
        sb.append(", listboxUrl=").append(listboxUrl);
        sb.append(", detailContentUrl=").append(detailContentUrl);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", audioUrl=").append(audioUrl);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", title=").append(title);
        sb.append(", author=").append(author);
        sb.append(", instruction=").append(instruction);
        sb.append(", readCount=").append(readCount);
        sb.append(", addinfotime=").append(addinfotime);
        sb.append(", usercode=").append(usercode);
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
        YhHealthLectureInfo other = (YhHealthLectureInfo) that;
        return (this.getInfoid() == null ? other.getInfoid() == null : this.getInfoid().equals(other.getInfoid()))
            && (this.getTips() == null ? other.getTips() == null : this.getTips().equals(other.getTips()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
            && (this.getThumbnailUrl() == null ? other.getThumbnailUrl() == null : this.getThumbnailUrl().equals(other.getThumbnailUrl()))
            && (this.getListboxUrl() == null ? other.getListboxUrl() == null : this.getListboxUrl().equals(other.getListboxUrl()))
            && (this.getDetailContentUrl() == null ? other.getDetailContentUrl() == null : this.getDetailContentUrl().equals(other.getDetailContentUrl()))
            && (this.getVideoUrl() == null ? other.getVideoUrl() == null : this.getVideoUrl().equals(other.getVideoUrl()))
            && (this.getAudioUrl() == null ? other.getAudioUrl() == null : this.getAudioUrl().equals(other.getAudioUrl()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getInstruction() == null ? other.getInstruction() == null : this.getInstruction().equals(other.getInstruction()))
            && (this.getReadCount() == null ? other.getReadCount() == null : this.getReadCount().equals(other.getReadCount()))
            && (this.getAddinfotime() == null ? other.getAddinfotime() == null : this.getAddinfotime().equals(other.getAddinfotime()))
            && (this.getUsercode() == null ? other.getUsercode() == null : this.getUsercode().equals(other.getUsercode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInfoid() == null) ? 0 : getInfoid().hashCode());
        result = prime * result + ((getTips() == null) ? 0 : getTips().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getThumbnailUrl() == null) ? 0 : getThumbnailUrl().hashCode());
        result = prime * result + ((getListboxUrl() == null) ? 0 : getListboxUrl().hashCode());
        result = prime * result + ((getDetailContentUrl() == null) ? 0 : getDetailContentUrl().hashCode());
        result = prime * result + ((getVideoUrl() == null) ? 0 : getVideoUrl().hashCode());
        result = prime * result + ((getAudioUrl() == null) ? 0 : getAudioUrl().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getInstruction() == null) ? 0 : getInstruction().hashCode());
        result = prime * result + ((getReadCount() == null) ? 0 : getReadCount().hashCode());
        result = prime * result + ((getAddinfotime() == null) ? 0 : getAddinfotime().hashCode());
        result = prime * result + ((getUsercode() == null) ? 0 : getUsercode().hashCode());
        return result;
    }
}