package ui;

import exceptions.EmptyStringException;
import exceptions.NegativeNumberException;
import exceptions.SameNameException;
import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.awt.Frame.MAXIMIZED_BOTH;

// Represents a trade application which displays a GUI for the user
public class TradeAppGui extends JPanel implements ListSelectionListener {

    // FIELDS
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Trades trades;
    private JList createdTrades;
    private JList createdTradesByPlayer;
    private JList createdTradesByTeam;
    private JList createdTeams;
    private JList createdPlayers;
    private JList createdPlayer;
    private JFrame jframe;
    private JPanel tradesPanel;
    private JPanel tradesByPlayerPanel;
    private JPanel tradesByTeamPanel;
    private JPanel tradePanel;
    private JPanel teamPanel;
    private JPanel playerPanel;
    private DefaultListModel createdTradesModel;
    private DefaultListModel createdTradesByPlayerModel;
    private DefaultListModel createdTradesByTeamModel;
    private DefaultListModel createdTeamsModel;
    private DefaultListModel createdPlayersModel;
    private DefaultListModel createdPlayerModel;
    private static final String JSON_TRADES = "./data/trades.json";
    private static final ImageIcon smileIcon = new ImageIcon(new ImageIcon("./data/smile.png")
            .getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
    private static final ImageIcon sadIcon = new ImageIcon(new ImageIcon("./data/sad.png")
            .getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));

