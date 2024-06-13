package com.yihe.system.service;

import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.domain.YhProgramsDictionary;
import com.yihe.system.domain.YhTemplateDetail;
import com.yihe.system.domain.Yhgroupintervention;

import java.util.List;

public interface YhGroupInterventionService {
    int updategroupintervention(YHDocGroup docgrop);
    int deletegroupintervention(YHDocGroup docgrop);
    List<Yhgroupintervention> selectgroppage(YHDocGroup Yhdocgrop);
    int deletegroup(YHDocGroup docgrop);
    int updategroup(YHDocGroup docgrop);
    int selectgroppagecount(YHDocGroup Yhdocgrop);
    List<YhTemplateDetail> selectplanbyuser(YhTemplateDetail yhTemplateDetail);
    int selectcount(YhTemplateDetail yhTemplateDetail);
    int deletesplanbyid(YhTemplateDetail yhTemplateDetail);
    List<YhProgramsDictionary> selectplancount();
}
