package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserFolderWeb;
 
public interface YhUserFolderWebMapper {
    int deleteByPrimaryKey(Long folderId);

    int insert(YhUserFolderWeb record);

    int insertSelective(YhUserFolderWeb record);

    YhUserFolderWeb selectByPrimaryKey(Long folderId);

    int updateByPrimaryKeySelective(YhUserFolderWeb record);

    int updateByPrimaryKey(YhUserFolderWeb record);
    
    List<YhUserFolderWeb> selectPageByParam(String userCode);
    
    YhUserFolderWeb selectFolderName(@Param("userCode") String userCode,@Param("folderName") String folderName);
}