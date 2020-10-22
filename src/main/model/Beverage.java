package model;

import org.json.JSONObject;

public class Beverage extends MenuItem {

    private int size;               // regular or large
    private int temperature;        // hot or cold

    public static final int NOT_CUSTOMIZABLE = -1;      // size or temperature is not customizable
    public static final int REGULAR = 0;                // size-REGULAR, temperature-HOT
    public static final int EXTRA = 1;                  // size-LARGE, temperature-ICED

    public static final int UPGRADE_PRICE = 1;

    // constructs a drink with name, price
    public Beverage(String name, Integer price, Integer size, Integer temperature) {
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
        if (size != NOT_CUSTOMIZABLE) {
            return true;
        }
        return false;
    }

    //EFFECTS: returns true if temperature is customizable, false otherwise
    public boolean isTemperatureCustomizable() {
        if (temperature != NOT_CUSTOMIZABLE) {
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: if i is REGULAR, adds UPGRADE_PRICE to price, if i is EXTRA subtracts UPGRADE_PRICE from price
    private void updatePrice(Integer i) {
        if (i == EXTRA) {
            price += UPGRADE_PRICE;
        } else if (i == REGULAR) {
            price -= UPGRADE_PRICE;
        }
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