package com.yihe.system.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.mapper.YhSysConfigMapper;
import com.yihe.system.service.SysConfigDas;
import com.yihe.system.utils.RRException;

@Service
public class SysConfigDasImpl implements SysConfigDas {
	@Autowired
	private YhSysConfigMapper sysConfigDao;

	public String getValue(String key) {
		YhSysConfig config = null;
		if(key != null){
			config = sysConfigDao.queryByKey(key);
		}

		return config == null ? null : config.getValue();
	}
	
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
