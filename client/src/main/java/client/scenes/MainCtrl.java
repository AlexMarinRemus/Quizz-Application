/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.MultiplayerGameManager;
import commons.Multiplayer;
import commons.Player;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import javax.swing.*;

public class MainCtrl {

    private Stage primaryStage;
    private Stage playersStage;

    private PlayersOverviewCtrl playerOverviewCtrl;
    private Scene playersOverview;

    private AddPlayerCtrl addPlayerCtrl;
    private Scene addPlayer;

    private SplashScreen splashCtrl;
    private Scene splash;

    private SinglePlayer singleCtrl;
    private Scene single;

    private MultiplayerScreen multiCtrl;
    private Scene multi;

    private WaitingroomScene waitCtrl;
    private Scene wait;

    private Helpbutton helpCtrl;
    private Scene help;

    private Leaderboard leaderboardCtrl;
    private Scene leaderboard_screen;

    private MultiplayerLeaderboard multiplayerLeaderboardCtrl;
    private Scene multiplayerLeaderboard_screen;

    private LeaveGame leaveCtrl;
    private Scene leave;


    private List<Timer> timers = new ArrayList<>();

    /**
     * initialize is a method used to initialize all the scene
     * @param primaryStage
     * @param secondaryStage
     * @param playersOverview
     * @param addPlayer
     * @param splash
     * @param single
     * @param multi
     * @param wait
     * @param help
     * @param leaderboard
     * @param leave
     */
    public void initialize(Stage primaryStage, Stage secondaryStage, Pair<PlayersOverviewCtrl, Parent> playersOverview,
                           Pair<AddPlayerCtrl, Parent> addPlayer, Pair<SplashScreen, Parent> splash, Pair<SinglePlayer,
                            Parent> single, Pair<MultiplayerScreen, Parent> multi, Pair<WaitingroomScene, Parent> wait,
                           Pair<Helpbutton, Parent> help, Pair<Leaderboard, Parent> leaderboard, Pair<MultiplayerLeaderboard, Parent> multiplayerLeaderboard, Pair<LeaveGame, Parent> leave){

        this.primaryStage = primaryStage;
        this.playerOverviewCtrl = playersOverview.getKey();
        this.playersOverview = new Scene(playersOverview.getValue());
        this.playersStage = secondaryStage;

        this.addPlayerCtrl = addPlayer.getKey();
        this.addPlayer = new Scene(addPlayer.getValue());

        this.splashCtrl = splash.getKey();
        this.splash = new Scene(splash.getValue());

        this.singleCtrl = single.getKey();
        this.single = new Scene(single.getValue());

        this.multiCtrl = multi.getKey();
        this.multi = new Scene(multi.getValue());

        this.waitCtrl = wait.getKey();
        this.wait = new Scene(wait.getValue());

        this.helpCtrl = help.getKey();
        this.help = new Scene(help.getValue());

        this.leaderboardCtrl = leaderboard.getKey();
        this.leaderboard_screen = new Scene(leaderboard.getValue());

        this.multiplayerLeaderboardCtrl = multiplayerLeaderboard.getKey();
        this.multiplayerLeaderboard_screen = new Scene(multiplayerLeaderboard.getValue());

        this.leaveCtrl = leave.getKey();
        this.leave = new Scene(leave.getValue());

        showOverview();
        showPlayersOverview();
        primaryStage.show();
        //playersStage.show();
        /*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });*/
    }

    /**
     * Checks if the player is currently seeing the leaving screen
     * @return true if player is on exit screen, false otherwise
     */
    public boolean exitScreen(){
        return primaryStage.getScene().equals(leave);
    }

