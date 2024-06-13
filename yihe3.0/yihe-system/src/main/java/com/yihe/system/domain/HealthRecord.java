package com.yihe.system.domain;


import com.yihe.system.utils.DataValidUtil;
import com.yihe.system.utils.DateTimeUtil;

import java.util.Date;

public class HealthRecord implements Comparable{
    private Integer healthRecordId;

    private String idCard;
    
    private Long searchNo;
    
    private Integer uploadTagId;

    private String checkid;

    private String workPlace;

    private String contact;

    private String contactPhone;

    private Integer registeredPermanent;

    private String nationCode;
    
    private String nationName;

    private Integer bloodTypeCode;

    private Integer rhBloodCode;

    private String educationCode;

    private String workCode;
    
    private String work;

    private String maritalStatusCode;

    private String insuranceCode;

    private String insuranceType;

    private String ywgms;//药物过敏史

    private String ywgmsqt;//其他药物过敏史

    private String bls;//暴露史

    private String jwsjbs;//既往历疾病
    
    private String jwsjbsqt;//既往历疾病其他

    private String jwssss;//既往历手术

    private String jwswss;//既往历外伤

    private String jwssxs;//既往历输血

    private String jzsfq;//家族史父亲

    private String jzsfqqt;//家族史父亲其他

    private String jzsmq;//家族史母亲

    private String jzsmqqt;//家族史母亲其他

    private String jzsxdjm;//家族史兄弟姐妹

    private String jzsxdjmqt;//家族史兄弟姐妹其他

    private String jzszn;//家族史子女

    private String jzsznqt;//家族史子女其他

    private String ycbs;//遗传病史
        
    private String ycbsm;//遗传病史名称

    private String cjqk;//残疾情况

    private String cjqkqt;//其他残疾

    private Integer cfpfss;//厨房排风设施

    private Integer rllx;//燃料类型

    private Integer ys;//饮水

    private Integer cs;//厕所

    private Integer qsl;//禽畜栏

    private String creator;

    private Date createTime;
    private String createTimeStr;
    private Date updateTime;
    private String updateTimeStr;
    private Date uploadTime;
    private String uploadTimeStr;
    private Integer osenUploaded;

    private Integer publicUploaded;

    
    //inner join
    private Integer patientChaozhouId;
    private String sexCode;
    private String birthday;
    private String homeAddress;
    private String mobileNumber;
    private String phoneNumber;
    private String personName;
    private String email;
    private String time;
    
    //condition
    private Integer dbpStart;
    private Integer dbpEnd;
    private Integer sbpStart;
    private Integer sbpEnd;
    
    private Integer gluStart;
    private Integer gluEnd;
    
    private Integer bpStart;
    private Integer bpEnd;
    
    private Integer rhythmStart;
    private Integer rhythmEnd;
    private Integer xinDianResult;
    
    //additional add
	private Integer isHouseHolder;
	private Integer upPatientChaozhouId;

	private Integer familyRelation;

	private FieldOption familyRelationOption;

	private String crowdType;

	private String crowdTypeMore; // 数据库中字段

	private FieldOption crowdTypeOption;
	private String provinceNo;

	private String cityNo;

	private String countyNo;

	private String streetNo;

	private String committeeNo;

	private String myHospitalNo;
    private Integer age;

	private Integer startInclude;
	private Integer endInclude;

	private Integer startAge;
	private Integer endAge;
	private Boolean isConditionRecord;

	private Date createTimeStart;
	private Date createTimeEnd;
	private Date updateTimeStart;
	private Date updateTimeEnd;

	private String createTimeStartStr;
	private String createTimeEndStr;
	private String updateTimeStartStr;
	private String updateTimeEndStr;
	
    
    public Integer getHealthRecordId() {
		return healthRecordId;
	}

	public void setHealthRecordId(Integer healthRecordId) {
		this.healthRecordId = healthRecordId;
	}

	public Integer getPatientChaozhouId() {
        return patientChaozhouId;
    }

