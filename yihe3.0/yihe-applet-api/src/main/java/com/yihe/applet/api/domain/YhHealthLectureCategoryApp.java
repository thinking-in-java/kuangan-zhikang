package com.yihe.applet.api.domain;

import java.util.List;

public class YhHealthLectureCategoryApp {
    private Integer categoryid;

    private String name;

    private String parentId;

    private Integer sortId;
    
    /**
     * 大类下的小类集合
     */
    private List<YhHealthLectureCategoryApp> typeList;
    
    

    public List<YhHealthLectureCategoryApp> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<YhHealthLectureCategoryApp> typeList) {
		this.typeList = typeList;
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
        this.name = name == null ? null : name.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}