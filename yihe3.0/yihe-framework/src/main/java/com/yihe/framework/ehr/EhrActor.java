package com.yihe.framework.ehr;
public final class EhrActor {
    public final String user,scope,mode,boundRecordId; public final boolean doctor; public final boolean mobile;
    public EhrActor(String user,String org,String mode,boolean doctor,boolean mobile){
        this(user,org,mode,doctor,mobile,null);
    }
    public EhrActor(String user,String org,String mode,boolean doctor,boolean mobile,String boundRecordId){
        if(org==null||!org.matches("[A-Za-z0-9_.:-]{1,55}"))throw new EhrException(403,"当前账号未绑定有效单位");
        this.user=user;this.scope="org:"+org;this.mode=mode;this.doctor=doctor;this.mobile=mobile;this.boundRecordId=boundRecordId;
    }
}
