package ui;

import exceptions.CannotFindNameException;
import exceptions.EmptyStringException;
import exceptions.NegativeNumberException;
import exceptions.SameNameException;
import model.Player;
import model.Team;
import model.Trade;
import model.Trades;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

// Represents a trade application that users can interact with to create their own trades
public class TradeApp {

    // FIELDS
    private Scanner scanner;
    private Trades trades;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_TRADES = "./data/trades.json";

    // EFFECTS: Starts the trade application
    public TradeApp() {
        trades = new Trades();
        jsonReader = new JsonReader(JSON_TRADES);
        jsonWriter = new JsonWriter(JSON_TRADES);
        startTradeApp();
    }

    // EFFECTS: Brings user to opening screen
    private void startTradeApp() {
        scanner = new Scanner(System.in);
        viewAllTrades();
        instructionList();
        String instruction = scanner.nextLine().toLowerCase();
        processInstruction(instruction);
    }

    // EFFECTS: Processes the instruction given and allows user to interact with their created trade
    private void processInstruction(String instruction) {
        Boolean hasTrades = trades.getTrades().size() > 0;

        if (instruction.equals("c")) {
            createTrade();
        } else if (instruction.equals("q")) {
            quitTradeApp();
        } else if (instruction.equals("l")) {
            loadTrades();
        } else if (instruction.equals("r") && hasTrades) {
            removeExistingTrade();
        } else if (instruction.equals("v") && hasTrades) {
            viewTrade();
        } else if (instruction.equals("p") && hasTrades) {
            filterByPlayer();
        } else if (instruction.equals("t") && hasTrades) {
            filterByTeam();
        } else {
            System.out.println("Instruction cannot be processed.");
            startTradeApp();
        }
    }

    // EFFECTS: Quits the trade application and allows user to save their made trades
    private void quitTradeApp() {
        scanner = new Scanner(System.in);
        System.out.println("Would you like to save your trades?");
        System.out.println("y: Save my trades.");
        System.out.println("n: Do not save my trades.");
        String instruction = scanner.next().toLowerCase();
        if (instruction.equals("y")) {
            saveTrades();
            System.out.println("Your trades have been saved.");
        } else {
            System.out.println("Your trades were not saved.");
        }
    }

    // EFFECTS: Allows the user to save their previously made trades
    private void saveTrades() {
        try {
            jsonWriter.open();
            jsonWriter.write(trades);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not save trades.");
            startTradeApp();
        }
    }

    // EFFECTS: Allows the user to load their previously made trades
    private void loadTrades() {
        try {
            trades = JsonReader.read();
            startTradeApp();
        } catch (IOException | SameNameException | EmptyStringException e) {
            System.out.println("Could not load trades.");
            startTradeApp();
        }
    }

    // EFFECTS: Shows all the current trades made and numbers them
    private void viewAllTrades() {
        System.out.println("Current trades:");
        int num = 1;

        for (Trade trade : trades.getTrades()) {
            System.out.println("Trade " + num);
            num++;
        }
    }

    // EFFECTS: Produces the possible instructions for the user
    private void instructionList() {
        System.out.println("Type in a letter to continue.");
        System.out.println("c: Create a new trade.");
        System.out.println("r: Remove an existing trade.");
        System.out.println("v: View a specific trade.");
        System.out.println("p: Filter trades by player.");
        System.out.println("t: Filter trades by team.");
        System.out.println("l: Load your saved trades.");
        System.out.println("q: Quit the trade application.");
    }

    // MODIFIES: this
    // EFFECTS: Creates a new trade
    private void createTrade() {
        scanner = new Scanner(System.in);
        Trade trade = new Trade();
        trades.addTrade(trade);

        System.out.println("e: Edit this trade.");
        System.out.println("Or type any letter to go back to trade commands.");
        String instruction = scanner.nextLine().toLowerCase();

        if (instruction.equals("e")) {
            editTrade(trade);
        } else {
            startTradeApp();
        }
    }

