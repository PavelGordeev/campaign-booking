package com.gordeev.campaignbooking.request;

public enum SortField {
    NAME("name"),
    STATUS("status"),
    ADS_COUNT("ads_count");

    private final String name;

    SortField(String name) {
        this.name = name;
    }

    public static SortField getByName(String name) {
        for (SortField value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Field with name: " + name + " not allowed");
    }
}