package client.scenes;

import client.IRandom;
import client.MultiplayerGameManager;
import client.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Inject;
import commons.Multiplayer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;


public class WaitingroomScene implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Multiplayer player;
    private int playerPosInList;
    private boolean entered = false;

    @FXML
    private TableView<Multiplayer> table;
    @FXML
    private TableColumn<Multiplayer, String> playerCol;

    @FXML
    private Label playerCounter;

    private ObservableList<Multiplayer> players;

    private boolean beginGame;

    private MultiplayerGameManager manager;

    @Inject private IRandom rand;

    @Inject
    public WaitingroomScene(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        beginGame = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name));

        server.registerMessages("/topic/multiplayers", Multiplayer.class, m -> {
            if(!entered){
                player = m;
            }
            players.add(m);
            Platform.runLater(() -> {
                playerCounter.setText(players.size() + "/10");
            });
        });

        server.registerMessages("/topic/delete", Multiplayer.class, m -> {
            players.remove(m);
            Platform.runLater(() -> {
                playerCounter.setText(players.size() + "/10");
            });
        });

        server.registerMessages("/topic/game", MultiplayerGameManager.class, m -> {
            if(player != null && entered) {
                entered = false;
                beginGame = true;
                manager = m;
                manager.receivedMGM();
                beginGame = true;
                Platform.runLater(() -> {
                    if(beginGame) {
                        mainCtrl.multiPlayerScreen(player, manager);
                        beginGame = false;
                    }
                });
            }
        }); //removed ", 0, 1"
    }

    /**
     * refresh is a method used to initialize the waiting room's elements
     */
    public void refresh() {
        entered = true;
        players = FXCollections.observableList(server.getMultiplayersByGameID(0));
        playerPosInList = players.size()-1;
        //player = players.get(playerPosInList);
        table.setItems(players);
        playerCounter.setText(players.size() + "/10");
    }

    /**
     * gomulti is a method used to start a new game, adding the players and generating the set of questions
     */
    public void gomulti() {
        int gameID = server.startNewGame();
        player.setGameID(gameID); //updates gameID of local player object - server's gameID has already been updated
        MultiplayerGameManager m = new MultiplayerGameManager(gameID);
        m.generateQuestions(server, rand);
        m.addPlayers(players);
        ObjectMapper om = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(MultiplayerGameManager.class, new MultiplayerGameManagerSerializer());
        sm.addDeserializer(MultiplayerGameManager.class, new MultiplayerGameManagerDeserializer()); //TMP ONLY
        om.registerModule(sm);
        try{
            String mgmSerialized = om.writeValueAsString(m);
//            System.out.println(m);
//            System.out.println(mgmSerialized);
            MultiplayerGameManager mgmDeserialized = om.readValue(mgmSerialized, MultiplayerGameManager.class);
//            System.out.println(mgmDeserialized);
        }catch(Exception e){
            e.printStackTrace();
        }
        server.send("/topic/game", m);
    }

    /**
     * backToMenu is a method associated with the leave button for the waiting room scene
     */
    public void backToMenu(){
        mainCtrl.leaveQuestion(1, 0);
    }

    /**
     * leave is a method for what happens when user leaves the waiting room
     */
    public void leave(){
        entered = false;
        server.send("/topic/delete",player);
        server.deleteMultiplayer(player.getId());
    }
}
