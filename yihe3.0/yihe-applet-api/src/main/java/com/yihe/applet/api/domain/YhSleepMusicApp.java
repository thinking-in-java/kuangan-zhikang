package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @version: shayu3.0
 * @Description: 睡眠-助眠音乐 
 * @author: lhh
 * @date: 2020年4月15日 上午11:50:32
 */
public class YhSleepMusicApp implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer musicId;
	private String musicTitle;
	private String musicVideo;
	private String musicImg;
	private Integer musicListennum;
	private String musicTime;
	public Integer getMusicId() {
		return musicId;
	}
	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}
	public String getMusicTitle() {
		return musicTitle;
	}
	public void setMusicTitle(String musicTitle) {
		this.musicTitle = musicTitle;
	}
	public String getMusicVideo() {
		return musicVideo;
	}
	public void setMusicVideo(String musicVideo) {
		this.musicVideo = musicVideo;
	}
	public String getMusicImg() {
		return musicImg;
	}
	public void setMusicImg(String musicImg) {
		this.musicImg = musicImg;
	}
	public Integer getMusicListennum() {
		return musicListennum;
	}
	public void setMusicListennum(Integer musicListennum) {
		this.musicListennum = musicListennum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMusicTime() {
		return musicTime;
	}
	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime;
	}
	
	
}
