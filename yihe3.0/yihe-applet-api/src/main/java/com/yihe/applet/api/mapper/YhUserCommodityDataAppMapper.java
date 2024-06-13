package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserCommodityDataApp;

public interface YhUserCommodityDataAppMapper {
    int deleteByPrimaryKey(Integer merchandiseId);

    int insert(YhUserCommodityDataApp record);

    int insertSelective(YhUserCommodityDataApp record);

    YhUserCommodityDataApp selectByPrimaryKey(Integer merchandiseId);

    int updateByPrimaryKeySelective(YhUserCommodityDataApp record);

    int updateByPrimaryKeyWithBLOBs(YhUserCommodityDataApp record);

    int updateByPrimaryKey(YhUserCommodityDataApp record);
  
    List<YhUserCommodityDataApp> selectCommodityDataByParam(@Param("groupingId") Integer groupingId,@Param("variable") Integer variable,
    		@Param("userCode")String userCode,@Param("page") Integer page, @Param("pageSize") Integer pageSize);
}