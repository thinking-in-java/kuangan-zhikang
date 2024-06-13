package com.yihe.system.domain;

import java.util.Date;

public class YhUserFolderWeb {
    private Long folderId;

    private String folderName;

    private String folderParent;

    private String folderConstraint;

    private Date folderCreationDate;

    private String userCode;

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public String getFolderParent() {
        return folderParent;
    }

    public void setFolderParent(String folderParent) {
        this.folderParent = folderParent == null ? null : folderParent.trim();
    }

    public String getFolderConstraint() {
        return folderConstraint;
    }

    public void setFolderConstraint(String folderConstraint) {
        this.folderConstraint = folderConstraint == null ? null : folderConstraint.trim();
    }

    public Date getFolderCreationDate() {
        return folderCreationDate;
    }

    public void setFolderCreationDate(Date folderCreationDate) {
        this.folderCreationDate = folderCreationDate;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }
}