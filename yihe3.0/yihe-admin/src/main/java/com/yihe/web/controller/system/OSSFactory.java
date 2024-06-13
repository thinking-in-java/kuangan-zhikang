package com.yihe.web.controller.system;

import com.yihe.system.domain.CloudStorageConfig;
import com.yihe.system.domain.CloudStorageService;
import com.yihe.system.domain.ConfigConstant;
import com.yihe.system.service.SysConfigDas;
import com.yihe.system.utils.SpringContextUtil;

/**
 * 文件上传Factory
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 10:18
 */
public final class OSSFactory {
	 private static SysConfigDas sysConfigService;

	    static {
	        OSSFactory.sysConfigService = (SysConfigDas) SpringContextUtil.getBean("sysConfigDas");
	    }
	 
	    public static CloudStorageService build(){
	        //获取云存储配置信息
	        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY_ECG, CloudStorageConfig.class);

	        return new QiniuCloudStorageService(config);
	    }


}
