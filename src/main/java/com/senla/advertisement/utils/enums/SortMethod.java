package com.senla.advertisement.utils.enums;

public enum SortMethod {

    NULL("null"),
    NAME("name"),
    STATUS("status"),
    CREATION_DATE("created"),
    USER("user");

    private final String name;

    SortMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
