package client.scenes;


import commons.Multiplayer;
import commons.Player;
import javafx.application.Platform;

import javax.inject.Inject;

public class LeaveGame {

    private SinglePlayer single;
    private WaitingroomScene waitingRoom;
    private MultiplayerScreen multi;
    private int screenType;
    private int leaveType;

    @Inject private MainCtrl mainCtrl;
    private Multiplayer mplayer;
    private Player splayer;

    /**
     * leaveGame is a method used for the implementation of the leave button in the waiting room/singleplayer mode/
     * multiplayer mode
     */
    public void leaveGame(){
        switch (screenType){
            case 0:
                single.onLeave();
                break;
            case 1:
                waitingRoom.leave();
                break;
            case 2:
            case 7:
                multi.leave();
                break;
            default:
        }

        switch (leaveType){
            case 0:
                mainCtrl.showOverview();
                break;
            case 1:
                Platform.exit();
                System.exit(0);
                break;
            default:
        }
    }

    /**
     * cancelLeave is a method used to put the player back to the waiting room/single player/multiplayer mode if he changed
     * his mind and don't want to leave again.
     */
    public void cancelLeave(){
        switch (screenType){
            case 0:
                mainCtrl.backToSinglePlayer();
                break;
            case 1:
                mainCtrl.backToWaiting();
                break;
            case 2:
                mainCtrl.multiPlayerScreen();
                break;
            case 3:
                mainCtrl.showOverview();
                break;
            case 4:
                mainCtrl.showHelp();
                break;
            case 5:
                mainCtrl.backToLeaderboard();
                break;
            case 6:
                mainCtrl.backToMultiplayerLeaderboard();
                break;
            case 7:
                mainCtrl.showmultiplayerLeaderboard(mplayer);
                break;
            case 8:
                mainCtrl.showLeaderboard(splayer);
                break;
            default:
        }
    }

    /**
     * refresh is a constructor method for the leave button where we initialize the variable depending on the screen
     * we want to leave.
     * @param p represents the single player scene
     * @param w represents the waiting room scene
     * @param m represents the multiplayer scene
     * @param screenType the type of screen we want to leave (from 0 to 8)
     * @param leaveType the type of leave the user wants (0 or 1)
     */
    public void refresh(SinglePlayer p, WaitingroomScene w, MultiplayerScreen m, int screenType, int leaveType) {
        single = p;
        waitingRoom = w;
        multi = m;
        this.screenType = screenType;
        this.leaveType = leaveType;
    }

    /**
     * refresh is a constructor method for the leave button where we initialize the variable depending on the screen
     * we want to leave. Use in case of screen switch to leaderboard while user is on leaving screen in multiplayer
     * @param p represents the single player scene
     * @param w represents the waiting room scene
     * @param m represents the multiplayer scene
     * @param screenType the type of screen we want to leave (from 0 to 8)
     * @param leaveType the type of leave the user wants (0 or 1)
     * @param player the player in the multiplayer game
     */
    public void refresh(SinglePlayer p, WaitingroomScene w, MultiplayerScreen m, int screenType, int leaveType, Multiplayer player) {
        single = p;
        waitingRoom = w;
        multi = m;
        this.screenType = screenType;
        this.leaveType = leaveType;
        this.mplayer = player;
    }

    /**
     * refresh is a constructor method for the leave button where we initialize the variable depending on the screen
     * we want to leave. Use in case of screen switch to leaderboard while user is on leaving screen in single player
     * @param p represents the single player scene
     * @param w represents the waiting room scene
     * @param m represents the multiplayer scene
     * @param screenType the type of screen we want to leave (from 0 to 8)
     * @param leaveType the type of leave the user wants (0 or 1)
     * @param player the player in the single player game
     */
    public void refresh(SinglePlayer p, WaitingroomScene w, MultiplayerScreen m, int screenType, int leaveType, Player player) {
        single = p;
        waitingRoom = w;
        multi = m;
        this.screenType = screenType;
        this.leaveType = leaveType;
        this.splayer = player;
    }
}
