package com.yihe.system.domain;

public class YhSportSetmeal {
	 private Integer id;

	    private Integer diseaseCode;

	    private String setmealMame;

	    private String subjectId;

	    private String setmealImg;

	    private Integer oneself;


	    private Integer exerciseFrequency;

	    private Integer recommendBenchmarks;

	    private Integer setmealType;

    
    private int jduang;

    private String describe;
    
    
    
    
    public Integer getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(Integer diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public Integer getExerciseFrequency() {
		return exerciseFrequency;
	}

	public void setExerciseFrequency(Integer exerciseFrequency) {
		this.exerciseFrequency = exerciseFrequency;
	}

	public Integer getRecommendBenchmarks() {
		return recommendBenchmarks;
	}

	public void setRecommendBenchmarks(Integer recommendBenchmarks) {
		this.recommendBenchmarks = recommendBenchmarks;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getJduang() {
		return jduang;
	}

	public void setJduang(int jduang) {
		this.jduang = jduang;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSetmealMame() {
        return setmealMame;
    }

    public void setSetmealMame(String setmealMame) {
        this.setmealMame = setmealMame == null ? null : setmealMame.trim();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId == null ? null : subjectId.trim();
    }

    public Integer getSetmealType() {
        return setmealType;
    }

    public void setSetmealType(Integer setmealType) {
        this.setmealType = setmealType;
    }


    public String getSetmealImg() {
        return setmealImg;
    }

    public void setSetmealImg(String setmealImg) {
        this.setmealImg = setmealImg == null ? null : setmealImg.trim();
    }

    public Integer getOneself() {
        return oneself;
    }

    public void setOneself(Integer oneself) {
        this.oneself = oneself;
    }
}