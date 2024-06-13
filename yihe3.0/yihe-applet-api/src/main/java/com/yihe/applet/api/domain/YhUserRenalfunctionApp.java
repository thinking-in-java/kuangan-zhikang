package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserRenalfunctionApp implements Serializable {
	/**
	 * 肾功能编号id
	 *
	 * @mbg.generated
	 */
	private Integer renalid;

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
	 * 用户id
	 *
	 * @mbg.generated
	 */
	private Integer userId;
	/**
	 * 是否删除
	 *
	 * @mbg.generated
	 */
	private Integer isDel;

	/**
	 * 新增时间
	 *
	 * @mbg.generated
	 */
	private String inerttime;

	/**
	 * 数据来源
	 *
	 * @mbg.generated
	 */
	private String dataSource;

	private String tiwen;

	private String jiedu;

	private boolean openNo;

	public String getTiwen() {
		return tiwen;
	}

	public void setTiwen(String tiwen) {
		this.tiwen = tiwen;
	}

	public String getJiedu() {
		return jiedu;
	}

	public void setJiedu(String jiedu) {
		this.jiedu = jiedu;
	}

	public boolean isOpenNo() {
		return openNo;
	}

	public void setOpenNo(boolean openNo) {
		this.openNo = openNo;
	}

	private static final long serialVersionUID = 1L;

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getRenalid() {
		return renalid;
	}

	public void setRenalid(Integer renalid) {
		this.renalid = renalid;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getInerttime() {
		return inerttime;
	}

	public void setInerttime(String inerttime) {
		this.inerttime = inerttime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", renalid=").append(renalid);
		sb.append(", creatinine=").append(creatinine);
		sb.append(", ureaNitrogen=").append(ureaNitrogen);
		sb.append(", uricAcid=").append(uricAcid);
		sb.append(", userId=").append(userId);
		sb.append(", inerttime=").append(inerttime);
		sb.append(", dataSource=").append(dataSource);
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
		YhUserRenalfunctionApp other = (YhUserRenalfunctionApp) that;
		return (this.getRenalid() == null ? other.getRenalid() == null : this.getRenalid().equals(other.getRenalid()))
				&& (this.getCreatinine() == null ? other.getCreatinine() == null
						: this.getCreatinine().equals(other.getCreatinine()))
				&& (this.getUreaNitrogen() == null ? other.getUreaNitrogen() == null
						: this.getUreaNitrogen().equals(other.getUreaNitrogen()))
				&& (this.getUricAcid() == null ? other.getUricAcid() == null
						: this.getUricAcid().equals(other.getUricAcid()))
				&& (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
				&& (this.getInerttime() == null ? other.getInerttime() == null
						: this.getInerttime().equals(other.getInerttime()))
				&& (this.getDataSource() == null ? other.getDataSource() == null
						: this.getDataSource().equals(other.getDataSource()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getRenalid() == null) ? 0 : getRenalid().hashCode());
		result = prime * result + ((getCreatinine() == null) ? 0 : getCreatinine().hashCode());
		result = prime * result + ((getUreaNitrogen() == null) ? 0 : getUreaNitrogen().hashCode());
		result = prime * result + ((getUricAcid() == null) ? 0 : getUricAcid().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getInerttime() == null) ? 0 : getInerttime().hashCode());
		result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
		return result;
	}
}