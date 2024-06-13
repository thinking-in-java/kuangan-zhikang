package com.yihe.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;

/**
 * 用户膳食日志对象 yh_food_user_diary
 * 
 * @author yihe
 * @date 2020-04-07
 */
public class YhFoodUserDiary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long uFdId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String cTime;

    /** 能量级别 */
    @Excel(name = "能量级别")
    private Long energyLevel;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private Long dataSources;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private Long rId;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String fat;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String cho;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private Long cholesterol;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String energyKcal;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String fElements;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String acElements;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private Long isDel;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String userCode;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String protein;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private Long alvol;

    /** $column.columnComment */
    @Excel(name = "能量级别")
    private String cellulose;
    
  private String userName;
    
    private int gender;
    
    private Date birthdate;
    
    
    private String situation;
    
    private float zaoKacl;
    private float wuKacl;
    private float wanKacl;
    private float jiaKacl;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private String dataSource;


    

    public float getZaoKacl() {
		return zaoKacl;
	}

	public void setZaoKacl(float zaoKacl) {
		this.zaoKacl = zaoKacl;
	}

	public float getWuKacl() {
		return wuKacl;
	}

	public void setWuKacl(float wuKacl) {
		this.wuKacl = wuKacl;
	}

	public float getWanKacl() {
		return wanKacl;
	}

	public void setWanKacl(float wanKacl) {
		this.wanKacl = wanKacl;
	}

	public float getJiaKacl() {
		return jiaKacl;
	}

	public void setJiaKacl(float jiaKacl) {
		this.jiaKacl = jiaKacl;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setuFdId(Long uFdId) 
    {
        this.uFdId = uFdId;
    }

    public Long getuFdId() 
    {
        return uFdId;
    }
    public void setcTime(String cTime) 
    {
        this.cTime = cTime;
    }

    public String getcTime() 
    {
        return cTime;
    }
    public void setEnergyLevel(Long energyLevel) 
    {
        this.energyLevel = energyLevel;
    }

    public Long getEnergyLevel() 
    {
        return energyLevel;
    }
    public void setDataSources(Long dataSources) 
    {
        this.dataSources = dataSources;
    }

    public Long getDataSources() 
    {
        return dataSources;
    }
    public void setrId(Long rId) 
    {
        this.rId = rId;
    }

    public Long getrId() 
    {
        return rId;
    }
    public void setFat(String fat)
    {
        this.fat = fat;
    }

    public String getFat()
    {
        return fat;
    }
    public void setCho(String cho)
    {
        this.cho = cho;
    }

    public String getCho()
    {
        return cho;
    }
    public void setCholesterol(Long cholesterol) 
    {
        this.cholesterol = cholesterol;
    }

    public Long getCholesterol() 
    {
        return cholesterol;
    }
    public void setEnergyKcal(String energyKcal)
    {
        this.energyKcal = energyKcal;
    }

    public String getEnergyKcal()
    {
        return energyKcal;
    }
    public void setfElements(String fElements) 
    {
        this.fElements = fElements;
    }

    public String getfElements() 
    {
        return fElements;
    }
    public void setAcElements(String acElements) 
    {
        this.acElements = acElements;
    }

    public String getAcElements() 
    {
        return acElements;
    }
    public void setIsDel(Long isDel) 
    {
        this.isDel = isDel;
    }

    public Long getIsDel() 
    {
        return isDel;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setProtein(String protein)
    {
        this.protein = protein;
    }

    public String getProtein()
    {
        return protein;
    }
    public void setAlvol(Long alvol) 
    {
        this.alvol = alvol;
    }

    public Long getAlvol() 
    {
        return alvol;
    }
    public void setCellulose(String cellulose)
    {
        this.cellulose = cellulose;
    }

    public String getCellulose()
    {
        return cellulose;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uFdId", getuFdId())
            .append("cTime", getcTime())
            .append("energyLevel", getEnergyLevel())
            .append("dataSources", getDataSources())
            .append("rId", getrId())
            .append("fat", getFat())
            .append("cho", getCho())
            .append("cholesterol", getCholesterol())
            .append("energyKcal", getEnergyKcal())
            .append("fElements", getfElements())
            .append("acElements", getAcElements())
            .append("isDel", getIsDel())
            .append("userCode", getUserCode())
            .append("protein", getProtein())
            .append("alvol", getAlvol())
            .append("cellulose", getCellulose())
            .toString();
    }
}
