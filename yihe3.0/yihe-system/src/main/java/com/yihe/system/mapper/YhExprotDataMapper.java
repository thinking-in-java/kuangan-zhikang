package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.*;

public interface YhExprotDataMapper {
	List<YhOrgTreeVojo> selectorg(String code);
	List<YhUserReginfoVOJO> selectreginbyorg(YhExportData yhexportnow);
	List<YhExportCheck> selectexportdate(YhExportData yhexportnow);
	
	YhExportCheck selectexportdateByRs(@Param("rs") String rs);
	List<YhExportRenalFunction> renal(YhExportData yhexportnow);
	YhExportCheck findCheckJsonInfoByRs(@Param("rs") String rs);
	List<yhDiseaseAssessTotalReport2> exportassessreport3(@Param("rs") String rs);
	List<YhExportHealthAssess> selectriskresult(YhExportData yhexportnow);
	List<yhDiseaseAssessTotalReport2> exportassessreport2(YhExportData yhexportnow);
	List<YhExportApressure> selectapressure(YhExportData yhexportnow);
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
	List<YhUserArtery> exportyhUserArteries(YhExportData yhexportnow);
}
