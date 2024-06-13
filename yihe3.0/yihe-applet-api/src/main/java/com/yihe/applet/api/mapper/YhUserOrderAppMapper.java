package com.yihe.applet.api.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserOrderApp;

public interface YhUserOrderAppMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(YhUserOrderApp record);

    int insertSelective(YhUserOrderApp record);

    YhUserOrderApp selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(YhUserOrderApp record);

    int updateByPrimaryKey(YhUserOrderApp record);
    
    List<YhUserOrderApp> selectUserAllOrderbyParam(@Param("page") Integer page, @Param("pageSize") Integer pageSize,
    		@Param("month") Integer month,@Param("type") Integer type,@Param("userCode") String userCode);
}