package com.yihe.system.domain.layim;

public class ChatRecordResult implements Comparable<ChatRecordResult>{
    private int id;

    public boolean isSelf() {
        return self; 
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    private boolean self;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	
	public String getTousercode() {
		return tousercode;
	}

	public void setTousercode(String tousercode) {
		this.tousercode = tousercode;
	}

	private String username;
    private String avatar;
    private String timestamp;
    private String content;
    private String usercode;
    private String roomid;
    private String tousercode;
    private Integer messageType;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    @Override
	public int compareTo(ChatRecordResult o) {
		// TODO Auto-generated method stub
		return 0;
	}

    
}
