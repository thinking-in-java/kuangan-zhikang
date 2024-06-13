package com.yihe.applet.api.service;

import java.util.Date;
import java.util.List;

import com.yihe.applet.api.domain.YhUserFileApp;

public interface YhUserFileAppService {
	
    int insertSelective(YhUserFileApp record);

    YhUserFileApp selectByPrimaryKey(Long fileId);

    int updateByPrimaryKeySelective(YhUserFileApp record);
    
    List<YhUserFileApp> selectFileByParam(YhUserFileApp yhUserFile);
    
    int delete(Integer fileId);
    
    List<Date> selectfilCreateDate(String userCode);
    List<YhUserFileApp> selectUserFile(Integer page,Integer pageSize,String fileParentFolder,String userCode);
    List<YhUserFileApp> selectUserFileByParam(Integer page, Integer pageSize,String fileParentFolder,
    		 String userCode, String fileName);
}
