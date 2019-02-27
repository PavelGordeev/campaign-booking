package com.gordeev.campaignbooking.dao.jdbc.mapper;

import com.gordeev.campaignbooking.entity.CampaignSummary;
import com.gordeev.campaignbooking.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignSummaryRowMapper implements RowMapper<CampaignSummary> {
    @Override
    public CampaignSummary mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        CampaignSummary campaignSummary = new CampaignSummary();

        campaignSummary.setId(resultSet.getInt("c_id"));
        campaignSummary.setName(resultSet.getString("c_name"));
        campaignSummary.setStatus(Status.getById(resultSet.getInt("c_status_id")));
        campaignSummary.setAdvertCount(resultSet.getInt("ads_count"));

        return campaignSummary;
    }
}