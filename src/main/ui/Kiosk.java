package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// console application ui
public class Kiosk {

    private static final String PLACE_ORDER_COMMAND = "p";
    private static final String SIGN_IN_COMMAND = "s";
    private static final String CREATE_ACCOUNT_COMMAND = "c";
    private static final String HOME_COMMAND = "home";
    private static final String HISTORY_COMMAND = "history";
    private static final String SIGN_OUT_COMMAND = "out";
    private static final String CAFE_MENU_COMMAND = "menuLoader";
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

    private static final String[] coffee = {"에스프레소", "아메리카노", "마끼아또", "라떼", "아이스 커피", "콜드브루"};
    private static final String[] tea = {
            "말차 라떼", "호지차 라떼", "런던 포그", "차이 라떼", "센차", "홍차"};
    private static final String[] noncaffeinated = {
            "생강 꿀 차", "과실 차", "금귤 국화 차", "히비스커스 차", "망고 케일 스무디"};
    private static final String[] brunch = {
            "태국 야채 카레 해산물 링귀네", "에그 베네딕트", "오므라이스", "버터넛 스쿼시 리조또",
            "일본 카레", "더치 치즈 샌드위치", "봄철 셀러드", "버터 크로아상"};
    private static final String[] dessert = {
            "키나코 모찌", "라즈베리 피스타치오 크림 타르트", "바나나 크림 파이", "고구마 크레페"};

    private static final String REGULAR_SIZE_COMMAND = "r";
    private static final String LARGE_SIZE_COMMAND = "l";
    private static final String HOT_TEMP_COMMAND = "h";
    private static final String COLD_TEMP_COMMAND = "i";
    private static final String ADD_TO_ORDER_COMMAND = "0";

    private Scanner input;
    private boolean runProgram;
    private MenuLoader menuLoader;
    private Account account;
    private ArrayList<String> accountList;
    private JsonWriter writer;
    private JsonReader reader;
    private Order order;

    // constructor, handleUserInput, makePrettyString, and endProgram methods taken from FitLifeGymKiosk.ui.Kiosk

