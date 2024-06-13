package com.yihe.system.mapper;

import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.domain.YhSysConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhSysConfigMapper {
    long countByExample(YhSysConfigExample example);

    int deleteByExample(YhSysConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YhSysConfig record);

    int insertSelective(YhSysConfig record);

    List<YhSysConfig> selectByExample(YhSysConfigExample example);

    YhSysConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") YhSysConfig record, @Param("example") YhSysConfigExample example);

    int updateByExample(@Param("record") YhSysConfig record, @Param("example") YhSysConfigExample example);

    int updateByPrimaryKeySelective(YhSysConfig record);

    int updateByPrimaryKey(YhSysConfig record);

    YhSysConfig showConfig();

	/** 
	 * 根据key，查询value
	 */
    YhSysConfig queryByKey(String paramKey);
}