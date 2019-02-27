package com.gordeev.campaignbooking.dao.jdbc;

import com.gordeev.campaignbooking.dao.AdDao;
import com.gordeev.campaignbooking.dao.jdbc.mapper.AdRowMapper;
import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Platform;
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

import java.util.ArrayList;
import java.util.Collections;
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

    private static final String DELETE_CAMPAIGN_ADS_SQL = "DELETE FROM ads WHERE ad_campaign_id = :campaignId";

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
        Ad newAd = createAdInDb(ad);

        addPlatformsToAds(Collections.singletonList(ad));
        return newAd;
    }

    private Ad createAdInDb(Ad ad) {
        logger.debug("Create ad in db: {}", ad);
        long startTime = System.currentTimeMillis();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource commonParameters = createCommonParameters(ad);
        namedParameterJdbcTemplate.update(CREATE_AD_SQL, commonParameters, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        int generatedId = (Integer) keys.get("ad_id");
        ad.setId(generatedId);
        ad.setStatus(Status.PLANNED);
        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
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
        addPlatformsToAds(Collections.singletonList(ad));
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

    @Override
    public List<Ad> createAdsForCampaign(List<Ad> ads, int campaignId) {
        logger.debug("Create ads {} for campaign: {}", ads, campaignId);
        long startTime = System.currentTimeMillis();

        List<Ad> createdAds = new ArrayList<>();
        removeByCampaignId(campaignId);

        for (Ad ad : ads) {
            Ad adInDb = createAdInDb(ad);

            createdAds.add(adInDb);
        }

        logger.debug("Execution took:  {} ms", System.currentTimeMillis() - startTime);
        addPlatformsToAds(createdAds);
        return createdAds;
    }


    @Override
    public List<Ad> updateAdsForCampaign(List<Ad> ads, int campaignId) {
        removeByCampaignId(campaignId);
        return createAdsForCampaign(ads, campaignId);
    }

    private void removeByCampaignId(int campaignId) {
        logger.debug("Remove ads for campaign with id = {}", campaignId);
        long startTime = System.currentTimeMillis();
        namedParameterJdbcTemplate.update(DELETE_CAMPAIGN_ADS_SQL, new MapSqlParameterSource("campaignId", campaignId));
        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }


//    public void updateAdsForCampaign(List<Ad> ads, int campaignId) {
//        deleteCampaignAds
//    }

//    private void deleteCampaignAds(int id) {
//        logger.debug("Delete ads from campaign with id = {}", id);
//        long startTime = System.currentTimeMillis();
//
//        Campaign campaign = findById(id);
//        List<Ad> ads = campaign.getAds();
//        for (Ad ad : ads) {
//            adDao.removeById(ad.getId());
//        }
//
//        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
//    }

//    private void addAdsToCampaign(Campaign campaign) {
//        logger.debug("Add ads {} to campaign with id = {}", campaign.getAds(), campaign.getId());
//        long startTime = System.currentTimeMillis();
//
//        for (Ad ad : campaign.getAds()) {
//            adDao.create(ad);
//        }
//
//        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
//    }

    private void addPlatformsToAds(List<Ad> ads) {
        logger.debug("Add platforms to ads {}", ads);
        long startTime = System.currentTimeMillis();

        List<MapSqlParameterSource> batchArgs = new ArrayList<>();
        for (Ad ad : ads) {
            addPlatformsToAdBatchArgs(batchArgs, ad);
        }
        namedParameterJdbcTemplate.batchUpdate(ADD_PLATFORMS_TO_AD_SQL, batchArgs.toArray(new MapSqlParameterSource[batchArgs.size()]));

        logger.debug("Execution took: {} ms", System.currentTimeMillis() - startTime);
    }

    private void addPlatformsToAdBatchArgs(List<MapSqlParameterSource> batchArgs, Ad ad) {
        List<Platform> platforms = ad.getPlatforms();
        int adId = ad.getId();

        for (Platform platform : platforms) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("ad_id", adId);
            parameters.addValue("platform_id", platform.getId());
            batchArgs.add(parameters);
        }
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