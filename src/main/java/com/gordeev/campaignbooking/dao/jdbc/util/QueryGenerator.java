package com.gordeev.campaignbooking.dao.jdbc.util;

import com.gordeev.campaignbooking.request.SortField;
import com.gordeev.campaignbooking.request.SummaryRequest;

import java.util.HashMap;
import java.util.Map;

public class QueryGenerator {
    private static final String FIND_SUMMARY_SQL_PREFIX = "SELECT c.c_id, c.c_name, c.c_status_id, " +
            "count(ads.ad_id) as ads_count FROM campaings c LEFT JOIN ads ON  c.c_id = ads.ad_campaign_id ";

    private static final Map<SortField, String> SORT_FIELD_TO_COLUMN_NAME_MAP = new HashMap<SortField, String>() {
        {
            put(SortField.NAME, "c.c_name");
            put(SortField.STATUS, "c.c_status_id");
            put(SortField.ADS_COUNT, "ads_count");
        }
    };

    public static String createfindSummaryQuery(SummaryRequest summaryRequest, int countOnPage) {
        StringBuilder query = new StringBuilder(FIND_SUMMARY_SQL_PREFIX);

        if (isFilterPresent(summaryRequest)) {
            query.append(" WHERE ");
        }

        if (isFilteredByName(summaryRequest)) {
            query.append("c.c_name like '%:filterName%'");
        }

        if (isFilteredByNameAndStatus(summaryRequest)) {
            query.append(" AND ");
        }

        if (isFilteredByStatus(summaryRequest)) {
            query.append("c.c_status_id = :filterStatus");
        }

        query.append(" GROUP BY c.c_id ");

        if (summaryRequest.getSortField() != null && summaryRequest.getSortDirection() != null) {
            query.append(" ORDER BY ");
            query.append(SORT_FIELD_TO_COLUMN_NAME_MAP.get(summaryRequest.getSortField()));
            query.append(" ");
            query.append(summaryRequest.getSortDirection());
        }

        Integer page = summaryRequest.getPage();
        if (page != null) {
            query.append(" OFFSET ");
            query.append((page - 1) * countOnPage);
            query.append(" LIMIT ");
            query.append(countOnPage);
        }

        return query.toString();
    }

    private static boolean isFilterPresent(SummaryRequest summaryRequest) {
        return isFilteredByName(summaryRequest) || isFilteredByStatus(summaryRequest);
    }

    private static boolean isFilteredByNameAndStatus(SummaryRequest summaryRequest) {
        return isFilteredByName(summaryRequest) && isFilteredByStatus(summaryRequest);
    }

    private static boolean isFilteredByName(SummaryRequest summaryRequest) {
        return summaryRequest.getFilterName() != null;
    }

    private static boolean isFilteredByStatus(SummaryRequest summaryRequest) {
        return summaryRequest.getFilterStatus() != null;
    }
}