    // MODIFIES: this
    // EFFECTS: Starts the GUI for the trade application
    public TradeAppGui() {
        super(new BorderLayout());
        trades = new Trades();
        jsonReader = new JsonReader(JSON_TRADES);
        jsonWriter = new JsonWriter(JSON_TRADES);

        jframe = new JFrame("Mock Trade Application");
        tradesPanel = new JPanel();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setExtendedState(MAXIMIZED_BOTH);

        tradesPanel.setLayout(new BoxLayout(tradesPanel, BoxLayout.LINE_AXIS));
        tradesPanel.add(showCreatedTradesPanel());
        tradesPanel.add(createTradesButtonPanel());
        jframe.add(tradesPanel);
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Creates the trades button panel
    private JPanel createTradesButtonPanel() {
        JPanel tradesButtonPanel = new JPanel();
        tradesButtonPanel.setLayout(new GridLayout(0, 1));
        tradesButtonPanel.setPreferredSize(new Dimension(100, 200));
        createTradeButton(tradesButtonPanel, "Create a new trade");
        removeTradeButton(tradesButtonPanel, "Remove a previously made trade");
        viewTradeButton(tradesButtonPanel, "View a previously made trade");
        filterByPlayerButton(tradesButtonPanel, "Filter trades by player");
        filterByTeamsButton(tradesButtonPanel, "Filter trades by team");
        saveTradesButton(tradesButtonPanel, "Save your trades");
        loadTradesButton(tradesButtonPanel, "Load previously made trades");
        quitButton(tradesButtonPanel, "Quit the trade application");
        return tradesButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a create trade button for the trades panel
    private void createTradeButton(JPanel tradesButtonPanel, String s) {
        JButton createTradeButton = new JButton(s);
        createTradeButton.setOpaque(true);
        createTradeButton.setBackground(Color.green);
        createTradeButton.setBorder(new LineBorder(Color.black));
        createTradeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createTradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trade trade = new Trade();
                trades.addTrade(trade);
                createdTradesModel.addElement("Trade " + trades.getTrades().size());
                JOptionPane.showMessageDialog(null,
                        "Trade was successfully added", "Success", 1, smileIcon);
            }
        });
        tradesButtonPanel.add(createTradeButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a load trade button for the trades panel
    private void loadTradesButton(JPanel tradesButtonPanel, String s) {
        JButton loadButton = new JButton(s);
        loadButton.setOpaque(true);
        loadButton.setBackground(Color.blue);
        loadButton.setBorder(new LineBorder(Color.black));
        loadButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    trades = JsonReader.read();
                } catch (IOException | SameNameException | EmptyStringException exception) {
                    System.out.println("Could not load trades.");
                }
                loadTrades();
                jframe.setExtendedState(MAXIMIZED_BOTH);
            }

        });
        tradesButtonPanel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: Loads the json file and overrides the collection
    private void loadTrades() {
        tradesPanel.removeAll();
        tradesPanel.add(showCreatedTradesPanel());
        tradesPanel.add(createTradesButtonPanel());
        jframe.pack();
        tradesPanel.setVisible(true);
        if (tradePanel != null) {
            tradePanel.setVisible(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a quit button for the trades panel
    private void quitButton(JPanel tradesButtonPanel, String s) {
        JButton quitButton = new JButton(s);
        quitButton.setOpaque(true);
        quitButton.setBackground(Color.red);
        quitButton.setBorder(new LineBorder(Color.black));
        quitButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }

        });
        tradesButtonPanel.add(quitButton);
    }

    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a view trade button for the trades panel
    private void viewTradeButton(JPanel tradesButtonPanel, String s) {
        JButton viewTradeButton = new JButton(s);
        viewTradeButton.setOpaque(true);
        viewTradeButton.setBackground(Color.orange);
        viewTradeButton.setBorder(new LineBorder(Color.black));
        viewTradeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        viewTradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trades.getTrades().size() > 0) {
                    Integer tradeNumber = createdTrades.getSelectedIndex();
                    if (tradeNumber != -1) {
                        Trade trade = trades.findTrade(tradeNumber);
                        tradePanel(trade);
                    }
                }
            }
        });
        tradesButtonPanel.add(viewTradeButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a view trade that are filtered by player button for the trades by player panel
    private void viewTradesFilteredByPlayerButton(JPanel tradesByPlayerButtonPanel, String name, String s) {
        JButton viewTradesFilteredByPlayerButton = new JButton(s);
        viewTradesFilteredByPlayerButton.setOpaque(true);
        viewTradesFilteredByPlayerButton.setBackground(Color.orange);
        viewTradesFilteredByPlayerButton.setBorder(new LineBorder(Color.black));
        viewTradesFilteredByPlayerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        Trades listByPlayer = new Trades();
        getTradesFilteredByPlayer(listByPlayer, name);
        viewTradesFilteredByPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listByPlayer.getTrades().size() > 0) {
                    Integer tradeNumber = createdTradesByPlayer.getSelectedIndex();
                    if (tradeNumber != -1) {
                        Trade trade = listByPlayer.findTrade(tradeNumber);
                        tradePanel(trade);
                    }
                }
            }
        });
        tradesByPlayerButtonPanel.add(viewTradesFilteredByPlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds trades filtered by player to the listByPlayer
    private void getTradesFilteredByPlayer(Trades listByPlayer, String name) {
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            Collection<Team> teamList = trade.getTeams();
            for (Team team : teamList) {
                team.getPlayerNames();
                if (team.getPlayerNames().contains(name)) {
                    listByPlayer.addTrade(trade);
                }
            }
            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a view trades that are filtered by team button for the trades by team panel
    private void viewTradesFilteredByTeamButton(JPanel tradesByTeamButtonPanel, String name, String s) {
        JButton viewTradesFilteredByTeamButton = new JButton(s);
        viewTradesFilteredByTeamButton.setOpaque(true);
        viewTradesFilteredByTeamButton.setBackground(Color.orange);
        viewTradesFilteredByTeamButton.setBorder(new LineBorder(Color.black));
        viewTradesFilteredByTeamButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        Trades listByTeam = new Trades();
        getTradesFilteredByTeam(listByTeam, name);
        viewTradesFilteredByTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listByTeam.getTrades().size() > 0) {
                    Integer tradeNumber = createdTradesByTeam.getSelectedIndex();
                    if (tradeNumber != -1) {
                        Trade trade = listByTeam.findTrade(tradeNumber);
                        tradePanel(trade);
                    }
                }
            }
        });
        tradesByTeamButtonPanel.add(viewTradesFilteredByTeamButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds trades filtered by team to the listByTeam
    private void getTradesFilteredByTeam(Trades listByTeam, String name) {
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            List<String> teamList = trade.getTeamNames();
            if (teamList.contains(name)) {
                listByTeam.addTrade(trade);
            }
            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a remove trade button for the trades panel
    private void removeTradeButton(JPanel tradesButtonPanel, String s) {
        JButton removeTradeButton = new JButton(s);
        removeTradeButton.setOpaque(true);
        removeTradeButton.setBackground(Color.cyan);
        removeTradeButton.setBorder(new LineBorder(Color.black));
        removeTradeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        removeTradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trades.getTrades().size() > 0 && createdTrades.getSelectedIndex() != -1) {
                    Integer tradeNumber = createdTrades.getSelectedIndex();
                        trades.removeTrade(tradeNumber);
                        createdTradesModel.remove(tradeNumber);
                        createdTradesModel.clear();
                        int num = 1;
                        for (Trade trade : trades.getTrades()) {
                            createdTradesModel.addElement("Trade " + num);
                            num++;
                        }
                }
            }
        });
        tradesButtonPanel.add(removeTradeButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by player button for the trades panel
    private void filterByPlayerButton(JPanel tradesButtonPanel, String s) {
        JButton filterByPlayerButton = new JButton(s);
        filterByPlayerButton.setOpaque(true);
        filterByPlayerButton.setBackground(Color.pink);
        filterByPlayerButton.setBorder(new LineBorder(Color.black));
        filterByPlayerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        filterByPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trades.getTrades().size() > 0) {
                    String name = JOptionPane
                            .showInputDialog("Input the name of the player you want to find in trades");
                    if (name != null) {
                        tradesByPlayerPanel(name);
                    }
                }
            }
        });
        tradesButtonPanel.add(filterByPlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades filtered by player panel and button panel
    private void tradesByPlayerPanel(String name) {
        if (tradesByPlayerPanel != null) {
            tradesByPlayerPanel.removeAll();
            tradesByPlayerPanel.add(showFilteredTradesByPlayerPanel(name));
            tradesByPlayerPanel.add(createFilteredTradesByPlayerButtonPanel(name));
            tradesByPlayerPanel.setVisible(true);
        } else {
            tradesByPlayerPanel = new JPanel();
            tradesByPlayerPanel.setLayout(new BoxLayout(tradesByPlayerPanel, BoxLayout.LINE_AXIS));
            tradesByPlayerPanel.add(showFilteredTradesByPlayerPanel(name));
            tradesByPlayerPanel.add(createFilteredTradesByPlayerButtonPanel(name));
            jframe.add(tradesByPlayerPanel);
        }
        jframe.pack();
        tradesPanel.setVisible(false);
        if (tradePanel != null) {
            tradePanel.setVisible(false);
        }
        jframe.setExtendedState(jframe.MAXIMIZED_BOTH);
        jframe.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());

    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by player button panel
    private JPanel showFilteredTradesByPlayerPanel(String name) {
        JPanel createdTradesByPlayerPanel = new JPanel();
        JLabel createdTradesByPlayerLabel = new JLabel("Trades with this player in them");
        createdTradesByPlayerLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createdTradesByPlayerPanel.setLayout(new BoxLayout(createdTradesByPlayerPanel, BoxLayout.PAGE_AXIS));
        createdTradesByPlayerModel = new DefaultListModel();
        createdTradesByPlayer = new JList(createdTradesByPlayerModel);
        showFilteredTradesByPlayer(name);
        createdTradesByPlayer.setSelectedIndex(0);
        createdTradesByPlayer.setVisibleRowCount(5);
        createdTradesByPlayer.setSelectionMode(0);
        createdTradesByPlayer.addListSelectionListener(this);
        JScrollPane tradesScrollPane = new JScrollPane(createdTradesByPlayer);
        tradesScrollPane.setPreferredSize(new Dimension(50, 100));
        createdTradesByPlayerPanel.add(createdTradesByPlayerLabel);
        createdTradesByPlayerPanel.add(tradesScrollPane);
        return createdTradesByPlayerPanel;
    }

    private void showFilteredTradesByPlayer(String name) {
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            Collection<Team> teamList = trade.getTeams();
            for (Team team : teamList) {
                team.getPlayerNames();
                if (team.getPlayerNames().contains(name)) {
                    createdTradesByPlayerModel.removeElement("Trade " + num);
                    createdTradesByPlayerModel.addElement("Trade " + num);
                }
            }
            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by player button panel
    private JPanel createFilteredTradesByPlayerButtonPanel(String name) {
        JPanel tradesByPlayerButtonPanel = new JPanel();
        tradesByPlayerButtonPanel.setLayout(new GridLayout(0, 1));
        tradesByPlayerButtonPanel.setPreferredSize(new Dimension(100, 200));
        viewTradesFilteredByPlayerButton(tradesByPlayerButtonPanel, name,"View a previously made trade");
        goBackToTradesButton(tradesByPlayerButtonPanel, "Go back to your trades");
        return tradesByPlayerButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by team button for the trades panel
    private void filterByTeamsButton(JPanel tradesButtonPanel, String s) {
        JButton filterByTeamsButton = new JButton(s);
        filterByTeamsButton.setOpaque(true);
        filterByTeamsButton.setBackground(Color.magenta);
        filterByTeamsButton.setBorder(new LineBorder(Color.black));
        filterByTeamsButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        filterByTeamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trades.getTrades().size() > 0) {
                    String name = JOptionPane
                            .showInputDialog("Input the name of the team you want to find in trades");
                    if (name != null) {
                        tradesByTeamPanel(name);
                    }
                }
            }
        });
        tradesButtonPanel.add(filterByTeamsButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by team panel and button panel
    private void tradesByTeamPanel(String name) {
        if (tradesByTeamPanel != null) {
            tradesByTeamPanel.removeAll();
            tradesByTeamPanel.add(showFilteredTradesByTeamPanel(name));
            tradesByTeamPanel.add(createFilteredTradesByTeamButtonPanel(name));
            tradesByTeamPanel.setVisible(true);
        } else {
            tradesByTeamPanel = new JPanel();
            tradesByTeamPanel.setLayout(new BoxLayout(tradesByTeamPanel, BoxLayout.LINE_AXIS));
            tradesByTeamPanel.add(showFilteredTradesByTeamPanel(name));
            tradesByTeamPanel.add(createFilteredTradesByTeamButtonPanel(name));
            jframe.add(tradesByTeamPanel);
        }
        jframe.pack();
        tradesPanel.setVisible(false);
        if (tradePanel != null) {
            tradePanel.setVisible(false);
        }
        jframe.setExtendedState(jframe.MAXIMIZED_BOTH);
        jframe.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by team panel
    private JPanel showFilteredTradesByTeamPanel(String name) {
        JPanel createdTradesByTeamPanel = new JPanel();
        JLabel createdTradesByTeamLabel = new JLabel("Trades with this team in them");
        createdTradesByTeamLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createdTradesByTeamPanel.setLayout(new BoxLayout(createdTradesByTeamPanel, BoxLayout.PAGE_AXIS));
        createdTradesByTeamModel = new DefaultListModel();
        createdTradesByTeam = new JList(createdTradesByTeamModel);
        showFilteredTradesByTeam(name);
        createdTradesByTeam.setSelectedIndex(0);
        createdTradesByTeam.setVisibleRowCount(5);
        createdTradesByTeam.setSelectionMode(0);
        createdTradesByTeam.addListSelectionListener(this);
        JScrollPane tradesScrollPane = new JScrollPane(createdTradesByTeam);
        tradesScrollPane.setPreferredSize(new Dimension(50, 100));
        createdTradesByTeamPanel.add(createdTradesByTeamLabel);
        createdTradesByTeamPanel.add(tradesScrollPane);
        return createdTradesByTeamPanel;
    }

    // MODIFIES: this
    // EFFECTS: Shows trades that are filtered by team
    private void showFilteredTradesByTeam(String name) {
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            List<String> teamList = trade.getTeamNames();
            if (teamList.contains(name)) {
                createdTradesByTeamModel.removeElement("Trade " + num);
                createdTradesByTeamModel.addElement("Trade " + num);
            }
            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a trades that are filtered by team button panel
    private JPanel createFilteredTradesByTeamButtonPanel(String name) {
        JPanel tradesByTeamButtonPanel = new JPanel();
        tradesByTeamButtonPanel.setLayout(new GridLayout(0, 1));
        tradesByTeamButtonPanel.setPreferredSize(new Dimension(100, 200));
        viewTradesFilteredByTeamButton(tradesByTeamButtonPanel, name,"View a previously made trade");
        goBackToTradesButton(tradesByTeamButtonPanel, "Go back to your trades");
        return tradesByTeamButtonPanel;

    }


    // MODIFIES: this
    // EFFECTS: Creates a save trades button for the trades panel
    private void saveTradesButton(JPanel tradesButtonPanel, String s) {
        JButton saveTradesButton = new JButton(s);
        saveTradesButton.setOpaque(true);
        saveTradesButton.setBackground(Color.yellow);
        saveTradesButton.setBorder(new LineBorder(Color.black));
        saveTradesButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        saveTradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(trades);
                    jsonWriter.close();
                } catch (FileNotFoundException exception) {
                    System.out.println("Could not save trades.");
                }
            }
        });
        tradesButtonPanel.add(saveTradesButton);

    }

    // MODIFIES: this
    // EFFECTS: Shows all the created trades in a list for the trades panel
    private JPanel showCreatedTradesPanel() {
        JPanel createdTradesPanel = new JPanel();
        JLabel createdTradesLabel = new JLabel("Your created trades");
        createdTradesLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createdTradesPanel.setLayout(new BoxLayout(createdTradesPanel, BoxLayout.PAGE_AXIS));

        createdTradesModel = new DefaultListModel();
        createdTrades = new JList(createdTradesModel);
        Integer num = 1;
        for (Trade trade : trades.getTrades()) {
            createdTradesModel.addElement("Trade " + num);
            num++;
        }
        createdTrades.setSelectedIndex(0);
        createdTrades.setVisibleRowCount(5);
        createdTrades.setSelectionMode(0);
        createdTrades.addListSelectionListener(this);
        JScrollPane tradesScrollPane = new JScrollPane(createdTrades);
        tradesScrollPane.setPreferredSize(new Dimension(50, 100));
        createdTradesPanel.add(createdTradesLabel);
        createdTradesPanel.add(tradesScrollPane);
        return createdTradesPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a trade panel
    public void tradePanel(Trade trade) {
        if (tradePanel != null) {
            tradePanel.removeAll();
            tradePanel.add(showTeamsPanel(trade));
            tradePanel.add(createTradeButtonPanel(trade));
            tradePanel.setVisible(true);
        } else {
            tradePanel = new JPanel();
            tradePanel.setLayout(new BoxLayout(tradePanel, BoxLayout.LINE_AXIS));
            tradePanel.add(showTeamsPanel(trade));
            tradePanel.add(createTradeButtonPanel(trade));
            jframe.add(tradePanel);
        }
        jframe.pack();
        tradesPanel.setVisible(false);
        if (tradesByPlayerPanel != null) {
            tradesByPlayerPanel.setVisible(false);
        }
        if (tradesByTeamPanel != null) {
            tradesByTeamPanel.setVisible(false);
        }
        jframe.setExtendedState(MAXIMIZED_BOTH);
    }

    // MODIFIES: this
    // EFFECTS: Shows all the created teams in a list for the trade panel
    private JPanel showTeamsPanel(Trade trade) {
        JPanel createdTeamsPanel = new JPanel();
        JLabel createdTeamsLabel = new JLabel("Your teams in this trade");
        createdTeamsLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createdTeamsPanel.setLayout(new BoxLayout(createdTeamsPanel, BoxLayout.PAGE_AXIS));

        createdTeamsModel = new DefaultListModel();
        for (Team team : trade.getTeams()) {
            createdTeamsModel.addElement(team.getName());
        }
        createdTeams = new JList(createdTeamsModel);
        createdTeams.setSelectedIndex(0);
        createdTeams.setVisibleRowCount(5);
        createdTeams.setSelectionMode(0);
        createdTeams.addListSelectionListener(this);
        JScrollPane tradesScrollPane = new JScrollPane(createdTeams);
        tradesScrollPane.setPreferredSize(new Dimension(50, 100));
        createdTeamsPanel.add(createdTeamsLabel);
        createdTeamsPanel.add(tradesScrollPane);
        return createdTeamsPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a trade button panel for the trade panel
    private JPanel createTradeButtonPanel(Trade trade) {
        JPanel tradeButtonPanel = new JPanel();
        tradeButtonPanel.setLayout(new GridLayout(0, 1));
        tradeButtonPanel.setPreferredSize(new Dimension(100, 200));
        createTeamButton(tradeButtonPanel, trade, "Add a new team");
        removeTeamButton(tradeButtonPanel, trade, "Remove a team");
        viewTeamButton(tradeButtonPanel, trade, "View an existing team");
        editTeamButton(tradeButtonPanel, trade, "Edit a team's name");
        goBackToTradesButton(tradeButtonPanel, "Go back to your trades");
        return tradeButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a create team button for the trade panel
    private void createTeamButton(JPanel tradeButtonPanel, Trade trade, String s) {
        JButton createTeamButton = new JButton(s);
        makeCreateTeamButton(createTeamButton);
        createTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Input the name of the team");
                try {
                    Team team = new Team(name);
                    trade.addTeam(team);
                    createdTeamsModel.addElement(team.getName());
                    JOptionPane.showMessageDialog(null,
                            "Team was successfully added", "Success", 1, smileIcon);
                } catch (SameNameException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Team name is already included", "Error", 1, sadIcon);
                } catch (EmptyStringException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Team name cannot be blank", "Error", 1, sadIcon);
                }
            }
        });
        tradeButtonPanel.add(createTeamButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a create team button
    private void makeCreateTeamButton(JButton createTeamButton) {
        createTeamButton.setOpaque(true);
        createTeamButton.setBackground(Color.green);
        createTeamButton.setBorder(new LineBorder(Color.black));
        createTeamButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
    }

    // MODIFIES: this
    // EFFECTS: Creates a remove team button for the trade panel
    private void removeTeamButton(JPanel tradeButtonPanel, Trade trade, String s) {
        JButton removeTeamButton = new JButton(s);
        removeTeamButton.setOpaque(true);
        removeTeamButton.setBackground(Color.cyan);
        removeTeamButton.setBorder(new LineBorder(Color.black));
        removeTeamButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        removeTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trade.getTeamNames().size() > 0 && createdTeams.getSelectedValue() != null) {
                    String name = createdTeams.getSelectedValue().toString();
                    Integer teamNum = createdTeams.getSelectedIndex();
                    trade.removeTeam(name);
                    createdTeamsModel.remove(teamNum);
                }
            }
        });
        tradeButtonPanel.add(removeTeamButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a view team button for the trade panel
    private void viewTeamButton(JPanel tradeButtonPanel, Trade trade, String s) {
        JButton viewTeamButton = new JButton(s);
        viewTeamButton.setOpaque(true);
        viewTeamButton.setBackground(Color.orange);
        viewTeamButton.setBorder(new LineBorder(Color.black));
        viewTeamButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        viewTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trade.getTeamNames().size() > 0 && createdTeams.getSelectedValue() != null) {
                    String name = createdTeams.getSelectedValue().toString();
                    Team team = trade.findTeam(name);
                    teamPanel(team);
                }
            }
        });
        tradeButtonPanel.add(viewTeamButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a edit team name button for the trade panel
    private void editTeamButton(JPanel tradeButtonPanel, Trade trade, String s) {
        JButton editTeamButton = new JButton(s);
        makeEditTeamButton(editTeamButton);
        editTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trade.getTeamNames().size() > 0 && createdTeams.getSelectedValue() != null) {
                    String currentName = createdTeams.getSelectedValue().toString();
                    String newName = JOptionPane.showInputDialog("Input the name of the team");
                    try {
                        trade.editTeamName(currentName, newName);
                        Integer teamNum = createdTeams.getSelectedIndex();
                        createdTeamsModel.remove(teamNum);
                        createdTeamsModel.addElement(newName);
                    } catch (SameNameException ex) {
                        showSameTeamNamePanel();
                    } catch (EmptyStringException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Team name cannot be blank", "Error", 1, sadIcon);
                    }
                }
            }
        });
        tradeButtonPanel.add(editTeamButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a edit team name button
    private void makeEditTeamButton(JButton editTeamButton) {
        editTeamButton.setOpaque(true);
        editTeamButton.setBackground(Color.yellow);
        editTeamButton.setBorder(new LineBorder(Color.black));
        editTeamButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
    }

    // MODIFIES: this
    // EFFECTS: Creates a new panel for when the same team name is inputted
    private void showSameTeamNamePanel() {
        JOptionPane.showMessageDialog(null,
                "Team is already included", "Error", 1, sadIcon);
    }

    // MODIFIES: this
    // EFFECTS: Creates a go back to trades button for the trade panel
    private void goBackToTradesButton(JPanel tradeButtonPanel, String s) {
        JButton goBackToTradesButton = new JButton(s);
        goBackToTradesButton.setOpaque(true);
        goBackToTradesButton.setBackground(Color.red);
        goBackToTradesButton.setBorder(new LineBorder(Color.black));
        goBackToTradesButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        goBackToTradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tradePanel != null) {
                    tradePanel.setVisible(false);
                }
                if (tradesByPlayerPanel != null) {
                    tradesByPlayerPanel.setVisible(false);
                }
                if (tradesByTeamPanel != null) {
                    tradesByTeamPanel.setVisible(false);
                }
                tradesPanel.setVisible(true);
            }
        });
        tradeButtonPanel.add(goBackToTradesButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a team panel
    private void teamPanel(Team team) {
        if (teamPanel != null) {
            teamPanel.removeAll();
            teamPanel.add(showPlayersPanel(team));
            teamPanel.add(createTeamButtonPanel(team));
            teamPanel.setVisible(true);
        } else {
            teamPanel = new JPanel();
            teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.LINE_AXIS));
            teamPanel.add(showPlayersPanel(team));
            teamPanel.add(createTeamButtonPanel(team));
            jframe.add(teamPanel);
        }
        jframe.pack();
        tradesPanel.setVisible(false);
        tradePanel.setVisible(false);
        if (tradesByPlayerPanel != null) {
            tradesByPlayerPanel.setVisible(false);
        }
        if (tradesByTeamPanel != null) {
            tradesByTeamPanel.setVisible(false);
        }
        jframe.setExtendedState(MAXIMIZED_BOTH);
    }

    // MODIFIES: this
    // EFFECTS: Shows all the created players in a list for the team panel
    private JPanel showPlayersPanel(Team team) {
        JPanel createdPlayersPanel = new JPanel();
        JLabel createdPlayersLabel = new JLabel("Your players in this team");
        createdPlayersLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createdPlayersPanel.setLayout(new BoxLayout(createdPlayersPanel, BoxLayout.PAGE_AXIS));

        createdPlayersModel = new DefaultListModel();
        for (Player player : team.getPlayers()) {
            createdPlayersModel.addElement(player.getName());
        }
        createdPlayers = new JList(createdPlayersModel);
        createdPlayers.setSelectedIndex(0);
        createdPlayers.setVisibleRowCount(5);
        createdPlayers.setSelectionMode(0);
        createdPlayers.addListSelectionListener(this);
        JScrollPane tradesScrollPane = new JScrollPane(createdPlayers);
        tradesScrollPane.setPreferredSize(new Dimension(50, 100));
        createdPlayersPanel.add(createdPlayersLabel);
        createdPlayersPanel.add(tradesScrollPane);
        return createdPlayersPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a team button panel
    private JPanel createTeamButtonPanel(Team team) {
        JPanel teamButtonPanel = new JPanel();
        teamButtonPanel.setLayout(new GridLayout(0, 1));
        teamButtonPanel.setPreferredSize(new Dimension(100, 200));
        createPlayerButton(teamButtonPanel, team, "Add a new player");
        removePlayerButton(teamButtonPanel, team, "Remove a player");
        viewPlayerButton(teamButtonPanel, team, "View an existing player");
        goBackToTeamsButton(teamButtonPanel, "Go back to editing teams");
        return teamButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a create player button for the team panel
    private void createPlayerButton(JPanel teamButtonPanel, Team team, String s) {
        JButton createPlayerButton = new JButton(s);
        makeCreatePlayerButton(createPlayerButton);
        createPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Input the name of the player");
                String position = JOptionPane.showInputDialog(("Input the position of the player"));
                Double salary = Double.parseDouble(JOptionPane.showInputDialog(("Input the salary of the player")));
                try {
                    Player player = new Player(name, position, salary);
                    team.addPlayer(player);
                    createdPlayersModel.addElement(player.getName());
                    JOptionPane.showMessageDialog(null,
                            "Player was successfully added", "Success", 1, smileIcon);
                } catch (SameNameException ex) {
                    showSamePlayerNamePanel();
                } catch (EmptyStringException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Player name or position cannot be blank", "Error", 1, sadIcon);
                }
            }
        });
        teamButtonPanel.add(createPlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a create player button
    private void makeCreatePlayerButton(JButton createPlayerButton) {
        createPlayerButton.setOpaque(true);
        createPlayerButton.setBackground(Color.green);
        createPlayerButton.setBorder(new LineBorder(Color.black));
        createPlayerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
    }

    // MODIFIES: this
    // EFFECTS: Creates a new panel for when an existing player name is inputted
    private void showSamePlayerNamePanel() {
        JOptionPane.showMessageDialog(null,
                "Player is already included", "Error", 1, sadIcon);
    }

    // MODIFIES: this
    // EFFECTS: Creates a remove player button for the team panel
    private void removePlayerButton(JPanel teamButtonPanel, Team team, String s) {
        JButton removePlayerButton = new JButton(s);
        removePlayerButton.setOpaque(true);
        removePlayerButton.setBackground(Color.cyan);
        removePlayerButton.setBorder(new LineBorder(Color.black));
        removePlayerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (team.getPlayerNames().size() > 0 && createdPlayers.getSelectedValue() != null) {
                    String name = createdPlayers.getSelectedValue().toString();
                    Integer playerNum = createdPlayers.getSelectedIndex();
                    team.removePlayer(name);
                    createdPlayersModel.remove(playerNum);
                }
            }
        });
        teamButtonPanel.add(removePlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a view player button for the team panel
    private void viewPlayerButton(JPanel teamButtonPanel, Team team, String s) {
        JButton viewPlayerButton = new JButton(s);
        viewPlayerButton.setOpaque(true);
        viewPlayerButton.setBackground(Color.orange);
        viewPlayerButton.setBorder(new LineBorder(Color.black));
        viewPlayerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        viewPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (team.getPlayerNames().size() > 0 && createdPlayers.getSelectedValue() != null) {
                    String name = createdPlayers.getSelectedValue().toString();
                    Integer playerNum = createdPlayers.getSelectedIndex();
                    Player player = team.findPlayer(name);
                    playerPanel(team, player, playerNum);
                }
            }
        });
        teamButtonPanel.add(viewPlayerButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a go back to trade button for the team panel
    private void goBackToTeamsButton(JPanel teamButtonPanel, String s) {
        JButton goBackToTeamsButton = new JButton(s);
        goBackToTeamsButton.setOpaque(true);
        goBackToTeamsButton.setBackground(Color.red);
        goBackToTeamsButton.setBorder(new LineBorder(Color.black));
        goBackToTeamsButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        goBackToTeamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (teamPanel != null) {
                    teamPanel.setVisible(false);
                }
                if (tradesByPlayerPanel != null) {
                    tradesByPlayerPanel.setVisible(false);
                }
                if (tradesByTeamPanel != null) {
                    tradesByTeamPanel.setVisible(false);
                }
                tradePanel.setVisible(true);
            }
        });
        teamButtonPanel.add(goBackToTeamsButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a player panel
    private void playerPanel(Team team, Player player, Integer num) {
        if (playerPanel != null) {
            playerPanel.removeAll();
            playerPanel.add(showPlayerPanel(player));
            playerPanel.add(createPlayerButtonPanel(team, player, num));
            playerPanel.setVisible(true);
        } else {
            playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
            playerPanel.add(showPlayerPanel(player));
            playerPanel.add(createPlayerButtonPanel(team, player, num));
            jframe.add(playerPanel);
        }
        jframe.pack();
        tradesPanel.setVisible(false);
        tradePanel.setVisible(false);
        teamPanel.setVisible(false);
        if (tradesByPlayerPanel != null) {
            tradesByPlayerPanel.setVisible(false);
        }
        if (tradesByTeamPanel != null) {
            tradesByTeamPanel.setVisible(false);
        }
        jframe.setExtendedState(MAXIMIZED_BOTH);
    }

    // MODIFIES: this
    // EFFECTS: Shows the player details in the player panel
    private JPanel showPlayerPanel(Player player) {
        JPanel createdPlayerPanel = new JPanel();
        JLabel createdPlayerLabel = new JLabel("Player details");
        createdPlayerLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createdPlayerPanel.setLayout(new BoxLayout(createdPlayerPanel, BoxLayout.PAGE_AXIS));
        createdPlayerModel = new DefaultListModel();
        createdPlayerModel.addElement(player.getName());
        createdPlayerModel.addElement(player.getPosition());
        createdPlayerModel.addElement(player.getSalary());
        createdPlayer = new JList(createdPlayerModel);
        createdPlayer.setSelectedIndex(0);
        createdPlayer.setVisibleRowCount(5);
        createdPlayer.setSelectionMode(0);
        createdPlayer.addListSelectionListener(this);
        JScrollPane tradesScrollPane = new JScrollPane(createdPlayer);
        tradesScrollPane.setPreferredSize(new Dimension(50, 100));
        createdPlayerPanel.add(createdPlayerLabel);
        createdPlayerPanel.add(tradesScrollPane);
        return createdPlayerPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a player button panel for the player panel
    private JPanel createPlayerButtonPanel(Team team, Player player, Integer num) {
        JPanel playerButtonPanel = new JPanel();
        playerButtonPanel.setLayout(new GridLayout(0, 1));
        playerButtonPanel.setPreferredSize(new Dimension(100, 200));
        editPlayerNameButton(playerButtonPanel, team, player, num, "Edit the player's name");
        editPlayerPositionButton(playerButtonPanel, player, "Edit the player's position");
        editPlayerSalaryButton(playerButtonPanel, player, "Edit the player's salary");
        goBackToPlayersButton(playerButtonPanel, "Go back to editing the team");
        return playerButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a edit player name button for the player panel
    private void editPlayerNameButton(JPanel playerButtonPanel, Team team, Player player, Integer num, String s) {
        JButton editPlayerNameButton = new JButton(s);
        makeEditPlayerNameButton(editPlayerNameButton);
        editPlayerNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog("Input the new name of the player");
                try {
                    team.editPlayerName(player.getName(), newName);
                    createdPlayerModel.clear();
                    createdPlayerModel.addElement(player.getName());
                    createdPlayerModel.addElement(player.getPosition());
                    createdPlayerModel.addElement(player.getSalary());
                    createdPlayersModel.remove(num);
                    createdPlayersModel.addElement(newName);
                } catch (EmptyStringException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Player name cannot be blank", "Error", 1, sadIcon);
                } catch (SameNameException ex) {
                    showSamePlayerNamePanel();
                }
            }
        });
        playerButtonPanel.add(editPlayerNameButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a edit player name button
    private void makeEditPlayerNameButton(JButton editPlayerNameButton) {
        editPlayerNameButton.setOpaque(true);
        editPlayerNameButton.setBackground(Color.green);
        editPlayerNameButton.setBorder(new LineBorder(Color.black));
        editPlayerNameButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
    }

    // MODIFIES: this
    // EFFECTS: Creates a edit player position button for the player panel
    private void editPlayerPositionButton(JPanel playerButtonPanel, Player player, String s) {
        JButton editPlayerPositionButton = new JButton(s);
        editPlayerPositionButton.setOpaque(true);
        editPlayerPositionButton.setBackground(Color.cyan);
        editPlayerPositionButton.setBorder(new LineBorder(Color.black));
        editPlayerPositionButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        editPlayerPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPosition = JOptionPane.showInputDialog("Input the new position of the player");
                try {
                    player.setPosition(newPosition);
                    createdPlayerModel.clear();
                    createdPlayerModel.addElement(player.getName());
                    createdPlayerModel.addElement(player.getPosition());
                    createdPlayerModel.addElement(player.getSalary());
                } catch (EmptyStringException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Player position cannot be blank", "Error", 1, sadIcon);
                }
            }
        });
        playerButtonPanel.add(editPlayerPositionButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a edit player salary button for the player panel
    private void editPlayerSalaryButton(JPanel playerButtonPanel, Player player, String s) {
        JButton editPlayerSalaryButton = new JButton(s);
        editPlayerSalaryButton.setOpaque(true);
        editPlayerSalaryButton.setBackground(Color.orange);
        editPlayerSalaryButton.setBorder(new LineBorder(Color.black));
        editPlayerSalaryButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        editPlayerSalaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double newSalary = Double.parseDouble(JOptionPane
                        .showInputDialog("Input the new salary of the player"));
                try {
                    player.setSalary(newSalary);
                    createdPlayerModel.clear();
                    createdPlayerModel.addElement(player.getName());
                    createdPlayerModel.addElement(player.getPosition());
                    createdPlayerModel.addElement(player.getSalary());
                } catch (NegativeNumberException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Player salary cannot be a negative number", "Error", 1, sadIcon);
                }
            }
        });
        playerButtonPanel.add(editPlayerSalaryButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a go back to team button for the player panel
    private void goBackToPlayersButton(JPanel playerButtonPanel, String s) {
        JButton goBackToPlayersButton = new JButton(s);
        goBackToPlayersButton.setOpaque(true);
        goBackToPlayersButton.setBackground(Color.red);
        goBackToPlayersButton.setBorder(new LineBorder(Color.black));
        goBackToPlayersButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        goBackToPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerPanel != null) {
                    playerPanel.setVisible(false);
                }
                if (tradesByPlayerPanel != null) {
                    tradesByPlayerPanel.setVisible(false);
                }
                if (tradesByTeamPanel != null) {
                    tradesByTeamPanel.setVisible(false);
                }
                teamPanel.setVisible(true);
            }
        });
        playerButtonPanel.add(goBackToPlayersButton);
    }

    // Needed to use the ListSelectionListener interface
    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
