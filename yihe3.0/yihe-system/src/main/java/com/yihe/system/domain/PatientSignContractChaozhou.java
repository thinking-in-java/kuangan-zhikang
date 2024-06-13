package com.yihe.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yihe.system.utils.DataValidUtil;
import com.yihe.system.utils.DateTimeUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * terry20200624,解决tomcat日志上的bug
 * java.io.WriteAbortedException: writing aborted; java.io.NotSerializableException: com.szosen.chaozhou.web.model.signcontract.PatientSignContractChaozhou
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1631)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:464)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:422)
 * @author terry.huang
 * 2020年6月24日 上午11:22:11
 */
public class PatientSignContractChaozhou implements Comparable, Cloneable,Serializable {
	private Integer patientSignContractId;

	private Integer upPscId;

	private Integer patientChaozhouId;

	private String idCard;

	private String time;

	private String hospitalNo;
	private String signNumber;
	private String personName;
	private String nationCode;
	private String nationName;

	private String educationCode;
	private String education;

	private String maritalStatusCode;
	private String maritalStatus;
	
	private String workCode;
	private String work; 
	private String workUnit;

	private String sexCode;
	private FieldOption sexCodeOption;

	private Integer whetherInsured;

	private String homeAddress;

	private String contactAddress;

	private String mobileNumber;

	private String phoneNumber;
	
	private String contacter;

    private String contacterMobile;

	private Integer populationTotal;

	private Integer upPatientSignContractId;

	private Integer aboveYear65Total;

	private String crowdType;

	private String crowdTypeMore;

	private String serviceResult;

	private Date startDate;
	private String startDateStr;
	private Date endDate;
	private String endDateStr;

	private Date signDate;
	private String signDateStr = "";

	private String signHospitalDepartNo;

	private String signCreator;

	private String signIcon;

	private Integer signType;

	private Integer signStatus;
	private Integer confirmSignStatus;

	private String signStatusCondition;

	private String signStatusMore;

	private FieldOption signStatusOption;

	private Integer terminateType;

	private String terminateReason;

	private Date terminateDate;

	private String terminateDateStr;

	private String terminateHospitalDepartNo;
	private String terminateCreator;
	private Integer isUsing;
	private Integer isJustHolder;

	private Date createDate;
	private String createDateStr;

	private String creator;

	private Date updateDate;
	private String updateDateStr;
	private Integer osenUploaded;
	private Integer publicUploaded;
	// inner join
	private String birthday;
	private String email;
	private Integer isHouseHolder;

	private String familyNumber;

	private String familyName;

	private Integer upPatientChaozhouId;

	private Integer familyRelation;

	private FieldOption familyRelationOption;

	private String guardianIdCard;

	private String guardianName;

	private String guardianIdCardOther;

	private String guardianNameOther;

	private String provinceNo;

	private String cityNo;

	private String countyNo;

	private String streetNo;

	private String committeeNo;
	
	private String committee;
	
	private String newHealthNumber;

    private String oldHealthNumber;

	private String doctorName;

	private String doctorSignIcon;

	private Integer isPatientLogined = 0;

	private List<PatientSignContractChaozhou> memberSignContracts;

	private Integer uploadResult; // 0,失败，数据不全；1，成功；2，待确认，3，已存在签约
	private Integer isFromSearch;

	private String[] crowdTypeMores;

	private Long currentDateTime;

	private Integer notPerformanceDays;

	private Date performanceDateStart;
	private Date performanceDateEnd;

	private String performanceDateStartStr;
	private String performanceDateEndStr;

	private String printSignContractIds;

	private Integer performanceOrNot;
	
	private Integer planOrNot;
	private Integer serviceStatus;
	private String servicePackNames;
	
	private Boolean isMatched;
	private Boolean isHavePlans;
	private Boolean isHaveExaminations;
	private Boolean isHaveHypertensions;
	private Boolean isHaveDiabetes;
	
	private Integer deathFlag;
	private Integer queryDateType;
	
	private Integer level1PatientSignId; //临时户主签约Id
	private Integer level2PatientSignId; //临时成员签约Id
	private Integer isAddingMember;
	
	private Integer level1PatientId; //临时户主Id
	private Integer level2PatientId; //临时成员Id
	
	private Integer startInclude;
    private Integer endInclude;
    
    private Integer startAge;
    private Integer endAge;
    private Boolean isConditionRecord;
    
