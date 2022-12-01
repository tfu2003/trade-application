package persistence;

import exceptions.EmptyStringException;
import exceptions.SameNameException;
import model.Player;
import model.Team;
import model.Trade;
import model.Trades;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Some code was taken from the example JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads the trades from JSON data that is stored in a file
public class JsonReader {

    // FIELDS
    private static String source;

    // EFFECTS: Constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads source and returns the trades from it
    public static Trades read() throws IOException, SameNameException, EmptyStringException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrades(jsonObject);
    }

    // EFFECTS: Reads source file as string and returns it
    private static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: Parses through all the trades from JSON object and returns it
    private static Trades parseTrades(JSONObject jsonObject)
            throws SameNameException, EmptyStringException {
        Trades trades = new Trades();
        addTrades(trades, jsonObject);
        return trades;
    }

    // MODIFIES: trades
    // EFFECTS: Parses trades from JSON object and adds them to trades
    private static void addTrades(Trades trades, JSONObject jsonObject)
            throws SameNameException, EmptyStringException {
        JSONArray jsonArray = jsonObject.getJSONArray("trades");
        for (Object json : jsonArray) {
            JSONObject nextTrade = (JSONObject) json;
            addTrade(trades, nextTrade);
        }
    }

    // MODIFIES: trades
    // EFFECTS: Parses individual trade from JSON object and adds them to trades
    private static void addTrade(Trades trades, JSONObject jsonObject)
            throws SameNameException, EmptyStringException {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        Trade trade = new Trade();
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(trade, nextTeam);
        }
        trades.addTrade(trade);
    }

    // MODIFIES: trade
    // EFFECTS: Parses teams from JSON object and adds them to trades
    private static void addTeam(Trade trade, JSONObject jsonObject)
            throws SameNameException, EmptyStringException {
        String name = jsonObject.getString("name");
        Team team = new Team(name);
        trade.addTeam(team);
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    // MODIFIES: team
    // Parses players from JSON object and adds them to trades
    private static void addPlayer(Team team, JSONObject jsonObject)
            throws SameNameException, EmptyStringException {
        String name = jsonObject.getString("name");
        String position = jsonObject.getString("position");
        Double salary = jsonObject.getDouble("salary");
        Player player = new Player(name, position, salary);
        team.addPlayer(player);
    }
}