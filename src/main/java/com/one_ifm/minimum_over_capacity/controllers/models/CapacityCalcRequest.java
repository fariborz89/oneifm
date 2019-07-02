package com.one_ifm.minimum_over_capacity.controllers.models;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CapacityCalcRequest {
    @NotNull private List<Integer> rooms;
    @NotNull private Integer senior;
    @NotNull private Integer junior;


    public List<Integer> getRooms() {
        return rooms;
    }

    public void setRooms(List<Integer> rooms) {
        this.rooms = rooms;
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
