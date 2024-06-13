package com.yihe.applet.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhdvdevicetypeApp;
public interface YhdvdevicetypeAppService {
	List<YhdvdevicetypeApp> findDeviceTypeListInfo();
}
