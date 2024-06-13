package com.yihe.system.domain;

import java.util.List;

public class YhFoodCookBook {
	private Integer fCId;

	private String fCName;

	private String appraise;

	private String fCImg;

	private String measuringUnit;

	private String fType;

	private String cookPerson;

	private Float energyKcal;

	private Float cho;

	private Float fat;

	private Float protein;

	private Float cholesterol;

	private Float vitamina;

	private Float cellulose;

	private Float vitaminc;

	private Float vitamine;

	private Float thiamine;

	private Float riboflavin;

	private Float niacin;

	private Float magnesium;

	private Float calcium;

	private Float rron;

	private Float zinc;

	private Float copper;

	private Float manganese;

	private Float potassium;

	private Float phosphorus;

	private Float sodium;

	private Float selenium;

	private Integer common;

	private Integer oneself;

	private Integer isDel;

	private String userCode;

	private String foodcategoryname;
	
	private String notRecommendFood;

	private String energyKcalRiji;

	/**
	 * 是否已经收藏
	 * 
	 * @return
	 */

	private Boolean isshoucang;

	/**
	 * 是否为成分
	 */
	private Boolean chengfen;


	private Integer gramNumber;// 推荐食材克数
	private float recipesNumber;// 每日食谱推荐热量

	private String foodMethod;

	private String[][] foodList;

	private List<String> foodKeshu;

	private Float moisture;
	private Integer usedFood;
	private Float insolubleDietaryFiber;
	
	 private Float saturatedFat;

	    private Float monounsaturatedFattyAcids;

	    private Float polyunsaturatedFattyAcids;

	    private int quanxian;


	public String getEnergyKcalRiji() {
		return energyKcalRiji;
	}

	public void setEnergyKcalRiji(String energyKcalRiji) {
		this.energyKcalRiji = energyKcalRiji;
	}

	public int getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(int quanxian) {
		this.quanxian = quanxian;
	}

	public Boolean getChengfen() {
		return chengfen;
	}

	public void setChengfen(Boolean chengfen) {
		this.chengfen = chengfen;
	}

	public String getNotRecommendFood() {
			return notRecommendFood;
		}

		public void setNotRecommendFood(String notRecommendFood) {
			this.notRecommendFood = notRecommendFood;
		}

	public Integer getUsedFood() {
			return usedFood;
		}

		public void setUsedFood(Integer usedFood) {
			this.usedFood = usedFood;
		}

	public Float getSaturatedFat() {
			return saturatedFat;
		}

		public void setSaturatedFat(Float saturatedFat) {
			this.saturatedFat = saturatedFat;
		}

		public Float getMonounsaturatedFattyAcids() {
			return monounsaturatedFattyAcids;
		}

		public void setMonounsaturatedFattyAcids(Float monounsaturatedFattyAcids) {
			this.monounsaturatedFattyAcids = monounsaturatedFattyAcids;
		}

		public Float getPolyunsaturatedFattyAcids() {
			return polyunsaturatedFattyAcids;
		}

		public void setPolyunsaturatedFattyAcids(Float polyunsaturatedFattyAcids) {
			this.polyunsaturatedFattyAcids = polyunsaturatedFattyAcids;
		}

	public Float getInsolubleDietaryFiber() {
		return insolubleDietaryFiber;
	}

	public void setInsolubleDietaryFiber(Float insolubleDietaryFiber) {
		this.insolubleDietaryFiber = insolubleDietaryFiber;
	}

	public Float getMoisture() {
		return moisture;
	}

	public void setMoisture(Float moisture) {
		this.moisture = moisture;
	}

	public String getFoodMethod() {
		return foodMethod;
	}

	public void setFoodMethod(String foodMethod) {
		this.foodMethod = foodMethod;
	}

	public String[][] getFoodList() {
		return foodList;
	}

	public void setFoodList(String[][] foodList) {
		this.foodList = foodList;
	}

