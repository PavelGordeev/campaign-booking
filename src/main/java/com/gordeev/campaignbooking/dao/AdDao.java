package com.gordeev.campaignbooking.dao;

import com.gordeev.campaignbooking.entity.Ad;

public interface AdDao {
    Ad findById(int id);

    Ad create(Ad ad);

    Ad update(Ad ad);

    void removeById(int id);
}
