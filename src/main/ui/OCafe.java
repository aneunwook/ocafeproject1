package ui;

import model.Account;
import model.Order;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.HomeTab;
import ui.tabs.MenuTab;
import ui.tabs.OrderTab;
import ui.tabs.Tab;
import ui.tabs.WeatherTab;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Calendar;

public class OCafe extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int MENU_TAB_INDEX = 1;
    public static final int ORDER_TAB_INDEX = 2;
    public static final int WEATHER_TAB_INDEX = 3;

    public static final int WIDTH = 1100;//
    public static final int HEIGHT = 800;
//
    protected MenuLoader menuLoader;
    protected Account account;//
    protected JsonWriter writer;
    protected JsonReader reader;
    protected Order order;  

    private JTabbedPane tabbedPane;

    // creates new OCafe object
    public OCafe() throws IOException, SAXException, ParserConfigurationException{
        super("OCafe");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuLoader = new MenuLoader();
        order = new Order();

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);

        loadTabs();
        
        
        JLabel labelOptions = new JLabel(WeatherTab.year + " " + WeatherTab.mon + " " + WeatherTab.day + " " + WeatherTab.weather);
        labelOptions.setBounds(550, 10, 300, 30);
        labelOptions.setFont(new Font("",Font.PLAIN,14));
        add(labelOptions);
        add(tabbedPane);

        JLabel labelOptions2 = new JLabel("현재 기온 :   " + WeatherTab.nowTMPValue  + "   최저 기온 : " + WeatherTab.todayTMN + "   최고 기온 : " + WeatherTab.todayTMX);
        labelOptions2.setBounds(1550, 10, 450, 30);
        labelOptions2.setFont(new Font("",Font.PLAIN,14));
        add(labelOptions2);
        add(tabbedPane);

        
        //현재 시각 뜨는건데 스윙 특성상 동기화는 안됨
        JLabel labelOptions3 = new JLabel(WeatherTab.now);
        labelOptions3.setBounds(938, 10, 300, 30);
        labelOptions3.setFont(new Font("",Font.PLAIN,17));
        add(labelOptions3);
        add(tabbedPane);
        
        setVisible(true);
    }
    
    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void loadTabs() throws IOException, SAXException, ParserConfigurationException {
        Tab homeTab = new HomeTab(this);
        Tab menuTab = new MenuTab(this);
        Tab orderTab = new OrderTab(this);
        Tab weatherTab = new WeatherTab(this);

        loadTab(homeTab, HOME_TAB_INDEX, "HOME");
        loadTab(menuTab, MENU_TAB_INDEX, "MENU");
        loadTab(orderTab, ORDER_TAB_INDEX, "ORDER");
        loadTab(weatherTab, WEATHER_TAB_INDEX, "Best");
//      loadTab(tempTab, 4, WeatherTab.weather);
 //       loadTab(tempTab, 4, WeatherTab.weather + ", " + WeatherTab.obsrValue);
    }

    private void loadTab(Tab t, int index, String title) {
        tabbedPane.add(t, index);
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(100, 50));
        label.setFont(new Font("", Font.PLAIN, 14));
        tabbedPane.setTabComponentAt(index, label);
    }

    // refreshes tab at index
    public void refreshTab(int tabIndex) {
        tabbedPane.setComponentAt(tabIndex, getTab(tabIndex));
        tabbedPane.getComponentAt(tabIndex).revalidate();
    }
    

    //MODIFIES: this
    //EFFECTS: creates an account associated with parameter name
    public void handleCreateAccount(String name) {
        if (name == null || name.equals("")) {
            displayAccountError("Cannot create account. \nPlease try again with a valid name.");
        } else {
            try {
                account = new Account(name);
                writer = new JsonWriter(account.getFile());
                reader = new JsonReader(account.getFile());
                readAccount();
                // account with given name already exists
                displayAccountError(
                        "An account with this name already exists. \nSign in or try again with a different name.");

            } catch (IOException exception) {
                // no account exists with given name
                try {
                    writer.open();
                    writer.write(account);
                    writer.close();
                    refreshTab(HOME_TAB_INDEX);
                } catch (FileNotFoundException e) {
                    System.out.println("Your name cannot contain backslashes or quotation marks");
                }
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: if account with input name exists, sets as current account
    public void handleSignIn(String name) {
        try {
            writer = new JsonWriter("./data/" + name + ".json");
            reader = new JsonReader("./data/" + name + ".json");
            readAccount();
            refreshTab(HOME_TAB_INDEX);

        } catch (IOException e) {
            handleSignOut();
            displayAccountError(
                    "No account with this name exists. \nTry again with a different name or create an account.");
        }
    }

    //MODIFIES: this
    //EFFECTS: if account is not already null, set account, writer, and reader to null and display start page,
    //         otherwise does nothing
    public void handleSignOut() {
        if (account != null) {
            account = null;
            writer = null;
            reader = null;
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

        } catch (FileNotFoundException e) {
            System.out.println("Order could not be saved");
        }
    }

    // reads account from json file and sets to account field
    public void readAccount() throws IOException {
        account = reader.read();
    }

    // assigns order field to a new order
    public void makeNewOrder() {
        order = new Order();
    }

    // clears order history for this account//
    public void clearHistory() {
        account.clearHistory();
        try {
            writer.open();
            writer.write(account);
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error clearing history");
        }
    }

    // plays error sound and displays error pop up with message
    private void displayAccountError(String message) {
        handleSignOut();
        playSound("./data/sounds/Basso.wav");
        JOptionPane.showMessageDialog(this, message, null, JOptionPane.ERROR_MESSAGE);
    }

    //plays sound from file soundName
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }

    //EFFECTS: returns tabbed pane of this UI
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public MenuLoader getMenuLoader() {
        return menuLoader;
    }

    public Account getAccount() {
        return account;
    }

    public Order getOrder() {
        return order;
    }

    // retrieves a new tab according to index
    private Tab getTab(int index) {
        if (index == HOME_TAB_INDEX) {
            return new HomeTab(this);
        } else if (index == MENU_TAB_INDEX) {
            return new MenuTab(this);
        } else {
            return new OrderTab(this);
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        new OCafe();
    }
}
