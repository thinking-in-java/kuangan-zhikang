package com.yihe.system.vo;


import lombok.Data;

@Data
public class BodyFatComparisonDTO {
    private String dayNum;
    private String weightStatus;
    private String weightChange;
/*    private String fatStatus;
    private String fatChange;*/

    private BodyFatInfoDTO before;

    private BodyFatInfoDTO after;
}
