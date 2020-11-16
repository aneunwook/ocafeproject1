package ui.tabs;

import model.AdditionalOptions;
import model.Beverage;
import model.Dish;
import model.MenuItem;
import ui.OCafe;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemDetailsPane extends Tab {
    private static final String REGULAR_SIZE = "Regular";
    private static final String LARGE = "Large";
    private static final String HOT = "Hot";
    private static final String ICED = "Iced";

    private static final String NO_ADD_ONS_OPTION = "Naked";

    Beverage beverageSelected;
    Dish dishSelected;

    JButton addToOrderButton;

    public ItemDetailsPane(String itemName, OCafe controller) {
        super(controller);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        Dimension d = new Dimension(OCafe.WIDTH / 3, OCafe.HEIGHT * 3 / 4);
        setPreferredSize(d);

//https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel/32885963#32885963
        String sep = System.getProperty("file.separator");
        String idk = System.getProperty("user.dir");
        ImageIcon image = new ImageIcon(new ImageIcon("./images/Japanese Curry Rice.jpg").getImage().getScaledInstance(450, 300, Image.SCALE_DEFAULT));

        JLabel icon = new JLabel(image);
        Dimension d2 = new Dimension(OCafe.WIDTH / 3, 300);
        icon.setPreferredSize(d2);
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(icon);

        JLabel name = new JLabel(itemName);
        name.setFont(new Font("Serif", Font.PLAIN, 30));
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(name);
    }

    // beverage details pane constructor
    public ItemDetailsPane(String itemName, List<Beverage> type, OCafe controller) {
        this(itemName, controller);
        Beverage b = getBeverageByName(itemName, type);
        beverageSelected = new Beverage(b.getName(), b.getPrice(), b.getSize(), b.getTemperature());

        if (beverageSelected.isSizeCustomizable()) {
            placeBeverageOptionsButtons(REGULAR_SIZE, LARGE);
        } else if (beverageSelected.isTemperatureCustomizable()) {
            placeBeverageOptionsButtons(HOT, ICED);
        }

        placeQuantityComboBox(beverageSelected);
        placeAddToOrderButton(beverageSelected);
    }

    // dish details pane constructor !!!fix dummy variable
    public ItemDetailsPane(String itemName, List<Dish> type, OCafe controller, int dummyVar) {
        this(itemName, controller);
        Dish d = getDishByName(itemName, type);
        dishSelected = new Dish(d.getName(), d.getPrice());
        for (AdditionalOptions addOn : d.getOptions()) {
            dishSelected.addSideToOptions(addOn);
        }

        if (dishSelected.getOptions().size() != 0) {
            placeDishOptionsButtons();
        }

        placeQuantityComboBox(dishSelected);
        placeAddToOrderButton(dishSelected);
    }


    private void placeBeverageOptionsButtons(String regular, String upgrade) {
//        String regularLabel = String.format("%-30s $%.2f", regular, beverageSelected.getPrice());
        String upgradeLabel = String.format("%-30s +$%.2f", upgrade, Beverage.UPGRADE_PRICE);

        JRadioButton regularButton = new JRadioButton(regular);
        JRadioButton upgradeButton = new JRadioButton(upgradeLabel);
        regularButton.setSelected(true);
//        regularButton.setActionCommand(regular);
        upgradeButton.setActionCommand(upgrade);

        regularButton.addActionListener(new BeverageCustomizer());
        upgradeButton.addActionListener(new BeverageCustomizer());

        ButtonGroup group = new ButtonGroup();
        group.add(regularButton);
        group.add(upgradeButton);

        regularButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        upgradeButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(regularButton);
        add(upgradeButton);
    }

    private void placeDishOptionsButtons() {
        JLabel addOnsTitle = new JLabel("Add Ons:");
        add(addOnsTitle);

        ButtonGroup group = new ButtonGroup();
        JRadioButton naked = new JRadioButton(NO_ADD_ONS_OPTION);
        naked.addActionListener(new DishCustomizer());
        group.add(naked);
        add(naked);

        for (AdditionalOptions addOn : dishSelected.getOptions()) {
            String addOnLabel = String.format("%-30s +$%.2f", addOn.getName(), addOn.getPrice());
            JRadioButton b = new JRadioButton(addOnLabel);
            b.setSelected(true);
            b.setActionCommand(addOn.getName());
            b.addActionListener(new DishCustomizer());
            group.add(b);
            add(b);
        }
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
                int q = (int)quantityBox.getSelectedItem();
                item.setQuantity(q);
                updatePriceDisplay(item);
            }
        });

        quantityBox.setAlignmentX(Component.LEFT_ALIGNMENT);
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

        addToOrderButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(addToOrderButton);
    }

    private class BeverageCustomizer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case REGULAR_SIZE:
                    beverageSelected.setSize(Beverage.REGULAR);
                    break;
                case LARGE:
                    beverageSelected.setSize(Beverage.UPGRADE);
                    break;
                case HOT:
                    beverageSelected.setTemperature(Beverage.REGULAR);
                    break;
                case ICED:
                default:
                    beverageSelected.setTemperature(Beverage.UPGRADE);
                    break;
            }
            updatePriceDisplay(beverageSelected);
        }
    }

    private class DishCustomizer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            if (buttonPressed.equals(NO_ADD_ONS_OPTION)) {
                dishSelected.unselectAddOn();
            } else {
                for (AdditionalOptions a : dishSelected.getOptions()) {
                    if (buttonPressed.equals(a.getName())) {
                        dishSelected.selectAddOn(a);
                    }
                }
            }
            updatePriceDisplay(dishSelected);
        }
    }

    private void updatePriceDisplay(MenuItem item) {
        String label = String.format("%-20s $%.2f", "Add to Order", item.getPrice());
        addToOrderButton.setText(label);
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
