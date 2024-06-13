package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserRolePermission;
import com.yihe.system.domain.YhUserRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhUserRolePermissionMapper {
    long countByExample(YhUserRolePermissionExample example);

    int deleteByExample(YhUserRolePermissionExample example);

    int deleteByPrimaryKey(Integer rolePermissionId);

    int insert(YhUserRolePermission record);

    int insertSelective(YhUserRolePermission record);

    List<YhUserRolePermission> selectByExample(YhUserRolePermissionExample example);

    YhUserRolePermission selectByPrimaryKey(Integer rolePermissionId);

    int updateByExampleSelective(@Param("record") YhUserRolePermission record, @Param("example") YhUserRolePermissionExample example);

    int updateByExample(@Param("record") YhUserRolePermission record, @Param("example") YhUserRolePermissionExample example);

    int updateByPrimaryKeySelective(YhUserRolePermission record);

    int updateByPrimaryKey(YhUserRolePermission record);
}