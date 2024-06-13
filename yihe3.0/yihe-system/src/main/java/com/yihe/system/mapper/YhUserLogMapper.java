package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserLog;
import com.yihe.system.domain.YhUserLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhUserLogMapper {
    long countByExample(YhUserLogExample example);

    int deleteByExample(YhUserLogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(YhUserLog record);

    int insertSelective(YhUserLog record);

    List<YhUserLog> selectByExample(YhUserLogExample example);

    YhUserLog selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") YhUserLog record, @Param("example") YhUserLogExample example);

    int updateByExample(@Param("record") YhUserLog record, @Param("example") YhUserLogExample example);

    int updateByPrimaryKeySelective(YhUserLog record);

    int updateByPrimaryKey(YhUserLog record);
}