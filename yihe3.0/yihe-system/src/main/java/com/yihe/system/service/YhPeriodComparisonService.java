package com.yihe.system.service;



import com.yihe.system.domain.PeriodComparison;
import com.yihe.system.domain.YhDiseaseAssessTotalReport3web;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface YhPeriodComparisonService {
    Map get(PeriodComparison model);
    List<YhDiseaseAssessTotalReport3web> getMeddle(PeriodComparison model);
    String getrepotimg();
}
