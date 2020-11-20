package ui.tabs;

import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTab extends Tab {
    private static final String[] coffee = {"Espresso", "Americano", "Macchiato", "Latte", "Iced Coffee", "Cold Brew"};
    private static final String[] tea = {
            "Matcha Latte", "Hojicha Latte", "London Fog", "Chai Latte", "Sencha", "Black Tea"};
    private static final String[] noncaffeinated = {
            "Honey Ginger Tea", "Fruit Tea", "Kumquat Chrysanthemum Tea", "Hibiscus Kombucha", "Mango Kale Smoothie"};
    private static final String[] brunch = {
            "Thai Green Curry Seafood Linguine", "Eggs Benedict", "Omurice", "Butternut Squash Risotto",
            "Japanese Curry Rice", "Dutch Cheese Sandwich", "Spring Salad", "Butter Croissant"};
    private static final String[] dessert = {
            "Kinako Mochi", "Raspberry Pistachio Cream Tart", "Banana Cream Pie", "Sweet Potato Crepe",
            "Hojicha Parfait", "Chestnut Cake", "Tofu Ice Cream"};

    private static final String COFFEE = "Coffee";
    private static final String TEA = "Tea";
    private static final String NONCAFFEINATED = "Noncaffeinated";
    private static final String BRUNCH = "Brunch";
    private static final String DESSERT = "Dessert";

    private static final String[] categories = {COFFEE, TEA, NONCAFFEINATED, BRUNCH, DESSERT};

    private JPanel categorySelectorPane;
    private JPanel categoryContainer;
    private JPanel itemDetailsContainer;

    public MenuTab(OCafe controller) {
        super(controller);

        setLayout(new GridBagLayout());

        placeTitle();

        placeCategoryButtons();

        placeCategoryContainer();

        placeItemDetailsContainer();
    }

    //EFFECTS: creates title at top of console
    private void placeTitle() {
        JLabel title = new JLabel("MENU", JLabel.CENTER);
        title.setSize(OCafe.WIDTH, OCafe.HEIGHT / 6);

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;

        add(title, c);
    }

    //EFFECTS: creates buttons for each menu category that change display of categoryContainer and title when clicked
    private void placeCategoryButtons() {
        categorySelectorPane = new JPanel();

        for (String s : categories) {
            JButton b = new JButton(s);
            b.addActionListener(new CategorySelector());
            categorySelectorPane.add(b);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;

        add(categorySelectorPane, c);
    }

    //EFFECTS: creates container for category pane
    private void placeCategoryContainer() {
        categoryContainer = new JPanel();
        setCategoryContainer(coffee);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 9;
        c.anchor = GridBagConstraints.FIRST_LINE_END;

        add(categoryContainer, c);
    }

    //EFFECTS: creates container for item details pane
    private void placeItemDetailsContainer() {
        itemDetailsContainer = new JPanel();

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 9;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(itemDetailsContainer, c);
    }

    //MODIFIES: this
    //EFFECTS: creates a panel of buttons representing each menu item in a category
    //         buttons display item name and price, displays further details when clicked
    //https://stackoverflow.com/questions/9401353/how-to-change-the-jpanel-in-a-jframe-at-runtime
    private void setCategoryContainer(String[] category) {
        categoryContainer.removeAll();
        CategoryPane p = new CategoryPane(this, getController(), category);
        categoryContainer.setSize(p.getSize());
        categoryContainer.add(p);
        categoryContainer.revalidate();
    }

    //EFFECTS: removes previous pane and adds parameter to itemDetailsPane
    public void setItemDetailsContainer(ItemDetailsPane p) {
        itemDetailsContainer.removeAll();
        itemDetailsContainer.setSize(p.getSize());
        itemDetailsContainer.add(p);
        itemDetailsContainer.revalidate();
    }


    //action listener for selector pane
    private class CategorySelector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case COFFEE:
                    setCategoryContainer(coffee);
                    break;
                case TEA:
                    setCategoryContainer(tea);
                    break;
                case NONCAFFEINATED:
                    setCategoryContainer(noncaffeinated);
                    break;
                case BRUNCH:
                    setCategoryContainer(brunch);
                    break;
                case DESSERT:
                    setCategoryContainer(dessert);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + buttonPressed);
            }
        }
    }

}
