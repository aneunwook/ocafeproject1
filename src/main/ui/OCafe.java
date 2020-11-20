package ui;

import model.Account;
import model.Order;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.HomeTab;
import ui.tabs.MenuTab;
import ui.tabs.OrderTab;
import ui.tabs.Tab;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class OCafe extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int MENU_TAB_INDEX = 1;
    public static final int ORDER_TAB_INDEX = 2;

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;

    protected MenuLoader menuLoader;
    protected Account account;
    protected JsonWriter writer;
    protected JsonReader reader;
    protected Order order;

    private JTabbedPane sidebar;

    public OCafe() {
        super("OCafe");
        setSize(WIDTH, HEIGHT);
//        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuLoader = new MenuLoader();
        order = new Order();

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void loadTabs() {
        Tab homeTab = new HomeTab(this);
        Tab menuTab = new MenuTab(this);
        Tab orderTab = new OrderTab(this);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(menuTab, MENU_TAB_INDEX);
        sidebar.setTitleAt(MENU_TAB_INDEX, "Menu");
        sidebar.add(orderTab, ORDER_TAB_INDEX);
        sidebar.setTitleAt(ORDER_TAB_INDEX, "Order");
    }

    public void refreshTab(int tabIndex) {
        sidebar.setComponentAt(tabIndex, getTab(tabIndex));
        sidebar.getComponentAt(tabIndex).revalidate();
    }

    //MODIFIES: this
    //EFFECTS: creates an account associated with parameter name
    public void handleCreateAccount(String name) {
        try {
            account = new Account(name);
//            accountList.add(str);
            writer = new JsonWriter(account.getFile());
            reader = new JsonReader(account.getFile());
            writer.open();
            writer.write(account);
            writer.close();

            refreshTab(HOME_TAB_INDEX);

        } catch (FileNotFoundException e) {
            System.out.println("Your name cannot contain backslashes or quotation marks");
        }
    }

    //MODIFIES: this
    //EFFECTS: if account with input name exists, sets account field
    public void handleSignIn(String name) {
        try {
            writer = new JsonWriter("./data/" + name + ".json");
            reader = new JsonReader("./data/" + name + ".json");
            account = reader.read();

            refreshTab(HOME_TAB_INDEX);

        } catch (IOException e) {
            System.out.println("An account with name '" + name + "' cannot be found :(");
            //try again or create account!!!
        }
    }

    //MODIFIES: this
    //EFFECTS: saves order to account history
    public void saveOrder() {
        account.addOrder(order);
        try {
            writer.open();
            writer.write(account);
            writer.close();

            refreshTab(HOME_TAB_INDEX);

        } catch (FileNotFoundException e) {
            System.out.println("Order could not be saved");
        }
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    public MenuLoader getMenuLoader() {
        return menuLoader;
    }

    public Account getAccount() {
        return account;
    }

    public JsonWriter getWriter() {
        return writer;
    }

    public JsonReader getReader() {
        return reader;
    }

    public Order getOrder() {
        return order;
    }

    public static void main(String[] args) {
        new OCafe();
    }

    private Tab getTab(int index) {
        if (index == HOME_TAB_INDEX) {
            return new HomeTab(this);
        } else if (index == MENU_TAB_INDEX) {
            return new MenuTab(this);
        } else {
            return new OrderTab(this);
        }
    }
}
