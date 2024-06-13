package com.yihe.system.service;


import java.util.List;

import com.yihe.system.domain.layim.JsonResult;
import com.yihe.system.domain.layim.MsgRecord;
import com.yihe.system.domain.layim.User;

public interface LayIMService {
    User getByUserId(String userId); 

    JsonResult getInitResult(String userId);

    JsonResult getMembersByGroupId(String groupId);

    List<String> getGroupIds(String userId);

    JsonResult addRecord(MsgRecord record);

    JsonResult getChatRecords(String userId,String id,Integer type,Integer lastId);
}
