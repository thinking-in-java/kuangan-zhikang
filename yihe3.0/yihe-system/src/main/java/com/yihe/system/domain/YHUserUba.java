package com.yihe.system.domain;

public class YHUserUba {
    private Integer ubaId;

    private String adddate;

    private String env;

    private String glucose;

    private String bilirubin;

    private String ketone;

    private String proportion;

    private String redbloodcell;

    private String ph;

    private String urineprotein;

    private String urobilinogen;

    private String nitrite;

    private String whitebloodcell;

    private Integer eUserId;

    private String dataSource;

    private String creatinine;

    private String microalbumin;

    private String calciumIon;

    private String vitamin;

    private String result;

    private String isDel;

    public Integer getUbaId() {
        return ubaId;
    }

    public void setUbaId(Integer ubaId) {
        this.ubaId = ubaId;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate == null ? null : adddate.trim();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose == null ? null : glucose.trim();
    }

    public String getBilirubin() {
        return bilirubin;
    }

    public void setBilirubin(String bilirubin) {
        this.bilirubin = bilirubin == null ? null : bilirubin.trim();
    }

    public String getKetone() {
        return ketone;
    }

    public void setKetone(String ketone) {
        this.ketone = ketone == null ? null : ketone.trim();
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion == null ? null : proportion.trim();
    }

    public String getRedbloodcell() {
        return redbloodcell;
    }

    public void setRedbloodcell(String redbloodcell) {
        this.redbloodcell = redbloodcell == null ? null : redbloodcell.trim();
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph == null ? null : ph.trim();
    }

    public String getUrineprotein() {
        return urineprotein;
    }

    public void setUrineprotein(String urineprotein) {
        this.urineprotein = urineprotein == null ? null : urineprotein.trim();
    }

    public String getUrobilinogen() {
        return urobilinogen;
    }

    public void setUrobilinogen(String urobilinogen) {
        this.urobilinogen = urobilinogen == null ? null : urobilinogen.trim();
    }

    public String getNitrite() {
        return nitrite;
    }

    public void setNitrite(String nitrite) {
        this.nitrite = nitrite == null ? null : nitrite.trim();
    }

    public String getWhitebloodcell() {
        return whitebloodcell;
    }

    public void setWhitebloodcell(String whitebloodcell) {
        this.whitebloodcell = whitebloodcell == null ? null : whitebloodcell.trim();
    }

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine == null ? null : creatinine.trim();
    }

    public String getMicroalbumin() {
        return microalbumin;
    }

    public void setMicroalbumin(String microalbumin) {
        this.microalbumin = microalbumin == null ? null : microalbumin.trim();
    }

    public String getCalciumIon() {
        return calciumIon;
    }

    public void setCalciumIon(String calciumIon) {
        this.calciumIon = calciumIon == null ? null : calciumIon.trim();
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin == null ? null : vitamin.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }
}