package com.one_ifm.minimum_over_capacity.controllers.models;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CapacityCalcRequest {

    @ApiParam(value = "Arrays of the number of rooms", example = "[24, 28, 98]")
    @NotNull private List<Integer> rooms;
    @ApiParam(value = "Capacity of a senior", example = "10")
    @NotNull private Integer senior;
    @ApiParam(value = "Capacity of a junior", example = "6")
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
