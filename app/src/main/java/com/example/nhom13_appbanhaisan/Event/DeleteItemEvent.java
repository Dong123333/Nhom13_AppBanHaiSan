package com.example.nhom13_appbanhaisan.Event;

public class DeleteItemEvent {
    private String itemName;

    public DeleteItemEvent(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
