package model;

import java.util.ArrayList;
import java.util.List;

public class Dish extends MenuItem {

    private List<AdditionalOptions> options;          // add-ons available for this dish //vegetarian????
    private AdditionalOptions selected;               // add-on selected

    // constructs an dish with empty options
    public Dish(String name, Integer price) {
        super(name, price);
        options = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds side to options
    public void addSideToOptions(AdditionalOptions side) {
        options.add(side);
    }

    //MODIFIES: this
    //EFFECTS: if an add-on is already selected, unselects add-on.
    //         if side is in options, then sets selected to side,
    //         adds the price of side to the price of this entree and returns true,
    //         otherwise do nothing and return false
    public boolean selectAddOn(AdditionalOptions side) {
        unselectAddOn();
        if (options.contains(side)) {
            selected = side;
            price += side.price;
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: if an add-on has been selected, subtract the add-on price from the entree price,
    //         reset to null and return true,
    //         otherwise do nothing and return false
    public boolean unselectAddOn() {
        if (hasSelected()) {
            price -= selected.price;
            selected = null;
            return true;
        }
        return false;
    }

    //EFFECTS: returns options
    public List<AdditionalOptions> getOptions() {
        return options;
    }

    //EFFECTS: returns the selected addOn, if none selected returns null
    public AdditionalOptions getSelected() {
        return selected;
    }

    //EFFECTS: returns true if selected is not null, false otherwise
    private boolean hasSelected() {
        return !(selected == null);
    }
}
