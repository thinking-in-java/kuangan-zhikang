package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface YhUserPermissionMapper {
    long countByExample(YhUserPermissionExample example);

    int deleteByExample(YhUserPermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(YhUserPermission record);

    int insertSelective(YhUserPermission record);

    List<YhUserPermission> selectByExample(YhUserPermissionExample example);

    YhUserPermission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") YhUserPermission record, @Param("example") YhUserPermissionExample example);

    int updateByExample(@Param("record") YhUserPermission record, @Param("example") YhUserPermissionExample example);

    int updateByPrimaryKeySelective(YhUserPermission record);

    int updateByPrimaryKey(YhUserPermission record);
    
    int deleteuserrolepermissionbypid(Integer permissionId);

    List<YhUserPermission> selectpermissions(@Param("id") Integer id, @Param("Usercode") String Usercode);
}