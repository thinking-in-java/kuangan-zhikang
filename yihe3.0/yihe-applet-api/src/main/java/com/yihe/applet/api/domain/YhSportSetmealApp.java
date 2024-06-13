package com.yihe.applet.api.domain;

import lombok.Data;

/**
 * Description:运动套餐
 * Created by Administrator on 2020/5/28 14:22
 */
@Data
public class YhSportSetmealApp {
	 private Integer id;

	    private Integer diseaseCode;

	    private String setmealMame;

	    private String subjectId;

	    private String setmealImg;

	    private Integer oneself;

	    private String describe;

	    private Integer exerciseFrequency;

	    private Integer recommendBenchmarks;
	    
	   

	    /** 套餐1,2,3 */
	    private Long setmealType;

	    /** 所属疾病ID */
	    private Long diseaseid;

	 
}
