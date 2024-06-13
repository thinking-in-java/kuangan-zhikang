package com.yihe.system.domain;

public class YhFoodCategoryWeb {
    private Integer id;

    private String foodCategoryName;

    private String foodCategoryImg;

    private Integer displayorder;

    private Integer everydaymin;

    private Integer everydaymax;

    private Integer analysis;
    
    private String value;
    
    private String status;
    
    
    
    

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName == null ? null : foodCategoryName.trim();
    }

    public String getFoodCategoryImg() {
        return foodCategoryImg;
    }

    public void setFoodCategoryImg(String foodCategoryImg) {
        this.foodCategoryImg = foodCategoryImg == null ? null : foodCategoryImg.trim();
    }

    public Integer getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(Integer displayorder) {
        this.displayorder = displayorder;
    }

    public Integer getEverydaymin() {
        return everydaymin;
    }

    public void setEverydaymin(Integer everydaymin) {
        this.everydaymin = everydaymin;
    }

    public Integer getEverydaymax() {
        return everydaymax;
    }

    public void setEverydaymax(Integer everydaymax) {
        this.everydaymax = everydaymax;
    }

    public Integer getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Integer analysis) {
        this.analysis = analysis;
    }
}