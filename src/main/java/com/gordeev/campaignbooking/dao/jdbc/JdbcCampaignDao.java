package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.CampaignDao;
import com.gordeev.campaignbooking.dao.jdbc.mapper.CampaignResultSetExtractor;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCampaignDao implements CampaignDao {
    // ADS.ad_id,
    // TODO: Move properties/spring beans
    private static final String GET_CAMPAIGN_BY_ID_SQL = "SELECT  campaings.*, ads.*," +
            "GROUP_CONCAT(platform_ads.platform_id SEPARATOR ',') as ad_platform_ids " +
            "FROM campaings " +
            "LEFT JOIN ADS ON  campaings.c_id = ads.ad_campaign_id " +
            "LEFT JOIN platform_ads ON ads.AD_ID = platform_ads.ad_id " +
            "WHERE campaings.c_id = :id GROUP BY  ads.ad_id;";
    private static final String CREATE_CAMPAIGN_SQL = "INSERT INTO campaings (c_name, c_status_id, c_start_date, c_end_date) " +
            "VALUES (:name, 0, :start_date, :end_date)";
    private static final String UPDATE_CAMPAIGN_SQL = "UPDATE campaings SET c_name = :name, c_status_id = :status, " +
            "c_start_date = :start_date, c_end_date = :end_date WHERE c_id = :id";
    private static final String DELETE_CAMPAIGN_SQL = "DELETE FROM campaings WHERE c_id = :id";

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

    @Override
    public Campaign create(Campaign campaign) {
        logger.debug("Create campaign in db: {}", campaign);
        long startTime = System.currentTimeMillis();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource commonParameters = createCommonParameters(campaign);
        namedParameterJdbcTemplate.update(CREATE_CAMPAIGN_SQL, commonParameters, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        int generatedId = (Integer) keys.get("c_id");
        campaign.setId(generatedId);
        campaign.setStatus(Status.PLANNED);

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);

        return campaign;
    }

    @Override
    public Campaign update(Campaign campaign) {
        logger.debug("Update campaign in db: {}", campaign);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource commonParameters = createCommonParameters(campaign);
        commonParameters.addValue("id", campaign.getId());
        commonParameters.addValue("status", campaign.getStatus().getId());
        namedParameterJdbcTemplate.update(UPDATE_CAMPAIGN_SQL, commonParameters);

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
        return campaign;
    }

    @Override
    @Transactional
    public void removeById(int id) {
        logger.debug("Remove campaign with id = {}", id);
        long startTime = System.currentTimeMillis();

//        deleteCampaignAds(id);
        namedParameterJdbcTemplate.update(DELETE_CAMPAIGN_SQL, new MapSqlParameterSource("id", id));

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }

    private MapSqlParameterSource createCommonParameters(Campaign campaign) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", campaign.getName());
        parameters.addValue("start_date", Timestamp.valueOf(campaign.getStartDate()));
        parameters.addValue("end_date", Timestamp.valueOf(campaign.getEndDate()));

        return parameters;
    }
}