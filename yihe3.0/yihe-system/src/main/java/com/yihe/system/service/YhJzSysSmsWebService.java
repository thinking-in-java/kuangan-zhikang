/**
 * 
 */
package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhJzSysSmsWeb;
import com.yihe.system.domain.YhJzSysSmsWebVojo;
import com.yihe.system.domain.YhWxInformation;
import com.yihe.system.domain.YhWxInformationVojo;

/**
 * @author dong
 *
 */
public interface YhJzSysSmsWebService {
	
	/**
	 * 查询不等于空的短信配置
	 * @return
	 */
    YhJzSysSmsWebVojo selectSmsNull();
    /**
     	* 查询物流模板
     * @return
     */
    YhJzSysSmsWeb selectYhJzSysSmslogistics();
    
    /**
     * 修改删除短信模板
     * @param record
     * @return
     */
    int insertSelective(YhJzSysSmsWebVojo record);
    
    /**
     * 查询微信消息模板
     * @return
     */
    YhWxInformationVojo selectbyWxINfo();
    
    /**
     * 修改微信模板
     * @param record
     * @return
     */
    int insertSelectiveWxInfo(YhWxInformationVojo record);
}
