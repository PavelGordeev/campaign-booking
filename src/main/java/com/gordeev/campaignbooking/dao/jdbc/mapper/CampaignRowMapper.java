package com.gordeev.campaignbooking.dao.jdbc.mapper;

import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CampaignRowMapper implements RowMapper<Campaign> {
    @Override
    public Campaign mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Campaign campaign = new Campaign();

        campaign.setId(resultSet.getInt("c_id"));
        campaign.setName(resultSet.getString("c_name"));
        campaign.setStatus(Status.getById(resultSet.getInt("c_status_id")));

        Timestamp starTimestamp = resultSet.getTimestamp("c_start_date");
        campaign.setStartDate(starTimestamp.toLocalDateTime());

        Timestamp endTimestamp = resultSet.getTimestamp("c_end_date");
        campaign.setEndDate(endTimestamp.toLocalDateTime());

        return campaign;
    }
}