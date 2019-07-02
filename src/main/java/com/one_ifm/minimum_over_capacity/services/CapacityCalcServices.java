package com.one_ifm.minimum_over_capacity.services;

import com.one_ifm.minimum_over_capacity.controllers.models.CapacityCalcRequest;
import com.one_ifm.minimum_over_capacity.models.SeniorJuniorNums;
import com.one_ifm.minimum_over_capacity.services.exceptions.InvalidInputException;

import java.util.List;

public interface CapacityCalcServices {
    List<SeniorJuniorNums> capacityCalculations(CapacityCalcRequest request) throws InvalidInputException;
}
