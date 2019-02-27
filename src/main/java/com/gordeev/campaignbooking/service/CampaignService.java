package com.gordeev.campaignbooking.service;

import com.gordeev.campaignbooking.entity.Campaign;

public interface CampaignService {
    Campaign findById(int campaignId);

    Campaign create(Campaign campaign);

    Campaign update(Campaign campaign);

    void removeById(int id);
}