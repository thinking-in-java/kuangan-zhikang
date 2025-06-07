package com.yihe.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureConfirmationVO {
    private String uuid;
    private Integer id;
    private String type;
    private Integer index;
    private String add1;
    private String add2;
    private String add3;
    private String add4;
    private String add5;
    private String add6;
    private String add7;
    private String add8;
    private String add9;
    private String add10;
    private String lable1;
    private String lable2;
    private String lable3;
    private String lable4;
    private String lable5;
    private String lable6;
    private String lable7;
    private String lable8;
    private String lable9;
    private String lable10;
    private boolean hidden=false;
    private SignatureConfirmationNoticeVO notice;
}
