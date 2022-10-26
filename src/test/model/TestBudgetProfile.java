package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for BudgetProfile
public class TestBudgetProfile {

    private BudgetProfile profile1;
    private BudgetSection section1;
    private BudgetSection section2;

    @BeforeEach
    public void setUp(){
        profile1 = new BudgetProfile("Timothy Green");
        section1 = new BudgetSection("Groceries", 400);
        section2 = new BudgetSection("Subscriptions", 50);
    }

    @Test
    public void testEmptyListBudgetSection() {
        assertEquals(new ArrayList<>(), profile1.getBudgetSectionList());
        assertEquals(0, profile1.getBudgetSectionList().size());
    }

    @Test
    public void testAddProfileSectionDuplicate() {
        assertEquals(new ArrayList<>(), profile1.getBudgetSectionList());
        profile1.addBudgetSection(section1);
        assertEquals(1, profile1.getBudgetSectionList().size());
        profile1.addBudgetSection(section1);
        assertEquals(1, profile1.getBudgetSectionList().size());
    }


    @Test
    public void testAddProfileSection() {
        assertEquals(new ArrayList<>(), profile1.getBudgetSectionList());
        assertEquals(0, profile1.getBudgetSectionList().size());
        profile1.addBudgetSection(section1);
        assertEquals(1, profile1.getBudgetSectionList().size());
    }

    @Test
    public void testAddProfileSectionTwice() {
        assertEquals(new ArrayList<>(), profile1.getBudgetSectionList());
        profile1.addBudgetSection(section1);
        assertEquals(1, profile1.getBudgetSectionList().size());
        assertEquals(section1, profile1.getBudgetSectionList().get(0));
        profile1.addBudgetSection(section2);
        assertEquals(2, profile1.getBudgetSectionList().size());
        assertEquals(section2, profile1.getBudgetSectionList().get(1));
    }

    @Test
    public void testGetProfileName() {
        assertEquals("Timothy Green", profile1.getProfileName());
    }
}
