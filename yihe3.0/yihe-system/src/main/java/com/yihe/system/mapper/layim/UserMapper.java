package com.yihe.system.mapper.layim;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.layim.User;

public interface UserMapper {
	 
    User getUser(String id);
    User findUserNameAndImg(String usercode);
    List<User> getUsersByIds(List<String> userIds);
    
    List<User> findUserInfoByDoctor(String usercode);
    
    List<User> findUserNameAndImgBygroup(@Param("usercode")String usercode,@Param("groupid")String groupid);
    
    
}
