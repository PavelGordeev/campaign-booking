package com.gordeev.campaignbooking.request;

import com.gordeev.campaignbooking.entity.Status;

public class SummaryRequest {
    private SortField sortField;
    private SortDirection sortDirection;
    private String filterName;
    private Status filterStatus;
    private Integer page;

    public SortField getSortField() {
        return sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public String getFilterName() {
        return filterName;
    }

    public Status getFilterStatus() {
        return filterStatus;
    }

    public Integer getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "SummaryRequest{" +
                "sortField=" + sortField +
                ", sortDirection=" + sortDirection +
                ", filterName='" + filterName + '\'' +
                ", filterStatus=" + filterStatus +
                ", page=" + page +
                '}';
    }

    private SummaryRequest(SortField sortField, SortDirection sortDirection, String filterName, Status filterStatus, Integer page) {
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.filterName = filterName;
        this.filterStatus = filterStatus;
        this.page = page;
    }

    public static class SummaryRequestBuilder {
        private SortField sortField;
        private SortDirection sortDirection;
        private String filterName;
        private Status filterStatus;
        private Integer page = 1;

        public SummaryRequestBuilder withSort(SortField sortField, SortDirection sortDirection) {
            this.sortField = sortField;
            this.sortDirection = sortDirection;
            return this;
        }

        public SummaryRequestBuilder withName(String filterName) {
            this.filterName = filterName;
            return this;
        }

        public SummaryRequestBuilder withStatus(Status filterStatus) {
            this.filterStatus = filterStatus;
            return this;
        }

        public SummaryRequestBuilder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public SummaryRequest build() {
            return new SummaryRequest(sortField, sortDirection, filterName, filterStatus, page);
        }

    }
}