package com.gordeev.campaignbooking.dao;

import com.gordeev.campaignbooking.entity.Campaign;

public interface CampaignDao {
    Campaign findById(int campaignId);

    Campaign create(Campaign campaign);

    Campaign update(Campaign campaign);

    void removeById(int id);
}