package ui;

import java.io.FileNotFoundException;

// Main class for the budget manager application; starts the program
public class Main {
    public static void main(String[] args) {
        try {
            new BudgetApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
