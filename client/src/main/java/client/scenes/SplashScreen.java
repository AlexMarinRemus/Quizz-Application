package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Multiplayer;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SplashScreen {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField name;

    @FXML
    private Label lead1;

    @FXML
    private Label lead2;

    @FXML
    private Label lead3;

    @FXML
    private Label lead4;

    @FXML
    private Label lead5;

    @FXML
    private Label lead6;

    @FXML
    private Label lead7;

    @FXML
    private Label lead8;

    @FXML
    private Label lead9;

    @FXML
    private Label lead10;

    @FXML
    private TextField url;

    @Inject
    public SplashScreen(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * startsingle is the method used to start the single player mode. In this method, firstly we verify if the name
     * text field exists. If it exists, we verify whether the string containing the name is empty or not. In case it is
     * empty it will display an error, in other case, it will start the single player mode, adding the user to the
     * single player database(when the singleplayer button is pressed).
     */
    public void startsingle() {
        if(name != null) {
            String nm = name.getText();
            if (nm.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fill all the required fields!");
                alert.showAndWait();
            }
            else{
                Player p = new Player(nm);
                p = server.addPlayer(p);
                mainCtrl.singlePlayerScreen(p);
            }

        }

    }


    /**
     * startmulti is the method used to start the multiplayer mode. In this method, firstly we verify if the name text
     * field exists. If it exists, we verify whether the string containing the name is empty, if it already exists in the
     * waiting room or if the waiting room is full. In these cases we display a specific error depending on the encountered
     * issue. In other case, it will start the waiting room scene, adding the user to the multiplayer database(when the
     * multiplayer button is pressed)
     */
    public void startmulti() {
        if(url != null && name != null){
            String port = url.getText();
            String n = name.getText();
            if(port.isEmpty() || n.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fill all the required fields!");
                alert.showAndWait();
            }
            else if (samename(n)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This name already exists!");
                alert.showAndWait();
            }
            else{

                server.setUrl(port);
                List<Multiplayer> waitingPlayers = null;
                try {
                    //only works with "8080" for now
                    waitingPlayers = server.getMultiplayersByGameID(0);
                }
                catch (RuntimeException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't connect to server, choose an other one!");
                    alert.showAndWait();
                    server.setToDefaultServer();
                    return;
                }

                if(waitingPlayers.size() >= 10){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Waiting Room is full, please join later.");
                    alert.showAndWait();
                    return;
                }


                String sc = "ws://localhost:"+port+"/websocket";
                try {
                    server.refreshConnection(sc);
                }
                catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't connect to server, choose an other one!");
                    alert.showAndWait();

                    return;
                }

                Multiplayer p = new Multiplayer(n);
                server.send("/app/multiplayers", p);
                mainCtrl.waiting();

            }
        }
    }

    /**
     * samename is the method used to verify if player's name already appears in the waiting room list of players
     * @param name is the string containing the name of the player
     * @return false or true depending on the list of names in the waiting room and the player's name
     */
    public boolean samename(String name){
        List<Multiplayer> mlist = server.getMultiplayersByGameID(0);
        if(!mlist.isEmpty()){
            for(int i = 0; i < mlist.size(); i++){
                if(name.equals(mlist.get(i).getName())) return true;
            }
        }
        return false;
    }

    /**
     * refresh is the method used to display the top ten players from the database and their scores. The leaderboard is
     * automaticaly modified when the splash screen is accessed.
     */
    public void refresh() {
        Label[] labels= {lead1, lead2, lead3, lead4, lead5, lead6, lead7, lead8, lead9, lead10};
        var players = server.getPlayers();
        List<Player> sortedPlayer = players.stream()
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .collect(Collectors.toList());
        for(int i = 0; i < 10; i++) {
            if (sortedPlayer.size() > i) {
                labels[i].setText((i+1)+". "+sortedPlayer.get(i).getName()+": "+sortedPlayer.get(i).getScore());
            }
            else {
                labels[i].setText((i+1)+".");
            }
        }
    }

    /**
     * starthelp is the method used to display the help screen about the game(when the help button is pressed).
     */
    public void starthelp() {
        mainCtrl.showHelp();
    }
}
