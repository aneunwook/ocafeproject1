package ui.tabs;

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

    private static final String[] SIZES = {REGULAR_SIZE, LARGE};
    private static final String[] TEMPS = {HOT, ICED};

    Beverage beverage;

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
        beverage = getBeverageByName(itemName, type);

        if (beverage.isSizeCustomizable()) {
            placeBeverageOptionsButtons(REGULAR_SIZE, LARGE);
        } else if (beverage.isTemperatureCustomizable()) {
            placeBeverageOptionsButtons(HOT, ICED);
        }
        placeQuantityComboBox(beverage);
        placeAddOrderButton(beverage);
    }

    //dish details pane constructor !!!fix dummy variable
    public ItemDetailsPane(String itemName, List<Dish> type, int dummyVar) {
        this(itemName);
    }

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
            }
        });

        add(quantityBox);
    }

    private void placeAddOrderButton(MenuItem item) {
        String label = String.format("%-10s $%.2f", "Add to Order", item.getPrice());
        JButton addToOrderButton = new JButton(label);

    }

    private void placeBeverageOptionsButtons(String regular, String upgrade) {
        String regularCommand = String.format("%-20s $%.2f", regular, beverage.getPrice());
        String upgradeCommand = String.format("%-20s $%.2f", upgrade, beverage.getPrice() + Beverage.UPGRADE_PRICE);

        JRadioButton regularButton = new JRadioButton(regularCommand);
        JRadioButton upgradeButton = new JRadioButton(upgradeCommand);

        regularButton.addActionListener(new BeverageCustomizer(beverage));
        upgradeButton.addActionListener(new BeverageCustomizer(beverage));

        add(regularButton);
        add(upgradeButton);

        ButtonGroup group = new ButtonGroup();
        group.add(regularButton);
        group.add(upgradeButton);
    }

//    private void displayDishDetails(String itemName, List<Dish> type) {
//        Dish dish = getDishByName(itemName, type);
//        if (dish.getOptions().size() == 0) {
//            displayItemNotCustomizableDetails(dish);
//        } else {
//            System.out.println("\n" + dish.getName() + "\t\t$" + dish.getPrice() + "");
//            for (AdditionalOptions addOn : dish.getOptions()) {
//                System.out.println("" + addOn.getName() + "\t\t+$" + addOn.getPrice() + "");
//            }
//            System.out.println("\nadd to your order:");
//            System.out.println("'0' -> naked " + dish.getName() + "");
//            for (int i = 0; i < dish.getOptions().size(); i++) {
//                System.out.println("'" + (i + 1) + "' -> " + dish.getOptions().get(i).getName() + " " + dish.getName());
//            }
//        }
//        printGeneralInstructions();
//        //handle input
//        int num;
//        String s = input.nextLine();
//
//        try {
//            num = Integer.parseInt(s);
//            parseInputAddDishToOrder(num, dish);
//        } catch (NumberFormatException e) {
//            parseInputNavigate(s);
//        }
//    }


    private class BeverageCustomizer implements ActionListener {
        Beverage beverage;

        BeverageCustomizer(Beverage beverage) {
            this.beverage = beverage;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            Beverage b = new Beverage(
                    beverage.getName(), beverage.getPrice(), beverage.getSize(), beverage.getTemperature());
            switch (buttonPressed) {
                case REGULAR_SIZE:
                    b.setSize(Beverage.REGULAR);
                    break;
                case LARGE:
                    b.setSize(Beverage.UPGRADE);
                    break;
                case HOT:
                    b.setTemperature(Beverage.REGULAR);
                    break;
                case ICED:
                default:
                    b.setTemperature(Beverage.UPGRADE);
                    break;
            }
        }
    }


//    //MODIFIES: this
//    //EFFECTS: adds dish with customization (if any) to order
//    private void parseInputAddDishToOrder(Integer num, Dish dish) {
//        Dish d = new Dish(dish.getName(), dish.getPrice());
//        for (AdditionalOptions addOn : dish.getOptions()) {
//            d.addSideToOptions(addOn);
//        }
//        if ((num <= d.getOptions().size()) && (num >= 0)) {
//            if (num != 0) {
//                d.selectAddOn(dish.getOptions().get(num - 1));
//                addItemAndPrintConfirmation(d.getSelected().getName(), d);
//            } else {
//                order.addItem(d);
//                System.out.println("\nnaked " + d.getName() + " has been added to your order!");
//                printGeneralInstructions();
//            }
//        } else {
//            System.out.println("Invalid selection, please try again.");
//        }
//    }


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
