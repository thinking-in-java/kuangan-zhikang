package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.IntegralShoppingGrouping;

public interface IntegralShoppingGroupingMapper {
    int deleteByPrimaryKey(Integer groupingId);

    int insert(IntegralShoppingGrouping record);

    int insertSelective(IntegralShoppingGrouping record);

    IntegralShoppingGrouping selectByPrimaryKey(Integer groupingId);

    int updateByPrimaryKeySelective(IntegralShoppingGrouping record);
    List<IntegralShoppingGrouping> selectGrouping(@Param("name") String name);
    int updateByPrimaryKey(IntegralShoppingGrouping record);
    IntegralShoppingGrouping selectGroupingName(@Param("name") String name);
    List<IntegralShoppingGrouping> selectAllGrouping(@Param("orgCode") String orgCode);
    List<IntegralShoppingGrouping> queryShoppingGroupingPage(@Param("name") String name,@Param("orgCode") String orgCode);
}