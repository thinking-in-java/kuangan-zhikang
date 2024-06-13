package com.yihe.system.domain.layim;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public class LayuiResult<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;

    /**
     * 响应编码
     */
    private int code = 0;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    private Integer pages = 0;
    private long total = 0;

    public Integer getPages() {
        return pages;
    }



    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public long getTotal() {
        return total;
    }


    public static LayuiResult ok() {

        return new LayuiResult().code(0);
    }
    public static LayuiResult error(String msg) {

        return new LayuiResult().code(1).msg(msg);
    }

    public LayuiResult code(int code) {
        this.code = code;
        return this;
    }

    public LayuiResult msg(String msg) {
        this.msg = msg;
        return this;
    }

    public LayuiResult data(T data) {
        this.data = data;

        return this;
    }

    public LayuiResult pages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public LayuiResult total(long total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return "LayuiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
