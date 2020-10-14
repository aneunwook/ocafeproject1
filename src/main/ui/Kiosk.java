package ui;

import model.Order;

import java.util.Scanner;

public class Kiosk {

    private static final String PLACE_ORDER_COMMAND = "p";
    private static final String SIGN_IN_COMMAND = "s";
    private static final String CREATE_ACCOUNT_COMMAND = "c";
    private static final String HOME_COMMAND = "home";
    private static final String CAFE_MENU_COMMAND = "menu";
    private static final String VIEW_ORDER_COMMAND = "order";
    private static final String COFFEE_COMMAND = "coffee";
    private static final String[] coffee = {"espresso", "americano", "macchiato", "latte"};
    private static final String TEA_COMMAND = "tea";
    private static final String[] tea = {"matcha", "hojicha", "london", "chai"};
    private static final String NONCAFFEINATED_COMMAND = "noncaffeinated";
    private static final String[] noncaffeinated = {"honey ginger", "fruit", "kumquat", "hibiscus", "mango"};
    private static final String BRUNCH_COMMAND = "brunch";
    private static final String[] brunch = {"thai", "benny", "omurice", "squash"};
    private static final String DESSERT_COMMAND = "dessert";
    private static final String[] dessert = {"kinako", "tart", "pie", "sweet"};
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
    }

    //EFFECTS: parses user input until user quits
    public void handleUserInput() {
        System.out.println("Home Page");
        homePage();
        String command;

        while (runProgram) {
            if (input.hasNext()) {
                command = input.nextLine();
                command = makePrettyString(command);
                parseInput(command);
            }
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    private void parseInput(String str) {
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
                    displayCoffee();
                    break;
                case TEA_COMMAND:
                    displayTea();
                    break;
                case NONCAFFEINATED_COMMAND:
                    displayNonCaffeinated();
                    break;
                case BRUNCH_COMMAND:
                    displayBrunch();
                    break;
                case DESSERT_COMMAND:
                    displayDessert();
                    break;
                case QUIT_COMMAND:
                    runProgram = false;
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
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
        System.out.println("\nenter one of:");
        System.out.println("\t'" + COFFEE_COMMAND + "'");
        System.out.println("\t'" + TEA_COMMAND + "'");
        System.out.println("\t'" + NONCAFFEINATED_COMMAND + "'");
        System.out.println("\t'" + BRUNCH_COMMAND + "'");
        System.out.println("\t'" + DESSERT_COMMAND + "'");
        System.out.println("\n'" + HOME_COMMAND + "' -> home page");
        System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
    }

    // displays order !!!
    private void displayOrder() {}

    // displays coffee menu
    private void displayCoffee() {
        System.out.println("\nselect from:");
        System.out.println("\t0 -> espresso");
        System.out.println("\t1 -> americano");
        System.out.println("\t2 -> macchiato");
        System.out.println("\t3 -> latte");
        System.out.println("\t4 -> iced coffee");
        System.out.println("\t5 -> cold brew");
        printGeneralInstructions();
    }

    // displays tea menu
    private void displayTea() {
        System.out.println("\nselect from:");
        System.out.println("\t0 -> matcha latte");
        System.out.println("\t1 -> hojicha latte");
        System.out.println("\t2 -> london fog");
        System.out.println("\t3 -> chai latte");
        System.out.println("\t4 -> oolong milk tea");
        System.out.println("\t5 -> genmaicha");
        System.out.println("\t6 -> black tea");
        printGeneralInstructions();
    }

    // displays noncaffeinated menu
    private void displayNonCaffeinated() {
        System.out.println("\nselect from:");
        System.out.println("\t0 -> honey ginger tea");
        System.out.println("\t1 -> fruit tea");
        System.out.println("\t2 -> kumquat chrysanthemum tea");
        System.out.println("\t3 -> hibiscus kombucha");
        System.out.println("\t4 -> mango kale smoothie");
        printGeneralInstructions();
    }

    // displays brunch menu
    private void displayBrunch() {
        System.out.println("\nselect from:");
        System.out.println("\t0 -> thai green curry seafood linguine");
        System.out.println("\t1 -> eggs benny");
        System.out.println("\t2 -> beef omurice");
        System.out.println("\t3 -> butternut squash risotto");
        System.out.println("\t4 -> japanese curry rice");
        System.out.println("\t5 -> dutch cheese sandwich");
        System.out.println("\t6 -> spring salad");
        System.out.println("\t7 -> butter croissant");
        printGeneralInstructions();
    }

    // displays dessert menu
    private void displayDessert() {
        System.out.println("\nselect from:");
        System.out.println("\t0 -> kinako mochi");
        System.out.println("\t1 -> raspberry pistachio tart");
        System.out.println("\t2 -> sweet potato crepe");
        System.out.println("\t3 -> hojicha parfait");
        System.out.println("\t4 -> chestnut cake");
        System.out.println("\t5 -> tofu ice cream");
        printGeneralInstructions();
    }

    // displays instructions for home page and view order
    private void printGeneralInstructions() {
        System.out.println("\n'" + CAFE_MENU_COMMAND + "' -> cafe menu");
        System.out.println("'" + HOME_COMMAND + "' -> home page");
        System.out.println("'" + VIEW_ORDER_COMMAND + "' -> view order");
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
