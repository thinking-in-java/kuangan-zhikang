package com.yihe.system.domain;

import java.util.Date;

public class YhDiseaseRiskResultAppVOJO {
	
	private String columnName;
	
	private String did;
	private String rs;


	public YhDiseaseRiskResultAppVOJO() {
	}


	public YhDiseaseRiskResultAppVOJO(String columnName, String did, String rs) {
		this.columnName = columnName;
		this.did = did;
		this.rs = rs;
	}

	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	private Long id;
    /** 疾病ID */
    private String diseaseid;

    /** 用户id */
    private String userCode;

    /** 请求序列号 */
    private String requestSerialize;

    /** 年龄对应的风险内容 */
    private String age;

    /** 高密度脂蛋白胆固醇对应的风险内容 */
    private String hdlc;

    /** 低密度脂蛋白胆固醇对应的风险内容 */
    private String ldlc;

    /** 蔬菜水果对应的风险内容 */
    private String food;

    /** 体力活动水平对应的风险内容 */
    private String sport;

    /** 高脂饮食对应的风险内容 */
    private String b4;

    /** 吸烟对应的风险内容 */
    private String smoke;

    /** 饮酒情况对应的风险内容 */
    private String drink;

    /** 红外检测报告对应的风险内容 */
    private String hwCheck;

    /** 体重指数（BMI）对应的风险内容 */
    private String bmi;

    /** 腰围对应的风险内容 */
    private String waist;

    /** 糖尿病史对应的风险内容 */
    private String diabHis;

    /** 高血压病史对应的风险内容 */
    private String hbpHis;

    /** 空腹血糖对应的风险内容 */
    private String fbg;

    /** 收缩压对应的风险内容 */
    private String sbp;

    /** 总胆固醇对应的风险内容 */
    private String cholesterol;

    /** 甘油三酯对应的风险内容 */
    private String tg;

    /** 高血脂的风险内容 */
    private String bf;

    /** 舒张压对应的风险内容 */
    private String dbp;

    /** 高血压家族史对应的风险内容 */
    private String hbps;

    /** 父母髋部骨折史对应的风险内容 */
    private String hfs;

    /** 个人既往骨折史的风险内容 */
    private String hfHis;

    /** 长期服用肾上腺皮质激素的风险内容 */
    private String c1;

    /** 牛奶摄入对应的风险内容 */
    private String milk;

    /** 反式脂肪酸对应的风险内容 */
    private String tfa;

    /** 冠心病家族史对应的风险内容 */
    private String chds;

    /** 糖尿病家族史对应的风险内容 */
    private String dms;

    /** 被动吸烟对应的风险内容 */
    private String smokeSencod;

    /** 肺癌家族史对应的风险内容 */
    private String lungs;

    /** 肺气肿病史对应的风险内容 */
    private String emphyHis;

    /** 粉尘接触史对应的风险内容 */
    private String asbestosContactHis;

    /** 近两年是否新房装修对应的风险内容 */
    private String decorateHouse;

    /** 精神压力对应的风险内容 */
    private String psyNervous;

    /** 慢性支气管炎病史对应的风险内容 */
    private String bronchHis;

    /** 油烟接触史对应的风险内容 */
    private String lampblackContact;

    /** 痛风家族史对应的风险内容 */
    private String gouts;

    /** 饮茶情况对应的风险内容 */
    private String tea;

    /** 高嘌呤饮食对应的风险内容 */
    private String foodPurine;

    /** 冠心病史对应的风险内容 */
    private String chdHis;

    /** 血尿酸对应的风险内容 */
    private String bua;

    /** 避孕药服用对应的风险内容 */
    private String medUsePill;

    /** 使用丰乳产品或做过乳房整形手术对应的风险内容 */
    private String breastPlasticSurgery;

    /** 乳腺癌家族史对应的风险内容 */
    private String bcs;

    /** 良性乳腺疾病史对应的风险内容 */
    private String begindBreastHis;

    /** 初潮年龄对应的风险内容 */
    private String femaleMenstruation;

    /** 初产年龄对应的风险内容 */
    private String femaleTire;

    /** 哺乳喂养时间对应的风险内容 */
    private String femaleBreastTime;

    /** 绝经年龄对应的风险内容 */
    private String femaleMenopauseAge;

    /** 雌激素替代疗法对应的风险内容 */
    private String hasMedErt;

    /** 脑卒中家族史对应的风险内容 */
    private String css;

    /** 心血管病史对应的风险内容 */
    private String cardHis;

    /** 左心室肥大对应的风险内容 */
    private String lvdHis;

    /** 房颤病史对应的风险内容 */
    private String afHis;

    /** 前列腺癌家族史对应的风险内容 */
    private String pcs;