    /**
     * showOverview is the method used for displaying the splashscreen
     */
    public void showOverview() {
        primaryStage.setTitle("Energy Game");
        primaryStage.setScene(splash);
        //primaryStage.centerOnScreen();
        splashCtrl.refresh();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(3,1);

            }
        });
    }

    /**
     * showPlayersOverview is the method used for displaying the database with players
     */
    public void showPlayersOverview() {
        playersStage.setTitle("Players overview");
        playersStage.setScene(playersOverview);
        playersStage.centerOnScreen();
        playerOverviewCtrl.refresh();
    }

    /**
     * singlePlayerScreen is the method user for displaying the single player scene
     * @param p is the user which has saved the score and the name
     */
    public void singlePlayerScreen(Player p){
        primaryStage.setTitle("Energy Game: Single Player");
        primaryStage.setScene(single);
        //primaryStage.centerOnScreen();
        singleCtrl.load(p);
        singleCtrl.decreaseTime(20, -0.5);
        //single.setOnKeyPressed(e -> singleCtrl.keyPressed(e));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(0,1);
            }
        });
    }

    /**
     * multiPlayerScreen is the method user for displaying the multiplayer scene
     * @param p is the user which has saved the score and the name
     * @param m is a manager which will handle all of the question generation / timing in a multiplayer game
     */
    public void multiPlayerScreen(Multiplayer p, MultiplayerGameManager m){
        primaryStage.setTitle("Energy Game: Multiplayer");
        primaryStage.setScene(multi);
        multiCtrl.load(p, m);
        multiCtrl.decreaseTime(20, -0.5);
        //single.setOnKeyPressed(e -> singleCtrl.keyPressed(e));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(2,1);
            }
        });
    }

    /**
     * Shows the multiplayer screen to the user
     */
    public void multiPlayerScreen(){
        primaryStage.setTitle("Energy Game: Multiplayer");
        primaryStage.setScene(multi);
        //single.setOnKeyPressed(e -> singleCtrl.keyPressed(e));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(2,1);
            }
        });
    }

    /**
     * waiting is the method used for displaying the waiting room scene
     */
    public void waiting(){
        primaryStage.setTitle("Energy Game: WaitingRoom");
        primaryStage.setScene(wait);
        //primaryStage.centerOnScreen();
        waitCtrl.refresh();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(1,1);
            }
        });
        //single.setOnKeyPressed(e -> singleCtrl.keyPressed(e));
    }

    /**
     * leaveQuestion is the method used for displaying a scene where the user is asked if he wants to leave the game
     * @param screenType the type of screen to be left (from 0 to 8)
     * @param leaveType which type of leave does the user asks for (0 - back to spalsh screen, 1 - completely close the window)
     */
    public void leaveQuestion(int screenType, int leaveType){
        primaryStage.setTitle("Leave the game");
        primaryStage.setScene(leave);
        //primaryStage.centerOnScreen();
        leaveCtrl.refresh(singleCtrl, waitCtrl, multiCtrl, screenType, leaveType);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveCtrl.cancelLeave();
            }
        });
    }

    /**
     * leaveQuestion is the method used for displaying a scene where the user is asked if he wants to leave the game. Use if player has to be set for leaderboard
     * @param screenType the type of screen to be left (from 0 to 8)
     * @param leaveType leaveType which type of leave does the user asks for (0 - back to spalsh screen, 1 - completely close the window)
     * @param player the player playing the multiplayer game
     */
    public void leaveQuestion(int screenType, int leaveType, Multiplayer player) {
        primaryStage.setTitle("Leave the game");
        primaryStage.setScene(leave);
        leaveCtrl.refresh(singleCtrl, waitCtrl, multiCtrl, screenType, leaveType, player);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveCtrl.cancelLeave();
            }
        });
    }

    /**
     * leaveQuestion is the method used for displaying a scene where the user is asked if he wants to leave the game. Use if player has to be set for leaderboard
     * @param screenType the type of screen to be left (from 0 to 8)
     * @param leaveType leaveType which type of leave does the user asks for (0 - back to spalsh screen, 1 - completely close the window)
     * @param player the player playing the single player game
     */
    public void leaveQuestion(int screenType, int leaveType, Player player) {
        primaryStage.setTitle("Leave the game");
        primaryStage.setScene(leave);
        leaveCtrl.refresh(singleCtrl, waitCtrl, multiCtrl, screenType, leaveType, player);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveCtrl.cancelLeave();
            }
        });
    }

    /**
     * backToSinglePlayer is the method used for displaying the single player scene again if the user changed his mind
     */
    public void backToSinglePlayer(){
        primaryStage.setTitle("Energy Game: Single Player");
        primaryStage.setScene(single);
        //primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(0,1);
            }
        });
    }

    /**
     * backToWaiting is the method used for displaying the waiting room scene again if the user changed his mind
     */
    public void backToWaiting(){
        primaryStage.setTitle("Energy Game: WaitingRoom");
        primaryStage.setScene(wait);
        //primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(1,1);
            }
        });
    }

//    public void showAdd() {
//        primaryStage.setTitle("Quotes: Adding Quote");
//        primaryStage.setScene(add);
//        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
//    }
    public void showAddPlayer() {
        playersStage.setTitle("Players: Adding Player");
        playersStage.setScene(addPlayer);
        playersStage.centerOnScreen();
        addPlayer.setOnKeyPressed(e -> addPlayerCtrl.keyPressed(e));
    }

    /**
     * showHelp is a method used to display the help scene, associated with the help button
     */
    public void showHelp(){
        primaryStage.setTitle("Energy Game: Help Menu");
        primaryStage.setScene(help);
        //primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(4,1);
            }
        });
    }

    /**
     * showLeaderboard is the method used to display the leaderboard at the end of the game
     * @param player is the user which has saved the score and the name
     */
    public void showLeaderboard(Player player){
        primaryStage.setTitle("Energy Game: Leaderboard");
        primaryStage.setScene(leaderboard_screen);
        leaderboardCtrl.load(player, singleCtrl);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(5,1);
            }
        });
    }

    /**
     * Shows the leaderboard screen to the user in single player
     */
    public void backToLeaderboard(){
        primaryStage.setTitle("Energy Game: Leaderboard");
        primaryStage.setScene(leaderboard_screen);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(5,1);
            }
        });
    }

    /**
     * showLeaderboard is the method used to display the multiplayer leaderboard at the middle and the end of the game
     * @param player is the user which has saved the score and the name
     */
    public void showmultiplayerLeaderboard(Multiplayer player){
        primaryStage.setTitle("Energy Game: Multiplayer Leaderboard");
        primaryStage.setScene(multiplayerLeaderboard_screen);
        multiplayerLeaderboardCtrl.refresh();
        multiplayerLeaderboardCtrl.load(player, multiCtrl);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(6,1);
            }
        });
    }

    /**
     * Shows the leaderboard screen to the user in multiplayer
     */
    public void backToMultiplayerLeaderboard(){
        primaryStage.setTitle("Energy Game: Multiplayer Leaderboard");
        primaryStage.setScene(multiplayerLeaderboard_screen);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                leaveQuestion(6,1);
            }
        });
    }

    /**
     * startTimer is the method used to start the timer of the game
     * @param t the timer
     * @param task the task
     * @param delay
     * @param period the number of seconds
     */
    public void startTimer(Timer t, TimerTask task, int delay, int period){
        timers.add(t);
        t.scheduleAtFixedRate(task,delay,period);
    }

    /**
     * stopTimer is the method used to stop the timer
     * @param t the timer
     */
    public void stopTimer(Timer t){
        if(timers.indexOf(t) >= 0){
            timers.get(timers.indexOf(t)).cancel();
            timers.remove(t);
        }
    }
}
