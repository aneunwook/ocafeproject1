package ui.tabs;

import model.Account;
import model.Order;
import ui.OCafe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// represents a home tab, facilitates account activities provides navigation of the console
public class HomeTab extends Tab {

    private JPanel startPage;
    private JPanel accountPage;
    private JPanel accountOptionsPane;

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
        //https://examples.javacodegeeks.com/desktop-java/swing/java-swing-boxlayout-example/
        startPage = initializePane("");
        startPage.setBorder(BorderFactory.createEmptyBorder(OCafe.HEIGHT / 3, 0, OCafe.HEIGHT / 3, 0));

        placeOrderButton(startPage);
        placeSignInButton();
        placeCreateAccountButton();

        add(startPage);
    }

    // displays account info
    private void initAccountPage() {
        accountPage = new JPanel();
        accountOptionsPane = initializePane(controller.getAccount().getName());
        accountOptionsPane.setPreferredSize(new Dimension(OCafe.WIDTH / 2, OCafe.HEIGHT));
        accountOptionsPane.setBorder(BorderFactory.createEmptyBorder(
                OCafe.HEIGHT / 4, OCafe.WIDTH / 6, 0, OCafe.WIDTH / 5));

        accountOptionsPane.add(createRigidArea());

        placeOrderButton(accountOptionsPane);
        placeOrderHistoryButton();
        placeSignOutButton();

        accountPage.add(accountOptionsPane);
        add(accountPage);
    }

    // creates order history panel and displays history of current account
    private void placeOrderHistoryPane() {
        JPanel orderHistoryPane = initializePane("Your Order History");
        accountOptionsPane.setPreferredSize(new Dimension(OCafe.WIDTH / 2, OCafe.HEIGHT));
        accountOptionsPane.setBorder(BorderFactory.createEmptyBorder(
                OCafe.HEIGHT / 4, OCafe.WIDTH / 8, 0, OCafe.WIDTH / 5));

        orderHistoryPane.add(createRigidArea());

        Account a = controller.getAccount();
        if (a.getHistory().size() == 0) {
            orderHistoryPane.add(new JLabel("No orders have been saved to your account yet!"));

        } else {
            for (Order o : a.getHistory()) {
                JTextArea order = new JTextArea(o.toString());
                order.setAlignmentX(Component.LEFT_ALIGNMENT);
                orderHistoryPane.add(order);
                orderHistoryPane.add(createRigidArea());
            }
        }
        accountPage.add(orderHistoryPane);
    }

    //MODIFIES: this
    //EFFECTS: if account with input name exists, sets account field
    private void placeSignInButton() {
        JButton signInButton = new JButton(SIGN_IN_COMMAND);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(
                        startPage, "Welcome back!\nPlease enter your name:", "Sign In", JOptionPane.PLAIN_MESSAGE);
                controller.handleSignIn(name);
            }
        });

        signInButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        startPage.add(signInButton);
    }

    //MODIFIES: this
    //EFFECTS: prompts user to enter info to create an account
    private void placeCreateAccountButton() {
        JButton createAccountButton = new JButton(CREATE_ACCOUNT_COMMAND);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(
                        startPage, "Please enter your name:", "Create Account", JOptionPane.PLAIN_MESSAGE);
                controller.handleCreateAccount(name);
            }
        });

        createAccountButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        startPage.add(createAccountButton);
    }

    //REQUIRES: account != null
    //EFFECTS: prints order history
    private void placeOrderHistoryButton() {
        JButton orderHistoryButton = new JButton("Orders");

        orderHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.readAccount();
                    placeOrderHistoryPane();
                } catch (IOException exception) {
                    System.out.println("Account file could not be read");
                }
            }
        });

        orderHistoryButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountOptionsPane.add(orderHistoryButton);
    }

    //MODIFIES: this
    //EFFECTS: creates sign out button,
    //         if account is not already null, set account, writer, and reader to null and display start page,
    //         otherwise does nothing
    private void placeSignOutButton() {
        JButton signOutButton = new JButton("Sign Out");

        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleSignOut();
                controller.refreshTab(OCafe.HOME_TAB_INDEX);
                controller.getTabbedPane().setSelectedIndex(OCafe.HOME_TAB_INDEX);
            }
        });

        signOutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountOptionsPane.add(signOutButton);
    }
}
