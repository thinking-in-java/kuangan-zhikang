package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhHealthLectureCommentApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhHealthLectureCommentAppService {
    List<YhHealthLectureCommentApp> findCommentBylId1(Map<String, Object> map);

    List<YhHealthLectureCommentApp> findCommentBylId(@Param("lid") Integer lid, @Param("status") Integer status);

    List<YhHealthLectureCommentApp> findCommentByPid(@Param("pid") Integer pid);

    YhHealthLectureCommentApp selecTcontAndName(Integer commentid);

    List<YhHealthLectureCommentApp> selectByPrimaryKeyByPid(Integer pid);

    List<YhHealthLectureCommentApp> selectAllparam(Integer categoryId);

    int insertSelective(YhHealthLectureCommentApp record);

    YhHealthLectureCommentApp ifclock(YhHealthLectureCommentApp record);

    int updClock(YhHealthLectureCommentApp record);

    YhHealthLectureCommentApp findCommentPuserCodeByParam(Integer pid);

    List<YhHealthLectureCommentApp> findCommentByPid2(Integer pid);

    List<YhHealthLectureCommentApp> findCommentByPid1(Integer pid);

	int updateSelective(YhHealthLectureCommentApp record);

    Boolean updateTotalDay(@Param("userCode") String userCode,@Param("habitId") Integer habitId);
}
