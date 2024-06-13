/**
 * 
 */
package com.yihe.system.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author dong 
 *
 */
public class YhOrgTreeVojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;

    private String name;


    private String superOrgCode;
    
	
    private List<YhOrgTreeVojo> children;
    private Boolean open;
    private String value;
    
	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public Boolean getOpen() {
		return open;
	}



	public void setOpen(Boolean open) {
		this.open = open;
	}




	public String getSuperOrgCode() {
		return superOrgCode;
	}


	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<YhOrgTreeVojo> getChildren() {
		return children;
	}


	public void setChildren(List<YhOrgTreeVojo> children) {
		this.children = children;
	}


	@Override
	public String toString() {
		return "YhOrgTreeVojo [id=" + id + ", name=" + name + ", superOrgCode=" + superOrgCode + ", children="
				+ children + "]";
	}

	

    
    
}
