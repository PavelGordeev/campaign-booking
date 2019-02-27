package com.gordeev.campaignbooking.request;

public enum SortDirection {
    ASC("ASC"), DESC("DESC");

    private final String name;

    SortDirection(String name) {
        this.name = name;
    }

    public static SortDirection getByName(String name) {
        for (SortDirection value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No sort direction for name: " + name + " found");
    }
}