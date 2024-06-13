package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageSymptomApp;

/**
* YhUserPersonageSymptomService接口
* Created by dong on 2020/2/12.
*/
public interface YhUserPersonageSymptomAppService {

	/**
	 * 根据code查询症状
	 * @param usercode
	 * @return
	 */
	YhUserPersonageSymptomApp showsymptom(String usercode);
	 int insertSelective(YhUserPersonageSymptomApp record);
	 int updateByPrimaryKeySelective(YhUserPersonageSymptomApp record);
	
	
}