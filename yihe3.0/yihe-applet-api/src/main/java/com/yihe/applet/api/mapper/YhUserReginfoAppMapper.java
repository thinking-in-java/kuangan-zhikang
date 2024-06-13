package com.yihe.applet.api.mapper;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhUserReginfoAppMapper {

	 Integer findUserRole(String usercode);

	List<YhUserReginfoApp> findDoctorForUserInfo(String usercode);
	
	int findUserCodeByOrgIsExist(String userCode);
	
	int findUserNameExist(String username);
	
	List<String> findUserCodeByUserTypeFor5();

	 int deleteByPrimaryKey(String userCode);

	    int insert(YhUserReginfoApp record);

	    int insertSelective(YhUserReginfoApp record);

	    YhUserReginfoApp selectByPrimaryKey(String userCode);

	    int updateByPrimaryKeySelective(YhUserReginfoApp record);

	    int updateByPrimaryKey(YhUserReginfoApp record);

	int updateByUsercodeAndLoginTime(@Param("record") YhUserReginfoApp record);

	int updateByUsernameAndMobile(@Param("userName") String userName, @Param("userPwd") String userPwd,
			@Param("mobile") String mobile);

	YhUserReginfoApp selectByUserNameAndUserPwd(@Param("userName") String userName, @Param("userPwd") String userPwd);

	YhUserReginfoApp selectByshoujiAndUsername(@Param("userName") String userName, @Param("mobile") String mobile);
    
    YhUserReginfoApp findYhUserReginfo(String userCode);
    
    YhUserReginfoApp selectUserPwd(@Param ("userCode") String userCode,@Param ("userPwd") String userPwd);   
    
    int updatePwd(YhUserReginfoApp yhUserReginfo);
    YhUserReginfoApp finddoctorHderer(String userCode);
    
}