    @JsonIgnore 
    private Date createDateStart;
    @JsonIgnore 
    private Date createDateEnd;
    private Date updateDateStart;
    private Date updateDateEnd;
        
    private String createDateStartStr;
    private String createDateEndStr;
    private String updateDateStartStr;
    private String updateDateEndStr;
    
    //分开签约与居民时间
    private Date createTime;
	private String createTimeStr = "";
	private Date updateTime;
	private String updateTimeStr;
	
	@JsonIgnore
	private Date createTimeStart;
	@JsonIgnore
    private Date createTimeEnd;
    private Date updateTimeStart;
    private Date updateTimeEnd;
        
    private String createTimeStartStr;
    private String createTimeEndStr;
    private String updateTimeStartStr;
    private String updateTimeEndStr;

	public Integer getPatientSignContractId() {
		return patientSignContractId;
	}

	public void setPatientSignContractId(Integer patientSignContractId) {
		this.patientSignContractId = patientSignContractId;
	}

	public Integer getUpPscId() {
		return upPscId;
	}

	public void setUpPscId(Integer upPscId) {
		this.upPscId = upPscId;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time == null ? null : time.trim();
	}

	public String getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	public String getSignNumber() {
		return signNumber;
	}

	public void setSignNumber(String signNumber) {
		this.signNumber = signNumber;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	public String getEducation() {
//		if ("27".equals(educationCode))
//			education = "大学专科及以上";
//		else if ("58".equals(educationCode))
//			education = "高中/技校/中专";
//		else if ("70".equals(educationCode))
//			education = "初中";
//		else if ("80".equals(educationCode))
//			education = "小学";
//		else if ("90".equals(educationCode))
//			education = "文盲或半文盲";
//		else if ("91".equals(educationCode))
//			education = "不详";
		if ("1".equals(educationCode))education = "研究生";
		else if ("2".equals(educationCode))
			education = "大学本科";
		else if ("3".equals(educationCode))
			education = "大学专科和专科学校";
		else if ("4".equals(educationCode))
			education = "中等专业学校";
		else if ("5".equals(educationCode))
			education = "技工学校";
		else if ("6".equals(educationCode))
			education = "高中";
		else if ("7".equals(educationCode))
			education = "初中";
		else if ("8".equals(educationCode))
			education = "小学";
		else if ("9".equals(educationCode))
			education = "文盲或半文盲";
		else if ("10".equals(educationCode))
			education = "不详";
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public String getMaritalStatus() {
		if("1".equals(maritalStatusCode)){
			maritalStatus="未婚";
		}
		else if("2".equals(maritalStatusCode)){
			maritalStatus="已婚";
		}
		else if("3".equals(maritalStatusCode)){
			maritalStatus="丧偶";
		}else if("4".equals(maritalStatusCode)){
			maritalStatus="离婚";
		}else if("9".equals(maritalStatusCode)){
			maritalStatus="未说明的婚姻状况";
		}
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
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

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode == null ? null : sexCode.trim();
	}

	public FieldOption getSexCodeOption() {
		return sexCodeOption;
	}

	public void setSexCodeOption(FieldOption sexCodeOption) {
		this.sexCodeOption = sexCodeOption;
	}

	public Integer getWhetherInsured() {
		return whetherInsured;
	}

	public void setWhetherInsured(Integer whetherInsured) {
		this.whetherInsured = whetherInsured;
	}

	public String getHomeAddress() {
		if(homeAddress!=null)homeAddress=homeAddress.replaceAll("\r|\n", "");
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress == null ? null : homeAddress.trim();
	}

	public String getContactAddress() {
		if(contactAddress!=null)contactAddress=contactAddress.replaceAll("\r|\n", "");
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress == null ? null : contactAddress
				.trim();
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}	

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public String getContacterMobile() {
		return contacterMobile;
	}

	public void setContacterMobile(String contacterMobile) {
		this.contacterMobile = contacterMobile;
	}

	public Integer getPopulationTotal() {
		return populationTotal;
	}

	public void setPopulationTotal(Integer populationTotal) {
		this.populationTotal = populationTotal;
	}

	public Integer getUpPatientSignContractId() {
		return upPatientSignContractId;
	}

	public void setUpPatientSignContractId(Integer upPatientSignContractId) {
		this.upPatientSignContractId = upPatientSignContractId;
	}

	public Integer getAboveYear65Total() {
		return aboveYear65Total;
	}

	public void setAboveYear65Total(Integer aboveYear65Total) {
		this.aboveYear65Total = aboveYear65Total;
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

	public String getServiceResult() {
		return serviceResult;
	}

	public void setServiceResult(String serviceResult) {
		this.serviceResult = serviceResult == null ? null : serviceResult
				.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getStartDateStr() {
		String result = startDateStr;
		if (startDate != null) {
			result = DataValidUtil.setDateToStr(startDate,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
		}
		return result;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		String result = endDateStr;
		if (endDate != null) {
			result = DataValidUtil.setDateToStr(endDate,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
		}
		return result;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getSignDateStr() {
		String result = signDateStr;
		if (signDate != null) {
			result = DataValidUtil.setDateToStr(signDate,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
		}
		return result;
	}

	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}

	public String getSignHospitalDepartNo() {
		return signHospitalDepartNo;
	}

	public void setSignHospitalDepartNo(String signHospitalDepartNo) {
		this.signHospitalDepartNo = signHospitalDepartNo;
	}

	public String getSignCreator() {
		return signCreator;
	}

	public void setSignCreator(String signCreator) {
		this.signCreator = signCreator == null ? null : signCreator.trim();
	}

	public String getSignIcon() {
		return signIcon;
	}

	public void setSignIcon(String signIcon) {
		this.signIcon = signIcon;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Integer getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

	public Integer getConfirmSignStatus() {
		return confirmSignStatus;
	}

	public void setConfirmSignStatus(Integer confirmSignStatus) {
		this.confirmSignStatus = confirmSignStatus;
	}

	public String getSignStatusCondition() {
		return signStatusCondition;
	}

	public void setSignStatusCondition(String signStatusCondition) {
		this.signStatusCondition = signStatusCondition;
	}

	public String getSignStatusMore() {
		return signStatusMore;
	}

	public void setSignStatusMore(String signStatusMore) {
		this.signStatusMore = signStatusMore;
	}

	public FieldOption getSignStatusOption() {
		return signStatusOption;
	}

	public void setSignStatusOption(FieldOption signStatusOption) {
		this.signStatusOption = signStatusOption;
	}

	public Integer getTerminateType() {
		return terminateType;
	}

	public void setTerminateType(Integer terminateType) {
		this.terminateType = terminateType;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason == null ? null : terminateReason
				.trim();
	}

	public Date getTerminateDate() {
		return terminateDate;
	}

	public void setTerminateDate(Date terminateDate) {
		this.terminateDate = terminateDate;
	}

	public String getTerminateDateStr() {
		String result = terminateDateStr;
		if (terminateDate != null) {
			result = DataValidUtil.setDateToStr(terminateDate,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
		}
		return result;
	}

	public void setTerminateDateStr(String terminateDateStr) {
		this.terminateDateStr = terminateDateStr;
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}

	public Integer getIsJustHolder() {
		return isJustHolder;
	}

	public void setIsJustHolder(Integer isJustHolder) {
		this.isJustHolder = isJustHolder;
	}

	public String getTerminateHospitalDepartNo() {
		return terminateHospitalDepartNo;
	}

	public void setTerminateHospitalDepartNo(String terminateHospitalDepartNo) {
		this.terminateHospitalDepartNo = terminateHospitalDepartNo;
	}

	public String getTerminateCreator() {
		return terminateCreator;
	}

	public void setTerminateCreator(String terminateCreator) {
		this.terminateCreator = terminateCreator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateDateStr() {
		String result = createDateStr;
		if (createDate != null) {
			result = DataValidUtil.setDateToStr(createDate,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		return result;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateDateStr() {
		String result = updateDateStr;
		if (updateDate != null) {
			result = DataValidUtil.setDateToStr(updateDate,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		return result;
	}

	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}

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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsHouseHolder() {
		return isHouseHolder;
	}

	public void setIsHouseHolder(Integer isHouseHolder) {
		this.isHouseHolder = isHouseHolder;
	}

	public String getFamilyNumber() {
		return familyNumber;
	}

	public void setFamilyNumber(String familyNumber) {
		this.familyNumber = familyNumber;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
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

	public String getGuardianIdCard() {
		return guardianIdCard;
	}

	public void setGuardianIdCard(String guardianIdCard) {
		this.guardianIdCard = guardianIdCard;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianIdCardOther() {
		return guardianIdCardOther;
	}

	public void setGuardianIdCardOther(String guardianIdCardOther) {
		this.guardianIdCardOther = guardianIdCardOther;
	}

	public String getGuardianNameOther() {
		return guardianNameOther;
	}

	public void setGuardianNameOther(String guardianNameOther) {
		this.guardianNameOther = guardianNameOther;
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

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getNewHealthNumber() {
		return newHealthNumber;
	}

	public void setNewHealthNumber(String newHealthNumber) {
		this.newHealthNumber = newHealthNumber;
	}

	public String getOldHealthNumber() {
		return oldHealthNumber;
	}

	public void setOldHealthNumber(String oldHealthNumber) {
		this.oldHealthNumber = oldHealthNumber;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSignIcon() {
		return doctorSignIcon;
	}

	public void setDoctorSignIcon(String doctorSignIcon) {
		this.doctorSignIcon = doctorSignIcon;
	}

	public Integer getIsPatientLogined() {
		return isPatientLogined;
	}

	public void setIsPatientLogined(Integer isPatientLogined) {
		this.isPatientLogined = isPatientLogined;
	}
	
	public List<PatientSignContractChaozhou> getMemberSignContracts() {
		return memberSignContracts;
	}

	public void setMemberSignContracts(
			List<PatientSignContractChaozhou> memberSignContracts) {
		this.memberSignContracts = memberSignContracts;
	}

	public Integer getUploadResult() {
		return uploadResult;
	}

	public void setUploadResult(Integer uploadResult) {
		this.uploadResult = uploadResult;
	}

	public Integer getIsFromSearch() {
		return isFromSearch;
	}

	public void setIsFromSearch(Integer isFromSearch) {
		this.isFromSearch = isFromSearch;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String[] getCrowdTypeMores() {
		return crowdTypeMores;
	}

	public void setCrowdTypeMores(String[] crowdTypeMores) {
		this.crowdTypeMores = crowdTypeMores;
	}

	public Long getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(Long currentDateTime) {
		this.currentDateTime = currentDateTime;
	}

	public Integer getNotPerformanceDays() {
		return notPerformanceDays;
	}

	public void setNotPerformanceDays(Integer notPerformanceDays) {
		this.notPerformanceDays = notPerformanceDays;
	}

	public Date getPerformanceDateStart() {
		return performanceDateStart;
	}

	public void setPerformanceDateStart(Date performanceDateStart) {
		this.performanceDateStart = performanceDateStart;
	}

	public Date getPerformanceDateEnd() {
		return performanceDateEnd;
	}

	public void setPerformanceDateEnd(Date performanceDateEnd) {
		this.performanceDateEnd = performanceDateEnd;
	}

	public String getPerformanceDateStartStr() {
		String result = performanceDateStartStr;
		if (performanceDateStart != null) {
			result = DataValidUtil.setDateToStr(performanceDateStart,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
		}
		return result;
	}

	public void setPerformanceDateStartStr(String performanceDateStartStr) {
		this.performanceDateStartStr = performanceDateStartStr;
	}

	public String getPerformanceDateEndStr() {
		String result = performanceDateEndStr;
		if (performanceDateEnd != null) {
			result = DataValidUtil.setDateToStr(performanceDateEnd,
					DateTimeUtil.AISAT_DATE_FORMAT_DAY);
		}
		return result;
	}

	public void setPerformanceDateEndStr(String performanceDateEndStr) {
		this.performanceDateEndStr = performanceDateEndStr;
	}

	public String getPrintSignContractIds() {
		return printSignContractIds;
	}

	public void setPrintSignContractIds(String printSignContractIds) {
		this.printSignContractIds = printSignContractIds;
	}

	public Integer getPerformanceOrNot() {
		return performanceOrNot;
	}

	public void setPerformanceOrNot(Integer performanceOrNot) {
		this.performanceOrNot = performanceOrNot;
	}

	public Integer getPlanOrNot() {
		return planOrNot;
	}

	public void setPlanOrNot(Integer planOrNot) {
		this.planOrNot = planOrNot;
	}

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServicePackNames() {
		return servicePackNames;
	}

	public void setServicePackNames(String servicePackNames) {
		this.servicePackNames = servicePackNames;
	}

	public Boolean getIsMatched() {
		return isMatched;
	}

	public void setIsMatched(Boolean isMatched) {
		this.isMatched = isMatched;
	}

	public Boolean getIsHavePlans() {
		return isHavePlans;
	}

	public void setIsHavePlans(Boolean isHavePlans) {
		this.isHavePlans = isHavePlans;
	}

	public Boolean getIsHaveExaminations() {
		return isHaveExaminations;
	}

	public void setIsHaveExaminations(Boolean isHaveExamination) {
		this.isHaveExaminations = isHaveExamination;
	}

	public Boolean getIsHaveHypertensions() {
		return isHaveHypertensions;
	}

	public void setIsHaveHypertensions(Boolean isHaveHypertensions) {
		this.isHaveHypertensions = isHaveHypertensions;
	}

	public Boolean getIsHaveDiabetes() {
		return isHaveDiabetes;
	}

	public void setIsHaveDiabetes(Boolean isHaveDiabetes) {
		this.isHaveDiabetes = isHaveDiabetes;
	}

	public Integer getDeathFlag() {
		return deathFlag;
	}

	public void setDeathFlag(Integer deathFlag) {
		this.deathFlag = deathFlag;
	}

	public Integer getQueryDateType() {
		return queryDateType;
	}

	public void setQueryDateType(Integer queryDateType) {
		this.queryDateType = queryDateType;
	}

	public Integer getLevel1PatientSignId() {
		return level1PatientSignId;
	}

	public void setLevel1PatientSignId(Integer level1PatientSignId) {
		this.level1PatientSignId = level1PatientSignId;
	}

	public Integer getLevel2PatientSignId() {
		return level2PatientSignId;
	}

	public void setLevel2PatientSignId(Integer level2PatientSignId) {
		this.level2PatientSignId = level2PatientSignId;
	}

	public Integer getIsAddingMember() {
		return isAddingMember;
	}

	public void setIsAddingMember(Integer isAddingMember) {
		this.isAddingMember = isAddingMember;
	}

	public Integer getLevel1PatientId() {
		return level1PatientId;
	}

	public void setLevel1PatientId(Integer level1PatientId) {
		this.level1PatientId = level1PatientId;
	}

	public Integer getLevel2PatientId() {
		return level2PatientId;
	}

	public void setLevel2PatientId(Integer level2PatientId) {
		this.level2PatientId = level2PatientId;
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
	
	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Date getUpdateDateStart() {
		return updateDateStart;
	}

	public void setUpdateDateStart(Date updateDateStart) {
		this.updateDateStart = updateDateStart;
	}

	public Date getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(Date updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}

	public String getCreateDateStartStr() {
		String result = createDateStartStr;
		if (createDateStart != null) {
			result = DataValidUtil.setDateToStr(createDateStart,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		
		return result;
	}

	public void setCreateDateStartStr(String createDateStartStr) {
		this.createDateStartStr = createDateStartStr;
	}

	public String getCreateDateEndStr() {
		String result = createDateEndStr;
		if (createDateEnd != null) {
			result = DataValidUtil.setDateToStr(createDateEnd,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		
		return result;
	}

	public void setCreateDateEndStr(String createDateEndStr) {
		this.createDateEndStr = createDateEndStr;
	}

	public String getUpdateDateStartStr() {
		String result = updateDateStartStr;
		if (updateDateStart != null) {
			result = DataValidUtil.setDateToStr(updateDateStart,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		
		return result;
	}

	public void setUpdateDateStartStr(String updateDateStartStr) {
		this.updateDateStartStr = updateDateStartStr;
	}

	public String getUpdateDateEndStr() {
		String result = updateDateEndStr;
		if (updateDateEnd != null) {
			result = DataValidUtil.setDateToStr(updateDateEnd,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		
		return result;
	}

	public void setUpdateDateEndStr(String updateDateEndStr) {
		this.updateDateEndStr = updateDateEndStr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		String result = createTimeStr;
		if (createTime != null) {
			result = DataValidUtil.setDateToStr(createTime,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		return result;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeStr() {
		String result = updateTimeStr;
		if (updateTime != null) {
			result = DataValidUtil.setDateToStr(updateTime,
					DateTimeUtil.AISAT_DATE_FORMAT_WITH_SECOND);
		}
		return result;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
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
		PatientSignContractChaozhou fo = null;
		int result = 0;
		if (o != null) {
			fo = (PatientSignContractChaozhou) o;
			if (fo != null && fo.getUpdateDate() != null && this.getUpdateDate()!=null) {
				if(fo.getUpdateDate().equals(this.getUpdateDate())){
					result=0;
				}else if(fo.getUpdateDate().before(this.getUpdateDate())){
					result=1;
				}else if(fo.getUpdateDate().after(this.getUpdateDate())){
					result=-1;
				}else{
					result = this.getUpdateDate().compareTo(fo.getUpdateDate());
				}
			}
		}

		return result;
	}	
}