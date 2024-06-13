package com.yihe.applet.api.domain;

public class YhSportCourseDetail {
    private Integer cdetailId;

    private Integer cdetailDay;

    private String cdetailName;

    private Integer courseId;

    private String viconIds;

    private Integer cdetailTotals;

    private Integer cdetailTotalduration;

    private Integer cdetailTotalkal;
    
    private String courseType;

    private String courseName;

    private String courseStrength;

    private String courseContent;

    private Integer courseTotalday;

    private Integer courseAveragedaily;

    private Float courseKcal;

    private String courseImg;

    private Integer courseNumber;
    
    

    public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseStrength() {
		return courseStrength;
	}

	public void setCourseStrength(String courseStrength) {
		this.courseStrength = courseStrength;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
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
		this.courseImg = courseImg;
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Integer getCdetailId() {
        return cdetailId;
    }

    public void setCdetailId(Integer cdetailId) {
        this.cdetailId = cdetailId;
    }

    public Integer getCdetailDay() {
        return cdetailDay;
    }

    public void setCdetailDay(Integer cdetailDay) {
        this.cdetailDay = cdetailDay;
    }

    public String getCdetailName() {
        return cdetailName;
    }

    public void setCdetailName(String cdetailName) {
        this.cdetailName = cdetailName == null ? null : cdetailName.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getViconIds() {
        return viconIds;
    }

    public void setViconIds(String viconIds) {
        this.viconIds = viconIds == null ? null : viconIds.trim();
    }

    public Integer getCdetailTotals() {
        return cdetailTotals;
    }

    public void setCdetailTotals(Integer cdetailTotals) {
        this.cdetailTotals = cdetailTotals;
    }

    public Integer getCdetailTotalduration() {
        return cdetailTotalduration;
    }

    public void setCdetailTotalduration(Integer cdetailTotalduration) {
        this.cdetailTotalduration = cdetailTotalduration;
    }

    public Integer getCdetailTotalkal() {
        return cdetailTotalkal;
    }

    public void setCdetailTotalkal(Integer cdetailTotalkal) {
        this.cdetailTotalkal = cdetailTotalkal;
    }
}