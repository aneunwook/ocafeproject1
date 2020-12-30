package ui.tabs;

import model.Beverage;
import model.Dish;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoryPane extends Tab {
    protected static final int DISPLAY_DETAILS_WIDTH = WIDTH * 3 / 5;
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_WIDTH = 400;

    private MenuTab menuTab;

    // creates panel with items within a category displayed in a grid
    public CategoryPane(MenuTab menuTab, OCafe controller, String[] category) {
        super(controller);

        setLayout(new GridLayout(0, 3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setPreferredSize(new Dimension(WIDTH, ITEM_AND_CATEGORY_DIM.height));

        this.menuTab = menuTab;

        placeItemButtons(category);
    }

    //MODIFIES: this
    //EFFECTS: sets the display layout to a column of item buttons
    public void setColumnLayout() {
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(ITEM_AND_CATEGORY_DIM);
    }

    // creates item buttons
    private void placeItemButtons(String[] category) {
        for (String itemName : category) {
            JButton itemButton = new JButton(itemName);
            itemButton.setLayout(new BoxLayout(itemButton, BoxLayout.Y_AXIS));
            itemButton.setMargin(new Insets(0,0,0,0));
            add(itemButton);
            itemButton.addActionListener(new ItemSelector());
        }
        placeItemImages();
    }

    // places images for each item to be displayed in their respective buttons
    private void placeItemImages() {
        for (Component c : getComponents()) {
            JButton itemButton = (JButton)c;
            Image itemImage = new ImageIcon("./data/images/" + itemButton.getText() + ".jpg").getImage();
            Dimension d = new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT);
            itemButton.add(loadImageJLabel(itemImage, d));

            JLabel name = new JLabel("   " + itemButton.getText());
            name.setFont(new Font("", Font.PLAIN, 15));
            name.setPreferredSize(new Dimension(IMAGE_WIDTH, 40));
            name.setMinimumSize(new Dimension(IMAGE_WIDTH, 40));
            name.setMaximumSize(new Dimension(IMAGE_WIDTH, 40));
            name.setAlignmentX(LEFT_ALIGNMENT);
            itemButton.add(name);
            itemButton.setActionCommand(itemButton.getText());
            itemButton.setText(null);

        }
    }

    //EFFECTS: creates new ItemDetailsPane and sets it in menu tab
    public void displayBeverageDetails(String itemName, List<Beverage> type) {
        ItemDetailsPane p = new ItemDetailsPane(itemName, type, this);
        menuTab.setShowItemDetailsConfiguration(p);
    }

    //EFFECTS: displays dish item details
    public void displayDishDetails(String itemName, List<Dish> type) {
        ItemDetailsPane p = new ItemDetailsPane(itemName, type, this, 1);
        menuTab.setShowItemDetailsConfiguration(p);
    }

    // selects item and sets the item details pane in menu tab
    private class ItemSelector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.playSound("./data/sounds/Morse.wav");
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case "Espresso":
                case "Americano":
                case "Macchiato":
                case "Latte":
                case "Iced Coffee":
                case "Cold Brew":
                    displayBeverageDetails(buttonPressed, controller.getMenuLoader().getCoffee());
                    break;
                case "Matcha Latte":
                case "Hojicha Latte":
                case "London Fog":
                case "Chai Latte":
                case "Sencha":
                case "Black Tea":
                    displayBeverageDetails(buttonPressed, controller.getMenuLoader().getTea());
                    break;
                default:
                    parseInputItemDetails2(buttonPressed);
                    break;
            }
        }

        //EFFECTS: extension of actionPerformed
        private void parseInputItemDetails2(String str) {
            switch (str) {
                case "Honey Ginger Tea":
                case "Fruit Tea":
                case "Kumquat Chrysanthemum Tea":
                case "Hibiscus Kombucha":
                case "Mango Kale Smoothie":
                    displayBeverageDetails(str, controller.getMenuLoader().getNonCaffeinated());
                    break;
                case "Thai Green Curry Seafood Linguine":
                case "Eggs Benedict":
                case "Omurice":
                case "Butternut Squash Risotto":
                case "Japanese Curry Rice":
                case "Dutch Cheese Sandwich":
                case "Spring Salad":
                case "Butter Croissant":
                    displayDishDetails(str, controller.getMenuLoader().getBrunch());
                    break;
                default:
                    parseInputItemDetails3(str);
                    break;
            }
        }

        //EFFECTS: extension of actionPerformed
        private void parseInputItemDetails3(String str) {
            switch (str) {
                case "Kinako Mochi":
                case "Raspberry Pistachio Cream Tart":
                case "Banana Cream Pie":
                case "Sweet Potato Crepe":
                case "Hojicha Parfait":
                case "Chestnut Cake":
                case "Tofu Ice Cream":
                    displayDishDetails(str, controller.getMenuLoader().getDessert());
                    break;
                default:
                    System.out.println("Invalid selection, please try again.");
                    break;
            }
        }
    }


}
