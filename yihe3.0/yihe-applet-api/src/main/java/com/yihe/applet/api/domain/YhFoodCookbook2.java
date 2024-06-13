package com.yihe.applet.api.domain;

import java.util.List;

public class YhFoodCookbook2 {
    private Integer fCId;

    private String fCName;

    private String appraise;

    private String fCImg;

    private String measuringUnit;

    private String fType;

    private String cookPerson;

    private String energyKcal;

    private String cho;

    private String fat;

    private String protein;

    private String cholesterol;

    private String vitamina;

    private String cellulose;

    private String vitaminc;

    private String vitamine;

    private String thiamine;

    private String riboflavin;

    private String niacin;

    private String magnesium;

    private String calcium;

    private String rron;

    private String zinc;

    private String copper;

    private String manganese;

    private String potassium;

    private String phosphorus;

    private String sodium;

    private String selenium;

    private Integer common;

    private Integer oneself;

    private Integer isDel;

    private String userCode;
    
    private Double gramNumber;
    
    private Integer collectOrNot;
    
    private String energyKcalRiji;
    
    private String keshu;
    
    private long belowHeat;//低于多少百分比热量
    
    private String typeName;
    
    
    private String allName;
    

    private Integer usedFood;

    private Integer ediblePart;

    private String moisture;


    private String insolubleDietaryFiber;


    private String saturatedFat;

    private String monounsaturatedFattyAcids;

    private String polyunsaturatedFattyAcids;
    
    //菜肴成分
    private double component;

    private String notRecommendFood;

    private List<String> notRecommendFoodList;
   
    private YhFoodCooked yhFoodCooked;
    
    private Integer canci;

    private  String fbName;
    private  List<String> fbNameList;


    public List<String> getFbNameList() {
        return fbNameList;
    }

    public void setFbNameList(List<String> fbNameList) {
        this.fbNameList = fbNameList;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }

    public Integer getCanci() {
        return canci;
    }

    public void setCanci(Integer canci) {
        this.canci = canci;
    }

    public YhFoodCooked getYhFoodCooked() {
		return yhFoodCooked;
	}

	public void setYhFoodCooked(YhFoodCooked yhFoodCooked) {
		this.yhFoodCooked = yhFoodCooked;
	}

	public double getComponent() {
		return component;
	}

	public void setComponent(double component) {
		this.component = component;
	}

	public Integer getUsedFood() {
		return usedFood;
	}

	public void setUsedFood(Integer usedFood) {
		this.usedFood = usedFood;
	}

	public Integer getEdiblePart() {
		return ediblePart;
	}

	public void setEdiblePart(Integer ediblePart) {
		this.ediblePart = ediblePart;
	}




	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public long getBelowHeat() {
		return belowHeat;
	}

	public void setBelowHeat(long belowHeat) {
		this.belowHeat = belowHeat;
	}


	public String getKeshu() {
		return keshu;
	}

	public void setKeshu(String keshu) {
		this.keshu = keshu;
	}

	public Integer getCollectOrNot() {
		return collectOrNot;
	}

	public void setCollectOrNot(Integer collectOrNot) {
		this.collectOrNot = collectOrNot;
	}

	public Double getGramNumber() {
		return gramNumber;
	}

	public void setGramNumber(Double gramNumber) {
		this.gramNumber = gramNumber;
	}

	public Integer getfCId() {
        return fCId;
    }

    public void setfCId(Integer fCId) {
        this.fCId = fCId;
    }

    public String getfCName() {
        return fCName;
    }

    public void setfCName(String fCName) {
        this.fCName = fCName == null ? null : fCName.trim();
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise == null ? null : appraise.trim();
    }

    public String getfCImg() {
        return fCImg;
    }

    public void setfCImg(String fCImg) {
        this.fCImg = fCImg == null ? null : fCImg.trim();
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit == null ? null : measuringUnit.trim();
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType == null ? null : fType.trim();
    }

    public String getCookPerson() {
        return cookPerson;
    }

