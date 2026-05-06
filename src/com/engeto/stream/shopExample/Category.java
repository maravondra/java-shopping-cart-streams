package com.engeto.stream.shopExample;

public enum Category {
    ELECTRONICS(0.21),
    FOOD(0.12),
    BOOKS(0.10),
    OTHER(0.21);

    private final double taxRate;

    Category(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
