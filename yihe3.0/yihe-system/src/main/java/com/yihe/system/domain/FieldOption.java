package com.yihe.system.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FieldOption implements Comparable,	Serializable{
    private Integer fieldOptionId;
    private String referenceName;
    private String value;
    private String guiValue;
    private String guiValueEn;
    private String description;
    @NotNull(message="{field.option.sort.not.null}")
    //@Digits(integer=2,fraction=0,message="{field.option.sort.is.integer}")
    private Byte sort;

    private Boolean valid;

    public Integer getFieldOptionId() {
        return fieldOptionId;
    }

    public void setFieldOptionId(Integer fieldOptionId) {
        this.fieldOptionId = fieldOptionId;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName == null ? null : referenceName.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getGuiValue() {
        return guiValue;
    }

    public void setGuiValue(String guiValue) {
        this.guiValue = guiValue == null ? null : guiValue.trim();
    }
    
    public String getGuiValueEn() {
		return guiValueEn;
	}

	public void setGuiValueEn(String guiValueEn) {
		this.guiValueEn = guiValueEn;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    
    @Override
	public int compareTo(Object o) {
		FieldOption fo=null;
		int result=0;
		 if(o!=null){
			 fo=(FieldOption)o;
			 result=this.getSort().compareTo(fo.getSort()); 
		 }
		 
		 return result;
	}
}