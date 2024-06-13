package com.yihe.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;

/**
 * 运动记录对象 yh_sport_diary
 * 
 * @author yihe
 * @date 2020-04-08
 */
public class YhSportDiary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long uSportId;

    /** 时间 */
    @Excel(name = "时间")
    private String entryTime;

    /** 体重 */
    @Excel(name = "体重")
    private String uweight;

    /** 总数 */
    @Excel(name = "总数")
    private String tenergy;

    /** 运动记录 */
    @Excel(name = "运动记录")
    private String diaryDetails;

    

    private String diaryBoutique;

    private String diaryRecommend;
    
    
    /** $column.columnComment */
    @Excel(name = "运动记录")
    private String userCode;

    /** 步数 */
    @Excel(name = "步数")
    private String number;

    private String Data_Source;

    private int day;
	private int daynum;

	private String userName;

	private String gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDaynum() {
		return daynum;
	}

	public void setDaynum(int daynum) {
		this.daynum = daynum;
	}

	public String getDiaryBoutique() {
		return diaryBoutique;
	}

	public void setDiaryBoutique(String diaryBoutique) {
		this.diaryBoutique = diaryBoutique;
	}

	public String getDiaryRecommend() {
		return diaryRecommend;
	}

	public void setDiaryRecommend(String diaryRecommend) {
		this.diaryRecommend = diaryRecommend;
	}

	public String getData_Source() {
        return Data_Source;
    }

    public void setData_Source(String data_Source) {
        Data_Source = data_Source;
    }

    public void setuSportId(Long uSportId)
    {
        this.uSportId = uSportId;
    }

    public Long getuSportId() 
    {
        return uSportId;
    }
    public void setEntryTime(String entryTime) 
    {
        this.entryTime = entryTime;
    }

    public String getEntryTime() 
    {
        return entryTime;
    }
    public void setUweight(String uweight) 
    {
        this.uweight = uweight;
    }

    public String getUweight() 
    {
        return uweight;
    }
    public void setTenergy(String tenergy) 
    {
        this.tenergy = tenergy;
    }

    public String getTenergy() 
    {
        return tenergy;
    }
    public void setDiaryDetails(String diaryDetails) 
    {
        this.diaryDetails = diaryDetails;
    }

    public String getDiaryDetails() 
    {
        return diaryDetails;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uSportId", getuSportId())
            .append("entryTime", getEntryTime())
            .append("uweight", getUweight())
            .append("tenergy", getTenergy())
            .append("diaryDetails", getDiaryDetails())
            .append("userCode", getUserCode())
            .append("remark", getRemark())
            .append("number", getNumber())
            .toString();
    }
}
