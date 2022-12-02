package ui;

import model.BudgetProfile;
import model.BudgetSection;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.BudgetSectionUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Referenced AlarmSystem:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/tree/main/src/main/ca/ubc/cpsc210/alarm/ui

// Class responsible for graphical user interface
public class GUI extends JFrame {

    private static final String JSON_STORE = "./data/budgetProfile.json";
    public static final int WIDTH = 769;
    public static final int HEIGHT = 468;

    private String imagePath = "./data/currencyBackground.jpg";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private BudgetProfile profile;
    private BudgetSection budgetSection;
    private JInternalFrame controlPanel;
    private JDesktopPane desktop;

    private JDialog dialog;
    private JTextArea profileDisplay;
    private BudgetSectionUI budgetSectionUI;

    // EFFECTS: Constructor for GUI class
    public GUI() {
        launchJson();
        launchDesktop();
        initialization();
        addButtonPanel();
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next + "\n");
                }
                System.exit(0);
            }
        });
        centreOnScreen();
        setVisible(true);
    }

    // EFFECTS: Initializes multiple objects; sets name and size
    public void initialization() {
        controlPanel = new JInternalFrame("Menu", true, false, false, false);
        controlPanel.setPreferredSize(new Dimension(185, HEIGHT - 100));
        controlPanel.setLayout(new BorderLayout());
        setContentPane(desktop);
        String profileNLoc = JOptionPane.showInputDialog(null,
                "Enter profile name: ", "Profile Name", JOptionPane.QUESTION_MESSAGE);
        profile = new BudgetProfile(profileNLoc);
        profileDisplay = new JTextArea();
        setTitle(profileNLoc + "'s Personal Budget Manager");
        setSize(WIDTH, HEIGHT);
    }

    // EFFECTS: Json Initialization
    public void launchJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: Creates a new desktop
    public void launchDesktop() {
        desktop = new JDesktopPane() {

            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage();

            // MODIFIES: desktop
            // EFFECTS: draws image to desktop
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(image, 0, 0, null);
            }
        };
        desktop.setBackground(Color.white);
    }

    // MODIFIES: controlPanel
    // EFFECTS: Creates a panel of 5 clickable buttons
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(new JButton(new DisplayBudgetSections()));
        buttonPanel.add(new JButton(new AddBudgetSection()));
        buttonPanel.add(new JButton(new EditBudgetSection()));
        buttonPanel.add(new JButton(new SaveBudgetProfile()));
        buttonPanel.add(new JButton(new LoadBudgetProfile()));

        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    // EFFECTS: Centers frame on screen
    public void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // Represents the button that displays the current list of budget sections
    private class DisplayBudgetSections extends AbstractAction {

        // EFFECTS: Constructor for DisplayBudgetSections
        DisplayBudgetSections() {
            super("Display Budget Sections");
        }

        // MODIFIES: budgetSectionUI
        // EFFECTS: If profile display already exists, disposes; creates new profile display frame with updated profile
        @Override
        public void actionPerformed(ActionEvent event) {
            if (budgetSectionUI != null) {
                budgetSectionUI.setVisible(false);
                budgetSectionUI.dispose();
            }
            budgetSectionUI = new BudgetSectionUI(profileDisplay, profile, GUI.this);
            desktop.add(budgetSectionUI);
        }
    }

    // Represents the button that adds a budget section
    private class AddBudgetSection extends AbstractAction {

        // EFFECTS: Constructor for AddBudgetSection
        AddBudgetSection() {
            super("Add Budget Section");
        }

        // MODIFIES: profile
        // EFFECTS: Adds budget section to profile with given name and limit
        @Override
        public void actionPerformed(ActionEvent event) {
            String budgetSectionNameLoc = JOptionPane.showInputDialog(null,
                    "Enter budget section name: ", "Budget Section Name", JOptionPane.QUESTION_MESSAGE);
            String initialLimitLoc = JOptionPane.showInputDialog(null,
                    "Enter initial limit: ", "Initial Limit", JOptionPane.QUESTION_MESSAGE);
            budgetSection = new BudgetSection(budgetSectionNameLoc, Double.parseDouble(initialLimitLoc));
            profile.addBudgetSection(budgetSection);
        }

    }

    // Represents the button that edits a budget section
    private class EditBudgetSection extends AbstractAction {

        // EFFECTS: Constructor for edit budget section class
        EditBudgetSection() {
            super("Edit Budget Section");
        }

        // MODIFIES: bs, profile
        // EFFECTS: Edits budget section with given name and amount
        @Override
        public void actionPerformed(ActionEvent event) {
            String budgetSectionNameLoc = JOptionPane.showInputDialog(null,
                    "Enter budget section name: ", "Edit Budget Section", JOptionPane.QUESTION_MESSAGE);
            if (findBudgetSection(budgetSectionNameLoc) != null) {
                String amountLoc = JOptionPane.showInputDialog(null,
                        "Enter amount to increase/decrease", "Edit Amount", JOptionPane.QUESTION_MESSAGE);
                editBudgetSectionDialog(amountLoc, budgetSectionNameLoc);
            }
        }
    }

    // MODIFIES: dialog
    // EFFECTS: Creates dialog of buttons that include 4 different edit options
    public void editBudgetSectionDialog(String amountLoc, String budgetSectionNameLoc) {

        JPanel buttonPanelEdit = new JPanel();
        buttonPanelEdit.setLayout(new GridLayout(1, 4));
        dialog = new JDialog(this, true);
        JTextArea label = new JTextArea("Choose an edit option");
        label.setEditable(false);
        addEditButtons(buttonPanelEdit, amountLoc, budgetSectionNameLoc);
        dialog.setSize(600,100);
        dialog.setLocationRelativeTo(null);
        dialog.add(BorderLayout.SOUTH, buttonPanelEdit);
        dialog.add(BorderLayout.CENTER, label);
        dialog.setVisible(true);
        dialog.pack();
        desktop.add(dialog);
    }

    // MODIFIES: buttonPanelEdit
    // EFFECTS: Adds budget section to the panel
    public void addEditButtons(JPanel buttonPanelEdit, String amountLoc, String budgetSectionNameLoc) {
        buttonPanelEdit.add(new JButton(new IncreaseLimit(Double.parseDouble(amountLoc),
                findBudgetSection(budgetSectionNameLoc))));
        buttonPanelEdit.add(new JButton(new DecreaseLimit(Double.parseDouble(amountLoc),
                findBudgetSection(budgetSectionNameLoc))));
        buttonPanelEdit.add(new JButton(new IncreaseBalance(Double.parseDouble(amountLoc),
                findBudgetSection(budgetSectionNameLoc))));
        buttonPanelEdit.add(new JButton(new DecreaseBalance(Double.parseDouble(amountLoc),
                findBudgetSection(budgetSectionNameLoc))));
    }

    // EFFECTS: Finds a budget section when given a name; returns null if no section matches the name entered
    public BudgetSection findBudgetSection(String name) {
        for (BudgetSection bs : profile.getBudgetSectionList()) {
            if (name.equals(bs.getName())) {
                return bs;
            }
        }
        return null;
    }

    // Represents button that increases the budget section limit
    private class IncreaseLimit extends AbstractAction {

        double amount;
        BudgetSection bs;

        // EFFECTS: Constructor for IncreaseLimit class
        public IncreaseLimit(double amount, BudgetSection bs) {
            super("Increase Limit");
            this.amount = amount;
            this.bs = bs;
        }

        // MODIFIES: bs
        // EFFECTS: Increases the limit of a budget section
        @Override
        public void actionPerformed(ActionEvent event) {
            bs.increaseLimit(amount);
        }
    }

    // Represents button that decreases budget section limit
    private class DecreaseLimit extends AbstractAction {

        double amount;
        BudgetSection bs;

        // EFFECTS: Constructor for DecreaseLimit class
        public DecreaseLimit(double amount, BudgetSection bs) {
            super("Decrease Limit");
            this.amount = amount;
            this.bs = bs;
        }

        // MODIFIES: bs
        // EFFECTS: Decreases the limit of a budget section
        @Override
        public void actionPerformed(ActionEvent event) {
            bs.decreaseLimit(amount);
        }
    }

    // Represents button that increases budget section balance
    private class IncreaseBalance extends AbstractAction {

        double amount;
        BudgetSection bs;

        // EFFECTS: Constructor for IncreaseBalance class
        public IncreaseBalance(double amount, BudgetSection bs) {
            super("Increase Balance");
            this.amount = amount;
            this.bs = bs;
        }

        // MODIFIES: bs
        // EFFECTS: Increases the balance of a budget section
        @Override
        public void actionPerformed(ActionEvent event) {
            bs.depositToBalance(amount);
        }
    }

    // Represents button that decreases budget section balance
    private class DecreaseBalance extends AbstractAction {

        double amount;
        BudgetSection bs;

        // EFFECTS: Constructor for DecreaseBalance class
        public DecreaseBalance(double amount, BudgetSection bs) {
            super("Decrease Balance");
            this.amount = amount;
            this.bs = bs;
        }

        // MODIFIES: bs
        // EFFECTS: Decreases balance of a budget section
        @Override
        public void actionPerformed(ActionEvent event) {
            bs.withdrawalFromBalance(amount);
        }
    }

    // Represents button that saves budget profile
    private class SaveBudgetProfile extends AbstractAction {

        // EFFECTS: Constructor for SaveBudgetProfile class
        public SaveBudgetProfile() {
            super("Save Budget Profile");
        }

        // EFFECTS: Saves a budget profile
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                jsonWriter.open();
                jsonWriter.write(profile);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                // Unable to open
            }
        }

    }

    // Represents button that loads budget profile
    private class LoadBudgetProfile extends AbstractAction {

        // EFFECTS: Constructor for LoadBudgetProfile
        public LoadBudgetProfile() {
            super("Load Budget Profile");
        }

        // EFFECTS: Loads previously saved budget profile
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                profile = jsonReader.read();
            } catch (IOException e) {
                // Unable to load file
            }
        }
    }

    // Main class for budget manager GUI; runs GUI
    public static void main(String[] args) {
        new GUI();
    }

}
