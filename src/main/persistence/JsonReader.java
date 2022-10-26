package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.BudgetProfile;
import model.BudgetSection;
import org.json.*;

// Referenced the Json Serialization Demo
// Json Serialization Demo Github link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads budget profile from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BudgetProfile read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudgetProfile(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budget profile from JSON object and returns it
    private BudgetProfile parseBudgetProfile(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BudgetProfile bp = new BudgetProfile(name);
        addBudgetSections(bp, jsonObject);
        return bp;
    }

    // MODIFIES: bp
    // EFFECTS: parses budget sections from JSON object and adds them to budget profile
    private void addBudgetSections(BudgetProfile bp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("budget sections");
        for (Object json : jsonArray) {
            JSONObject nextSection = (JSONObject) json;
            addBudgetSection(bp, nextSection);
        }
    }

    // MODIFIES: bp
    // EFFECTS: parses budget section from JSON object and adds it to budget profile
    private void addBudgetSection(BudgetProfile bp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double limit = jsonObject.getDouble("limit");
        double remainingBalance = jsonObject.getDouble("balance");
        BudgetSection budgetSection = new BudgetSection(name, limit);
        budgetSection.setRemainingBalance(remainingBalance);
        bp.addBudgetSection(budgetSection);
    }
}

