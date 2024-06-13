/**
 * 
 */
package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class YhSportMotionVojo implements Serializable {
	private Integer id;

    /**
     * （数据字典）动作类型：1-力量动作，2-主体动作，3-拉伸动作
     *
     * @mbg.generated
     */
    private Integer sportType;

    /**
     * 动作名称
     *
     * @mbg.generated
     */
    private String sportName;

    /**
     * 动作步骤
     *
     * @mbg.generated
     */
    private String sportSteps;

    /**
     * 动作感觉
     *
     * @mbg.generated
     */
    private String sportFell;

    /**
     * 呼吸节奏
     *
     * @mbg.generated
     */
    private String sportBreath;

    /**
     * 练习次数（每次20秒）
     *
     * @mbg.generated
     */
    private Integer followSecond;

    /**
     * 动作图片
     *
     * @mbg.generated
     */
    private String sportImg;

    /**
     * 动作视频
     *
     * @mbg.generated
     */
    private String sportVideo;

    /**
     * 逻辑删除(2显示，1删除)
     *
     * @mbg.generated
     */
    private Integer isDel;

    private String kcal;

    /**
     * 追加次数
     *
     * @mbg.generated
     */
    private Integer hot;

    /**
     * 备用
     *
     * @mbg.generated
     */
    private String standby;
    
    private int whether;
    
    private int subjectId;
    
    private String num;//天数
    
    private String minute;//分钟
    
    private String kcalsum;//千卡

    private static final long serialVersionUID = 1L;

    
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getKcalsum() {
		return kcalsum;
	}

	public void setKcalsum(String kcalsum) {
		this.kcalsum = kcalsum;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getWhether() {
		return whether;
	}

	public void setWhether(int whether) {
		this.whether = whether;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSportType() {
		return sportType;
	}

	public void setSportType(Integer sportType) {
		this.sportType = sportType;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getSportSteps() {
		return sportSteps;
	}

	public void setSportSteps(String sportSteps) {
		this.sportSteps = sportSteps;
	}

	public String getSportFell() {
		return sportFell;
	}

	public void setSportFell(String sportFell) {
		this.sportFell = sportFell;
	}

	public String getSportBreath() {
		return sportBreath;
	}

	public void setSportBreath(String sportBreath) {
		this.sportBreath = sportBreath;
	}

	public Integer getFollowSecond() {
		return followSecond;
	}

	public void setFollowSecond(Integer followSecond) {
		this.followSecond = followSecond;
	}

	public String getSportImg() {
		return sportImg;
	}

	public void setSportImg(String sportImg) {
		this.sportImg = sportImg;
	}

	public String getSportVideo() {
		return sportVideo;
	}

	public void setSportVideo(String sportVideo) {
		this.sportVideo = sportVideo;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getKcal() {
		return kcal;
	}

	public void setKcal(String kcal) {
		this.kcal = kcal;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getStandby() {
		return standby;
	}

	public void setStandby(String standby) {
		this.standby = standby;
	}
    
    
    
    
}
