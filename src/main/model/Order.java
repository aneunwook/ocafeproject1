package model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<MenuItem> itemList;    // list of items in order
    private int total;                  // total value of items in itemList in dollars

    // constructs an order with an empty itemList and zero total
    public Order() {
        itemList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds item to the itemList and adds the price of the item to total
    public void addItem(MenuItem item) {
        itemList.add(item);
        total += item.price;
    }

    //MODIFIES: this
    //EFFECTS: if item is in the itemList, then removes item from the itemList
    //         and subtracts its price from the total, does nothing otherwise
    public void removeItem(MenuItem item) {
        if (contains(item)) {
            itemList.remove(item);
            total -= item.price;
        }
    }

    //EFFECTS: returns the item in itemList if already there,
    //         if not, returns null
    public MenuItem getItemByName(String name) {
        if (getItemNames().contains(name)) {
            for (MenuItem i : itemList) {
                if (name == i.getName()) {
                    return i;
                }
            }
        }
        return null;
    }

    //EFFECTS: returns order total
    public int getTotal() {
        return total;
    }

    //EFFECTS: returns itemList
    public List<MenuItem> getItemList() {
        return itemList;
    }

    //EFFECTS: returns number of items in itemList
    public int size() {
        return itemList.size();
    }

    //EFFECTS: returns a list of item names
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (MenuItem i : itemList) {
            names.add(i.name);
        }
        return names;
    }

    //EFFECTS: returns a list of item prices
    public List<Integer> getItemPrices() {
        List<Integer> prices = new ArrayList<>();
        for (MenuItem i : itemList) {
            prices.add(i.price);
        }
        return prices;
    }

    //EFFECTS: returns true if itemList contains item
    public boolean contains(MenuItem item) {
        return itemList.contains(item);
    }
}
