package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.domain.YhUserRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhUserRoleMapper {
    long countByExample(YhUserRoleExample example);

    int deleteByExample(YhUserRoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(YhUserRole record);

    int insertSelective(YhUserRole record);

    List<YhUserRole> selectByExample(YhUserRoleExample example);

    String selectRoleName(@Param("userCode")String userCode);

    YhUserRole selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") YhUserRole record, @Param("example") YhUserRoleExample example);

    int updateByExample(@Param("record") YhUserRole record, @Param("example") YhUserRoleExample example);

    int updateByPrimaryKeySelective(YhUserRole record);

    int updateByPrimaryKey(YhUserRole record);
    
    Integer selectRoleId(String roleName);

    Integer selectcountbyname(String name);

    YhUserMakeCard selectmakecard(Integer num);

    int updatemakecard(YhUserMakeCard cards);

    List<String> listimg();
}