package ui;

import model.BudgetProfile;
import model.BudgetSection;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.BudgetSectionUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI extends JFrame {

    private static final String JSON_STORE = "./data/budgetProfile.json";
    public static final int WIDTH = 769;
    public static final int HEIGHT = 468;

    String imagePath = "./data/currencyBackground.jpg";

    JsonWriter jsonWriter;
    JsonReader jsonReader;

    private BudgetProfile profile;
    private BudgetSection budgetSection;
    JInternalFrame controlPanel;
    JInternalFrame editControlPanel;
    JDesktopPane desktop;

    public GUI() {
        launchJson();
        launchDesktop();
//        profile = new BudgetProfile("My budget profile"); // Not necessary
        controlPanel = new JInternalFrame("Menu", true, false, false, false);
        controlPanel.setPreferredSize(new Dimension(185, HEIGHT - 100));
        controlPanel.setLayout(new BorderLayout());
        setContentPane(desktop);
        String profileNLoc = JOptionPane.showInputDialog(null,
                "Enter profile name: ", "Profile Name", JOptionPane.QUESTION_MESSAGE);
        profile = new BudgetProfile(profileNLoc);
        setTitle(profileNLoc + "'s Personal Budget Manager");
        setSize(WIDTH, HEIGHT);
        addButtonPanel();
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        centreOnScreen();
        setVisible(true);
    }

    // EFFECTS: Json Initialization
    public void launchJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public void launchDesktop() {
        desktop = new JDesktopPane() {

            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage();

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(image, 0, 0, null);
            }
        };
        desktop.setBackground(Color.white);
    }

    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
//        buttonPanel.add(new JButton(new CreateBudgetProfile())); // Not necessary!!
        buttonPanel.add(new JButton(new DisplayBudgetSections()));
        buttonPanel.add(new JButton(new AddBudgetSection()));
        buttonPanel.add(new JButton(new EditBudgetSection()));
        buttonPanel.add(new JButton(new SaveBudgetProfile()));
        buttonPanel.add(new JButton(new LoadBudgetProfile()));

        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    public void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private class CreateBudgetProfile extends AbstractAction {

        CreateBudgetProfile() {
            super("Create Budget Profile");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String profileNLoc = JOptionPane.showInputDialog(null,
                    "Enter profile name: ", "Profile Name", JOptionPane.QUESTION_MESSAGE);
            profile = new BudgetProfile(profileNLoc);
        }

    }

    private class DisplayBudgetSections extends AbstractAction {

        DisplayBudgetSections() {
            super("Display Budget Sections");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            desktop.add(new BudgetSectionUI(profile, GUI.this));
        }

    }

    private class AddBudgetSection extends AbstractAction {

        AddBudgetSection() {
            super("Add Budget Section");
        }

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

    private class EditBudgetSection extends AbstractAction {

        EditBudgetSection() {
            super("Edit Budget Section");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String budgetSectionNameLoc = JOptionPane.showInputDialog(null,
                    "Enter budget section name: ", "Edit Budget Section", JOptionPane.QUESTION_MESSAGE);
            if (findBudgetSection(budgetSectionNameLoc) != null) {
                String amountLoc = JOptionPane.showInputDialog(null,
                        "Enter amount to increase/decrease", "Edit Amount", JOptionPane.QUESTION_MESSAGE);
                editControlPanel = new JInternalFrame("Edit Options", true, false, false, false);
                editControlPanel.setPreferredSize(new Dimension(WIDTH - 210, 100));
                editControlPanel.setLayout(new BorderLayout());
                JPanel buttonPanelEdit = new JPanel();
                buttonPanelEdit.setLayout(new GridLayout(1, 4));

//                buttonPanelEdit.add(new JButton(new IncreaseLimit(Double.parseDouble(amountLoc),
//                        findBudgetSection(budgetSectionNameLoc))));
//                buttonPanelEdit.add(new JButton(new DecreaseLimit(Double.parseDouble(amountLoc),
//                        findBudgetSection(budgetSectionNameLoc))));
//                buttonPanelEdit.add(new JButton(new IncreaseBalance(Double.parseDouble(amountLoc),
//                        findBudgetSection(budgetSectionNameLoc))));
//                buttonPanelEdit.add(new JButton(new DecreaseBalance(Double.parseDouble(amountLoc),
//                        findBudgetSection(budgetSectionNameLoc))));

                addEditButtons(buttonPanelEdit, amountLoc, budgetSectionNameLoc);

                editControlPanel.add(buttonPanelEdit, BorderLayout.EAST);
                editControlPanel.pack();
                editControlPanel.setVisible(true);
                editControlPanel.setLocation(195, 0);
                desktop.add(editControlPanel);
            }
        }
    }

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

    public BudgetSection findBudgetSection(String name) {
        for (BudgetSection bs : profile.getBudgetSectionList()) {
            if (name.equals(bs.getName())) {
                return bs;
            }
        }
        return null;
    }

    private class IncreaseLimit extends AbstractAction {

        double amount;
        BudgetSection bs;

        public IncreaseLimit(double amount, BudgetSection bs) {
            super("Increase Limit");
            this.amount = amount;
            this.bs = bs;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            bs.increaseLimit(amount);
        }
    }

    private class DecreaseLimit extends AbstractAction {

        double amount;
        BudgetSection bs;

        public DecreaseLimit(double amount, BudgetSection bs) {
            super("Decrease Limit");
            this.amount = amount;
            this.bs = bs;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            bs.decreaseLimit(amount);
        }
    }

    private class IncreaseBalance extends AbstractAction {

        double amount;
        BudgetSection bs;

        public IncreaseBalance(double amount, BudgetSection bs) {
            super("Increase Balance");
            this.amount = amount;
            this.bs = bs;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            bs.depositToBalance(amount);
        }
    }

    private class DecreaseBalance extends AbstractAction {

        double amount;
        BudgetSection bs;

        public DecreaseBalance(double amount, BudgetSection bs) {
            super("Decrease Balance");
            this.amount = amount;
            this.bs = bs;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            bs.withdrawalFromBalance(amount);
        }
    }

    private class SaveBudgetProfile extends AbstractAction {

        public SaveBudgetProfile() {
            super("Save Budget Profile");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                jsonWriter.open();
                jsonWriter.write(profile);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
//                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }

    }

    private class LoadBudgetProfile extends AbstractAction {

        public LoadBudgetProfile() {
            super("Load Budget Profile");
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                profile = jsonReader.read();
            } catch (IOException e) {
//                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }

    }

    public static void main(String[] args) {
        new GUI();
    }


    //    public void displayBudgetSections() {
//        System.out.println("Current Budget: ");
//        for (BudgetSection bs : profile.getBudgetSectionList()) {
//            System.out.println("\n" + "Budget Section Name: " + bs.getName());
//            System.out.println("Remaining balance: " + bs.getRemainingBalance());
//            System.out.println("Current budget limit: " + bs.getLimit() + "\n");
//        }
//        if (profile.getBudgetSectionList().size() == 0) {
//            System.out.println("Budget section list is empty." + "\n");
//        }
//    }

}
