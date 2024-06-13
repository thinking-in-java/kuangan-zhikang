package com.yihe.system.domain;

public class YHUserBodyFat {
    private Integer wubfId;

    private Float weightscale;

    private Float idealweight;

    private Float withoutfatforweightscale;

    private String weightmessages;

    private String weightstate;

    private Float fatRate;

    private Float idealbodyfat;

    private Float bodyfatscale;

    private String fatmessages;

    private String fatstate;

    private Float muscle;

    private Float musclescale;

    private Float musclecontrolscale;

    private String musclemessages;

    private String musclestate;

    private Float bones;

    private String bomesmessages;

    private String bonesstate;

    private Float visceralLevel;

    private String levelmessages;

    private String levelstate;

    private Float water;

    private String watermessages;

    private String waterstate;

    private Float heat;

    private Float protein;

    private String porteinmessages;

    private String proteinstate;

    private Float basalMet;

    private Float standardbasicmetabolism;

    private Float budgetbasicmetabolism;

    private String basalmessages;

    private String basalstate;

    private Float storagemusclelevel;

    private String storagemessages;

    private String storagestate;

    private String bodyage;

    private String bodyscore;

    private String bodytype;

    private String entryTime;

    private Integer isDel;

    private Integer eUserId;

    private String type;

    private Integer source;

    
    public YHUserBodyFat( Float fat_rate, Float muscle, Float bones, Float visceral_level,
			Float water, Float heat, Float protein, Float basal_met, String entryTime, Integer is_Del, Integer eUserId,
			String type) {
		super();
		this.fatRate = fat_rate;
		this.muscle = muscle;
		this.bones = bones;
		this.visceralLevel = visceral_level;
		this.water = water;
		this.heat = heat;
		this.protein = protein;
		this.basalMet = basal_met;
		this.entryTime = entryTime;
		this.isDel = is_Del;
		this.eUserId = eUserId;
		this.type = type;
	}
	public YHUserBodyFat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
    public Integer getWubfId() {
        return wubfId;
    }

    public void setWubfId(Integer wubfId) {
        this.wubfId = wubfId;
    }

    public Float getWeightscale() {
        return weightscale;
    }

    public void setWeightscale(Float weightscale) {
        this.weightscale = weightscale;
    }

    public Float getIdealweight() {
        return idealweight;
    }

    public void setIdealweight(Float idealweight) {
        this.idealweight = idealweight;
    }

    public Float getWithoutfatforweightscale() {
        return withoutfatforweightscale;
    }

    public void setWithoutfatforweightscale(Float withoutfatforweightscale) {
        this.withoutfatforweightscale = withoutfatforweightscale;
    }

    public String getWeightmessages() {
        return weightmessages;
    }

    public void setWeightmessages(String weightmessages) {
        this.weightmessages = weightmessages == null ? null : weightmessages.trim();
    }

    public String getWeightstate() {
        return weightstate;
    }

    public void setWeightstate(String weightstate) {
        this.weightstate = weightstate == null ? null : weightstate.trim();
    }

    public Float getFatRate() {
        return fatRate;
    }

    public void setFatRate(Float fatRate) {
        this.fatRate = fatRate;
    }

    public Float getIdealbodyfat() {
        return idealbodyfat;
    }

    public void setIdealbodyfat(Float idealbodyfat) {
        this.idealbodyfat = idealbodyfat;
    }

    public Float getBodyfatscale() {
        return bodyfatscale;
    }

    public void setBodyfatscale(Float bodyfatscale) {
        this.bodyfatscale = bodyfatscale;
    }

    public String getFatmessages() {
        return fatmessages;
    }

    public void setFatmessages(String fatmessages) {
        this.fatmessages = fatmessages == null ? null : fatmessages.trim();
    }

    public String getFatstate() {
        return fatstate;
    }

    public void setFatstate(String fatstate) {
        this.fatstate = fatstate == null ? null : fatstate.trim();
    }

    public Float getMuscle() {
        return muscle;
    }

    public void setMuscle(Float muscle) {
        this.muscle = muscle;
    }

    public Float getMusclescale() {
        return musclescale;
    }

    public void setMusclescale(Float musclescale) {
        this.musclescale = musclescale;
    }

    public Float getMusclecontrolscale() {
        return musclecontrolscale;
    }

    public void setMusclecontrolscale(Float musclecontrolscale) {
        this.musclecontrolscale = musclecontrolscale;
    }

