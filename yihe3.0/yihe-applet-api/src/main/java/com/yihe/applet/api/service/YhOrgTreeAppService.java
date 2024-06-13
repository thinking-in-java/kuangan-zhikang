package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhOrgTreeApp;

import java.util.List;

public interface YhOrgTreeAppService {
	YhOrgTreeApp selectByPrimaryKey(String orgCode);
	/**
	 * 根据orgCode、orgName获取组织全名
	 * 例：10010101 --》 公司1001（顶级组织）>公司100101>公司10010101（当前查询组织）
	 * @param orgCode 当前
	 * @param orgFullName
	 */
	 public String selectFullOrgName(String orgCode,String orgName);

	/**
	 * 其上所有直系父节点
	 */
	public List<String> allImmediateParentNodes(String orgCode,List<String> list);

	public List<String> allImmediateParentOrg(String orgCode,List<String> list);
}
