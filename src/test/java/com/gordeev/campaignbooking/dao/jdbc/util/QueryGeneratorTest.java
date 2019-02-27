package com.gordeev.campaignbooking.dao.jdbc.util;

import com.gordeev.campaignbooking.entity.Status;
import com.gordeev.campaignbooking.request.SortDirection;
import com.gordeev.campaignbooking.request.SortField;
import com.gordeev.campaignbooking.request.SummaryRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryGeneratorTest {
    private static final String FIND_SUMMARY_SQL_PREFIX = "SELECT c.c_id, c.c_name, c.c_status_id, " +
            "count(ads.ad_id) as ads_count FROM campaings c LEFT JOIN ads ON  c.c_id = ads.ad_campaign_id ";

    @Test
    public void testCreatefingSummaryQueryWithPageNumberOne() {
        SummaryRequest request = new SummaryRequest.SummaryRequestBuilder()
                .withPage(1)
                .build();
        String summaryQuery = QueryGenerator.createfindSummaryQuery(request, 1);
        assertEquals(FIND_SUMMARY_SQL_PREFIX +
                " GROUP BY c.c_id  OFFSET 0 LIMIT 1", summaryQuery);
    }

    @Test
    public void testCreatefingSummaryQueryWithFilterByStatus() {
        SummaryRequest request = new SummaryRequest.SummaryRequestBuilder()
                .withStatus(Status.ACTIVE)
                .build();
        String summaryQuery = QueryGenerator.createfindSummaryQuery(request, 1);
        assertEquals(FIND_SUMMARY_SQL_PREFIX +
                " WHERE c.c_status_id = :filterStatus GROUP BY c.c_id  OFFSET 0 LIMIT 1", summaryQuery);
    }

    @Test
    public void testCreatefingSummaryQueryWithSortByNameByAsc() {
        SummaryRequest request = new SummaryRequest.SummaryRequestBuilder()
                .withSort(SortField.NAME, SortDirection.ASC)
                .build();
        String summaryQuery = QueryGenerator.createfindSummaryQuery(request, 1);
        assertEquals(FIND_SUMMARY_SQL_PREFIX +
                " GROUP BY c.c_id  ORDER BY c.c_name ASC OFFSET 0 LIMIT 1", summaryQuery);
    }

    @Test
    public void testCreatefingSummaryQueryWithAllParamsInOneQuery() {
        SummaryRequest request = new SummaryRequest.SummaryRequestBuilder()
                .withPage(1)
                .withStatus(Status.ACTIVE)
                .withSort(SortField.NAME, SortDirection.ASC)
                .build();
        String summaryQuery = QueryGenerator.createfindSummaryQuery(request, 1);
        assertEquals(FIND_SUMMARY_SQL_PREFIX +
                " WHERE c.c_status_id = :filterStatus GROUP BY c.c_id  ORDER BY c.c_name ASC OFFSET 0 LIMIT 1", summaryQuery);
    }
}