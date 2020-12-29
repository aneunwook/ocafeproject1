package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

// Represents a general item in a menu with a name and price
// that can be displayed on a menu and added to an order
public abstract class MenuItem implements Writable {

    protected String name;
    protected Double price;        // price in dollars
    protected int quantity;
    protected Image image;

    // constructs a MenuItem with a name and price
    public MenuItem(String name, Double price) {
        this.name = name;
        this.price = price;
        quantity = 1;
        image = new ImageIcon("./data/images/" + name + ".jpg").getImage();
    }

    //REQUIRES: q >= 1
    //MODIFIES: this
    //EFFECTS: sets quantity to parameter and multiplies price by quantity
    public void setQuantity(Integer q) {
        double singlePrice = price / quantity;
        quantity = q;
        price = singlePrice * quantity;
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

    public Image getImage() {
        return image;
    }

    //EFFECTS: returns menu item in string format
    public String toString() {
        return String.format("%-60s $%.2f", name, price);
    }


    // JsonSerializationDemo.model.Thingy
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name) && Objects.equals(image, menuItem.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image);
    }
}
