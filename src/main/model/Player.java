package model;

import exceptions.EmptyStringException;
import exceptions.NegativeNumberException;
import org.json.JSONObject;
import persistence.JsonReader;

// Player represents a player that has a name, a position, and a salary.
public class Player {

    // FIELDS:
    private String name;
    private String position;
    private double salary;

    // EFFECTS: Constructs a player with the given name, position, and salary
    public Player(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // EFFECTS: Gets the name of player
    public String getName() {
        return name;
    }

    // EFFECTS: Gets the position of player
    public String getPosition() {
        return position;
    }

    // EFFECTS: Gets the salary of player
    public double getSalary() {
        return salary;
    }

    // MODIFIES: this
    // EFFECTS: Sets the name of player
    public void setName(String name) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException();
        }
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: Sets the position of player
    public void setPosition(String position) throws EmptyStringException {
        if (position.isEmpty()) {
            throw new EmptyStringException();
        }
        this.position = position;
    }

    // MODIFIES: this
    // EFFECTS: Sets the salary of player
    public void setSalary(double salary) throws NegativeNumberException {
        if (salary < 0) {
            throw new NegativeNumberException();
        }
        this.salary = salary;
    }

    // EFFECTS: Puts player details into JSON and returns it as an object
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("position", position);
        jsonObject.put("salary", salary);
        return jsonObject;
    }
}
