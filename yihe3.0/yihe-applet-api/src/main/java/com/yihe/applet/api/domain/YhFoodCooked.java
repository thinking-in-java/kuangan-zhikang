package com.yihe.applet.api.domain;

import java.util.List;

public class YhFoodCooked {
    private Integer fCookedid;

    private String fCookedelements;

    private String foodmethod;
    
    List<YhFoodCookbook2> blist;
    
    

    public List<YhFoodCookbook2> getBlist() {
		return blist;
	}

	public void setBlist(List<YhFoodCookbook2> blist) {
		this.blist = blist;
	}

	public Integer getfCookedid() {
        return fCookedid;
    }

    public void setfCookedid(Integer fCookedid) {
        this.fCookedid = fCookedid;
    }

    public String getfCookedelements() {
        return fCookedelements;
    }

    public void setfCookedelements(String fCookedelements) {
        this.fCookedelements = fCookedelements == null ? null : fCookedelements.trim();
    }

    public String getFoodmethod() {
        return foodmethod;
    }

    public void setFoodmethod(String foodmethod) {
        this.foodmethod = foodmethod == null ? null : foodmethod.trim();
    }
}