package persistence;

import exceptions.EmptyStringException;
import exceptions.SameNameException;
import model.Player;
import model.Team;
import model.Trade;
import model.Trades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private Trades trades;
    private JsonWriter jsonWriter;

    @BeforeEach
    public void setUp() {
        trades = new Trades();
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            jsonWriter.open();
            fail("Exception expected");
        } catch (FileNotFoundException e) {
            System.out.println("Caught");
        }
    }

    @Test
    public void testWriterEmptyTrades() {
        try {
            jsonWriter = new JsonWriter("./data/testWriterEmptyTrades.json");
            jsonWriter.open();
            jsonWriter.write(trades);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterEmptyTrades.json");
            trades = jsonReader.read();
            assertEquals(0, trades.getTrades().size());
        } catch (FileNotFoundException e) {
            fail("Not expecting exception");
        } catch (IOException e) {
            fail("Not expecting exception");
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        }
    }

    @Test
    public void testWriterGeneralTrades() {
        try {
            Player player1 = new Player("Kevin Durant", "Small Forward", 48000000);
            Player player2 = new Player("Kyle Lowry", "Point Guard", 28000000);
            Team team1 = new Team("Brooklyn Nets");
            Team team2 = new Team("Toronto Raptors");
            team1.addPlayer(player1);
            team2.addPlayer(player2);
            Trade trade = new Trade();
            trade.addTeam(team1);
            trade.addTeam(team2);
            trades.addTrade(trade);
            jsonWriter = new JsonWriter("./data/testWriterGeneralTrades.json");
            jsonWriter.open();
            jsonWriter.write(trades);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterGeneralTrades.json");
            jsonReader.read();
            List<Trade> tradeList = trades.getTrades();
            assertEquals(1, tradeList.size());
        } catch (SameNameException e) {
            fail("Not expecting exception");
        } catch (EmptyStringException e) {
            fail("Not expecting exception");
        } catch (FileNotFoundException e) {
            fail("Not expecting exception");
        } catch (IOException e) {
            fail("Not expecting exception");
        }
    }
}
