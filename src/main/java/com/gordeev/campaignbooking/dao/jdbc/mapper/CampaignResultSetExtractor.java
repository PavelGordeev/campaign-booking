package com.gordeev.campaignbooking.dao.jdbc.mapper;

import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampaignResultSetExtractor implements ResultSetExtractor<Campaign> {
    private static final CampaignRowMapper CAMPAIGN_ROW_MAPPER = new CampaignRowMapper();
    private static final AdRowMapper AD_ROW_MAPPER = new AdRowMapper();

    @Override
    public Campaign extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        int rowCount = 1;
        if (resultSet.next()) {
            Campaign campaign = CAMPAIGN_ROW_MAPPER.mapRow(resultSet, rowCount);
            List<Ad> ads = new ArrayList<>();

            do {
                ads.add(AD_ROW_MAPPER.mapRow(resultSet, rowCount++));
            } while (resultSet.next());

            campaign.setAds(ads);

            return campaign;
        }
        return null;
    }
}