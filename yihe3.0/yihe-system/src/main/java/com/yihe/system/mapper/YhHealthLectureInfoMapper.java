package com.yihe.system.mapper;

import com.yihe.system.domain.YhHealthLectureInfo;
import com.yihe.system.domain.YhHealthLectureInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface YhHealthLectureInfoMapper {
    long countByExample(YhHealthLectureInfoExample example);

    int deleteByExample(YhHealthLectureInfoExample example);

    int deleteByPrimaryKey(Integer infoid);

    int insert(YhHealthLectureInfo record);

    int insertSelective(YhHealthLectureInfo record);

    List<YhHealthLectureInfo> selectByExample();

    YhHealthLectureInfo selectByPrimaryKey(Integer infoid);

    int updateByExampleSelective(@Param("record") YhHealthLectureInfo record, @Param("example") YhHealthLectureInfoExample example);

    int updateByExample(@Param("record") YhHealthLectureInfo record, @Param("example") YhHealthLectureInfoExample example);

    int updateByPrimaryKeySelective(YhHealthLectureInfo record);

    int updateByPrimaryKey(YhHealthLectureInfo record);
    
    int selectBylectureCount(Integer categoryId);
    
  List<YhHealthLectureInfo> selectLectureInfo(Map<String, Object> map);
    
    int selectLectureInfoConut(Map<String, Object> map);
    
    int selctTitleRepetition(Map<String, Object> map);
}