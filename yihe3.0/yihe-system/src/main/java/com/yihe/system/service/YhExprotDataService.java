package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.*;

public interface YhExprotDataService {
	List<yhDiseaseAssessTotalReport2> exportassessreport3( String rs);
	YhExportCheck findCheckJsonInfoByRs(String rs);
	YhExportCheck selectexportdateByRs(String rs);
	List<YhOrgTreeVojo> selectorg(String code);
	List<YhUserReginfoVOJO> exportnow(YhExportData yhexportnow);
	List<YhExportCheck> exportcheck(YhExportData yhexportnow);
	List<YhExportHealthAssess> exportassessreport(YhExportData yhexportnow);
	List<yhDiseaseAssessTotalReport2> exportassessreport2(YhExportData yhexportnow);
	List<YhExportApressure> exportapressure(YhExportData yhexportnow);
	List<YhExportApressure> exportxinli(YhExportData yhexportnow);
	List<YhExportApressure> exportrstripressure(YhExportData yhexportnow);
	List<YhExprotBloodPressure> exportbloodpressure(YhExportData yhexportnow);
	List<YhExprotBloodSuger> exportbloodsuger(YhExportData yhexportnow);
	List<YhExportBodyFat> exportbodyfat(YhExportData yhexportnow);
	List<YhExprotBMI> exportbmi(YhExportData yhexportnow);
	List<YhExprotBloodFat> exportbloodfat(YhExportData yhexportnow);
	List<YhExportLiver> exportliver(YhExportData yhexportnow);
	List<YhExportUserUBA> exportuba(YhExportData YhExportUserUBA);
	List<YhExportOther> other(YhExportData yhexportnow);
	List<YhExprotBloodSuger> allexportbloodsuger(YhExportData yhexportnow);
	List<YhExportBodyFat> allexportbodyfat(YhExportData yhexportnow);
	List<YhExprotBloodPressure> allexportbloodpressure(YhExportData yhexportnow);
	List<YhExprotBMI> allexportbmi(YhExportData yhexportnow);
	List<YhExprotBloodFat> allexportbloodfat(YhExportData yhexportnow);
	List<YhExportLiver> allexportliver(YhExportData yhexportnow);
	List<YhExportRenalFunction> allexportfunction(YhExportData yhexportnow);
	List<YhExportUserUBA> allexportuba(YhExportData YhExportUserUBA);
	List<YhExportOther> allother(YhExportData yhexportnow);
	List<YhExportRenalFunction> renal(YhExportData yhexportnow);
	List<YhUserArtery> exportyhUserArteries(YhExportData yhexportnow);
}
