package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserRole implements Serializable {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * 角色名称
     *
     * @mbg.generated
     */
    private String roleName;

    /**
     * 角色描述
     *
     * @mbg.generated
     */
    private String roleDescription;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date roleCtime;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Long orders;
    
    private Integer count;

    private Integer roletype;

    private Integer pid;

    private boolean ishava;

    private String manage_roleid;

    private String ctime;

    private Boolean isshow;

    public Boolean getIsshow() {
        return isshow;
    }

    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getManage_roleid() {
        return manage_roleid;
    }

    public void setManage_roleid(String manage_roleid) {
        this.manage_roleid = manage_roleid;
    }

    public boolean isIshava() {
        return ishava;
    }

    public void setIshava(boolean ishava) {
        this.ishava = ishava;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRoletype() {
        return roletype;
    }

    public void setRoletype(Integer roletype) {
        this.roletype = roletype;
    }

    private static final long serialVersionUID = 1L;
    
    
    public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Date getRoleCtime() {
        return roleCtime;
    }

    public void setRoleCtime(Date roleCtime) {
        this.roleCtime = roleCtime;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleDescription=").append(roleDescription);
        sb.append(", roleCtime=").append(roleCtime);
        sb.append(", orders=").append(orders);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        YhUserRole other = (YhUserRole) that;
        return (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()))
            && (this.getRoleDescription() == null ? other.getRoleDescription() == null : this.getRoleDescription().equals(other.getRoleDescription()))
            && (this.getRoleCtime() == null ? other.getRoleCtime() == null : this.getRoleCtime().equals(other.getRoleCtime()))
            && (this.getOrders() == null ? other.getOrders() == null : this.getOrders().equals(other.getOrders()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        result = prime * result + ((getRoleDescription() == null) ? 0 : getRoleDescription().hashCode());
        result = prime * result + ((getRoleCtime() == null) ? 0 : getRoleCtime().hashCode());
        result = prime * result + ((getOrders() == null) ? 0 : getOrders().hashCode());
        return result;
    }
}