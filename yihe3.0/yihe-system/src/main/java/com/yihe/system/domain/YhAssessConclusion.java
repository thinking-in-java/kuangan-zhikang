package com.yihe.system.domain;

import lombok.Data;

@Data
public class YhAssessConclusion {
    private Integer id;
    private Integer scoreMin;
    private Integer scoreMax;
    private String state;
    private String conclusion;
    private String suggest;
    private String guidance;
    private String link;
    private String assess;
    private String leftwidth;
}
