package com.gordeev.campaignbooking.service;

import com.gordeev.campaignbooking.entity.Ad;

public interface AdService {
    Ad findById(int id);

    Ad create(Ad ad);

    Ad update(Ad ad);

    void removeById(int id);
}