    /** 前列腺炎或肥大病史对应的风险内容 */
    private String prostateDisease;

    /** 尿路感染对应的风险内容 */
    private String urinaryTractInfection;

    /** 尿频、尿痛、尿不尽、排尿困难症状对应的风险内容 */
    private String urineSymptom;

    /** T值对应的风险内容 */
    private String tScore;

    /** 类风湿关节炎对应的风险内容 */
    private String raHis;

    /** 居住环境对应的风险内容 */
    private String livingEnvir;

    /** 啤酒摄入对应的风险内容 */
    private String foodDrinkBeer;

    private String remark;
    /** 时间 */
    private Date qtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDiseaseid() {
		return diseaseid;
	}
	public void setDiseaseid(String diseaseid) {
		this.diseaseid = diseaseid;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRequestSerialize() {
		return requestSerialize;
	}
	public void setRequestSerialize(String requestSerialize) {
		this.requestSerialize = requestSerialize;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHdlc() {
		return hdlc;
	}
	public void setHdlc(String hdlc) {
		this.hdlc = hdlc;
	}
	public String getLdlc() {
		return ldlc;
	}
	public void setLdlc(String ldlc) {
		this.ldlc = ldlc;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getB4() {
		return b4;
	}
	public void setB4(String b4) {
		this.b4 = b4;
	}
	public String getSmoke() {
		return smoke;
	}
	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}
	public String getDrink() {
		return drink;
	}
	public void setDrink(String drink) {
		this.drink = drink;
	}
	public String getHwCheck() {
		return hwCheck;
	}
	public void setHwCheck(String hwCheck) {
		this.hwCheck = hwCheck;
	}
	public String getBmi() {
		return bmi;
	}
	public void setBmi(String bmi) {
		this.bmi = bmi;
	}
	public String getWaist() {
		return waist;
	}
	public void setWaist(String waist) {
		this.waist = waist;
	}
	public String getDiabHis() {
		return diabHis;
	}
	public void setDiabHis(String diabHis) {
		this.diabHis = diabHis;
	}
	public String getHbpHis() {
		return hbpHis;
	}
	public void setHbpHis(String hbpHis) {
		this.hbpHis = hbpHis;
	}
	public String getFbg() {
		return fbg;
	}
	public void setFbg(String fbg) {
		this.fbg = fbg;
	}
	public String getSbp() {
		return sbp;
	}
	public void setSbp(String sbp) {
		this.sbp = sbp;
	}
	public String getCholesterol() {
		return cholesterol;
	}
	public void setCholesterol(String cholesterol) {
		this.cholesterol = cholesterol;
	}
	public String getTg() {
		return tg;
	}
	public void setTg(String tg) {
		this.tg = tg;
	}
	public String getBf() {
		return bf;
	}
	public void setBf(String bf) {
		this.bf = bf;
	}
	public String getDbp() {
		return dbp;
	}
	public void setDbp(String dbp) {
		this.dbp = dbp;
	}
	public String getHbps() {
		return hbps;
	}
	public void setHbps(String hbps) {
		this.hbps = hbps;
	}
	public String getHfs() {
		return hfs;
	}
	public void setHfs(String hfs) {
		this.hfs = hfs;
	}
	public String getHfHis() {
		return hfHis;
	}
	public void setHfHis(String hfHis) {
		this.hfHis = hfHis;
	}
	public String getC1() {
		return c1;
	}
	public void setC1(String c1) {
		this.c1 = c1;
	}
	public String getMilk() {
		return milk;
	}
	public void setMilk(String milk) {
		this.milk = milk;
	}
	public String getTfa() {
		return tfa;
	}
	public void setTfa(String tfa) {
		this.tfa = tfa;
	}
	public String getChds() {
		return chds;
	}
	public void setChds(String chds) {
		this.chds = chds;
	}
	public String getDms() {
		return dms;
	}
	public void setDms(String dms) {
		this.dms = dms;
	}
	public String getSmokeSencod() {
		return smokeSencod;
	}
	public void setSmokeSencod(String smokeSencod) {
		this.smokeSencod = smokeSencod;
	}
	public String getLungs() {
		return lungs;
	}
	public void setLungs(String lungs) {
		this.lungs = lungs;
	}
	public String getEmphyHis() {
		return emphyHis;
	}
	public void setEmphyHis(String emphyHis) {
		this.emphyHis = emphyHis;
	}
	public String getAsbestosContactHis() {
		return asbestosContactHis;
	}
	public void setAsbestosContactHis(String asbestosContactHis) {
		this.asbestosContactHis = asbestosContactHis;
	}

	public String getDecorateHouse() {
		return decorateHouse;
	}

	public void setDecorateHouse(String decorateHouse) {
		this.decorateHouse = decorateHouse;
	}

	public String getPsyNervous() {
		return psyNervous;
	}
	public void setPsyNervous(String psyNervous) {
		this.psyNervous = psyNervous;
	}
	public String getBronchHis() {
		return bronchHis;
	}
	public void setBronchHis(String bronchHis) {
		this.bronchHis = bronchHis;
	}
	public String getLampblackContact() {
		return lampblackContact;
	}
	public void setLampblackContact(String lampblackContact) {
		this.lampblackContact = lampblackContact;
	}
	public String getGouts() {
		return gouts;
	}
	public void setGouts(String gouts) {
		this.gouts = gouts;
	}
	public String getTea() {
		return tea;
	}
	public void setTea(String tea) {
		this.tea = tea;
	}
	public String getFoodPurine() {
		return foodPurine;
	}
	public void setFoodPurine(String foodPurine) {
		this.foodPurine = foodPurine;
	}
	public String getChdHis() {
		return chdHis;
	}
	public void setChdHis(String chdHis) {
		this.chdHis = chdHis;
	}
	public String getBua() {
		return bua;
	}
	public void setBua(String bua) {
		this.bua = bua;
	}
	public String getMedUsePill() {
		return medUsePill;
	}
	public void setMedUsePill(String medUsePill) {
		this.medUsePill = medUsePill;
	}
	public String getBreastPlasticSurgery() {
		return breastPlasticSurgery;
	}
	public void setBreastPlasticSurgery(String breastPlasticSurgery) {
		this.breastPlasticSurgery = breastPlasticSurgery;
	}
	public String getBcs() {
		return bcs;
	}
	public void setBcs(String bcs) {
		this.bcs = bcs;
	}
	public String getBegindBreastHis() {
		return begindBreastHis;
	}
	public void setBegindBreastHis(String begindBreastHis) {
		this.begindBreastHis = begindBreastHis;
	}
	public String getFemaleMenstruation() {
		return femaleMenstruation;
	}
	public void setFemaleMenstruation(String femaleMenstruation) {
		this.femaleMenstruation = femaleMenstruation;
	}
	public String getFemaleTire() {
		return femaleTire;
	}
	public void setFemaleTire(String femaleTire) {
		this.femaleTire = femaleTire;
	}
	public String getFemaleBreastTime() {
		return femaleBreastTime;
	}
	public void setFemaleBreastTime(String femaleBreastTime) {
		this.femaleBreastTime = femaleBreastTime;
	}
	public String getFemaleMenopauseAge() {
		return femaleMenopauseAge;
	}
	public void setFemaleMenopauseAge(String femaleMenopauseAge) {
		this.femaleMenopauseAge = femaleMenopauseAge;
	}
	public String getHasMedErt() {
		return hasMedErt;
	}
	public void setHasMedErt(String hasMedErt) {
		this.hasMedErt = hasMedErt;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getCardHis() {
		return cardHis;
	}
	public void setCardHis(String cardHis) {
		this.cardHis = cardHis;
	}
	public String getLvdHis() {
		return lvdHis;
	}
	public void setLvdHis(String lvdHis) {
		this.lvdHis = lvdHis;
	}
	public String getAfHis() {
		return afHis;
	}
	public void setAfHis(String afHis) {
		this.afHis = afHis;
	}
	public String getPcs() {
		return pcs;
	}
	public void setPcs(String pcs) {
		this.pcs = pcs;
	}
	public String getProstateDisease() {
		return prostateDisease;
	}
	public void setProstateDisease(String prostateDisease) {
		this.prostateDisease = prostateDisease;
	}
	public String getUrinaryTractInfection() {
		return urinaryTractInfection;
	}
	public void setUrinaryTractInfection(String urinaryTractInfection) {
		this.urinaryTractInfection = urinaryTractInfection;
	}
	public String getUrineSymptom() {
		return urineSymptom;
	}
	public void setUrineSymptom(String urineSymptom) {
		this.urineSymptom = urineSymptom;
	}
	public String gettScore() {
		return tScore;
	}
	public void settScore(String tScore) {
		this.tScore = tScore;
	}
	public String getRaHis() {
		return raHis;
	}
	public void setRaHis(String raHis) {
		this.raHis = raHis;
	}
	public String getLivingEnvir() {
		return livingEnvir;
	}
	public void setLivingEnvir(String livingEnvir) {
		this.livingEnvir = livingEnvir;
	}
	public String getFoodDrinkBeer() {
		return foodDrinkBeer;
	}
	public void setFoodDrinkBeer(String foodDrinkBeer) {
		this.foodDrinkBeer = foodDrinkBeer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getQtime() {
		return qtime;
	}
	public void setQtime(Date qtime) {
		this.qtime = qtime;
	}

}
