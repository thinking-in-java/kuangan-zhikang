package com.yihe.system.service.impl;

import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.domain.YhUserRolePermission;
import com.yihe.system.mapper.YhUserAdminInfoMapper;
import com.yihe.system.mapper.YhUserRoleManageMapper;
import com.yihe.system.service.YhRoleManageService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional

public class YhRoleManageServiceImpl  implements YhRoleManageService{
	@Autowired
	YhUserRoleManageMapper yhrolemanagemapper;
	@Autowired
	YhUserAdminInfoMapper yhUserAdminInfoMapper;
	
	@Override
	public List<YhUserRole> selectallrole() {
		// TODO Auto-generated method stub
		return yhrolemanagemapper.selecrlistrole();
	}

	@Override
	public List<YhUserRole> selectrolebycode(Integer roletype, String usercode) {
		int type=1;
		if(roletype==1||roletype==5){
			type=1;
		}else{
			type=2;
		}
		List<YhUserRole> selectrolebycode=yhrolemanagemapper.selectrolebycode(type,usercode);
		for (int i=0;i<selectrolebycode.size();i++){
			selectrolebycode.get(i).setIsshow(true);
			if(i==0){
				selectrolebycode.get(i).setIshava(true);
			}
		}

		if(roletype==1){
			List<YhUserRole> selectrolebycode1=new ArrayList<>();
			if(selectrolebycode.size()!=0){
				selectrolebycode1.add(selectrolebycode.get(0));
			}

			selectrolebycode1=selectall(selectrolebycode1,selectrolebycode1);
			for (int o=0;o<selectrolebycode1.size();o++){
				boolean b=false;
				for (int p=0;p<selectrolebycode.size();p++){
							if(selectrolebycode1.get(o).getRoleId()==selectrolebycode.get(p).getRoleId()){
								b=true;
							}

				}
				if(!b){
					selectrolebycode1.get(o).setIsshow(false);
					selectrolebycode.add(selectrolebycode1.get(o));
				}
			}

		}

		if(roletype==2){
			List<YhUserRole> selectrolebycode1=new ArrayList<>();
			if(selectrolebycode.size()!=0){
				selectrolebycode1.add(selectrolebycode.get(0));
			}

			selectrolebycode1=selectall1(selectrolebycode1,selectrolebycode1);
			for (int o=0;o<selectrolebycode1.size();o++){
				boolean b=false;
				for (int p=0;p<selectrolebycode.size();p++){
					if(selectrolebycode1.get(o).getRoleId()==selectrolebycode.get(p).getRoleId()){
						b=true;
					}


				}
				if(!b){
					selectrolebycode1.get(o).setIsshow(false);
					selectrolebycode.add(selectrolebycode1.get(o));
				}
			}

		}
		//递归查询出的角色
		return selectrolebycode;
	}

	public  List<YhUserRole>  selectuserrole(Integer Roleid){
		List<YhUserRole> roles=yhrolemanagemapper.selectroleid(Roleid);
		return roles;
	}

	public List<YhUserRole> selectall(List<YhUserRole> roles,List<YhUserRole> Allrole){
		List<YhUserRole> jai=new ArrayList<>();
		int k=0;
		for (int i=0;i<roles.size();i++){
			List<YhUserRole> jai2=	 selectuserrole(roles.get(i).getRoleId());
			for (int u=1;u<jai2.size();u++){
				jai2.get(u).setIsshow(false);
			}
			k=k+jai2.size();
			jai.addAll(jai2);
		}
		if(k==0){
			return Allrole;
		}else{
			Allrole.addAll(jai);
			return selectall(jai,Allrole);
		}

	}

	public  List<YhUserRole>  selectuserrole1(Integer Roleid){
		List<YhUserRole> roles=yhrolemanagemapper.selectroleid1(Roleid);
		return roles;
	}

