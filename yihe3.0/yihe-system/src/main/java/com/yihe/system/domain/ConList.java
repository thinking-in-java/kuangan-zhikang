/**
  * Copyright 2019 bejson.com 
  */
package com.yihe.system.domain;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-22 17:26:43
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ConList  implements Serializable{

    @Override
	public String toString() {
		return "[con_id=" + con_id + ", conKey=" + conKey + ", conValue=" + conValue + ", pos=" + pos + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String con_id;
    private String conKey;
    private String conValue;
    private int pos;
    public ConList() {
		 
		
	}
    public ConList(String conKey, String conValue, int pos) {
		super();
		this.conKey = conKey;
		this.conValue = conValue;
		this.pos = pos;
	}
    public ConList(String conKey, int pos) {
		super();
		this.conKey = conKey;
		this.pos = pos;
	}
    public ConList(int pos) {
		super();
		
		this.pos = pos;
	}
	public void setCon_id(String con_id) {
         this.con_id = con_id;
     }
     public String getCon_id() {
         return con_id;
     }

    public void setConKey(String conKey) {
         this.conKey = conKey;
     }
     public String getConKey() {
         return conKey;
     }

    public void setConValue(String conValue) {
         this.conValue = conValue;
     }
     public String getConValue() {
         return conValue;
     }

    public void setPos(int pos) {
         this.pos = pos;
     }
     public int getPos() {
         return pos;
     }

}