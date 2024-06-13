package com.yihe.system.mapper.layim;


import java.util.List;

import com.yihe.system.domain.layim.FriendGroup;

public interface FriendGroupMapper {
    List<FriendGroup> getGroupsByUserId(String userId); 
}
