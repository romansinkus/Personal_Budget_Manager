package ui.tools;

import model.BudgetProfile;
import model.BudgetSection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Referenced AlarmSystem:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/tree/main/src/main/ca/ubc/cpsc210/alarm/ui

// Class responsible for creating a window with displayed budget sections
public class BudgetSectionUI extends JInternalFrame {

    private static final int WIDTH = 250;
    private static final int HEIGHT = 400;

    // EFFECTS: Constructor for BudgetSectionUI class
    public BudgetSectionUI(JTextArea profileDisplay, BudgetProfile bp, Component parent) {

        profileDisplay = new JTextArea(toString(bp));

        profileDisplay.setText(toString(bp));

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

    // EFFECTS: Creates an output string with all budget sections inside a budget profile
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

    // EFFECTS: Sets position of the frame that includes the budget profile information
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - WIDTH - WIDTH + 30, 0);
    }



}
