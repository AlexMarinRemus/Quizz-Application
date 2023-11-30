package client.scenes;

import commons.Player;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ProgressBar;

public class SinglePlayerTimer extends AnimationTimer {
    private SinglePlayer sp;

    private Player pla;
    private int count;
    private ProgressBar timeBar;
    private double progress = 1;
    private int timerQuestionNotAnswered = 0;
    private int timerQuestionAnswered = 0;
    private long time = 20;
    private double progressNew;
    private long nanosBetweenPulses;
    private long lastPulseTimeStamp;
    private SimpleStringProperty timeLeft = new SimpleStringProperty("Time left: 00:20");

    /**
     * SinglePlayerTimer is a constructor method for the single player timer
     * @param secondsBetweenPulses represents the period between the pulses
     * @param singlePlayer represents the single player class
     */
    public SinglePlayerTimer(double secondsBetweenPulses, SinglePlayer singlePlayer) {
        super();
        this.sp = singlePlayer;
        if (secondsBetweenPulses < 0) secondsBetweenPulses = 0;
        nanosBetweenPulses =(long)(secondsBetweenPulses*1000000000L);
        lastPulseTimeStamp = System.nanoTime();
        sp.getTimer().textProperty().bind(timeLeft);
        time = sp.timeCountdown;
    }
    @Override
    public void handle(long now) {
        long nanosSinceLastPulse = now - lastPulseTimeStamp;
        if (nanosSinceLastPulse > nanosBetweenPulses) {
            lastPulseTimeStamp = now;
            pla = sp.getPlayer();

            if (!sp.getSubmit().isVisible() && time > 3)
            {timerQuestionAnswered = 1;
                if(count <= 7)
            run();}

            if(count == 20)
            {run();
           count = 0;
            timerQuestionNotAnswered = 1;}
           count++;

           if(timerQuestionNotAnswered == 1)
           {sp.decreaseTime(time, progress);
            progress = progress - 0.0025;}

           if(timerQuestionAnswered == 1){
               sp.decreaseTime(21, progress);
               progress = progress - progressNew;
               timerQuestionNotAnswered = 0;
           }
        }
    }

    /**
     * run is a method associated with the timer and its features
     */
    public void run() {

        if (!sp.getSubmit().isVisible() && time > 3) {
            time = 3;
            sp.timeCountdown = 3;
            timerQuestionAnswered = 1;
            progressNew = progress/60;
        }

        if (time == 0) {
            timerQuestionAnswered = 0;
            progress = 1;
            sp.timeCountdown = (int) time;
            timeLeft.set("Time left: 00:00");

            if (sp.getCurr().getType() != 4) {
                if (sp.getGroup().getSelectedToggle() != null && sp.getSubmit().isVisible()) {
                    sp.showCorrect();
                    sp.checkCorrect(pla);
                }
            } else {
                if(sp.getAnstext().getText() != null && !sp.getAnstext().getText().equals("") && sp.getSubmit().isVisible()) {
                    sp.showCorrect();
                    sp.checkCorrect(pla);
                }
            }
            time = 20;
            sp.timeCountdown = 20;
            sp.advanceNextQuestion(this);
            sp.refreshQuestion();
        }
        if (time > 9) {
            timeLeft.set("Time left: 00:" + time--);
            sp.timeCountdown = (int) time;
        } else {
            timeLeft.set("Time left: 00:0" + time--);
            sp.timeCountdown = (int) time;

        }
    }
}
