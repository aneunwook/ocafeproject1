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
    protected static final Dimension DISPLAY_DETAILS_DIM = new Dimension(WIDTH * 3 / 5, ITEM_AND_CATEGORY_DIM.height);
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_WIDTH = 400; // 사진들 크기

    private MenuTab menuTab;
    private WeatherTab weatherTab;

 // 그리드에 표시된 범주 내의 항목이 있는 패널을 만듭니다.    
    public CategoryPane(MenuTab menuTab, OCafe controller, String[] category) {
        super(controller);

        setLayout(new GridLayout(0, 3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setPreferredSize(new Dimension(WIDTH, ITEM_AND_CATEGORY_DIM.height));

        this.menuTab = menuTab;

        placeItemButtons(category);
    }
    // 추가 : weathertab을 쓰기 위해 생성자 오버로딩을 했다 ㅎㅋ
    public CategoryPane(WeatherTab weatherTab, OCafe controller, String[] category) {
        super(controller);

        setLayout(new GridLayout(0, 3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setPreferredSize(new Dimension(WIDTH, ITEM_AND_CATEGORY_DIM.height));

        this.weatherTab = weatherTab;

        placeItemButtonsWeather(category);
        
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
            Dimension d = new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT); //치수 
            itemButton.add(loadImageJLabel(itemImage, d)); 

            JLabel name = new JLabel("  " + itemButton.getText());
            name.setFont(new Font("", Font.PLAIN, 15));
            name.setPreferredSize(new Dimension(IMAGE_WIDTH, 40));
            name.setMinimumSize(new Dimension(IMAGE_WIDTH, 40));
            name.setMaximumSize(new Dimension(IMAGE_WIDTH, 40));
            name.setAlignmentX(LEFT_ALIGNMENT); //왼쪽 정렬
            itemButton.add(name);
            itemButton.setActionCommand(itemButton.getText());
            itemButton.setText(null);

        }
    }

    // Weather -----------------------------------------------------------------------------
    
    
    private void placeItemButtonsWeather(String[] category) {
        for (String itemName : category) {
            JButton itemButton = new JButton(itemName);
            itemButton.setLayout(new BoxLayout(itemButton, BoxLayout.Y_AXIS));
            itemButton.setMargin(new Insets(0,0,0,0));
            add(itemButton);
            itemButton.addActionListener(new ItemSelectorWeather());
        }
        placeItemImagesWeather();
    }
    
    private void placeItemImagesWeather() {
        for (Component c : getComponents()) {
            JButton itemButton = (JButton)c;
            Image itemImage = new ImageIcon("./data/images/" + itemButton.getText() + ".jpg").getImage();
            Dimension d = new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT); //치수 
            itemButton.add(loadImageJLabel(itemImage, d)); 

            JLabel name = new JLabel("  " + itemButton.getText());
            name.setFont(new Font("", Font.PLAIN, 15));
            name.setPreferredSize(new Dimension(IMAGE_WIDTH, 40));
            name.setMinimumSize(new Dimension(IMAGE_WIDTH, 40));
            name.setMaximumSize(new Dimension(IMAGE_WIDTH, 40));
            name.setAlignmentX(LEFT_ALIGNMENT); //왼쪽 정렬
            itemButton.add(name);
            itemButton.setActionCommand(itemButton.getText());
            itemButton.setText(null);

        }
    }
    
    // ItemDetailsPane Class 를 ItemDetailsPaneWeather 로 하나 만듬 ( 클래스 안에 들어가면 설명 있음 )
    //효과: 새 ItemDetailsPane을 만들고 메뉴 탭에서 설정합니다.
    public void displayBeverageDetailsWeather(String itemName, List<Beverage> type) {
    	setPreferredSize(DISPLAY_DETAILS_DIM);
    	ItemDetailsPaneWeather p = new ItemDetailsPaneWeather(itemName, type, this);
    	weatherTab.displayItemDetailsWeather(p);
    }
    
    //효과: dish 항목 세부 정보 표시
    public void displayDishDetailsWeather(String itemName, List<Dish> type) {
    	setPreferredSize(DISPLAY_DETAILS_DIM);
    	ItemDetailsPaneWeather p = new ItemDetailsPaneWeather(itemName, type, this, 1);
    	weatherTab.displayItemDetailsWeather(p);
    }
    
    // 항목을 선택하고 메뉴 탭에서 항목 세부 정보 창을 설정합니다.
    private class ItemSelectorWeather implements ActionListener {
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
                    displayBeverageDetailsWeather(buttonPressed, controller.getMenuLoader().getCoffee());
                    break;
                case "Matcha Latte":
                case "Hojicha Latte":
                case "London Fog":
                case "Chai Latte":
                case "Sencha":
                case "Black Tea":
                    displayBeverageDetailsWeather(buttonPressed, controller.getMenuLoader().getTea());
                    break;
                default:
                    parseInputItemDetails2(buttonPressed);
                    break;
            }
        }

                 //효과: 동작의 확장 수행
        private void parseInputItemDetails2(String str) {
            switch (str) {
                case "Honey Ginger Tea":
                case "Fruit Tea":
                case "Kumquat Chrysanthemum Tea":
                case "Hibiscus Kombucha":
                case "Mango Kale Smoothie":
                    displayBeverageDetailsWeather(str, controller.getMenuLoader().getNonCaffeinated());
                    break;
                case "Thai Green Curry Seafood Linguine":
                case "Eggs Benedict":
                case "Omurice":
                case "Butternut Squash Risotto":
                case "Japanese Curry Rice":
                case "Dutch Cheese Sandwich":
                case "Spring Salad":
                case "Butter Croissant":
                	displayDishDetailsWeather(str, controller.getMenuLoader().getBrunch());
                    break;
                default:
                    parseInputItemDetails3(str);
                    break;
            }
        }

        		//효과: 동작의 확장 수행
        private void parseInputItemDetails3(String str) {
            switch (str) {
                case "Kinako Mochi":
                case "Raspberry Pistachio Cream Tart":
                case "Banana Cream Pie":
                case "Sweet Potato Crepe":
                case "Hojicha Parfait":
                case "Chestnut Cake":
                case "Tofu Ice Cream":
                	displayDishDetailsWeather(str, controller.getMenuLoader().getDessert());
                    break;
                default:
                    System.out.println("Invalid selection, please try again.");
                    break;
            }
        }
    }
    // Weather -----------------------------------------------------------------------------End
    
    //효과: 새 ItemDetailsPane을 만들고 메뉴 탭에서 설정합니다.
    public void displayBeverageDetails(String itemName, List<Beverage> type) {
        setPreferredSize(DISPLAY_DETAILS_DIM);
        ItemDetailsPane p = new ItemDetailsPane(itemName, type, this);
        menuTab.displayItemDetails(p);
    }

    //효과: dish 항목 세부 정보 표시
    public void displayDishDetails(String itemName, List<Dish> type) {
        setPreferredSize(DISPLAY_DETAILS_DIM);
        ItemDetailsPane p = new ItemDetailsPane(itemName, type, this, 1);
        menuTab.displayItemDetails(p);
    }

     // 항목을 선택하고 메뉴 탭에서 항목 세부 정보 창을 설정합니다.
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

                 //효과: 동작의 확장 수행
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

        		//효과: 동작의 확장 수행
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