package ui.tabs;

import model.MenuItem;
import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

public abstract class Tab extends JPanel {
    protected static final String PLACE_ORDER_COMMAND = "Place Order";
    protected static final String SIGN_IN_COMMAND = "Sign In";
    protected static final String CREATE_ACCOUNT_COMMAND = "Create Account";

    protected static final int WIDTH = OCafe.WIDTH - 100;
    protected static final Dimension ITEM_AND_CATEGORY_DIM = new Dimension(OCafe.WIDTH * 11 / 30, OCafe.HEIGHT * 3 / 4);
    protected static final Color backgroundColor = Color.white;

    protected OCafe controller;

    //REQUIRES: OCafe controller that holds this tab
    public Tab(OCafe controller) {
        this.controller = controller;
        setBackground(backgroundColor);
    }

    // returns a box layout panel with a title
    public JPanel initializeBoxLayoutPanel(String title) {
        JPanel panel = initializeDefaultPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        return panel;
    }

    // returns a flow layout panel
    public JPanel initializeDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);

        return panel;
    }

    //EFFECTS: returns image in JLabel form
    public ImageIcon loadImageJLabel(Image originalImage, Dimension targetDimension) {
//        Image originalImage = item.getImage();
        int height = originalImage.getHeight(new ItemImageObserver());
        int width = originalImage.getWidth(new ItemImageObserver());
        double scale = ((double)width / (targetDimension.width));

        // to scale the image according to height
//        double scale = ((double)height / IMAGE_HEIGHT);
//        ImageIcon scaledImage = new ImageIcon(item.getImage().getScaledInstance((int)(targetDimension.width / scale),
//              IMAGE_HEIGHT,
//              Image.SCALE_SMOOTH));

        ImageIcon scaledImage = new ImageIcon(originalImage.getScaledInstance(targetDimension.width,
                (int)(height / scale),
                Image.SCALE_SMOOTH));

        JLabel icon = new JLabel(scaledImage);
        icon.setPreferredSize(new Dimension(targetDimension.width, targetDimension.height));
//        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

//        return icon;
        return scaledImage;
    }

    // image observer
    private class ItemImageObserver implements ImageObserver {

        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }
    }

    //EFFECTS: creates Place Order button that switches the content pane to the menu tab when pressed
    public void placeOrderButton(JPanel panel) {
        JButton placeOrderButton = new JButton(PLACE_ORDER_COMMAND);

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(PLACE_ORDER_COMMAND)) {
                    controller.refreshTab(OCafe.ORDER_TAB_INDEX);
                    controller.getTabbedPane().setSelectedIndex(OCafe.MENU_TAB_INDEX);
                }
            }
        });

        placeOrderButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(placeOrderButton);
    }

    // creates rigid area
    public Component createRigidArea() {
        return Box.createRigidArea(new Dimension(50, 20));
    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public OCafe getController() {
        return controller;
    }

}
