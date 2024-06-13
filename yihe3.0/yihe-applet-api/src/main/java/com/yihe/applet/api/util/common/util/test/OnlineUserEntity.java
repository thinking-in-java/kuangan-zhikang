package com.yihe.applet.api.util.common.util.test;

import javax.websocket.Session;

public class OnlineUserEntity {
	private String id;
	private String to;
	private String message;
	private String time;
	private Session session;
	private String userid;
	
	public OnlineUserEntity(){
		
	}
	public OnlineUserEntity(String id, String to, String message,String time) {
		this.id = id;
		this.to = to;
		this.message = message;
		this.time = time;
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
