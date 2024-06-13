package com.yihe.applet.api.domain;

public class YhUserChatRecord {
    private Integer id;

    private String userCode;

    private String toUserCode;

    private String message;

    private String addtimeinfo;
    
    private Integer read;
    
    private String name;
    
    
    private Integer mtype;
    
    private String roomid;



    private Integer messageType;

    private  String filename;
    private  String filesize;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
    public Integer getMtype() {
		return mtype;
	}

	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public YhUserChatRecord(){
    	
    }

    public YhUserChatRecord(String userCode, String toUserCode, String message, Integer read) {
		 
		this.userCode = userCode;
		this.toUserCode = toUserCode;
		this.message = message;
		this.read = read;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(String toUserCode) {
        this.toUserCode = toUserCode == null ? null : toUserCode.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getAddtimeinfo() {
        return addtimeinfo;
    }

    public void setAddtimeinfo(String addtimeinfo) {
        this.addtimeinfo = addtimeinfo == null ? null : addtimeinfo.trim();
    }
}