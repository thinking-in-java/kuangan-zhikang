package com.yihe.applet.api.mapper;

import java.util.Date;
import java.util.List;

import com.yihe.applet.api.domain.YhUserFileApp;
import org.apache.ibatis.annotations.Param;

public interface YhUserFileAppMapper {
    int deleteByPrimaryKey(Long fileId);

    int insert(YhUserFileApp record);

    int insertSelective(YhUserFileApp record);

    YhUserFileApp selectByPrimaryKey(Long fileId);

    int updateByPrimaryKeySelective(YhUserFileApp record);

    int updateByPrimaryKey(YhUserFileApp record);
    
    List<YhUserFileApp> selectFileByParam(YhUserFileApp yhUserFile);
    
    int delete(Integer fileId);
    
    List<Date> selectfilCreateDate(String userCode);
    
    List<YhUserFileApp> selectUserFile(@Param("page") Integer page, @Param("pageSize") Integer pageSize,@Param("fileParentFolder") String fileParentFolder,@Param("userCode") String userCode);
    
     List<YhUserFileApp> selectUserFileByParam(@Param("page") Integer page, @Param("pageSize") Integer pageSize,@Param("fileParentFolder") String fileParentFolder,
     		@Param("userCode") String userCode,@Param("fileName") String fileName);
}