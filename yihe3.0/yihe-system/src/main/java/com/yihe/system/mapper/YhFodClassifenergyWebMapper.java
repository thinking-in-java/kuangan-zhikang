package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhFodClassifenergyWeb;

public interface YhFodClassifenergyWebMapper {
	String findZaoFenInfo(Integer flevel);
	String findWuFenInfo(Integer flevel);
	String findWanFenInfo(Integer flevel);
	String findJiaFenInfo(Integer flevel);
	
	
	YhFodClassifenergyWeb findFenergyByFlevel(String flevel);
	
	List<String> findFenergyListAll();
	
    int deleteByPrimaryKey(Integer fClassifenergyid);

    int insert(YhFodClassifenergyWeb record);

    int insertSelective(YhFodClassifenergyWeb record);

    YhFodClassifenergyWeb selectByPrimaryKey(Integer fClassifenergyid);

    int updateByPrimaryKeySelective(YhFodClassifenergyWeb record);

    int updateByPrimaryKey(YhFodClassifenergyWeb record);
}