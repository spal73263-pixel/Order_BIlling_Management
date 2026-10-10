package com.restaurant;

import java.util.ArrayList;
import java.util.List;

public class BillingService {
    private List<OrderItem> currentOrder;
    private static final double GST_RATE = 0.05;

    public BillingService() {
        this.currentOrder = new ArrayList<>();
    }

    public void addOrderItem(FoodItem item, int quantity) {
        if (quantity <= 0) return;
        
        // Prevent duplicate entries by updating quantity instead
        for (OrderItem orderItem : currentOrder) {
            if (orderItem.getFoodItem().getName().equals(item.getName())) {
                orderItem.addQuantity(quantity);
                return;
            }
        }
        currentOrder.add(new OrderItem(item, quantity));
    }

    public void removeOrderItem(int index) {
        if (index >= 0 && index < currentOrder.size()) {
            currentOrder.remove(index);
        }
    }

    public void clearOrder() {
        currentOrder.clear();
    }

    public List<OrderItem> getCurrentOrder() {
        return currentOrder;
    }

    public int getTotalItems() {
        return currentOrder.size();
    }

    public int getTotalQuantity() {
        int totalQty = 0;
        for (OrderItem item : currentOrder) {
            totalQty += item.getQuantity();
        }
        return totalQty;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (OrderItem item : currentOrder) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public double getGst() {
        return getSubtotal() * GST_RATE;
    }

    public double getGrandTotal() {
        return getSubtotal() + getGst();
    }
}
