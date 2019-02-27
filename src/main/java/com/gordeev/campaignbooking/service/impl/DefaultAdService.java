package com.gordeev.campaignbooking.service.impl;

import com.gordeev.campaignbooking.dao.AdDao;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAdService implements AdService {

    private AdDao adDao;

    @Autowired
    public DefaultAdService(AdDao adDao) {
        this.adDao = adDao;
    }

    @Override
    public Ad findById(int id) {
        return adDao.findById(id);
    }

    @Override
    public Ad create(Ad ad) {
        return adDao.create(ad);
    }

    @Override
    public Ad update(Ad ad) {
        return adDao.update(ad);
    }

    @Override
    public void removeById(int id) {
        adDao.removeById(id);
    }

    @Override
    public List<Ad> createAdsForCampaign(List<Ad> ads, int campaignId) {
        return adDao.createAdsForCampaign(ads, campaignId);
    }

    @Override
    public List<Ad> updateAdsForCampaign(List<Ad> ads, int campaignId) {
        return adDao.updateAdsForCampaign(ads, campaignId);
    }
}