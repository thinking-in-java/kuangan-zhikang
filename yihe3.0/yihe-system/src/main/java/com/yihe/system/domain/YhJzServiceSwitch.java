package com.yihe.system.domain;

public class YhJzServiceSwitch {
    private Integer id;
    private Integer service_type;
    private Double service_date;
    private String service_content;
    private String service_describe;
    private Integer isopen;
    private Integer return_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getService_type() {
        return service_type;
    }

    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }

    public Double getService_date() {
        return service_date;
    }

    public void setService_date(Double service_date) {
        this.service_date = service_date;
    }

    public String getService_content() {
        return service_content;
    }

    public void setService_content(String service_content) {
        this.service_content = service_content;
    }

    public String getService_describe() {
        return service_describe;
    }

    public void setService_describe(String service_describe) {
        this.service_describe = service_describe;
    }

    public Integer getIsopen() {
        return isopen;
    }

    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }

    public Integer getReturn_id() {
        return return_id;
    }

    public void setReturn_id(Integer return_id) {
        this.return_id = return_id;
    }
}
