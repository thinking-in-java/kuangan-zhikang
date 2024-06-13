package com.yihe.system.domain.layim;

public class User {
    public static final User Anonymous = new User("匿名","");

    public User(){

    }
    public User(String name,String avatar){
        setUsername(name); 
        setAvatar(avatar);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String avatar="http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png";
    private String username;
    private String sign;
}
