package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonagePhysical;

public interface YhUserPersonagePhysicalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonagePhysical record);

    int insertSelective(YhUserPersonagePhysical record);

    YhUserPersonagePhysical selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonagePhysical record);

    int updateByPrimaryKey(YhUserPersonagePhysical yhUserPersonagePhysical);
}