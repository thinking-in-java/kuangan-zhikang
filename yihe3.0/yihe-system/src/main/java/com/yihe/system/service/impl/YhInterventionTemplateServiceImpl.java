package com.yihe.system.service.impl;

import com.yihe.system.domain.YhDiseaseCategory;
import com.yihe.system.domain.YhInterventionTemplate;
import com.yihe.system.domain.YhProgramsDictionary;
import com.yihe.system.domain.YhProgramsTemplate;
import com.yihe.system.mapper.YhInterventionTemplateMapper;
import com.yihe.system.service.YhInterventionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class YhInterventionTemplateServiceImpl implements YhInterventionTemplateService{
    @Autowired
    YhInterventionTemplateMapper yhinterventiontemplatemapper;

    @Override
    public int selectcount(YhInterventionTemplate templte) {
        return yhinterventiontemplatemapper.selectcount(templte);
    }

    @Override
    public List<YhInterventionTemplate> selectBypage(YhInterventionTemplate templte) {
        templte.setStart((templte.getPage()-1)*templte.getLimit());
        return yhinterventiontemplatemapper.selectBypage(templte);
    }

    @Override
    public String roleid(String Usercode) {
        return yhinterventiontemplatemapper.roleid(Usercode);
    }

    @Override
    public int selectyhprogramstemplatecount(YhProgramsTemplate templte) {
        return yhinterventiontemplatemapper.selectyhprogramstemplatecount(templte);
    }

    @Override
    public List<YhProgramsTemplate> selectbyprograms(YhProgramsTemplate templte) {
        templte.setStart(templte.getLimit()*(templte.getPage()-1));
        return yhinterventiontemplatemapper.selectbyprograms(templte);
    }

    @Override
    public List<YhProgramsDictionary> selectyhprogramsdictionary() {
        return yhinterventiontemplatemapper.selectyhprogramsdictionary();
    }

    @Override
    public int changetable(YhInterventionTemplate templte) {
        int count=yhinterventiontemplatemapper.temporarycount(templte);
        if(count==0){
            return yhinterventiontemplatemapper.changetable(templte);
        }else{
            return 0;
        }

    }



    @Override
    public int insertproogram(YhProgramsTemplate templte) {

        return yhinterventiontemplatemapper.insertproogram(templte);
    }

    @Override
    public int updateplan(YhProgramsTemplate templte) {
        return yhinterventiontemplatemapper.updateplan(templte);
    }

    @Override
    public int deleteplan(List<YhProgramsTemplate> programsTemplates) {
        int a=0;
        for (int i=0;i<programsTemplates.size();i++){
            a=a+yhinterventiontemplatemapper.deleteplan(programsTemplates.get(i));
        }
        return a;
    }

    @Override
    public YhInterventionTemplate interventionId(YhInterventionTemplate templte) {
        return yhinterventiontemplatemapper.interventionId(templte);
    }

    @Override
    public int removetemporary(YhProgramsTemplate templte) {
        return yhinterventiontemplatemapper.removetemporary(templte);
    }

    @Override
    public int changetempdate(YhInterventionTemplate templte) {
        //把先增加或修改总表
        if(templte.getInterventionId()==null){
            //增加，并返回值
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            templte.setInsertTime(df.format(new Date()));
            //查询计划数
            templte.setPlanCount(yhinterventiontemplatemapper.selecttemporary(templte));
            //查询来源名字
            templte.setSouceName(yhinterventiontemplatemapper.selectname(templte));
            templte.setSouceIsdelete(0);
            int count=yhinterventiontemplatemapper.insertSelective(templte);
            //把临时表的Intervention_id改成新增加的
            yhinterventiontemplatemapper.changesave(templte);
            //把临时表转移到正式表
            yhinterventiontemplatemapper.changetableformal(templte);
            YhProgramsTemplate te=new YhProgramsTemplate();
            te.setInterventionId(templte.getInterventionId());
            te.setUsercode(templte.getUsercode());
            //移除临时表的数据
            yhinterventiontemplatemapper.removetemporary(te);
        }else{
            //修改
            //先修改总表的值
            templte.setPlanCount(yhinterventiontemplatemapper.selecttemporary(templte));
            int update=yhinterventiontemplatemapper.updateByPrimaryKeySelective(templte);
            YhProgramsTemplate te=new YhProgramsTemplate();
            te.setInterventionId(templte.getInterventionId());
            te.setUsercode(templte.getUsercode());
            //然后再清除正式表的值
            yhinterventiontemplatemapper.removetemporaryformal(te);
            //然后再转移临时表的数据到正式表
            yhinterventiontemplatemapper.changetableformal(templte);
            //清除临时表的值

            yhinterventiontemplatemapper.removetemporary(te);
        }
     return 0;
    }

    @Override
    public int deletetempdate(YhInterventionTemplate templte) {
        YhProgramsTemplate te=new YhProgramsTemplate();
        te.setInterventionId(templte.getInterventionId());
        //先删除临时表
        yhinterventiontemplatemapper.removetemporary(te);
        //再删除正式表
        yhinterventiontemplatemapper.removetemporaryformal(te);
        //再删除总表

        return yhinterventiontemplatemapper.deleteByPrimaryKey(templte.getInterventionId());
    }

    @Override
    public List<YhInterventionTemplate> selecttemplate(YhInterventionTemplate templte) {
        List<YhInterventionTemplate> templates=yhinterventiontemplatemapper.selecttemplate(templte);
        System.out.println(templates);
        if(templates !=null){
            for (int i=0;i<templates.size();i++){
                String names=templates.get(i).getDisease_name();
                if(templates.get(i).getTemplateRisk()!=null){
                    String tiao[]=templates.get(i).getTemplateRisk().split(",");
                    for (int j=0;j<tiao.length;j++){
                        if(tiao[j].equals("1")){
                            names=names+"、一级（疑似已患或异常）";
                        }
                        if(tiao[j].equals("2")){
                            names=names+"、二级（高风险或高危）";
                        }
                        if(tiao[j].equals("3")){
                            names=names+"、三级（中风险或中危）";
                        }
                        if(tiao[j].equals("4")){
                            names=names+"、四级（低风险或低危）";
                        }
                    }
                }
                if(templates.get(i).getApplySex()==1){
                    names=names+"、男";
                }
                if(templates.get(i).getApplySex()==2){
                    names=names+"、女";
                }

                if(templates.get(i).getApplyAgeMin()!=null&&templates.get(i).getApplyAgeMax()!=null){
                    names=names+"、"+templates.get(i).getApplyAgeMin()+"至"+templates.get(i).getApplyAgeMax()+"岁";
                }
                if(templates.get(i).getApplyAgeMin()!=null&&templates.get(i).getApplyAgeMax()==null){
                    names=names+"、大于"+templates.get(i).getApplyAgeMin()+"岁";
                }
                if(templates.get(i).getApplyAgeMin()==null&&templates.get(i).getApplyAgeMax()!=null){
                    names=names+"、小于"+templates.get(i).getApplyAgeMax()+"岁";
                }

                if(templates.get(i).getApplyBimMin()!=null&&templates.get(i).getApplyBimMax()!=null){
                    names=names+"、BMI："+templates.get(i).getApplyBimMin()+"至"+templates.get(i).getApplyBimMax();
                }
                if(templates.get(i).getApplyBimMin()!=null&&templates.get(i).getApplyBimMax()==null){
                    names=names+"、BMI：大于"+templates.get(i).getApplyBimMin();
                }
                if(templates.get(i).getApplyBimMin()==null&&templates.get(i).getApplyBimMax()!=null){
                    names=names+"、BMI：小于"+templates.get(i).getApplyBimMax();
                }
                templates.get(i).setDisease_name(names);



            }
        }

        return templates;
    }

    @Override
    public List<YhDiseaseCategory> selectallcategory() {
        return yhinterventiontemplatemapper.selectallcategory();
    }

    @Override
    public Integer selectcountdata(String usercode) {
        return yhinterventiontemplatemapper.selectcountdata(usercode);
    }

    @Override
    public Integer selectcountbyname(String templateName) {
        return yhinterventiontemplatemapper.selectcountbyname(templateName);
    }
}
