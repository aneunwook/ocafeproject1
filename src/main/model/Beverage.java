package model;

import org.json.JSONObject;

// Represents a menu item of type beverage
public class Beverage extends MenuItem {

    private int size;               // regular or large
    private int temperature;        // hot or iced

    public static final int NOT_CUSTOMIZABLE = -1;      // size or temperature is not customizable
    public static final int REGULAR = 0;                // size-REGULAR, temperature-HOT
    public static final int UPGRADE = 1;                // size-LARGE, temperature-ICED

    public static final Double UPGRADE_PRICE = 0.65;

    //REQUIRES: a beverage cannot be created with a customizable size AND customizable temperature,
    //          only one can be customized or both cannot be customized
    //EFFECTS: constructs a beverage with name, price, and customization options
    public Beverage(String name, Double price, Integer size, Integer temperature) {
        super(name, price);
        this.size = size;
        this.temperature = temperature;
    }

    //REQUIRES: s is one of REGULAR, LARGE, or NOT_CUSTOMIZABLE
    //MODIFIES: this
    //EFFECTS: if size is set to SIZE_NOT_CUSTOMIZABLE or size and s have the same value,
    //         does nothing and returns false.
    //         otherwise sets size to parameter and returns true
    //         if s is LARGE, adds UPGRADE_PRICE to price, if s is REGULAR, subtracts UPGRADE_PRICE from price
    public boolean setSize(Integer s) {
        if (isSizeCustomizable() && (size != s)) {
            size = s;
            updatePrice(s);
            return true;
        }
        return false;
    }

    //REQUIRES: t is one of HOT, COLD, or NOT_CUSTOMIZABLE
    //MODIFIES: this
    //EFFECTS: if temperature is set to TEMPERATURE_NOT_CUSTOMIZABLE or temperature and t have the same value,
    //         does nothing and returns false
    //         otherwise sets temperature to parameter and returns true
    //         if t is COLD, adds UPGRADE_PRICE to price, if t is HOT subtracts UPGRADE_PRICE from price
    public boolean setTemperature(Integer t) {
        if (isTemperatureCustomizable() && (temperature != t)) {
            temperature = t;
            updatePrice(t);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (!isSizeCustomizable() && !isTemperatureCustomizable()) {
            return super.toString();
        } else if (isSizeCustomizable()) {
            if (size == 0) {
                return toStringCustomizable("Regular");
            } else {
                return toStringCustomizable("Large");       //add test case!!!
            }
        } else {
            if (temperature == 0) {
                return toStringCustomizable("Hot");         //add test case!!!
            } else {
                return toStringCustomizable("Iced");
            }
        }
    }

    //getters
    //EFFECTS: returns drink size or NOT_CUSTOMIZABLE
    public int getSize() {
        return size;
    }

    //EFFECTS: returns drink temperature or NOT_CUSTOMIZABLE
    public int getTemperature() {
        return temperature;
    }

    //EFFECTS: returns true if size is customizable, false otherwise
    public boolean isSizeCustomizable() {
        return size != NOT_CUSTOMIZABLE;
    }

    //EFFECTS: returns true if temperature is customizable, false otherwise
    public boolean isTemperatureCustomizable() {
        return temperature != NOT_CUSTOMIZABLE;
    }

    //MODIFIES: this
    //EFFECTS: if c is REGULAR, adds UPGRADE_PRICE to price, if c is UPGRADE, subtracts UPGRADE_PRICE from price
    private void updatePrice(Integer c) {
        if (c == UPGRADE) {
            price += UPGRADE_PRICE;
        } else if (c == REGULAR) {
            price -= UPGRADE_PRICE;
        }
    }

    //EFFECTS: returns customizable beverages in string format
    private String toStringCustomizable(String s) {
        return super.toString() + "\n\n   " + s;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("type", "beverage");
        json.put("size", size);
        json.put("temperature", temperature);
        return json;
    }
}