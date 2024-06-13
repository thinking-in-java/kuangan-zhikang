package com.yihe.applet.api.domain;

import java.util.List;

public class YhChineseAnwser2 {
    private String num1;
    private String numbers;
    private String rusult3;

    private List<YhChineseAnwser3> zonghe;
    private List<YhChineseAnwser4> tabData;

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getRusult3() {
        return rusult3;
    }

    public void setRusult3(String rusult3) {
        this.rusult3 = rusult3;
    }

    public List<YhChineseAnwser3> getZonghe() {
        return zonghe;
    }

    public void setZonghe(List<YhChineseAnwser3> zonghe) {
        this.zonghe = zonghe;
    }

    public List<YhChineseAnwser4> getTabData() {
        return tabData;
    }

    public void setTabData(List<YhChineseAnwser4> tabData) {
        this.tabData = tabData;
    }
}
