package model;

import exceptions.CannotFindNameException;
import exceptions.EmptyStringException;
import exceptions.SameNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {
    private Team team1;
    private Team team2;
    private Team team3;
    private Trade trade;

    @BeforeEach
    public void setUp() {
        team1 = new Team("Toronto Raptors");
        team2 = new Team("Brooklyn Nets");
        team3 = new Team("");
        trade = new Trade();
    }

    @Test
    public void testTrade() {
        assertEquals(0, trade.getTeamNames().size());
    }

    @Test
    public void testGetTeams() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail ("Not expecting exception");
        } catch (EmptyStringException e) {
            fail ("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        assertTrue(trade.getTeams().contains(team1));
        assertEquals(1, trade.getTeams().size());
    }

    @Test
    public void testGetTradeNumber() {
        try {
            trade.addTeam(team1);
            trade.setTradeNumber(1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertEquals(1, trade.getTradeNumber());
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        assertTrue(trade.getTeams().contains(team1));
        assertEquals(1, trade.getTeams().size());
    }

    @Test
    public void testAddOneTeamToTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
    }

    @Test
    public void testAddOneEmptyNameTeamToTrade() {
        try {
            trade.addTeam(team3);
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertEquals(0, trade.getTeamNames().size());
    }

    @Test
    public void testAddTwoSameTeamsToTrades() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        try {
            trade.addTeam(team1);
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
    }

    @Test
    public void testAddTwoTeamsToTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        try {
            trade.addTeam(team2);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertTrue(trade.getTeamNames().contains(team2.getName()));
        assertEquals(2, trade.getTeamNames().size());
    }

    @Test
    public void testRemoveTeamFromTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        trade.removeTeam(team1.getName());
        assertEquals(0, trade.getTeamNames().size());
    }

    @Test
    public void testRemoveNonexistentTeamFromTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        trade.removeTeam(team2.getName());
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
    }

    @Test
    public void testEditTeamNameInTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        try {
            trade.editTeamName(team1.getName(), "New Name");
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains("New Name"));
        assertEquals(1, trade.getTeamNames().size());
    }

    @Test
    public void testEditTeamNameToOneExistingInTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        try {
            trade.addTeam(team2);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertTrue(trade.getTeamNames().contains(team2.getName()));
        assertEquals(2, trade.getTeamNames().size());
        try {
            trade.editTeamName(team2.getName(), team1.getName());
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertTrue(trade.getTeamNames().contains(team2.getName()));
        assertEquals(2, trade.getTeamNames().size());
    }

    @Test
    public void testEditTeamNameToEmptyNameInTrade() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        try {
            trade.editTeamName(team1.getName(), "");
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
    }

    @Test
    public void testFindPlayerInTeam() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        assertTrue(trade.findTeam(team1.getName()).equals(team1));
    }

    @Test
    public void testFindNonexistentPlayerInTeam() {
        try {
            trade.addTeam(team1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(trade.getTeamNames().contains(team1.getName()));
        assertEquals(1, trade.getTeamNames().size());
        trade.findTeam(team2.getName());
    }
}
