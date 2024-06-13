package com.yihe.system.domain;

import java.io.Serializable;

public class YhDiseaseAssessTotalReport implements Serializable {
    private Integer id;

    /**
     * 返回结果序列号
     *
     * @mbg.generated
     */
    private String resultSerialnum;

    /**
     * 请求序列号
     *
     * @mbg.generated
     */
    private String requestSerialnum;

    /**
     * 用户编码
     *
     * @mbg.generated
     */
    private String userCode;
    /**
     * 评估时间
     */
    private String qtime;
    
    

    public String getQtime() {
		return qtime;
	}

	public void setQtime(String qtime) {
		this.qtime = qtime;
	}

	/**
     * 性别
     *
     * @mbg.generated
     */
    private Integer gender;

    /**
     * 年龄
     *
     * @mbg.generated
     */
    private Integer age;

    /**
     * 用户名
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * 职业
     *
     * @mbg.generated
     */
    private String userOccupation;

    /**
     * 居住地 1南方; 2北方
     *
     * @mbg.generated
     */
    private String userJzd;

    /**
     * 居住环境 1城市; 2农村
     *
     * @mbg.generated
     */
    private String userJzhj;
    
    
    /**
     * 机构
     */
    private String  orgCode;
    //用户姓名
    private String name;
    
    //干预状态
    private Integer evaluateFlag;
    
    private int classify;
    

    public int getClassify() {
		return classify;
	}

	public void setClassify(int classify) {
		this.classify = classify;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEvaluateFlag() {
		return evaluateFlag;
	}

	public void setEvaluateFlag(Integer evaluateFlag) {
		this.evaluateFlag = evaluateFlag;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResultSerialnum() {
        return resultSerialnum;
    }

    public void setResultSerialnum(String resultSerialnum) {
        this.resultSerialnum = resultSerialnum;
    }

    public String getRequestSerialnum() {
        return requestSerialnum;
    }

    public void setRequestSerialnum(String requestSerialnum) {
        this.requestSerialnum = requestSerialnum;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserJzd() {
        return userJzd;
    }

    public void setUserJzd(String userJzd) {
        this.userJzd = userJzd;
    }

    public String getUserJzhj() {
        return userJzhj;
    }

    public void setUserJzhj(String userJzhj) {
        this.userJzhj = userJzhj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resultSerialnum=").append(resultSerialnum);
        sb.append(", requestSerialnum=").append(requestSerialnum);
        sb.append(", userCode=").append(userCode);
        sb.append(", gender=").append(gender);
        sb.append(", age=").append(age);
        sb.append(", userName=").append(userName);
        sb.append(", userOccupation=").append(userOccupation);
        sb.append(", userJzd=").append(userJzd);
        sb.append(", userJzhj=").append(userJzhj);
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
        YhDiseaseAssessTotalReport other = (YhDiseaseAssessTotalReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getResultSerialnum() == null ? other.getResultSerialnum() == null : this.getResultSerialnum().equals(other.getResultSerialnum()))
            && (this.getRequestSerialnum() == null ? other.getRequestSerialnum() == null : this.getRequestSerialnum().equals(other.getRequestSerialnum()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserOccupation() == null ? other.getUserOccupation() == null : this.getUserOccupation().equals(other.getUserOccupation()))
            && (this.getUserJzd() == null ? other.getUserJzd() == null : this.getUserJzd().equals(other.getUserJzd()))
            && (this.getUserJzhj() == null ? other.getUserJzhj() == null : this.getUserJzhj().equals(other.getUserJzhj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getResultSerialnum() == null) ? 0 : getResultSerialnum().hashCode());
        result = prime * result + ((getRequestSerialnum() == null) ? 0 : getRequestSerialnum().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserOccupation() == null) ? 0 : getUserOccupation().hashCode());
        result = prime * result + ((getUserJzd() == null) ? 0 : getUserJzd().hashCode());
        result = prime * result + ((getUserJzhj() == null) ? 0 : getUserJzhj().hashCode());
        return result;
    }
}