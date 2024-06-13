package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhLifeHabitQuestion implements Serializable {
    /**
     * 自增长ID
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 题目
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 选项1
     *
     * @mbg.generated
     */
    private String choose1;

    /**
     * 选项1 的分值
     *
     * @mbg.generated
     */
    private String choose1Value;

    /**
     * 选项2
     *
     * @mbg.generated
     */
    private String choose2;

    /**
     * 选项2 的分值
     *
     * @mbg.generated
     */
    private String choose2Value;

    /**
     * 选项3
     *
     * @mbg.generated
     */
    private String choose3;

    /**
     * 选项3 的分值
     *
     * @mbg.generated
     */
    private String choose3Value;

    /**
     * 选项4
     *
     * @mbg.generated
     */
    private String choose4;

    /**
     * 选项4的分值
     *
     * @mbg.generated
     */
    private String choose4Value;

    /**
     * 选项5
     *
     * @mbg.generated
     */
    private String choose5;

    /**
     * 选项5的分值
     *
     * @mbg.generated
     */
    private String choose5Value;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoose1() {
        return choose1;
    }

    public void setChoose1(String choose1) {
        this.choose1 = choose1;
    }

    public String getChoose1Value() {
        return choose1Value;
    }

    public void setChoose1Value(String choose1Value) {
        this.choose1Value = choose1Value;
    }

    public String getChoose2() {
        return choose2;
    }

    public void setChoose2(String choose2) {
        this.choose2 = choose2;
    }

    public String getChoose2Value() {
        return choose2Value;
    }

    public void setChoose2Value(String choose2Value) {
        this.choose2Value = choose2Value;
    }

    public String getChoose3() {
        return choose3;
    }

    public void setChoose3(String choose3) {
        this.choose3 = choose3;
    }

    public String getChoose3Value() {
        return choose3Value;
    }

    public void setChoose3Value(String choose3Value) {
        this.choose3Value = choose3Value;
    }

    public String getChoose4() {
        return choose4;
    }

    public void setChoose4(String choose4) {
        this.choose4 = choose4;
    }

    public String getChoose4Value() {
        return choose4Value;
    }

    public void setChoose4Value(String choose4Value) {
        this.choose4Value = choose4Value;
    }

    public String getChoose5() {
        return choose5;
    }

    public void setChoose5(String choose5) {
        this.choose5 = choose5;
    }

    public String getChoose5Value() {
        return choose5Value;
    }

    public void setChoose5Value(String choose5Value) {
        this.choose5Value = choose5Value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", choose1=").append(choose1);
        sb.append(", choose1Value=").append(choose1Value);
        sb.append(", choose2=").append(choose2);
        sb.append(", choose2Value=").append(choose2Value);
        sb.append(", choose3=").append(choose3);
        sb.append(", choose3Value=").append(choose3Value);
        sb.append(", choose4=").append(choose4);
        sb.append(", choose4Value=").append(choose4Value);
        sb.append(", choose5=").append(choose5);
        sb.append(", choose5Value=").append(choose5Value);
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
        YhLifeHabitQuestion other = (YhLifeHabitQuestion) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getChoose1() == null ? other.getChoose1() == null : this.getChoose1().equals(other.getChoose1()))
            && (this.getChoose1Value() == null ? other.getChoose1Value() == null : this.getChoose1Value().equals(other.getChoose1Value()))
            && (this.getChoose2() == null ? other.getChoose2() == null : this.getChoose2().equals(other.getChoose2()))
            && (this.getChoose2Value() == null ? other.getChoose2Value() == null : this.getChoose2Value().equals(other.getChoose2Value()))
            && (this.getChoose3() == null ? other.getChoose3() == null : this.getChoose3().equals(other.getChoose3()))
            && (this.getChoose3Value() == null ? other.getChoose3Value() == null : this.getChoose3Value().equals(other.getChoose3Value()))
            && (this.getChoose4() == null ? other.getChoose4() == null : this.getChoose4().equals(other.getChoose4()))
            && (this.getChoose4Value() == null ? other.getChoose4Value() == null : this.getChoose4Value().equals(other.getChoose4Value()))
            && (this.getChoose5() == null ? other.getChoose5() == null : this.getChoose5().equals(other.getChoose5()))
            && (this.getChoose5Value() == null ? other.getChoose5Value() == null : this.getChoose5Value().equals(other.getChoose5Value()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getChoose1() == null) ? 0 : getChoose1().hashCode());
        result = prime * result + ((getChoose1Value() == null) ? 0 : getChoose1Value().hashCode());
        result = prime * result + ((getChoose2() == null) ? 0 : getChoose2().hashCode());
        result = prime * result + ((getChoose2Value() == null) ? 0 : getChoose2Value().hashCode());
        result = prime * result + ((getChoose3() == null) ? 0 : getChoose3().hashCode());
        result = prime * result + ((getChoose3Value() == null) ? 0 : getChoose3Value().hashCode());
        result = prime * result + ((getChoose4() == null) ? 0 : getChoose4().hashCode());
        result = prime * result + ((getChoose4Value() == null) ? 0 : getChoose4Value().hashCode());
        result = prime * result + ((getChoose5() == null) ? 0 : getChoose5().hashCode());
        result = prime * result + ((getChoose5Value() == null) ? 0 : getChoose5Value().hashCode());
        return result;
    }
}