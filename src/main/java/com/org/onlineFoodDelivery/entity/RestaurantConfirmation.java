package com.org.onlineFoodDelivery.entity;

public enum RestaurantConfirmation {
    ACCEPTED("Accepted"), PENDING("Pending");
    private String confirmation;

    RestaurantConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getStatus() {
        return this.confirmation;
    }
}
