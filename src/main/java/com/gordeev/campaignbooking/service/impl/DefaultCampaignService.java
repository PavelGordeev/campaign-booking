package com.gordeev.campaignbooking.service.impl;

import com.gordeev.campaignbooking.dao.CampaignDao;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.CampaignSummary;
import com.gordeev.campaignbooking.request.SummaryRequest;
import com.gordeev.campaignbooking.service.AdService;
import com.gordeev.campaignbooking.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCampaignService implements CampaignService {

    private CampaignDao campaignDao;
    private AdService adService;

    @Autowired
    public DefaultCampaignService(CampaignDao campaignDao, AdService adService) {
        this.campaignDao = campaignDao;
        this.adService = adService;
    }

    @Override
    public Campaign findById(int campaignId) {
        return campaignDao.findById(campaignId);
    }

    @Override
    public Campaign create(Campaign newCampaign) {
        Campaign campaign = campaignDao.create(newCampaign);

        int campaignId = campaign.getId();
        List<Ad> adsForCampaign = adService.createAdsForCampaign(campaign.getAds(), campaignId);
        campaign.setAds(adsForCampaign);

        return campaign;
    }

    @Override
    public Campaign update(Campaign campaign) {
        Campaign updatedCampaign = campaignDao.update(campaign);

        int campaignId = campaign.getId();

        List<Ad> adsForCampaign = adService.updateAdsForCampaign(campaign.getAds(), campaignId);
        campaign.setAds(adsForCampaign);

        return updatedCampaign;
    }

    @Override
    public void removeById(int id) {
        campaignDao.removeById(id);
    }

    @Override
    public List<CampaignSummary> findSummary(SummaryRequest summaryRequest) {
        return campaignDao.findSummary(summaryRequest);
    }
}