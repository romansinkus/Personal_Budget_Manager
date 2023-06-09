package model;

import org.json.JSONObject;
import persistence.Writable;

// Referenced the Json Serialization Demo
// Json Serialization Demo Github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Class that includes all the information in a given budget section (name, limit, remaining balance)
public class BudgetSection implements Writable {
    private String name;
    private double limit;
    private double remainingBalance;

    // EFFECTS: Creates budget section object
    public BudgetSection(String budgetSectionName, double initialLimit) {
        this.name = budgetSectionName;
        this.limit = initialLimit;
        this.remainingBalance = initialLimit;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Subtracts from remainingBalance. Doesn't withdrawal amount entered if greater than remainingBalance
    public boolean withdrawalFromBalance(double amount) {
        if (amount <= remainingBalance) {
            remainingBalance -= amount;
            EventLog.getInstance().logEvent(new Event("Money withdrawn from balance."));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Increase the remainingBalance by amount
    public void depositToBalance(double amount) {
        remainingBalance += amount;
        EventLog.getInstance().logEvent(new Event("Money deposited to balance."));
    }

    // REQUIRES: limitIncrease > 0
    // MODIFIES: this
    // EFFECTS: Increase limit by limitIncrease
    public void increaseLimit(double limitIncrease) {
        limit += limitIncrease;
        EventLog.getInstance().logEvent(new Event("Budget section limit increased."));
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Subtracts from limit. Doesn't subtract from limit if limitDecrease is greater than limit
    public boolean decreaseLimit(double limitDecrease) {
        if (limitDecrease <= limit) {
            limit -= limitDecrease;
            EventLog.getInstance().logEvent(new Event("Budget section limit decreased."));
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: Returns name of budget section
    public String getName() {
        return name;
    }

    // EFFECTS: Returns current limit of budget section
    public double getLimit() {
        return limit;
    }

    // EFFECTS: Returns current balance remaining in budget section
    public double getRemainingBalance() {
        return remainingBalance;
    }

    // EFFECTS: Setter for the balance in budget section
    public void setRemainingBalance(double num) {
        this.remainingBalance = num;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("limit", limit);
        json.put("balance", remainingBalance);
        return json;
    }
}
