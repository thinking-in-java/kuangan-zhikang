package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.DataExportVojo;
import com.yihe.system.domain.YhAssessConclusion;
import com.yihe.system.domain.YhAssessResult;
import com.yihe.system.domain.YhAssessResultVojo;

import java.util.List;
import java.util.Map;

public interface YhAssessResultService {
    String findByTs(String userCode, String assess);

    boolean submitAssess(String userCode, String assess, String answers);

    List<Map<String, Object>> findByUser(String userCode);

    YhAssessResult findByAssess(String userCode, String assess);

    YhAssessConclusion findByConcent(String state, String assess);

    String findScoreL(Integer scorel);

    int findMaxScore(String assess);

    List<YhAssessConclusion> findBydivisor(String assess);

    List<String> findConcents();

    String findName(Integer id);

    YhAssessResult findById(Integer id);

    PageInfo findByList(YhAssessResultVojo record);

    List<Map<String,Object>> findAssess();

    List<Map<String,Object>> findAssessAll();

    YhAssessResult findAssessInfo(String userCode, Integer assess);

    PageInfo findUserAssList(YhAssessResultVojo record);

    List<Map<String,Object>> findAssRes();

    List<YhAssessResult> selPsychologyAssess(DataExportVojo record);
}
