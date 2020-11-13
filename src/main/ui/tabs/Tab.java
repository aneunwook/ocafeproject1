package ui.tabs;

import ui.OCafe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class Tab extends JPanel {

    protected OCafe controller;
    protected static final int INSET = 50;

    //REQUIRES: SmartHomeUI controller that holds this tab
    public Tab(OCafe controller) {
        this.controller = controller;
        setBorder(new EmptyBorder(new Insets(INSET, INSET, INSET, INSET)));
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: creates and returns column with button included
//    public JPanel formatButtonColumn(JButton b) {
//        JPanel p = new JPanel();
//        p.setLayout(new FlowLayout());
//        p.add(b);
//
//        return p;
//    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public OCafe getController() {
        return controller;
    }

}
