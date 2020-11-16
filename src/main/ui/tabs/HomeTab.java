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

    public HomeTab(OCafe controller) {
        super(controller);

//https://examples.javacodegeeks.com/desktop-java/swing/java-swing-boxlayout-example/
        startPage = new JPanel();
        startPage.setLayout(new BoxLayout(startPage, BoxLayout.Y_AXIS));
        startPage.setBorder(new EmptyBorder(new Insets(OCafe.HEIGHT / 4, 0, OCafe.HEIGHT / 3, 0)));

        add(startPage);

        placeOrderButton();
        signInButton();
        createAccountButton();
    }

    //EFFECTS: creates Place Order button that switches to the menu tab on the console
    private void placeOrderButton() {
        JButton b = new JButton(PLACE_ORDER_COMMAND);

        JPanel buttonRow = formatButtonRow(b);
//        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(PLACE_ORDER_COMMAND)) {
                    getController().getTabbedPane().setSelectedIndex(OCafe.MENU_TAB_INDEX);
                }
            }
        });

        startPage.add(buttonRow);
    }

    private void signInButton() {
        JButton b = new JButton(SIGN_IN_COMMAND);
        JPanel buttonRow = formatButtonRow(b);
//        buttonRow.setSize(WIDTH, HEIGHT / 6);

        startPage.add(buttonRow);
    }

    private void createAccountButton() {
        JButton b = new JButton(CREATE_ACCOUNT_COMMAND);
        JPanel buttonRow = formatButtonRow(b);
//        buttonRow.setSize(WIDTH, HEIGHT / 6);

        startPage.add(buttonRow);
    }
}
