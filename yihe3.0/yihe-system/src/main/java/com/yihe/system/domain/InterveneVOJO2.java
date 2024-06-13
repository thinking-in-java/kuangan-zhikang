package com.yihe.system.domain;

import java.util.List;

/**
 * 健康干预数据表格封装实体
 * 
 * @author Administrator
 *
 */
public class InterveneVOJO2 {
private	List<InterveneVOJO> record;
private	List<String> userList;

private InterveneVOJO city;

private List<InterveneIntelligentGroupingVOJO> intelligence;


 

public List<String> getUserList() {
	return userList;
}

public void setUserList(List<String> userList) {
	this.userList = userList;
}

public List<InterveneIntelligentGroupingVOJO> getIntelligence() {
	return intelligence;
}

public void setIntelligence(List<InterveneIntelligentGroupingVOJO> intelligence) {
	this.intelligence = intelligence;
}

public List<InterveneVOJO> getRecord() {
	return record;
}

public void setRecord(List<InterveneVOJO> record) {
	this.record = record;
}

public InterveneVOJO getCity() {
	return city;
}

public void setCity(InterveneVOJO city) {
	this.city = city;
}

}
