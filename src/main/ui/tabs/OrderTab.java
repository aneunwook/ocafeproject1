package ui.tabs;

import model.MenuItem;
import model.Order;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class OrderTab extends Tab {
    private Order order;

    private JPanel summaryPane;
    private JPanel checkoutPane;

    public OrderTab(OCafe controller) {
        super(controller);
        order = controller.getOrder();

        placeSummaryPane();
    }

    private void placeSummaryPane() {
        summaryPane = initializePane("Your Order");

        displayOrderItems(summaryPane);
        placeCheckoutButton();

        add(summaryPane);
    }

    private void placeCheckoutPane() {
        checkoutPane = initializePane("Checkout");

        displayOrderItems(checkoutPane);

        JLabel total = new JLabel(displayOrderTotal("Total:"));
        checkoutPane.add(total);

        placePayNowButton();

        add(checkoutPane);
    }

    private void displayOrderItems(JPanel currentPanel) {
        for (MenuItem item : order.getItemList()) {
            JPanel itemPane = new JPanel();
            itemPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            if (currentPanel.equals(summaryPane)) {
                placeQuantityComboBox(item, itemPane);
            }

            JTextArea text = new JTextArea(item.toString());
            text.setBackground(new Color(0,0,0, 0));
            itemPane.add(text);

            itemPane.setAlignmentX(Component.LEFT_ALIGNMENT);
            currentPanel.add(itemPane);
        }
    }

    // creates combo box representing item quantity and remove option
    private void placeQuantityComboBox(MenuItem item, JPanel panel) {
        JComboBox quantityBox = new JComboBox();
        quantityBox.setPreferredSize(new Dimension(60, 50));
        quantityBox.addItem("Remove");
        for (int i = 1; i <= 100; i++) {
            quantityBox.addItem(new Integer(i));
        }
        quantityBox.setSelectedIndex(item.getQuantity());

        quantityBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int q = (int)quantityBox.getSelectedItem();
                    item.setQuantity(q);
                } catch (ClassCastException exception) {
                    order.removeItem(item);
                }
                controller.refreshTab(OCafe.ORDER_TAB_INDEX);
            }
        });

        panel.add(quantityBox);
    }

    // creates checkout button, switches contentpane to checkout pane
    private void placeCheckoutButton() {
        JButton checkoutButton = new JButton(displayOrderTotal("Checkout"));

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                placeCheckoutPane();
                setSize(checkoutPane.getSize());
                revalidate();
            }
        });

        checkoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPane.add(checkoutButton);
    }

    // creates pay button, places order and prompts user to save this order when pressed
    private void placePayNowButton() {
        JButton payNowButton = new JButton("Pay Now");

        payNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // process payment??
                order.setDate();
                int selected = JOptionPane.showConfirmDialog(null,
                        "Your order has been placed!\nWould you like to save this order?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (selected == JOptionPane.YES_OPTION) {
                    controller.saveOrder();
                }
            }
        });

        payNowButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkoutPane.add(payNowButton);
    }



    private String displayOrderTotal(String s) {
        return String.format("%-50s $%.2f", s, order.getTotal());
    }
}
