package com.yihe.applet.api.domain;

public class YhSportCourse {
	 private Integer courseId;

	    private String courseType;

	    private String courseName;

	    private String courseStrength;

	    private String courseContent;

	    private Integer courseTotalday;

	    private Integer courseComposing;

	    private Integer courseAveragedaily;

	    private Float courseKcal;

	    private String courseImg;

	    private Integer courseNumber;

	    private Integer isDel;

	    private String courseUse;
    
	    private int choice;
    
    

    public Integer getCourseComposing() {
			return courseComposing;
		}

		public void setCourseComposing(Integer courseComposing) {
			this.courseComposing = courseComposing;
		}

	public String getCourseUse() {
		return courseUse;
	}

	public void setCourseUse(String courseUse) {
		this.courseUse = courseUse;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType == null ? null : courseType.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getCourseStrength() {
        return courseStrength;
    }

    public void setCourseStrength(String courseStrength) {
        this.courseStrength = courseStrength == null ? null : courseStrength.trim();
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent == null ? null : courseContent.trim();
    }

    public Integer getCourseTotalday() {
        return courseTotalday;
    }

    public void setCourseTotalday(Integer courseTotalday) {
        this.courseTotalday = courseTotalday;
    }

    public Integer getCourseAveragedaily() {
        return courseAveragedaily;
    }

    public void setCourseAveragedaily(Integer courseAveragedaily) {
        this.courseAveragedaily = courseAveragedaily;
    }

    public Float getCourseKcal() {
        return courseKcal;
    }

    public void setCourseKcal(Float courseKcal) {
        this.courseKcal = courseKcal;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg == null ? null : courseImg.trim();
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}