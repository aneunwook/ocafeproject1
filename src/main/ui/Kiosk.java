package ui;

import model.*;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private static final String PLACE_ORDER_COMMAND = "p";
    private static final String SIGN_IN_COMMAND = "s";
    private static final String CREATE_ACCOUNT_COMMAND = "c";
    private static final String HOME_COMMAND = "home";
    private static final String HISTORY_COMMAND = "history";
    private static final String SIGN_OUT_COMMAND = "out";
    private static final String CAFE_MENU_COMMAND = "menu";
    private static final String VIEW_ORDER_COMMAND = "order";
    private static final String REMOVE_COMMAND = "remove";
    private static final String CHECKOUT_COMMAND = "checkout";
    private static final String TIP_COMMAND = "tip";
    private static final String PAY_COMMAND = "pay";
    private static final String SAVE_COMMAND = "save";
    private static final String DO_NOT_SAVE_COMMAND = "continue";
    private static final String QUIT_COMMAND = "quit";

    private static final String COFFEE_COMMAND = "coffee";
    private static final String TEA_COMMAND = "tea";
    private static final String NONCAFFEINATED_COMMAND = "noncaffeinated";
    private static final String BRUNCH_COMMAND = "brunch";
    private static final String DESSERT_COMMAND = "dessert";

    private static final String[] coffee = {"Espresso", "Americano", "Macchiato", "Latte", "Iced Coffee", "Cold Brew"};
    private static final String[] tea = {
            "Matcha Latte", "Hojicha Latte", "London Fog", "Chai Latte", "Sencha", "Black Tea"};
    private static final String[] noncaffeinated = {
            "Honey Ginger Tea", "Fruit Tea", "Kumquat Chrysanthemum", "Hibiscus Kombucha", "Mango Kale Smoothie"};
    private static final String[] brunch = {
            "Thai Green Curry Seafood Linguine", "Eggs Benedict", "Omurice", "Butternut Squash Risotto",
            "Japanese Curry Rice", "Dutch Cheese Sandwich", "Spring Salad", "Butter Croissant "};
    private static final String[] dessert = {
            "Kinako Mochi", "Raspberry Pistachio Cream Tart", "Banana Cream Pie", "Sweet Potato Crepe",
            "Hojicha Parfait", "Chestnut Cake", "Tofu Ice Cream"};

    private static final String REGULAR_SIZE_COMMAND = "r";
    private static final String LARGE_SIZE_COMMAND = "l";
    private static final String HOT_TEMP_COMMAND = "h";
    private static final String COLD_TEMP_COMMAND = "c";
    private static final String ADD_TO_ORDER_COMMAND = "0";

    private Scanner input;
    private boolean runProgram;
    private Cafe cafe;
    private Account account;
    private JsonWriter writer;
    private Order order;

    // constructor, handleUserInput, makePrettyString, and endProgram methods taken from FitLifeGymKiosk.ui.Kiosk

    // takes in a cafe and constructs new kiosk with new scanner, cafe, and new order
    public Kiosk(Cafe cafe) {
        input = new Scanner(System.in);
        runProgram = true;
        this.cafe = cafe;
        order = new Order();
    }

    //EFFECTS: parses user input until user quits
    public void handleUserInput() {
        startPage();
        String str;

        while (runProgram) {
            if (input.hasNext()) {
                str = input.nextLine();
                str = makePrettyString(str);
                parseInputNavigate(str);
            }
        }
    }

    //EFFECTS: prints menu options depending on input str
    private void parseInputNavigate(String str) {
        if (str.length() > 0) {
            switch (str) {
                case PLACE_ORDER_COMMAND:
                case CAFE_MENU_COMMAND:
                    displayCafeMenu();
                    break;
                case CREATE_ACCOUNT_COMMAND:
                    handleCreateAccount();
                    break;
                case HOME_COMMAND:
                    displayAccountHome();
                    break;
                case VIEW_ORDER_COMMAND:
                    displayOrderSummary();
                    break;
                case REMOVE_COMMAND:
                    displayItemsRemove();
                    break;
                case CHECKOUT_COMMAND:
                    handleCheckOut();
                    break;
                case PAY_COMMAND:
                    handlePayment();
                    break;
                case SAVE_COMMAND:
                    saveOrder();
                case COFFEE_COMMAND:
                    displayCategory(coffee);
                    break;
                default:
                    parseInputNavigate2(str);
                    break;
            }
        }
    }

    //EFFECTS: extends parseInputNavigate
    private void parseInputNavigate2(String str) {
        switch (str) {
            case TEA_COMMAND:
                displayCategory(tea);
                break;
            case NONCAFFEINATED_COMMAND:
                displayCategory(noncaffeinated);
                break;
            case BRUNCH_COMMAND:
                displayCategory(brunch);
                break;
            case DESSERT_COMMAND:
                displayCategory(dessert);
                break;
            case QUIT_COMMAND:
                runProgram = false;
                break;
            default:
                parseInputItemDetails(str);
                break;
        }
    }

    //AccountNotRobust.ui.TellerApp
    //EFFECTS: displays start page
    private void startPage() {
        System.out.println("\nselect from:");
        System.out.println("\t'" + PLACE_ORDER_COMMAND + "' -> place order");
        System.out.println("\t'" + SIGN_IN_COMMAND + "' -> sign in");
        System.out.println("\t'" + CREATE_ACCOUNT_COMMAND + "' -> create account");
        System.out.println("\nenter '" + QUIT_COMMAND + "' to quit any time.");
    }

    //EFFECTS: displays cafe menu by category
    private void displayCafeMenu() {
        System.out.println("\nCafe Menu");
        System.out.println("enter one of:");
        System.out.println("\t'" + COFFEE_COMMAND + "'");
        System.out.println("\t'" + TEA_COMMAND + "'");
        System.out.println("\t'" + NONCAFFEINATED_COMMAND + "'");
        System.out.println("\t'" + BRUNCH_COMMAND + "'");
        System.out.println("\t'" + DESSERT_COMMAND + "'");
        System.out.println("\n'" + HOME_COMMAND + "'  -> home page");
        if (order.size() != 0) {
            System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
        }
    }

    //MODIFIES: this
    //EFFECTS: prompts user to enter info to create an account
    private void handleCreateAccount() {
        System.out.println("Please enter your name:");
        String str = input.nextLine();

        account = new Account(str);
        writer = new JsonWriter(account.getFile());
        try {
            writer.open();
            writer.write(account);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Your name cannot contain backslashes or quotation marks");
            handleCreateAccount();
        }

        System.out.println("\nYour account has been created, " + str + "!\n");

        displayAccountHome();
    }

    //EFFECTS: displays account home page
    private void displayAccountHome() {
        System.out.println("\n" + account.getName() + "'s Account");
        System.out.println("\t'" + CAFE_MENU_COMMAND + "'    -> cafe menu");
        System.out.println("\t'" + HISTORY_COMMAND + "' -> order history");
        System.out.println("\t'" + SIGN_OUT_COMMAND + "'     -> sign out");
    }

    //EFFECTS: displays order summary
    private void displayOrderSummary() {
        System.out.println("\nYour Order Summary:");
        for (MenuItem item : order.getItemList()) {
            System.out.println("$" + item.getPrice() + "\t\t" + item.getName());
        }
        System.out.println("\nTotal: $" + order.getTotal());
        if (order.size() != 0) {
            System.out.println("'" + REMOVE_COMMAND + "'   -> select item to remove");
        }
        System.out.println("'" + CHECKOUT_COMMAND + "' -> proceed to payment");
        System.out.println("'" + CAFE_MENU_COMMAND + "'     -> cafe menu");
        System.out.println("'" + HOME_COMMAND + "'     -> home page");
    }

    //MODIFIES: this
    //EFFECTS: prompts user to select an item to remove,
    //         if an item is selected, removes it from order
    private void displayItemsRemove() {
        if (order.size() == 0) {
            System.out.println("\nyour order is empty :(");
            printGeneralInstructions();
        } else {
            System.out.println("\nselect which item to remove:");
            int i = 0;
            for (MenuItem item : order.getItemList()) {
                System.out.println("\t" + i + " -> $" + item.getPrice() + "\t" + item.getName());
                i++;
            }
            System.out.println("\nTotal: $" + order.getTotal());
            System.out.println("'" + CHECKOUT_COMMAND + "' -> proceed to payment");
            System.out.println("'" + CAFE_MENU_COMMAND + "'     -> cafe menu");
            System.out.println("'" + HOME_COMMAND + "'     -> home page");

            //handle input
            String s = input.nextLine();

            try {
                int num = Integer.parseInt(s);
                order.removeItem(order.getItemList().get(num));
                displayItemsRemove();
            } catch (NumberFormatException e) {
                parseInputNavigate(s);
            }
        }
    }

    //EFFECTS: prompts user to complete the order
    private void handleCheckOut() {
        System.out.println("\nCheckout");
        System.out.println("Total: $" + order.getTotal());
        System.out.println("\n'" + CAFE_MENU_COMMAND + "' -> add more items");
        System.out.println("\n'" + TIP_COMMAND + "'  -> add a tip :)");
        System.out.println("'" + PAY_COMMAND + "'  -> confirm payment");
    }

    //MODIFIES: this
    //EFFECTS: makes purchase and prompts to save order !!!change after making card
    private void handlePayment() {
        //process payment using card...
        //assign date to order right after payment
        System.out.println("Your order is complete, enjoy!");
        System.out.println("\nWould you like to save this order?");
        System.out.println("-> '" + SAVE_COMMAND + "'");
        System.out.println("-> '" + DO_NOT_SAVE_COMMAND + "'");
    }

    //MODIFIES: this
    //EFFECTS: saves order to account history
    private void saveOrder() {
        account.addOrder(order);
        try {
            writer.open();
            writer.write(account);
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("Order could not be saved");
        }

    }

    //EFFECTS: displays menu items in a category
    private void displayCategory(String[] category) {
        System.out.println("\nselect from:");
        int size = category.length;
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
            parseInputNavigate(s);
        }
    }


    //EFFECTS: prints menu item details depending on input str
    private void parseInputItemDetails(String str) {
        if (str.length() > 0) {
            switch (str) {
                case "Espresso":
                case "Americano":
                case "Macchiato":
                case "Latte":
                case "Iced Coffee":
                case "Cold Brew":
                    displayBeverageDetails(str, cafe.coffee);
                    break;
                case "Matcha Latte":
                case "Hojicha Latte":
                case "London Fog":
                case "Chai Latte":
                case "Sencha":
                case "Black Tea":
                    displayBeverageDetails(str, cafe.tea);
                    break;
                default:
                    parseInputItemDetails2(str);
                    break;
            }
        }
    }

    //EFFECTS: extension of parseInputItemDetails
    private void parseInputItemDetails2(String str) {
        switch (str) {
            case "Honey Ginger Tea":
            case "Fruit Tea":
            case "Kumquat Chrysanthemum Tea":
            case "Hibiscus Kombucha":
            case "Mango Kale Smoothie":
                displayBeverageDetails(str, cafe.nonCaffeinated);
                break;
            case "Thai Green Curry Seafood Linguine":
            case "Eggs Benedict":
            case "Omurice":
            case "Butternut Squash Risotto":
            case "Japanese Curry Rice":
            case "Dutch Cheese Sandwich":
            case "Spring Salad":
            case "Butter Croissant ":
                displayDishDetails(str, cafe.brunch);
                break;
            default:
                parseInputItemDetails3(str);
                break;
        }
    }

    //EFFECTS: extension of parseInputItemDetails
    private void parseInputItemDetails3(String str) {
        switch (str) {
            case "Kinako Mochi":
            case "Raspberry Pistachio Cream Tart":
            case "Banana Cream Pie":
            case "Sweet Potato Crepe":
            case "Hojicha Parfait":
            case "Chestnut Cake":
            case "Tofu Ice Cream":
                displayDishDetails(str, cafe.dessert);
                break;
            default:
                System.out.println("Invalid selection, please try again.");
                break;
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
                System.out.println("'" + LARGE_SIZE_COMMAND + "' -> add large " + beverage.getName() + " to order");
            } else {
                System.out.println("hot \t\t$" + beverage.getPrice() + "");
                System.out.println("cold\t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE) + "");

                System.out.println("\n'" + HOT_TEMP_COMMAND + "' -> add hot " + beverage.getName() + " to order");
                System.out.println("'" + COLD_TEMP_COMMAND + "' -> add cold " + beverage.getName() + " to order");
            }
        }
        printGeneralInstructions();
        parseInputAddBeverageToOrder(beverage);
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
            parseInputNavigate(s);
        }
    }


    //MODIFIES: this
    //EFFECTS: adds beverage with customization (if any) to order
    private void parseInputAddBeverageToOrder(Beverage beverage) {
        String str = input.next();
        Beverage b = new Beverage(
                beverage.getName(), beverage.getPrice(), beverage.getSize(), beverage.getTemperature());
        switch (str) {
            case REGULAR_SIZE_COMMAND:
                b.setSize(Beverage.REGULAR);
                addItemAndPrintConfirmation("regular", b);
                break;
            case LARGE_SIZE_COMMAND:
                b.setSize(Beverage.EXTRA);
                addItemAndPrintConfirmation("large", b);
                break;
            case HOT_TEMP_COMMAND:
                b.setTemperature(Beverage.REGULAR);
                addItemAndPrintConfirmation("hot", b);
                break;
            default:
                parseInputAddBeverageToOrder2(b, str);
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: extends parseInputAddBeverageToOrder
    private void parseInputAddBeverageToOrder2(Beverage b, String str) {
        switch (str) {
            case COLD_TEMP_COMMAND:
                b.setTemperature(Beverage.EXTRA);
                addItemAndPrintConfirmation("cold", b);
                break;
            case ADD_TO_ORDER_COMMAND:
                addItemAndPrintConfirmation(str, b);
                break;
            default:
                parseInputNavigate(str);
                break;
        }
    }


    //MODIFIES: this
    //EFFECTS: adds dish with customization (if any) to order
    private void parseInputAddDishToOrder(Integer num, Dish dish) {
        Dish d = new Dish(dish.getName(), dish.getPrice());
        for (AdditionalOptions addOn : dish.getOptions()) {
            d.addSideToOptions(addOn);
        }
        if ((num <= d.getOptions().size()) && (num >= 0)) {
            if (num != 0) {
                d.selectAddOn(dish.getOptions().get(num - 1));
                addItemAndPrintConfirmation(d.getSelected().getName(), d);
            } else {
                order.addItem(d);
                System.out.println("\nnaked " + d.getName() + " has been added to your order!");
                printGeneralInstructions();
            }
        } else {
            System.out.println("Invalid selection, please try again.");
        }
    }


    //EFFECTS: displays instructions for home page and view order
    private void printGeneralInstructions() {
        System.out.println("\n'" + CAFE_MENU_COMMAND + "'  -> cafe menu");
        System.out.println("'" + HOME_COMMAND + "'  -> home page");
        if (order.size() != 0) {
            System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
        }
    }

    //EFFECTS: displays details for non customizable menu items
    private void displayItemNotCustomizableDetails(MenuItem item) {
        System.out.println("\n" + item.getName() + "\t\t$" + item.getPrice() + "");
//        System.out.println("\nthis item is not customizable :(");
        System.out.println("\n'" + ADD_TO_ORDER_COMMAND + "' -> add " + item.getName() + " to order");
    }

    //MODIFIES: this
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

    //EFFECTS: returns the Beverage in a category if already there,
    //         if not, returns null
    private Beverage getBeverageByName(String name, List<Beverage> category) {
        for (Beverage b : category) {
            if (name.equals(b.getName())) {
                return b;
            }
        }
        return null;
    }

    //EFFECTS: returns the Dish in a category if already there,
    //         if not, returns null
    private Dish getDishByName(String name, List<Dish> category) {
        for (Dish d : category) {
            if (name.equals(d.getName())) {
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
        System.out.println("Thanks for visiting OCafe! See you next time!");
        input.close();
    }
}
