package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class YhUserReginfo implements Serializable {
    /**
     * 用户code
     *
     * @mbg.generated
     */
    private String userCode;

    /**
     * 用户类型
     *
     * @mbg.generated
     */
    private Integer userType;

    /**
     * 用户姓名
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * 用户密码
     *
     * @mbg.generated
     */
    private String userPwd;

    /**
     * 用户性别（1.男；2.女）
     *
     * @mbg.generated
     */
    private Integer gender;

    /**
     * 出生年月
     *
     * @mbg.generated
     */

    private Date birthDate;

    private String birthtime;


    /**
     * 民族
     *
     * @mbg.generated
     */
    private String nation;

    /**
     * 用户邮箱
     *
     * @mbg.generated
     */
    private String email;

    /**
     * 身份证
     *
     * @mbg.generated
     */
    private String cardidentity;

    /**
     * 省
     *
     * @mbg.generated
     */
    private String provinceId;

    /**
     * 市
     *
     * @mbg.generated
     */
    private String cityId;

    /**
     * 区
     *
     * @mbg.generated
     */
    private String areaId;

    /**
     * 注册时间
     *
     * @mbg.generated
     */
    private Date regTime;

    /**
     * 登录次数
     *
     * @mbg.generated
     */
    private Integer loginTimes;

    /**
     * 真实姓名
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 户籍地址
     *
     * @mbg.generated
     */
    private String address;

    /**
     * 常住地址
     *
     * @mbg.generated
     */
    private String permanentaddress;

    /**
     * 邮编
     *
     * @mbg.generated
     */
    private String postCode;

    /**
     * 紧急联系人手机号
     *
     * @mbg.generated
     */
    private String relation;

    /**
     * 手机号码
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     * 职业
     *
     * @mbg.generated
     */
    private String occupation;

    /**
     * 文化程度
     *
     * @mbg.generated
     */
    private String edBackground;

    /**
     * 婚姻状况
     *
     * @mbg.generated
     */
    private String maritalStatus;

    /**
     * 锁标记
     *
     * @mbg.generated
     */
    private Integer lockFlag;

    /**
     * 注册来源
     *
     * @mbg.generated
     */
    private Integer regSource;

    /**
     * 证书类型
     *
     * @mbg.generated
     */
    private Integer certType;

    /**
     * 证书编号
     *
     * @mbg.generated
     */
    private String certNo;

    /**
     * 组织code
     *
     * @mbg.generated
     */
    private String orgCode;

    /**
     * 血型
     *
     * @mbg.generated
     */
    private String bloodType;

    /**
     * rh阴性
     *
     * @mbg.generated
     */
    private String rhfeminine;

    /**
     * 注册卡Id
     *
     * @mbg.generated
     */
    private String enrolCardId;

    /**
     * 登录次数
     *
     * @mbg.generated
     */
    private Date loginTime;

    /**
     * 工作单位
     *
     * @mbg.generated
     */
    private String unit;

    /**
     * 是否社团（0是1否）
     *
     * @mbg.generated
     */
    private Integer isAssociation;

    /**
     * 社团名称
     *
     * @mbg.generated
     */
    private String associationName;

    /**
     * 是否独居（0是1否）
     *
     * @mbg.generated
     */
    private Integer isAlone;

    /**
     * 是否过敏（0是1否）
     *
     * @mbg.generated
     */
    private Integer isAllergy;

    private Integer id;
    /**
     * 过敏源
     *
     * @mbg.generated
     */
    private String allergyName;
    private String ber;
    private Integer roleId;
    private Integer page;
    private Integer limit;

    private String orgname;

    private String doctor;

    private String treedate;
    private String value;

    private String createdate;
    private String linetime;

    //角色类型
    private String roleName;

    private String modules;

    private String key;

    //中医体质
    private Integer aIstype;
    private Integer bIstype;
    private Integer cIstype;
    private Integer dIstype;
    private Integer eIstype;
    private Integer fIstype;
    private Integer gIstype;
    private Integer hIstype;
    private Integer iIstype;
    private String time;
    private String tiems;
    private String strIs;
    private String tendencyIs;


    public String getStrIs() {
        return strIs;
    }

    public void setStrIs(String strIs) {
        this.strIs = strIs;
    }

    public String getTendencyIs() {
        return tendencyIs;
    }

    public void setTendencyIs(String tendencyIs) {
        this.tendencyIs = tendencyIs;
    }

    public String getTiems() {
        return tiems;
    }

    public void setTiems(String tiems) {
        this.tiems = tiems;
    }

    public Integer getaIstype() {
        return aIstype;
    }

    public void setaIstype(Integer aIstype) {
        this.aIstype = aIstype;
    }

    public Integer getbIstype() {
        return bIstype;
    }

    public void setbIstype(Integer bIstype) {
        this.bIstype = bIstype;
    }

    public Integer getcIstype() {
        return cIstype;
    }

    public void setcIstype(Integer cIstype) {
        this.cIstype = cIstype;
    }

    public Integer getdIstype() {
        return dIstype;
    }

    public void setdIstype(Integer dIstype) {
        this.dIstype = dIstype;
    }

    public Integer geteIstype() {
        return eIstype;
    }

    public void seteIstype(Integer eIstype) {
        this.eIstype = eIstype;
    }

    public Integer getfIstype() {
        return fIstype;
    }

    public void setfIstype(Integer fIstype) {
        this.fIstype = fIstype;
    }

    public Integer getgIstype() {
        return gIstype;
    }

    public void setgIstype(Integer gIstype) {
        this.gIstype = gIstype;
    }

    public Integer gethIstype() {
        return hIstype;
    }

    public void sethIstype(Integer hIstype) {
        this.hIstype = hIstype;
    }

    public Integer getiIstype() {
        return iIstype;
    }

    public void setiIstype(Integer iIstype) {
        this.iIstype = iIstype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "YhUserReginfo{" +
                "aIstype=" + aIstype +
                ", bIstype=" + bIstype +
                ", cIstype=" + cIstype +
                ", dIstype=" + dIstype +
                ", eIstype=" + eIstype +
                ", fIstype=" + fIstype +
                ", gIstype=" + gIstype +
                ", hIstype=" + hIstype +
                ", iIstype=" + iIstype +
                ", iIstype=" + tiems +
                ", time='" + time + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLinetime() {
        return linetime;
    }

    public void setLinetime(String linetime) {
        this.linetime = linetime;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTreedate() {
        return treedate;
    }

    public void setTreedate(String treedate) {
        this.treedate = treedate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getBer() {
        return ber;
    }

    public void setBer(String ber) {
        this.ber = ber;
    }
    /**
     * 角色
     */
    private YhUserRole yhuserrole;

    private List<YhUserRole> yhuserroles;

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public YhUserRole getYhuserrole() {
        return yhuserrole;
    }

    public void setYhuserrole(YhUserRole yhuserrole) {
        this.yhuserrole = yhuserrole;
    }

    public List<YhUserRole> getYhuserroles() {
        return yhuserroles;
    }

    public void setYhuserroles(List<YhUserRole> yhuserroles) {
        this.yhuserroles = yhuserroles;
    }

    private static final long serialVersionUID = 1L;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;

    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardidentity() {
        return cardidentity;
    }

    public void setCardidentity(String cardidentity) {
        this.cardidentity = cardidentity;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPermanentaddress() {
        return permanentaddress;
    }

    public void setPermanentaddress(String permanentaddress) {
        this.permanentaddress = permanentaddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEdBackground() {
        return edBackground;
    }

    public void setEdBackground(String edBackground) {
        this.edBackground = edBackground;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
    }

    public Integer getRegSource() {
        return regSource;
    }

    public void setRegSource(Integer regSource) {
        this.regSource = regSource;
    }

    public Integer getCertType() {
        return certType;
    }

    public void setCertType(Integer certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRhfeminine() {
        return rhfeminine;
    }

    public void setRhfeminine(String rhfeminine) {
        this.rhfeminine = rhfeminine;
    }

    public String getEnrolCardId() {
        return enrolCardId;
    }

    public void setEnrolCardId(String enrolCardId) {
        this.enrolCardId = enrolCardId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getIsAssociation() {
        return isAssociation;
    }

    public void setIsAssociation(Integer isAssociation) {
        this.isAssociation = isAssociation;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public Integer getIsAlone() {
        return isAlone;
    }

    public void setIsAlone(Integer isAlone) {
        this.isAlone = isAlone;
    }

    public Integer getIsAllergy() {
        return isAllergy;
    }

    public void setIsAllergy(Integer isAllergy) {
        this.isAllergy = isAllergy;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }


    public void newUserCode(String userCode)
    {
        switch (this.getUserType())
        {
            case 4: // '\004'
                String intUserCode = "1";
                if (!userCode.equals("0"))
                    intUserCode = Integer.toString(Integer.parseInt(userCode.substring(2)) + 1);
                this.setUserCode(getDrCode(intUserCode));
                break;
        }
    }

    public String getDrCode(String userCode)
    {
        switch (userCode.length())
        {
            case 1: // '\001'
                return (new StringBuilder("DA00000")).append(userCode).toString();

            case 2: // '\002'
                return (new StringBuilder("DA0000")).append(userCode).toString();

            case 3: // '\003'
                return (new StringBuilder("DA000")).append(userCode).toString();

            case 4: // '\004'
                return (new StringBuilder("DA00")).append(userCode).toString();

            case 5: // '\005'
                return (new StringBuilder("DA0")).append(userCode).toString();

            case 6: // '\006'
                return (new StringBuilder("DA")).append(userCode).toString();
        }
        return (new StringBuilder("DB")).append(userCode).toString();
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        YhUserReginfo other = (YhUserReginfo) that;
        return (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
                && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
                && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
                && (this.getUserPwd() == null ? other.getUserPwd() == null : this.getUserPwd().equals(other.getUserPwd()))
                && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
                && (this.getBirthDate() == null ? other.getBirthDate() == null : this.getBirthDate().equals(other.getBirthDate()))
                && (this.getNation() == null ? other.getNation() == null : this.getNation().equals(other.getNation()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getCardidentity() == null ? other.getCardidentity() == null : this.getCardidentity().equals(other.getCardidentity()))
                && (this.getProvinceId() == null ? other.getProvinceId() == null : this.getProvinceId().equals(other.getProvinceId()))
                && (this.getCityId() == null ? other.getCityId() == null : this.getCityId().equals(other.getCityId()))
                && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
                && (this.getRegTime() == null ? other.getRegTime() == null : this.getRegTime().equals(other.getRegTime()))
                && (this.getLoginTimes() == null ? other.getLoginTimes() == null : this.getLoginTimes().equals(other.getLoginTimes()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getPermanentaddress() == null ? other.getPermanentaddress() == null : this.getPermanentaddress().equals(other.getPermanentaddress()))
                && (this.getPostCode() == null ? other.getPostCode() == null : this.getPostCode().equals(other.getPostCode()))
                && (this.getRelation() == null ? other.getRelation() == null : this.getRelation().equals(other.getRelation()))
                && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
                && (this.getOccupation() == null ? other.getOccupation() == null : this.getOccupation().equals(other.getOccupation()))
                && (this.getEdBackground() == null ? other.getEdBackground() == null : this.getEdBackground().equals(other.getEdBackground()))
                && (this.getMaritalStatus() == null ? other.getMaritalStatus() == null : this.getMaritalStatus().equals(other.getMaritalStatus()))
                && (this.getLockFlag() == null ? other.getLockFlag() == null : this.getLockFlag().equals(other.getLockFlag()))
                && (this.getRegSource() == null ? other.getRegSource() == null : this.getRegSource().equals(other.getRegSource()))
                && (this.getCertType() == null ? other.getCertType() == null : this.getCertType().equals(other.getCertType()))
                && (this.getCertNo() == null ? other.getCertNo() == null : this.getCertNo().equals(other.getCertNo()))
                && (this.getOrgCode() == null ? other.getOrgCode() == null : this.getOrgCode().equals(other.getOrgCode()))
                && (this.getBloodType() == null ? other.getBloodType() == null : this.getBloodType().equals(other.getBloodType()))
                && (this.getRhfeminine() == null ? other.getRhfeminine() == null : this.getRhfeminine().equals(other.getRhfeminine()))
                && (this.getEnrolCardId() == null ? other.getEnrolCardId() == null : this.getEnrolCardId().equals(other.getEnrolCardId()))
                && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
                && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
                && (this.getIsAssociation() == null ? other.getIsAssociation() == null : this.getIsAssociation().equals(other.getIsAssociation()))
                && (this.getAssociationName() == null ? other.getAssociationName() == null : this.getAssociationName().equals(other.getAssociationName()))
                && (this.getIsAlone() == null ? other.getIsAlone() == null : this.getIsAlone().equals(other.getIsAlone()))
                && (this.getIsAllergy() == null ? other.getIsAllergy() == null : this.getIsAllergy().equals(other.getIsAllergy()))
                && (this.getAllergyName() == null ? other.getAllergyName() == null : this.getAllergyName().equals(other.getAllergyName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserPwd() == null) ? 0 : getUserPwd().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getBirthDate() == null) ? 0 : getBirthDate().hashCode());
        result = prime * result + ((getNation() == null) ? 0 : getNation().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getCardidentity() == null) ? 0 : getCardidentity().hashCode());
        result = prime * result + ((getProvinceId() == null) ? 0 : getProvinceId().hashCode());
        result = prime * result + ((getCityId() == null) ? 0 : getCityId().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getRegTime() == null) ? 0 : getRegTime().hashCode());
        result = prime * result + ((getLoginTimes() == null) ? 0 : getLoginTimes().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getPermanentaddress() == null) ? 0 : getPermanentaddress().hashCode());
        result = prime * result + ((getPostCode() == null) ? 0 : getPostCode().hashCode());
        result = prime * result + ((getRelation() == null) ? 0 : getRelation().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getOccupation() == null) ? 0 : getOccupation().hashCode());
        result = prime * result + ((getEdBackground() == null) ? 0 : getEdBackground().hashCode());
        result = prime * result + ((getMaritalStatus() == null) ? 0 : getMaritalStatus().hashCode());
        result = prime * result + ((getLockFlag() == null) ? 0 : getLockFlag().hashCode());
        result = prime * result + ((getRegSource() == null) ? 0 : getRegSource().hashCode());
        result = prime * result + ((getCertType() == null) ? 0 : getCertType().hashCode());
        result = prime * result + ((getCertNo() == null) ? 0 : getCertNo().hashCode());
        result = prime * result + ((getOrgCode() == null) ? 0 : getOrgCode().hashCode());
        result = prime * result + ((getBloodType() == null) ? 0 : getBloodType().hashCode());
        result = prime * result + ((getRhfeminine() == null) ? 0 : getRhfeminine().hashCode());
        result = prime * result + ((getEnrolCardId() == null) ? 0 : getEnrolCardId().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getIsAssociation() == null) ? 0 : getIsAssociation().hashCode());
        result = prime * result + ((getAssociationName() == null) ? 0 : getAssociationName().hashCode());
        result = prime * result + ((getIsAlone() == null) ? 0 : getIsAlone().hashCode());
        result = prime * result + ((getIsAllergy() == null) ? 0 : getIsAllergy().hashCode());
        result = prime * result + ((getAllergyName() == null) ? 0 : getAllergyName().hashCode());
        return result;
    }

    public Object get(String h) {
        // TODO Auto-generated method stub
        return null;
    }



}