    public void setPatientChaozhouId(Integer patientChaozhouId) {
        this.patientChaozhouId = patientChaozhouId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Long getSearchNo() {
		return searchNo;
	}

	public void setSearchNo(Long searchNo) {
		this.searchNo = searchNo;
	}

	public Integer getUploadTagId() {
		return uploadTagId;
	}

	public void setUploadTagId(Integer uploadTagId) {
		this.uploadTagId = uploadTagId;
	}

	public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid == null ? null : checkid.trim();
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace == null ? null : workPlace.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getRegisteredPermanent() {
        return registeredPermanent;
    }

    public void setRegisteredPermanent(Integer registeredPermanent) {
        this.registeredPermanent = registeredPermanent;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode == null ? null : nationCode.trim();
    }

    public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public Integer getBloodTypeCode() {
        return bloodTypeCode;
    }

    public void setBloodTypeCode(Integer bloodTypeCode) {
        this.bloodTypeCode = bloodTypeCode;
    }

    public Integer getRhBloodCode() {
        return rhBloodCode;
    }

    public void setRhBloodCode(Integer rhBloodCode) {
        this.rhBloodCode = rhBloodCode;
    }

    public String getEducationCode() {
        return educationCode;
    }

    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode == null ? null : educationCode.trim();
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode == null ? null : workCode.trim();
    }
    
