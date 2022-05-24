package ui.tabs;

import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTab extends Tab {
	private static final String[] coffee = {"에스프레소", "아메리카노", "마끼아또", "라떼", "아이스 커피", "콜드브루"};
    private static final String[] tea = {
            "말차 라떼", "호지차 라떼", "런던 포그", "차이 라떼", "센차", "홍차"};
    private static final String[] noncaffeinated = {
            "생강 꿀 차", "과실 차", "금귤 국화 차", "히비스커스 차", "망고 케일 스무디"};
    private static final String[] brunch = {
            "태국 야채 카레 해산물 링귀네", "에그 베네딕트", "오므라이스", "버터넛 스쿼시 리조또",
            "일본 카레", "더치 치즈 샌드위치", "봄철 샐러드", "버터 크로아상"};
    private static final String[] dessert = {
            "키나코 모찌", "라즈베리 피스타치오 크림 타르트", "바나나 크림 파이", "고구마 크레페"};
    private static final String COFFEE = "Coffee";
    private static final String TEA = "Tea";
    private static final String NONCAFFEINATED = "Noncaffeinated";
    private static final String BRUNCH = "Brunch";
    private static final String DESSERT = "Dessert";
    private static final String[] categories = {COFFEE, TEA, NONCAFFEINATED, BRUNCH, DESSERT};

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

        placeCategorySelectorPanel();

        placeItemDetailsContainer();

        placeCategoryContainer();

        displayNewCategory(coffee);
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

    //EFFECTS: places panel with buttons for each menu category,
    //         changes display of categoryContainer and title when clicked
    private void placeCategorySelectorPanel() {
        categorySelectorPane = initializeDefaultPanel();

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
    private void displayNewCategory(String[] category) {
        setNewCategoryGridBagConstraints();
        CategoryPane cp = new CategoryPane(this, getController(), category);
        setContainerContent(categoryContainer, cp);

        itemDetailsContainer.removeAll();
        itemDetailsContainer.revalidate();
    }

    //MODIFIED: this
    //EFFECTS: sets layout to show the category panel and the item details panel
    //         removes previous panel and adds parameter to itemDetailsContainer
    public void displayItemDetails(ItemDetailsPane p) {
        setDisplayItemDetailsGridBagConstraints();
        categoryContainer.revalidate();
        setContainerContent(itemDetailsContainer, p);
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

    //MODIFIES: this
    //EFFECTS: sets GridBag Constraints for CategoryContainer and itemDetailsContainer to show both panels
    private void setDisplayItemDetailsGridBagConstraints() {
        GridBagConstraints categoryConstraints = new GridBagConstraints();
        categoryConstraints.weightx = 0.5;
        categoryConstraints.weighty = 1.0;
        categoryConstraints.gridx = 0;
        categoryConstraints.gridy = 2;
        categoryConstraints.gridheight = 9;
        categoryConstraints.fill = GridBagConstraints.HORIZONTAL;
        categoryConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        gridBagLayout.setConstraints(categoryContainer, categoryConstraints);

        GridBagConstraints itemDetailsConstraints = new GridBagConstraints();
        itemDetailsConstraints.weightx = 0.5;
        itemDetailsConstraints.gridx = 1;
        itemDetailsConstraints.gridy = 2;
        itemDetailsConstraints.gridheight = 9;
        itemDetailsConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        gridBagLayout.setConstraints(itemDetailsContainer, itemDetailsConstraints);
    }

    //MODIFIES: this
    //EFFECTS: replaces previous panel in container with parameter p
    //https://stackoverflow.com/questions/9401353/how-to-change-the-jpanel-in-a-jframe-at-runtime
    private void setContainerContent(JPanel container, Tab p) {
        container.removeAll();
        container.setSize(p.getSize());
        container.add(p);
        container.revalidate();
    }

    //action listener for buttons in category selector panel
    private class CategorySelector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case COFFEE:
                    displayNewCategory(coffee);
                    break;
                case TEA:
                    displayNewCategory(tea);
                    break;
                case NONCAFFEINATED:
                    displayNewCategory(noncaffeinated);
                    break;
                case BRUNCH:
                    displayNewCategory(brunch);
                    break;
                case DESSERT:
                    displayNewCategory(dessert);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + buttonPressed);
            }
            setTitle(buttonPressed);
            controller.playSound("./data/sounds/Morse.wav");
        }
    }

}
