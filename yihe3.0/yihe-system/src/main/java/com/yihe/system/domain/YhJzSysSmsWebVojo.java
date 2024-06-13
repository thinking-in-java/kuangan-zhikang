package com.yihe.system.domain;

import java.util.Date;

/**
 * 
 * @author dong
 *
 */
public class YhJzSysSmsWebVojo {
    private Long smsId;

    private String templateId;

    private String smsname;

    private String signname;

    private String accesskey;

    private String accesskeysecret;

    private String templateType;

    private String templateText;
    
    private String templateGenre;

    private Integer type;
    
    //计划提醒
    private String tasktemplateId;
    
    private String tasktemplateType;

    private String tasktemplateText;
    
    private String tasktemplateGenre;
    
    //饮食指导
    private String foodtemplateId;
    
    private String foodtemplateType;

    private String foodtemplateText;
    
    private String foodtemplateGenre;
    
    //运动指导
    private String sportstemplateId;
    
    private String sportstemplateType;

    private String sportstemplateText;
    
    private String sportstemplateGenre;
    
    //健康知识
    private String knowledgetemplateId;
    
    private String knowledgetemplateType;

    private String knowledgetemplateText;
    
    private String knowledgetemplateGenre;
    
    //发货提醒
    private String logisticsId;
    
    private String logisticsType;

    private String logisticsText;
    
    private String logisticsGenre;
    
    
    
    public String getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getLogisticsText() {
		return logisticsText;
	}

	public void setLogisticsText(String logisticsText) {
		this.logisticsText = logisticsText;
	}

	public String getLogisticsGenre() {
		return logisticsGenre;
	}

	public void setLogisticsGenre(String logisticsGenre) {
		this.logisticsGenre = logisticsGenre;
	}

	public String getTasktemplateId() {
		return tasktemplateId;
	}

	public void setTasktemplateId(String tasktemplateId) {
		this.tasktemplateId = tasktemplateId;
	}

	public String getTasktemplateType() {
		return tasktemplateType;
	}

	public void setTasktemplateType(String tasktemplateType) {
		this.tasktemplateType = tasktemplateType;
	}

	public String getTasktemplateText() {
		return tasktemplateText;
	}

	public void setTasktemplateText(String tasktemplateText) {
		this.tasktemplateText = tasktemplateText;
	}

	public String getTasktemplateGenre() {
		return tasktemplateGenre;
	}

	public void setTasktemplateGenre(String tasktemplateGenre) {
		this.tasktemplateGenre = tasktemplateGenre;
	}

	public String getFoodtemplateId() {
		return foodtemplateId;
	}

	public void setFoodtemplateId(String foodtemplateId) {
		this.foodtemplateId = foodtemplateId;
	}

	public String getFoodtemplateType() {
		return foodtemplateType;
	}

	public void setFoodtemplateType(String foodtemplateType) {
		this.foodtemplateType = foodtemplateType;
	}

	public String getFoodtemplateText() {
		return foodtemplateText;
	}

	public void setFoodtemplateText(String foodtemplateText) {
		this.foodtemplateText = foodtemplateText;
	}

	public String getFoodtemplateGenre() {
		return foodtemplateGenre;
	}

	public void setFoodtemplateGenre(String foodtemplateGenre) {
		this.foodtemplateGenre = foodtemplateGenre;
	}

	public String getSportstemplateId() {
		return sportstemplateId;
	}

	public void setSportstemplateId(String sportstemplateId) {
		this.sportstemplateId = sportstemplateId;
	}

	public String getSportstemplateType() {
		return sportstemplateType;
	}

	public void setSportstemplateType(String sportstemplateType) {
		this.sportstemplateType = sportstemplateType;
	}

	public String getSportstemplateText() {
		return sportstemplateText;
	}

	public void setSportstemplateText(String sportstemplateText) {
		this.sportstemplateText = sportstemplateText;
	}

	public String getSportstemplateGenre() {
		return sportstemplateGenre;
	}

	public void setSportstemplateGenre(String sportstemplateGenre) {
		this.sportstemplateGenre = sportstemplateGenre;
	}

	public String getKnowledgetemplateId() {
		return knowledgetemplateId;
	}

	public void setKnowledgetemplateId(String knowledgetemplateId) {
		this.knowledgetemplateId = knowledgetemplateId;
	}

	public String getKnowledgetemplateType() {
		return knowledgetemplateType;
	}

	public void setKnowledgetemplateType(String knowledgetemplateType) {
		this.knowledgetemplateType = knowledgetemplateType;
	}

	public String getKnowledgetemplateText() {
		return knowledgetemplateText;
	}

	public void setKnowledgetemplateText(String knowledgetemplateText) {
		this.knowledgetemplateText = knowledgetemplateText;
	}

	public String getKnowledgetemplateGenre() {
		return knowledgetemplateGenre;
	}

	public void setKnowledgetemplateGenre(String knowledgetemplateGenre) {
		this.knowledgetemplateGenre = knowledgetemplateGenre;
	}

	public String getTemplateGenre() {
		return templateGenre;
	}

	public void setTemplateGenre(String templateGenre) {
		this.templateGenre = templateGenre;
	}

	public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getSmsname() {
        return smsname;
    }

    public void setSmsname(String smsname) {
        this.smsname = smsname == null ? null : smsname.trim();
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname == null ? null : signname.trim();
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey == null ? null : accesskey.trim();
    }

    public String getAccesskeysecret() {
        return accesskeysecret;
    }

    public void setAccesskeysecret(String accesskeysecret) {
        this.accesskeysecret = accesskeysecret == null ? null : accesskeysecret.trim();
    }

  
    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType == null ? null : templateType.trim();
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText == null ? null : templateText.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}