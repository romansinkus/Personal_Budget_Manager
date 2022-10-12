package model;

import java.util.*;

public class BudgetProfile {
    private String profileName;
    private List<BudgetSection> budgetSectionList;

    // EFFECTS: Creates budgetProfile object
    public BudgetProfile(String profileName) {
        this.profileName = profileName;
        this.budgetSectionList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a budget section to the budgetSectionList
    public void addProfileSection(BudgetSection newSection) {
        if (!(budgetSectionList.contains(newSection))) {
            budgetSectionList.add(newSection);
        }
    }

    // EFFECTS: Returns profile name
    public String getProfileName() {
        return this.profileName;
    }

    // EFFECTS: Returns budgetSectionList
    public List<BudgetSection> getBudgetSectionList() {
        return this.budgetSectionList;
    }
}
