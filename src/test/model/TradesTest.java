package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TradesTest {
    private Trade trade1;
    private Trade trade2;
    private Trades trades;

    @BeforeEach
    public void setUp() {
        trade1 = new Trade();
        trade2 = new Trade();
        trades = new Trades();
    }

    @Test
    public void testTrades() {
        assertEquals(0, trades.getTrades().size());
    }

    @Test
    public void testAddTrade() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
    }

    @Test
    public void testRemoveTrade() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        try {
            trades.removeTrade(0);
        } catch (IndexOutOfBoundsException e) {
            fail("Not expecting exception");
        }
        assertEquals(0, trades.getTrades().size());
    }

    @Test
    public void testRemoveNonexistentHighTradeNumber() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        try {
            trades.removeTrade(2);
            fail("Expecting exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught");
        }
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
    }

    @Test
    public void testRemoveNonexistentLowTradeNumber() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        try {
            trades.removeTrade(-2);
            fail("Expecting exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught");
        }
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
    }

    @Test
    public void testRemoveTradeDecreasingTradeNumber() {
        trades.addTrade(trade1);
        trades.addTrade(trade2);
        assertEquals(2, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        assertTrue(trades.getTrades().contains(trade2));
        try {
            trades.removeTrade(0);
        } catch (IndexOutOfBoundsException e) {
            fail("Not expecting exception");
        }
        assertEquals(1, trade2.getTradeNumber());
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade2));
        assertFalse(trades.getTrades().contains(trade1));
    }

    @Test
    public void testRemoveTradeNotDecreasingTradeNumber() {
        trades.addTrade(trade1);
        trades.addTrade(trade2);
        assertEquals(2, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        assertTrue(trades.getTrades().contains(trade2));
        try {
            trades.removeTrade(1);
        } catch (IndexOutOfBoundsException e) {
            fail("Not expecting exception");
        }
        assertEquals(1, trade1.getTradeNumber());
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        assertFalse(trades.getTrades().contains(trade2));
    }


    @Test
    public void testFindTrade() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        try {
            assertTrue(trades.findTrade(0).equals(trade1));
        } catch (IndexOutOfBoundsException e) {
            fail("Not expecting exception");
        }
    }

    @Test
    public void testFindNonexistentHighTradeNumber() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        try {
            trades.findTrade(2);
            fail("Expecting exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught");
        }
    }

    @Test
    public void testFindNonexistentLowTradeNumber() {
        trades.addTrade(trade1);
        assertEquals(1, trades.getTrades().size());
        assertTrue(trades.getTrades().contains(trade1));
        try {
            trades.findTrade(-2);
            fail("Expecting exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught");
        }
    }
}
