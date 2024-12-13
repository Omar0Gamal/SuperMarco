package com.supermacro;

public enum empType {

    NONE(0),
    ADMIN(1),
    MARKETING_EMPLOYEE(2),
    INVENTORY_EMPLOYEE(3),
    SALES_EMPLOYEE(4);

    private final int value;

    // Constructor to initialize the value for each constant
    empType(int value) {
        this.value = value;
    }

    // Getter method to retrieve the value
    public int getValue() {
        return value;
    }
}
