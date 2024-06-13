package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhUserPersonagePharmacy;

public interface YhUserPersonagePharmacyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonagePharmacy yhUserPersonagePharmacy);

    int insertSelective(YhUserPersonagePharmacy record);

    List<YhUserPersonagePharmacy> selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonagePharmacy record);

    int updateByPrimaryKey(YhUserPersonagePharmacy yhUserPersonagePharmacy);
}