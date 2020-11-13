package ui;

import ui.tabs.HomeTab;
import ui.tabs.MenuTab;

import javax.swing.*;

public class OCafe extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int MENU_TAB_INDEX = 1;
    public static final int ORDER_TAB_INDEX = 2;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;

    protected MenuLoader menuLoader;

    private JTabbedPane sidebar;

    public OCafe() {
        super("OCafe");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuLoader = new MenuLoader();

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void loadTabs() {
        JPanel homeTab = new HomeTab(this);
        JPanel menuTab = new MenuTab(this);
//        JPanel reportTab = new OrderTab(this);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(menuTab, MENU_TAB_INDEX);
        sidebar.setTitleAt(MENU_TAB_INDEX, "Menu");
//        sidebar.add(reportTab, ORDER_TAB_INDEX);
//        sidebar.setTitleAt(ORDER_TAB_INDEX, "Report");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    public MenuLoader getMenuLoader() {
        return menuLoader;
    }

    public static void main(String[] args) {
        new OCafe();
    }
}
