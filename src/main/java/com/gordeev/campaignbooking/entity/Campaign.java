package com.gordeev.campaignbooking.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Campaign {
    private int id;
    private String name;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Ad> ads;

    public Campaign() {
    }

    public Campaign(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return id == campaign.id &&
                Objects.equals(name, campaign.name) &&
                status == campaign.status &&
                Objects.equals(startDate, campaign.startDate) &&
                Objects.equals(endDate, campaign.endDate) &&
                Objects.equals(ads, campaign.ads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, startDate, endDate, ads);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", ads=" + ads +
                '}';
    }
}