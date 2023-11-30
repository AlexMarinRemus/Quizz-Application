package client;

import client.scenes.MultiplayerTimer;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Multiplayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiplayerGameManager implements Serializable {

    private transient int gameID;
    private List <Question> questions;
    private transient Iterator<Question> it;
    private transient Question curr;
    private List <Multiplayer> players;

    //this will handle all of the question generation / timing in a multiplayer game. It is generated once per game.

    public MultiplayerGameManager() {
        this.gameID = 0;
        questions = new ArrayList<>();
    }

    @Inject
    public void generateQuestions(ServerUtils server, IRandom rand) {
        for (int i = 0; i < 20; i++) {
            //decreaseTime(timeCountdown);
            Question q = new Question(server, rand);
            q.generateQuestion();
            questions.add(q);
        }
        it = questions.iterator();
        curr = it.next();
    }

    public void advanceNextQuestion(MultiplayerTimer timer) {
        if (hasNextQuestion()) {
            if (questions.indexOf(curr) == 9){
                Question Leaderboard = new Question();
                Leaderboard.setQuestionText("Leaderboard");
                curr = Leaderboard;
            } else {
                curr = it.next();
            }
        } else {
            Question Leaderboard = new Question();
            Leaderboard.setQuestionText("Leaderboard");
            curr = Leaderboard;
            timer.stop();
        }
    }

    /**
     * Updates all the players with the new gameId
     */
    public void updateGameId(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).setGameID(gameID);
        }
    }

    public boolean hasNextQuestion(){
        if (it == null) return false;
        return it.hasNext();
    }

    public Question getCurrent() {
        return curr;
    }

    public MultiplayerGameManager(int gameID) {
        questions = new ArrayList<>();
        this.gameID = gameID;
    }

    public void receivedMGM() {
        it = questions.iterator();
        curr = it.next();
    }

    public int getGameID() {
        return gameID;
    }

    public void addQuestions(List<Question> questions){
        this.questions = questions;
    }

    public void addPlayers(List <Multiplayer> players) {
        this.players = players;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Multiplayer> getPlayers() {
        return players;
    }

}
