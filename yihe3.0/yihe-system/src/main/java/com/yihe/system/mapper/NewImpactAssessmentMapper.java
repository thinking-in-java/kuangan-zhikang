package com.yihe.system.mapper;


import com.yihe.system.domain.NewImpactAssessment;

import java.util.List;

public interface NewImpactAssessmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewImpactAssessment row);

    int insertSelective(NewImpactAssessment row);

    NewImpactAssessment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewImpactAssessment row);

    int updateByPrimaryKey(NewImpactAssessment row);

    List<NewImpactAssessment> selectByPage(NewImpactAssessment row);

    int selectByPageCount(NewImpactAssessment row);
}