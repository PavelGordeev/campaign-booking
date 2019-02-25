package com.gordeev.campaignbooking.dao.jdbc.mapper;

import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Platform;
import com.gordeev.campaignbooking.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AdRowMapper implements RowMapper<Ad> {
    @Override
    public Ad mapRow(ResultSet resultSet, int i) throws SQLException {
        Ad ad = new Ad();

        ad.setId(resultSet.getInt("ad_id"));
        ad.setName(resultSet.getString("ad_name"));
        ad.setStatus(Status.getById(resultSet.getInt("ad_status_id")));
        ad.setAssetUrl(resultSet.getString("ad_asset_url"));

        List<Platform> platforms = Arrays.stream(resultSet.getString("ad_platform_ids").split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(Platform::getById)
                .collect(Collectors.toList());
        ad.setPlatforms(platforms);
        return ad;
    }
}
