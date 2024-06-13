package com.yihe.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;

/**
 * 用户绑定设备对象 yh_user_device
 * 
 * @author yihe
 * @date 2020-04-20
 */
public class YhUserDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Integer tid;
    
    
    private String xueya;
    private String xuetan;
    private String xueyaxuetan;
    
    private String name;
    private String username;
    
    /** 设备ID */
    private String terminalid;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String usercode;

    /** 是否在线：0-在线，1-未穿戴，2-已穿戴 */
    @Excel(name = "是否在线：0-在线，1-未穿戴，2-已穿戴")
    private String online;

    /** 紧急呼叫手机卡号 */
    @Excel(name = "紧急呼叫手机卡号")
    private String sosNos;

    /** $column.columnComment */
    @Excel(name = "紧急呼叫手机卡号")
    private String note;

    /** 设备类型：1-血压计，2-血糖仪 */
    @Excel(name = "设备类型：1-血压计，2-血糖仪")
    private String terminaltype;

    /** 设备名 */
    @Excel(name = "设备名")
    private String terminalname;

    /** $column.columnComment */
    @Excel(name = "设备名")
    private String mobileNo;
    
    
    
  

	public String getXueya() {
		return xueya;
	}

	public void setXueya(String xueya) {
		this.xueya = xueya;
	}

	public String getXuetan() {
		return xuetan;
	}

	public void setXuetan(String xuetan) {
		this.xuetan = xuetan;
	}

	public String getXueyaxuetan() {
		return xueyaxuetan;
	}

	public void setXueyaxuetan(String xueyaxuetan) {
		this.xueyaxuetan = xueyaxuetan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getBindingtime() {
		return bindingtime;
	}

	public void setBindingtime(String bindingtime) {
		this.bindingtime = bindingtime;
	}

	private String bindingtime;
    
    

    public String getBindingTime() {
		return bindingtime;
	}

	public void setBindingTime(String bindingTime) {
		this.bindingtime = bindingTime;
	}

	public void setTerminalid(String terminalid) 
    {
        this.terminalid = terminalid;
    }

    public String getTerminalid() 
    {
        return terminalid;
    }
    public void setUsercode(String usercode) 
    {
        this.usercode = usercode;
    }

    public String getUsercode() 
    {
        return usercode;
    }
    public void setOnline(String online) 
    {
        this.online = online;
    }

    public String getOnline() 
    {
        return online;
    }
    public void setSosNos(String sosNos) 
    {
        this.sosNos = sosNos;
    }

    public String getSosNos() 
    {
        return sosNos;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }
    public void setTerminaltype(String terminaltype) 
    {
        this.terminaltype = terminaltype;
    }

    public String getTerminaltype() 
    {
        return terminaltype;
    }
    public void setTerminalname(String terminalname) 
    {
        this.terminalname = terminalname;
    }

    public String getTerminalname() 
    {
        return terminalname;
    }
    public void setMobileNo(String mobileNo) 
    {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() 
    {
        return mobileNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("terminalid", getTerminalid())
            .append("usercode", getUsercode())
            .append("online", getOnline())
            .append("sosNos", getSosNos())
            .append("note", getNote())
            .append("terminaltype", getTerminaltype())
            .append("terminalname", getTerminalname())
            .append("mobileNo", getMobileNo())
            .toString();
    }
}
