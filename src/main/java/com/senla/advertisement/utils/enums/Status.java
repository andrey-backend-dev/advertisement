package com.senla.advertisement.utils.enums;

public enum Status {

    BLOCKED("BLOCKED"),
    ALIVE("ALIVE");

    private final String name;

    private Status(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
