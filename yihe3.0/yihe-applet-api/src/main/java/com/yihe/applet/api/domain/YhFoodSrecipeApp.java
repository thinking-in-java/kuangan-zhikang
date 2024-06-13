package com.yihe.applet.api.domain;

import lombok.Data;

/**
 * Description:
 * Created by Administrator on 2020/5/28 9:26
 */
@Data
public class YhFoodSrecipeApp {
    /** $column.columnComment */
    private Long fsId;

    /** 1.清淡 2.甜  4.咸 */
    private Long dType;

    /** 每天的热量 */
    private Long eLevel;

    /** 1.男 2.女 */
    private Long genter;

    /** $column.columnComment */
    private Long isUse;

    /** 01 —对应推荐详情id */
    private String fElements;

    /** 套餐1,2,3.... */
    private Long packageNumber;

    /** $column.columnComment */
    private Long isDel;

    /** 推荐的疾病 */
    private Long diseaseid;

    /** 1,本公司添加  2，加盟商添加 */
    private Long oneself;

    /** 套餐描述 */
    private String fsDescribe;
}
