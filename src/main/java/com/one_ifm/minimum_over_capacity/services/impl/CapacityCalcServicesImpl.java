package com.one_ifm.minimum_over_capacity.services.impl;

import com.one_ifm.minimum_over_capacity.controllers.models.CapacityCalcRequest;
import com.one_ifm.minimum_over_capacity.models.SeniorJuniorNums;
import com.one_ifm.minimum_over_capacity.services.CapacityCalcServices;
import com.one_ifm.minimum_over_capacity.services.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CapacityCalcServicesImpl implements CapacityCalcServices {

    private static Logger logger = LoggerFactory.getLogger(CapacityCalcServicesImpl.class);

    // The map with the solutions for our problem
    private Map<Integer, SeniorJuniorNums> mapOfMinOverCapacity;

    // The map with the solutions for our problem just without the rule of at least on senior is necessary
    private Map<Integer, SeniorJuniorNums> tempMapOfMinOverCapacity;

    /**
     *
     * @param request Object of the array of the rooms and the capacity of senior and junior cleaner
     * @return List of objects that shows the number of seniors and juniors for the rooms with the minimum over capacity
     * @throws InvalidInputException
     */
    @Override
    public List<SeniorJuniorNums> capacityCalculations(CapacityCalcRequest request) throws InvalidInputException {

        if (request.getRooms() == null || request.getJunior() == null || request.getSenior() == null)
            throw new InvalidInputException("Inputs can not be null");
        if (request.getSenior() <= 0 || request.getJunior() <= 0)
            throw new InvalidInputException("senior capacity and junior capacity can not be negative");
        if (request.getJunior() > request.getSenior())
            throw new InvalidInputException("Senior capacity can not be less than junior capacity");


        // we solve the problems for all the number of rooms that are smaller that the maxRooms
        Integer maxNumberOfRooms = Collections.max(request.getRooms());
        setupMaps(request.getSenior(), request.getJunior(), maxNumberOfRooms);

        List<SeniorJuniorNums> list = request
                .getRooms()
                .stream()
                .map(it -> mapOfMinOverCapacity.get(it))
                .collect(Collectors.toList());

        return list;
    }

    private void setupMaps(Integer seniorCapacity, Integer juniorCapacity, Integer maxSizeOfMap) {
        // First we create the temp map which doesn't have the rule of one senior is necessary
        buildingTempMap(seniorCapacity, juniorCapacity, maxSizeOfMap);
        // We Create the main map(which includes or answers) by using the temp map
        buildMainMap(seniorCapacity, maxSizeOfMap);
    }


    /* The minimum over capacity can be found by recursive algorithm(which its changed to dynamic programming)
    if min == min {min of (rooms - senior capacity) , min or (rooms - junior capacity)}*/
    private void buildingTempMap(Integer seniorCapacity, Integer juniorCapacity, Integer maxSizeOfMap) {
        tempMapOfMinOverCapacity = new HashMap<>();

        for (int i = 1; i <= maxSizeOfMap; i++) {
            if (i <= juniorCapacity) {
                tempMapOfMinOverCapacity.put(i, new SeniorJuniorNums(0, 1));
                continue;
            }

            SeniorJuniorNums juniorMinOver = tempMapOfMinOverCapacity.get(i - juniorCapacity);
            SeniorJuniorNums seniorMinOver = tempMapOfMinOverCapacity.get(i - seniorCapacity);

            if (juniorMinOver == null) {
                tempMapOfMinOverCapacity.put(i,
                        new SeniorJuniorNums(seniorMinOver.getSenior(), seniorMinOver.getJunior()));
            } else if (seniorMinOver == null) {
                Integer overByJunior = calcOverCapacityByJunior(seniorCapacity, juniorCapacity, i, juniorMinOver);

                if (overByJunior < seniorCapacity - i)
                    tempMapOfMinOverCapacity.put(i,
                            new SeniorJuniorNums(juniorMinOver.getSenior(), juniorMinOver.getJunior() + 1));
                else
                    tempMapOfMinOverCapacity.put(i, new SeniorJuniorNums(1, 0));

            } else {

                Integer overBySenior = calcOverCapacityBySenior(seniorCapacity, juniorCapacity, i, seniorMinOver);
                Integer overByJunior = calcOverCapacityByJunior(seniorCapacity, juniorCapacity, i, juniorMinOver);
                if (overByJunior < overBySenior)
                    tempMapOfMinOverCapacity
                            .put(i, new SeniorJuniorNums(juniorMinOver.getSenior(), juniorMinOver.getJunior() + 1));
                else
                    tempMapOfMinOverCapacity
                            .put(i, new SeniorJuniorNums(seniorMinOver.getSenior() + 1, seniorMinOver.getJunior()));
            }
        }
    }

    private int calcOverCapacityByJunior(Integer seniorCapacity, Integer juniorCapacity, int i, SeniorJuniorNums se) {
        return se.getSenior() * seniorCapacity
                + se.getJunior() * juniorCapacity
                - (i - juniorCapacity);
    }

    private int calcOverCapacityBySenior(Integer seniorCapacity, Integer juniorCapacity, int i, SeniorJuniorNums se) {
        return se.getSenior() * seniorCapacity
                + se.getJunior() * juniorCapacity
                - (i - seniorCapacity);
    }

    private void buildMainMap(Integer seniorCapacity, Integer maxSizeOfMap) {
        mapOfMinOverCapacity = new HashMap<>();
        for(int i = 1; i <= maxSizeOfMap; i++) {
            if (i <= seniorCapacity)
                mapOfMinOverCapacity.put(i, new SeniorJuniorNums(1, 0));
            else {
                Integer numOfSeniors = tempMapOfMinOverCapacity.get(i - seniorCapacity).getSenior() + 1;
                Integer numOfJuniors = tempMapOfMinOverCapacity.get(i - seniorCapacity).getJunior();
                mapOfMinOverCapacity.put(i, new SeniorJuniorNums(numOfSeniors, numOfJuniors));
            }
        }
    }
}
