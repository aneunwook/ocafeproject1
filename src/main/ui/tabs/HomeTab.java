package ui.tabs;

import ui.OCafe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeTab extends Tab {

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
        startPage.setBorder(BorderFactory.createEmptyBorder(OCafe.HEIGHT / 3, 0, OCafe.HEIGHT / 3, 0));

        placeOrderButton(startPage);
        placeSignInButton();
        placeCreateAccountButton();

        add(startPage);
    }

    // displays account info
    private void initAccountPage() {
        accountPage = initializePane(controller.getAccount().getName());
        accountPage.setBorder(BorderFactory.createEmptyBorder(OCafe.HEIGHT / 4, OCafe.WIDTH / 4, 0, OCafe.WIDTH / 2));

        placeOrderHistoryButton();
        placeSignOutButton();

        add(accountPage);
    }

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

    private void placeOrderHistoryButton() {
        //!!!
    }

    private void placeSignOutButton() {
        //!!!
    }
}
