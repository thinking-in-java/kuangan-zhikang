package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.*;
import com.yihe.system.domain.YhTemplateDetail;
import com.yihe.system.domain.YhUserHealthyPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhstopSmokingMapper {
    List<YhQuestionSmoking> selectallquetion();
    int insertqustionansers(SmokeAnswers smokeAnswers);
    SmokeAnswers selectanswers(SmokeAnswers smokeAnswers);
    SmokeAnswers selectanwser(SmokeAnswers smokeAnswers);
    String selecttoonce(String usercode);
    Integer updateonce(String usercode);
    Integer insertaddsmoking(YhUserSmoking yhUserSmoking);
    List<YhTemplateDetail> detail();
    int insertserviceperson(YhTemplateDetail templateDetail);
    int selectiscount(YhTemplateDetail templateDetail);
    int inserttotalperson(YhTemplateDetail templateDetail);
    List<YhUserHealthyPlan> selectnewplan();
    String getplanmun(@Param("day") String day, @Param("planid") String planid);
    int insertplansbyuser(YhUserHealthyPlan yhUserHealthyPlan);
    YhUserSmoking selecthavesmoke(String usercode);
    Integer updatewatch(Integer alterid);
    Integer selectcount(@Param("alterid") Integer alterid,@Param("usercode") String usercode);
    Integer insertjilu(@Param("alterid") Integer alterid,@Param("parentid") Integer parentid,@Param("usercode") String usercode);
    List<YhGiveupSmokingDirectory> selectdirector();
    List<YhGiveupSmokingDirectory> selectsmokingcount(String id);
    Integer selectjindu(@Param("id")Integer id,@Param("usercode")String usercode);
    YhGiveupSmokingInfo selectarticle(String id);
    List<YhHealthLectureCommentApp> findCommentBylId(@Param("lid") Integer lid);
    List<YhHealthLectureCommentApp> findcommmbypage(@Param("lid") Integer lid,@Param("page") Integer page,@Param("usercode") String usercode);
    List<YhHealthLectureCommentApp> findcommmbypage2(@Param("lid") Integer lid,@Param("page") Integer page,@Param("usercode") String usercode);
    List<YhHealthLectureCommentApp> findCommentByPid(@Param("pid") Integer pid);
    int insertSelective(YhHealthLectureCommentApp record);
    YhHealthLectureCommentApp findCommentPuserCodeByParam(Integer pid);
    int adddianzan(@Param("commentid") String commentid,@Param("usercode") String usercode);
    int quxiaodianzan(@Param("commentid") String commentid,@Param("usercode") String usercode);
    int addall(@Param("commentid") String commentid);
    int quxiaodianzanall(@Param("commentid") String commentid);
    String selectbeginday(String usercode);
    int stopsmokings(String usercode);
    YhSmokingReport selectyilai(String usercode);
    YhSmokeCraving  selectyanying(String usercode);
    Integer addyanying(String usercode);
    List<Integer> selectsevenyanying(String usercode);
    SmokeRecord selectSmokeRecord7(String usercode);
    SmokeRecord selectSmokeRecord12(String usercode);
    List<Integer> selectyanyingDuarybymothh(String usercode);
    List<YhUserSmoking> selecytstopsmokinglog(String usercode);
    List<YhSmokingReport> selectsmokingreport(String usercode);
    int findSmokingCount();
}
