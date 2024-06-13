package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhHealthLectureComment;

public interface YhHealthLectureCommentMapper {
    long countByExample(YhHealthLectureComment example);

    int deleteByExample(YhHealthLectureComment example);

    int deleteByPrimaryKey(Integer commentid);

    int insert(YhHealthLectureComment record);
    
    int findCommentByUserCodeForCount(@Param("usercode")String usercode);

    int insertSelective(YhHealthLectureComment record);

    List<YhHealthLectureComment> selectByExampleWithBLOBs(YhHealthLectureComment example);

    List<YhHealthLectureComment> selectByExample(YhHealthLectureComment example);

    YhHealthLectureComment selectByPrimaryKey(Integer commentid);

    int updateByExampleSelective(@Param("record") YhHealthLectureComment record, @Param("example") YhHealthLectureComment example);

    int updateByExampleWithBLOBs(@Param("record") YhHealthLectureComment record, @Param("example") YhHealthLectureComment example);

    int updateByExample(@Param("record") YhHealthLectureComment record, @Param("example") YhHealthLectureComment example);

    int updateByPrimaryKeySelective(YhHealthLectureComment record);

    int updateByPrimaryKeyWithBLOBs(YhHealthLectureComment record);

    int updateByPrimaryKey(YhHealthLectureComment record);
    
    int selectByCount(int lectureinfoid);
    
    List<YhHealthLectureComment> selectComment(Map<String, Object> map);
    
    List<YhHealthLectureComment> showHabitExport(Map<String, Object> map);
    
    
    int selectCommentConut(Map<String, Object> map);
    
    List<YhHealthLectureComment> selectsecondComment(Map<String, Object> map);
    
    int selectsecondCommentConut(Map<String, Object> map);
    
    int selectnumber(int pid);
    
    int deleteByPrimaryKeyPid(int pid);
    
    int deleteLectureCode(Map<String, Object> map);
}