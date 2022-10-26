package persistence;

import model.BudgetProfile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

//    @Test
//    void testReaderGeneralBudgetProfile() {
//        JsonReader reader = new JsonReader();
//    }
//
}
