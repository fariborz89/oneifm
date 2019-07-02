package com.one_ifm.minimum_over_capacity;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.one_ifm.minimum_over_capacity.controllers.models.CapacityCalcRequest;
import com.one_ifm.minimum_over_capacity.models.SeniorJuniorNums;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Profile("test")
@AutoConfigureMockMvc
public class MinimumOverCapacityTests {
    private static MockMvc mockMvc;

    @Autowired WebApplicationContext wac;
    @Autowired ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test1() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(35); rooms.add(21); rooms.add(17); rooms.add(28);

        List<SeniorJuniorNums> response = doTheRequest(rooms, 10, 6);
        Assert.assertTrue(response.size() == 4);
        Assert.assertTrue(response.get(0).getSenior() == 3);
        Assert.assertTrue(response.get(0).getJunior() == 1);

        Assert.assertTrue(response.get(1).getSenior() == 1);
        Assert.assertTrue(response.get(1).getJunior() == 2);

        Assert.assertTrue(response.get(2).getSenior() == 2);
        Assert.assertTrue(response.get(2).getJunior() == 0);

        Assert.assertTrue(response.get(3).getSenior() == 1);
        Assert.assertTrue(response.get(3).getJunior() == 3);

    }

    @Test
    public void test2() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(24); rooms.add(28);

        List<SeniorJuniorNums> response = doTheRequest(rooms, 11, 6);
        Assert.assertTrue(response.size() == 2);
        Assert.assertTrue(response.get(0).getSenior() == 2);
        Assert.assertTrue(response.get(0).getJunior() == 1);

        Assert.assertTrue(response.get(1).getSenior() == 2);
        Assert.assertTrue(response.get(1).getJunior() == 1);

    }

    @Test
    public void test3() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(30); rooms.add(29);

        List<SeniorJuniorNums> response = doTheRequest(rooms, 2, 1);
        Assert.assertTrue(response.size() == 2);
        Assert.assertTrue(response.get(0).getSenior() == 15);
        Assert.assertTrue(response.get(0).getJunior() == 0);

        Assert.assertTrue(response.get(1).getSenior() == 14);
        Assert.assertTrue(response.get(1).getJunior() == 1);
    }

    @Test
    public void test4() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(80); rooms.add(69);

        List<SeniorJuniorNums> response = doTheRequest(rooms, 8, 6);
        Assert.assertTrue(response.size() == 2);
        Assert.assertTrue(response.get(0).getSenior() == 10);
        Assert.assertTrue(response.get(0).getJunior() == 0);

        Assert.assertTrue(response.get(1).getSenior() == 8);
        Assert.assertTrue(response.get(1).getJunior() == 1);
    }

    @Test
    public void testWithNegativeSenior() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(30); rooms.add(29);
        doTheRequestWith4xx(rooms, -3, 4);
    }

    @Test
    public void testWithNegativeJunior() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(30); rooms.add(29);
        doTheRequestWith4xx(rooms, 3, -1);
    }

    @Test
    public void testWithSeniorLessThanJunior() throws Exception {
        List<Integer> rooms = new ArrayList<>();
        rooms.add(30); rooms.add(29);
        doTheRequestWith4xx(rooms, 3, 4);
    }

    private List<SeniorJuniorNums> doTheRequest(List<Integer> rooms, Integer senior, Integer junior) throws Exception {
        CapacityCalcRequest request = new CapacityCalcRequest();
        request.setSenior(senior);
        request.setJunior(junior);
        request.setRooms(rooms);
        MvcResult result =mockMvc.perform(
                post("/calculation")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<SeniorJuniorNums> list = Arrays.asList(objectMapper.readValue(content, SeniorJuniorNums[].class));
        return list;
    }

    private void doTheRequestWith4xx(List<Integer> rooms, Integer senior, Integer junior) throws Exception {
        CapacityCalcRequest request = new CapacityCalcRequest();
        request.setSenior(senior);
        request.setJunior(junior);
        request.setRooms(rooms);
        mockMvc.perform(
                post("/calculation")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}
