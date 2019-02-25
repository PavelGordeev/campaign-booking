package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.CampaignDao;
import com.gordeev.campaignbooking.dao.jdbc.mapper.CampaignResultSetExtractor;
import com.gordeev.campaignbooking.dao.jdbc.mapper.CampaignRowMapper;
import com.gordeev.campaignbooking.entity.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCampaignDao implements CampaignDao {
    // ADS.ad_id,
    // TODO: Move properties/spring beans
    private static final String GET_CAMPAIGN_BY_ID_SQL = "SELECT  campaings.*, ads.*," +
            "GROUP_CONCAT(platform_ads.platform_id SEPARATOR ',') as ad_platform_ids " +
            "FROM campaings " +
            "LEFT JOIN ADS ON  campaings.c_id = ads.campaign_id " +
            "LEFT JOIN platform_ads ON ads.AD_ID = platform_ads.ad_id " +
            "WHERE campaings.c_id = :id GROUP BY  ads.ad_id;";

    private static final CampaignResultSetExtractor CAMPAIGN_RESULT_SET_EXTRACTOR = new CampaignResultSetExtractor();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcCampaignDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Campaign findById(int campaignId) {
        logger.debug("Start processing query to get campaign by id: {}", campaignId);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource("id", campaignId);

        Campaign campaign = namedParameterJdbcTemplate.query(GET_CAMPAIGN_BY_ID_SQL, parameters, CAMPAIGN_RESULT_SET_EXTRACTOR);

        logger.debug("Finish processing query to get campaign by id: {}. It took {} ms", campaignId, System.currentTimeMillis() - startTime);
        return campaign;
    }
}
