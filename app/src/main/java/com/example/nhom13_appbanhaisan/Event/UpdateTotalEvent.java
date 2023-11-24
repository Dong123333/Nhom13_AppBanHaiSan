package com.example.nhom13_appbanhaisan.Event;

public class UpdateTotalEvent {
    private int itemPrice;

    public UpdateTotalEvent(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemPrice() {
        return itemPrice;
    }
}
