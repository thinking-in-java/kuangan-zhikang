/**
 * 
 */
package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhHomeinfoConfig;

/**
 * @author dong
 *
 */
public interface YhHomeinfoConfigService {

	/**
	 * 查询轮播图 
	 * @return
	 */
	List<YhHomeinfoConfig> selectByslideshow();
    
	/**
	 * 查询轮播图总行数 
	 * @return
	 */
    int selectByslideshownumber();
    
    /**
     *  查询健康头条
     * @return
     */
    List<YhHomeinfoConfig> selectByheadline();
    
    /***
     * 查询健康头条总行数
     * @return
     */
    int selectByheadlinenumber();
    
    int insertSelective(YhHomeinfoConfig record);
    
    int updateByPrimaryKeySelective(YhHomeinfoConfig record);
    
    int deleteByPrimaryKey(Integer id);
    
    YhHomeinfoConfig amendinfotip(int infoTip);
    
    String InfoTipMax();
    
    List<YhHomeinfoConfig> selectByOrder();
}
