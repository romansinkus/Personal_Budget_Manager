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

// Test class for reading data from the json file
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BudgetProfile bp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBudgetProfile.json");
        try {
            BudgetProfile bp = reader.read();
            assertEquals("My Budget Profile", bp.getProfileName());
            assertEquals(0, bp.numBudgetSections());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudgetProfile() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudgetProfile.json");
        try {
            BudgetProfile bp = reader.read();
            assertEquals("My Budget Profile", bp.getProfileName());
            List<BudgetSection> budgetSections = bp.getBudgetSectionList();
            assertEquals(4, budgetSections.size());
            checkBudgetSection("groceries", 400, 200, budgetSections.get(0));
            checkBudgetSection("subscriptions", 40, 35, budgetSections.get(1));
            checkBudgetSection("school", 50, 10, budgetSections.get(2));
            checkBudgetSection("clothing", 40, 40, budgetSections.get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
