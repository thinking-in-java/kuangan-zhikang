package com.yihe.system.mapper;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Past;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserFile;
import com.yihe.system.domain.YhUserFolderWeb;

public interface YhUserFileMapper {
    int deleteByPrimaryKey(Long fileId);

    int insert(YhUserFile record);

    int insertSelective(YhUserFile record);

    YhUserFile selectByPrimaryKey(Long fileId);

    int updateByPrimaryKeySelective(YhUserFile record);

    int updateByPrimaryKey(YhUserFile record);
    
    List<Date> selectfilCreateDate(String userCode);
    
    List<YhUserFile> selectFileByParam(YhUserFile yhUserFile);
    
    YhUserFile selectFileName(@Param("fileName") String fileName,@Param("userCode") String userCode);
    List<YhUserFile> selectPageByParam(@Param("userCode") String userCode,@Param("fileParentFolder") String fileParentFolder);
}