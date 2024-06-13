package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserFolderApp;

public interface YhUserFolderAppMapper {
    int deleteByPrimaryKey(Long folderId);

    int insert(YhUserFolderApp record);

    int insertSelective(YhUserFolderApp record);

    YhUserFolderApp selectByPrimaryKey(Long folderId);

    int updateByPrimaryKeySelective(YhUserFolderApp record);

    int updateByPrimaryKey(YhUserFolderApp record);
    
    List<YhUserFolderApp> selectYhUserFolderLimitParent(@Param("userCode") String userCode,
		@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    List<YhUserFolderApp> selectYhUserFolderByparam(@Param("userCode") String userCode,@Param("folderName") String folderName,
    		@Param("page") Integer page, @Param("pageSize") Integer pageSize);
}