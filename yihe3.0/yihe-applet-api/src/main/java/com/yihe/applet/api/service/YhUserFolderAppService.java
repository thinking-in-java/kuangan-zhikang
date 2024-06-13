package com.yihe.applet.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserFolderApp;

public interface YhUserFolderAppService {
	 int deleteByPrimaryKey(Long folderId);

	    int insertSelective(YhUserFolderApp record);

	    YhUserFolderApp selectByPrimaryKey(Long folderId);

	    int updateByPrimaryKeySelective(YhUserFolderApp record);
	    
	    List<YhUserFolderApp> selectYhUserFolderLimitParent( String userCode,Integer page,  Integer pageSize);
	    List<YhUserFolderApp> selectYhUserFolderByparam( String userCode,String folderName,Integer page,Integer pageSize);

}
