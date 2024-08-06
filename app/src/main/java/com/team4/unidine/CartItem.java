package com.team4.unidine;

public class CartItem {
    private int imageResId;
    private String name;
    private double price;
    private int quantity;

    public CartItem(int imageResId, String name, double price, int quantity) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
