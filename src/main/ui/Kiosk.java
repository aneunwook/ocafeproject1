package ui;

import model.Order;

import java.util.Scanner;

public class Kiosk {

    //format of fields and constructor taken from FitLifeGym.ui.Kiosk
    private static final String PLACE_ORDER_COMMAND = "place order";
    private static final String HOME_COMMAND = "home";
    private static final String CATEGORIES_COMMAND = "categories";
    private static final String VIEW_ORDER_COMMAND = "view order";
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

    public Kiosk(Cafe cafe) {
        input = new Scanner(System.in);
        runProgram = true;
        this.cafe = cafe;
    }
}
