package com.one_ifm.minimum_over_capacity.controllers;


import com.one_ifm.minimum_over_capacity.controllers.models.CapacityCalcRequest;
import com.one_ifm.minimum_over_capacity.models.SeniorJuniorNums;
import com.one_ifm.minimum_over_capacity.services.CapacityCalcServices;
import com.one_ifm.minimum_over_capacity.services.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CapacityController {

    @Autowired
    private CapacityCalcServices capacityCalcServices;

    static Logger logger = LoggerFactory.getLogger(CapacityController.class);

    @PostMapping(value = "/calculation")
    @ResponseStatus(HttpStatus.OK)
    public List<SeniorJuniorNums> capcityCalculation(@RequestBody @Valid CapacityCalcRequest request)
            throws InvalidInputException {
        return capacityCalcServices.capacityCalculations(request);
    }

}
