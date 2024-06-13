package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserOrderApp {
    private Integer orderId;

    private String userCode;

    private String orderNumber;

    private Integer siteId;

    private Integer merchandiseId;

    private Integer orderStatus;

    private Integer consumeIntegral;

    private Integer money;
    
    private String merchandiseName;
    private String logisticsCompany;

    private String logisticsOdd;
    
    private String orderDate;

    private Integer ssm;

    private String creator;

    private String standby;
    private String coverImg;
    
    private Date addDate;

    private Date updateDate;

    
    
    public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getMerchandiseName() {
		return merchandiseName;
	}

	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
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