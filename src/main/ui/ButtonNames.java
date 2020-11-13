package ui;

//SmartHome.ButtonNames
public enum ButtonNames {
    COFFEE("Coffee"),
    TEA("Tea"),
    NONCAFFEINATED("Noncaffeinated"),
    BRUNCH("Brunch"),
    DESSERT("Dessert"),
    GENERATE_REPORT("Appliance Status"),
    GO_TO_REPORT("Current Report"),
    OFF("Off"),
    ON("On"),
    SIGN_IN("Sign In"),
    CREATE_ACCOUNT("Create Account"),
    PLACE_ORDER("Place Order");

    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getValue() {
        return name;
    }
}
