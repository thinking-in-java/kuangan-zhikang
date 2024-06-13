package com.yihe.system.mapper.layim;

import java.util.List;

import com.yihe.system.domain.layim.UserFriendGroup;

public interface UserFriendGroupMapper {
    List<UserFriendGroup> getUserIdsByGroupIds(List<String> groupIds); 
}
