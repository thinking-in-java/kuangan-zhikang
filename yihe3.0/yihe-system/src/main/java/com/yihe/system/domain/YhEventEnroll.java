package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhEventEnroll implements Serializable {
    /**
     * 活动ID,
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 活动名
     *
     * @mbg.generated
     */
    private String eventName;

    /**
     * 活动内容
     *
     * @mbg.generated
     */
    private String eventContent;

    /**
     * 主办方
     *
     * @mbg.generated
     */
    private String provider;

    /**
     * 活动发布日期
     *
     * @mbg.generated
     */
    private Date publicDate;

    /**
     * 活动报名结束日期
     *
     * @mbg.generated
     */
    private Date registerEndDate;

    /**
     * 活动开始日期
     *
     * @mbg.generated
     */
    private Date startDate;

    /**
     * 总报名人数
     *
     * @mbg.generated
     */
    private Integer total;

    /**
     * 活动结束日期
     *
     * @mbg.generated
     */
    private Date endDate;

    /**
     * 活动注意事项及规则
     *
     * @mbg.generated
     */
    private String eventRule;

    /**
     * 活动举办地点
     *
     * @mbg.generated
     */
    private String eventAddr;

    /**
     * 活动图片
     *
     * @mbg.generated
     */
    private String eventImg;

    /**
     * 状态（1，已发布 2，草稿）
     *
     * @mbg.generated
     */
    private Integer eventType;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 属于哪个机构发布的精彩活动
     *
     * @mbg.generated
     */
    private String orgCode;
    
    private String shij;
    
    private String eventContentmd;

    private static final long serialVersionUID = 1L;

    
    
    public String getEventContentmd() {
		return eventContentmd;
	}

	public void setEventContentmd(String eventContentmd) {
		this.eventContentmd = eventContentmd;
	}

	public String getShij() {
		return shij;
	}

	public void setShij(String shij) {
		this.shij = shij;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public Date getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(Date registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEventRule() {
        return eventRule;
    }

    public void setEventRule(String eventRule) {
        this.eventRule = eventRule;
    }

    public String getEventAddr() {
        return eventAddr;
    }

    public void setEventAddr(String eventAddr) {
        this.eventAddr = eventAddr;
    }

    public String getEventImg() {
        return eventImg;
    }

    public void setEventImg(String eventImg) {
        this.eventImg = eventImg;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", eventName=").append(eventName);
        sb.append(", eventContent=").append(eventContent);
        sb.append(", provider=").append(provider);
        sb.append(", publicDate=").append(publicDate);
        sb.append(", registerEndDate=").append(registerEndDate);
        sb.append(", startDate=").append(startDate);
        sb.append(", total=").append(total);
        sb.append(", endDate=").append(endDate);
        sb.append(", eventRule=").append(eventRule);
        sb.append(", eventAddr=").append(eventAddr);
        sb.append(", eventImg=").append(eventImg);
        sb.append(", eventType=").append(eventType);
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
        YhEventEnroll other = (YhEventEnroll) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEventName() == null ? other.getEventName() == null : this.getEventName().equals(other.getEventName()))
            && (this.getEventContent() == null ? other.getEventContent() == null : this.getEventContent().equals(other.getEventContent()))
            && (this.getProvider() == null ? other.getProvider() == null : this.getProvider().equals(other.getProvider()))
            && (this.getPublicDate() == null ? other.getPublicDate() == null : this.getPublicDate().equals(other.getPublicDate()))
            && (this.getRegisterEndDate() == null ? other.getRegisterEndDate() == null : this.getRegisterEndDate().equals(other.getRegisterEndDate()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getEventRule() == null ? other.getEventRule() == null : this.getEventRule().equals(other.getEventRule()))
            && (this.getEventAddr() == null ? other.getEventAddr() == null : this.getEventAddr().equals(other.getEventAddr()))
            && (this.getEventImg() == null ? other.getEventImg() == null : this.getEventImg().equals(other.getEventImg()))
            && (this.getEventType() == null ? other.getEventType() == null : this.getEventType().equals(other.getEventType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEventName() == null) ? 0 : getEventName().hashCode());
        result = prime * result + ((getEventContent() == null) ? 0 : getEventContent().hashCode());
        result = prime * result + ((getProvider() == null) ? 0 : getProvider().hashCode());
        result = prime * result + ((getPublicDate() == null) ? 0 : getPublicDate().hashCode());
        result = prime * result + ((getRegisterEndDate() == null) ? 0 : getRegisterEndDate().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getEventRule() == null) ? 0 : getEventRule().hashCode());
        result = prime * result + ((getEventAddr() == null) ? 0 : getEventAddr().hashCode());
        result = prime * result + ((getEventImg() == null) ? 0 : getEventImg().hashCode());
        result = prime * result + ((getEventType() == null) ? 0 : getEventType().hashCode());
        return result;
    }
}