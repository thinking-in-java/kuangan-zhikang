package com.yihe.system.mapper.layim;

import java.util.List;

import com.yihe.system.domain.layim.MsgRecord;

public interface MsgRecordMapper {
    void addRecord(MsgRecord record);

    List<MsgRecord> getList(String roomId,Integer lastId); 
}
