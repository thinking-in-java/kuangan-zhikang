package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.IntegralShoppingGroupingApp;

public interface IntegralShoppingGroupingAppMapper {
    int deleteByPrimaryKey(Integer groupingId);

    int insert(IntegralShoppingGroupingApp record);

    int insertSelective(IntegralShoppingGroupingApp record);

    IntegralShoppingGroupingApp selectByPrimaryKey(Integer groupingId);

    int updateByPrimaryKeySelective(IntegralShoppingGroupingApp record);

    int updateByPrimaryKey(IntegralShoppingGroupingApp record);
    List<IntegralShoppingGroupingApp> selectAllGrouping(@Param("orgCode") String orgCode);
}