package com.yihe.system.domain;

import java.util.List;

public class YhChineseAnwser2r {
    private String num1;
    private String numbers;
    private String rusult3;

    private List<YhChineseAnwser3r> zonghe;
    private List<YhChineseAnwser4r> tabData;

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

    public List<YhChineseAnwser3r> getZonghe() {
        return zonghe;
    }

    public void setZonghe(List<YhChineseAnwser3r> zonghe) {
        this.zonghe = zonghe;
    }

    public List<YhChineseAnwser4r> getTabData() {
        return tabData;
    }

    public void setTabData(List<YhChineseAnwser4r> tabData) {
        this.tabData = tabData;
    }
}
