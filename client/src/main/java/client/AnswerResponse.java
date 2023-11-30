package client;

import commons.Multiplayer;

public class AnswerResponse {
    private Multiplayer player;
    private boolean answer = false;

    public AnswerResponse() {
        player = new Multiplayer();
        answer = false;
    }

    public AnswerResponse(Multiplayer player) {
        this.player = player;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {
        return answer;
    }

    public Multiplayer getPlayer() {
        return player;
    }
}
