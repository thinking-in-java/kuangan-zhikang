package com.yihe.system.domain;

import java.io.Serializable;
import java.util.List;

public class YhExportAll implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private String Bs;
	private String bloodsuger_Entry_Time;
	private Float fat_rate;
	private Float muscle;
	private Float bones;
	private Float visceral_level;
	private Float water;
	private Float heat;
	private Float protein;
	private Float basal_met;
	private String bodyfat_Entry_Time;
	private Double U_Height;
	private Double U_Weight;
	private String bmi_Entry_Time;
	private String Sbp;
	private String Dbp;
	private String xinlv;
	private String bloodpressure_Entry_Time;
	private Float Cholesterin;
	private Float Tring;
	private Float Hdl;
	private Float Ldl;
	private String bloofat_Entry_Time;
	private String gu_Bing;
	private String millet_straw;
	private String serum_albumin;
	private String serum_globulin;
	private String insertTime;
	private String addDate;
	private String env;
	private String glucose;
	private String bilirubin;
	private String ketone;
	private String proportion;
	private String redBloodCell;
	private String ph;
	private String urineProtein;
	private String urobilinogen;
	private String nitrite;
	private String whiteBloodCell;
	private String urine_microprotein;
	private String glycosylated_hemoglobin;
	private String hcy;
	private String other_insertTime;
	private String time;
	private String creatinine;
	private String urea_nitrogen;
	private String uric_acid;
	private String bsevn;

	public String getBsevn() {
		return bsevn;
	}

	public void setBsevn(String bsevn) {
		this.bsevn = bsevn;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	//血糖
	List<YhExprotBloodSuger> bloodsuger;
	//体脂
	List<YhExportBodyFat> bodyfat;
	//BMI
	List<YhExprotBMI> bmi;
	//血压
	List<YhExprotBloodPressure> bloodpressure;
	//血脂四项
	List<YhExprotBloodFat> bloofat;
	//肝功能
	List<YhExportLiver> liver;
	//尿检
	List<YhExportUserUBA> uba;
	//其它
	List<YhExportOther> other;

	List<YhExportRenalFunction> yhExportRenalFunctions;

	public String getCreatinine() {
		return creatinine;
	}

	public void setCreatinine(String creatinine) {
		this.creatinine = creatinine;
	}

	public String getUrea_nitrogen() {
		return urea_nitrogen;
	}

	public void setUrea_nitrogen(String urea_nitrogen) {
		this.urea_nitrogen = urea_nitrogen;
	}

	public String getUric_acid() {
		return uric_acid;
	}

	public void setUric_acid(String uric_acid) {
		this.uric_acid = uric_acid;
	}

	public List<YhExportRenalFunction> getYhExportRenalFunctions() {
		return yhExportRenalFunctions;
	}

	public void setYhExportRenalFunctions(List<YhExportRenalFunction> yhExportRenalFunctions) {
		this.yhExportRenalFunctions = yhExportRenalFunctions;
	}

	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getUser_Code() {
		return User_Code;
	}
	public void setUser_Code(String user_Code) {
		User_Code = user_Code;
	}
	public String getBs() {
		return Bs;
	}
	public void setBs(String bs) {
		Bs = bs;
	}
	public String getBloodsuger_Entry_Time() {
		return bloodsuger_Entry_Time;
	}
	public void setBloodsuger_Entry_Time(String bloodsuger_Entry_Time) {
		this.bloodsuger_Entry_Time = bloodsuger_Entry_Time;
	}
	public Float getFat_rate() {
		return fat_rate;
	}
	public void setFat_rate(Float fat_rate) {
		this.fat_rate = fat_rate;
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
	public Float getVisceral_level() {
		return visceral_level;
	}
	public void setVisceral_level(Float visceral_level) {
		this.visceral_level = visceral_level;
	}
	public Float getWater() {
		return water;
	}
	public void setWater(Float water) {
		this.water = water;
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
	public Float getBasal_met() {
		return basal_met;
	}
	public void setBasal_met(Float basal_met) {
		this.basal_met = basal_met;
	}
	public String getBodyfat_Entry_Time() {
		return bodyfat_Entry_Time;
	}
	public void setBodyfat_Entry_Time(String bodyfat_Entry_Time) {
		this.bodyfat_Entry_Time = bodyfat_Entry_Time;
	}

	public Double getU_Height() {
		return U_Height;
	}

	public void setU_Height(Double u_Height) {
		U_Height = u_Height;
	}

	public Double getU_Weight() {
		return U_Weight;
	}

	public void setU_Weight(Double u_Weight) {
		U_Weight = u_Weight;
	}

	public String getBmi_Entry_Time() {
		return bmi_Entry_Time;
	}
	public void setBmi_Entry_Time(String bmi_Entry_Time) {
		this.bmi_Entry_Time = bmi_Entry_Time;
	}
	public String getSbp() {
		return Sbp;
	}
	public void setSbp(String sbp) {
		Sbp = sbp;
	}
	public String getDbp() {
		return Dbp;
	}
	public void setDbp(String dbp) {
		Dbp = dbp;
	}
	public String getXinlv() {
		return xinlv;
	}
	public void setXinlv(String xinlv) {
		this.xinlv = xinlv;
	}
	public String getBloodpressure_Entry_Time() {
		return bloodpressure_Entry_Time;
	}
	public void setBloodpressure_Entry_Time(String bloodpressure_Entry_Time) {
		this.bloodpressure_Entry_Time = bloodpressure_Entry_Time;
	}
	public Float getCholesterin() {
		return Cholesterin;
	}
	public void setCholesterin(Float cholesterin) {
		Cholesterin = cholesterin;
	}
	public Float getTring() {
		return Tring;
	}
	public void setTring(Float tring) {
		Tring = tring;
	}
	public Float getHdl() {
		return Hdl;
	}
	public void setHdl(Float hdl) {
		Hdl = hdl;
	}
	public Float getLdl() {
		return Ldl;
	}
	public void setLdl(Float ldl) {
		Ldl = ldl;
	}
	public String getBloofat_Entry_Time() {
		return bloofat_Entry_Time;
	}
	public void setBloofat_Entry_Time(String bloofat_Entry_Time) {
		this.bloofat_Entry_Time = bloofat_Entry_Time;
	}
	public String getGu_Bing() {
		return gu_Bing;
	}
	public void setGu_Bing(String gu_Bing) {
		this.gu_Bing = gu_Bing;
	}
	public String getMillet_straw() {
		return millet_straw;
	}
	public void setMillet_straw(String millet_straw) {
		this.millet_straw = millet_straw;
	}
	public String getSerum_albumin() {
		return serum_albumin;
	}
	public void setSerum_albumin(String serum_albumin) {
		this.serum_albumin = serum_albumin;
	}
	public String getSerum_globulin() {
		return serum_globulin;
	}
	public void setSerum_globulin(String serum_globulin) {
		this.serum_globulin = serum_globulin;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getGlucose() {
		return glucose;
	}
	public void setGlucose(String glucose) {
		this.glucose = glucose;
	}
	public String getBilirubin() {
		return bilirubin;
	}
	public void setBilirubin(String bilirubin) {
		this.bilirubin = bilirubin;
	}
	public String getKetone() {
		return ketone;
	}
	public void setKetone(String ketone) {
		this.ketone = ketone;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getRedBloodCell() {
		return redBloodCell;
	}
	public void setRedBloodCell(String redBloodCell) {
		this.redBloodCell = redBloodCell;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getUrineProtein() {
		return urineProtein;
	}
	public void setUrineProtein(String urineProtein) {
		this.urineProtein = urineProtein;
	}
	public String getUrobilinogen() {
		return urobilinogen;
	}
	public void setUrobilinogen(String urobilinogen) {
		this.urobilinogen = urobilinogen;
	}
	public String getNitrite() {
		return nitrite;
	}
	public void setNitrite(String nitrite) {
		this.nitrite = nitrite;
	}
	public String getWhiteBloodCell() {
		return whiteBloodCell;
	}
	public void setWhiteBloodCell(String whiteBloodCell) {
		this.whiteBloodCell = whiteBloodCell;
	}
	public String getUrine_microprotein() {
		return urine_microprotein;
	}
	public void setUrine_microprotein(String urine_microprotein) {
		this.urine_microprotein = urine_microprotein;
	}
	public String getGlycosylated_hemoglobin() {
		return glycosylated_hemoglobin;
	}
	public void setGlycosylated_hemoglobin(String glycosylated_hemoglobin) {
		this.glycosylated_hemoglobin = glycosylated_hemoglobin;
	}
	public String getHcy() {
		return hcy;
	}
	public void setHcy(String hcy) {
		this.hcy = hcy;
	}
	public String getOther_insertTime() {
		return other_insertTime;
	}
	public void setOther_insertTime(String other_insertTime) {
		this.other_insertTime = other_insertTime;
	}
	public List<YhExprotBloodSuger> getBloodsuger() {
		return bloodsuger;
	}
	public void setBloodsuger(List<YhExprotBloodSuger> bloodsuger) {
		this.bloodsuger = bloodsuger;
	}
	public List<YhExportBodyFat> getBodyfat() {
		return bodyfat;
	}
	public void setBodyfat(List<YhExportBodyFat> bodyfat) {
		this.bodyfat = bodyfat;
	}
	public List<YhExprotBMI> getBmi() {
		return bmi;
	}
	public void setBmi(List<YhExprotBMI> bmi) {
		this.bmi = bmi;
	}
	public List<YhExprotBloodPressure> getBloodpressure() {
		return bloodpressure;
	}
	public void setBloodpressure(List<YhExprotBloodPressure> bloodpressure) {
		this.bloodpressure = bloodpressure;
	}
	public List<YhExprotBloodFat> getBloofat() {
		return bloofat;
	}
	public void setBloofat(List<YhExprotBloodFat> bloofat) {
		this.bloofat = bloofat;
	}
	public List<YhExportLiver> getLiver() {
		return liver;
	}
	public void setLiver(List<YhExportLiver> liver) {
		this.liver = liver;
	}
	public List<YhExportUserUBA> getUba() {
		return uba;
	}
	public void setUba(List<YhExportUserUBA> uba) {
		this.uba = uba;
	}
	public List<YhExportOther> getOther() {
		return other;
	}
	public void setOther(List<YhExportOther> other) {
		this.other = other;
	}
	@Override
	public String toString() {
		return "YhExportAll [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", Bs=" + Bs + ", bloodsuger_Entry_Time=" + bloodsuger_Entry_Time + ", fat_rate="
				+ fat_rate + ", muscle=" + muscle + ", bones=" + bones + ", visceral_level=" + visceral_level
				+ ", water=" + water + ", heat=" + heat + ", protein=" + protein + ", basal_met=" + basal_met
				+ ", bodyfat_Entry_Time=" + bodyfat_Entry_Time + ", U_Height=" + U_Height + ", U_Weight=" + U_Weight
				+ ", bmi_Entry_Time=" + bmi_Entry_Time + ", Sbp=" + Sbp + ", Dbp=" + Dbp + ", xinlv=" + xinlv
				+ ", bloodpressure_Entry_Time=" + bloodpressure_Entry_Time + ", Cholesterin=" + Cholesterin + ", Tring="
				+ Tring + ", Hdl=" + Hdl + ", Ldl=" + Ldl + ", bloofat_Entry_Time=" + bloofat_Entry_Time + ", gu_Bing="
				+ gu_Bing + ", millet_straw=" + millet_straw + ", serum_albumin=" + serum_albumin + ", serum_globulin="
				+ serum_globulin + ", insertTime=" + insertTime + ", addDate=" + addDate + ", env=" + env + ", glucose="
				+ glucose + ", bilirubin=" + bilirubin + ", ketone=" + ketone + ", proportion=" + proportion
				+ ", redBloodCell=" + redBloodCell + ", ph=" + ph + ", urineProtein=" + urineProtein + ", urobilinogen="
				+ urobilinogen + ", nitrite=" + nitrite + ", whiteBloodCell=" + whiteBloodCell + ", urine_microprotein="
				+ urine_microprotein + ", glycosylated_hemoglobin=" + glycosylated_hemoglobin + ", hcy=" + hcy
				+ ", other_insertTime=" + other_insertTime + ", bloodsuger=" + bloodsuger + ", bodyfat=" + bodyfat
				+ ", bmi=" + bmi + ", bloodpressure=" + bloodpressure + ", bloofat=" + bloofat + ", liver=" + liver
				+ ", uba=" + uba + ", other=" + other + "]";
	}
	
}
