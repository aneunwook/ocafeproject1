package ui.tabs;

import ui.OCafe;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class Tab extends JPanel {
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
        panel.setBorder(BorderFactory.createEtchedBorder());

        panel.add(createRigidArea());
        JLabel label = new JLabel(title);
        label.setFont(new Font("", Font.PLAIN, 36));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(createRigidArea());

        return panel;
    }

    // returns a flow layout panel
    public JPanel initializeDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);

        return panel;
    }

    //EFFECTS: returns image in JLabel form with target dimension
    public JLabel loadImageJLabel(Image originalImage, Dimension targetDimension) {
        int height = originalImage.getHeight(new ItemImageObserver());
        int width = originalImage.getWidth(new ItemImageObserver());
        double scale = ((double)width / (targetDimension.width));

        // to scale the image according to height
//        double scale = ((double)height / targetDimension.height);
//        ImageIcon scaledImage = new ImageIcon(originalImage.getScaledInstance((int)(width / scale),
//                  targetDimension.height,
//                  Image.SCALE_SMOOTH));

        ImageIcon scaledImage = new ImageIcon(originalImage.getScaledInstance(targetDimension.width,
                (int)(height / scale),
                Image.SCALE_SMOOTH));

        JLabel icon = new JLabel(scaledImage);
        icon.setPreferredSize(new Dimension(targetDimension.width, targetDimension.height));
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

        return icon;
    }

    // creates rigid area
    public Component createRigidArea() {
        return Box.createRigidArea(new Dimension(50, 20));
    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public OCafe getController() {
        return controller;
    }

    // image observer
    private class ItemImageObserver implements ImageObserver {

        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }
    }
}