    public void setCookPerson(String cookPerson) {
        this.cookPerson = cookPerson == null ? null : cookPerson.trim();
    }

    public String getEnergyKcal() {
        return energyKcal;
    }

    public void setEnergyKcal(String energyKcal) {
        this.energyKcal = energyKcal;
    }

    public String getCho() {
        return cho;
    }

    public void setCho(String cho) {
        this.cho = cho;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getVitamina() {
        return vitamina;
    }

    public void setVitamina(String vitamina) {
        this.vitamina = vitamina;
    }

    public String getCellulose() {
        return cellulose;
    }

    public void setCellulose(String cellulose) {
        this.cellulose = cellulose;
    }

    public String getVitaminc() {
        return vitaminc;
    }

    public void setVitaminc(String vitaminc) {
        this.vitaminc = vitaminc;
    }

    public String getVitamine() {
        return vitamine;
    }

    public void setVitamine(String vitamine) {
        this.vitamine = vitamine;
    }

    public String getThiamine() {
        return thiamine;
    }

    public void setThiamine(String thiamine) {
        this.thiamine = thiamine;
    }

    public String getRiboflavin() {
        return riboflavin;
    }

    public void setRiboflavin(String riboflavin) {
        this.riboflavin = riboflavin;
    }

    public String getNiacin() {
        return niacin;
    }

    public void setNiacin(String niacin) {
        this.niacin = niacin;
    }

    public String getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(String magnesium) {
        this.magnesium = magnesium;
    }

    public String getCalcium() {
        return calcium;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public String getRron() {
        return rron;
    }

    public void setRron(String rron) {
        this.rron = rron;
    }

    public String getZinc() {
        return zinc;
    }

    public void setZinc(String zinc) {
        this.zinc = zinc;
    }

    public String getCopper() {
        return copper;
    }

    public void setCopper(String copper) {
        this.copper = copper;
    }

    public String getManganese() {
        return manganese;
    }

    public void setManganese(String manganese) {
        this.manganese = manganese;
    }

    public String getPotassium() {
        return potassium;
    }

    public void setPotassium(String potassium) {
        this.potassium = potassium;
    }

    public String getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(String phosphorus) {
        this.phosphorus = phosphorus;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getSelenium() {
        return selenium;
    }

    public void setSelenium(String selenium) {
        this.selenium = selenium;
    }

    public String getEnergyKcalRiji() {
        return energyKcalRiji;
    }

    public void setEnergyKcalRiji(String energyKcalRiji) {
        this.energyKcalRiji = energyKcalRiji;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getInsolubleDietaryFiber() {
        return insolubleDietaryFiber;
    }

    public void setInsolubleDietaryFiber(String insolubleDietaryFiber) {
        this.insolubleDietaryFiber = insolubleDietaryFiber;
    }

    public String getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public String getMonounsaturatedFattyAcids() {
        return monounsaturatedFattyAcids;
    }

    public void setMonounsaturatedFattyAcids(String monounsaturatedFattyAcids) {
        this.monounsaturatedFattyAcids = monounsaturatedFattyAcids;
    }

    public String getPolyunsaturatedFattyAcids() {
        return polyunsaturatedFattyAcids;
    }

    public void setPolyunsaturatedFattyAcids(String polyunsaturatedFattyAcids) {
        this.polyunsaturatedFattyAcids = polyunsaturatedFattyAcids;
    }

    public Integer getCommon() {
        return common;
    }

    public void setCommon(Integer common) {
        this.common = common;
    }

    public Integer getOneself() {
        return oneself;
    }

    public void setOneself(Integer oneself) {
        this.oneself = oneself;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getNotRecommendFood() {
        return notRecommendFood;
    }

    public void setNotRecommendFood(String notRecommendFood) {
        this.notRecommendFood = notRecommendFood;
    }

    public List<String> getNotRecommendFoodList() {
        return notRecommendFoodList;
    }

    public void setNotRecommendFoodList(List<String> notRecommendFoodList) {
        this.notRecommendFoodList = notRecommendFoodList;
    }
}