    // MODIFIES: this, trade
    // EFFECTS: Edits a previously made trade
    private void editTrade(Trade trade) {
        viewAllTeams(trade);

        scanner = new Scanner(System.in);
        System.out.println("Type in a letter to continue.");
        System.out.println("a: Add a new team to the trade.");
        System.out.println("r: Remove a team from the trade.");
        System.out.println("v: View an existing team in the trade.");
        System.out.println("e: Edit an existing team name in the trade.");
        System.out.println("b: Go back to editing trades.");
        String instruction = scanner.nextLine().toLowerCase();

        if (instruction.equals("a")) {
            addTeamToTrade(trade);
        } else if (instruction.equals("r")) {
            removeTeamFromTrade(trade);
        } else if (instruction.equals("v")) {
            viewTeam(trade);
        } else if (instruction.equals("e")) {
            editTeamNameInTrade(trade);
        } else if (instruction.equals("b")) {
            startTradeApp();
        } else {
            System.out.println("Instruction cannot be processed.");
            editTrade(trade);
        }
    }

    // EFFECTS: Prints out all the teams in a specific trade
    private void viewAllTeams(Trade trade) {
        for (String teamName : trade.getTeamNames()) {
            System.out.println(teamName);
        }
    }

    // MODIFIES: this, trade
    // EFFECTS: Adds a team to a specific trade
    private void addTeamToTrade(Trade trade) {
        scanner = new Scanner(System.in);
        System.out.println("Add a team to the trade.");
        String name = scanner.nextLine().toLowerCase();
        Team team = new Team(name);

        try {
            trade.addTeam(team);
            System.out.println("e: Edit this team.");
            System.out.println("Or type any letter to go back to team commands.");
            String instruction = scanner.nextLine().toLowerCase();

            if (instruction.equals("e")) {
                editTeam(trade, team);
            } else {
                editTrade(trade);
            }
        } catch (SameNameException e) {
            System.out.println("Team already exists in the trade.");
            editTrade(trade);
        } catch (EmptyStringException e) {
            System.out.println("Team name cannot be empty.");
            editTrade(trade);
        }
    }

    // MODIFIES: this, trade
    // EFFECTS: Removes a team from a specific trade
    private void removeTeamFromTrade(Trade trade) {
        scanner = new Scanner(System.in);
        System.out.println("Remove a team from the trade.");
        String name = scanner.nextLine().toLowerCase();
        trade.removeTeam(name);
        editTrade(trade);
    }

    // EFFECTS: Allows user to see inside a specific team
    private void viewTeam(Trade trade) {
        scanner = new Scanner(System.in);
        viewAllTeams(trade);
        System.out.println("Provide the name of the team you want to view.");
        String name = scanner.nextLine().toLowerCase();

        Team team = trade.findTeam(name);
        for (String playerName : team.getPlayerNames()) {
            System.out.println(playerName);
        }
        System.out.println("e: Edit this team.");
        System.out.println("Or type any letter to go back to team commands.");
        String instruction = scanner.nextLine().toLowerCase();
        if (instruction.equals("e")) {
            editTeam(trade, team);
        } else {
            editTrade(trade);
        }
    }

