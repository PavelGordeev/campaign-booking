package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.CampaignDao;
import com.gordeev.campaignbooking.dao.jdbc.mapper.CampaignResultSetExtractor;
import com.gordeev.campaignbooking.dao.jdbc.mapper.CampaignSummaryRowMapper;
import com.gordeev.campaignbooking.dao.jdbc.util.QueryGenerator;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.CampaignSummary;
import com.gordeev.campaignbooking.entity.Status;
import com.gordeev.campaignbooking.request.SummaryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final CampaignSummaryRowMapper CAMPAIGN_SUMMARY_ROW_MAPPER = new CampaignSummaryRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private int countOnPage;

    @Autowired
    public JdbcCampaignDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, @Value("${summary.count.on.page:5}") int countOnPage) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.countOnPage = countOnPage;
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

        namedParameterJdbcTemplate.update(DELETE_CAMPAIGN_SQL, new MapSqlParameterSource("id", id));

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public List<CampaignSummary> findSummary(SummaryRequest summaryRequest) {
        logger.debug("Get summaries from db for request: {}", summaryRequest);
        long startTime = System.currentTimeMillis();

        String query = QueryGenerator.createfindSummaryQuery(summaryRequest, countOnPage);

        logger.debug("Generated query: {}", query);
        MapSqlParameterSource parametersForSummary = createParametersForSummary(summaryRequest);

        List<CampaignSummary> summaries = namedParameterJdbcTemplate.query(query, parametersForSummary, CAMPAIGN_SUMMARY_ROW_MAPPER);

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
        return summaries;
    }

    private MapSqlParameterSource createParametersForSummary(SummaryRequest summaryRequest) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if(summaryRequest.getFilterName() != null) {
            parameters.addValue("filterName", summaryRequest.getFilterName());
        }

        if(summaryRequest.getFilterStatus() != null) {
            parameters.addValue("filterStatus", summaryRequest.getFilterStatus().getId());
        }

        return parameters;
    }

    private MapSqlParameterSource createCommonParameters(Campaign campaign) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", campaign.getName());
        parameters.addValue("start_date", Timestamp.valueOf(campaign.getStartDate()));
        parameters.addValue("end_date", Timestamp.valueOf(campaign.getEndDate()));

        return parameters;
    }
}