package ui;

import model.BudgetProfile;
import model.BudgetSection;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Referenced the TellerApp example
// TellerApp Github link: https://github.students.cs.ubc.ca/CPSC210/TellerApp

// Referenced the Json Serialization Demo
// Json Serialization Demo Github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Class that takes user through the application's features (responsible for the user interface)
public class BudgetApp {
    private static final String JSON_STORE = "./data/budgetProfile.json";
    private Scanner input;
    private BudgetProfile profile;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the budget application
    public BudgetApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBudgetApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes input from user; continues program until user enters 'q'
    public void runBudgetApp() {

        boolean keepGoing = true;
        String command = null;

        System.out.println("Personal Budget Manager: ");

        //input = new Scanner(System.in);
        createProfile();

        while (keepGoing) {
            displayMenuInitial();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                interpretCommand(command);
            }

        }

        EventLog.getInstance().iterator();

        System.out.println("Closing budget manager application...");;
    }

    // EFFECTS: Displays a menu of options to the user (display, add, edit)
    public void displayMenuInitial() {
        System.out.println("Hi " + profile.getProfileName() + ". Select one of the options below:");
        System.out.println("d - display budget sections");
        System.out.println("a - add budget section");
        System.out.println("e - edit a budget section");
        System.out.println("l - load budget profile");
        System.out.println("s - save budget profile");
        System.out.println("q - quit");
    }

    // MODIFIES: this
    // EFFECTS: processes command from user
    public void interpretCommand(String command) {
        if (command.equals("d")) {
            displayBudgetSections();
        } else if (command.equals("a")) {
            addBudgetSection();
        } else if (command.equals("e")) {
            editBudgetSection();
        } else if (command.equals("l")) {
            loadBudgetProfile();
        } else if (command.equals("s")) {
            saveBudgetProfile();
        } else {
            System.out.println("User input invalid. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a new profile for the user (with name and list of budget sections)
    public void createProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter profile name: ");
        String profileName = scanner.nextLine();
        profile = new BudgetProfile(profileName);
        System.out.println("Name for profile: " + profile.getProfileName());
    }

    // EFFECTS: Displays all the budget sections in the profile; if empty, outputs a message
    public void displayBudgetSections() {
        System.out.println("Current Budget: ");
        for (BudgetSection bs : profile.getBudgetSectionList()) {
            System.out.println("\n" + "Budget Section Name: " + bs.getName());
            System.out.println("Remaining balance: " + bs.getRemainingBalance());
            System.out.println("Current budget limit: " + bs.getLimit() + "\n");
        }
        if (profile.getBudgetSectionList().size() == 0) {
            System.out.println("Budget section list is empty." + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a new budget section to the profile
    public void addBudgetSection() {
        String name;
        double lim;
        System.out.println("Enter name for budget section: ");
        name = input.next();
        System.out.println("Enter initial limit for section: ");
        lim = input.nextDouble();
        BudgetSection newBudgetSection = new BudgetSection(name, lim);
        profile.addBudgetSection(newBudgetSection);
    }

    // MODIFIES: this
    // EFFECTS: Edits a section specified by the user by the amount specified by the user
    public void editBudgetSection() {
        System.out.println("edit budget section");
        int numCommand;
        double amount = 0;
        String sectionName = null;
        System.out.println("Enter the name of the element you would like to edit: ");
        sectionName = input.next();
        BudgetSection budgetSection = findBudgetSection(sectionName);
        displayEditSectionMenu();
        numCommand = input.nextInt();
        System.out.println("Enter amount you would like to increase/decrease: ");
        amount = input.nextDouble();
        interpretEditSectionMenuCommand(numCommand, budgetSection, amount);
    }

    // EFFECTS: Displays list of different edit options to the user
    public void displayEditSectionMenu() {
        System.out.println("How would you like to edit the budget section?");
        System.out.println("1 - Increase limit");
        System.out.println("2 - Decrease limit");
        System.out.println("3 - Increase balance");
        System.out.println("4 - Decrease balance");
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Processes user command and edits specified budget by specified amount
    public void interpretEditSectionMenuCommand(int command, BudgetSection budgetSection, double amount) {
        if (command == 1) {
            budgetSection.increaseLimit(amount);
        } else if (command == 2) {
            budgetSection.decreaseLimit(amount);
        } else if (command == 3) {
            budgetSection.depositToBalance(amount);
        } else if (command == 4) {
            budgetSection.withdrawalFromBalance(amount);
        } else {
            System.out.println("User input invalid. Please try again.");
        }
    }

    // EFFECTS: Finds budget section specified by user; if none match, outputs message
    public BudgetSection findBudgetSection(String name) {
        for (BudgetSection bs : profile.getBudgetSectionList()) {
            if (name.equals(bs.getName())) {
                System.out.println("The budget name has been found!!!!!");
                return bs;
            }
        }
        System.out.println("No budget section found matching that name...");
        return null;
    }

    // EFFECTS: saves the budget profile to file
    private void saveBudgetProfile() {
        try {
            jsonWriter.open();
            jsonWriter.write(profile);
            jsonWriter.close();
            System.out.println("Saved " + profile.getProfileName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads budget profile from file
    private void loadBudgetProfile() {
        try {
            profile = jsonReader.read();
            System.out.println("Loaded " + profile.getProfileName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }




}
