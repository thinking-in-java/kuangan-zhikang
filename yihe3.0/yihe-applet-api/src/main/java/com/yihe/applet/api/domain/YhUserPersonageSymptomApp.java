package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserPersonageSymptomApp implements Serializable {
    private Integer id;

    /**
     * 症状
     *
     * @mbg.generated
     */
    private String personageName;

    private String userCode;

    /**
     * 备用
     *
     * @mbg.generated
     */
    private String standby;

    private static final long serialVersionUID = 1L;

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
        this.personageName = personageName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", personageName=").append(personageName);
        sb.append(", userCode=").append(userCode);
        sb.append(", standby=").append(standby);
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
        YhUserPersonageSymptomApp other = (YhUserPersonageSymptomApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPersonageName() == null ? other.getPersonageName() == null : this.getPersonageName().equals(other.getPersonageName()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getStandby() == null ? other.getStandby() == null : this.getStandby().equals(other.getStandby()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPersonageName() == null) ? 0 : getPersonageName().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getStandby() == null) ? 0 : getStandby().hashCode());
        return result;
    }
}