package model;

import exceptions.CannotFindNameException;
import exceptions.EmptyStringException;
import exceptions.NegativeNumberException;
import exceptions.SameNameException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

// Represents a team that contains players that are on the team
public class Team {

    // FIELDS
    private String name;
    private HashMap<String, Player> players;

    // EFFECTS: Constructs a team with the given name and no players in the hashmap
    public Team(String name) {
        this.name = name;
        players = new HashMap<>();
    }

    // EFFECTS: Gets the name of team
    public String getName() {
        return name;
    }

    // EFFECTS: Gets a collection of players in the team
    public Collection<Player> getPlayers() {
        return players.values();
    }

    // EFFECTS: Gets a list of player names in the team
    public List<String> getPlayerNames() {
        return new ArrayList<>(players.keySet());
    }

    // EFFECTS: Sets the name of team
    public void setName(String name) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException();
        }
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: Adds a given player to the team
    public void addPlayer(Player player) throws SameNameException, EmptyStringException {
        if (players.containsKey(player.getName())) {
            throw new SameNameException();
        } else if (player.getName().isEmpty()) {
            throw new EmptyStringException();
        } else if (player.getPosition().isEmpty()) {
            throw new EmptyStringException();
        } else {
            players.put(player.getName(), player);
            EventLog.getInstance().logEvent(new Event("Added Player: " + player.getName() + " to Team: "
                    + getName()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes a given player from the team if found in the team
    public void removePlayer(String name) {
        players.remove(name);
        EventLog.getInstance().logEvent(new Event("Removed Player: " + name + " from Team: "
                + getName()));
    }

    // EFFECTS: Finds the player in the team and returns the player if found
    public Player findPlayer(String name) {
        return players.get(name);
    }

    // MODIFIES: this
    // EFFECTS: Edits a given player's name to one that is not already in the list of players of a team
    public void editPlayerName(String currentName, String newName) throws
            SameNameException, EmptyStringException {
        if (players.containsKey(newName)) {
            throw new SameNameException();
        } else if (newName.isEmpty()) {
            throw new EmptyStringException();
        } else {
            Player player = players.get(currentName);
            players.remove(currentName);
            player.setName(newName);
            players.put(newName, player);
        }
    }

    // MODIFIES: this
    // EFFECTS: Edits a given player's position to a new given position
    public void editPlayerPosition(String name, String position) throws CannotFindNameException, EmptyStringException {
        if (!players.containsKey(name)) {
            throw new CannotFindNameException();
        } else if (position.isEmpty()) {
            throw new EmptyStringException();
        } else {
            Player player = players.get(name);
            player.setPosition(position);
        }
    }

    // MODIFIES: this
    // EFFECTS: Edits a given player's salary to a new given salary
    public void editPlayerSalary(String name, double salary) throws CannotFindNameException, NegativeNumberException {
        if (!players.containsKey(name)) {
            throw new CannotFindNameException();
        } else if (salary < 0) {
            throw new NegativeNumberException();
        } else {
            Player player = players.get(name);
            player.setSalary(salary);
        }
    }

    // EFFECTS: Puts team details into JSON and returns it as an object
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("players", playersToJson());
        return jsonObject;
    }

    // EFFECTS: Returns players in the team as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player player : players.values()) {
            jsonArray.put(player.getJson());
        }
        return jsonArray;
    }
}

