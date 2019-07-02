package com.one_ifm.minimum_over_capacity.models;



public class SeniorJuniorNums {
    private Integer senior;
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
