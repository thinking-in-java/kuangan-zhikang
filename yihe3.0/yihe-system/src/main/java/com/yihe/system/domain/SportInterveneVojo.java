/**
 * 
 */
package com.yihe.system.domain;

import java.util.List;

import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
public class SportInterveneVojo {
	private String usercode;
	private String user_name;
	private String name;
	private String age;
	
	private String bmi;
	private String frequency;//频率
	private String planName;//
	private String Flag_Dgroup;
	
	private String pitch;
	private List<String> sportarrayList;
	
}
