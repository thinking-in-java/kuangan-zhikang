/**
 * 
 */
package com.yihe.system.domain;

/**
 * @author dong
 *
 */
public class Servefulfill {

	private int finish;
	private int fail;
	private String text;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getFinish() {
		return finish;
	}
	public void setFinish(int finish) {
		this.finish = finish;
	}
	public int getFail() {
		return fail;
	}
	public void setFail(int fail) {
		this.fail = fail;
	}
	
	
}
