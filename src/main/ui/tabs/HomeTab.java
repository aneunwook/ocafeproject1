package ui.tabs;

import ui.OCafe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeTab extends Tab {
    private static final String PLACE_ORDER_COMMAND = "Place Order";
    private static final String SIGN_IN_COMMAND = "Sign In";
    private static final String CREATE_ACCOUNT_COMMAND = "Create Account";

    private JPanel startPage;
    private JPanel accountPage;

    public HomeTab(OCafe controller) {
        super(controller);

        if (controller.getAccount() == null) {
            initStartPage();
        } else {
            initAccountPage();
        }
    }

    private void initStartPage() {
        //https://examples.javacodegeeks.com/desktop-java/swing/java-swing-boxlayout-example/
        startPage = initializePane("");
        startPage.setBorder(new EmptyBorder(new Insets(OCafe.HEIGHT / 3, 0, OCafe.HEIGHT / 3, 0)));

        placeOrderButton();
        placeSignInButton();
        placeCreateAccountButton();

        add(startPage);
    }

    // displays account info
    private void initAccountPage() {
        accountPage = initializePane(controller.getAccount().getName());

        placeOrderHistoryButton();
        placeSignOutButton();

        add(accountPage);
    }

    //EFFECTS: creates Place Order button that switches to the menu tab on the console
    private void placeOrderButton() {
        JButton placeOrderButton = new JButton(PLACE_ORDER_COMMAND);
        placeOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(PLACE_ORDER_COMMAND)) {
                    getController().getTabbedPane().setSelectedIndex(OCafe.MENU_TAB_INDEX);
                }
            }
        });

        startPage.add(placeOrderButton);
    }

    private void placeSignInButton() {
        JButton signInButton = new JButton(SIGN_IN_COMMAND);
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(
                        startPage, "Welcome back!\nPlease enter your name:", "Sign In", JOptionPane.PLAIN_MESSAGE);
                controller.handleSignIn(name);
            }
        });

        startPage.add(signInButton);
    }

    private void placeCreateAccountButton() {
        JButton createAccountButton = new JButton(CREATE_ACCOUNT_COMMAND);
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(
                        startPage, "Please enter your name:", "Create Account", JOptionPane.PLAIN_MESSAGE);
                controller.handleCreateAccount(name);
            }
        });

        startPage.add(createAccountButton);
    }

    private void placeOrderHistoryButton() {
        //!!!
    }

    private void placeSignOutButton() {
        //!!!
    }
}
