package model;

import exceptions.IllegalQuantityException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a general item in a menu with a name and price
// that can be displayed on a menu and added to an order
public abstract class MenuItem implements Writable {

    protected String name;
    protected Double price;        // price in dollars
    protected int quantity;

    // constructs a MenuItem with a name and price
    public MenuItem(String name, Double price) {
        this.name = name;
        this.price = price;
        quantity = 1;
    }

    //REQUIRES: q >= 1
    //MODIFIES: this
    //EFFECTS: sets quantity to parameter and multiplies price by quantity
    //         throws IllegalQuantityException
    public void setQuantity(Integer q) {
        quantity = q;
        price = price * q;
    }

    //EFFECTS: returns item quantity
    public int getQuantity() {
        return quantity;
    }

    //EFFECTS: returns item name
    public String getName() {
        return name;
    }

    //EFFECTS: returns item price
    public Double getPrice() {
        return price;
    }

    //EFFECTS: returns menu item in string format
    public String toString() {
        return String.format("%-20s $%.2f", name, price);
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
