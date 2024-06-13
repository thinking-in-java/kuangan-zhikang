package com.yihe.system.domain;

import lombok.Data;

@Data
public class YhAssessOption {
    /* 序号 */
    private Integer id;
    /* 题目id */
    private Integer topicId;
    /* 选项 */
        private String option;
    /* 分数 */
    private Integer score;
}
