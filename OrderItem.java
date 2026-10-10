package com.restaurant;

public class OrderItem {
    private FoodItem foodItem;
    private int quantity;

    public OrderItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int additionalQuantity) {
        this.quantity += additionalQuantity;
    }

    public double getTotalPrice() {
        return foodItem.getPrice() * quantity;
    }
}
