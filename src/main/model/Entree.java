package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entree extends MenuItem {

    private List<Side> addOnOptions;      // add-ons available for this dish
    private Side selected;                // add-on selected

    public Entree(String name, Integer price) {
        super(name, price);
        addOnOptions = new ArrayList<>();
        selected = null;
    }

    //MODIFIES: this
    //EFFECTS: adds side to addOnOptions
    public void addSideToAddOns(Side side) {
        //stub
    }

    //EFFECTS: if side is in addOnOptions, then sets selected to side,
    //         adds the price of side to the price of this entree and returns true,
    //         otherwise do nothing and return false
    public boolean selectAddOn(Side side) {
        return false;
    }

    //EFFECTS: if an add-on has been selected, reset to null and return true,
    //         otherwise do nothing and return false
    public boolean unselectAddOn() {
        return false;
    }

    //EFFECTS: returns addOnOptions
    public List<Side> getAddOnOptions() {
        return Collections.emptyList();
    }

    //EFFECTS: returns the selected addOn, if none selected !!!
    public Side getSelected() {
        return null;
    }

    //EFFECTS: returns true if selected is not null, false otherwise
    private boolean hasSelected() {
        return false;
    }
}
