package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class MenuLoader {

    //menu categories
    protected List<Beverage> coffee = new ArrayList<>();
    protected List<Beverage> tea = new ArrayList<>();
    protected List<Beverage> nonCaffeinated = new ArrayList<>();
    protected List<Dish> brunch = new ArrayList<>();
    protected List<Dish> dessert = new ArrayList<>();
    protected List<AdditionalOptions> additionalOptions = new ArrayList<>();

    //constructs the cafe menu, initializes new Order object, calls Kiosk with this cafe
    public MenuLoader() {
        loadCoffee();
        loadTea();
        loadNonCaffeinated();
        loadAdditionalOptions();
        loadBrunch();
        loadDessert();

    }

    //MODIFIES: this
    //EFFECTS: initializes and adds beverages to coffee category
    public void loadCoffee() {
        coffee.add(new Beverage("Espresso", 3.45, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Americano", 3.45, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        coffee.add(new Beverage("Macchiato", 3.65, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        coffee.add(new Beverage("Latte", 4.75, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Iced Coffee", 4.75, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Cold Brew", 4.25, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
//        coffee.add(new Beverage("Hot Cocoa", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds beverages to tea category
    public void loadTea() {
        tea.add(new Beverage("Matcha Latte", 5.95, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        tea.add(new Beverage("Hojicha Latte", 5.95, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        tea.add(new Beverage("London Fog", 4.95, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Chai Latte", 4.95, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
//        tea.add(new Beverage("Oolong Milk Tea", 5, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
//        tea.add(new Beverage("Genmaicha", 4, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Sencha", 3.95, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Black Tea", 3.25, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds beverages to non-caffeinated category
    public void loadNonCaffeinated() {
        nonCaffeinated.add(new Beverage(
                "Honey Ginger Tea",
                4.25,
                Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        nonCaffeinated.add(new Beverage(
                "Fruit Tea",
                4.65,
                Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        nonCaffeinated.add(new Beverage(
                "Kumquat Chrysanthemum Tea",
                4.65,
                Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        nonCaffeinated.add(new Beverage(
                "Hibiscus Kombucha",
                3.95, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        nonCaffeinated.add(new Beverage(
                "Mango Kale Smoothie",
                3.95,
                Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds dishes to brunch category
    public void loadBrunch() {
        brunch.add(new Dish("Thai Green Curry Seafood Linguine", 15.95));
        Dish eb = new Dish("Eggs Benedict", 12.95);
        eb.addSideToOptions(additionalOptions.get(0));
        eb.addSideToOptions(additionalOptions.get(1));
//        eb.addSideToAddOns(additionalOptions.get(2));
//        eb.addSideToAddOns(additionalOptions.get(3));
        brunch.add(eb);
        Dish bo = new Dish("Omurice", 11.95);
        bo.addSideToOptions(additionalOptions.get(4));
        brunch.add(bo);
        brunch.add(new Dish("Butternut Squash Risotto", 13.95));
        Dish jcr = new Dish("Japanese Curry Rice", 11.95);
        jcr.addSideToOptions(additionalOptions.get(5));
        jcr.addSideToOptions(additionalOptions.get(6));
        brunch.add(jcr);
        Dish dcs = new Dish("Dutch Cheese Sandwich", 10.95);
        brunch.add(dcs);
        Dish ss = new Dish("Spring Salad", 10.95);
        ss.addSideToOptions(additionalOptions.get(7));
        ss.addSideToOptions(additionalOptions.get(8));
        ss.addSideToOptions(additionalOptions.get(9));
        brunch.add(ss);
        Dish bc = new Dish("Butter Croissant", 3.45);
        bc.addSideToOptions(additionalOptions.get(10));
        brunch.add(bc);
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds dishes to dessert category
    public void loadDessert() {
        dessert.add(new Dish("Kinako Mochi", 4.65));
        dessert.add(new Dish("Raspberry Pistachio Cream Tart", 5.95));
        dessert.add(new Dish("Banana Cream Pie", 5.95));
        dessert.add(new Dish("Sweet Potato Crepe", 4.65));
        dessert.add(new Dish("Hojicha Parfait", 4.65));
        dessert.add(new Dish("Chestnut Cake", 5.25));
        dessert.add(new Dish("Tofu Ice Cream", 3.75));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds dishes to additional options category
    public void loadAdditionalOptions() {
        AdditionalOptions bacon = new AdditionalOptions("Canadian Bacon", 3.00);
        AdditionalOptions salmon = new AdditionalOptions("Smoked Salmon", 4.00);
        AdditionalOptions potatoes = new AdditionalOptions("Crispy Potatoes", 0.00);
        AdditionalOptions greens = new AdditionalOptions("Mixed Greens", 0.00);
        AdditionalOptions vegetarian = new AdditionalOptions("Beef", 4.00);
        AdditionalOptions pork = new AdditionalOptions("Fried Pork Cutlet", 4.00);
        AdditionalOptions prawns = new AdditionalOptions("Assorted Tempura", 3.00);
        AdditionalOptions chicken = new AdditionalOptions("Chicken Breast", 4.00);
        AdditionalOptions tuna = new AdditionalOptions("Albacore Tuna", 5.00);
        AdditionalOptions shrimp = new AdditionalOptions("Grilled Shrimp", 5.00);
        AdditionalOptions almond = new AdditionalOptions("Almond Croissant", 1.00);

        additionalOptions.add(bacon);
        additionalOptions.add(salmon);
        additionalOptions.add(potatoes);
        additionalOptions.add(greens);
        additionalOptions.add(vegetarian);
        additionalOptions.add(pork);
        additionalOptions.add(prawns);
        additionalOptions.add(chicken);
        additionalOptions.add(tuna);
        additionalOptions.add(shrimp);
        additionalOptions.add(almond);
    }

    //getters
    public List<Beverage> getCoffee() {
        return coffee;
    }

    public List<Beverage> getTea() {
        return tea;
    }

    public List<Beverage> getNonCaffeinated() {
        return nonCaffeinated;
    }

    public List<Dish> getBrunch() {
        return brunch;
    }

    public List<Dish> getDessert() {
        return dessert;
    }
}
