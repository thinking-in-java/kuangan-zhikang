package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhHealthLectureCommentApp;
import com.yihe.applet.api.domain.YhLifeHabitPersonDictApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhHealthLectureCommentAppMapper {
    int deleteByPrimaryKey(Integer commentid);

    int insert(YhHealthLectureCommentApp record);

    int insertSelective(YhHealthLectureCommentApp record);

    YhHealthLectureCommentApp ifclock(YhHealthLectureCommentApp record);

    int updClock(YhHealthLectureCommentApp record);

    YhHealthLectureCommentApp selectByPrimaryKey(Integer commentid);

    int updateByPrimaryKeySelective(YhHealthLectureCommentApp record);

    int updateByPrimaryKeyWithBLOBs(YhHealthLectureCommentApp record);

    int updateByPrimaryKey(YhHealthLectureCommentApp record);

    List<YhHealthLectureCommentApp> findCommentBylId1(Map<String, Object> map);

    List<YhHealthLectureCommentApp> findCommentBylId(@Param("lid") Integer lid,@Param("status") Integer status);


	List<YhHealthLectureCommentApp> findCommentByPid(@Param("pid") Integer pid);
	List<YhHealthLectureCommentApp> findCommentByPid2(@Param("pid") Integer pid);
	List<YhHealthLectureCommentApp> selectByPrimaryKeyByPid(Integer pid);
	List<YhHealthLectureCommentApp> selectAllparam(Integer categoryId);
	YhHealthLectureCommentApp findCommentPuserCodeByParam(Integer pid);
	YhHealthLectureCommentApp selecTcontAndName (Integer commentid);
    List<YhHealthLectureCommentApp> findCommentByPid1(@Param("pid") Integer pid);

	int updateSelective(YhHealthLectureCommentApp record);

    YhLifeHabitPersonDictApp selectTotalDay(@Param("userCode") String userCode, @Param("habitId") Integer habitId);
    int updateTotalDay(@Param("userCode") String userCode, @Param("habitId") Integer habitId,@Param("totalDay") Integer totalDay);

    int selectCurrentDay(@Param("userCode") String userCode, @Param("habitId") Integer habitId);
}
