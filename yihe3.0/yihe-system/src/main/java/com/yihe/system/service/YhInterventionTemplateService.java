package com.yihe.system.service;


import com.yihe.system.domain.YhDiseaseCategory;
import com.yihe.system.domain.YhInterventionTemplate;
import com.yihe.system.domain.YhProgramsDictionary;
import com.yihe.system.domain.YhProgramsTemplate;

import java.util.List;

public interface YhInterventionTemplateService {
    int selectcount(YhInterventionTemplate templte);
    List<YhInterventionTemplate> selectBypage(YhInterventionTemplate templte);
    String roleid(String Usercode);
    int selectyhprogramstemplatecount(YhProgramsTemplate templte);
    List<YhProgramsTemplate> selectbyprograms(YhProgramsTemplate templte);
    List<YhProgramsDictionary> selectyhprogramsdictionary();
    int changetable(YhInterventionTemplate templte);
    int insertproogram(YhProgramsTemplate templte);
    int updateplan(YhProgramsTemplate templte);
    int deleteplan(List<YhProgramsTemplate> programsTemplates);
    YhInterventionTemplate  interventionId(YhInterventionTemplate templte);
    int removetemporary(YhProgramsTemplate templte);
    int changetempdate(YhInterventionTemplate templte);
    int deletetempdate(YhInterventionTemplate templte);
    List<YhInterventionTemplate> selecttemplate(YhInterventionTemplate templte );
    List<YhDiseaseCategory> selectallcategory();
    Integer selectcountdata(String usercode);
    Integer selectcountbyname(String templateName);
}
