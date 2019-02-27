package com.gordeev.campaignbooking.dao;

import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.CampaignSummary;
import com.gordeev.campaignbooking.request.SummaryRequest;

import java.util.List;

public interface CampaignDao {
    Campaign findById(int campaignId);

    Campaign create(Campaign campaign);

    Campaign update(Campaign campaign);

    void removeById(int id);

    List<CampaignSummary> findSummary(SummaryRequest summaryRequest);
}