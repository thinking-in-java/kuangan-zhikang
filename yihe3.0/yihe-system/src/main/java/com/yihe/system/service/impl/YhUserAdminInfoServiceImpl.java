package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhUserAdminInfoMapper;
import com.yihe.system.service.YhUserAdminInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* YhUserAdminInfoService实现
* Created by dyb on 2020/3/19.
*/
@Service
@Transactional

public class YhUserAdminInfoServiceImpl  implements YhUserAdminInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserAdminInfoServiceImpl.class);

    @Autowired
    YhUserAdminInfoMapper yhUserAdminInfoMapper;

	@Override
	public int selectByadminCount(String orgCode) {
		// TODO Auto-generated method stub
		return yhUserAdminInfoMapper.selectByadminCount(orgCode);
	}

	@Override
	public List<YhUserAdminInfo> selectByExample(YhUserAdminInfoExample example) {
		// TODO Auto-generated method stub
		return yhUserAdminInfoMapper.selectByExample(example);
	}

	@Override
	public YhUserAdminInfo selectByPrimaryKeyByusercode(String usercode) {
		// TODO Auto-generated method stub
		return yhUserAdminInfoMapper.selectByPrimaryKeyByusercode(usercode);
	}

	@Override
	public PageInfo<TestingVOJO> selectByPrimaryKeyByDoctor(Integer doctor,Integer orgid,String username,String usernameLike,String  order, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<TestingVOJO> selectByPrimaryKeyByDoctor = yhUserAdminInfoMapper.selectByPrimaryKeyByDoctor(doctor,orgid,username,usernameLike,order);
		return new PageInfo<>(selectByPrimaryKeyByDoctor);
	}

	@Override
	public List<YhUserRole> selectRole(Integer roleid) {
		//

		//先查询出这个这个健管师的所属机构，再根据这个机构查询出它名下的二级机构
			List<YhUserRole> roletype=yhUserAdminInfoMapper.selectRole(roleid);
		//查询出现在拥有哪些
			String location=yhUserAdminInfoMapper.selectlocation(roleid);
		System.out.println(location);
		String []item=null;
		if(location!=null && location!=""){
			String []nowstring=location.split("R");
			String sata="";
			for (int j=0;j<nowstring.length;j++){
				sata+=nowstring[j];
			}
			item=sata.split(";");
		}
		if(item!=null){
			for (int i=0;i<roletype.size();i++){
				for (int k=0;k<item.length;k++){
					if(roletype.get(i).getRoleId()==Integer.valueOf(item[k])){
						roletype.get(i).setIshava(true);
					}
				}
			}
		}

		return roletype;
	}

	@Override
	public Integer changemanager(Integer roleid, Integer[] arry) {
		String beguns="";
		//循环查出删除有该字段的角色
		if(arry!=null){
			for (int i=0;i<arry.length;i++){
				beguns+="R"+arry[i]+";";
				//给arr里面的所有机构的医生的权限


				//查询出未变之前角色的地址
				String locations= yhUserAdminInfoMapper.selectnoworglocation("R"+arry[i]+";");
				System.out.println(locations);
				if(locations!=null && locations!=""){
					String []nowstring=locations.split("R"+arry[i]+";");
					String sata="";
					for (int j=0;j<nowstring.length;j++){
						sata+=nowstring[j];
					}
					yhUserAdminInfoMapper.updatelocation(locations,sata);
				}
			}
			changepermisstion(roleid,arry);
		}


		//重新插入新的角色
		return yhUserAdminInfoMapper.updatenewlocations(roleid,beguns);
	}

	@Override
	public Integer changeusermanager(Integer roleid, String[] userCodes) {
		ArrayList<String> arrayList = new ArrayList<String>(userCodes.length);
		Collections.addAll(arrayList, userCodes);
		for (int i=0;i<userCodes.length;i++){
			yhUserAdminInfoMapper.changeusermanager(roleid,userCodes[i]);
		}
		return 0;
	}


	public void changepermisstion(Integer roleid, Integer[] arry){
		for (int i=0;i<arry.length;i++){
			//首先查找需要改变机构的子健管师
			Integer[] ordpermission=yhUserAdminInfoMapper.selectnextjian(arry[i],2);
			//新的可以拥有的权限
			List<YhUserRolePermission> newpermission=yhUserAdminInfoMapper.selectpermissbyroleid(roleid);
			for(int j=0;j<ordpermission.length;j++){
				//老的权限
				List<YhUserRolePermission> oldpermission=yhUserAdminInfoMapper.selectpermissbyroleid(ordpermission[j]);
				List<Integer> list=new ArrayList<>();
				//循环老的权限跟新的权限，找出相同的权限
					for (int k=0;k<newpermission.size();k++){
						for (int h=0;h<oldpermission.size();h++){
							System.out.println(newpermission.get(k).getPermissionId());
							System.out.println(oldpermission.get(h).getPermissionId());
							System.out.println(newpermission.get(k).getPermissionId()==oldpermission.get(h).getPermissionId());
							if(Integer.valueOf(newpermission.get(k).getPermissionId()).equals(Integer.valueOf(oldpermission.get(h).getPermissionId()))){
								list.add(newpermission.get(k).getPermissionId());
							}
						}
					}

				System.out.println(list);
				yhUserAdminInfoMapper.qinkongquanxian(ordpermission[j]);
				if(list.size()!=0){
					yhUserAdminInfoMapper.addnewquanxian(list,ordpermission[j]);
				}

			}

			Integer[] ordorgpermission=yhUserAdminInfoMapper.selectnextjian(arry[i],1);
			if(ordorgpermission.length!=0){
				changepermisstion(roleid,ordorgpermission);
			}

		}
	}

}