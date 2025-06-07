package com.yihe.system.vo;

import lombok.Data;

@Data
public class NewEdicalExaminationRecordsVO {

    private Integer id;
    private Integer regId;
    private Integer gender;
    private Integer age;
    private String phone;
    private String marriage;
    private String occupation;
    private String height;
    private String weight;
    private String bodyMassIndex;
    private String bloodPressure;
    private String heartRate;
    private String bloodOxygen;
    private String personalMedicalExaminationReport;
    private String stage;
    private Integer isCheck;
    private String userCode;
    private String time;
    private String signature;
    private boolean hidden = false;
}
