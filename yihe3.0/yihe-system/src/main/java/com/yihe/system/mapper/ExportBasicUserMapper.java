package com.yihe.system.mapper;

import com.yihe.system.domain.AssessmentOfTotalTable;
import com.yihe.system.domain.BasicUser;
import com.yihe.system.domain.YhFoodUserDiary;
import com.yihe.system.domain.YhSportDiary;

import java.util.List;

public interface ExportBasicUserMapper {
    List<AssessmentOfTotalTable> jiankangfengxiandatanouser(BasicUser basicUser);
    List<AssessmentOfTotalTable> chagjianzhengzhuang(BasicUser basicUser);
    List<AssessmentOfTotalTable> axingdata(BasicUser basicUser);
    List<AssessmentOfTotalTable> pstridata(BasicUser basicUser);
    List<AssessmentOfTotalTable> xinlijiankangdata(BasicUser basicUser);
    List<AssessmentOfTotalTable> shuimiandata(BasicUser basicUser);
    List<AssessmentOfTotalTable> jiankangxiguandata(BasicUser basicUser);
    List<AssessmentOfTotalTable> tiwen(BasicUser basicUser);
    List<AssessmentOfTotalTable> tizhong(BasicUser basicUser);
    List<AssessmentOfTotalTable> xueya(BasicUser basicUser);
    List<AssessmentOfTotalTable> xuetang(BasicUser basicUser);
    List<AssessmentOfTotalTable> xueyang(BasicUser basicUser);
    List<AssessmentOfTotalTable> xinlv(BasicUser basicUser);
    List<AssessmentOfTotalTable> tizhi(BasicUser basicUser);
    List<AssessmentOfTotalTable> xuezhisixiang(BasicUser basicUser);
    List<AssessmentOfTotalTable> shengongneng(BasicUser basicUser);
    List<AssessmentOfTotalTable> gangongneng(BasicUser basicUser);
    List<AssessmentOfTotalTable> xindaintu(BasicUser basicUser);
    List<AssessmentOfTotalTable> niaojian(BasicUser basicUser);
    List<AssessmentOfTotalTable> qitazhibiao(BasicUser basicUser);
    List<YhFoodUserDiary> yinshiriji(BasicUser basicUser);
    List<YhSportDiary> selectYhpory(BasicUser basicUser);
    List<AssessmentOfTotalTable> shuimian(BasicUser basicUser);
    List<AssessmentOfTotalTable> gaoxueyariji(BasicUser basicUser);
    List<AssessmentOfTotalTable> gaoxuetangriji(BasicUser basicUser);
    List<AssessmentOfTotalTable> gaoxuezhiriji(BasicUser basicUser);
    List<AssessmentOfTotalTable> gaoniansuanriji(BasicUser basicUser);
    List<AssessmentOfTotalTable> jianzhiriji(BasicUser basicUser);
    List<AssessmentOfTotalTable> jiankangxiguanriji(BasicUser basicUser);
    String chazhaoshijian(Integer options);
}
