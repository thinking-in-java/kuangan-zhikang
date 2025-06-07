package com.yihe.system.service;

import com.yihe.system.vo.AddNewLogDTO;

import java.util.List;
import java.util.Map;

/**
 * 大数据中心
 */
public interface OrganizationIndexService {
    Map<String, Object> bigDataCenterRightNew(String orgId);

    List<AddNewLogDTO> queryTheNewMonitoringLogs(String orgId);

}
