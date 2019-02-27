package com.gordeev.campaignbooking.entity;

public class CampaignSummary {
    private int id;
    private String name;
    private Status status;
    private int advertCount;

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

    public int getAdvertCount() {
        return advertCount;
    }

    public void setAdvertCount(int advertCount) {
        this.advertCount = advertCount;
    }

    @Override
    public String toString() {
        return "CampaignSummary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", advertCount=" + advertCount +
                '}';
    }
}