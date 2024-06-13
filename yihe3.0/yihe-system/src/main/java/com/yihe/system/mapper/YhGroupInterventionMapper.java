package com.yihe.system.mapper;

import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhGroupInterventionMapper {
    int updategroupintervention(YHDocGroup docgrop);
    List<YhUserEnrolinfo> selectenroinfo(String isname);
    int updateYhUserEnrolinfo(YhUserEnrolinfo yhUserEnrolinfo);
    int deletegropvention(YHDocGroup docgrop);
    List<Yhgroupintervention> selectgroppage(YHDocGroup Yhdocgrop);
    List<Integer>  selectgroppagecount(YHDocGroup Yhdocgrop);
    List<YhUserEnrolinfo> needdelete(YHDocGroup Yhdocgrop);
    List<YhTemplateDetail> selectplanbyuser(YhTemplateDetail yhTemplateDetail);
    int selectcount(YhTemplateDetail yhTemplateDetail);
    int deletesplanbyid(YhTemplateDetail yhTemplateDetail);
    int updatelasttime(YhTemplateDetail yhTemplateDetail);
    List<YhProgramsDictionary> selectplancount();
    int jianfa(@Param("jianshu")Integer jianshu, @Param("usercode")String usercode);
}
