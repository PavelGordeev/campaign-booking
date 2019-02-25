package com.gordeev.campaignbooking.web.controller;

import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