    public String getWork() {
		if("1".equals(workCode)){work="国家机关、党群组织、企业、事业单位负责人";}
		else if("2".equals(workCode)){work="专业技术人员";}
		else if("3".equals(workCode)){work="办事人员和有关人员";}
		else if("4".equals(workCode)){work="商业、服务业人员";}
		else if("5".equals(workCode)){work="农、林、牧、渔、水利业生产人员";}
		else if("6".equals(workCode)){work="生产、运输设备操作人员及有关人员";}
		else if("7".equals(workCode)){work="军人";}
		else if("8".equals(workCode)){work="不便分类的其他从业人员";}
		else if("9".equals(workCode)){work="无职业";}
		
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

    public String getMaritalStatusCode() {
        return maritalStatusCode;
    }

    public void setMaritalStatusCode(String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode == null ? null : maritalStatusCode.trim();
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType == null ? null : insuranceType.trim();
    }

    public String getYwgms() {
        return ywgms;
    }

    public void setYwgms(String ywgms) {
        this.ywgms = ywgms == null ? null : ywgms.trim();
    }

    public String getYwgmsqt() {
        return ywgmsqt;
    }

    public void setYwgmsqt(String ywgmsqt) {
        this.ywgmsqt = ywgmsqt == null ? null : ywgmsqt.trim();
    }

    public String getBls() {
        return bls;
    }

    public void setBls(String bls) {
        this.bls = bls == null ? null : bls.trim();
    }

    public String getJwsjbs() {
        return jwsjbs;
    }

    public void setJwsjbs(String jwsjbs) {
        this.jwsjbs = jwsjbs == null ? null : jwsjbs.trim();
    }
    
    public String getJwsjbsqt() {
		return jwsjbsqt;
	}

	public void setJwsjbsqt(String jwsjbsqt) {
		this.jwsjbsqt = jwsjbsqt;
	}

	public String getJwssss() {
        return jwssss;
    }

    public void setJwssss(String jwssss) {
        this.jwssss = jwssss == null ? null : jwssss.trim();
    }

    public String getJwswss() {
        return jwswss;
    }

    public void setJwswss(String jwswss) {
        this.jwswss = jwswss == null ? null : jwswss.trim();
    }

    public String getJwssxs() {
        return jwssxs;
    }

    public void setJwssxs(String jwssxs) {
        this.jwssxs = jwssxs == null ? null : jwssxs.trim();
    }

    public String getJzsfq() {
        return jzsfq;
    }

    public void setJzsfq(String jzsfq) {
        this.jzsfq = jzsfq == null ? null : jzsfq.trim();
    }

    public String getJzsfqqt() {
        return jzsfqqt;
    }

    public void setJzsfqqt(String jzsfqqt) {
        this.jzsfqqt = jzsfqqt == null ? null : jzsfqqt.trim();
    }

    public String getJzsmq() {
        return jzsmq;
    }

    public void setJzsmq(String jzsmq) {
        this.jzsmq = jzsmq == null ? null : jzsmq.trim();
    }

    public String getJzsmqqt() {
        return jzsmqqt;
    }

    public void setJzsmqqt(String jzsmqqt) {
        this.jzsmqqt = jzsmqqt == null ? null : jzsmqqt.trim();
    }

    public String getJzsxdjm() {
        return jzsxdjm;
    }

    public void setJzsxdjm(String jzsxdjm) {
        this.jzsxdjm = jzsxdjm == null ? null : jzsxdjm.trim();
    }

    public String getJzsxdjmqt() {
        return jzsxdjmqt;
    }

    public void setJzsxdjmqt(String jzsxdjmqt) {
        this.jzsxdjmqt = jzsxdjmqt == null ? null : jzsxdjmqt.trim();
    }

    public String getJzszn() {
        return jzszn;
    }

    public void setJzszn(String jzszn) {
        this.jzszn = jzszn == null ? null : jzszn.trim();
    }

    public String getJzsznqt() {
        return jzsznqt;
    }

    public void setJzsznqt(String jzsznqt) {
        this.jzsznqt = jzsznqt == null ? null : jzsznqt.trim();
    }

    public String getYcbs() {
        return ycbs;
    }

    public void setYcbs(String ycbs) {
        this.ycbs = ycbs == null ? null : ycbs.trim();
    }

    public String getYcbsm() {
        return ycbsm;
    }

    public void setYcbsm(String ycbsm) {
        this.ycbsm = ycbsm == null ? null : ycbsm.trim();
    }

    public String getCjqk() {
        return cjqk;
    }

    public void setCjqk(String cjqk) {
        this.cjqk = cjqk == null ? null : cjqk.trim();
    }

    public String getCjqkqt() {
        return cjqkqt;
    }

    public void setCjqkqt(String cjqkqt) {
        this.cjqkqt = cjqkqt == null ? null : cjqkqt.trim();
    }

    public Integer getCfpfss() {
        return cfpfss;
    }

    public void setCfpfss(Integer cfpfss) {
        this.cfpfss = cfpfss;
    }

    public Integer getRllx() {
        return rllx;
    }

    public void setRllx(Integer rllx) {
        this.rllx = rllx;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public Integer getQsl() {
        return qsl;
    }

    public void setQsl(Integer qsl) {
        this.qsl = qsl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTimeStr() {
		String result=createTimeStr;
    	if(createTime!=null){
    		result= DataValidUtil.setDateToStr(createTime, DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
    	}
		return result;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		String result=updateTimeStr;
    	if(updateTime!=null){
    		result=DataValidUtil.setDateToStr(updateTime, DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
    	}
		return result;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadTimeStr() {
		String result=uploadTimeStr;
    	if(uploadTime!=null){
    		result=DataValidUtil.setDateToStr(uploadTime, DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
    	}
		return result;
	}

	public void setUploadTimeStr(String uploadTimeStr) {
		this.uploadTimeStr = uploadTimeStr;
	}
    //other

	public Integer getOsenUploaded() {
		return osenUploaded;
	}

	public void setOsenUploaded(Integer osenUploaded) {
		this.osenUploaded = osenUploaded;
	}

	public Integer getPublicUploaded() {
		return publicUploaded;
	}

	public void setPublicUploaded(Integer publicUploaded) {
		this.publicUploaded = publicUploaded;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getDbpStart() {
		return dbpStart;
	}

	public void setDbpStart(Integer dbpStart) {
		this.dbpStart = dbpStart;
	}

	public Integer getDbpEnd() {
		return dbpEnd;
	}

	public void setDbpEnd(Integer dbpEnd) {
		this.dbpEnd = dbpEnd;
	}

	public Integer getSbpStart() {
		return sbpStart;
	}

	public void setSbpStart(Integer sbpStart) {
		this.sbpStart = sbpStart;
	}

	public Integer getSbpEnd() {
		return sbpEnd;
	}

	public void setSbpEnd(Integer sbpEnd) {
		this.sbpEnd = sbpEnd;
	}

	public Integer getGluStart() {
		return gluStart;
	}

	public void setGluStart(Integer gluStart) {
		this.gluStart = gluStart;
	}

	public Integer getGluEnd() {
		return gluEnd;
	}

	public void setGluEnd(Integer gluEnd) {
		this.gluEnd = gluEnd;
	}

	public Integer getBpStart() {
		return bpStart;
	}

	public void setBpStart(Integer bpStart) {
		this.bpStart = bpStart;
	}

	public Integer getBpEnd() {
		return bpEnd;
	}

	public void setBpEnd(Integer bpEnd) {
		this.bpEnd = bpEnd;
	}

	public Integer getRhythmStart() {
		return rhythmStart;
	}

	public void setRhythmStart(Integer rhythmStart) {
		this.rhythmStart = rhythmStart;
	}

	public Integer getRhythmEnd() {
		return rhythmEnd;
	}

	public void setRhythmEnd(Integer rhythmEnd) {
		this.rhythmEnd = rhythmEnd;
	}
	

	public Integer getXinDianResult() {
		return xinDianResult;
	}

	public void setXinDianResult(Integer xinDianResult) {
		this.xinDianResult = xinDianResult;
	}

	public Integer getIsHouseHolder() {
		return isHouseHolder;
	}

	public void setIsHouseHolder(Integer isHouseHolder) {
		this.isHouseHolder = isHouseHolder;
	}

	public Integer getUpPatientChaozhouId() {
		return upPatientChaozhouId;
	}

	public void setUpPatientChaozhouId(Integer upPatientChaozhouId) {
		this.upPatientChaozhouId = upPatientChaozhouId;
	}

	public Integer getFamilyRelation() {
		return familyRelation;
	}

	public void setFamilyRelation(Integer familyRelation) {
		this.familyRelation = familyRelation;
	}

	public FieldOption getFamilyRelationOption() {
		return familyRelationOption;
	}

	public void setFamilyRelationOption(FieldOption familyRelationOption) {
		this.familyRelationOption = familyRelationOption;
	}

	public String getCrowdType() {
		return crowdType;
	}

	public void setCrowdType(String crowdType) {
		this.crowdType = crowdType;
	}

	public String getCrowdTypeMore() {
		return crowdTypeMore;
	}

	public void setCrowdTypeMore(String crowdTypeMore) {
		this.crowdTypeMore = crowdTypeMore;
	}

	public FieldOption getCrowdTypeOption() {
		return crowdTypeOption;
	}

	public void setCrowdTypeOption(FieldOption crowdTypeOption) {
		this.crowdTypeOption = crowdTypeOption;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getCountyNo() {
		return countyNo;
	}

	public void setCountyNo(String countyNo) {
		this.countyNo = countyNo;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getCommitteeNo() {
		return committeeNo;
	}

	public void setCommitteeNo(String committeeNo) {
		this.committeeNo = committeeNo;
	}

	public String getMyHospitalNo() {
		return myHospitalNo;
	}

	public void setMyHospitalNo(String myHospitalNo) {
		this.myHospitalNo = myHospitalNo;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getStartInclude() {
		return startInclude;
	}

	public void setStartInclude(Integer startInclude) {
		this.startInclude = startInclude;
	}

	public Integer getEndInclude() {
		return endInclude;
	}

	public void setEndInclude(Integer endInclude) {
		this.endInclude = endInclude;
	}

	public Integer getStartAge() {
		return startAge;
	}

	public void setStartAge(Integer startAge) {
		this.startAge = startAge;
	}

	public Integer getEndAge() {
		return endAge;
	}

	public void setEndAge(Integer endAge) {
		this.endAge = endAge;
	}

	public Boolean getIsConditionRecord() {
		return isConditionRecord;
	}

	public void setIsConditionRecord(Boolean isConditionRecord) {
		this.isConditionRecord = isConditionRecord;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getCreateTimeStartStr() {
		String result = createTimeStartStr;
		if (createTimeStart != null) {
			result = DataValidUtil.setDateToStr(createTimeStart,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}

		return result;
	}

	public void setCreateTimeStartStr(String createTimeStartStr) {
		this.createTimeStartStr = createTimeStartStr;
	}

	public String getCreateTimeEndStr() {
		String result = createTimeEndStr;
		if (createTimeEnd != null) {
			result = DataValidUtil.setDateToStr(createTimeEnd,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}

		return result;
	}

	public void setCreateTimeEndStr(String createTimeEndStr) {
		this.createTimeEndStr = createTimeEndStr;
	}

	public String getUpdateTimeStartStr() {
		String result = updateTimeStartStr;
		if (updateTimeStart != null) {
			result = DataValidUtil.setDateToStr(updateTimeStart,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}

		return result;
	}

	public void setUpdateTimeStartStr(String updateTimeStartStr) {
		this.updateTimeStartStr = updateTimeStartStr;
	}

	public String getUpdateTimeEndStr() {
		String result = updateTimeEndStr;
		if (updateTimeEnd != null) {
			result = DataValidUtil.setDateToStr(updateTimeEnd,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}

		return result;
	}

	public void setUpdateTimeEndStr(String updateTimeEndStr) {
		this.updateTimeEndStr = updateTimeEndStr;
	}
    
	@Override
	public int compareTo(Object o) {
		HealthRecord fo = null;
		int result = 0;
		if (o != null) {
			fo = (HealthRecord) o;
			if (fo != null && fo.getUpdateTime() != null && this.getUpdateTime()!=null) {
				result = this.getUpdateTime().compareTo(fo.getUpdateTime());
			}
		}

		return result;
	}
}