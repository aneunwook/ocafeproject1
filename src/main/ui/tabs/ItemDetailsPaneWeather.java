package ui.tabs;

import model.MenuItem;
import model.*;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// ItemDetailsPane Class 를 Weather 용에 맞춰 번거롭지 않게 클래스명과, 생성자를 편리하게 사용 할 수 있게 그냥 클래스를 추가함
public class ItemDetailsPaneWeather extends Tab {
    private static final String REGULAR_SIZE = "Regular";
    private static final String LARGE = "Large";
    private static final String HOT = "Hot";
    private static final String ICED = "Iced";
    private static final String NO_ADD_ONS_OPTION = "Naked";

    private static final Dimension IMAGE_DIMENSION = new Dimension(ITEM_AND_CATEGORY_DIM.width, 300);
    private static final int ADD_TO_ORDER_AREA_HEIGHT = 100;

    private final CategoryPane categoryPane;

    private Beverage beverageSelected;
    private List<Beverage> beverageType;

    private Dish dishSelected;
    private List<Dish> dishType;

    private JButton addToOrderButton;


    // creates a panel representing an item
    public ItemDetailsPaneWeather(CategoryPane cp) {
        super(cp.controller);
        categoryPane = cp;
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(ITEM_AND_CATEGORY_DIM);
    }

    // beverage details panel constructor
    public ItemDetailsPaneWeather(String itemName, List<Beverage> type, CategoryPane cp) {
        this(cp);
        beverageType = type;
        Beverage b = getBeverageByName(itemName, type);
        beverageSelected = new Beverage(b.getName(), b.getPrice(), b.getSize(), b.getTemperature());

        loadImageAndName(beverageSelected);

        if (beverageSelected.isSizeCustomizable()) {
            placeBeverageOptionsButtons(REGULAR_SIZE, LARGE);
        } else if (beverageSelected.isTemperatureCustomizable()) {
            placeBeverageOptionsButtons(HOT, ICED);
        }

        placeAddToOrderArea(beverageSelected);
    }

    // dish details panel constructor
    public ItemDetailsPaneWeather(String itemName, List<Dish> type, CategoryPane cp, int dummyVar) {
        this(cp);
        dishType = type;
        Dish d = getDishByName(itemName, type);
        dishSelected = new Dish(d.getName(), d.getPrice());
        for (AdditionalOptions addOn : d.getOptions()) {
            dishSelected.addSideToOptions(addOn);
        }

        loadImageAndName(dishSelected);

        if (dishSelected.getOptions().size() != 0) {
            placeDishOptionsButtons();
        }

        placeAddToOrderArea(dishSelected);
    }

    // loads image and name of an item
    private void loadImageAndName(MenuItem item) {
        Image originalImage = item.getImage();
        JLabel image = loadImageJLabel(originalImage, IMAGE_DIMENSION);
        add(image);

        JLabel name = new JLabel(" " + item.getName());
        name.setFont(new Font("", Font.PLAIN, 25));
        name.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(name);
    }

    // places radio buttons for beverage customization
    private void placeBeverageOptionsButtons(String regular, String upgrade) {
        String upgradeLabel = String.format("%-75s +$%.2f", upgrade, Beverage.UPGRADE_PRICE);

        JRadioButton regularButton = new JRadioButton(regular);
        JRadioButton upgradeButton = new JRadioButton(upgradeLabel);
        regularButton.setSelected(true);
        upgradeButton.setActionCommand(upgrade);

        regularButton.addActionListener(new BeverageCustomizer());
        upgradeButton.addActionListener(new BeverageCustomizer());

        regularButton.setBackground(backgroundColor);
        upgradeButton.setBackground(backgroundColor);

        ButtonGroup group = new ButtonGroup();
        group.add(regularButton);
        group.add(upgradeButton);

        regularButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        upgradeButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(regularButton);
        add(upgradeButton);
    }

    // places radio buttons for dish customization
    private void placeDishOptionsButtons() {
        ButtonGroup group = new ButtonGroup();
        JRadioButton naked = new JRadioButton(NO_ADD_ONS_OPTION);
        naked.setSelected(true);
        naked.addActionListener(new DishCustomizer());
        group.add(naked);
        add(naked);

        for (AdditionalOptions addOn : dishSelected.getOptions()) {
            String addOnLabel = String.format("%-65s +$%.2f", addOn.getName(), addOn.getPrice());
            JRadioButton b = new JRadioButton(addOnLabel);
            b.setBackground(backgroundColor);
            b.setActionCommand(addOn.getName());
            b.addActionListener(new DishCustomizer());
            group.add(b);
            add(b);
        }
    }

    // places panel area for quantity combo box and add to order button
    private void placeAddToOrderArea(MenuItem item) {
        add(Box.createVerticalGlue());
        JPanel area = new JPanel();
        area.setLayout(new BoxLayout(area, BoxLayout.X_AXIS));
        area.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        area.setPreferredSize(new Dimension(ITEM_AND_CATEGORY_DIM.width, ADD_TO_ORDER_AREA_HEIGHT));
        area.setMinimumSize(new Dimension(ITEM_AND_CATEGORY_DIM.width, ADD_TO_ORDER_AREA_HEIGHT));
        area.setMaximumSize(new Dimension(ITEM_AND_CATEGORY_DIM.width, ADD_TO_ORDER_AREA_HEIGHT));
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        area.add(placeQuantityComboBox(item));
        area.add(placeAddToOrderButton(item));
        add(area);
    }

    // returns combo box representing quantities of item,
    // based on selected item, updates the quantity of item and
    // text displayed on Add to Order button to reflect price change
    private JComboBox placeQuantityComboBox(MenuItem item) {
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
                controller.playSound("./data/sounds/Pop.wav");
            }
        });

        return quantityBox;
    }

    // returns add to order button that adds item to order when pressed
    private JButton placeAddToOrderButton(MenuItem item) {
        addToOrderButton = new JButton();
        updatePriceDisplay(item);

        addToOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order order = controller.getOrder();
                order.addItem(item);
                controller.refreshTab(OCafe.ORDER_TAB_INDEX);

                if (beverageSelected != null) {
                    categoryPane.displayBeverageDetailsWeather(item.getName(), beverageType);
                } else {
                    categoryPane.displayDishDetailsWeather(item.getName(), dishType);
                }
                controller.playSound("./data/sounds/Bottle.wav");
            }
        });

        return addToOrderButton;
    }

    // customizes beverages according to selection
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
            controller.playSound("./data/sounds/Pop.wav");
        }
    }

    // customizes dishes according to selection
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
            controller.playSound("./data/sounds/Pop.wav");
        }
    }

    // updates price displayed
    private void updatePriceDisplay(MenuItem item) {
        String s = String.format("%-15s Add to Order %10s $%.2f  ","","", item.getPrice());
        JLabel label = new JLabel(s);
        label.setFont(new Font("", Font.PLAIN, 15));
        addToOrderButton.removeAll();
        addToOrderButton.add(label);
        addToOrderButton.revalidate();
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
