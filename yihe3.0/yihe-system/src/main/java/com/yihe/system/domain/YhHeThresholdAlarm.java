package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhHeThresholdAlarm implements Serializable {
    private String id;

    private String memberId;

    private String terminalId;

    private String orgId;

    /**
     * type for [pulse, temperature, blood_pressure, blood_sugar]
     *
     * @mbg.generated
     */
    private String type;

    private String recordId;

    private String content;

    private Date time;

    /**
     * 0 -- 未处理
            1 -- 已受理 (正在处理)
            2 -- 已处理 ( 处理完毕)
     *
     * @mbg.generated
     */
    private String status;

    private Date acceptTime;

    private String acceptOperatorId;

    private Date archiveTime;

    private String acceptDetailDescription;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptOperatorId() {
        return acceptOperatorId;
    }

    public void setAcceptOperatorId(String acceptOperatorId) {
        this.acceptOperatorId = acceptOperatorId;
    }

    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    public String getAcceptDetailDescription() {
        return acceptDetailDescription;
    }

    public void setAcceptDetailDescription(String acceptDetailDescription) {
        this.acceptDetailDescription = acceptDetailDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", terminalId=").append(terminalId);
        sb.append(", orgId=").append(orgId);
        sb.append(", type=").append(type);
        sb.append(", recordId=").append(recordId);
        sb.append(", content=").append(content);
        sb.append(", time=").append(time);
        sb.append(", status=").append(status);
        sb.append(", acceptTime=").append(acceptTime);
        sb.append(", acceptOperatorId=").append(acceptOperatorId);
        sb.append(", archiveTime=").append(archiveTime);
        sb.append(", acceptDetailDescription=").append(acceptDetailDescription);
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
        YhHeThresholdAlarm other = (YhHeThresholdAlarm) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getTerminalId() == null ? other.getTerminalId() == null : this.getTerminalId().equals(other.getTerminalId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getAcceptTime() == null ? other.getAcceptTime() == null : this.getAcceptTime().equals(other.getAcceptTime()))
            && (this.getAcceptOperatorId() == null ? other.getAcceptOperatorId() == null : this.getAcceptOperatorId().equals(other.getAcceptOperatorId()))
            && (this.getArchiveTime() == null ? other.getArchiveTime() == null : this.getArchiveTime().equals(other.getArchiveTime()))
            && (this.getAcceptDetailDescription() == null ? other.getAcceptDetailDescription() == null : this.getAcceptDetailDescription().equals(other.getAcceptDetailDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getTerminalId() == null) ? 0 : getTerminalId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getAcceptTime() == null) ? 0 : getAcceptTime().hashCode());
        result = prime * result + ((getAcceptOperatorId() == null) ? 0 : getAcceptOperatorId().hashCode());
        result = prime * result + ((getArchiveTime() == null) ? 0 : getArchiveTime().hashCode());
        result = prime * result + ((getAcceptDetailDescription() == null) ? 0 : getAcceptDetailDescription().hashCode());
        return result;
    }
}