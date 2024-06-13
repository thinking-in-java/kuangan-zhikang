package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * 轮播图片
 * @version: shayu3.0
 * @Description: 
 * @author: lhh
 * @date: 2020年6月10日 下午3:13:01
 */
public class YhHomeinfoConfigApp implements Serializable{
	private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 1:轮播图 ；2:健康头条 */
    private Long infoType;

    /** 信息图片 */
    private String infoImg;

    /** 信息标题 */
    private String infoTitle;

    /** 添加时间 */
    private String infoTime;

    /** 1:已发布；2:草稿 */
    private Long infoStatus;

    /** 来源（1，健康课堂 2，精彩活动） */
    private Long infoSource;

    /** 内容id */
    private Long infoContent;

    /** 轮播标识(1-5) */
    private Long infoTip;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInfoType(Long infoType) 
    {
        this.infoType = infoType;
    }

    public Long getInfoType() 
    {
        return infoType;
    }
    public void setInfoImg(String infoImg) 
    {
        this.infoImg = infoImg;
    }

    public String getInfoImg() 
    {
        return infoImg;
    }
    public void setInfoTitle(String infoTitle) 
    {
        this.infoTitle = infoTitle;
    }

    public String getInfoTitle() 
    {
        return infoTitle;
    }
    public void setInfoTime(String infoTime) 
    {
        this.infoTime = infoTime;
    }

    public String getInfoTime() 
    {
        return infoTime;
    }
    public void setInfoStatus(Long infoStatus) 
    {
        this.infoStatus = infoStatus;
    }

    public Long getInfoStatus() 
    {
        return infoStatus;
    }
    public void setInfoSource(Long infoSource) 
    {
        this.infoSource = infoSource;
    }

    public Long getInfoSource() 
    {
        return infoSource;
    }
    public void setInfoContent(Long infoContent) 
    {
        this.infoContent = infoContent;
    }

    public Long getInfoContent() 
    {
        return infoContent;
    }
    public void setInfoTip(Long infoTip) 
    {
        this.infoTip = infoTip;
    }

    public Long getInfoTip() 
    {
        return infoTip;
    }
}
