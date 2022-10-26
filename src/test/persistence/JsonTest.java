package persistence;

import model.BudgetSection;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Referenced the Json Serialization Demo
// Json Serialization Demo Github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Checks that given parameters (name, lim, remBal) are equal to fields of the object that gets passed as a parameter
public class JsonTest {
    protected void checkBudgetSection(String name, double lim, double remBal, BudgetSection budgetSection) {
        assertEquals(name, budgetSection.getName());
        assertEquals(lim, budgetSection.getLimit());
        assertEquals(remBal, budgetSection.getRemainingBalance());
    }
}
