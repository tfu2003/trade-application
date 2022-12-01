package model;

import exceptions.EmptyStringException;
import exceptions.SameNameException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

// Represents a trade that contains teams involved in the trade
public class Trade {

    // FIELDS
    private HashMap<String, Team> teams;
    private Integer tradeNum;

    // EFFECTS: Constructs a trade with no teams in the hashmap
    public Trade() {
        teams = new HashMap<>();
    }

    // EFFECTS: Gets a collection of teams in the trade
    public Collection<Team> getTeams() {
        return teams.values();
    }

    // EFFECTS: Gets a list of team names in the trade
    public List<String> getTeamNames() {
        return new ArrayList<>(teams.keySet());
    }

    public Integer getTradeNumber() {
        return tradeNum;
    }

    public void setTradeNumber(Integer num) {
        tradeNum = num;
    }

    // MODIFIES: this
    // EFFECTS: Adds team to the trade
    public void addTeam(Team team) throws SameNameException, EmptyStringException {
        if (teams.containsKey(team.getName())) {
            throw new SameNameException();
        } else if (team.getName().isEmpty()) {
            throw new EmptyStringException();
        } else {
            teams.put(team.getName(), team);
            EventLog.getInstance().logEvent(new Event("Added Team: " + team.getName() + " to Trade "
                    + tradeNum));
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes team from the trade
    public void removeTeam(String name) {
        teams.remove(name);
        EventLog.getInstance().logEvent(new Event("Removed Team: " + name + " from Trade " + tradeNum));
    }

    // MODIFIES: this
    // EFFECTS: Edits a given team's name to one that is not already in the list of teams of a trade
    public void editTeamName(String currentName, String newName) throws
            SameNameException, EmptyStringException {
        if (teams.containsKey(newName)) {
            throw new SameNameException();
        } else if (newName.isEmpty()) {
            throw new EmptyStringException();
        } else {
            Team team = teams.get(currentName);
            teams.remove(currentName);
            team.setName(newName);
            teams.put(newName, team);
        }
    }

    // EFFECTS: Finds a team in the trade
    public Team findTeam(String name) {
        return teams.get(name);
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teams", teamsToJson());
        return jsonObject;
    }

    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Team team : teams.values()) {
            jsonArray.put(team.getJson());
        }
        return jsonArray;
    }
}