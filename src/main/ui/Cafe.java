package ui;

import model.AdditionalOptions;
import model.Beverage;
import model.Dish;
import model.Order;

import java.util.ArrayList;
import java.util.List;

public class Cafe {

    //menu categories
    protected List<Beverage> coffee = new ArrayList<>();
    protected List<Beverage> tea = new ArrayList<>();
    protected List<Beverage> nonCaffeinated = new ArrayList<>();
    protected List<Dish> brunch = new ArrayList<>();
    protected List<Dish> dessert = new ArrayList<>();
    protected List<AdditionalOptions> additionalOptions = new ArrayList<>();

    //order
    protected Order order;

    //constructs the cafe menu, initializes new Order object, calls Kiosk with this cafe
    public Cafe() {
        loadCoffee();
        loadTea();
        loadBrunch();
        loadDessert();
        loadAdditionalOptions();
        order = new Order();
        Kiosk kiosk = new Kiosk(this);
    }

    //initializes and adds beverages to coffee category
    public void loadCoffee() {
        coffee.add(new Beverage("Espresso", 3, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Americano", 3, Beverage.REGULAR, Beverage.REGULAR));
        coffee.add(new Beverage("Macchiato", 3, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        coffee.add(new Beverage("Latte", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Iced Coffee", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Cold Brew", 4, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("Hot Cocoa", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
    }

    //initializes and adds beverages to tea category
    public void loadTea() {
        tea.add(new Beverage("Matcha Latte", 6, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Hojicha Latte", 6, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("London Fog", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Chai Latte", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Oolong Milk Tea", 5, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        tea.add(new Beverage("Genmaicha", 4, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Sencha", 5, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("Black Tea", 5, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
    }

    //initializes and adds beverages to non-caffeinated category
    public void loadNonCaffeinated() {
        nonCaffeinated.add(new Beverage(
                "Honey Ginger Tea",
                5,
                Beverage.NOT_CUSTOMIZABLE,
                Beverage.REGULAR));
        nonCaffeinated.add(new Beverage("Fruit Tea", 6, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        nonCaffeinated.add(new Beverage(
                "Kumquat Chrysanthemum Tea",
                6,
                Beverage.NOT_CUSTOMIZABLE,
                Beverage.NOT_CUSTOMIZABLE));
        nonCaffeinated.add(new Beverage(
                "Hibiscus Kombucha",
                6, Beverage.NOT_CUSTOMIZABLE,
                Beverage.NOT_CUSTOMIZABLE));
        nonCaffeinated.add(new Beverage(
                "Mango Kale Smoothie",
                6,
                Beverage.REGULAR,
                Beverage.NOT_CUSTOMIZABLE));
    }

    //initializes and adds dishes to brunch category
    public void loadBrunch() {

        AdditionalOptions salmon = new AdditionalOptions("Smoked Salmon", 4);
        AdditionalOptions potatoes = new AdditionalOptions("Crispy Potatoes", 0);
        AdditionalOptions greens = new AdditionalOptions("Mixed Greens", 0);
        AdditionalOptions vegetarian = new AdditionalOptions("Vegetarian Option", 0);
        AdditionalOptions pork = new AdditionalOptions("Fried Pork Cutlet", 4);
        AdditionalOptions prawns = new AdditionalOptions("Fried Prawns", 3);
        AdditionalOptions chicken = new AdditionalOptions("Chicken Breast 4oz", 5);
        AdditionalOptions tuna = new AdditionalOptions("Albacore Tuna 3oz", 5);
        AdditionalOptions shrimp = new AdditionalOptions("Grilled Shrimp 4 pieces", 5);
        AdditionalOptions almond = new AdditionalOptions("Almond Croissant", 1);

        brunch.add(new Dish("Thai Green Curry Seafood Linguine", 16));

        Dish eb = new Dish("Eggs Benny", 12);
        brunch.add(eb);
        eb.addSideToAddOns(additionalOptions.get(0));
        eb.addSideToAddOns(salmon);
        eb.addSideToAddOns(potatoes);
        eb.addSideToAddOns(greens);

        Dish bo = new Dish("Beef Omurice", 14);
        bo.addSideToAddOns(vegetarian);
        brunch.add(bo);

        brunch.add(new Dish("Butternut Squash Risotto", 14));

        Dish jcr = new Dish("Japanese Curry Rice", 12);
        jcr.addSideToAddOns(pork);
        jcr.addSideToAddOns(prawns);
        brunch.add(jcr);

        Dish dcs = new Dish("Dutch Cheese Sandwich", 11);
        dcs.addSideToAddOns(greens);
        brunch.add(dcs);

        Dish bc = new Dish("Butter Croissant", 3);
        bc.addSideToAddOns(almond);
        brunch.add(bc);

        Dish ss = new Dish("Spring Salad", 11);
        brunch.add(ss);
    }

    //initializes and adds dishes to dessert category
    public void loadDessert() {
        dessert.add(new Dish("Kinako Mochi", 5));
        dessert.add(new Dish("Raspberry Pistachio Cream Tart", 6));
        dessert.add(new Dish("Banana Cream Pie", 5));
        dessert.add(new Dish("Sweet Potato Crepe", 6));
        dessert.add(new Dish("Hojicha Parfait", 6));
        dessert.add(new Dish("Chestnut Cake", 5));
        dessert.add(new Dish("Tofu Ice Cream", 4));
    }

    public void loadAdditionalOptions() {
        AdditionalOptions bacon = new AdditionalOptions("Canadian Bacon", 3);
        additionalOptions.add(bacon);
    }
}
