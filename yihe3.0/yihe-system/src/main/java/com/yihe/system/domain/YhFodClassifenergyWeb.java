package com.yihe.system.domain;

import java.util.List;

public class YhFodClassifenergyWeb {
    private Integer fClassifenergyid;

    private String fLevel;

    private String fBreakfast;

    private String fLunch;

    private String fDinner;

    private String fExtrameal;
    
   
    
    private List<List<YhFoodCookBook>> zaoBooks;
    private List<List<YhFoodCookBook>> wuBooks;
    private List<List<YhFoodCookBook>> wanBooks;
    private List<List<YhFoodCookBook>> jiaBooks;
    
    
    private String[] zaoSplit;
    private String[] wuSplit;
    private String[] wanSplit;
    private String[] jiaSplit;
    
    
    
    
    

     

	 

	public String[] getZaoSplit() {
		return zaoSplit;
	}

	public void setZaoSplit(String[] zaoSplit) {
		this.zaoSplit = zaoSplit;
	}

	public String[] getWuSplit() {
		return wuSplit;
	}

	public void setWuSplit(String[] wuSplit) {
		this.wuSplit = wuSplit;
	}

	public String[] getWanSplit() {
		return wanSplit;
	}

	public void setWanSplit(String[] wanSplit) {
		this.wanSplit = wanSplit;
	}

	public String[] getJiaSplit() {
		return jiaSplit;
	}

	public void setJiaSplit(String[] jiaSplit) {
		this.jiaSplit = jiaSplit;
	}

	public List<List<YhFoodCookBook>> getZaoBooks() {
		return zaoBooks;
	}

	public void setZaoBooks(List<List<YhFoodCookBook>> zaoBooks) {
		this.zaoBooks = zaoBooks;
	}

	public List<List<YhFoodCookBook>> getWuBooks() {
		return wuBooks;
	}

	public void setWuBooks(List<List<YhFoodCookBook>> wuBooks) {
		this.wuBooks = wuBooks;
	}

	public List<List<YhFoodCookBook>> getWanBooks() {
		return wanBooks;
	}

	public void setWanBooks(List<List<YhFoodCookBook>> wanBooks) {
		this.wanBooks = wanBooks;
	}

	public List<List<YhFoodCookBook>> getJiaBooks() {
		return jiaBooks;
	}

	public void setJiaBooks(List<List<YhFoodCookBook>> jiaBooks) {
		this.jiaBooks = jiaBooks;
	}

	public Integer getfClassifenergyid() {
        return fClassifenergyid;
    }

    public void setfClassifenergyid(Integer fClassifenergyid) {
        this.fClassifenergyid = fClassifenergyid;
    }

    public String getfLevel() {
        return fLevel;
    }

    public void setfLevel(String fLevel) {
        this.fLevel = fLevel == null ? null : fLevel.trim();
    }

    public String getfBreakfast() {
        return fBreakfast;
    }

    public void setfBreakfast(String fBreakfast) {
        this.fBreakfast = fBreakfast == null ? null : fBreakfast.trim();
    }

    public String getfLunch() {
        return fLunch;
    }

    public void setfLunch(String fLunch) {
        this.fLunch = fLunch == null ? null : fLunch.trim();
    }

    public String getfDinner() {
        return fDinner;
    }

    public void setfDinner(String fDinner) {
        this.fDinner = fDinner == null ? null : fDinner.trim();
    }

    public String getfExtrameal() {
        return fExtrameal;
    }

    public void setfExtrameal(String fExtrameal) {
        this.fExtrameal = fExtrameal == null ? null : fExtrameal.trim();
    }
}