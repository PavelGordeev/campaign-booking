package com.gordeev.campaignbooking.entity;

public enum Platform {

    WEB(0), ANDROID(1), IOS(2);

    private final int id;

    Platform(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Platform getById(int id) {
        for (Platform platform : values()) {
            if (platform.id == id) {
                return platform;
            }
        }
        throw new IllegalArgumentException("No platform with id: " + id + " found");
    }
}