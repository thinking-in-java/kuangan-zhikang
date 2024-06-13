package com.yihe.applet.api.domain;

/**
 * 数据监测所有数据的最新数据
 * 
 * @author Administrator
 *
 */
public class MonitorToDayApp {
	/**
	 * 正常个数
	 */
	private int allzhengchang;
	/**
	 * 异常个数
	 */
	private int allyichang;
	/**
	 * 体温情况
	 */
	private int tiwensituation;
	/**
	 * 体重情况
	 */
	private int tizhongsituation;
	/**
	 * 血压情况
	 */
	private int xueyasituation;
	/**
	 * 血糖情况
	 */
	private int xuetangsituation;
	/**
	 * 血氧情况
	 */
	private int xueyangsituation;
	/**
	 * 心率情况
	 */
	private int xinlvsituation;
	/**
	 * 体脂情况
	 */
	private int tizhisituation;
	/**
	 * 血脂四项情况
	 */
	private int xuezhisituation;
	/**
	 * 肾功能情况
	 */
	private int shengnsituation;
	/**
	 * 肝功能情况X
	 */
	private int gangnsituation;
	/**
	 * 尿检情况
	 */
	private int niaojiansituation;
	/**
	 * 其他指标情况
	 */
	private int qitasituation;
	
	/**
	 * 血氧
	 */
	private Float oxygen;
	/**
	 * 血氧测量时间
	 */
	private String oxygentime;

	public Float getOxygen() {
		return oxygen;
	}

	public void setOxygen(Float oxygen) {
		this.oxygen = oxygen;
	}

	public String getOxygentime() {
		return oxygentime;
	}

	public void setOxygentime(String oxygentime) {
		this.oxygentime = oxygentime;
	}

	/**
	 * 体温
	 */
	private Float temperature;
	/**
	 * 体温测量时间
	 */
	private String temperatureTime;

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public String getTemperatureTime() {
		return temperatureTime;
	}

	public void setTemperatureTime(String temperatureTime) {
		this.temperatureTime = temperatureTime;
	}

	/**
	 * 身高
	 *
	 * @mbg.generated
	 */
	private Float uHeight;

	/**
	 * 体重
	 *
	 * @mbg.generated
	 */
	private Float uWeight;

	/**
	 * BMI测量时间
	 *
	 * @mbg.generated
	 */
	private String bmiEntryTime;

	/**
	 * 收缩压
	 *
	 * @mbg.generated
	 */
	private Integer sbp;

	/**
	 * 舒张压
	 *
	 * @mbg.generated
	 */
	private Integer dbp;
	/**
	 * 心率
	 *
	 * @mbg.generated
	 */
	private Integer xinlv;

	/**
	 * 血压测量时间
	 *
	 * @mbg.generated
	 */
	private String bloodPressureEntryTime;
	/**
	 * 心率测量时间
	 */
	private String xinlvEntryTime;

	public String getXinlvEntryTime() {
		return xinlvEntryTime;
	}

	public void setXinlvEntryTime(String xinlvEntryTime) {
		this.xinlvEntryTime = xinlvEntryTime;
	}

	/**
	 * 血糖值
	 *
	 * @mbg.generated
	 */
	private Float bs;
	/**
	 * 血糖监测类型
	 *
	 * @mbg.generated
	 */
	private String bsType;

	/**
	 * 血糖值测量时间
	 *
	 * @mbg.generated
	 */
	private String bloodSugerEntryTime;
	/**
	 * 脂肪率
	 *
	 * @mbg.generated
	 */
	private Float fatRate;

	/**
	 * 肌肉
	 *
	 * @mbg.generated
	 */
	private Float muscle;

	/**
	 * 骨骼
	 *
	 * @mbg.generated
	 */
	private Float bones;

	/**
	 * 体脂值测量时间
	 *
	 * @mbg.generated
	 */
	private String bodyFatEntryTime;

	/**
	 * 总胆固醇
	 *
	 * @mbg.generated
	 */
	private Float cholesterin;

	/**
	 * 甘油三酯
	 *
	 * @mbg.generated
	 */
	private Float tring;

	/**
	 * 高密度脂蛋白胆固醇
	 *
	 * @mbg.generated
	 */
	private Float hdl;

	/**
	 * 低密度脂蛋白胆固醇
	 *
	 * @mbg.generated
	 */
	private Float ldl;

	/**
	 * 血脂录入时间
	 *
	 * @mbg.generated
	 */
	private String bloodfatEntryTime;

	/**
	 * 肌酐
	 *
	 * @mbg.generated
	 */
	private String creatinine;

	/**
	 * 尿素氮
	 *
	 * @mbg.generated
	 */
	private String ureaNitrogen;