    // MODIFIES: this, trade
    // EFFECTS: Edits the name of a team in a trade
    private void editTeamNameInTrade(Trade trade) {
        scanner = new Scanner(System.in);
        System.out.println("Provide the name of the team you want to edit.");
        String currentName = scanner.nextLine().toLowerCase();

        scanner = new Scanner(System.in);
        System.out.println("Provide the name you want to change to.");
        String newName = scanner.nextLine().toLowerCase();

        try {
            trade.editTeamName(currentName, newName);
            editTrade(trade);
        } catch (SameNameException e) {
            System.out.println("Team already exists in the trade.");
            editTrade(trade);
        } catch (EmptyStringException e) {
            System.out.println("New name cannot be empty.");
            editTrade(trade);
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Allows user to edit a specific part of a team
    private void editTeam(Trade trade, Team team) {
        scanner = new Scanner(System.in);
        viewAllPlayers(team);

        System.out.println("Type in a letter to continue.");
        System.out.println("a: Add a new player to the team.");
        System.out.println("r: Remove a player from the team.");
        System.out.println("v: View an existing player in the trade.");
        System.out.println("b: Go back to editing teams.");
        String instruction = scanner.nextLine().toLowerCase();

        if (instruction.equals("a")) {
            addPlayerToTeam(trade, team);
        } else if (instruction.equals("r")) {
            removePlayerFromTeam(trade, team);
        } else if (instruction.equals("v")) {
            viewPlayer(trade, team);
        } else if (instruction.equals("b")) {
            editTrade(trade);
        } else {
            System.out.println("Instruction cannot be processed.");
            editTeam(trade, team);
        }
    }

    // EFFECTS: Allows user to see all the players within a specific team
    private void viewAllPlayers(Team team) {
        for (String playerName : team.getPlayerNames()) {
            System.out.println(playerName);
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Adds a player to a specific team
    private void addPlayerToTeam(Trade trade, Team team) {
        scanner = new Scanner(System.in);
        System.out.println("Provide the player's name.");
        String name = scanner.nextLine().toLowerCase();

        scanner = new Scanner(System.in);
        System.out.println("Provide the player's position.");
        String position = scanner.nextLine().toLowerCase();

        scanner = new Scanner(System.in);
        System.out.println("Provide the player's salary.");
        Double salary = scanner.nextDouble();

        if (salary < 0) {
            System.out.println("Salary cannot be negative number.");
            editTeam(trade, team);
        }
        Player player = new Player(name, position, salary);
        addPlayer(team, player);
        editTeam(trade, team);
    }

    // MODIFIES: this, team
    // EFFECTS: Adds the given player to the given team
    private void addPlayer(Team team, Player player) {
        try {
            team.addPlayer(player);
        } catch (SameNameException e) {
            System.out.println("Player already exists in the trade.");
        } catch (EmptyStringException e) {
            System.out.println("Player name and/or position cannot be empty.");
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Removes player from a specific team
    private void removePlayerFromTeam(Trade trade, Team team) {
        scanner = new Scanner(System.in);
        System.out.println("Remove a player from the team.");
        String name = scanner.nextLine().toLowerCase();
        team.removePlayer(name);
        editTeam(trade, team);
    }

    // EFFECTS: Allows user to see the details of a player
    private void viewPlayer(Trade trade, Team team) {
        viewAllPlayers(team);

        scanner = new Scanner(System.in);
        System.out.println("Provide the name of the player you want to view.");
        String name = scanner.nextLine().toLowerCase();
        Player player = team.findPlayer(name);
        System.out.println("Name: " + player.getName());
        System.out.println("Position: " + player.getPosition());
        System.out.println("Salary: " + player.getSalary());
        System.out.println("e: Edit this player.");
        System.out.println("Or type any letter to go back to player commands.");
        String instruction = scanner.nextLine().toLowerCase();

        if (instruction.equals("e")) {
            editPlayer(trade, team, name);
        } else {
            editTeam(trade, team);
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Allows user to edit a detail of a player
    private void editPlayer(Trade trade, Team team, String name) {
        System.out.println("Type in a letter to continue.");
        System.out.println("n: Change the name of a player.");
        System.out.println("p: Change the position of a player.");
        System.out.println("s: Change the salary of a player.");

        String instruction = scanner.nextLine().toLowerCase();

        if (instruction.equals("n")) {
            changePlayerName(trade, team, name);
        } else if (instruction.equals("p")) {
            changePlayerPosition(trade, team, name);
        } else if (instruction.equals("s")) {
            changePlayerSalary(trade, team, name);
        } else {
            System.out.println("Instruction cannot be processed.");
            editPlayer(trade, team, name);
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Changes the player's name to given name
    private void changePlayerName(Trade trade, Team team, String currentName) {
        scanner = new Scanner(System.in);
        System.out.println("Provide the name you want to change to.");
        String newName = scanner.nextLine().toLowerCase();

        try {
            team.editPlayerName(currentName, newName);
            editTeam(trade, team);
        } catch (SameNameException e) {
            System.out.println("Player already exists on the team.");
            editTeam(trade, team);
        } catch (EmptyStringException e) {
            System.out.println("New name cannot be empty.");
            editTeam(trade, team);
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Changes a player's position to given position
    private void changePlayerPosition(Trade trade, Team team, String name) {
        scanner = new Scanner(System.in);
        System.out.println("Provide the new position you want to change to.");
        String position = scanner.nextLine().toLowerCase();

        try {
            team.editPlayerPosition(name, position);
            editTeam(trade, team);
        } catch (CannotFindNameException e) {
            System.out.println("Cannot find player on the team.");
            editTeam(trade, team);
        } catch (EmptyStringException e) {
            System.out.println("New position cannot be empty.");
            editTeam(trade, team);
        }
    }

    // MODIFIES: this, trade, team
    // EFFECTS: Changes a player's salary to given salary
    private void changePlayerSalary(Trade trade, Team team, String name) {
        scanner = new Scanner(System.in);
        System.out.println("Provide the new salary you want to change to.");
        Double salary = scanner.nextDouble();

        try {
            team.editPlayerSalary(name, salary);
            editTeam(trade, team);
        } catch (CannotFindNameException e) {
            System.out.println("Cannot find player on the team.");
            editTeam(trade, team);
        } catch (NegativeNumberException e) {
            System.out.println("Salary cannot be a negative number.");
            editTeam(trade, team);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes a specific trade
    private void removeExistingTrade() {
        scanner = new Scanner(System.in);
        System.out.println("Type the number of the trade you want to remove.");
        Integer num = scanner.nextInt();

        try {
            trades.removeTrade(num);
            startTradeApp();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unable to find Trade " + num);
            startTradeApp();
        }
    }

    // EFFECTS: Allows user to view the teams involved in a trade
    private void viewTrade() {
        scanner = new Scanner(System.in);
        viewAllTrades();
        System.out.println("Type the number of the trade you want to look at.");
        Integer num = scanner.nextInt();

        try {
            Trade trade = trades.findTrade(num);
            for (String name : trade.getTeamNames()) {
                System.out.println(name);
            }
            System.out.println("e: Edit this trade.");
            System.out.println("Or type any letter to go back to trade commands.");

            scanner = new Scanner(System.in);
            String instruction = scanner.nextLine().toLowerCase();
            if (instruction.equals("e")) {
                editTrade(trade);
            } else {
                startTradeApp();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unable to find Trade " + num);
            startTradeApp();
        }
    }

    // EFFECTS: Allows user to filter the trades by players in the trades
    private void filterByPlayer() {
        scanner = new Scanner(System.in);
        System.out.println("Type the name of the player you want to find in trades.");
        String name = scanner.nextLine();

        List<Integer> listByPlayer = new ArrayList<>();
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            Collection<Team> teamList = trade.getTeams();
            for (Team team : teamList) {
                team.getPlayerNames();
                if (team.getPlayerNames().contains(name)) {
                    listByPlayer.add(num);
                }
            }
            num++;
        }
        System.out.println("Trades: " + listByPlayer);
        startTradeApp();
    }

    // EFFECTS: Allows user to filter the trades by players in the trades
    private void filterByTeam() {
        scanner = new Scanner(System.in);
        System.out.println("Type the name of the team you want to find in trades.");
        String name = scanner.nextLine();

        List<Integer> listByTeam = new ArrayList<>();
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            List<String> teamList = trade.getTeamNames();
            if (teamList.contains(name)) {
                listByTeam.add(num);
            }
            num++;
        }
        System.out.println("Trades: " + listByTeam);
        startTradeApp();
    }
}
