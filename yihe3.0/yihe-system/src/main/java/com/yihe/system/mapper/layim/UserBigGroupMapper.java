package com.yihe.system.mapper.layim;



import java.util.List;

public interface UserBigGroupMapper {
    List<String> getGroupsByUserId(String userId);
    List<String> getGroupMembers(String groupId); 
}
