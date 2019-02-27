package com.gordeev.campaignbooking.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.Platform;
import com.gordeev.campaignbooking.entity.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CampaignControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindById() throws Exception {
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

    @Test
    public void testCreate() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setName("First created test Campaign");
        campaign.setStartDate(LocalDateTime.of(2019, 6, 18, 0, 0));
        campaign.setEndDate(LocalDateTime.of(2019, 7, 18, 0, 0));

        Ad firstAd = new Ad();
        firstAd.setName("first created ad with campaign");
        firstAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        firstAd.setAssetUrl("asset url");
        firstAd.setCampaign(campaign);

        Ad secondAd = new Ad();
        secondAd.setName("second created ad with campaign");
        secondAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        secondAd.setAssetUrl("asset url");
        secondAd.setCampaign(campaign);

        campaign.setAds(Arrays.asList(firstAd, secondAd));

        String content = objectMapper.writeValueAsString(campaign);
        System.out.println(content);
        mockMvc.perform(post("/campaign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(3)))
                .andExpect(jsonPath("name", is("First created test Campaign")))
                .andExpect(jsonPath("status", is("PLANNED")))
                .andExpect(jsonPath("startDate", is("2019-06-18T00:00:00")))
                .andExpect(jsonPath("endDate", is("2019-07-18T00:00:00")))
                .andExpect(jsonPath("ads[0].id", is(5)))
                .andExpect(jsonPath("ads[0].name", is("first created ad with campaign")))
                .andExpect(jsonPath("ads[0].status", is("PLANNED")))
                .andExpect(jsonPath("ads[0].assetUrl", is("asset url")))
                .andExpect(jsonPath("ads[0].platforms[0]", is("WEB")))
                .andExpect(jsonPath("ads[0].platforms[1]", is("IOS")))
                .andExpect(jsonPath("ads[1].id", is(6)))
                .andExpect(jsonPath("ads[1].name", is("second created ad with campaign")))
                .andExpect(jsonPath("ads[1].status", is("PLANNED")))
                .andExpect(jsonPath("ads[1].assetUrl", is("asset url")))
                .andExpect(jsonPath("ads[1].platforms[0]", is("WEB")))
                .andExpect(jsonPath("ads[1].platforms[1]", is("IOS")));
    }

    @Test
    public void testUpdate() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setId(1);
        campaign.setName("First updated test Campaign");
        campaign.setStatus(Status.PLANNED);
        campaign.setStartDate(LocalDateTime.of(2019, 6, 18, 0, 0));
        campaign.setEndDate(LocalDateTime.of(2019, 7, 18, 0, 0));

        Ad firstAd = new Ad();
        firstAd.setName("first updated ad with campaign");
        firstAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        firstAd.setAssetUrl("asset url");
        firstAd.setStatus(Status.PLANNED);
        firstAd.setCampaign(campaign);

        Ad secondAd = new Ad();
        secondAd.setName("second updated ad with campaign");
        secondAd.setPlatforms(Arrays.asList(Platform.WEB, Platform.IOS));
        secondAd.setAssetUrl("asset url");
        secondAd.setStatus(Status.PLANNED);
        secondAd.setCampaign(campaign);

        campaign.setAds(Arrays.asList(firstAd, secondAd));

        mockMvc.perform(put("/campaign/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campaign)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("First updated test Campaign")))
                .andExpect(jsonPath("status", is("PLANNED")))
                .andExpect(jsonPath("startDate", is("2019-06-18T00:00:00")))
                .andExpect(jsonPath("endDate", is("2019-07-18T00:00:00")))
                .andExpect(jsonPath("ads[0].name", is("first updated ad with campaign")))
                .andExpect(jsonPath("ads[0].status", is("PLANNED")))
                .andExpect(jsonPath("ads[0].assetUrl", is("asset url")))
                .andExpect(jsonPath("ads[0].platforms[0]", is("WEB")))
                .andExpect(jsonPath("ads[0].platforms[1]", is("IOS")))
                .andExpect(jsonPath("ads[1].name", is("second updated ad with campaign")))
                .andExpect(jsonPath("ads[1].status", is("PLANNED")))
                .andExpect(jsonPath("ads[1].assetUrl", is("asset url")))
                .andExpect(jsonPath("ads[1].platforms[0]", is("WEB")))
                .andExpect(jsonPath("ads[1].platforms[1]", is("IOS")));

    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/campaign/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}