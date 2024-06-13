package com.yihe.system.domain;

import java.io.Serializable;

public class YhSysConfig implements Serializable{
		private Long id;
		private String key;
		private String value;
		private Integer status;
		private String remark;
		private YhJzSysSms yhjzsyssms;

		private static final long serialVersionUID = 1L;
		
		public YhJzSysSms getYhjzsyssms() {
			return yhjzsyssms;
		}
		public void setYhjzsyssms(YhJzSysSms yhjzsyssms) {
			this.yhjzsyssms = yhjzsyssms;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		@Override
		public String toString() {
			return "YhSysConfig [id=" + id + ", key=" + key + ", value=" + value + ", status=" + status + ", remark="
					+ remark + ", yhjzsyssms=" + yhjzsyssms + "]";
		}
		
}
