package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class YhOrgTree implements Serializable {
    private String Org_Code;
    private String orgCode;
    private String orgName;
    
    private String Org_Name;

    private String superOrgCode;

    private Integer isDel;

    private Date addDate;
    
    private String addDate1;
    
    private String addDate2;

    private Integer classNumber;

    private String userCode;

    private String docdorCode;

    private Integer orgTreeUploadId;

    private String orgCustomerCode;

    private String orgNote;
    
    private String province;

    private String city;

    private String county;

    private String standby;

    private String oaTreeId;
    
    private int num;
    
    private boolean spread;
    private int states;
    /**
     * 注册卡
     */
    private String stuck;
    /**
     * 医生
     */
    private String doctor;
    /**
     * 子节点
     */
    private int node;
    
    private String userName;
    
    private String name;
    
    private String password;
    
    private String number;
    
    private String shij;
    
    private String mobile;
    
    private String userPwd;
    
    private List<YhOrgTree> children;
    
    private String OrgsuperiorName;
    
    private String modules;

    private static final long serialVersionUID = 1L;


    private int hasnum;
    private int asset_num;
    private int hasdise;
    private int highrisk;
    private int health;

    public String getOaTreeId() {
        return oaTreeId;
    }

    public void setOaTreeId(String oaTreeId) {
        this.oaTreeId = oaTreeId;
    }

    public int getHasnum() {
        return hasnum;
    }

    public void setHasnum(int hasnum) {
        this.hasnum = hasnum;
    }

    public int getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(int asset_num) {
        this.asset_num = asset_num;
    }

    public int getHasdise() {
        return hasdise;
    }

    public void setHasdise(int hasdise) {
        this.hasdise = hasdise;
    }

    public int getHighrisk() {
        return highrisk;
    }

    public void setHighrisk(int highrisk) {
        this.highrisk = highrisk;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getOrg_Code() {
		return Org_Code;
	}

	public void setOrg_Code(String org_Code) {
		Org_Code = org_Code;
	}

	public String getOrg_Name() {
		return Org_Name;
	}

	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}

	public String getAddDate2() {
		return addDate2;
	}

	public void setAddDate2(String addDate2) {
		this.addDate2 = addDate2;
	}

	public String getAddDate1() {
		return addDate1;
	}

	public void setAddDate1(String addDate1) {
		this.addDate1 = addDate1;
	}

	public String getOrgsuperiorName() {
		return OrgsuperiorName;
	}

	public void setOrgsuperiorName(String orgsuperiorName) {
		OrgsuperiorName = orgsuperiorName;
	}

	public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStandby() {
		return standby;
	}

	public void setStandby(String standby) {
		this.standby = standby;
	}

	public String getShij() {
		return shij;
	}

	public void setShij(String shij) {
		this.shij = shij;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStuck() {
		return stuck;
	}

	public void setStuck(String stuck) {
		this.stuck = stuck;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public List<YhOrgTree> getChildren() {
		return children;
	}

	public void setChildren(List<YhOrgTree> children) {
		this.children = children;
	}

	public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSuperOrgCode() {
        return superOrgCode;
    }

    public void setSuperOrgCode(String superOrgCode) {
        this.superOrgCode = superOrgCode;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDocdorCode() {
        return docdorCode;
    }

    public void setDocdorCode(String docdorCode) {
        this.docdorCode = docdorCode;
    }

    public Integer getOrgTreeUploadId() {
        return orgTreeUploadId;
    }

    public void setOrgTreeUploadId(Integer orgTreeUploadId) {
        this.orgTreeUploadId = orgTreeUploadId;
    }

    public String getOrgCustomerCode() {
        return orgCustomerCode;
    }

    public void setOrgCustomerCode(String orgCustomerCode) {
        this.orgCustomerCode = orgCustomerCode;
    }

    public String getOrgNote() {
        return orgNote;
    }

    public void setOrgNote(String orgNote) {
        this.orgNote = orgNote;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orgCode=").append(orgCode);
        sb.append(", orgName=").append(orgName);
        sb.append(", superOrgCode=").append(superOrgCode);
        sb.append(", isDel=").append(isDel);
        sb.append(", addDate=").append(addDate);
        sb.append(", classNumber=").append(classNumber);
        sb.append(", userCode=").append(userCode);
        sb.append(", docdorCode=").append(docdorCode);
        sb.append(", orgTreeUploadId=").append(orgTreeUploadId);
        sb.append(", orgCustomerCode=").append(orgCustomerCode);
        sb.append(", orgNote=").append(orgNote);
        sb.append("]");
        return sb.toString();
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
        YhOrgTree other = (YhOrgTree) that;
        return (this.getOrgCode() == null ? other.getOrgCode() == null : this.getOrgCode().equals(other.getOrgCode()))
            && (this.getOrgName() == null ? other.getOrgName() == null : this.getOrgName().equals(other.getOrgName()))
            && (this.getSuperOrgCode() == null ? other.getSuperOrgCode() == null : this.getSuperOrgCode().equals(other.getSuperOrgCode()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getAddDate() == null ? other.getAddDate() == null : this.getAddDate().equals(other.getAddDate()))
            && (this.getClassNumber() == null ? other.getClassNumber() == null : this.getClassNumber().equals(other.getClassNumber()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getDocdorCode() == null ? other.getDocdorCode() == null : this.getDocdorCode().equals(other.getDocdorCode()))
            && (this.getOrgTreeUploadId() == null ? other.getOrgTreeUploadId() == null : this.getOrgTreeUploadId().equals(other.getOrgTreeUploadId()))
            && (this.getOrgCustomerCode() == null ? other.getOrgCustomerCode() == null : this.getOrgCustomerCode().equals(other.getOrgCustomerCode()))
            && (this.getOrgNote() == null ? other.getOrgNote() == null : this.getOrgNote().equals(other.getOrgNote()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrgCode() == null) ? 0 : getOrgCode().hashCode());
        result = prime * result + ((getOrgName() == null) ? 0 : getOrgName().hashCode());
        result = prime * result + ((getSuperOrgCode() == null) ? 0 : getSuperOrgCode().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getAddDate() == null) ? 0 : getAddDate().hashCode());
        result = prime * result + ((getClassNumber() == null) ? 0 : getClassNumber().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getDocdorCode() == null) ? 0 : getDocdorCode().hashCode());
        result = prime * result + ((getOrgTreeUploadId() == null) ? 0 : getOrgTreeUploadId().hashCode());
        result = prime * result + ((getOrgCustomerCode() == null) ? 0 : getOrgCustomerCode().hashCode());
        result = prime * result + ((getOrgNote() == null) ? 0 : getOrgNote().hashCode());
        return result;
    }
}