package com.yihe.system.mapper;

import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhUserAdminInfoMapper {
	long countByExample(YhUserAdminInfoExample example);

	int deleteByExample(YhUserAdminInfoExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(YhUserAdminInfo record);

	int insertSelective(YhUserAdminInfo record);

	List<YhUserAdminInfo> selectByExample(YhUserAdminInfoExample example);

	YhUserAdminInfo selectByPrimaryKey(Integer id);

	YhUserAdminInfo selectByPrimaryKeyByusercode(String usercode);

	int selectRegisterByDoctor(@Param("doctor") Integer doctor);

	int updateByExampleSelective(@Param("record") YhUserAdminInfo record,
			@Param("example") YhUserAdminInfoExample example);

	int updateByExample(@Param("record") YhUserAdminInfo record, @Param("example") YhUserAdminInfoExample example);

	int updateByPrimaryKeySelective(YhUserAdminInfo record);

	int updateByPrimaryKey(YhUserAdminInfo record);

	int selectByadminCount(String orgCode);
	
	List<TestingVOJO> selectByPrimaryKeyByDoctor(@Param("doctor")Integer doctor,@Param("orgid")Integer orgid,@Param("username")String username,@Param("usernameLike")String usernameLike,@Param("order")String order);

	YhUserAdminInfo showid();
	
	int updateByPrimaryKeySelectiveUser(YhUserAdminInfo admin);

	List<YhUserRole> selectRole(Integer roleoid);

	String selectnoworglocation(String locations);

	int updatelocation(@Param("locations")String locations,@Param("sata")String sata);

	int updatenewlocations(@Param("roleid")Integer roleid,@Param("beguns")String beguns);

	String selectlocation(Integer roleid);

	List<YhUserRolePermission> selectordpermission(Integer promisstion);

	List<YhUserRolePermission> selectpermissbyroleid(Integer roleid);

	Integer[] selectnextjian(@Param("roleid")Integer roleid,@Param("role_type")Integer role_type);

	int qinkongquanxian(Integer qingkong);

	int addnewquanxian(@Param("list")List<Integer> list,@Param("roleid")Integer roleid);

	int selecttyper(Integer roleid);

	Integer changeusermanager(@Param("roleid")Integer roleid, @Param("usercode")String usercode);

	List<YhUserAdminInfo> selectAll(String orgCode);



	Integer selectAllCount();

	Integer selectAllCountToday();

	Integer selectAllCountMo();



	Integer selectAllCountNM();

	Integer selectAllCountTodayNM();

	Integer selectAllCountMoNM();

}