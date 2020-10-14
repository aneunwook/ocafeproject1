package ui;

import model.*;

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
    private static final String[] brunch = {"Eggs Benny", "Omurice", "Butternut Squash Risotto"};
    private static final String DESSERT_COMMAND = "dessert";
    private static final String[] dessert = {"Kinako Mochi", "Raspberry Pistachio Cream Tart", "Banana Cream Pie"};
    private static final String REGULAR_SIZE_COMMAND = "regular";
    private static final String LARGE_SIZE_COMMAND = "large";
    private static final String HOT_TEMP_COMMAND = "hot";
    private static final String COLD_TEMP_COMMAND = "cold";
    private static final String ADD_TO_ORDER_COMMAND = "0";
    private static final String QUIT_COMMAND = "quit";


    private Scanner input;
    private boolean runProgram;
    private Cafe cafe;
    private Order order;

    // constructor, handleUserInput, parseInputMenus, makePrettyString,
    // and endProgram taken from FitLifeGymKiosk.ui.Kiosk

    // takes in a cafe and constructs new kiosk with new scanner, cafe, and new order
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
        String str;

        while (runProgram) {
            if (input.hasNext()) {
                str = input.nextLine();
                str = makePrettyString(str);
                parseInputMenus(str);
            }
        }
    }

    //EFFECTS: prints menu options depending on input str
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
                    displayMenu(coffee);
                    break;
                case TEA_COMMAND:
                    displayMenu(tea);
                    break;
                default:
                    parseInputMenus2(str);
                    break;
            }
        }
    }

    private void parseInputMenus2(String str) {
        switch (str) {
            case NONCAFFEINATED_COMMAND:
                displayMenu(noncaffeinated);
                break;
            case BRUNCH_COMMAND:
                displayMenu(brunch);
                break;
            case DESSERT_COMMAND:
                displayMenu(dessert);
                break;
            case QUIT_COMMAND:
                runProgram = false;
                break;
            default:
                parseInputItemDetails1(str);
                break;
        }
    }

    // taken from AccountNotRobust.ui.TellerApp
    //EFFECTS: displays home page
    private void homePage() {
        System.out.println("\nselect from:");
        System.out.println("\t'" + PLACE_ORDER_COMMAND + "' -> place order");
//        System.out.println("\t'" + SIGN_IN_COMMAND + "' -> sign in");
//        System.out.println("\t'" + CREATE_ACCOUNT_COMMAND + "' -> create account");
        System.out.println("\nenter '" + QUIT_COMMAND + "' to quit any time.");
    }

    //EFFECTS: displays cafe menu
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

    //EFFECTS: displays order
    private void displayOrder() {
        System.out.println("\nYour Order Summary:");
        for (MenuItem item : order.getItemList()) {
            System.out.println("" + item.getName() + "\t\t$" + item.getPrice() + "");
        }
        System.out.println("\nTotal: $" + order.getTotal());
        System.out.println("\n'" + HOME_COMMAND + "'  -> home page");
        System.out.println("'" + CAFE_MENU_COMMAND + "'  -> cafe menu");

    }

    //EFFECTS: displays menu items by category
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
            parseInputItemDetails1(str);
        } catch (NumberFormatException e) {
            parseInputMenus(s);
        }
    }

    //EFFECTS: prints menu item details depending on input str
    private void parseInputItemDetails1(String str) {
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
                default:
                    parseInputItemDetails2(str);
                    break;
            }
        }
    }

    //EFFECTS: extension of parseInputItemDetails1
    private void parseInputItemDetails2(String str) {
        if (str.length() > 0) {
            switch (str) {
                case "Honey Ginger Tea":
                case "Fruit Tea":
                case "Hibiscus Kombucha":
                    displayBeverageDetails(str, cafe.nonCaffeinated);
                    break;
                case "Eggs Benny":
                case "Omurice":
                case "Butternut Squash Risotto":
                    displayDishDetails(str, cafe.brunch);
                    break;
                case "Kinako Mochi":
                case "Raspberry Pistachio Cream Tart":
                case "Banana Cream Pie":
                    displayDishDetails(str, cafe.dessert);
                    break;
                default:
                    System.out.println("Invalid selection, please try again.");
                    break;
            }
        }
    }

    //EFFECTS: displays dish item details
    private void displayDishDetails(String itemName, List<Dish> type) {
        Dish dish = getDishByName(itemName, type);
        if (dish.getOptions().size() == 0) {
            displayItemNotCustomizableDetails(dish);
        } else {
            System.out.println("\n" + dish.getName() + "\t\t$" + dish.getPrice() + "");
            for (AdditionalOptions addOn : dish.getOptions()) {
                System.out.println("" + addOn.getName() + "\t\t+$" + addOn.getPrice() + "");
            }
            System.out.println("\nadd to your order:");
            System.out.println("'0' -> naked " + dish.getName() + "");
            for (int i = 0; i < dish.getOptions().size(); i++) {
                System.out.println("'" + (i + 1) + "' -> " + dish.getOptions().get(i).getName() + " " + dish.getName());
            }
        }
        printGeneralInstructions();
        //handle input
        int num;
        String s = input.nextLine();

        try {
            num = Integer.parseInt(s);
            parseInputAddDishToOrder(num, dish);
        } catch (NumberFormatException e) {
            parseInputMenus(s);
        }
    }

    //EFFECTS: adds dish with customization (if any) to order
    private void parseInputAddDishToOrder(Integer num, Dish dish) {
        Dish d = new Dish(dish.getName(), dish.getPrice());
        for (AdditionalOptions addOn : dish.getOptions()) {
            d.addSideToOptions(addOn);
        }
        if ((num <= d.getOptions().size()) && (num >= 0)) {
            if (num != 0) {
                d.selectAddOn(dish.getOptions().get(num - 1));
                System.out.println(
                        "\n" + d.getSelected().getName() + " " + d.getName() + " has been added to your order!");
            } else {
                System.out.println("\nnaked " + d.getName() + " has been added to your order!");
            }
            order.addItem(d);
            printGeneralInstructions();
        } else {
            System.out.println("Invalid selection, please try again.");
        }
    }

    //EFFECTS: displays beverage item details
    private void displayBeverageDetails(String itemName, List<Beverage> type) {
        Beverage beverage = getBeverageByName(itemName, type);
        if (!beverage.isSizeCustomizable() && !beverage.isTemperatureCustomizable()) {
            displayItemNotCustomizableDetails(beverage);
        } else {
            System.out.println("\n" + beverage.getName() + "");
            if (beverage.isSizeCustomizable()) {
                System.out.println("regular\t\t$" + beverage.getPrice() + "");
                System.out.println("large  \t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE) + "");

                System.out.println(
                        "\n'" + REGULAR_SIZE_COMMAND + "' -> add regular " + beverage.getName() + " to order");
                System.out.println("'" + LARGE_SIZE_COMMAND + "'   -> add large " + beverage.getName() + " to order");
            } else {
                System.out.println("hot \t\t$" + beverage.getPrice() + "");
                System.out.println("cold\t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE) + "");
                // maybe change hot to HOT_TEMP_COMMAND
                System.out.println("\n'" + HOT_TEMP_COMMAND + "'  -> add hot " + beverage.getName() + " to order");
                System.out.println("'" + COLD_TEMP_COMMAND + "' -> add cold " + beverage.getName() + " to order");
            }
        }
        printGeneralInstructions();
        parseInputAddBeverageToOrder(beverage);
    }

    //EFFECTS: displays details for non customizable menu items
    private void displayItemNotCustomizableDetails(MenuItem item) {
        System.out.println("\n" + item.getName() + "\t\t$" + item.getPrice() + "");
        System.out.println("\nthis item is not customizable :(");
        System.out.println("'" + ADD_TO_ORDER_COMMAND + "' -> add " + item.getName() + " to order");
    }

    //EFFECTS: adds beverage with customization (if any) to order
    private void parseInputAddBeverageToOrder(Beverage beverage) {
        String str = input.next();
        Beverage b = new Beverage(
                beverage.getName(), beverage.getPrice(), beverage.getSize(), beverage.getTemperature());
        switch (str) {
            case REGULAR_SIZE_COMMAND:
                b.setSize(Beverage.REGULAR);
                addItemAndPrintConfirmation(str, b);
                break;
            case LARGE_SIZE_COMMAND:
                b.setSize(Beverage.EXTRA);
                addItemAndPrintConfirmation(str, b);
                break;
            case HOT_TEMP_COMMAND:
                b.setTemperature(Beverage.REGULAR);
                addItemAndPrintConfirmation(str, b);
                break;
            default:
                parseInputAddBeverageToOrder2(b, str);
                break;
        }
    }

    //EFFECTS: extends parseInputAddBeverageToOrder
    private void parseInputAddBeverageToOrder2(Beverage b, String str) {
        switch (str) {
            case COLD_TEMP_COMMAND:
                b.setTemperature(Beverage.EXTRA);
                addItemAndPrintConfirmation(str, b);
                break;
            case ADD_TO_ORDER_COMMAND:
                addItemAndPrintConfirmation(str, b);
                break;
            default:
                parseInputMenus(str);
                break;
        }
    }

    //EFFECTS: prints line to confirm item has been added to order
    private void addItemAndPrintConfirmation(String command, MenuItem item) {
        order.addItem(item);
        if (command.equals(ADD_TO_ORDER_COMMAND)) {
            System.out.println("\n" + item.getName() + " has been added to your order!");
        } else {
            System.out.println("\n" + command + " " + item.getName() + " has been added to your order!");
        }
        printGeneralInstructions();
    }

    //EFFECTS: displays instructions for home page and view order
    private void printGeneralInstructions() {
        System.out.println("\n'" + CAFE_MENU_COMMAND + "'  -> cafe menu");
        System.out.println("'" + HOME_COMMAND + "'  -> home page");
        System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
    }

    //EFFECTS: returns the item in itemList if already there,
    //         if not, returns null !!! List<Beverage>
    private Beverage getBeverageByName(String name, List<Beverage> category) {
        for (Beverage b : category) {
            if (name == b.getName()) {
                return b;
            }
        }
        return null;
    }

    //EFFECTS:
    private Dish getDishByName(String name, List<Dish> category) {
        for (Dish d : category) {
            if (name == d.getName()) {
                return d;
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
