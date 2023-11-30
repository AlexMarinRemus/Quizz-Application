package client.scenes;

import commons.Multiplayer;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ProgressBar;

public class MultiplayerTimer extends AnimationTimer {
    private MultiplayerScreen mp;

    private Multiplayer pla;
    private int count;
    private ProgressBar timeBar;
    private double progress = 1;
    private int timerQuestionNotAnswered = 0;
    private int timerQuestionAnswered = 0;
    private long time = 20;
    private long resTime = 0;
    private long nanosBetweenPulses;
    private long lastPulseTimeStamp;
    private double progressNew;
    private SimpleStringProperty timeLeft = new SimpleStringProperty("Time left: 00:20");

    /**
     * MultiplayerTimer is a constructor method for the multiplayer timer
     * @param secondsBetweenPulses represents the period between the pulses
     * @param singlePlayer represents the multiplayer class
     */
    public MultiplayerTimer(double secondsBetweenPulses, MultiplayerScreen singlePlayer) {
        super();
        this.mp = singlePlayer;
        if (secondsBetweenPulses < 0) secondsBetweenPulses = 0;
        nanosBetweenPulses =(long)(secondsBetweenPulses*1000000000L);
        lastPulseTimeStamp = System.nanoTime();
        mp.getTimer().textProperty().bind(timeLeft);
    }
    @Override
    public void handle(long now) {
        long nanosSinceLastPulse = now - lastPulseTimeStamp;
        if (nanosSinceLastPulse > nanosBetweenPulses) {
            lastPulseTimeStamp = now;
            pla = mp.getPlayer();
            if (!mp.getSubmit().isVisible() && time > 3 && mp.getPlayersAnswered() == mp.getOpponentsNum()) {
                timerQuestionAnswered = 1;
                if (count <= 7)
                    run();
            }

            if (count == 20) {
                run();
                count = 0;
                timerQuestionNotAnswered = 1;
            }
            count++;

            if (timerQuestionNotAnswered == 1) {
                mp.decreaseTime(time, progress);
                progress = progress - 0.0025;
            }

            if (timerQuestionAnswered == 1) {
                mp.decreaseTime(21, progress);
                progress = progress - progressNew;
                timerQuestionNotAnswered = 0;
            }
        }
    }

    /**
     * run is a method associated with the timer and its features
     */
    public void run() {
        if (!mp.getSubmit().isVisible() && time > 3 && mp.getPlayersAnswered() == mp.getOpponentsNum()) {
            mp.disableAllJokers();
            time = 3;
            resTime = 0;
            progressNew = progress/60;
        }
        if (time == 0) {
            timerQuestionAnswered = 0;
            progress = 1;
            timeLeft.set("Time left: 00:00");

            if (mp.getCurr().getType() != 4) {
                if (mp.getGroup().getSelectedToggle() != null && mp.getSubmit().isVisible()) {
                    mp.showCorrect();
                    mp.checkCorrect(pla);
                }
            } else {
                if (mp.getAnstext().getText() != null && !mp.getAnstext().getText().equals("") && mp.getSubmit().isVisible()) {
                    mp.showCorrect();
                    mp.checkCorrect(pla);
                }
            }
            if (resTime > 0){
                time = resTime;
                resTime = 0;
                mp.submit.setVisible(false);
                mp.setwrongtrue(false);
                mp.showCorrect();
                progressNew = (double) 1/(20*time);
                timerQuestionAnswered = 1;
            } else {
                time = 20;
                mp.getManager().advanceNextQuestion(this);
                mp.refreshQuestion();
            }
        }
        if (time > 9) {
            timeLeft.set("Time left: 00:" + time--);
        } else {
            timeLeft.set("Time left: 00:0" + time--);

        }
    }

    /**
     * lessTime is a method that halves the time for answering and adds that to resTime so it is still synchronized with the opponents
     */
    public void lessTime() {
        resTime += time/2;
        time = time/2;
        progress = (double) time/20;
    }

    /**
     * getTime is a method that return the time you have left
     * @return the time you have left for answering a question
     */
    public long getTime() { return time; }
}
