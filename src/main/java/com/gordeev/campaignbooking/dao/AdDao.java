package com.gordeev.campaignbooking.dao;

import com.gordeev.campaignbooking.entity.Ad;

import java.util.List;

public interface AdDao {
    Ad findById(int id);

    Ad create(Ad ad);

    Ad update(Ad ad);

    void removeById(int id);

    List<Ad> createAdsForCampaign(List<Ad> ads, int campaignId);

    List<Ad> updateAdsForCampaign(List<Ad> ads, int campaignId);

}