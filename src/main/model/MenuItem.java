package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a general item in a menu with a name and price
public abstract class MenuItem implements Writable {

    protected String name;
    protected Integer price;        // price in dollars

    // constructs a MenuItem with a name and price
    public MenuItem(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    //EFFECTS: returns item name
    public String getName() {
        return name;
    }

    //EFFECTS: returns item price
    public int getPrice() {
        return price;
    }

    //EFFECTS: returns menu item in string format
    public String toString() {
        return String.format("\n$" + price + "\t\t" + name);
    }

    // JsonSerializationDemo.model.Thingy
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }
}
