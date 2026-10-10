package com.restaurant;

public class FoodItem {
    private int itemNumber;
    private String name;
    private String category;
    private double price;

    public FoodItem(int itemNumber, String name, String category, double price) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