	/**
	 * 尿酸
	 *
	 * @mbg.generated
	 */
	private String uricAcid;
	/**
	 * 肾功能录入时间
	 *
	 * @mbg.generated
	 */
	private String renalEntryTime;
	/**
	 * 谷丙转氨酶
	 *
	 * @mbg.generated
	 */
	private String guBing;

	/**
	 * 谷草转氨酶
	 *
	 * @mbg.generated
	 */
	private String milletStraw;

	/**
	 * 血清白蛋白
	 *
	 * @mbg.generated
	 */
	private String serumAlbumin;

	/**
	 * 血清球蛋白
	 *
	 * @mbg.generated
	 */
	private String serumGlobulin;
	/**
	 * 肝功能录入时间
	 *
	 * @mbg.generated
	 */
	private String liverEntryTime;
	/**
	 * 尿微量蛋白
	 *
	 * @mbg.generated
	 */
	private String urineMicroprotein;

	/**
	 * 糖化血红蛋白
	 *
	 * @mbg.generated
	 */
	private String glycosylatedHemoglobin;

	/**
	 * 同型半胱氨酸
	 *
	 * @mbg.generated
	 */
	private String hcy;
	/**
	 * 其他功能录入时间
	 *
	 * @mbg.generated
	 */
	private String otherEntryTime;
	/**
	 * 心电图
	 */
	private String ecgImage;
	/**
	 * 心电图测量结果说明
	 */
	private String resurtText;

	/**
	 * 细胞检测
	 */
	private String celldetection;
	private String celldetectionTime;
	/**
	 * 量子检测
	 */
	private String quantumdetection;
	private String quantumdetectionTime;

	private String renalfunctionType;

	//血红蛋白
	private int xhdbsituation;
	private Float hemoglobin;

	private String xhdbEntryTime;


	public int getXhdbsituation() {
		return xhdbsituation;
	}

	public void setXhdbsituation(int xhdbsituation) {
		this.xhdbsituation = xhdbsituation;
	}

