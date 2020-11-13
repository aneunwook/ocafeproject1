package ui.tabs;

import ui.ButtonNames;
import ui.OCafe;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeTab extends Tab {
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
        JButton b = new JButton(ButtonNames.PLACE_ORDER.getValue());

        JPanel buttonRow = formatButtonRow(b);
//        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.PLACE_ORDER.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(OCafe.MENU_TAB_INDEX);
                }
            }
        });

        startPage.add(buttonRow);
    }

    private void signInButton() {
        JButton b = new JButton(ButtonNames.SIGN_IN.getValue());
        JPanel buttonRow = formatButtonRow(b);
//        buttonRow.setSize(WIDTH, HEIGHT / 6);

        startPage.add(buttonRow);
    }

    private void createAccountButton() {
        JButton b = new JButton(ButtonNames.CREATE_ACCOUNT.getValue());
        JPanel buttonRow = formatButtonRow(b);
//        buttonRow.setSize(WIDTH, HEIGHT / 6);

        startPage.add(buttonRow);
    }
}
