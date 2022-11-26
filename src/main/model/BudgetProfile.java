package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Referenced the Json Serialization Demo
// Json Serialization Demo Github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Class for the collection of budget sections that includes list of budget sections and a profile name
public class BudgetProfile implements Writable {
    private String profileName;
    private List<BudgetSection> budgetSectionList;

    // EFFECTS: Creates budgetProfile object
    public BudgetProfile(String profileName) {
        this.profileName = profileName;
        this.budgetSectionList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a budget section to the budgetSectionList
    public void addBudgetSection(BudgetSection newSection) {
        if (!(budgetSectionList.contains(newSection))) {
            budgetSectionList.add(newSection);
        }
        EventLog.getInstance().logEvent(new Event("Budget Section Added to Profile."));
    }

    // MODIFIES: this
    // EFFECTS: Creates an output string with all budget sections inside a budget profile
    public String toString() {
        ArrayList<String> str = new ArrayList<>();

        if (budgetSectionList.size() == 0) {
            str.add("Budget section list is empty." + "\n");
        } else {
            for (BudgetSection bs : budgetSectionList) {
                str.add("\n" + "Budget Section Name: " + bs.getName() + "\nRemaining balance: "
                        + bs.getRemainingBalance() + "\nCurrent budget limit: " + bs.getLimit() + "\n");
            }
        }
        String listString = String.join("", str);
        EventLog.getInstance().logEvent(new Event("Displayed list of budget sections."));
        return listString;
    }

    // EFFECTS: Returns profile name
    public String getProfileName() {
        return this.profileName;
    }

    // EFFECTS: Returns budgetSectionList
    public List<BudgetSection> getBudgetSectionList() {
        return this.budgetSectionList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", profileName);
        json.put("budget sections", budgetSectionsToJson());
        return json;
    }

    // EFFECTS: returns budgetSections in this budgetProfile as a JSON array
    private JSONArray budgetSectionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BudgetSection bs : budgetSectionList) {
            jsonArray.put(bs.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns number of budget sections in this budget profile
    public int numBudgetSections() {
        return budgetSectionList.size();
    }
}
