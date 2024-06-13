package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.TestingVOJO;
import com.yihe.system.domain.YHUserTemperature;

public interface YHUserTemperatureService {
	int deleteByPrimaryKey(Integer tid);

    int insert(YHUserTemperature record);

    int insertSelective(YHUserTemperature record);

    YHUserTemperature selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(YHUserTemperature record);

    int updateByPrimaryKey(YHUserTemperature record);
    
    YHUserTemperature selectByPrimaryKeyByUsercodeLimit(String usercode);
    
    List<YHUserTemperature> selectByPrimaryKeyForInfoNum(String usercode,String nameLike,String username,String endTime,String strTime);
    

    PageInfo<TestingVOJO> selectByPrimaryKeyForInfoName(String usercode,String nameLike,String usernameLike,String endTime,String strTime,String TypeName,int pageNum,int pageSize);
    PageInfo<TestingVOJO> selectByPrimaryKeyForInfoNameTiwen(String usercode,String nameLike,String usernameLike,String endTime,String strTime,String TypeName,int pageNum,int pageSize);
    
    List<TestingVOJO> selectByPrimaryKeyForInfoName2(String usercode,String nameLike,String usernameLike,String endTime,String strTime,String TypeName,int pageNum,int pageSize);
    
    Map<String, Object> selectUserAnimalHeat (String userCode);
    
    public PageInfo<YHUserTemperature> queryUserByCriteriaWithPage(String userCode,int page,int limit);
    
    PageInfo<YHUserTemperature> selectTemperature(String userCode,int page,int limit);
}
