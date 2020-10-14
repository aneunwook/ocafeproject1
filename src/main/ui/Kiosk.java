package ui;

import model.Beverage;
import model.MenuItem;
import model.Order;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Kiosk {

    private static final String PLACE_ORDER_COMMAND = "p";
    private static final String SIGN_IN_COMMAND = "s";
    private static final String CREATE_ACCOUNT_COMMAND = "c";
    private static final String HOME_COMMAND = "home";
    private static final String CAFE_MENU_COMMAND = "menu";
    private static final String VIEW_ORDER_COMMAND = "order";
    private static final String COFFEE_COMMAND = "coffee";
    private static final String[] coffee = {"Espresso", "Americano", "Macchiato", "Latte"};
    private static final String TEA_COMMAND = "tea";
    private static final String[] tea = {"Matcha Latte", "Hojicha Latte", "London Fog", "Chai Latte"};
    private static final String NONCAFFEINATED_COMMAND = "noncaffeinated";
    private static final String[] noncaffeinated = {"Honey Ginger Tea", "Fruit Tea", "Hibiscus Kombucha"};
    private static final String BRUNCH_COMMAND = "brunch";
    private static final String[] brunch = {"Eggs Benny", "Beef Omurice", "Butternut Squash Risotto"};
    private static final String DESSERT_COMMAND = "dessert";
    private static final String[] dessert = {"Kinako mochi", "Raspberry Pistachio Cream Tart", "Banana Cream Pie"};
    private static final String REGULAR_SIZE_COMMAND = "regular";
    private static final String LARGE_SIZE_COMMAND = "large";
    private static final String HOT_TEMP_COMMAND = "hot";
    private static final String COLD_TEMP_COMMAND = "cold";
    private static final String ADD_TO_ORDER_COMMAND = "add";
    private static final String QUIT_COMMAND = "quit";


    private Scanner input;
    private boolean runProgram;
    private Cafe cafe;
    private Order order;

    // constructor, handleUserInput, parseInput, and makePrettyString taken from FitLifeGymKiosk.ui.Kiosk
    public Kiosk(Cafe cafe) {
        input = new Scanner(System.in);
        runProgram = true;
        this.cafe = cafe;
        order = new Order();
    }

    //EFFECTS: parses user input until user quits
    public void handleUserInput() {
        System.out.println("Home Page");
        homePage();
        String s1;

        while (runProgram) {
            if (input.hasNext()) {
                s1 = input.nextLine();
                s1 = makePrettyString(s1);
                parseInputMenus(s1);
            }
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    private void parseInputMenus(String str) {
        if (str.length() > 0) {
            switch (str) {
                case PLACE_ORDER_COMMAND:
                case CAFE_MENU_COMMAND:
                    displayCafeMenu();
                    break;
                case HOME_COMMAND:
                    homePage();
                    break;
                case VIEW_ORDER_COMMAND:
                    displayOrder();
                    break;
                case COFFEE_COMMAND:
                case TEA_COMMAND:
                case NONCAFFEINATED_COMMAND:
                case BRUNCH_COMMAND:
                case DESSERT_COMMAND:
                    displayMenu(str);
                    break;
                case QUIT_COMMAND:
                    runProgram = false;
                    break;
                default:
                    parseInputItemDetails(str);
                    break;
            }
        }
    }

    // taken from AccountNotRobust.ui.TellerApp
    // displays home page
    private void homePage() {
        System.out.println("\nselect from:");
        System.out.println("\t'" + PLACE_ORDER_COMMAND + "' -> place order");
        System.out.println("\t'" + SIGN_IN_COMMAND + "' -> sign in");
        System.out.println("\t'" + CREATE_ACCOUNT_COMMAND + "' -> create account");
        System.out.println("\n enter '" + QUIT_COMMAND + "' to quit any time.");
    }

    // displays cafe menu
    private void displayCafeMenu() {
        System.out.println("\nCafe Menu");
        System.out.println("enter one of:");
        System.out.println("\t'" + COFFEE_COMMAND + "'");
        System.out.println("\t'" + TEA_COMMAND + "'");
        System.out.println("\t'" + NONCAFFEINATED_COMMAND + "'");
        System.out.println("\t'" + BRUNCH_COMMAND + "'");
        System.out.println("\t'" + DESSERT_COMMAND + "'");
        System.out.println("\n'" + HOME_COMMAND + "'  -> home page");
        System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
    }

    // displays order !!!
    private void displayOrder() {}

    // displays menu items by category
    private void displayMenu(String[] category) {
        System.out.println("\nselect from:");
        Integer size = category.length;
        for (int i = 0; i < size; i++) {
            System.out.println("\t'" + i + "' -> " + category[i] + "");
        }
        printGeneralInstructions();

        //handle input
        int num;
        String s = input.nextLine();

        try {
            num = Integer.parseInt(s);
            String str = category[num];
            parseInputItemDetails(str);
        } catch (NumberFormatException e) {
            parseInputMenus(s);
        }
    }

    // prints menu item details depending on input i
    private void parseInputItemDetails(String str) {
        if (str.length() > 0) {
            switch (str) {
                case "Espresso":
                case "Americano":
                case "Macchiato":
                case "Latte":
                    displayBeverageDetails(str, cafe.coffee);
                    break;
                case "Matcha Latte":
                case "Hojicha Latte":
                case "London Fog":
                case "Chai Latte":
                    displayBeverageDetails(str, cafe.tea);
                    break;
                case "Honey Ginger Tea":
                case "Fruit Tea":
                case "Hibiscus Kombucha":
                    displayBeverageDetails(str, cafe.nonCaffeinated);
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
                    break;
            }
        }
    }


    // displays menu item details !!! (List<Beverage>) refactor with helper functions
    private void displayBeverageDetails(String itemName, List<Beverage> type) {
        Beverage beverage = getBeverageByName(itemName, type);
        System.out.println("\n" + beverage.getName() + "");
        if (beverage.getSize() != Beverage.NOT_CUSTOMIZABLE) {
            System.out.println("regular\t\t$" + beverage.getPrice() + "");
            System.out.println("large  \t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE) + "");

            System.out.println("\n'" + REGULAR_SIZE_COMMAND + "' -> add regular " + beverage.getName() + " to order");
            System.out.println("'" + LARGE_SIZE_COMMAND + "'   -> add large " + beverage.getName() + " to order");
            printGeneralInstructions();

            parseInputAddBeverageToOrder(itemName, beverage);
        } else if (beverage.getTemperature() != Beverage.NOT_CUSTOMIZABLE) {
            System.out.println("hot \t\t$" + beverage.getPrice() + "");
            System.out.println("cold\t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE) + "");
            // maybe change hot to HOT_TEMP_COMMAND
            System.out.println("\n'" + HOT_TEMP_COMMAND + "'  -> add hot " + beverage.getName() + " to order");
            System.out.println("'" + COLD_TEMP_COMMAND + "' -> add cold " + beverage.getName() + " to order");
            printGeneralInstructions();

            parseInputAddBeverageToOrder(itemName, beverage);
        } else {
            System.out.println("this item is not customizable :(");
            System.out.println("'" + ADD_TO_ORDER_COMMAND + "' -> add " + beverage.getName() + " to order");
            printGeneralInstructions();

            parseInputAddBeverageToOrder(itemName, beverage);
        }
    }

    // adds beverage with customization (if any) to order
    private void parseInputAddBeverageToOrder(String itemName, Beverage beverage) {
        String str = input.next();
        Beverage b;
        switch (str) {
            case REGULAR_SIZE_COMMAND:
                b = new Beverage(itemName, beverage.getPrice(), Beverage.REGULAR, beverage.getTemperature());
                break;
            case LARGE_SIZE_COMMAND:
                b = new Beverage(itemName, beverage.getPrice(), Beverage.EXTRA, beverage.getTemperature());
                break;
            case HOT_TEMP_COMMAND:
                b = new Beverage(itemName, beverage.getPrice(), beverage.getSize(), Beverage.REGULAR);
                break;
            case COLD_TEMP_COMMAND:
                b = new Beverage(itemName, beverage.getPrice(), beverage.getSize(), Beverage.EXTRA);
                break;
            case ADD_TO_ORDER_COMMAND:
                b = new Beverage(itemName, beverage.getPrice(), beverage.getSize(), beverage.getTemperature());
                break;
            default:
                b = beverage;               //!!! unnecessary initialization of b
                parseInputMenus(str);       //!!! should parseInputMenus call this method instead?
                break;
        }
        order.addItem(b);
        printConfirmation(str, beverage);
        printGeneralInstructions();
    }

    //prints line to confirm item has been added to order
    private void printConfirmation(String command, MenuItem item) {
        if (command != ADD_TO_ORDER_COMMAND) {
            System.out.println("\n" + command + " " + item.getName() + " has been added to your order!");
        } else {
            System.out.println("\n" + item.getName() + " has been added to your order!");
        }
    }

    // displays instructions for home page and view order
    private void printGeneralInstructions() {
        System.out.println("\n'" + CAFE_MENU_COMMAND + "'  -> cafe menu");
        System.out.println("'" + HOME_COMMAND + "'  -> home page");
        System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
    }

    //EFFECTS: returns the item in itemList if already there,
    //         if not, returns null !!! List<Beverage>
    private Beverage getBeverageByName(String name, List<Beverage> category) {
        for (Beverage i : category) {
            if (name == i.getName()) {
                return i;
            }
        }
        return null;
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    //EFFECTS: stops receiving user input
    public void endProgram() {
        System.out.println("Quitting...");
        input.close();
    }
}
