package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhExportPDFData;

public interface YhExprotPDFService {
	List<YhExportHealthAssess> selectdiseaseriskresult(YhExportPDFData yhexportnow);
	List<YhExportHealthAssess> selectdiseasestatistical(YhExportPDFData yhexportnow);
}
