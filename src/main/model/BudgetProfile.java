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

    // EFFECTS: returns things in this workroom as a JSON array
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
