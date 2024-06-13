package com.yihe.system.domain;

import java.math.BigDecimal;

public class YiheRoud {
	
	public static Double round(Double db) {
		BigDecimal   b   =   new   BigDecimal(db);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
	
	/*public static void main(String[] args) {
		
		System.out.println(test1(3.4449));
	}*/
}
