package model;

import exceptions.EmptyStringException;
import exceptions.NegativeNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    public void setUp() {
        p1 = new Player("Kevin Durant", "Small Forward", 48000000);
        p2 = new Player("Kyle Lowry", "Point Guard", 28000000);
    }

    @Test
    public void testPlayer() {
        assertEquals(p1.getName(), "Kevin Durant");
        assertEquals(p1.getPosition(), "Small Forward");
        assertEquals(p1.getSalary(), 48000000);
        assertEquals(p2.getName(), "Kyle Lowry");
        assertEquals(p2.getPosition(), "Point Guard");
        assertEquals(p2.getSalary(), 28000000);
    }

    @Test
    public void testSetName() {
        try {
            p1.setName("Ben Simmons");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(p1.getName().equals("Ben Simmons"));
    }

    @Test
    public void testSetEmptyName() {
        try {
            p1.setName("");
            fail("Expecting exception");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(p1.getName().equals("Kevin Durant"));
    }

    @Test
    public void testSetPosition() {
        try {
            p1.setPosition("Point Guard");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(p1.getPosition().equals("Point Guard"));
    }

    @Test
    public void testSetEmptyPosition() {
        try {
            p1.setPosition("");
            fail("Expecting exception");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(p1.getPosition().equals("Small Forward"));
    }

    @Test
    public void testSetPositiveSalary() {
        try {
            p1.setSalary(100);
        } catch (NegativeNumberException e) {
            fail("Not expecting exception");
        }
        assertEquals(100, p1.getSalary());
    }

    @Test
    public void testSetNegativeSalary() {
        try {
            p1.setSalary(-100);
            fail("Expecting exception");
        } catch (NegativeNumberException e) {
            System.out.println("Caught");
        }
        assertEquals(48000000, p1.getSalary());
    }
}
