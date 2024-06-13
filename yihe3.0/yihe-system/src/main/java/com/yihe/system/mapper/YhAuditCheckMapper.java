package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhAuditCheck;

public interface YhAuditCheckMapper {
    int deleteByPrimaryKey(Integer checkid);

    int insert(YhAuditCheck record);

    int insertSelective(YhAuditCheck record);

    YhAuditCheck selectByPrimaryKey(Integer checkid);

    int updateByPrimaryKeySelective(YhAuditCheck record);

    int updateByPrimaryKey(YhAuditCheck record);
    
    List<YhAuditCheck> selectaudit(Map<String, Object> map);
    
    int selectauditConut(Map<String, Object> map);
    
    int showfoodcount();
    
    int showfoodcountDispose();
    
    int showExercisecount();
    
    int showExercisecountDispose();
}