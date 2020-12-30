package ui.tabs;

import model.Account;
import model.Order;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// represents a home tab, facilitates account activities provides navigation of the console
public class HomeTab extends Tab {
    protected static final String PLACE_ORDER_COMMAND = "Place Order";
    protected static final String SIGN_IN_COMMAND = "Sign In";
    protected static final String SIGN_OUT_COMMAND = "Sign Out";
    protected static final String CREATE_ACCOUNT_COMMAND = "Create Account";
    protected static final String ORDER_HISTORY_COMMAND = "History";
    protected static final String CLEAR_HISTORY_COMMAND = "Clear History";

    private static final Dimension ORDER_HISTORY_MAX_SIZE = new Dimension(WIDTH * 2 / 5, OCafe.HEIGHT);

    private JPanel startPage;

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

        placeButton(startPage, PLACE_ORDER_COMMAND);
        startPage.add(createRigidArea());
        placeButton(startPage, SIGN_IN_COMMAND);
        startPage.add(createRigidArea());
        placeButton(startPage, CREATE_ACCOUNT_COMMAND);
        startPage.add(createRigidArea());

        JLabel message = new JLabel("You must sign in or create an account to save orders!");
        message.setAlignmentX(Component.LEFT_ALIGNMENT);
        startPage.add(message);

        add(startPage);
    }

    // displays account info
    private void initAccountPage() {
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        placeAccountOptionsPane();
    }

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

    // creates order history panel and displays history of current account
    private void placeOrderHistoryPane() {
        JPanel orderHistoryPane = initializeBoxLayoutPanel("Your Order History");
        orderHistoryPane.setBorder(BorderFactory.createEmptyBorder());
        orderHistoryPane.setMaximumSize(ORDER_HISTORY_MAX_SIZE);
        orderHistoryPane.setAlignmentY(TOP_ALIGNMENT);

        Account a = controller.getAccount();
        if (a.getHistory().size() == 0) {
            orderHistoryPane.add(new JLabel("No orders have been saved to your account yet!"));
        } else {
            JPanel historyList = new ScrollablePanel();
            historyList.setLayout(new BoxLayout(historyList, BoxLayout.Y_AXIS));
            historyList.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            historyList.setBackground(backgroundColor);

            for (Order o : a.getHistory()) {
                JTextArea order = new JTextArea(o.toString());
                historyList.add(order);
                historyList.add(new JSeparator());
            }

            JScrollPane scrollPane = new JScrollPane(historyList);
            scrollPane.setAlignmentX(LEFT_ALIGNMENT);
            orderHistoryPane.add(scrollPane);

            JButton clearButton = new JButton(CLEAR_HISTORY_COMMAND);
            clearButton.setAlignmentX(RIGHT_ALIGNMENT);
            clearButton.addActionListener(new HomeTabListener());
            orderHistoryPane.add(clearButton);
        }
        add(Box.createHorizontalGlue());
        add(orderHistoryPane);
    }

    public JPanel initializeGridLayoutPanel(String title) {
        JPanel panel = initializeDefaultPanel();
        panel.setLayout(new GridLayout(0, 1, 0, 20));

        JLabel label = new JLabel(title);
        label.setFont(new Font("", Font.PLAIN, 36));
        panel.add(label);
        return panel;
    }

    //MODIFIES: this
    //EFFECTS: creates button with command and adds it to panel
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

    // action listener for HomeTab
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
//                    controller.getTabbedPane().setSelectedIndex(OCafe.HOME_TAB_INDEX);
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
                    controller.getAccount().clearHistory();
                    revalidate();
                    break;
            }
        }
    }

    // scrollable JPanel
    private class ScrollablePanel extends JPanel implements Scrollable {

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return new Dimension(getPreferredSize().width,OCafe.HEIGHT - 300);
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
