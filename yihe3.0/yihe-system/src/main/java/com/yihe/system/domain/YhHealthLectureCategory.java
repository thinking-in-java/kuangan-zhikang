package com.yihe.system.domain;

import java.io.Serializable;

public class YhHealthLectureCategory implements Serializable {
    private Integer categoryid;

    /**
     * 分类名
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 父分类ID
     *
     * @mbg.generated
     */
    private String parentId;

    /**
     * 顺序id
     *
     * @mbg.generated
     */
    private Integer sortId;
    
    private Integer timenum;
    
    private int fateast;

    private static final long serialVersionUID = 1L;

    
    
    public int getFateast() {
		return fateast;
	}

	public void setFateast(int fateast) {
		this.fateast = fateast;
	}

	public Integer getTimenum() {
		return timenum;
	}

	public void setTimenum(Integer timenum) {
		this.timenum = timenum;
	}

	public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categoryid=").append(categoryid);
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", sortId=").append(sortId);
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
        YhHealthLectureCategory other = (YhHealthLectureCategory) that;
        return (this.getCategoryid() == null ? other.getCategoryid() == null : this.getCategoryid().equals(other.getCategoryid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getSortId() == null ? other.getSortId() == null : this.getSortId().equals(other.getSortId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategoryid() == null) ? 0 : getCategoryid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getSortId() == null) ? 0 : getSortId().hashCode());
        return result;
    }
}