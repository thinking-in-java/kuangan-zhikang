package com.yihe.system.domain;

import java.io.Serializable;

public class YhHeThreshold implements Serializable {
    private String id;

    /**
     * type for [pulse, temperature, blood_pressure, blood_sugar]
     *
     * @mbg.generated
     */
    private String type;

    private String name;

    private String low;

    private String high;

    private String low1;

    private String high1;

    private String low2;

    private String high2;

    private String invalidLow;

    private String invalidHigh;

    private String description;
    
    private Integer ISTRUE;

    private static final long serialVersionUID = 1L;
    
    
    public Integer getISTRUE() {
		return ISTRUE;
	}

	public void setISTRUE(Integer iSTRUE) {
		ISTRUE = iSTRUE;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow1() {
        return low1;
    }

    public void setLow1(String low1) {
        this.low1 = low1;
    }

    public String getHigh1() {
        return high1;
    }

    public void setHigh1(String high1) {
        this.high1 = high1;
    }

    public String getLow2() {
        return low2;
    }

    public void setLow2(String low2) {
        this.low2 = low2;
    }

    public String getHigh2() {
        return high2;
    }

    public void setHigh2(String high2) {
        this.high2 = high2;
    }

    public String getInvalidLow() {
        return invalidLow;
    }

    public void setInvalidLow(String invalidLow) {
        this.invalidLow = invalidLow;
    }

    public String getInvalidHigh() {
        return invalidHigh;
    }

    public void setInvalidHigh(String invalidHigh) {
        this.invalidHigh = invalidHigh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", low=").append(low);
        sb.append(", high=").append(high);
        sb.append(", low1=").append(low1);
        sb.append(", high1=").append(high1);
        sb.append(", low2=").append(low2);
        sb.append(", high2=").append(high2);
        sb.append(", invalidLow=").append(invalidLow);
        sb.append(", invalidHigh=").append(invalidHigh);
        sb.append(", description=").append(description);
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
        YhHeThreshold other = (YhHeThreshold) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLow() == null ? other.getLow() == null : this.getLow().equals(other.getLow()))
            && (this.getHigh() == null ? other.getHigh() == null : this.getHigh().equals(other.getHigh()))
            && (this.getLow1() == null ? other.getLow1() == null : this.getLow1().equals(other.getLow1()))
            && (this.getHigh1() == null ? other.getHigh1() == null : this.getHigh1().equals(other.getHigh1()))
            && (this.getLow2() == null ? other.getLow2() == null : this.getLow2().equals(other.getLow2()))
            && (this.getHigh2() == null ? other.getHigh2() == null : this.getHigh2().equals(other.getHigh2()))
            && (this.getInvalidLow() == null ? other.getInvalidLow() == null : this.getInvalidLow().equals(other.getInvalidLow()))
            && (this.getInvalidHigh() == null ? other.getInvalidHigh() == null : this.getInvalidHigh().equals(other.getInvalidHigh()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLow() == null) ? 0 : getLow().hashCode());
        result = prime * result + ((getHigh() == null) ? 0 : getHigh().hashCode());
        result = prime * result + ((getLow1() == null) ? 0 : getLow1().hashCode());
        result = prime * result + ((getHigh1() == null) ? 0 : getHigh1().hashCode());
        result = prime * result + ((getLow2() == null) ? 0 : getLow2().hashCode());
        result = prime * result + ((getHigh2() == null) ? 0 : getHigh2().hashCode());
        result = prime * result + ((getInvalidLow() == null) ? 0 : getInvalidLow().hashCode());
        result = prime * result + ((getInvalidHigh() == null) ? 0 : getInvalidHigh().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }
}