/**
 * 
 */
package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhHealthHeadline;

/**
 * @author dong
 *
 */
public interface YhHealthHeadlineService {
    YhHealthHeadline selectByPrimaryKey(Integer id);
    
    List<YhHealthHeadline> selectByExample();
}
