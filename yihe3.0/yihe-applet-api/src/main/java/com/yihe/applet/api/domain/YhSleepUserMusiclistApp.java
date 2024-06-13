package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @version: shayu3.0
 * @Description: 用户音乐列表实体类
 * @author: lhh
 * @date: 2020年5月9日 下午2:11:52
 */
public class YhSleepUserMusiclistApp implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userCode;
	private String musicId;
	private Date time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
