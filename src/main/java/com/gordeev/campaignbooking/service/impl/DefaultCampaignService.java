package com.gordeev.campaignbooking.service.impl;

import com.gordeev.campaignbooking.dao.CampaignDao;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCampaignService implements CampaignService {

    private CampaignDao campaignDao;

    @Autowired
    public DefaultCampaignService(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @Override
    public Campaign findById(int campaignId) {
        Campaign campaign = campaignDao.findById(campaignId);
        return campaign;
    }
}