	public List<String> getFoodKeshu() {
		return foodKeshu;
	}

	public void setFoodKeshu(List<String> foodKeshu) {
		this.foodKeshu = foodKeshu;
	}

	public float getRecipesNumber() {
		return recipesNumber;
	}

	public void setRecipesNumber(float recipesNumber) {
		this.recipesNumber = recipesNumber;
	}

	public Boolean getIsshoucang() {
		return isshoucang;
	}

	public void setIsshoucang(Boolean isshoucang) {
		this.isshoucang = isshoucang;
	}

	public Integer getGramNumber() {
		return gramNumber;
	}

	public void setGramNumber(Integer gramNumber) {
		this.gramNumber = gramNumber;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public String getFoodcategoryname() {
		return foodcategoryname;
	}

	public void setFoodcategoryname(String foodcategoryname) {
		this.foodcategoryname = foodcategoryname;
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

	public Float getEnergyKcal() {
		return energyKcal;
	}

	public void setEnergyKcal(Float energyKcal) {
		this.energyKcal = energyKcal;
	}

	public Float getCho() {
		return cho;
	}

	public void setCho(Float cho) {
		this.cho = cho;
	}

	public Float getFat() {
		return fat;
	}

	public void setFat(Float fat) {
		this.fat = fat;
	}

	public Float getProtein() {
		return protein;
	}

	public void setProtein(Float protein) {
		this.protein = protein;
	}

	public Float getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(Float cholesterol) {
		this.cholesterol = cholesterol;
	}

	public Float getVitamina() {
		return vitamina;
	}

	public void setVitamina(Float vitamina) {
		this.vitamina = vitamina;
	}

	public Float getCellulose() {
		return cellulose;
	}

	public void setCellulose(Float cellulose) {
		this.cellulose = cellulose;
	}

	public Float getVitaminc() {
		return vitaminc;
	}

	public void setVitaminc(Float vitaminc) {
		this.vitaminc = vitaminc;
	}

	public Float getVitamine() {
		return vitamine;
	}

	public void setVitamine(Float vitamine) {
		this.vitamine = vitamine;
	}

	public Float getThiamine() {
		return thiamine;
	}

	public void setThiamine(Float thiamine) {
		this.thiamine = thiamine;
	}

	public Float getRiboflavin() {
		return riboflavin;
	}

	public void setRiboflavin(Float riboflavin) {
		this.riboflavin = riboflavin;
	}

	public Float getNiacin() {
		return niacin;
	}

	public void setNiacin(Float niacin) {
		this.niacin = niacin;
	}

	public Float getMagnesium() {
		return magnesium;
	}

	public void setMagnesium(Float magnesium) {
		this.magnesium = magnesium;
	}

	public Float getCalcium() {
		return calcium;
	}

	public void setCalcium(Float calcium) {
		this.calcium = calcium;
	}

	public Float getRron() {
		return rron;
	}

	public void setRron(Float rron) {
		this.rron = rron;
	}

	public Float getZinc() {
		return zinc;
	}

	public void setZinc(Float zinc) {
		this.zinc = zinc;
	}

	public Float getCopper() {
		return copper;
	}

	public void setCopper(Float copper) {
		this.copper = copper;
	}

	public Float getManganese() {
		return manganese;
	}

	public void setManganese(Float manganese) {
		this.manganese = manganese;
	}

	public Float getPotassium() {
		return potassium;
	}

	public void setPotassium(Float potassium) {
		this.potassium = potassium;
	}

	public Float getPhosphorus() {
		return phosphorus;
	}

	public void setPhosphorus(Float phosphorus) {
		this.phosphorus = phosphorus;
	}

	public Float getSodium() {
		return sodium;
	}

	public void setSodium(Float sodium) {
		this.sodium = sodium;
	}

	public Float getSelenium() {
		return selenium;
	}

	public void setSelenium(Float selenium) {
		this.selenium = selenium;
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

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}
}