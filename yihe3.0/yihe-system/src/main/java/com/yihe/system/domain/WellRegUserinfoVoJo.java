package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: WellRegUserInfo.java
 * @Package com.yihe.admin.entity
 * @Description: TODO用户类
 * @author hzb  
 * @date 2018年11月26日 下午2:26:22
 * @version V1.0   
 */
public class WellRegUserinfoVoJo implements Serializable{


	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private Integer gender;

	    private String birthDate;

	    private String name;

	    private String mobile;

	    private String idCard;
	    
	    private String jobno;
	    
	    private String orgCode;
	    
	    private String doctorid;
	    
	    
	    
	    
		public String getOrgCode() {
			return orgCode;
		}

		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		public String getDoctorid() {
			return doctorid;
		}

		public void setDoctorid(String doctorid) {
			this.doctorid = doctorid;
		}

		public String getJobno() {
			return jobno;
		}

		public void setJobno(String jobno) {
			this.jobno = jobno;
		}

		public String getIdCard() {
			return idCard;
		}

		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}

		public Integer getGender() {
			return gender;
		}

		public void setGender(Integer gender) {
			this.gender = gender;
		}

		public String getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(String birthDate) {
			this.birthDate = birthDate;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}


	  
	

	
	
	
}
