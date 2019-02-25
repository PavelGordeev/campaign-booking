package com.gordeev.campaignbooking.dao.jdbc.mapper;

import com.gordeev.campaignbooking.entity.Campaign;
import com.gordeev.campaignbooking.entity.Status;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CampaignRowMapperTest {
    @Test
    public void mapRow() throws SQLException {
        // prepare
        Campaign expectedCampaign = new Campaign();
        expectedCampaign.setId(1);
        expectedCampaign.setName("campaign name");
        expectedCampaign.setStatus(Status.PLANNED);
        expectedCampaign.setStartDate(LocalDateTime.of(2018, 8, 15, 18, 16));
        expectedCampaign.setEndDate(LocalDateTime.of(2018, 9, 15, 18, 16));

        CampaignRowMapper campaignRowMapper = new CampaignRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("c_id")).thenReturn(1);
        when(resultSet.getString("c_name")).thenReturn("campaign name");
        when(resultSet.getInt("c_status_id")).thenReturn(0);
        when(resultSet.getTimestamp("c_start_date")).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 8, 15, 18, 16)));
        when(resultSet.getTimestamp("c_end_date")).thenReturn(Timestamp.valueOf(LocalDateTime.of(2018, 9, 15, 18, 16)));

        // when
        Campaign actualCampaign = campaignRowMapper.mapRow(resultSet, 1);

        // then
        assertNotNull(actualCampaign);
        assertEquals(expectedCampaign.getId(), actualCampaign.getId());
        assertEquals(expectedCampaign.getName(), actualCampaign.getName());
        assertEquals(expectedCampaign.getStatus(), actualCampaign.getStatus());
        assertEquals(expectedCampaign.getStartDate(), actualCampaign.getStartDate());
        assertEquals(expectedCampaign.getEndDate(), actualCampaign.getEndDate());
    }
}