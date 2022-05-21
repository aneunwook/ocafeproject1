package ui.tabs;

import model.Order;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

//홈 탭을 나타내며, 계정 활동을 용이하게 하여 콘솔의 탐색을 제공합니다.
public class HomeTab extends Tab {
    protected static final String PLACE_ORDER_COMMAND = "Place Order";
    protected static final String SIGN_IN_COMMAND = "Sign In";
    protected static final String SIGN_OUT_COMMAND = "Sign Out";
    protected static final String CREATE_ACCOUNT_COMMAND = "Create Account";
    protected static final String ORDER_HISTORY_COMMAND = "History";
    protected static final String CLEAR_HISTORY_COMMAND = "Clear History";

    private static final Dimension ORDER_HISTORY_MAX_SIZE = new Dimension(WIDTH * 2 / 5, OCafe.HEIGHT);

    private JPanel startPage;
    private JPanel orderHistoryPane;

    public HomeTab(OCafe controller) {
        super(controller);

        if (controller.getAccount() == null) {
            initStartPage();
        } else {
            initAccountPage();
        }
    }

    // creates start page with navigation buttons
    private void initStartPage() {
        startPage = initializeBoxLayoutPanel("Welcome to OCafe!");
        startPage.setBorder(BorderFactory.createEmptyBorder(OCafe.HEIGHT / 4, 0, OCafe.HEIGHT / 3, 0));

        placeButton(startPage, PLACE_ORDER_COMMAND); 	//place, signin, sign out
        startPage.add(createRigidArea());
        placeButton(startPage, SIGN_IN_COMMAND);         
        startPage.add(createRigidArea());
        placeButton(startPage, CREATE_ACCOUNT_COMMAND);   
        startPage.add(createRigidArea());

        JLabel message = new JLabel("You  must sign in or create an account to save orders!");
        message.setAlignmentX(Component.LEFT_ALIGNMENT);
        startPage.add(message);

               add(startPage);
    }

    // 계정 페이지 표시
    private void initAccountPage() {
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        placeAccountOptionsPane();
    }

    private void placeImage() {
        add(Box.createHorizontalGlue());
        Image img = new ImageIcon("./data/images/accountfiller.jpg").getImage();
        JLabel icon = loadImageJLabel(img, ORDER_HISTORY_MAX_SIZE);
        icon.setAlignmentY(TOP_ALIGNMENT);
        add(icon);
    }

    // 계정 옵션 표시
    private void placeAccountOptionsPane() {
        JPanel accountOptionsPane = initializeBoxLayoutPanel(controller.getAccount().getName());
        accountOptionsPane.setBorder(BorderFactory.createEmptyBorder());
        accountOptionsPane.setAlignmentY(TOP_ALIGNMENT);

        placeButton(accountOptionsPane, PLACE_ORDER_COMMAND);
        accountOptionsPane.add(createRigidArea());
        placeButton(accountOptionsPane, ORDER_HISTORY_COMMAND);
        accountOptionsPane.add(createRigidArea());
        placeButton(accountOptionsPane, SIGN_OUT_COMMAND);

        add(accountOptionsPane);
    }

    // 주문 내역 패널을 만들고 현재 계정의 기록을 표시합니다.
    private void placeOrderHistoryPane() {
        if (orderHistoryPane == null) {
            orderHistoryPane = initializeBoxLayoutPanel("Your Order History");
            orderHistoryPane.setBorder(BorderFactory.createEmptyBorder());
            orderHistoryPane.setMaximumSize(ORDER_HISTORY_MAX_SIZE);
            orderHistoryPane.setAlignmentY(TOP_ALIGNMENT);

            List<Order> orderList = controller.getAccount().getHistory();
            if (orderList.size() == 0) {
                orderHistoryPane.add(new JLabel("No orders have been saved to your account yet!"));
            } else {
                orderHistoryPane.add(loadOrderHistory(orderList));
                orderHistoryPane.add(createRigidArea());
                orderHistoryPane.add(loadClearHistoryButton());
            }
            add(Box.createHorizontalGlue());
            add(orderHistoryPane);
        }
    }
  //수정: this
  //Effects: 명령이 포함된 버튼을 만들어 패널에 추가합니다.
    private void placeButton(JPanel panel, String command) {
        JLabel label = new JLabel(command);
        label.setForeground(backgroundColor);
        label.setFont(new Font("", Font.PLAIN, 14));

        JButton b = new JButton();
        b.add(label);
        b.setBackground(Color.black);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setActionCommand(command);
        b.addActionListener(new HomeTabListener());
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(b);
    }

    //orderList를 나타내는 JScrollPane을 반환합니다.
    private JScrollPane loadOrderHistory(List<Order> orderList) {
        JPanel historyList = new ScrollablePanel();
        historyList.setLayout(new BoxLayout(historyList, BoxLayout.Y_AXIS));
        historyList.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        historyList.setBackground(backgroundColor);

        for (Order o : orderList) {
            JTextArea order = new JTextArea(o.toString());
            historyList.add(order);
            historyList.add(new JSeparator());
        }

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        return scrollPane;
    }

//    기록 지우기 JButton을 반환합니다.

    private JButton loadClearHistoryButton() {
        JButton clearButton = new JButton(CLEAR_HISTORY_COMMAND);
        clearButton.setAlignmentX(LEFT_ALIGNMENT);
        clearButton.addActionListener(new HomeTabListener());
        return clearButton;
    }

    // 홈 탭의 작업 수신기

    private class HomeTabListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.playSound("./data/sounds/Morse.wav");
            String buttonPressed = e.getActionCommand();
            String name;
            switch (buttonPressed) {
                case PLACE_ORDER_COMMAND:
                    controller.refreshTab(OCafe.ORDER_TAB_INDEX);
                    controller.getTabbedPane().setSelectedIndex(OCafe.MENU_TAB_INDEX);
                    break;
                case CREATE_ACCOUNT_COMMAND:
                    name = JOptionPane.showInputDialog(
                            startPage, "Please enter your name:", "Create Account", JOptionPane.PLAIN_MESSAGE);
                    controller.handleCreateAccount(name);
                    break;
                case SIGN_IN_COMMAND:
                    name = JOptionPane.showInputDialog(
                            startPage, "Welcome back!\nPlease enter your name:", "Sign In", JOptionPane.PLAIN_MESSAGE);
                    controller.handleSignIn(name);
                    break;
                default:
                    actionPerformed2(buttonPressed);
                    break;
            }
        }

        public void actionPerformed2(String buttonPressed) {
            switch (buttonPressed) {
                case SIGN_OUT_COMMAND:
                    controller.handleSignOut();
                    controller.refreshTab(OCafe.HOME_TAB_INDEX);
                    break;
                case ORDER_HISTORY_COMMAND:
                    try {
                        controller.readAccount();
                        placeOrderHistoryPane();
                        revalidate();
                    } catch (IOException exception) {
                        System.out.println("Account file could not be read");
                    }
                    break;
                default:
                    controller.clearHistory();
                    removeAll();
                    initAccountPage();
                    placeOrderHistoryPane();
                    revalidate();
                    break;
            }
        }
    }

    // scrollable JPanel
    private class ScrollablePanel extends JPanel implements Scrollable {

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return new Dimension(getPreferredSize().width, OCafe.HEIGHT - 400);
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 10;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            if (orientation == SwingConstants.HORIZONTAL) {
                return visibleRect.width - 5;
            } else {
                return visibleRect.height - 5;
            }
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return false;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}