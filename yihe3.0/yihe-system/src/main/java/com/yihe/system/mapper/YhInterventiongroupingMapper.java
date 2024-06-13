package com.yihe.system.mapper;

import com.yihe.system.domain.*;

import java.util.List;

public interface YhInterventiongroupingMapper {
    List<Interventiongrouping> selectnameandplan(Interventiongrouping interventiongrouping);
    List<YHDocGroup> selectalldoc();
    List<YhProgramsTemplate> selectserviceplan(YhProgramsTemplate yhProgramsTemplate);
    List<YhProgramsTemplate> selectservicetemplateplan(YhProgramsTemplate yhProgramsTemplate);
    int selecthavebegin(YhProgramsTemplate yhProgramsTemplate);
    int  deletetempbyusercode(String usercode);
    int templlatetemproary(YhProgramsTemplate yhProgramsTemplate);
    List<Integer> selectchangeid(YhProgramsTemplate yhProgramsTemplate);
    int changeischooes(YhProgramsTemplate yhProgramsTemplate);
    int changeischooesbyid(YhProgramsTemplate yhProgramsTemplate);
    int selecttemplatecount(YhProgramsTemplate yhProgramsTemplate);
    yhTemplateExecution selectservicetemplateexcution(String usercode);
    int updateexecution(yhTemplateExecution excution);
    int insertexcution(yhTemplateExecution excution);
    int insertdetail(YhProgramsTemplate programsTemplate);
    int addtemplatetempro(YhProgramsTemplate yhProgramsTemplate);
    int updatetemplatetempro(YhProgramsTemplate yhProgramsTemplate);
    List<YhTemplateDetail> detail();
    int insertserviceperson(YhTemplateDetail templateDetail);
    int selectiscount(YhTemplateDetail templateDetail);
    int inserttotalperson(YhTemplateDetail templateDetail);
    int deletetemplat(String Usercode);
    List<YhJzServiceAssessmentSwitch> selectallysas();
    List<YhJzServicePerson> selectpingguwatting(String nowdatetime);
    int insertSelectiveperson(YhJzServicePerson person);
    List<YhJzServicePerson> selectpingguhabit(String nowdatetime);
    List<YhJzServicePerson> selectpinggusleep(String nowdatetime);
    List<YhJzServicePerson> selectpingguaxing(String nowdatetime);
    List<YhJzServicePerson> selectpinggupst(String nowdatetime);
    List<YhJzServicePerson> selectpingxinli(String nowdatetime);
    List<YhJzServicePerson> selectyueya(String nowdatetime);
    List<YhJzServicePerson> selectxuetang(String nowdatetime);
    List<YhJzServicePerson> selectxuezhi(String nowdatetime);
    List<YhJzServicePerson> selecttizhong(String nowdatetime);
    List<YhJzServicePerson> selectyinshiriji(String nowdatetime);
    List<YhJzServicePerson> selectshuimian(String nowdatetime);
    List<YhJzServicePerson> selectyundongriji(String nowdatetime);
    List<YhJzServicePerson> selectxiguan(String nowdatetime);
    List<YhJzServicePerson> selectjiankangjihua(String nowdatetime);
    List<YhJzServiceSwitch> selectallswich();
}
