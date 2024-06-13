package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserNotify implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private String user_id;
	private Integer is_read;
	private Date send_date;
	private Date read_date;
	private Integer type;
	private String to_service_id;
	private Integer news_id;

	public Integer getNews_id() {
		return news_id;
	}

	public void setNews_id(Integer news_id) {
		this.news_id = news_id;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}
	public Date getRead_date() {
		return read_date;
	}
	public void setRead_date(Date read_date) {
		this.read_date = read_date;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTo_service_id() {
		return to_service_id;
	}
	public void setTo_service_id(String to_service_id) {
		this.to_service_id = to_service_id;
	}
	@Override
	public String toString() {
		return "YhUserNotify [id=" + id + ", title=" + title + ", content=" + content + ", user_id=" + user_id
				+ ", is_read=" + is_read + ", send_date=" + send_date + ", read_date=" + read_date + ", type=" + type
				+ ", to_service_id=" + to_service_id + "]";
	}
	
}
