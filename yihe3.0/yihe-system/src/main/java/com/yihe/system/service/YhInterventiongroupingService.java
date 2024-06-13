package com.yihe.system.service;

import com.yihe.system.domain.Interventiongrouping;
import com.yihe.system.domain.YhProgramsTemplate;

import java.text.ParseException;
import java.util.List;

public interface YhInterventiongroupingService {
    List<Interventiongrouping> selectnameandplan(Interventiongrouping interventiongrouping);
    List<YhProgramsTemplate> selectserviceplan(YhProgramsTemplate yhProgramsTemplate);
    Integer updateischoose(YhProgramsTemplate yhProgramsTemplate);
    int selecttemplatecount(YhProgramsTemplate yhProgramsTemplate);
    int insertplans(YhProgramsTemplate yhProgramsTemplate) throws ParseException;
    int addtemplatetempro(YhProgramsTemplate yhProgramsTemplate);
    int updatetemplatetempro(YhProgramsTemplate yhProgramsTemplate);
    void insertservice();
    void wattingservice();
}
