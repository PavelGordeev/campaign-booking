package com.gordeev.campaignbooking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gordeev.campaignbooking.web.json.CampaignInAdSerializer;

import java.util.List;
import java.util.Objects;

public class Ad {
    private int id;
    private String name;
    private Status status;
    private List<Platform> platforms;
    private String assetUrl;

    @JsonProperty("campaignId")
    @JsonSerialize(using = CampaignInAdSerializer.class)
    private Campaign campaign;

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

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public String getAssetUrl() {
        return assetUrl;
    }

    public void setAssetUrl(String assetUrl) {
        this.assetUrl = assetUrl;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return id == ad.id &&
                Objects.equals(name, ad.name) &&
                status == ad.status &&
                Objects.equals(platforms, ad.platforms) &&
                Objects.equals(assetUrl, ad.assetUrl) &&
                Objects.equals(campaign, ad.campaign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, platforms, assetUrl, campaign);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", platforms=" + platforms +
                ", assetUrl='" + assetUrl + '\'' +
                ", campaign=" + campaign +
                '}';
    }
}