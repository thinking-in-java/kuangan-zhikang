package com.yihe.system.domain;

import java.util.Date;

public class YhUserOrderWeb {
    private Integer orderId;

    private String userCode;

    private String orderNumber;

    private Integer siteId;
    private Integer merchandiseType;
    private Integer merchandiseId;
    private Integer orderStatus;

    private Integer consumeIntegral;

    private Integer money;
    private Integer type;

    private String logisticsCompany;

    private String logisticsOdd;

    private Integer ssm;

    private String creator;

    private String standby;

    private Date addDate;

    private Date updateDate;
    
    private String name;
    private String merchandiseName;
    private String site;
    private String detailedSite;
    private String consigneeName;
    private String consigneePhone;
    

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMerchandiseType() {
		return merchandiseType;
	}

	public void setMerchandiseType(Integer merchandiseType) {
		this.merchandiseType = merchandiseType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerchandiseName() {
		return merchandiseName;
	}

	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDetailedSite() {
		return detailedSite;
	}

	public void setDetailedSite(String detailedSite) {
		this.detailedSite = detailedSite;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(Integer merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getConsumeIntegral() {
        return consumeIntegral;
    }

    public void setConsumeIntegral(Integer consumeIntegral) {
        this.consumeIntegral = consumeIntegral;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getLogisticsOdd() {
        return logisticsOdd;
    }

    public void setLogisticsOdd(String logisticsOdd) {
        this.logisticsOdd = logisticsOdd == null ? null : logisticsOdd.trim();
    }

    public Integer getSsm() {
        return ssm;
    }

    public void setSsm(Integer ssm) {
        this.ssm = ssm;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}