package com.yihe.system.domain;

import lombok.Data;

import java.util.List;

@Data
public class YhAssessTopic {
    /* 序号 */
    private Integer id;
    /* 题目 */
    private String name;
    /* 所属评估 */
    private String assess;
    /* 排序 */
    private Integer sort;
    /* 选项 */
    private List<YhAssessOption> options;
}
