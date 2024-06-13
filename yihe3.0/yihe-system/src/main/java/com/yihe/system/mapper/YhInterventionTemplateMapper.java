package com.yihe.system.mapper;

import com.yihe.system.domain.YhDiseaseCategory;
import com.yihe.system.domain.YhInterventionTemplate;
import com.yihe.system.domain.YhProgramsDictionary;
import com.yihe.system.domain.YhProgramsTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhInterventionTemplateMapper {
    int deleteByPrimaryKey(Integer interventionId);

    int insert(YhInterventionTemplate record);

    int insertSelective(YhInterventionTemplate record);

    YhInterventionTemplate selectByPrimaryKey(Integer interventionId);

    int updateByPrimaryKeySelective(YhInterventionTemplate record);

    int updateByPrimaryKey(YhInterventionTemplate record);

    List<YhInterventionTemplate> selectBypage(YhInterventionTemplate templte );

    int selectcount(YhInterventionTemplate templte);

    String roleid(String Usercode);

    int selectyhprogramstemplatecount(YhProgramsTemplate templte);

    List<YhProgramsTemplate> selectbyprograms(YhProgramsTemplate templte);

    List<YhProgramsDictionary> selectyhprogramsdictionary();

    int changetable(YhInterventionTemplate templte);

    int temporarycount(YhInterventionTemplate templte);

    int insertproogram(YhProgramsTemplate templte);

    int updateplan(YhProgramsTemplate templte);

    int deleteplan(YhProgramsTemplate templte);

    YhInterventionTemplate  interventionId(YhInterventionTemplate templte);

    int removetemporary(YhProgramsTemplate templte);

    int removetemporaryformal(YhProgramsTemplate templte);

    int changetableformal(YhInterventionTemplate templte);

    int selecttemporary(YhInterventionTemplate template);

    String selectname(YhInterventionTemplate template);

    int changesave(YhInterventionTemplate template);

    List<YhInterventionTemplate> selecttemplate(YhInterventionTemplate templte );

    List<YhDiseaseCategory> selectallcategory();
    Integer selectcountdata(String usercode);
    Integer selectcountbyname(String templateName);
}