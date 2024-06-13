package com.yihe.applet.api.domain;

public class YhPainResolveAppWithBLOBs extends YhPainResolveApp {
    private String resolveName;

    private String resolveHarm;

    private String resolveExplain;

    private String resolveSolution;

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