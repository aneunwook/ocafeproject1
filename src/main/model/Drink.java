package model;

public class Drink extends MenuItem {

    private int size;               // regular or large
    private int temperature;        // hot or cold

    public static final int SIZE_NOT_CUSTOMIZABLE = -1;
    public static final int REGULAR = 0;
    public static final int LARGE = 1;

    public static final int TEMPERATURE_NOT_CUSTOMIZABLE = -1;
    public static final int COLD = 0;
    public static final int HOT = 1;

    // constructs a drink with name, price
    public Drink(String name, Integer price) {
        super(name, price);
        size = REGULAR;
        temperature = COLD;
    }

    //EFFECTS: returns drink size or SIZE_NOT_CUSTOMIZABLE
    public int getSize() {
        return size;
    }

    //EFFECTS: returns drink temperature or TEMPERATURE_NOT_CUSTOMIZABLE
    public int getTemperature() {
        return temperature;
    }

    //REQUIRES: s is one of REGULAR, LARGE, or SIZE_NOT_CUSTOMIZABLE
    //EFFECTS: sets drink size to parameter
    public void setSize(Integer s) {
        size = s;
    }

    //REQUIRES: t is one of HOT, COLD, or TEMPERATURE_NOT_CUSTOMIZABLE
    //EFFECTS: sets temperature to parameter
    public void setTemperature(Integer t) {
        temperature = t;
    }

    //EFFECTS: returns true if size is customizable, false otherwise
    public boolean isSizeCustomizable() {
        if (size != -1) {
            return true;
        }
        return false;
    }

    //EFFECTS: returns true if temperature is customizable, false otherwise
    public boolean isTemperatureCustomizable() {
        if (temperature != -1) {
            return true;
        }
        return false;
    }
}
