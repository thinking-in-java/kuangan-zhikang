package com.yihe.applet.api.domain;

import java.util.List;

public class YhUserFoodWeekly {
    private Integer wid;

    private String userCode;

    private String starttime;

    private String endtime;

    private Integer ecorddays;

    private Integer exceedingdays;

    private Integer norepetition;

    private Float averageintake;
    private Float averageintake7;

    private Float protein;

    private Float fat;

    private Float carbohydrate;
    
    private Float proteinbfb;

    private Float fatbfb;

    private Float carbohydratebfb;
    
    private String proteincheck;

    private String fatcheck;

    private String carbohydratecheck;

    private Float zao;

    private Float wu;

    private Float wan;

    private Float jia;
    

    private String zaoCheck;

    private String wuCheck;

    private String wanCheck;

    private String jiaCheck;

    private String foodtype;

    private Float type1;

    private Float type2;

    private Float type3;

    private Float type4;

    private Float type5;

    private Float type6;

    private Float type7;

    private Float type8;

    private Float type9;

    private Float type10;

    private Float type11;

    private Float type12;

    private Float type13;

    private Float type14;

    private Float type15;
    
    private  List<YhFoodCategoryApi> findAllInfo;
    
    
    
    

    public String getZaoCheck() {
		return zaoCheck;
	}

	public void setZaoCheck(String zaoCheck) {
		this.zaoCheck = zaoCheck;
	}

	public String getWuCheck() {
		return wuCheck;
	}

	public void setWuCheck(String wuCheck) {
		this.wuCheck = wuCheck;
	}

	public String getWanCheck() {
		return wanCheck;
	}

	public void setWanCheck(String wanCheck) {
		this.wanCheck = wanCheck;
	}

	public String getJiaCheck() {
		return jiaCheck;
	}

	public void setJiaCheck(String jiaCheck) {
		this.jiaCheck = jiaCheck;
	}

	public Float getProteinbfb() {
		return proteinbfb;
	}

	public void setProteinbfb(Float proteinbfb) {
		this.proteinbfb = proteinbfb;
	}

	public Float getFatbfb() {
		return fatbfb;
	}

	public void setFatbfb(Float fatbfb) {
		this.fatbfb = fatbfb;
	}

	public Float getCarbohydratebfb() {
		return carbohydratebfb;
	}

	public void setCarbohydratebfb(Float carbohydratebfb) {
		this.carbohydratebfb = carbohydratebfb;
	}

	public String getProteincheck() {
		return proteincheck;
	}

	public void setProteincheck(String proteincheck) {
		this.proteincheck = proteincheck;
	}

	public String getFatcheck() {
		return fatcheck;
	}

	public void setFatcheck(String fatcheck) {
		this.fatcheck = fatcheck;
	}

	public String getCarbohydratecheck() {
		return carbohydratecheck;
	}

	public void setCarbohydratecheck(String carbohydratecheck) {
		this.carbohydratecheck = carbohydratecheck;
	}

	public List<YhFoodCategoryApi> getFindAllInfo() {
		return findAllInfo;
	}

	public void setFindAllInfo(List<YhFoodCategoryApi> findAllInfo) {
		this.findAllInfo = findAllInfo;
	}

	public Float getAverageintake7() {
		return averageintake7;
	}

	public void setAverageintake7(Float averageintake7) {
		this.averageintake7 = averageintake7;
	}

	public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public Integer getEcorddays() {
        return ecorddays;
    }

    public void setEcorddays(Integer ecorddays) {
        this.ecorddays = ecorddays;
    }

    public Integer getExceedingdays() {
        return exceedingdays;
    }

    public void setExceedingdays(Integer exceedingdays) {
        this.exceedingdays = exceedingdays;
    }

    public Integer getNorepetition() {
        return norepetition;
    }

    public void setNorepetition(Integer norepetition) {
        this.norepetition = norepetition;
    }

    public Float getAverageintake() {
        return averageintake;
    }

    public void setAverageintake(Float averageintake) {
        this.averageintake = averageintake;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Float getZao() {
        return zao;
    }

    public void setZao(Float zao) {
        this.zao = zao;
    }

    public Float getWu() {
        return wu;
    }

    public void setWu(Float wu) {
        this.wu = wu;
    }

    public Float getWan() {
        return wan;
    }

    public void setWan(Float wan) {
        this.wan = wan;
    }

    public Float getJia() {
        return jia;
    }

    public void setJia(Float jia) {
        this.jia = jia;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype == null ? null : foodtype.trim();
    }

    public Float getType1() {
        return type1;
    }

    public void setType1(Float type1) {
        this.type1 = type1;
    }

    public Float getType2() {
        return type2;
    }

    public void setType2(Float type2) {
        this.type2 = type2;
    }

    public Float getType3() {
        return type3;
    }

    public void setType3(Float type3) {
        this.type3 = type3;
    }

    public Float getType4() {
        return type4;
    }

    public void setType4(Float type4) {
        this.type4 = type4;
    }

    public Float getType5() {
        return type5;
    }

    public void setType5(Float type5) {
        this.type5 = type5;
    }

    public Float getType6() {
        return type6;
    }

    public void setType6(Float type6) {
        this.type6 = type6;
    }

    public Float getType7() {
        return type7;
    }

    public void setType7(Float type7) {
        this.type7 = type7;
    }

    public Float getType8() {
        return type8;
    }

    public void setType8(Float type8) {
        this.type8 = type8;
    }

    public Float getType9() {
        return type9;
    }

    public void setType9(Float type9) {
        this.type9 = type9;
    }

    public Float getType10() {
        return type10;
    }

    public void setType10(Float type10) {
        this.type10 = type10;
    }

    public Float getType11() {
        return type11;
    }

    public void setType11(Float type11) {
        this.type11 = type11;
    }

    public Float getType12() {
        return type12;
    }

    public void setType12(Float type12) {
        this.type12 = type12;
    }

    public Float getType13() {
        return type13;
    }

    public void setType13(Float type13) {
        this.type13 = type13;
    }

    public Float getType14() {
        return type14;
    }

    public void setType14(Float type14) {
        this.type14 = type14;
    }

    public Float getType15() {
        return type15;
    }

    public void setType15(Float type15) {
        this.type15 = type15;
    }
}