package model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<MenuItem> itemList;    // list of items in order
    private int total;                  // total value of items in itemList in dollars

    // constructs an order with an empty itemList and zero total
    public Order() {
        itemList = new ArrayList<>();
        total = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds item to the itemList and adds the price of the item to total
    public void addItem(MenuItem item) {
        // stub
    }

    //MODIFIES: this
    //EFFECTS: if item is in the itemList, then removes item from the itemList
    //         and subtracts its price from the total, does nothing otherwise
    public void removeItem(MenuItem item) {
        // stub
    }

    //EFFECTS: returns order total
    public int getTotal() {
        return 0;
    }

    //EFFECTS: returns itemList
    public List<MenuItem> getItemList() {
        return itemList;
    }

    //EFFECTS: returns true if itemList contains item
    public boolean contains(MenuItem item) {
        return false;
    }
}
