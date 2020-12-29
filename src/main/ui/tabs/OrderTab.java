package ui.tabs;

import model.MenuItem;
import model.Order;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents an order tab, allows interaction with the current order
public class OrderTab extends Tab {
    private static final Dimension ORDER_BUTTON_DIM = new Dimension(OCafe.WIDTH * 2 / 5, 50);

    private Order order;

    private JPanel summaryPane;
    private JPanel checkoutPane;
    private JPanel receiptPane;
    private JPanel unsavedOrderPane;

    // creates new order tab
    public OrderTab(OCafe controller) {
        super(controller);
        order = controller.getOrder();

        setBorder(BorderFactory.createEmptyBorder(100, OCafe.WIDTH / 4, 0, OCafe.WIDTH / 2));

        placeSummaryPane();
    }

    // creates and adds order summary panel
    private void placeSummaryPane() {
        summaryPane = initializeBoxLayoutPanel("Your Order");

        displayOrderItems(summaryPane);

        summaryPane.add(createRigidArea());
        placeCheckoutButton();
        summaryPane.add(createRigidArea());
        add(summaryPane);
    }

    // creates and adds checkout panel
    private void placeCheckoutPane() {
        checkoutPane = initializeBoxLayoutPanel("Checkout");

        displayOrderItems(checkoutPane);

//        checkoutPane.add(createRigidArea());
//        JLabel total = new JLabel(displayOrderTotal("Total:"));
//        checkoutPane.add(total);

        checkoutPane.add(createRigidArea());
        placePayNowButton();
        checkoutPane.add(createRigidArea());
        add(checkoutPane);
    }

    // creates and adds receipt panel
    private void placeReceiptPane() {
        receiptPane = initializeBoxLayoutPanel("Saved!");
        receiptPane.setBorder(BorderFactory.createEmptyBorder());

        JTextArea receipt = new JTextArea(order.toString());
        receipt.setBackground(new Color(0,0,0, 0));
        receipt.setAlignmentX(Component.LEFT_ALIGNMENT);
        receiptPane.add(receipt);

        receiptPane.add(createRigidArea());

        placeHomeButton(receiptPane);

        add(receiptPane);
    }

    // creates and adds panel with confirmation message
    private void placeUnsavedOrderPane() {
        unsavedOrderPane = initializeBoxLayoutPanel("Your order has been placed!");
        unsavedOrderPane.setBorder(BorderFactory.createEmptyBorder());

        placeHomeButton(unsavedOrderPane);

        add(unsavedOrderPane);
    }

    // displays items in current order
    private void displayOrderItems(JPanel currentPanel) {
        for (MenuItem item : order.getItemList()) {
            JPanel itemPane = initializeDefaultPanel();
            itemPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 15, 5));
            itemPane.setLayout(new BoxLayout(itemPane, BoxLayout.X_AXIS));

            if (currentPanel.equals(summaryPane)) {
                placeQuantityComboBox(item, itemPane);
            }

            JTextArea text = new JTextArea("\n  " + item.toString());
            text.setBackground(new Color(0,0,0, 0));
            itemPane.add(text);

            itemPane.setAlignmentX(Component.LEFT_ALIGNMENT);
            currentPanel.add(itemPane);
            currentPanel.add(new JSeparator());
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
        checkoutButton.setPreferredSize(ORDER_BUTTON_DIM);

        if (order.getTotal() <= 0.0) {
            checkoutButton.setEnabled(false);
        }

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
        JLabel label = new JLabel(displayOrderTotal("Pay Now"));
        label.setForeground(Color.white);
        label.setFont(new Font("", Font.PLAIN, 14));

        JButton payNowButton = new JButton();
        payNowButton.add(label);
        payNowButton.setPreferredSize(ORDER_BUTTON_DIM);
        payNowButton.setBackground(Color.black);
        payNowButton.setOpaque(true);
        payNowButton.setBorderPainted(false);

        payNowButton.addActionListener(new PaymentConfirmation());

        payNowButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkoutPane.add(payNowButton);
    }

    // creates home button, sets tabbed pane to home tab
    private void placeHomeButton(JPanel panel) {
        JButton placeOrderButton = new JButton("Home");

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Home")) {
                    controller.refreshTab(OCafe.ORDER_TAB_INDEX);
                    controller.getTabbedPane().setSelectedIndex(OCafe.HOME_TAB_INDEX);
                }
            }
        });

        placeOrderButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(placeOrderButton);
    }

    // returns string representing parameter and order total
    private String displayOrderTotal(String s) {
        return String.format("%-35s " + s + " %30s $%.2f  ", "", "", order.getTotal());
    }

    // action listener for payment confirmation
    private class PaymentConfirmation implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // process payment??
            int selected = JOptionPane.NO_OPTION;
            if (controller.getAccount() != null) {
                order.setDate();
                selected = JOptionPane.showConfirmDialog(null,
                        "Would you like to save this order to your history?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
            }
            if (selected == JOptionPane.YES_OPTION) {
                controller.saveOrder();
                controller.refreshTab(OCafe.HOME_TAB_INDEX);
                removeAll();
                placeReceiptPane();
                setSize(receiptPane.getSize());
            } else {
                removeAll();
                placeUnsavedOrderPane();
                setSize(unsavedOrderPane.getSize());
            }
            revalidate();
            controller.makeNewOrder();
        }
    }
}
