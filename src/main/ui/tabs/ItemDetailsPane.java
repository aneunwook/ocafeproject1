package ui.tabs;

import model.AdditionalOptions;
import model.Beverage;
import model.Dish;
import model.MenuItem;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemDetailsPane extends JPanel {
    private static final String REGULAR_SIZE = "Regular";
    private static final String LARGE = "Large";
    private static final String HOT = "Hot";
    private static final String ICED = "Iced";

    Beverage beverage; //new beverage instance matching selected item
    Dish dish;

    JButton addToOrderButton;

    public ItemDetailsPane(String itemName) {
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        JLabel name = new JLabel(itemName);
        name.setFont(new Font("Serif", Font.PLAIN, 30));
        add(name);
    }

    //beverage details pane constructor
    public ItemDetailsPane(String itemName, List<Beverage> type) {
        this(itemName);
        Beverage b = getBeverageByName(itemName, type);
        beverage = new Beverage(b.getName(), b.getPrice(), b.getSize(), b.getTemperature());

        if (beverage.isSizeCustomizable()) {
            placeBeverageOptionsButtons(REGULAR_SIZE, LARGE);
        } else if (beverage.isTemperatureCustomizable()) {
            placeBeverageOptionsButtons(HOT, ICED);
        }
        placeQuantityComboBox(beverage);
        placeAddToOrderButton(beverage);
    }

    //dish details pane constructor !!!fix dummy variable
    public ItemDetailsPane(String itemName, List<Dish> type, int dummyVar) {
        this(itemName);
        Dish d = getDishByName(itemName, type);
        dish = new Dish(d.getName(), d.getPrice());
        for (AdditionalOptions addOn : d.getOptions()) {
            dish.addSideToOptions(addOn);
        }
        //!!!
    }

    private void placeBeverageOptionsButtons(String regular, String upgrade) {
        String regularLabel = String.format("%-30s $%.2f", regular, beverage.getPrice());
        String upgradeLabel = String.format("%-30s $%.2f", upgrade, beverage.getPrice() + Beverage.UPGRADE_PRICE);

        JRadioButton regularButton = new JRadioButton(regularLabel);
        JRadioButton upgradeButton = new JRadioButton(upgradeLabel);
        regularButton.setActionCommand(regular);
        upgradeButton.setActionCommand(upgrade);

        regularButton.addActionListener(new BeverageCustomizer());
        upgradeButton.addActionListener(new BeverageCustomizer());

        ButtonGroup group = new ButtonGroup();
        group.add(regularButton);
        group.add(upgradeButton);

        add(regularButton);
        add(upgradeButton);
    }

    //creates combo box representing quantities of item,
    //based on selected item, updates the quantity of item and
    //text displayed on Add to Order button to reflect price change
    private void placeQuantityComboBox(MenuItem item) {
        JComboBox quantityBox = new JComboBox();
        for (int i = 1; i <= 100; i++) {
            quantityBox.addItem(new Integer(i));
        }
        quantityBox.setSelectedIndex(0);

        quantityBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int q = (int) quantityBox.getSelectedItem();
                item.setQuantity(q);
                updatePriceDisplay(item);
            }
        });

        add(quantityBox);
    }

    //creates add to order button that adds item to order when pressed
    private void placeAddToOrderButton(MenuItem item) {
        addToOrderButton = new JButton();
        updatePriceDisplay(item);

        addToOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //!!!
            }
        });

        add(addToOrderButton);
    }



    private void displayDishDetails(String itemName, List<Dish> type) {
        Dish dish = getDishByName(itemName, type);
        if (dish.getOptions().size() == 0) {
            displayItemNotCustomizableDetails(dish);
        } else {
            System.out.println("\n" + dish.getName() + "\t\t$" + dish.getPrice() + "");
            for (AdditionalOptions addOn : dish.getOptions()) {
                System.out.println("" + addOn.getName() + "\t\t+$" + addOn.getPrice() + "");
            }
            System.out.println("\nadd to your order:");
            System.out.println("'0' -> naked " + dish.getName() + "");
            for (int i = 0; i < dish.getOptions().size(); i++) {
                System.out.println("'" + (i + 1) + "' -> " + dish.getOptions().get(i).getName() + " " + dish.getName());
            }
        }
        printGeneralInstructions();
        //handle input
        int num;
        String s = input.nextLine();

        try {
            num = Integer.parseInt(s);
            parseInputAddDishToOrder(num, dish);
        } catch (NumberFormatException e) {
            parseInputNavigate(s);
        }
    }


    private class BeverageCustomizer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case REGULAR_SIZE:
                    beverage.setSize(Beverage.REGULAR);
                    break;
                case LARGE:
                    beverage.setSize(Beverage.UPGRADE);
                    break;
                case HOT:
                    beverage.setTemperature(Beverage.REGULAR);
                    break;
                case ICED:
                default:
                    beverage.setTemperature(Beverage.UPGRADE);
                    break;
            }
            updatePriceDisplay(beverage);
        }
    }

    private void updatePriceDisplay(MenuItem item) {
        String label = String.format("%-20s $%.2f", "Add to Order", item.getPrice());
        addToOrderButton.setText(label);
    }


    //MODIFIES: this
    //EFFECTS: adds dish with customization (if any) to order
    private void parseInputAddDishToOrder(Integer num, Dish dish) {
        Dish d = new Dish(dish.getName(), dish.getPrice());
        for (AdditionalOptions addOn : dish.getOptions()) {
            d.addSideToOptions(addOn);
        }
        if ((num <= d.getOptions().size()) && (num >= 0)) {
            if (num != 0) {
                d.selectAddOn(dish.getOptions().get(num - 1));
                addItemAndPrintConfirmation(d.getSelected().getName(), d);
            } else {
                order.addItem(d);
                System.out.println("\nnaked " + d.getName() + " has been added to your order!");
                printGeneralInstructions();
            }
        } else {
            System.out.println("Invalid selection, please try again.");
        }
    }


    //EFFECTS: returns the Beverage in a category if already there,
    //         if not, returns null
    private Beverage getBeverageByName(String name, List<Beverage> category) {
        for (Beverage b : category) {
            if (name.equals(b.getName())) {
                return b;
            }
        }
        return null;
    }

    //EFFECTS: returns the Dish in a category if already there,
    //         if not, returns null
    private Dish getDishByName(String name, List<Dish> category) {
        for (Dish d : category) {
            if (name.equals(d.getName())) {
                return d;
            }
        }
        return null;
    }
}
