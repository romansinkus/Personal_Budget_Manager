package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class testBudgetSection {

    private BudgetSection section;

    @BeforeEach
    public void setup() {
        section = new BudgetSection("Groceries", 200);
    }

    @Test
    public void testIncreaseLimit() {
        section.increaseLimit(100);
        assertEquals(300, section.getLimit());
    }

    @Test
    public void testIncreaseLimitTwoTimes() {
        section.increaseLimit(100);
        assertEquals(300, section.getLimit());
        section.increaseLimit(50);
        assertEquals(350, section.getLimit());
    }

    @Test
    public void testDecreaseLimit() {
        section.decreaseLimit(100);
        assertEquals(100, section.getLimit());
    }

    @Test
    public void testDecreaseLimitTwoTimes() {
        section.decreaseLimit(50);
        assertEquals(150, section.getLimit());
        section.decreaseLimit(50);
        assertEquals(100, section.getLimit());
    }

    @Test
    public void testDecreaseLimitTooMuch() {
        assertFalse(section.decreaseLimit(300));
    }

    @Test
    public void testIncreaseAndDecreaseLimit(){
        section.increaseLimit(100);
        assertEquals(300, section.getLimit());
        section.decreaseLimit(100);
        assertEquals(200, section.getLimit());
    }

    @Test
    public void testDecreaseLimitToZero() {
        section.decreaseLimit(200);
        assertEquals(0, section.getLimit());
    }

    @Test
    public void testWithdrawalOnce() {
        section.withdrawalFromBalance(100);
        assertEquals(100, section.getRemainingBalance());
    }

    @Test
    public void testWithdrawalTwice() {
        section.withdrawalFromBalance(100);
        assertEquals(100, section.getRemainingBalance());
        section.withdrawalFromBalance(50);
        assertEquals(50, section.getRemainingBalance());
    }

    @Test
    public void testDepositToBalance() {
        section.depositToBalance(100);
        assertEquals(300, section.getRemainingBalance());
    }

    @Test
    public void testDepositToBalanceTwoTimes() {
        section.depositToBalance(100);
        assertEquals(300, section.getRemainingBalance());
        section.depositToBalance(200);
        assertEquals(500, section.getRemainingBalance());
    }

    @Test
    public void testDepositThenWithdrawal() {
        section.depositToBalance(100);
        assertEquals(300, section.getRemainingBalance());
        section.withdrawalFromBalance(100);
        assertEquals(200, section.getRemainingBalance());
    }

    @Test
    public void testWithdrawalThenDeposit() {
        section.withdrawalFromBalance(100);
        assertEquals(100, section.getRemainingBalance());
        section.depositToBalance(100);
        assertEquals(200, section.getRemainingBalance());
    }

    @Test
    public void testWithdrawalTooLarge() {
        assertFalse(section.withdrawalFromBalance(300));
    }

    @Test
    public void testWithdrawalTooLargeByOne() {
        assertFalse(section.withdrawalFromBalance(200.1));
    }

    @Test
    public void testGetName() {
        assertEquals("Groceries", section.getName());
    }
}