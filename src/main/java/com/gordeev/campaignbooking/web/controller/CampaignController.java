package com.gordeev.campaignbooking.web.controller;

import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    private CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Campaign findById(@PathVariable int id) {
        return campaignService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Campaign create(@RequestBody Campaign campaign) {
        return campaignService.create(campaign);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Campaign update(@RequestBody Campaign campaign, @PathVariable int id) {
        campaign.setId(id);
        return campaignService.update(campaign);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        campaignService.removeById(id);
    }
}