	public List<YhUserRole> selectall1(List<YhUserRole> roles,List<YhUserRole> Allrole){
		List<YhUserRole> jai=new ArrayList<>();
		int k=0;
		for (int i=0;i<roles.size();i++){
			List<YhUserRole> jai2=	 selectuserrole(roles.get(i).getPid());
			for (int u=0;u<jai2.size();u++){
				List<YhUserRole> jai3=	 selectuserrole1(jai2.get(u).getRoleId());
				for (int r=0;r<jai3.size();r++){
					jai3.get(r).setIsshow(false);
				}
				jai.addAll(jai3);
			}
			k=k+jai2.size();

		}
		if(k==0){
			return Allrole;
		}else{
			Allrole.addAll(jai);
			return selectall1(jai,Allrole);
		}

	}

	@Override
	public List<YhUserRole> selecdoctorrole(Integer roletype, String usercode) {
		List<YhUserRole> roles=yhrolemanagemapper.selecdoctorrole(roletype,usercode);
		for (int i=0;i<roles.size();i++){
			roles.get(i).setCount(yhrolemanagemapper.selectrolecount(roles.get(i)));
			if(roles.get(i).getManage_roleid()!=null && roles.get(i).getManage_roleid()!=""){
				String []item=roles.get(i).getManage_roleid().split("R");
				String news="";
				for (int h=0;h<item.length;h++){
					news+=item[h];
				}
				String []item2=news.split(";");
				String names="";
				for (int j=0;j<item2.length;j++){
					names+=yhrolemanagemapper.selectrolename(item2[j])+";";
				}
				roles.get(i).setManage_roleid(names);
			}

		}
		return roles;
	}


	@Override
	public int selectuserrolepowerbyroleid(int roleId) {
		// TODO Auto-generated method stub
		return yhrolemanagemapper.selectuserroleroleid(roleId);
	}


	@Override
	public Map<String, Object> yhuserpermission(int roleId) {
		// TODO Auto-generated method stub
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		//先查出角色的类型
		Integer roletype=yhrolemanagemapper.selectroletypebyusercode(usercode);
		//查询该角色的类型
		Integer nowuserrole=yhrolemanagemapper.selectnowuserrole(usercode);
		boolean isshow=true;
		if(roleId==nowuserrole){
			isshow=false;
		}
		//先查询角色拥有的权限跟改角色拥有的权限
		List<YhUserPermission> havepermission=yhrolemanagemapper.selectpermissionbyroleid(roleId);
		List<YhUserPermission> allpermission=yhrolemanagemapper.selectpermissionall(usercode,roletype);
		//循环所有权限，变成false
			for(int i = 0; i < allpermission.size(); i++) {
				allpermission.get(i).setChecked(false);
				allpermission.get(i).setOpen(false);
			}
		//如果该权限拥有，则把checked等变为true
			for (int i = 0; i < havepermission.size(); i++) {
				for (int j = 0; j < allpermission.size(); j++) {
					if(havepermission.get(i).getPermissionId().equals(allpermission.get(j).getPermissionId())) {
						allpermission.get(j).setChecked(true);
						allpermission.get(j).setOpen(true);
					}
				}
			}
		Map<String,Object> map=new HashMap<>();
		map.put("isshow",isshow);
		map.put("allpermission",allpermission);
		return map;
	}

	@Override
	public Map selectdoctorrolepower(int roleId) {
		// TODO Auto-generated method stub
		Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		//先查出角色所属机构的id
		Integer orgcodeid=yhrolemanagemapper.selectorgcodeid(roleId);
		//再查出机构能分配哪些医生的权限
		Integer doctorid=yhrolemanagemapper.selectdoctorcode("R"+orgcodeid+";");
		List<YhUserPermission> allpermission=new ArrayList<>();
		if(orgcodeid==1){
			 allpermission=yhrolemanagemapper.selectalldoctorper();
		}else{
			 allpermission=yhrolemanagemapper.selectpermissionbyroleid(doctorid);
		}
		//查询该角色的类型
		//Integer nowuserrole=yhrolemanagemapper.selectnowuserrole(usercode);
		boolean isshow=true;
		if(roleId==1){
			isshow=false;
		}
		Map<String,Object> map=new HashMap<>();
		//先查询角色拥有的权限跟改角色拥有的权限
		List<YhUserPermission> havepermission=yhrolemanagemapper.selectpermissionbyroleid(roleId);
		if(doctorid==null  && orgcodeid!=1){
			return map;
		}else{

		}

		//循环所有权限，变成false
		for(int i = 0; i < allpermission.size(); i++) {
			allpermission.get(i).setChecked(false);
			allpermission.get(i).setOpen(false);
		}
		//如果该权限拥有，则把checked等变为true
		for (int i = 0; i < havepermission.size(); i++) {
			for (int j = 0; j < allpermission.size(); j++) {
				if(havepermission.get(i).getPermissionId().equals(allpermission.get(j).getPermissionId())) {
					allpermission.get(j).setChecked(true);
					allpermission.get(j).setOpen(true);
				}
			}
		}

		map.put("isshow",isshow);
		map.put("allpermission",allpermission);
		return map;
	}


