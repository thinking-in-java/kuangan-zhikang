package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhUserRoleManageMapper {
		List<YhUserRole> selecrlistrole();
		List<YhUserRole> selectrolebycode(@Param("roletype")Integer roletype,@Param("usercode")String usercode);
		List<YhUserRole> selecdoctorrole(@Param("roletype")Integer roletype,@Param("usercode")String usercode);
		int insert(YhUserRole record);
		int maxorder();
		int updateByPrimaryKey(YhUserRole record);
		int selectuserroleroleid(int roleId);
		int deleteByPrimaryKey(Integer roleId);
		Integer selectroletypebyusercode(String usercode);
		List<YhUserPermission> selectpermissionbyroleid(Integer roleId);
		List<YhUserPermission> selectalldoctorper();
		Integer selectnowuserrole(String usercode);
		List<YhUserPermission> selectpermissionall(@Param("usercode")String usercode,@Param("roletype")Integer roletype);
		int deleterolepermissionbyroleid(int roleId);
		int insertrolepermission(@Param("roleId")Integer roleId,@Param("permissionId")Integer permissionId);
		int selectrolenamecount(YhUserRole yhuerrole);
		int selectpidbyusercode(String usercode);
		Integer selectorgcodeid(int roleId);
		Integer selectdoctorcode(String selectdoctorcode);
		String selectrolename(String roleid);
		
		List<YhUserRole> selectrolebycode1(@Param("roletype")Integer roletype,@Param("usercode")String usercode);
		Integer selectrolecount(YhUserRole yhUserRole);
		String selectorgcode(String usercoce);
		List<YhUserRole> selectroleid(Integer Roleid);
		List<YhUserRole> selectroleid1(Integer Roleid);
		
}