    // takes in a menuLoader and constructs new kiosk with new scanner, menuLoader, and new order
    public Kiosk(MenuLoader menuLoader) {
        input = new Scanner(System.in);
        runProgram = true;
        this.menuLoader = menuLoader;
//        accountList = new AbstractSet<String>()
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

    //EFFECTS: prints menuLoader options depending on input str
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
                case SIGN_IN_COMMAND:
                    handleSignIn();
                    break;
                case SIGN_OUT_COMMAND:
                    accountSignOut();
                    break;
                case HOME_COMMAND:
                    displayAccountHome();
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
            case HISTORY_COMMAND:
                displayOrderHistory();
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
                break;
            default:
                parseInputNavigate3(str);
                break;
        }
    }

    //EFFECTS: extends parseInputNavigate
    private void parseInputNavigate3(String str) {
        switch (str) {
            case DO_NOT_SAVE_COMMAND:
                doNotSaveOrder();
                break;
            case COFFEE_COMMAND:
                displayCategory(coffee);
                break;
            case TEA_COMMAND:
                displayCategory(tea);
                break;
            case NONCAFFEINATED_COMMAND:
                displayCategory(noncaffeinated);
                break;
            default:
                parseInputNavigate4(str);
                break;
        }
    }

    //EFFECTS: extends parseInputNavigate
    private void parseInputNavigate4(String str) {
        switch (str) {
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
        System.out.println("\t'" + PLACE_ORDER_COMMAND + "' -> place order ");
        System.out.println("\t'" + SIGN_IN_COMMAND + "' -> sign in");
        System.out.println("\t'" + CREATE_ACCOUNT_COMMAND + "' -> create account");
        System.out.println("\nenter '" + QUIT_COMMAND + "' to quit any time.");
    }

    //EFFECTS: displays menuLoader menuLoader by category
    private void displayCafeMenu() {
        System.out.println("\nMenuLoader MenuLoader");
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

        try {
            account = new Account(str);
//            accountList.add(str);
            writer = new JsonWriter(account.getFile());
            reader = new JsonReader(account.getFile());
            writer.open();
            writer.write(account);
            writer.close();

            System.out.println("Your account has been created, " + str + "!");
            displayAccountHome();

        } catch (FileNotFoundException e) {
            System.out.println("Your name cannot contain backslashes or quotation marks");
            handleCreateAccount();
        }
    }

    //MODIFIES: this
    //EFFECTS: if account with input name exists, sets account field
    private void handleSignIn() {
        System.out.println("Please enter your name:");
        String str = input.nextLine();

        try {
            writer = new JsonWriter("./data/" + str + ".json");
            reader = new JsonReader("./data/" + str + ".json");
            account = reader.read();
            System.out.println("Welcome back, " + account.getName() + "!");
            displayAccountHome();
        } catch (IOException e) {
            System.out.println("An account with name '" + str + "' cannot be found :(");
            //try again or create account
            System.out.println("\n'" + SIGN_IN_COMMAND + "' -> try again");
            System.out.println("'" + CREATE_ACCOUNT_COMMAND + "' -> create account");
        }
    }

    //MODIFIES: this
    //EFFECTS: if account is not already null, set account, writer, and reader to null and display start page,
    //         otherwise does nothing
    private void accountSignOut() {
        if (account != null) {
            account = null;
            writer = null;
            reader = null;
            startPage();
        }
    }

    //EFFECTS: displays account home page
    private void displayAccountHome() {
        if (account == null) {
            startPage();
        } else {
            System.out.println("\n" + account.getName() + "'s Account");
            System.out.println("\n\t'" + CAFE_MENU_COMMAND + "'    -> place order");
            System.out.println("\t'" + HISTORY_COMMAND + "' -> order history");
            System.out.println("\t'" + SIGN_OUT_COMMAND + "'     -> sign out");
        }
    }

    //REQUIRES: account != null
    //EFFECTS: prints order history !!!make another method to print out individual orders
    private void displayOrderHistory() {
        try {
            account = reader.read();
            if (account.getHistory().size() == 0) {
                System.out.println("No orders have been saved to your account yet!");
            } else {
                System.out.println("\nYour Order History");
                for (Order o : account.getHistory()) {
                    System.out.println("\n" + o);
                }
            }

            System.out.println("\n\t'" + CAFE_MENU_COMMAND + "' -> place order");
            System.out.println("\t'" + HOME_COMMAND + "' -> home page");
        } catch (IOException e) {
            System.out.println("Account file could not be read");
        }
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
        System.out.println("'" + CAFE_MENU_COMMAND + "'     -> add more items");
//        System.out.println("'" + HOME_COMMAND + "'     -> home page");
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
                System.out.println("\t" + i + " -> $" + item.getPrice() + "\t\t" + item.getName());
                i++;
            }
            System.out.println("\nTotal: $" + order.getTotal());
            System.out.println("'" + CHECKOUT_COMMAND + "' -> proceed to payment");
            System.out.println("'" + CAFE_MENU_COMMAND + "'     -> menuLoader menuLoader");
//            System.out.println("'" + HOME_COMMAND + "'     -> home page");

            //handle input
            String s = input.nextLine();

            try {
                int num = Integer.parseInt(s);
                order.removeItem(order.getItemList().get(num));
                System.out.println("Removed!");
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
//        System.out.println("\n'" + TIP_COMMAND + "'  -> add a tip :)");
        System.out.println("\n'" + PAY_COMMAND + "'  -> confirm payment");
        System.out.println("'" + CAFE_MENU_COMMAND + "' -> add more items");
    }

    //MODIFIES: this
    //EFFECTS: makes purchase and prompts to save order !!!change after making card
    private void handlePayment() {
        //process payment using card...
        order.setDate();
        System.out.println("Your order has been placed, enjoy!");
        if (account != null) {
            System.out.println("\nWould you like to save this order?");
            System.out.println("-> '" + SAVE_COMMAND + "'");
            System.out.println("-> '" + DO_NOT_SAVE_COMMAND + "'");
        } else {
            printPostPaymentInstructions();
        }
    }

    //MODIFIES: this
    //EFFECTS: saves order to account history and prints order summary
    private void saveOrder() {
        account.addOrder(order);
        try {
            writer.open();
            writer.write(account);
            writer.close();
            System.out.println("\nSaved!");
            System.out.println(order);
            printPostPaymentInstructions();
        } catch (FileNotFoundException e) {
            System.out.println("Order could not be saved");
        }
    }

    //EFFECTS: prints unsaved message and post payment instructions
    private void doNotSaveOrder() {
        System.out.println("This order was not saved to your history");
        printPostPaymentInstructions();
    }

    //EFFECTS: displays menuLoader items in a category
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


    //EFFECTS: prints menuLoader item details depending on input str
    private void parseInputItemDetails(String str) {
        if (str.length() > 0) {
            switch (str) {
                case "에스프레소":
                case "아메리카노":
                case "마끼아또":
                case "라떼":
                case "아이스 커피":
                case "콜드브루":
                    displayBeverageDetails(str, menuLoader.coffee);
                    break;
                case "말차 라뗴":
                case "호지차 라떼":
                case "런던 포그":
                case "차이 라떼":
                case "센차":
                case "홍차":
                    displayBeverageDetails(str, menuLoader.tea);
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
            case "생강 꿀 차":
            case "과실 차":
            case "금귤 국화 차":
            case "히비스커스 차":
            case "망고 케일 스무디":
                displayBeverageDetails(str, menuLoader.nonCaffeinated);
                break;
            case "태국 야채 카레 해산물 링귀네":
            case "에그 베네딕트":
            case "오므라이스":
            case "버터넛 스쿼시 리조또":
            case "일본 카레":
            case "더치 치즈 샌드위치":
            case "봄철 셀러드":
            case "버터 크로아상":
                displayDishDetails(str, menuLoader.brunch);
                break;
            default:
                parseInputItemDetails3(str);
                break;
        }
    }

    //EFFECTS: extension of parseInputItemDetails
    private void parseInputItemDetails3(String str) {
        switch (str) {
            case "키나코 모찌":
            case "라즈베리 피스타치오 크림 타르트":
            case "바나나 크림 파이":
            case "고구마 크레페":
            case "Hojicha Parfait":
            case "Chestnut Cake":
            case "Tofu Ice Cream":
                displayDishDetails(str, menuLoader.dessert);
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
                System.out.println("regular\t\t$" + beverage.getPrice());
                System.out.println("large  \t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE));

                System.out.println(
                        "\n'" + REGULAR_SIZE_COMMAND + "' -> add regular " + beverage.getName() + " to order");
                System.out.println("'" + LARGE_SIZE_COMMAND + "' -> add large " + beverage.getName() + " to order");
            } else {
                System.out.println("hot \t\t$" + beverage.getPrice());
                System.out.println("iced\t\t$" + (beverage.getPrice() + Beverage.UPGRADE_PRICE));

                System.out.println("\n'" + HOT_TEMP_COMMAND + "' -> add hot " + beverage.getName() + " to order");
                System.out.println("'" + COLD_TEMP_COMMAND + "' -> add iced " + beverage.getName() + " to order");
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
                b.setSize(Beverage.UPGRADE);
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
                b.setTemperature(Beverage.UPGRADE);
                addItemAndPrintConfirmation("iced", b);
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
        System.out.println("\n'" + CAFE_MENU_COMMAND + "'  -> menuLoader menuLoader");
        System.out.println("'" + HOME_COMMAND + "'  -> home page");
        if (order.size() != 0) {
            System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
        }
    }

    //EFFECTS: displays details for non customizable menuLoader items
    private void displayItemNotCustomizableDetails(MenuItem item) {
        System.out.println("\n" + item.getName() + "\t\t$" + item.getPrice() + "");
        System.out.println("\n'" + ADD_TO_ORDER_COMMAND + "' -> add " + item.getName() + " to order");
    }

    //MODIFIES: this
    //EFFECTS: prints line to confirm item has been added to order
    private void addItemAndPrintConfirmation(String command, MenuItem item) {
        order.addItem(item);
        if (command.equals(ADD_TO_ORDER_COMMAND)) {
            System.out.println("\n" + item.getName() + " has been added to your order!");
        } else {
            System.out.println(command + " " + item.getName() + " has been added to your order!");
        }
        printGeneralInstructions();
    }

    //EFFECTS: prints instructions for home page and menuLoader
    private void printPostPaymentInstructions() {
        System.out.println("-> '" + HOME_COMMAND + "'");
        System.out.println("-> '" + CAFE_MENU_COMMAND + "'");
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
