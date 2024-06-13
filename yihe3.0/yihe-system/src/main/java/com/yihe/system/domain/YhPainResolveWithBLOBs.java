package com.yihe.system.domain;

public class YhPainResolveWithBLOBs extends YhPainResolve {
    private String resolveName;

    private String resolveHarm;

    private String resolveExplain;
    
    private String[] resolveSolution2;

    private String resolveSolution;

    
    public String[] getResolveSolution2() {
		return resolveSolution2;
	}

	public void setResolveSolution2(String[] resolveSolution2) {
		this.resolveSolution2 = resolveSolution2;
	}

	public String getResolveName() {
        return resolveName;
    }

    public void setResolveName(String resolveName) {
        this.resolveName = resolveName == null ? null : resolveName.trim();
    }

    public String getResolveHarm() {
        return resolveHarm;
    }

    public void setResolveHarm(String resolveHarm) {
        this.resolveHarm = resolveHarm == null ? null : resolveHarm.trim();
    }

    public String getResolveExplain() {
        return resolveExplain;
    }

    public void setResolveExplain(String resolveExplain) {
        this.resolveExplain = resolveExplain == null ? null : resolveExplain.trim();
    }

    public String getResolveSolution() {
        return resolveSolution;
    }

    public void setResolveSolution(String resolveSolution) {
        this.resolveSolution = resolveSolution == null ? null : resolveSolution.trim();
    }
}