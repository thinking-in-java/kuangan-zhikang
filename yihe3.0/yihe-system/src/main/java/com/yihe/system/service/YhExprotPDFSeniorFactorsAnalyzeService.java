package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhSeniorFactorsAnalyze;

public interface YhExprotPDFSeniorFactorsAnalyzeService {
		List<YhExportHealthAssess> exportfactors(YhSeniorFactorsAnalyze factor);
		List<YhExportHealthAssess> selectallcount(YhSeniorFactorsAnalyze factor);
}
