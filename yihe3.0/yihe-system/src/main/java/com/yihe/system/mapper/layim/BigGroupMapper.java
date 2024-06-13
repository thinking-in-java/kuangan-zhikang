package com.yihe.system.mapper.layim;



import java.util.List;

import com.yihe.system.domain.layim.BigGroup;

public interface BigGroupMapper {
    List<BigGroup>getGroupsByGroupIds(List<String> groupIds);
    List<BigGroup>getGroupsByDoctor(String usercode);
    String getGroupOwnerId(String groupId); 
}