    public String getMusclemessages() {
        return musclemessages;
    }

    public void setMusclemessages(String musclemessages) {
        this.musclemessages = musclemessages == null ? null : musclemessages.trim();
    }

    public String getMusclestate() {
        return musclestate;
    }

    public void setMusclestate(String musclestate) {
        this.musclestate = musclestate == null ? null : musclestate.trim();
    }

    public Float getBones() {
        return bones;
    }

    public void setBones(Float bones) {
        this.bones = bones;
    }

    public String getBomesmessages() {
        return bomesmessages;
    }

    public void setBomesmessages(String bomesmessages) {
        this.bomesmessages = bomesmessages == null ? null : bomesmessages.trim();
    }

    public String getBonesstate() {
        return bonesstate;
    }

    public void setBonesstate(String bonesstate) {
        this.bonesstate = bonesstate == null ? null : bonesstate.trim();
    }

    public Float getVisceralLevel() {
        return visceralLevel;
    }

    public void setVisceralLevel(Float visceralLevel) {
        this.visceralLevel = visceralLevel;
    }

    public String getLevelmessages() {
        return levelmessages;
    }

    public void setLevelmessages(String levelmessages) {
        this.levelmessages = levelmessages == null ? null : levelmessages.trim();
    }

    public String getLevelstate() {
        return levelstate;
    }

    public void setLevelstate(String levelstate) {
        this.levelstate = levelstate == null ? null : levelstate.trim();
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public String getWatermessages() {
        return watermessages;
    }

    public void setWatermessages(String watermessages) {
        this.watermessages = watermessages == null ? null : watermessages.trim();
    }

    public String getWaterstate() {
        return waterstate;
    }

    public void setWaterstate(String waterstate) {
        this.waterstate = waterstate == null ? null : waterstate.trim();
    }

    public Float getHeat() {
        return heat;
    }

    public void setHeat(Float heat) {
        this.heat = heat;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public String getPorteinmessages() {
        return porteinmessages;
    }

    public void setPorteinmessages(String porteinmessages) {
        this.porteinmessages = porteinmessages == null ? null : porteinmessages.trim();
    }

    public String getProteinstate() {
        return proteinstate;
    }

    public void setProteinstate(String proteinstate) {
        this.proteinstate = proteinstate == null ? null : proteinstate.trim();
    }

    public Float getBasalMet() {
        return basalMet;
    }

    public void setBasalMet(Float basalMet) {
        this.basalMet = basalMet;
    }

    public Float getStandardbasicmetabolism() {
        return standardbasicmetabolism;
    }

    public void setStandardbasicmetabolism(Float standardbasicmetabolism) {
        this.standardbasicmetabolism = standardbasicmetabolism;
    }

    public Float getBudgetbasicmetabolism() {
        return budgetbasicmetabolism;
    }

    public void setBudgetbasicmetabolism(Float budgetbasicmetabolism) {
        this.budgetbasicmetabolism = budgetbasicmetabolism;
    }

    public String getBasalmessages() {
        return basalmessages;
    }

    public void setBasalmessages(String basalmessages) {
        this.basalmessages = basalmessages == null ? null : basalmessages.trim();
    }

    public String getBasalstate() {
        return basalstate;
    }

    public void setBasalstate(String basalstate) {
        this.basalstate = basalstate == null ? null : basalstate.trim();
    }

    public Float getStoragemusclelevel() {
        return storagemusclelevel;
    }

    public void setStoragemusclelevel(Float storagemusclelevel) {
        this.storagemusclelevel = storagemusclelevel;
    }

    public String getStoragemessages() {
        return storagemessages;
    }

    public void setStoragemessages(String storagemessages) {
        this.storagemessages = storagemessages == null ? null : storagemessages.trim();
    }

    public String getStoragestate() {
        return storagestate;
    }

    public void setStoragestate(String storagestate) {
        this.storagestate = storagestate == null ? null : storagestate.trim();
    }

    public String getBodyage() {
        return bodyage;
    }

    public void setBodyage(String bodyage) {
        this.bodyage = bodyage == null ? null : bodyage.trim();
    }

    public String getBodyscore() {
        return bodyscore;
    }

    public void setBodyscore(String bodyscore) {
        this.bodyscore = bodyscore == null ? null : bodyscore.trim();
    }

    public String getBodytype() {
        return bodytype;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype == null ? null : bodytype.trim();
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime == null ? null : entryTime.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}