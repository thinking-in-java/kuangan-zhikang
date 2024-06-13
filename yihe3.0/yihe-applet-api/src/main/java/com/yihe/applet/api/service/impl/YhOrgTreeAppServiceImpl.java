package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhOrgTreeApp;
import com.yihe.applet.api.mapper.YhOrgTreeAppMapper;
import com.yihe.applet.api.service.YhOrgTreeAppService;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhOrgTreeAppServiceImpl implements YhOrgTreeAppService{
	@Autowired
	YhOrgTreeAppMapper yhOrgTreeAppMapper;
	@Override
	public YhOrgTreeApp selectByPrimaryKey(String orgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeAppMapper.selectByPrimaryKey(orgCode);
	}
	
	@Override
	public String selectFullOrgName(String orgCode,String orgName) {//0,中国水利水电第八集团有限公司 > 集团总公司
		YhOrgTreeApp yhOrgTree = yhOrgTreeAppMapper.selectByPrimaryKey(orgCode);//0
		//yhOrgTree --> null
		if(yhOrgTree!=null) {
			String fullOrgName = StringUtil.isEmpty(orgName)
					? yhOrgTree.getOrgName() : String.format("%s>%s", yhOrgTree.getOrgName(),orgName);
			//fullOrgName --> 中国水利水电第八集团有限公司 > 集团总公司
			return selectFullOrgName(yhOrgTree.getSuperOrgCode(), fullOrgName);//
		}
		return orgName;
	}

	@Override
	public List<String> allImmediateParentNodes(String orgCode,List<String> list) {

		YhOrgTreeApp yhOrgTree = yhOrgTreeAppMapper.selectByPrimaryKey(orgCode);
		if(null==yhOrgTree){
			return list;
		}
		//父节点
		String superOrgCode = yhOrgTree.getSuperOrgCode();
		String userCode = yhOrgTree.getUserCode();
		if(StringUtil.isNotEmpty(userCode)){
			list.add(userCode);
		}
		if("0".equals(superOrgCode)){
			return list;
		}else{
			allImmediateParentNodes(superOrgCode,list);
		}
		return list;
	}

	@Override
	public List<String> allImmediateParentOrg(String orgCode,List<String> list) {
		list.add(orgCode);
		YhOrgTreeApp yhOrgTree = yhOrgTreeAppMapper.selectByPrimaryKey(orgCode);
		if(null==yhOrgTree){
			return list;
		}
		//父节点
		String superOrgCode = yhOrgTree.getSuperOrgCode();
		if("0".equals(superOrgCode)){
			return list;
		}else{
			allImmediateParentOrg(superOrgCode,list);
		}
		return list;
	}

}
