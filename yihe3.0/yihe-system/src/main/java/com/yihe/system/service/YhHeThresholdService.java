package com.yihe.system.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yihe.system.domain.YhHeThreshold;
import com.yihe.system.domain.YhHealthSetting;


/**
* YhHeThresholdService接口
* Created by royiwen on 2020/3/27.
*/
public interface YhHeThresholdService  {
		List<YhHeThreshold> selectallheath(); 
		int updatasetting(YhHealthSetting yhhealthsetting);
		
		/**
		 * 检查异常借口
		 * @param type 
		 * @param userCode
		 * @param value 值
		 * @param typeName type名称
		 * @return
		 * @throws ParseException 
		 */
		YhHeThreshold selectBythresType(String type,String userCode,String value,String typeName,HttpServletRequest request) throws ParseException;
}