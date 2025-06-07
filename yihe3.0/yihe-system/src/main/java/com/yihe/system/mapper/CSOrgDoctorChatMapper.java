package com.yihe.system.mapper;

import com.yihe.system.domain.CSOrgDoctorChat;
import org.apache.ibatis.annotations.Param;

public interface CSOrgDoctorChatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CSOrgDoctorChat record);

    int insertSelective(CSOrgDoctorChat record);

    CSOrgDoctorChat selectByPrimaryKey(Integer id);

    CSOrgDoctorChat selectListByOrgAndType(@Param("orgId") String orgId, @Param("type") Integer type);

    int deleteByOrgAndType(@Param("orgId") String orgId,@Param("type") Integer type);


    int updateByPrimaryKeySelective(CSOrgDoctorChat record);

    int updateByPrimaryKey(CSOrgDoctorChat record);
}