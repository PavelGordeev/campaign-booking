package com.gordeev.campaignbooking.entity;

public enum Status {

    PLANNED(0), ACTIVE(1), PAUSED(2), FINISHED(3);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Status getById(int id) {
        for (Status status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("No status with id: " + id + " found");
    }
}
