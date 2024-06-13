/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhAuditCheck;

/**
 * 审核
 * @author dong
 *
 */
public interface YhAuditCheckService {
	
	/**
	 * 查询所有审核
	 * @param map
	 * @return
	 */
	List<YhAuditCheck> selectaudit(Map<String, Object> map);
    
	/**
	 * 查询所有审核总行数
	 * @param map
	 * @return
	 */
    int selectauditConut(Map<String, Object> map);
    
    int updateByPrimaryKeySelective(YhAuditCheck record);
    
    /**
     * 查询膳食新增申请
     * @return
     */
    int showfoodcount();
    /**
     *  查询膳食申请待处理
     * @return
     */
    int showfoodcountDispose();

    /**
     * 查询运动新增申请
     * @return
     */
    int showExercisecount();
    /**
     *  查询运动申请待处理
     * @return
     */
    int showExercisecountDispose();
}
