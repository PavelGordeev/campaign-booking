package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.AdDao;
import com.gordeev.campaignbooking.dao.jdbc.mapper.AdRowMapper;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcAdDao implements AdDao {
    private static final AdRowMapper AD_ROW_MAPPER = new AdRowMapper();

    private static final String GET_AD_BY_ID_SQL = "SELECT  ads.*," +
            "GROUP_CONCAT(platform_ads.platform_id SEPARATOR ',') as ad_platform_ids " +
            "FROM ads " +
            "LEFT JOIN platform_ads ON ads.AD_ID = platform_ads.ad_id " +
            "WHERE ads.ad_id = :id GROUP BY ads.ad_id;";

    private static final String CREATE_AD_SQL = "INSERT INTO ads (ad_name, ad_status_id, ad_asset_url, ad_campaign_id) " +
            "VALUES (:name, 0, :asset_url, :campaign_id)";
    private static final String ADD_PLATFORMS_TO_AD_SQL = "INSERT INTO platform_ads (ad_id, platform_id) VALUES ( :ad_id, :platform_id)";
    private static final String UPDATE_AD_SQL = "UPDATE ads SET ad_name = :name, ad_status_id = :status_id, " +
            "ad_asset_url = :asset_url, ad_campaign_id = :campaign_id WHERE ad_id = :id";
    private static final String DELETE_AD_SQL = "DELETE FROM ads WHERE ad_id = :id";
    private static final String DELETE_PLATFORMS_FROM_AD_SQL = "DELETE FROM platform_ads WHERE ad_id = :id";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcAdDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Ad findById(int id) {
        logger.debug("Get ad by id: {}", id);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);

        Ad ad = namedParameterJdbcTemplate.queryForObject(GET_AD_BY_ID_SQL, parameters, AD_ROW_MAPPER);

        logger.debug("Execution took:  {} ms", System.currentTimeMillis() - startTime);
        return ad;
    }


    @Override
    @Transactional
    public Ad create(Ad ad) {
        logger.debug("Create ad in db: {}", ad);
        long startTime = System.currentTimeMillis();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource commonParameters = createCommonParameters(ad);
        namedParameterJdbcTemplate.update(CREATE_AD_SQL, commonParameters, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        int generatedId = (Integer) keys.get("ad_id");
        ad.setId(generatedId);
        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);

        addPlatformsToAd(ad.getPlatforms(), ad.getId());
        return ad;
    }


    @Override
    @Transactional
    public Ad update(Ad ad) {
        logger.debug("Update ad in db: {}", ad);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource commonParameters = createCommonParameters(ad);
        commonParameters.addValue("id", ad.getId());
        commonParameters.addValue("status_id", ad.getStatus().getId());
        namedParameterJdbcTemplate.update(UPDATE_AD_SQL, commonParameters);

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);

        deleteAdPlatforms(ad.getId());
        addPlatformsToAd(ad.getPlatforms(), ad.getId());
        return ad;
    }

    @Override
    public void removeById(int id) {
        logger.debug("Remove ad with id = {}", id);
        long startTime = System.currentTimeMillis();

        deleteAdPlatforms(id);
        namedParameterJdbcTemplate.update(DELETE_AD_SQL, new MapSqlParameterSource("id", id));

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }

    private void addPlatformsToAd(List<Platform> platforms, int adId) {
        logger.debug("Add platforms {} to ad with id = {}", platforms, adId);
        long startTime = System.currentTimeMillis();

        List<MapSqlParameterSource> batchArgs = new ArrayList<>();

        for (Platform platform : platforms) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("ad_id", adId);
            parameters.addValue("platform_id", platform.getId());
            batchArgs.add(parameters);
        }

        namedParameterJdbcTemplate.batchUpdate(ADD_PLATFORMS_TO_AD_SQL, batchArgs.toArray(new MapSqlParameterSource[platforms.size()]));

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }

    private void deleteAdPlatforms(int adId) {
        logger.debug("Delete platforms from ad with id = {}", adId);
        long startTime = System.currentTimeMillis();

        namedParameterJdbcTemplate.update(DELETE_PLATFORMS_FROM_AD_SQL, new MapSqlParameterSource("id", adId));

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }

    private MapSqlParameterSource createCommonParameters(Ad ad) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", ad.getName());
        parameters.addValue("asset_url", ad.getAssetUrl());
        // campaign id should always present
        parameters.addValue("campaign_id", ad.getCampaign().getId());
        return parameters;
    }
}
