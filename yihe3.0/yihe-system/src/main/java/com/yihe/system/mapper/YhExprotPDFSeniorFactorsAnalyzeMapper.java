package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhSeniorFactorsAnalyze;

public interface YhExprotPDFSeniorFactorsAnalyzeMapper {
	List<YhExportHealthAssess> selectdiseaseriskresult(YhSeniorFactorsAnalyze factor);
	List<YhExportHealthAssess> selectallcount(YhSeniorFactorsAnalyze factor);
}
