package com.yihe.system.domain;

import java.io.Serializable;

public class YhManageTask implements Serializable{
	  private static final long serialVersionUID = 1L;
	  private Integer id;
	  private int wattingcount;
	  private int begincount;
	  private int closecount;
	  private int finishicount;
	  private String birth_date;
	  private int gender;
	  private int service_type;
	  private String service_content;
	  private int service_state;
	  private String service_waiting_time;
	  private String service_record;
	  private String service_name;
	  //服务开始时间
	  private String start_service_time;
	  //服务结束时间
	  private String insert_time;
	  private String end_service_time;
	  private Integer returnvisit;
	  private Double longitude;
	  private Double dimension;
	  private String time;
	  private String acceptsituation;
	  private String cname;
	  private String cbname;
	  private String childtag;
	  private String usercode;
	  private String doctorid;
	  private Integer childnodes;
	  private String plan_name;
	  private Integer satisfaction;
	  private String service_subtitle;
	  private String threshold_name;

	public String getThreshold_name() {
		return threshold_name;
	}

	public void setThreshold_name(String threshold_name) {
		this.threshold_name = threshold_name;
	}

	public String getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}

	public String getService_subtitle() {
		return service_subtitle;
	}

	public void setService_subtitle(String service_subtitle) {
		this.service_subtitle = service_subtitle;
	}

	public Integer getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(Integer satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
	public Integer getChildnodes() {
		return childnodes;
	}
	public void setChildnodes(Integer childnodes) {
		this.childnodes = childnodes;
	}

	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getChildtag() {
		return childtag;
	}
	public void setChildtag(String childtag) {
		this.childtag = childtag;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCbname() {
		return cbname;
	}
	public void setCbname(String cbname) {
		this.cbname = cbname;
	}
	public String getAcceptsituation() {
		return acceptsituation;
	}
	public void setAcceptsituation(String acceptsituation) {
		this.acceptsituation = acceptsituation;
	}
	public String getStart_service_time() {
		return start_service_time;
	}
	public void setStart_service_time(String start_service_time) {
		this.start_service_time = start_service_time;
	}
	public String getEnd_service_time() {
		return end_service_time;
	}
	public void setEnd_service_time(String end_service_time) {
		this.end_service_time = end_service_time;
	}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public Double getLongitude() {
			return longitude;
		}
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		public Double getDimension() {
			return dimension;
		}
		public void setDimension(Double dimension) {
			this.dimension = dimension;
		}
		public Integer getReturnvisit() {
			return returnvisit;
		}
		public void setReturnvisit(Integer returnvisit) {
			this.returnvisit = returnvisit;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getService_name() {
			return service_name;
		}
		public void setService_name(String service_name) {
			this.service_name = service_name;
		}
		public int getWattingcount() {
			return wattingcount;
		}
		public void setWattingcount(int wattingcount) {
			this.wattingcount = wattingcount;
		}
		public int getBegincount() {
			return begincount;
		}
		public void setBegincount(int begincount) {
			this.begincount = begincount;
		}
		public int getClosecount() {
			return closecount;
		}
		public void setClosecount(int closecount) {
			this.closecount = closecount;
		}
		public int getFinishicount() {
			return finishicount;
		}
		public void setFinishicount(int finishicount) {
			this.finishicount = finishicount;
		}
		public String getBirth_date() {
			return birth_date;
		}
		public void setBirth_date(String birth_date) {
			this.birth_date = birth_date;
		}
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public int getService_type() {
			return service_type;
		}
		public void setService_type(int service_type) {
			this.service_type = service_type;
		}
		public String getService_content() {
			return service_content;
		}
		public void setService_content(String service_content) {
			this.service_content = service_content;
		}
		public int getService_state() {
			return service_state;
		}
		public void setService_state(int service_state) {
			this.service_state = service_state;
		}
		public String getService_waiting_time() {
			return service_waiting_time;
		}
		public void setService_waiting_time(String service_waiting_time) {
			this.service_waiting_time = service_waiting_time;
		}
	
		public String getService_record() {
			return service_record;
		}
		public void setService_record(String service_record) {
			this.service_record = service_record;
		}
	
	@Override
	public String toString() {
		return "YhManageTask [wattingcount=" + wattingcount + ", begincount=" + begincount + ", closecount="
				+ closecount + ", finishicount=" + finishicount + ", birth_date=" + birth_date + ", gender=" + gender
				+ ", service_type=" + service_type + ", service_content=" + service_content + ", service_state="
				+ service_state + ", service_waiting_time=" + service_waiting_time + ", service_record="
				+ service_record + ", service_name=" + service_name + ", id=" + id + ", returnvisit=" + returnvisit
				+ ", longitude=" + longitude + ", dimension=" + dimension + "]";
	}
	  
}
