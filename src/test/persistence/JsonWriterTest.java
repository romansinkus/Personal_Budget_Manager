package persistence;

import model.BudgetProfile;
import model.BudgetSection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Referenced the Json Serialization Demo
// Json Serialization Demo Github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Test class for writing data to the json file
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            BudgetProfile bp = new BudgetProfile("My Budget Profile");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBudgetProfile() {
        try {
            BudgetProfile bp = new BudgetProfile("My Budget Profile");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudgetProfile.json");
            writer.open();
            writer.write(bp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudgetProfile.json");
            bp = reader.read();
            assertEquals("My Budget Profile", bp.getProfileName());
            assertEquals(0, bp.numBudgetSections());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBudgetProfile() {
        try {
            BudgetProfile bp = new BudgetProfile("My Budget Profile");
            bp.addBudgetSection(new BudgetSection("technology", 1000));
            bp.getBudgetSectionList().get(0).setRemainingBalance(900);
            bp.addBudgetSection(new BudgetSection("food", 350));
            bp.getBudgetSectionList().get(1).setRemainingBalance(200);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudgetProfile.json");
            writer.open();
            writer.write(bp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudgetProfile.json");
            bp = reader.read();
            assertEquals("My Budget Profile", bp.getProfileName());
            List<BudgetSection> budgetSections = bp.getBudgetSectionList();
            assertEquals(2, budgetSections.size());
            checkBudgetSection("technology", 1000, 900, budgetSections.get(0));
            checkBudgetSection("food", 350, 200, budgetSections.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
