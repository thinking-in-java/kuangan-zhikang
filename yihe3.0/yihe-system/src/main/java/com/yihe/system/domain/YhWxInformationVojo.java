/**
 * 
 */
package com.yihe.system.domain;

/**
 * @author Administrator
 *
 */
public class YhWxInformationVojo {
	  private Integer wxId;

	    private String gzAppid;

	    private String gzAppsecret;

	    private String xcAppid;

	    private String xcAppsecret;
	    
	    
	    //随访任务提醒
	    private String taskboardId;

	    private String taskvariate1;

	    private String taskvariate2;

	    private String wxType;

	    private String mbName;
	    
	    //咨询回复通知
	    private String replyboardId;

	    private String replyvariate1;

	    private String replyvariate2;
	    
	    private String replyvariate3;
	    
	    
	    
	    //公众号
	    //随访任务提醒
	    private String gzhboardId;

	    private String gzhvariate1;

	    private String gzhvariate2;
	    
	    private String gzhvariate3;

	    private String gzhvariate4;

	    
	    //咨询回复通知
	    private String gzhreplyboardId;

	    private String gzhreplyvariate1;

	    private String gzhreplyvariate2;
	    
	    private String gzhreplyvariate3;
	    
	    private String gzhreplyvariate4;
	    
	    private String gzhreplyvariate5;
	    

	    
	    
	    public String getGzhreplyboardId() {
			return gzhreplyboardId;
		}

		public void setGzhreplyboardId(String gzhreplyboardId) {
			this.gzhreplyboardId = gzhreplyboardId;
		}

		public String getGzhreplyvariate1() {
			return gzhreplyvariate1;
		}

		public void setGzhreplyvariate1(String gzhreplyvariate1) {
			this.gzhreplyvariate1 = gzhreplyvariate1;
		}

		public String getGzhreplyvariate2() {
			return gzhreplyvariate2;
		}

		public void setGzhreplyvariate2(String gzhreplyvariate2) {
			this.gzhreplyvariate2 = gzhreplyvariate2;
		}

		public String getGzhreplyvariate3() {
			return gzhreplyvariate3;
		}

		public void setGzhreplyvariate3(String gzhreplyvariate3) {
			this.gzhreplyvariate3 = gzhreplyvariate3;
		}

		public String getGzhreplyvariate4() {
			return gzhreplyvariate4;
		}

		public void setGzhreplyvariate4(String gzhreplyvariate4) {
			this.gzhreplyvariate4 = gzhreplyvariate4;
		}

		public String getGzhreplyvariate5() {
			return gzhreplyvariate5;
		}

		public void setGzhreplyvariate5(String gzhreplyvariate5) {
			this.gzhreplyvariate5 = gzhreplyvariate5;
		}

		public String getGzhboardId() {
			return gzhboardId;
		}

		public void setGzhboardId(String gzhboardId) {
			this.gzhboardId = gzhboardId;
		}

		public String getGzhvariate1() {
			return gzhvariate1;
		}

		public void setGzhvariate1(String gzhvariate1) {
			this.gzhvariate1 = gzhvariate1;
		}

		public String getGzhvariate2() {
			return gzhvariate2;
		}

		public void setGzhvariate2(String gzhvariate2) {
			this.gzhvariate2 = gzhvariate2;
		}

		public String getGzhvariate3() {
			return gzhvariate3;
		}

		public void setGzhvariate3(String gzhvariate3) {
			this.gzhvariate3 = gzhvariate3;
		}

		public String getGzhvariate4() {
			return gzhvariate4;
		}

		public void setGzhvariate4(String gzhvariate4) {
			this.gzhvariate4 = gzhvariate4;
		}

		public String getTaskboardId() {
			return taskboardId;
		}

		public void setTaskboardId(String taskboardId) {
			this.taskboardId = taskboardId;
		}

		public String getTaskvariate1() {
			return taskvariate1;
		}

		public void setTaskvariate1(String taskvariate1) {
			this.taskvariate1 = taskvariate1;
		}

		public String getTaskvariate2() {
			return taskvariate2;
		}

		public void setTaskvariate2(String taskvariate2) {
			this.taskvariate2 = taskvariate2;
		}

		public String getReplyboardId() {
			return replyboardId;
		}

		public void setReplyboardId(String replyboardId) {
			this.replyboardId = replyboardId;
		}

		public String getReplyvariate1() {
			return replyvariate1;
		}

		public void setReplyvariate1(String replyvariate1) {
			this.replyvariate1 = replyvariate1;
		}

		public String getReplyvariate2() {
			return replyvariate2;
		}

		public void setReplyvariate2(String replyvariate2) {
			this.replyvariate2 = replyvariate2;
		}

		public String getReplyvariate3() {
			return replyvariate3;
		}

		public void setReplyvariate3(String replyvariate3) {
			this.replyvariate3 = replyvariate3;
		}

		public Integer getWxId() {
	        return wxId;
	    }

	    public void setWxId(Integer wxId) {
	        this.wxId = wxId;
	    }

	    public String getGzAppid() {
	        return gzAppid;
	    }

	    public void setGzAppid(String gzAppid) {
	        this.gzAppid = gzAppid == null ? null : gzAppid.trim();
	    }

	    public String getGzAppsecret() {
	        return gzAppsecret;
	    }

	    public void setGzAppsecret(String gzAppsecret) {
	        this.gzAppsecret = gzAppsecret == null ? null : gzAppsecret.trim();
	    }

	    public String getXcAppid() {
	        return xcAppid;
	    }

	    public void setXcAppid(String xcAppid) {
	        this.xcAppid = xcAppid == null ? null : xcAppid.trim();
	    }

	    public String getXcAppsecret() {
	        return xcAppsecret;
	    }

	    public void setXcAppsecret(String xcAppsecret) {
	        this.xcAppsecret = xcAppsecret == null ? null : xcAppsecret.trim();
	    }
	   
	    public String getWxType() {
	        return wxType;
	    }

	    public void setWxType(String wxType) {
	        this.wxType = wxType == null ? null : wxType.trim();
	    }

	    public String getMbName() {
	        return mbName;
	    }

	    public void setMbName(String mbName) {
	        this.mbName = mbName == null ? null : mbName.trim();
	    }
}
