package com.yihe.system.service;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserFolderWeb;

public interface YhUserFolderWebService {
	 int deleteByPrimaryKey(Long folderId);
	 int insertSelective(YhUserFolderWeb record);
	 YhUserFolderWeb selectByPrimaryKey(Long folderId);
	 int updateByPrimaryKeySelective(YhUserFolderWeb record);
	 public PageInfo<YhUserFolderWeb> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	 YhUserFolderWeb selectFolderName(String userCode,String folderName);
}
