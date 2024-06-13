package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhExportPDFData;

public interface YhExprotPDFMapper {
	List<YhExportHealthAssess> selectdiseaseriskresult(YhExportPDFData yhexportnow);
	List<YhExportHealthAssess> selectdiseasestatistical(YhExportPDFData yhexportnow);
}
