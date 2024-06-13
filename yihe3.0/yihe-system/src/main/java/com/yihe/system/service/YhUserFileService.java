package com.yihe.system.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserFile;
import com.yihe.system.domain.YhUserFolderWeb;

public interface YhUserFileService {
	 List<Date> selectfilCreateDate(String userCode);
	 public PageInfo<YhUserFile> queryUserByCriteriaWithPage(String userCode,String fileParentFolder,int page,int limit);
	    List<YhUserFile> selectFileByParam(YhUserFile yhUserFile);
		int deleteByPrimaryKey(Long fileId);
		 YhUserFile selectFileName(String fileName, String userCode);
		 int updateByPrimaryKeySelective(YhUserFile record);
		 int insertSelective(YhUserFile record);
		 YhUserFile selectByPrimaryKey(Long fileId);

}
