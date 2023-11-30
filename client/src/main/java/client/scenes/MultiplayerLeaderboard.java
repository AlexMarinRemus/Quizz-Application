package client.scenes;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Multiplayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

    public class MultiplayerLeaderboard {

        private final ServerUtils server;
        private final MainCtrl mainCtrl;
        private int intermediary = 1;

        @FXML
        private Label leaderboardposition1;
        @FXML
        private Label leaderboardposition2;
        @FXML
        private Label leaderboardposition3;
        @FXML
        private Label leaderboardposition4;
        @FXML
        private Label leaderboardposition5;
        @FXML
        private Label leaderboardposition6;
        @FXML
        private Label leaderboardposition7;
        @FXML
        private Label leaderboardposition8;
        @FXML
        private Label leaderboardposition9;
        @FXML
        private Label leaderboardposition10;
        @FXML
        private Label leaderboardposition11;
        @FXML
        private Button goToMenu;
        @FXML
        private ProgressIndicator progressIndicator;

        private MultiplayerScreen playerSetup;

        @Inject
        public MultiplayerLeaderboard(ServerUtils server, MainCtrl mainCtrl) {
            this.mainCtrl = mainCtrl;
            this.server = server;
        }

        /**
         * goback is a function accessed from the leave button which opens the splash screen
         */
        public void goback() {
            refresh();
            playerSetup.resetGameSetting();
            mainCtrl.showOverview();
        }

        /**
         * load is a method used to add the players from the server in the leaderboard's labels making bold the labels
         * containing the name of the player who finished the game.
         * @param player is the user which has saved the score and the name
         */
        public void load(Multiplayer player, MultiplayerScreen ms) {
            playerSetup = ms;
            if(intermediary == 1) {
                goToMenu.setVisible(false);
                intermediary = 0;
                progressIndicator.setVisible(true);
            }
            else{
                goToMenu.setVisible(true);
                progressIndicator.setVisible(false);
            }
            Label[] labels= {leaderboardposition1, leaderboardposition2, leaderboardposition3, leaderboardposition4, leaderboardposition5, leaderboardposition6, leaderboardposition7, leaderboardposition8, leaderboardposition9, leaderboardposition10};
            var players = server.getMultiplayersByGameID(player.getGameID());
            List<Multiplayer> sortedPlayer = players.stream()
                    .sorted(Comparator.comparing(Multiplayer::getScore).reversed())
                    .collect(Collectors.toList());
            for(int i = 0; i < sortedPlayer.size(); i++) {
                if (sortedPlayer.size() > i) {
                    if (i < 10){
                        labels[i].setText((i+1)+". "+sortedPlayer.get(i).getName()+": "+sortedPlayer.get(i).getScore());
                    }
                    if (sortedPlayer.get(i).equals(player)) {
                        if(i > 10) {
                            leaderboardposition9.setText("...");
                            leaderboardposition11.setText("...");
                            leaderboardposition10.setText((i+1)+". "+sortedPlayer.get(i).getName()+": "+sortedPlayer.get(i).getScore());
                            leaderboardposition10.setStyle("-fx-font-weight: bold");

                        }
                        else {
                            labels[i].setStyle("-fx-font-weight: bold");
                            leaderboardposition11.setText("");
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
            leaderboardposition1.setStyle("-fx-font-weight: normal");
            leaderboardposition2.setStyle("-fx-font-weight: normal");
            leaderboardposition3.setStyle("-fx-font-weight: normal");
            leaderboardposition4.setStyle("-fx-font-weight: normal");
            leaderboardposition5.setStyle("-fx-font-weight: normal");
            leaderboardposition6.setStyle("-fx-font-weight: normal");
            leaderboardposition7.setStyle("-fx-font-weight: normal");
            leaderboardposition8.setStyle("-fx-font-weight: normal");
            leaderboardposition9.setStyle("-fx-font-weight: normal");
            leaderboardposition10.setStyle("-fx-font-weight: normal");
            leaderboardposition11.setStyle("-fx-font-weight: normal");

        }
    }

