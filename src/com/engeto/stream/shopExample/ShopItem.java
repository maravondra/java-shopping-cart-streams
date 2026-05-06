package com.engeto.stream.shopExample;

public record ShopItem(
        String name,
        Category category,
        double unitPrice,
        int quantity
) {

    // Record může mít i metody – ideální pro logiku, která se týká přímo dat v něm
    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    public double getPriceWithTax(double taxRate) {
        return getTotalPrice() * (1 + taxRate);
    }
}
