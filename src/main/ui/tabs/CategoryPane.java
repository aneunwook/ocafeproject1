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
    private MenuTab menuTab;

    public CategoryPane(MenuTab menuTab, OCafe controller, String[] category) {
        super(controller);
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEtchedBorder());
        setSize(WIDTH, OCafe.HEIGHT * 3 / 4);

        this.menuTab = menuTab;

        int size = category.length;
        for (int i = 0; i < size; i++) {
            JButton itemButton = new JButton(category[i]);       // !!!add price and actionListener
            itemButton.addActionListener(new ItemSelector());
            add(itemButton);
        }
    }

    //EFFECTS: creates new ItemDetailsPane and sets it in menu tab
    private void displayBeverageDetails(String itemName, List<Beverage> type) {
        ItemDetailsPane p = new ItemDetailsPane(itemName, type);
        menuTab.setItemDetailsContainer(p);
    }

    //EFFECTS: displays dish item details
    private void displayDishDetails(String itemName, List<Dish> type) {
        ItemDetailsPane p = new ItemDetailsPane(itemName, type, 1);
        menuTab.setItemDetailsContainer(p);
    }

    private class ItemSelector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
