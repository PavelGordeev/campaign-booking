package com.gordeev.campaignbooking.dao.jdbc.mapper;

import com.gordeev.campaignbooking.entity.Ad;
import com.gordeev.campaignbooking.entity.Platform;
import com.gordeev.campaignbooking.entity.Status;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("ad_id")).thenReturn(1);
        when(resultSet.getString("ad_name")).thenReturn("firstAd");
        when(resultSet.getInt("ad_status_id")).thenReturn(1);
        when(resultSet.getString("ad_asset_url")).thenReturn("url1");
        when(resultSet.getString("ad_platform_ids")).thenReturn(" 0, 2 ");

        AdRowMapper adRowMapper = new AdRowMapper();
        Ad actualAd = adRowMapper.mapRow(resultSet, 1);

        assertNotNull(actualAd);
        assertEquals(1, actualAd.getId());
        assertEquals("firstAd", actualAd.getName());
        assertEquals(Status.ACTIVE, actualAd.getStatus());
        assertEquals("url1", actualAd.getAssetUrl());

        List<Platform> actualAdPlatforms = actualAd.getPlatforms();
        assertEquals(2, actualAdPlatforms.size());
        assertTrue(actualAdPlatforms.contains(Platform.WEB));
        assertTrue(actualAdPlatforms.contains(Platform.IOS));

    }
}