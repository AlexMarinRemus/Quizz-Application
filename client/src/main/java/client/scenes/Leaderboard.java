package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Leaderboard {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Label label10;
    @FXML
    private Label label11;
    private SinglePlayer singleplayerScreen;

    @Inject
    public Leaderboard(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * goback is a function accessed from the leave button which opens the splash screen
     */
    public void goback() {
        refresh();
        singleplayerScreen.onLeave();
        mainCtrl.showOverview();
    }

    /**
     * load is a method used to add the players from the server in the leaderboard's labels making bold the labels
     * containing the name of the player who finished the game.
     * @param player is the user which has saved the score and the name
     */
    public void load(Player player, SinglePlayer sp) {
        this.singleplayerScreen = sp;
        Label[] labels= {label1, label2, label3, label4, label5, label6, label7, label8, label9, label10};
        var players = server.getPlayers();
        List<Player> sortedPlayer = players.stream()
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .collect(Collectors.toList());
        for(int i = 0; i < sortedPlayer.size(); i++) {
            if (sortedPlayer.size() > i) {
                if (i < 10){
                    labels[i].setText((i+1)+". "+sortedPlayer.get(i).getName()+": "+sortedPlayer.get(i).getScore());
                }
                if (sortedPlayer.get(i).equals(player)) {
                    if(i > 10) {
                        label9.setText("...");
                        label11.setText("...");
                        label10.setText((i+1)+". "+sortedPlayer.get(i).getName()+": "+sortedPlayer.get(i).getScore());
                        label10.setStyle("-fx-font-weight: bold");

                    }
                    else {
                        labels[i].setStyle("-fx-font-weight: bold");
                        label11.setText("");
                    }
                }

            }
            else {
                labels[i].setText((i+1)+".");
            }
        }
    }

    /**
     * refresh is a method used to initialize the style of the labels
     */
    public void refresh() {
        label1.setStyle("-fx-font-weight: normal");
        label2.setStyle("-fx-font-weight: normal");
        label3.setStyle("-fx-font-weight: normal");
        label4.setStyle("-fx-font-weight: normal");
        label5.setStyle("-fx-font-weight: normal");
        label6.setStyle("-fx-font-weight: normal");
        label7.setStyle("-fx-font-weight: normal");
        label8.setStyle("-fx-font-weight: normal");
        label9.setStyle("-fx-font-weight: normal");
        label10.setStyle("-fx-font-weight: normal");
        label11.setStyle("-fx-font-weight: normal");

    }
}
