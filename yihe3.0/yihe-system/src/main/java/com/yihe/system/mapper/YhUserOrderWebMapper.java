package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserOrderWeb;

public interface YhUserOrderWebMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(YhUserOrderWeb record);

    int insertSelective(YhUserOrderWeb record);

    YhUserOrderWeb selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(YhUserOrderWeb record);

    int updateByPrimaryKey(YhUserOrderWeb record); 
    List<YhUserOrderWeb> selectAllYhUserOrderExport();
    List<YhUserOrderWeb> selectAllYhUserOrderWebPage(@Param("orgCode") String orgCode,@Param("merchandiseType") Integer merchandiseType,@Param("groupingId") Integer groupingId,
    		@Param("orderStatus") Integer orderStatus,
    		@Param("qtstrTime") String qtstrTime,@Param("qtendTime") String qtendTime,
    		@Param("merchandiseName") String merchandiseName,@Param("name") String name);
    
    List<YhUserOrderWeb> selectAllYhUserOrderWebPage2(@Param("orgCode") String orgCode,@Param("merchandiseType") Integer merchandiseType,@Param("groupingId") Integer groupingId,
    		@Param("orderStatus") Integer orderStatus,
    		@Param("qtstrTime") String qtstrTime,@Param("qtendTime") String qtendTime,
    		@Param("merchandiseName") String merchandiseName,@Param("name") String name);
}