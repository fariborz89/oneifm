package com.one_ifm.minimum_over_capacity.models;


import io.swagger.annotations.ApiModelProperty;

public class SeniorJuniorNums {

    @ApiModelProperty(value = "number of seniors")
    private Integer senior;
    @ApiModelProperty(value = "number of juniors")
    private Integer junior;

    public SeniorJuniorNums() {}
    public SeniorJuniorNums(Integer senior, Integer junior) {
        setSenior(senior);
        setJunior(junior);
    }

    public Integer getSenior() {
        return senior;
    }

    public void setSenior(Integer senior) {
        this.senior = senior;
    }

    public Integer getJunior() {
        return junior;
    }

    public void setJunior(Integer junior) {
        this.junior = junior;
    }
}
