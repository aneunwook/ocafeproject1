package ui;

import com.sun.org.apache.regexp.internal.RE;
import model.Beverage;
import model.Dish;

import java.util.ArrayList;
import java.util.List;

public class CafeMenu {

    //menu categories
    private List<Beverage> coffee = new ArrayList<>();
    private List<Beverage> tea = new ArrayList<>();
    private List<Beverage> nonCaffeinated = new ArrayList<>();
    private List<Dish> brunch = new ArrayList<>();
    private List<Dish> dessert = new ArrayList<>();

    //constructs a CafeMenu with specific menu items in each category
    public CafeMenu() {
        //stub
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
        brunch.add(new Dish("Thai Green Curry Seafood Linguine", 16));
        brunch.add(new Dish("Eggs Benny", 12));
        brunch.add(new Dish("Beef Omurice", 14));
        brunch.add(new Dish("Butternut Squash Risotto", 14));
        brunch.add(new Dish("Japanese Curry Rice", 13));
        brunch.add(new Dish("Dutch Cheese Sandwich", 11));
        brunch.add(new Dish("Butter Croissant", 3));
        brunch.add(new Dish("Spring Salad", 11));
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

}
