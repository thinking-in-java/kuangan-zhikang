package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserCommodityData;

public interface YhUserCommodityDataMapper {
    int deleteByPrimaryKey(Integer merchandiseId);

    int insert(YhUserCommodityData record);

    int insertSelective(YhUserCommodityData record);

    YhUserCommodityData selectByPrimaryKey(Integer merchandiseId);
     int batchUpdateYhUserCommodityData(Map<String,Object> map);
    int updateByPrimaryKeySelective(YhUserCommodityData record);
    int batchDeleteYhUserCommodityData(Map<String,Object> map);
    int updateByPrimaryKeyWithBLOBs(YhUserCommodityData record);
    List<YhUserCommodityData> selectAll();
    int updateByPrimaryKey(YhUserCommodityData record);
    List<YhUserCommodityData> selectAllCommodityData(@Param("merchandiseType") Integer merchandiseType,@Param("groupingId") Integer groupingId,
    		@Param("putaway") Integer putaway,@Param("conversion") Integer conversion,@Param("merchandiseName") String merchandiseName,@Param("orgCode") String orgCode);
}