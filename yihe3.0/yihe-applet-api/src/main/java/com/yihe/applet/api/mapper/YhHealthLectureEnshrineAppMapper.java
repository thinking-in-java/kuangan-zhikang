package com.yihe.applet.api.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhHealthLectureEnshrineApp;
 
public interface YhHealthLectureEnshrineAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhHealthLectureEnshrineApp record);

    int insertSelective(YhHealthLectureEnshrineApp record);

    YhHealthLectureEnshrineApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhHealthLectureEnshrineApp record);

    int updateByPrimaryKey(YhHealthLectureEnshrineApp record);
    
    YhHealthLectureEnshrineApp selectWhetherEnshrineByParam(@Param("userCode") String userCode,@Param("categoryid") Integer categoryid);
    int deleteWhetherEnshrineByParam(@Param("userCode") String userCode,@Param("categoryid") Integer categoryid);
}