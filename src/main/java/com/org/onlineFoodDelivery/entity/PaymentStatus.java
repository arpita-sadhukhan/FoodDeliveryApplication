package com.org.onlineFoodDelivery.entity;

public enum PaymentStatus {
    SUCCESS("S"), FAILED("F");
    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
