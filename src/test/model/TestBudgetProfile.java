package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// Test class for BudgetProfile
public class TestBudgetProfile {

    private BudgetProfile profile1;
    private BudgetSection section1;
    private BudgetSection section2;
    private EventLog el;

    @BeforeEach
    public void setUp(){
        profile1 = new BudgetProfile("Timothy Green");
        section1 = new BudgetSection("Groceries", 400);
        section2 = new BudgetSection("Subscriptions", 50);
        el = EventLog.getInstance();
        el.clear();
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

    @Test
    public void testToStringEmpty() {
        String str = "Budget section list is empty." + "\n";
        assertEquals(str, profile1.toString());
    }

    @Test
    public void testToStringNotEmpty() {
        String str = "\nBudget Section Name: Groceries\n" +
                "Remaining balance: 400.0\n" +
                "Current budget limit: 400.0\n";
        profile1.addBudgetSection(section1);
        assertEquals(str, profile1.toString());
    }

    @Test
    public void testToStringTwoSections() {
        String str = "\nBudget Section Name: Groceries\n" +
                "Remaining balance: 400.0\n" +
                "Current budget limit: 400.0\n" +
                "\n" +
                "Budget Section Name: Subscriptions\n" +
                "Remaining balance: 50.0\n" +
                "Current budget limit: 50.0\n";
        profile1.addBudgetSection(section1);
        profile1.addBudgetSection(section2);
        assertEquals(str, profile1.toString());
    }

    @Test
    public void testLogEventAddBudgetSection() {
        profile1.addBudgetSection(section1);
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("Budget section added to profile.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testLogEventToString() {
        profile1.toString();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("Displayed list of budget sections.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

}