	public Float getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(Float hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

	public String getXhdbEntryTime() {
		return xhdbEntryTime;
	}

	public void setXhdbEntryTime(String xhdbEntryTime) {
		this.xhdbEntryTime = xhdbEntryTime;
	}

	public String getCelldetection() {
		return celldetection;
	}

	public void setCelldetection(String celldetection) {
		this.celldetection = celldetection;
	}

	public String getCelldetectionTime() {
		return celldetectionTime;
	}

	public void setCelldetectionTime(String celldetectionTime) {
		this.celldetectionTime = celldetectionTime;
	}

	public String getQuantumdetection() {
		return quantumdetection;
	}

	public void setQuantumdetection(String quantumdetection) {
		this.quantumdetection = quantumdetection;
	}

	public String getQuantumdetectionTime() {
		return quantumdetectionTime;
	}

	public void setQuantumdetectionTime(String quantumdetectionTime) {
		this.quantumdetectionTime = quantumdetectionTime;
	}

	public String getResurtText() {
		return resurtText;
	}

	public void setResurtText(String resurtText) {
		this.resurtText = resurtText;
	}

	/**
	 * 心电图录入时间
	 *
	 * @mbg.generated
	 */
	private String ecgEntryTime;
	/**
	 * 红细胞
	 *
	 * @mbg.generated
	 */
	private String redbloodcell;
	/**
	 * 红细胞
	 *
	 * @mbg.generated
	 */
	private String whitebloodcell;
	/**
	 * 尿蛋白
	 *
	 * @mbg.generated
	 */
	private String urineprotein;
	/**
	 * 酮体
	 *
	 * @mbg.generated
	 */
	private String ketone;
	/**
	 * 尿检功能录入时间
	 *
	 * @mbg.generated
	 */
	private String ubaEntryTime;


	/**
	 * avi
	 * @return
	 */
	private  Integer avi;

	/**
	 * api
	 * @return
	 */
	private Integer api;
	/**
	 * api
	 * @return
	 */
	private Integer csbp;
	/**
	 * api
	 * @return
	 */
	private Integer capp;

	private String dongmaiTime;

	private Integer dongmaisituation;

	public Integer getAvi() {
		return avi;
	}

	public void setAvi(Integer avi) {
		this.avi = avi;
	}

	public Integer getApi() {
		return api;
	}

	public void setApi(Integer api) {
		this.api = api;
	}

	public Integer getCsbp() {
		return csbp;
	}

	public void setCsbp(Integer csbp) {
		this.csbp = csbp;
	}

	public Integer getCapp() {
		return capp;
	}

	public void setCapp(Integer capp) {
		this.capp = capp;
	}

	public String getDongmaiTime() {
		return dongmaiTime;
	}

	public void setDongmaiTime(String dongmaiTime) {
		this.dongmaiTime = dongmaiTime;
	}

	public Integer getDongmaisituation() {
		return dongmaisituation;
	}

	public void setDongmaisituation(Integer dongmaisituation) {
		this.dongmaisituation = dongmaisituation;
	}

	public String getRenalfunctionType() {
		return renalfunctionType;
	}

	public void setRenalfunctionType(String renalfunctionType) {
		this.renalfunctionType = renalfunctionType;
	}

	public String getWhitebloodcell() {
		return whitebloodcell;
	}

	public void setWhitebloodcell(String whitebloodcell) {
		this.whitebloodcell = whitebloodcell;
	}

	public Float getuHeight() {
		return uHeight;
	}

	public void setuHeight(Float uHeight) {
		this.uHeight = uHeight;
	}

	public Float getuWeight() {
		return uWeight;
	}

	public void setuWeight(Float uWeight) {
		this.uWeight = uWeight;
	}

	public String getBmiEntryTime() {
		return bmiEntryTime;
	}

	public void setBmiEntryTime(String bmiEntryTime) {
		this.bmiEntryTime = bmiEntryTime;
	}

	public Integer getSbp() {
		return sbp;
	}

	public void setSbp(Integer sbp) {
		this.sbp = sbp;
	}

	public Integer getDbp() {
		return dbp;
	}

	public void setDbp(Integer dbp) {
		this.dbp = dbp;
	}

	public Integer getXinlv() {
		return xinlv;
	}

	public void setXinlv(Integer xinlv) {
		this.xinlv = xinlv;
	}

	public String getBloodPressureEntryTime() {
		return bloodPressureEntryTime;
	}

	public void setBloodPressureEntryTime(String bloodPressureEntryTime) {
		this.bloodPressureEntryTime = bloodPressureEntryTime;
	}

	public Float getBs() {
		return bs;
	}

	public void setBs(Float bs) {
		this.bs = bs;
	}

	public String getBloodSugerEntryTime() {
		return bloodSugerEntryTime;
	}

	public void setBloodSugerEntryTime(String bloodSugerEntryTime) {
		this.bloodSugerEntryTime = bloodSugerEntryTime;
	}

	public Float getFatRate() {
		return fatRate;
	}

	public void setFatRate(Float fatRate) {
		this.fatRate = fatRate;
	}

	public Float getMuscle() {
		return muscle;
	}

	public void setMuscle(Float muscle) {
		this.muscle = muscle;
	}

	public Float getBones() {
		return bones;
	}

	public void setBones(Float bones) {
		this.bones = bones;
	}

	public String getBodyFatEntryTime() {
		return bodyFatEntryTime;
	}

	public void setBodyFatEntryTime(String bodyFatEntryTime) {
		this.bodyFatEntryTime = bodyFatEntryTime;
	}

	public Float getCholesterin() {
		return cholesterin;
	}

	public void setCholesterin(Float cholesterin) {
		this.cholesterin = cholesterin;
	}

	public Float getTring() {
		return tring;
	}

	public void setTring(Float tring) {
		this.tring = tring;
	}

	public Float getHdl() {
		return hdl;
	}

	public void setHdl(Float hdl) {
		this.hdl = hdl;
	}

	public Float getLdl() {
		return ldl;
	}

	public void setLdl(Float ldl) {
		this.ldl = ldl;
	}

	public String getBloodfatEntryTime() {
		return bloodfatEntryTime;
	}

	public void setBloodfatEntryTime(String bloodfatEntryTime) {
		this.bloodfatEntryTime = bloodfatEntryTime;
	}

	public String getCreatinine() {
		return creatinine;
	}

	public void setCreatinine(String creatinine) {
		this.creatinine = creatinine;
	}

	public String getUreaNitrogen() {
		return ureaNitrogen;
	}

	public void setUreaNitrogen(String ureaNitrogen) {
		this.ureaNitrogen = ureaNitrogen;
	}

	public String getUricAcid() {
		return uricAcid;
	}

	public void setUricAcid(String uricAcid) {
		this.uricAcid = uricAcid;
	}

	public String getRenalEntryTime() {
		return renalEntryTime;
	}

	public void setRenalEntryTime(String renalEntryTime) {
		this.renalEntryTime = renalEntryTime;
	}

	public String getGuBing() {
		return guBing;
	}

	public void setGuBing(String guBing) {
		this.guBing = guBing;
	}

	public String getMilletStraw() {
		return milletStraw;
	}

	public void setMilletStraw(String milletStraw) {
		this.milletStraw = milletStraw;
	}

	public String getSerumAlbumin() {
		return serumAlbumin;
	}

	public void setSerumAlbumin(String serumAlbumin) {
		this.serumAlbumin = serumAlbumin;
	}

	public String getSerumGlobulin() {
		return serumGlobulin;
	}

	public void setSerumGlobulin(String serumGlobulin) {
		this.serumGlobulin = serumGlobulin;
	}

	public String getLiverEntryTime() {
		return liverEntryTime;
	}

	public void setLiverEntryTime(String liverEntryTime) {
		this.liverEntryTime = liverEntryTime;
	}

	public String getUrineMicroprotein() {
		return urineMicroprotein;
	}

	public void setUrineMicroprotein(String urineMicroprotein) {
		this.urineMicroprotein = urineMicroprotein;
	}

	public String getGlycosylatedHemoglobin() {
		return glycosylatedHemoglobin;
	}

	public void setGlycosylatedHemoglobin(String glycosylatedHemoglobin) {
		this.glycosylatedHemoglobin = glycosylatedHemoglobin;
	}

	public String getHcy() {
		return hcy;
	}

	public void setHcy(String hcy) {
		this.hcy = hcy;
	}

	public String getOtherEntryTime() {
		return otherEntryTime;
	}

	public void setOtherEntryTime(String otherEntryTime) {
		this.otherEntryTime = otherEntryTime;
	}

	public String getEcgImage() {
		return ecgImage;
	}

	public void setEcgImage(String ecgImage) {
		this.ecgImage = ecgImage;
	}

	public String getEcgEntryTime() {
		return ecgEntryTime;
	}

	public void setEcgEntryTime(String ecgEntryTime) {
		this.ecgEntryTime = ecgEntryTime;
	}

	public String getRedbloodcell() {
		return redbloodcell;
	}

	public void setRedbloodcell(String redbloodcell) {
		this.redbloodcell = redbloodcell;
	}

	public String getUrineprotein() {
		return urineprotein;
	}

	public void setUrineprotein(String urineprotein) {
		this.urineprotein = urineprotein;
	}

	public String getKetone() {
		return ketone;
	}

	public void setKetone(String ketone) {
		this.ketone = ketone;
	}

	public String getUbaEntryTime() {
		return ubaEntryTime;
	}

	public void setUbaEntryTime(String ubaEntryTime) {
		this.ubaEntryTime = ubaEntryTime;
	}

	public String getBsType() {
		return bsType;
	}

	public void setBsType(String bsType) {
		this.bsType = bsType;
	}

	public int getAllzhengchang() {
		return allzhengchang;
	}

	public void setAllzhengchang(int allzhengchang) {
		this.allzhengchang = allzhengchang;
	}

	public int getAllyichang() {
		return allyichang;
	}

	public void setAllyichang(int allyichang) {
		this.allyichang = allyichang;
	}

	public int getTiwensituation() {
		return tiwensituation;
	}

	public void setTiwensituation(int tiwensituation) {
		this.tiwensituation = tiwensituation;
	}

	public int getTizhongsituation() {
		return tizhongsituation;
	}

	public void setTizhongsituation(int tizhongsituation) {
		this.tizhongsituation = tizhongsituation;
	}

	public int getXueyasituation() {
		return xueyasituation;
	}

	public void setXueyasituation(int xueyasituation) {
		this.xueyasituation = xueyasituation;
	}

	public int getXuetangsituation() {
		return xuetangsituation;
	}

	public void setXuetangsituation(int xuetangsituation) {
		this.xuetangsituation = xuetangsituation;
	}

	public int getXueyangsituation() {
		return xueyangsituation;
	}

	public void setXueyangsituation(int xueyangsituation) {
		this.xueyangsituation = xueyangsituation;
	}

	public int getXinlvsituation() {
		return xinlvsituation;
	}

	public void setXinlvsituation(int xinlvsituation) {
		this.xinlvsituation = xinlvsituation;
	}

	public int getTizhisituation() {
		return tizhisituation;
	}

	public void setTizhisituation(int tizhisituation) {
		this.tizhisituation = tizhisituation;
	}

	public int getXuezhisituation() {
		return xuezhisituation;
	}

	public void setXuezhisituation(int xuezhisituation) {
		this.xuezhisituation = xuezhisituation;
	}

	public int getShengnsituation() {
		return shengnsituation;
	}

	public void setShengnsituation(int shengnsituation) {
		this.shengnsituation = shengnsituation;
	}

	public int getGangnsituation() {
		return gangnsituation;
	}

	public void setGangnsituation(int gangnsituation) {
		this.gangnsituation = gangnsituation;
	}

	public int getNiaojiansituation() {
		return niaojiansituation;
	}

	public void setNiaojiansituation(int niaojiansituation) {
		this.niaojiansituation = niaojiansituation;
	}

	public int getQitasituation() {
		return qitasituation;
	}

	public void setQitasituation(int qitasituation) {
		this.qitasituation = qitasituation;
	}
	

}
