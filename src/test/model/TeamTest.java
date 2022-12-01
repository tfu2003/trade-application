package model;

import exceptions.CannotFindNameException;
import exceptions.EmptyStringException;
import exceptions.NegativeNumberException;
import exceptions.SameNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Team team;

    @BeforeEach
    public void setUp() {
        player1 = new Player("Kevin Durant", "Small Forward", 48000000);
        player2 = new Player("Kyle Lowry", "Point Guard", 28000000);
        player3 = new Player("", "Shooting Guard", 2000000);
        player4 = new Player("Ben Simmons", "", 1000000);
        team = new Team("Toronto Raptors");
    }

    @Test
    public void testTeam() {
        assertEquals(0, team.getPlayerNames().size());
    }

    @Test
    public void testSetName() {
        try {
            team.setName("Brooklyn Nets");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getName().equals("Brooklyn Nets"));
    }

    @Test
    public void testSetEmptyName() {
        try {
            team.setName("");
            fail("Expecting exception");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(team.getName().equals("Toronto Raptors"));
    }

    @Test
    public void testGetPlayers() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail ("Not expecting exception");
        } catch (EmptyStringException e) {
            fail ("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        assertTrue(team.getPlayers().contains(player1));
        assertEquals(1, team.getPlayers().size());
    }

    @Test
    public void testAddOnePlayerToTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testAddOneEmptyNamePlayerToTeam() {
        try {
            team.addPlayer(player3);
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertEquals(0, team.getPlayerNames().size());
    }

    @Test
    public void testAddOneEmptyPositionPlayerToTeam() {
        try {
            team.addPlayer(player4);
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertEquals(0, team.getPlayerNames().size());
    }

    @Test
    public void testAddTwoSamePlayersToTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.addPlayer(player1);
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testAddTwoPlayersToTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.addPlayer(player2);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertTrue(team.getPlayerNames().contains(player2.getName()));
        assertEquals(2, team.getPlayerNames().size());
    }

    @Test
    public void testRemovePlayerFromTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        team.removePlayer(player1.getName());
        assertEquals(0, team.getPlayerNames().size());
    }

    @Test
    public void testRemoveNonexistentPlayerFromTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        team.removePlayer(player2.getName());
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testFindPlayerInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        assertTrue(team.findPlayer(player1.getName()).equals(player1));
    }

    @Test
    public void testFindNonexistentPlayerInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        team.findPlayer(player2.getName());
    }

    @Test
    public void testEditPlayerNameInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerName(player1.getName(), "New Name");
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains("New Name"));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditPlayerNameToOneExistingInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.addPlayer(player2);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertTrue(team.getPlayerNames().contains(player2.getName()));
        assertEquals(2, team.getPlayerNames().size());
        try {
            team.editPlayerName(player2.getName(), player1.getName());
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertTrue(team.getPlayerNames().contains(player2.getName()));
        assertEquals(2, team.getPlayerNames().size());
    }

    @Test
    public void testEditPlayerNameToEmptyOneInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerName(player1.getName(), "");
            fail("Expecting exception");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditPlayerPositionInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerPosition(player1.getName(), "New Position");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        } catch (CannotFindNameException e) {
            fail("Not expecting exception");
        }
        assertTrue(player1.getPosition().equals("New Position"));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditNonexistentPlayerPositionInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerPosition(player2.getName(), "New Position");
            fail("Expecting exception");
        } catch (EmptyStringException | CannotFindNameException e) {
            System.out.println("Caught");
        }
        assertTrue(player1.getPosition().equals(player1.getPosition()));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditPlayerPositionToEmptyInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerPosition(player1.getName(), "");
            fail("Expecting exception");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        } catch (CannotFindNameException e) {
            System.out.println("Caught");
        }
        assertTrue(player1.getPosition().equals(player1.getPosition()));
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditPlayerSalaryInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerSalary(player1.getName(), 100);
        } catch (CannotFindNameException e) {
            fail("Not expecting exception");
        } catch (NegativeNumberException e) {
            fail("Not expecting exception");
        }
        assertTrue(player1.getSalary() == 100);
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditNonexistentPlayerSalaryInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException | EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerSalary(player2.getName(), 100);
            fail("Expecting exception");
        } catch (CannotFindNameException e) {
            System.out.println("Caught");
        } catch (NegativeNumberException e) {
            System.out.println("Caught");
        }
        assertTrue(player1.getSalary() == player1.getSalary());
        assertEquals(1, team.getPlayerNames().size());
    }

    @Test
    public void testEditPlayerSalaryToNegativeInTeam() {
        try {
            team.addPlayer(player1);
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
        assertTrue(team.getPlayerNames().contains(player1.getName()));
        assertEquals(1, team.getPlayerNames().size());
        try {
            team.editPlayerSalary(player1.getName(), -100);
            fail("Expecting exception");
        } catch (CannotFindNameException e) {
            System.out.println("Caught");
        } catch (NegativeNumberException e) {
            System.out.println("Caught");
        }
        assertTrue(player1.getSalary() == 48000000);
        assertEquals(1, team.getPlayerNames().size());
    }
}

