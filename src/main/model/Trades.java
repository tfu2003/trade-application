package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a list of created trades
public class Trades {

    // FIELDS
    private List<Trade> trades;

    // EFFECTS: Constructs a list of trades that is empty
    public Trades() {
        trades = new ArrayList<>();
    }

    // EFFECTS: Gets the list of trades
    public List<Trade> getTrades() {
        return trades;
    }

    // MODIFIES: this
    // EFFECTS: Adds a trade to the list of trades
    public void addTrade(Trade trade) {
        trades.add(trade);
        trade.setTradeNumber(trades.size());
        EventLog.getInstance().logEvent(new Event("Added Trade " + trades.size()));
    }

    // MODIFIES: this
    // EFFECTS: Removes a trade from a list of trades
    public void removeTrade(int i) throws IndexOutOfBoundsException {
        if (i <= -1 || i > trades.size()) {
            throw new IndexOutOfBoundsException();
        }
        Trade trade = trades.get(i);
        trades.remove(trade);
        for (Trade t : trades) {
            if (trade.getTradeNumber() < t.getTradeNumber()) {
                t.setTradeNumber(t.getTradeNumber() - 1);
            }
        }
        EventLog.getInstance().logEvent(new Event("Removed Trade " + (i + 1)));
    }

    // EFFECTS: Returns a trade if found given a number
    public Trade findTrade(int i) {
        Trade trade = trades.get(i);
        return trade;
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("trades", tradesToJson());
        return jsonObject;
    }

    private JSONArray tradesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Trade trade : trades) {
            jsonArray.put(trade.getJson());
        }
        return jsonArray;
    }
}
