package com.gordeev.campaignbooking.web.controller;

import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    private CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/{id}")
    public Campaign findById(@PathVariable int id) {
        return campaignService.findById(id);
    }

    @PostMapping
    public Campaign create(@RequestBody Campaign campaign) {
        return campaignService.create(campaign);
    }

    @PutMapping("/{id}")
    public Campaign update(@RequestBody Campaign campaign, @PathVariable int id) {
        campaign.setId(id);
        return campaignService.update(campaign);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        campaignService.removeById(id);
    }
}