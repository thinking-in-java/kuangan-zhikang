package com.yihe.framework.ehr;
public class EhrException extends RuntimeException {
    public final int status;
    public EhrException(int status,String message){super(message);this.status=status;}
}
