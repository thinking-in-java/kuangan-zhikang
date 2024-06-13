package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserRoleRelation;
import com.yihe.system.domain.YhUserRoleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhUserRoleRelationMapper {
    long countByExample(YhUserRoleRelationExample example);

    int deleteByExample(YhUserRoleRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YhUserRoleRelation record);

    int insertSelective(YhUserRoleRelation record);

    List<YhUserRoleRelation> selectByExample(YhUserRoleRelationExample example);

    YhUserRoleRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YhUserRoleRelation record, @Param("example") YhUserRoleRelationExample example);

    int updateByExample(@Param("record") YhUserRoleRelation record, @Param("example") YhUserRoleRelationExample example);

    int updateByPrimaryKeySelective(YhUserRoleRelation record);

    int updateByPrimaryKey(YhUserRoleRelation record);
}