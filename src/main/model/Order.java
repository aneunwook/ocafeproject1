package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Writable {

    private static int PreviousOrderId = 0;     // latest Order ID
    private int id;                             // this order ID
    private List<MenuItem> itemList;            // list of items in order
    private int total;                          // total value of items in itemList in dollars
//    private Date date;
//    private String address;

    // constructs an order with an empty itemList and zero total;
    // order id is unique; PreviousOrderId is incremented
    public Order() {
        itemList = new ArrayList<>();
        id = ++PreviousOrderId;
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
        for (MenuItem i : itemList) {
            if (name == i.name) {
                return i;
            }
        }
        return null;
    }


    //getters
    public int getPreviousOrderId() {
        return PreviousOrderId;
    }

    public int getId() {
        return id;
    }

    //EFFECTS: returns order total
    public int getTotal() {
        return total;
    }

    //EFFECTS: returns itemList
    public List<MenuItem> getItemList() {
        return itemList;
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

    //EFFECTS: returns number of items in itemList
    public int size() {
        return itemList.size();
    }

    //EFFECTS: returns true if itemList contains item
    public boolean contains(MenuItem item) {
        return itemList.contains(item);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);             //!!!
        json.put("itemList", itemListToJson());
        json.put("total", total);
        return json;
    }

    // JsonSerializationDemo.model.WorkRoom
    // EFFECTS: returns options in this Dish as a JSON array
    private JSONArray itemListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MenuItem item : itemList) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }
}
