package persistence;

import exceptions.EmptyStringException;
import exceptions.SameNameException;
import model.Trade;
import model.Trades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private Trades trades;
    private JsonWriter jsonWriter;

    @BeforeEach
    public void setUp() {
        trades = new Trades();
    }

    @Test
    void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Trades trades = reader.read();
            fail("Exception expected");
        } catch (IOException e) {
            System.out.println("Caught");
        } catch (SameNameException e) {
            System.out.println("Caught");
        } catch (EmptyStringException e) {
            System.out.println("Caught");
        }
    }

    @Test
    void testReaderEmptyTrades() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/testReaderEmptyTrades.json");
            jsonWriter.open();
            jsonWriter.write(trades);
            jsonWriter.close();
            JsonReader jsonReader = new JsonReader("./data/testReaderEmptyTrades.json");
            Trades trades = jsonReader.read();
            assertEquals(0, trades.getTrades().size());
        } catch (IOException | SameNameException | EmptyStringException e) {
            fail("Not expecting exception");
        }
    }

    @Test
    public void testReaderGeneralTrades() {
        try {
            trades.addTrade(new Trade());
            trades.addTrade(new Trade());
            jsonWriter = new JsonWriter("./data/testReaderGeneralTrades.json");
            jsonWriter.open();
            jsonWriter.write(trades);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testReaderGeneralTrades.json");
            jsonReader.read();
            List<Trade> tradeList = trades.getTrades();
            assertEquals(2, tradeList.size());
        } catch (IOException | SameNameException | EmptyStringException e) {
            fail("Not expecting exception");
        }
    }
}