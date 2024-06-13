package com.yihe.applet.api.domain;

import lombok.Data;

/**
 * Description:疾病信息表
 * Created by Administrator on 2020/5/26 14:07
 */
@Data
public class YhDiseaseCategoryApp implements Comparable<YhDiseaseCategoryApp>{
    private Long id;
    private long diseaseCode;
    private String diseaseName;
    private String riskGroup;
    private Integer orderNum;
	@Override
	public int compareTo(YhDiseaseCategoryApp o) {
		if (this.orderNum > o.getOrderNum()) {
            return 1;
        } else {
            return -1;
        }
	}
}
