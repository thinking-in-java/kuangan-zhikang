package com.yihe.system.mapper;

import com.yihe.system.domain.YhHealthLectureCategory;
import com.yihe.system.domain.YhHealthLectureCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhHealthLectureCategoryMapper {
    long countByExample(YhHealthLectureCategoryExample example);

    int deleteByExample(YhHealthLectureCategoryExample example);

    int deleteByPrimaryKey(Integer categoryid);

    int insert(YhHealthLectureCategory record);

    int insertSelective(YhHealthLectureCategory record);

    List<YhHealthLectureCategory> selectByExample(YhHealthLectureCategoryExample example);

    YhHealthLectureCategory selectByPrimaryKey(Integer categoryid);

    int updateByExampleSelective(@Param("record") YhHealthLectureCategory record, @Param("example") YhHealthLectureCategoryExample example);

    int updateByExample(@Param("record") YhHealthLectureCategory record, @Param("example") YhHealthLectureCategoryExample example);

    int updateByPrimaryKeySelective(YhHealthLectureCategory record);

    int updateByPrimaryKey(YhHealthLectureCategory record);
    
    List<YhHealthLectureCategory> showcategory();
    
    int deleteByparentId(String parentId);
    
    List<YhHealthLectureCategory> showcategoryparentId(String parentId);
    
    List<YhHealthLectureCategory> showpaid();
    
    List<YhHealthLectureCategory> showcategoryyiji();
}