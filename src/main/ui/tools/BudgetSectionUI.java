package ui.tools;

import model.BudgetProfile;
import model.BudgetSection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BudgetSectionUI extends JInternalFrame {

    private BudgetProfile profile;
    private String name;
    private JTextArea profileDisplay;
    private boolean profileNullCheck;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 400;

    public BudgetSectionUI(BudgetProfile bp, Component parent) {
        if (profileDisplay == null) {
            profileDisplay = new JTextArea(toString(bp));
        } else {
            //remove the previous JFrame
            profileDisplay.setText(toString(bp));
        }

//        profileDisplay = new JTextArea(toString(bp));
        profileDisplay.setEditable(false);
        profileDisplay.setAlignmentX(CENTER_ALIGNMENT);

        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(profileDisplay);
        setSize(WIDTH, HEIGHT);
        setTitle("Budget Sections");
        setPosition(parent);
        setVisible(true);
    }

    public String toString(BudgetProfile profile) {
        System.out.println("Current Budget: ");
        ArrayList<String> str = new ArrayList<>();

        if (profile.getBudgetSectionList().size() == 0) {
            str.add("Budget section list is empty." + "\n");
        } else {
            for (BudgetSection bs : profile.getBudgetSectionList()) {
                str.add("\n" + "Budget Section Name: " + bs.getName() + "\nRemaining balance: "
                        + bs.getRemainingBalance() + "\nCurrent budget limit: " + bs.getLimit() + "\n");
            }
        }
        String listString = String.join("", str);
        return listString;
    }

    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - WIDTH - WIDTH + 30, 0);
    }



}
