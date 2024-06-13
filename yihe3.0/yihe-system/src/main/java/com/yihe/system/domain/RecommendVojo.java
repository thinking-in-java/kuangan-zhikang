/**
 * 
 */
package com.yihe.system.domain;

/**
 * 系统推荐习惯
 * @author dong
 *
 */
public class RecommendVojo {

	 private String habitName;
	 
	 private int complete;//完成天数
	 
	 private int sum;//总天数
	 
	 private int sustain;//持续天数

	public String getHabitName() {
		return habitName;
	}

	public void setHabitName(String habitName) {
		this.habitName = habitName;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getSustain() {
		return sustain;
	}

	public void setSustain(int sustain) {
		this.sustain = sustain;
	}
	 
	 
	 
	 
}
