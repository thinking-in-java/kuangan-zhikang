package com.yihe.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评估报告接收实体
 */
@Data
public class YhReportDetailsVOJO implements Serializable {
    //评估序列号
    private String requestSerialNum;

    //病种id
    private Integer diseasid;

    //病种名称
    private String diseaseName;

    //风险等级
    private String riskLevel;

    //风险描述
    private String riskDesc;

    //当前风险描述
    private String nowRiskDesc;

    //当前风险值
    private String nowRiskValue;

    //上次风险值
    private String nowRiskLastValue;

    //理想风险
    private String idealRiskLevel;

    //理想风险描述
    private String idealRiskDesc;

    //各病种改善指南
    private List<YhReportResolveVOJO> glist;

    /**
     * 相关因素集合
     */
    private List<RiskFactorsVOJO> rlist;

    /**
     * 危险因素数组
     */
    private YhDiseaseRiskFactorGroups[] fxlist;

    /**
     * 雷达图数组
     */
    private YhDiseaseRiskFactorGroupsVOJO[] gvojolist;

    //当前
    private Integer[] dangqianList;
    //理想
    private Integer[] lixiangList;


    //危险因素数组
    private YhDiseaseRiskFactorGroups[] flist;

    private int sign;




}