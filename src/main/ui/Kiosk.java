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
    private static final String TEA_COMMAND = "tea";
    private static final String NONCAFFEINATED_COMMAND = "noncaffeinated";
    private static final String BRUNCH_COMMAND = "brunch";
    private static final String DESSERT_COMMAND = "dessert";
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
                    printClasses();
                    break;
                case COFFEE_COMMAND:
                    displayCoffeeMenu();
                    break;
                case TEA_COMMAND:
                    printCardioMachines();
                    break;
                case NONCAFFEINATED_COMMAND:
                    printWeights();
                    break;
                case BRUNCH_COMMAND:
                    printBrunch();
                    break;
                case DESSERT_COMMAND:
                    printDessert();
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

    // displays coffee menu
    private void displayCoffeeMenu() {
        System.out.println("\nselect from:");
        System.out.println("\t'" + PLACE_ORDER_COMMAND + "' -> place order");
        System.out.println("\t'" + SIGN_IN_COMMAND + "' -> sign in");
        System.out.println("\t'" + CREATE_ACCOUNT_COMMAND + "' -> create account")
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
}
