package com.gordeev.campaignbooking.web.controller;

import com.gordeev.campaignbooking.CampaignBookingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CampaignBookingApplication.class)
@AutoConfigureMockMvc
public class CampaignControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findById() throws Exception {

        mockMvc.perform(get("/campaign/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("First test Campaign")))
                .andExpect(jsonPath("status", is("PLANNED")))
                .andExpect(jsonPath("startDate", is("2019-06-18T00:00:00")))
                .andExpect(jsonPath("endDate", is("2019-07-18T00:00:00")))
                .andExpect(jsonPath("ads[0].id", is(1)))
                .andExpect(jsonPath("ads[0].name", is("first test ad")))
                .andExpect(jsonPath("ads[0].status", is("PLANNED")))
                .andExpect(jsonPath("ads[0].assetUrl", is("asset url")))
                .andExpect(jsonPath("ads[0].platforms[0]", is("WEB")))
                .andExpect(jsonPath("ads[0].platforms[1]", is("IOS")))
                .andExpect(jsonPath("ads[1].id", is(2)))
                .andExpect(jsonPath("ads[1].name", is("second test ad")))
                .andExpect(jsonPath("ads[1].status", is("ACTIVE")))
                .andExpect(jsonPath("ads[1].assetUrl", is("asset2 url")))
                .andExpect(jsonPath("ads[1].platforms[0]", is("ANDROID")))
                .andExpect(jsonPath("ads[1].platforms[1]", is("IOS")));

    }
}