	@Override
	public int changerolepowerbyroleid(String authIds, int roleId) {
		// TODO Auto-generated method stub
		authIds.trim();
		authIds=authIds.substring(0,authIds.length()-1);
		authIds=authIds.substring(1,authIds.length());
		//切割权限
		String[] splits = authIds.split(",");
		//查询角色的类型
		int typer=yhUserAdminInfoMapper.selecttyper(roleId);
		//查询子机构
		Integer[] zijigou=yhUserAdminInfoMapper.selectnextjian(roleId,typer);
		changepermission(zijigou,roleId,splits);
		//清空该角色拥有的权限
		yhrolemanagemapper.deleterolepermissionbyroleid(roleId);
		//循环添加该角色的权限
		for (int i = 0; i < splits.length; i++) {
			if(splits[i] !=null && !splits[i] .equals("")){
			yhrolemanagemapper.insertrolepermission(roleId,Integer.parseInt(splits[i]));
			}
		}
		return 0;
	}



	@Override
	public int deleterolepermission(int roleId) {
		// TODO Auto-generated method stub
		//清空该角色拥有的权限
		return yhrolemanagemapper.deleterolepermissionbyroleid(roleId);
	}


	@Override
	public int insert(YhUserRole yhuerrole) {
		// TODO Auto-generated method stub
		return yhrolemanagemapper.insert(yhuerrole);
	}

	@Override
	public int maxorder() {
		return yhrolemanagemapper.maxorder();
	}


	@Override
	public int updateByPrimaryKey(YhUserRole yhuerrole) {
		// TODO Auto-generated method stub
		return yhrolemanagemapper.updateByPrimaryKey(yhuerrole);
	}

	@Override
	public int selectrolenamecount(YhUserRole yhuerrole) {
		return yhrolemanagemapper.selectrolenamecount(yhuerrole);
	}


	@Override
	public int deleteByPrimaryKey(int roleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectpidbyusercode(String usercode) {
		return yhrolemanagemapper.selectpidbyusercode(usercode);
	}

	//改变子集的权限
	public  void changepermission(Integer [] arr, int roleId,String[] splits){
		//循环查询每个机构的权限
		if(arr.length!=0){
			for (int i=0;i<arr.length;i++){
				//老的权限
				List<YhUserRolePermission> rolePermissions=yhUserAdminInfoMapper.selectpermissbyroleid(arr[i]);
				List<Integer> list=new ArrayList<>();
				//循环老的权限跟新的权限，找出相同的权限
				for (int k=0;k<splits.length;k++){
					for (int h=0;h<rolePermissions.size();h++){
						if(Integer.valueOf(splits[k]).equals(Integer.valueOf(rolePermissions.get(h).getPermissionId()))){
							list.add(rolePermissions.get(h).getPermissionId());
						}
					}
				}
				yhUserAdminInfoMapper.qinkongquanxian(arr[i]);
				if(list.size()!=0){
					yhUserAdminInfoMapper.addnewquanxian(list,arr[i]);
				}
				Integer[] ordorgpermission=yhUserAdminInfoMapper.selectnextjian(arr[i],1);
				if(ordorgpermission.length!=0){
					changepermission(ordorgpermission,roleId,splits);
				}
			}

		}
	}

	@Override
	public List<YhUserRole> selectrolebycode1(Integer roletype, String usercode) {
		// TODO Auto-generated method stub
		return yhrolemanagemapper.selectrolebycode1(roletype,usercode);
	}

}
