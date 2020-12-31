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

    private static final int CATEGORY_SELECTION_HEIGHT = 50;

    private JPanel categorySelectorPane;
    private JPanel categoryContainer;
    private JPanel itemDetailsContainer;

    private GridBagLayout gridBagLayout;
    private JLabel title;

    // creates menu tab with coffee category selected
    public MenuTab(OCafe controller) {
        super(controller);
        setBorder(BorderFactory.createEmptyBorder(20, 20,30,25));

        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        placeTitle();

        placeCategoryButtons();

        placeItemDetailsContainer();

        placeCategoryContainer();

        setNewCategorySelectedConfiguration(coffee);
    }

    //EFFECTS: creates title at top of console
    private void placeTitle() {
        title = new JLabel();
        setTitle("MENU");

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;

        add(title, c);
    }

    //EFFECTS: creates buttons for each menu category that change display of categoryContainer and title when clicked
    private void placeCategoryButtons() {
        categorySelectorPane = initializeDefaultPanel();
//        categorySelectorPane.setPreferredSize(new Dimension(WIDTH, CATEGORY_SELECTION_HEIGHT));
//        categorySelectorPane.setBorder(BorderFactory.createEmptyBorder(10, 0,0,0));

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
        categoryContainer = initializeDefaultPanel();
        add(categoryContainer);
    }

    //EFFECTS: creates container for item details pane
    private void placeItemDetailsContainer() {
        itemDetailsContainer = initializeDefaultPanel();
        add(itemDetailsContainer);
    }

    //EFFECTS: sets the title displayed at the top of MenuTab
    private void setTitle(String category) {
        title.setText(category);
        title.setFont(new Font("", Font.PLAIN, 16));
        title.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: creates a panel of buttons representing each menu item in a category,
    //         buttons display item name and price, displays further details when clicked
    private void setNewCategorySelectedConfiguration(String[] category) {
        setNewCategoryGridBagConstraints();
        CategoryPane cp = new CategoryPane(this, getController(), category);
        setContainer(categoryContainer, cp);

        itemDetailsContainer.removeAll();
        itemDetailsContainer.revalidate();
    }

    //MODIFIED: this
    //EFFECTS: sets layout to show the category panel and the item details panel
    //         removes previous panel and adds parameter to itemDetailsContainer
    public void setShowItemDetailsConfiguration(ItemDetailsPane p) {
        GridBagConstraints categoryConstraints = new GridBagConstraints();
        categoryConstraints.weightx = 0.5;
        categoryConstraints.weighty = 1.0;
        categoryConstraints.gridx = 0;
        categoryConstraints.gridy = 2;
        categoryConstraints.gridheight = 9;
        categoryConstraints.fill = GridBagConstraints.HORIZONTAL;
        categoryConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        gridBagLayout.setConstraints(categoryContainer, categoryConstraints);

        CategoryPane cp = (CategoryPane)categoryContainer.getComponent(0);
        cp.setPreferredSize(new Dimension(CategoryPane.DISPLAY_DETAILS_WIDTH, ITEM_AND_CATEGORY_DIM.height));
        categoryContainer.revalidate();

        GridBagConstraints itemDetailsConstraints = new GridBagConstraints();
        itemDetailsConstraints.weightx = 0.5;
        itemDetailsConstraints.gridx = 1;
        itemDetailsConstraints.gridy = 2;
        itemDetailsConstraints.gridheight = 9;
        itemDetailsConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        gridBagLayout.setConstraints(itemDetailsContainer, itemDetailsConstraints);
        setContainer(itemDetailsContainer, p);
    }

    //MODIFIES: this
    //EFFECTS: replaces previous panel in container with parameter p
    //https://stackoverflow.com/questions/9401353/how-to-change-the-jpanel-in-a-jframe-at-runtime
    private void setContainer(JPanel container, Tab p) {
        container.removeAll();
        container.setSize(p.getSize());
        container.add(p);
        container.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: sets GridBagConstraints for categoryContainer and itemDetailsContainer to only display categoryContainer
    private void setNewCategoryGridBagConstraints() {
        GridBagConstraints categoryConstraints = new GridBagConstraints();
        categoryConstraints.weightx = 1.0;
        categoryConstraints.weighty = 1.0;
        categoryConstraints.gridx = 0;
        categoryConstraints.gridy = 2;
        categoryConstraints.gridwidth = 2;
        categoryConstraints.gridheight = 9;
        categoryConstraints.fill = GridBagConstraints.BOTH;
        categoryConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagLayout.setConstraints(categoryContainer, categoryConstraints);
        gridBagLayout.setConstraints(itemDetailsContainer, new GridBagConstraints());

    }

    //action listener for selector pane
    private class CategorySelector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case COFFEE:
                    setNewCategorySelectedConfiguration(coffee);
                    break;
                case TEA:
                    setNewCategorySelectedConfiguration(tea);
                    break;
                case NONCAFFEINATED:
                    setNewCategorySelectedConfiguration(noncaffeinated);
                    break;
                case BRUNCH:
                    setNewCategorySelectedConfiguration(brunch);
                    break;
                case DESSERT:
                    setNewCategorySelectedConfiguration(dessert);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + buttonPressed);
            }
            setTitle(buttonPressed);
            controller.playSound("./data/sounds/Morse.wav");
        }
    }

}
