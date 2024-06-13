package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhstopSmokingService {
    List<YhQuestionSmoking> selectallquetion();
    int insertqustionansers(SmokeAnswers smokeAnswers);
    SmokeAnswers selectanswers(SmokeAnswers smokeAnswers);
    Integer selecttoonce(String usercode);
    SmokeAnswers selectanwser(SmokeAnswers smokeAnswers);
    Integer insertaddsmoking(YhUserSmoking yhUserSmoking);
    Integer chanplan();
    YhUserSmoking selecthavesmoke(String usercode);
    List<YhGiveupSmokingDirectory> selectdirector();
    Integer changejindu(Integer alterid,Integer parentid,String usercode);
    List<Integer> selectprogrund(String id,String usercode);
    YhGiveupSmokingInfo selectarticle(String id);
    List<YhHealthLectureCommentApp> findCommentBylId(@Param("lid") Integer lid);
    List<YhHealthLectureCommentApp> findcommmbypage(@Param("lid") Integer lid,@Param("page") Integer page,@Param("usercode") String usercode);
    List<YhHealthLectureCommentApp> findcommmbypage2(@Param("lid") Integer lid,@Param("page") Integer page,@Param("usercode") String usercode);
    List<YhHealthLectureCommentApp> findCommentByPid(@Param("pid") Integer pid);
    int insertSelective(YhHealthLectureCommentApp record);
    YhHealthLectureCommentApp findCommentPuserCodeByParam(Integer pid);
    int adddianzan(String commentid,String usercode);
    int quxiaodianzan(String commentid,String usercode);
    String selectbeginday(String usercode);
    int stopsmokings(String usercode);
    YhSmokingReport selectyilai(String usercode);
    YhSmokeCraving  selectyanying(String usercode);
    Integer addyanying(String usercode);
    Integer[] selectyanyingDuary(String usercode);
    Integer[] selectyanyingDuarybymothh(String usercode);
    SmokeRecord selectSmokeRecord7(String usercode);
    SmokeRecord selectSmokeRecord12(String usercode);
    List<YhUserSmoking> selecytstopsmokinglog(String usercode);
    List<YhSmokingReport> selectsmokingreport(String usercode);
    int findSmokingCount();
}
