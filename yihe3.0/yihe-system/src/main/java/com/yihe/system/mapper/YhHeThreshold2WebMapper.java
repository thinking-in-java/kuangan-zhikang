package com.yihe.system.mapper;

import com.yihe.system.domain.YhHeThreshold2Web;
 
public interface YhHeThreshold2WebMapper {
    int deleteByPrimaryKey(String userCode);

    int insert(YhHeThreshold2Web record);

    int insertSelective(YhHeThreshold2Web record);

    YhHeThreshold2Web selectByPrimaryKey(String id);
    YhHeThreshold2Web selectUserYhHeThreshold2(String userCode);

    int updateByPrimaryKeySelective(YhHeThreshold2Web record);

    int updateByPrimaryKey(YhHeThreshold2Web record);
}