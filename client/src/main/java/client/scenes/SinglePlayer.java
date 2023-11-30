package client.scenes;

import client.IRandom;
import client.Question;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import java.util.*;

public class SinglePlayer{

    @Inject private ServerUtils server;
    @Inject private MainCtrl mainCtrl;

    private AnimationTimer time;

    @FXML
    private Label User_name;

    @FXML
    private Label timer;

    @FXML
    private Button submit;
    @FXML
    private Button eliminate;

    @FXML
    private TextField anstext;

    @FXML
    private ProgressBar timeBar;

    @FXML
    private Label question;

    @FXML
    private Button leave;

    @FXML
    private Label ans1;
    @FXML
    private Label ans2;
    @FXML
    private Label ans3;
    @FXML
    private Label ans4;
    @FXML
    private RadioButton rad1;
    @FXML
    private RadioButton rad2;
    @FXML
    private RadioButton rad3;
    @FXML
    private RadioButton rad4;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

   @FXML
   private ImageView imageEstimate;

   @FXML
   private ImageView imageGTE1;

   @FXML
   private ImageView imageGTE2;

    private ToggleGroup group;
    private List<Question> questions;
    private Iterator<Question> it;
    private Question curr;

    private Player player;

    @Inject private IRandom rand;
    public SinglePlayer() {
        player = null;
        group = new ToggleGroup();
        questions = new ArrayList<>();
    }

    private String correct_ans = "";
    public int timeCountdown = 20;

    @FXML
    AnchorPane pane;

    @FXML
    public Button dbpoints;

    @FXML
    Label points;

    @FXML
    Label cans;

    @FXML
    Label questioncounter;

    @FXML
    Label box1;

    @FXML
    Label box2;

    @FXML
    Label box3;

    @FXML
    Label box4;

    @FXML
    Label box5;

    @FXML
    Label box6;

    @FXML
    Label box7;

    @FXML
    Label box8;

    @FXML
    Label box9;

    @FXML
    Label box10;

    @FXML
    Label box11;

    @FXML
    Label box12;

    @FXML
    Label box13;

    @FXML
    Label box14;

    @FXML
    Label box15;

    @FXML
    Label box16;

    @FXML
    Label box17;

    @FXML
    Label box18;

    @FXML
    Label box19;

    @FXML
    Label box20;

    int question_index=0;

    public void do1(ActionEvent event) {
    }

    public void do2(ActionEvent event) {
    }

    public void do3(ActionEvent event) {
    }

    public void do4(ActionEvent event) {
    }

    public void verify(ActionEvent event) {
    }

    /**
     * removeWrong is the method used for the "Remove wrong answer" joker. We take 2 random numbers corresponding to the
     * index of the answer and if that answer is incorrect we remove it, in other case we take another number. In the
     * end we will hide the button corresponding to the joker to show that the joker has been used.
     */
    public void removeWrong() {
        int removed = -1;
        int i = 0;
        while (i != 2) {
            int random = rand.nextInt(3);
            if (random != curr.getCorrectAnswer() && random != removed ) {
                removed = random;
                switch (random) {
                    case 0:
                        ans1.setVisible(false);
                        rad3.setDisable(true);
                        rad3.setVisible(false);
                        image1.setVisible(false);
                        i++;
                        break;
                    case 1:
                        ans2.setVisible(false);
                        rad2.setDisable(true);
                        rad2.setVisible(false);
                        image2.setVisible(false);
                        i++;
                        break;
                    case 2:
                        ans3.setVisible(false);
                        rad1.setDisable(true);
                        rad1.setVisible(false);
                        image3.setVisible(false);
                        i++;
                        break;
                    case 3:
                        ans4.setVisible(false);
                        rad4.setDisable(true);
                        rad4.setVisible(false);
                        image4.setVisible(false);
                        i++;
                        break;
                }
            }
        }
        eliminate.setVisible(false);
    }

    /**
     * addpoints5 is the method used to calculate and add the points to the player's score for the 5th type of question
     * (the open question). It is used a formula based on how far is the user's answer from the correct answer( a
     * maximum difference of 20): 10*(20-|x-y|), where x represents player's input and y represents the correct answer.
     * If the double points joker has been activated, then the player gets a double amount of points for that specific
     * round. Also this method calls the setwrongtrue method.
     * @param player is the user which has saved the score and the name
     */
    public void addpoints5(Player player) {
        int input = Integer.parseInt(anstext.getText());
        int correct = Integer.parseInt(correct_ans);
        int score = Integer.parseInt(points.getText());
        if(input-correct<=20&&input-correct>=0) {
            if(dbpoints.getText().equals("first")) {
                dbpoints.textProperty().setValue("used");
                score = Integer.parseInt(points.getText()) + 20 * (20 - input + correct);
            }
            else {
                score = Integer.parseInt(points.getText()) + 10 * (20 - input + correct);
            }
            setwrongtrue(true);
        }
        else if(correct-input<=20 && correct-input>=0) {
            if(dbpoints.getText().equals("first")) {
                dbpoints.textProperty().setValue("used");
                score = Integer.parseInt(points.getText()) + 20 * (20 - correct + input);
            }
            else {
                score = Integer.parseInt(points.getText()) + 10 * (20 - correct + input);
            }
            setwrongtrue(true);
        }
        else
            setwrongtrue(false);
        player.score = score;
        points.textProperty().setValue(score+"");
    }

    /**
     * getCurr is a method used to get the current question
     * @return the current questions
     */
    public Question getCurr() {
        return curr;
    }

    /**
     * setPoints is a method used to calculate and add the points to the player's score for the first 4 types of questions
     * (multiple choice questions).It is used a formula based on how much time has left: 10* timeleft. If the
     * double points joker has been activated, then the player gets a double amount of points for that specific round.
     * @param player is the user which has saved the score and the name
     */
    public void setPoints(Player player){
        long score;
        if(dbpoints.getText().equals("first")) {
            dbpoints.setText("used");
            score = Integer.parseInt(points.getText()) + 2 * timeCountdown * 10;
        }
        else {
            score = Integer.parseInt(points.getText()) + timeCountdown * 10;
        }
        player.score = (int)score;
        points.textProperty().setValue(score+"");
    }

    /**
     * removedouble is a method used to modify the message on the double points joker button which helps us to verify if
     * the button was already used
     */
    public void removedouble(){
        if(dbpoints.getText().equals("first"))
            dbpoints.setText("used");
    }

    public void init(MouseEvent mouseEvent) {

    }

    /**
     * getPlayer is a method used to return the player
     * @return the player who is actually playing the game, containing the name and score
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * ToggleGroup is a method used to return the ToggleGroup
     * @return the togglegroup containing the buttons
     */
    public ToggleGroup getGroup() {
        return group;
    }

    /**
     * getSubmit is a method used to return the submit button
     * @return the submit button
     */
    public Button getSubmit() {
        return submit;
    }

    /**
     * getAnstext is a method used to return the textfield anstext, which represents the textfield that contains the value
     * for the answer in 5th type of question
     * @return the anstext texfield
     */
    public TextField getAnstext() {
        return anstext;
    }

    /**
     * getTimer is a method used to return the Label containing the timer
     * @return the label timer
     */
    public Label getTimer() {
        return timer;
    }

    /**
     * getTimeBar is a method used to return the ProgressBar containing the timeBar
     * @return the ProgressBar for the timeBar
     */
    public ProgressBar getTimeBar(){return timeBar;}

    /**
     * load is a method used to create the toggle group, to create the set of questions for the game, initializing the
     * labels and going from question to question, verifying if there exists other questions. It sets the initial status
     * of the buttons for submit and jokers. Also, the method calls a method which shows the correct answer.
     * @param p is the user which has saved the score and the name
     */
    public void load(Player p) {
        player = p;
        rad1.setToggleGroup(group);
        rad2.setToggleGroup(group);
        rad3.setToggleGroup(group);
        rad4.setToggleGroup(group);
        anstext.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(anstext.getText().equals("")){
                    submit.setDisable(true);
                }
                else
                    submit.setDisable(false);
            }
        });

        rad1.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                submit.setDisable(false);
        });

        rad2.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                submit.setDisable(false);
        });

        rad3.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY)
                    submit.setDisable(false);
        });

        rad4.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                submit.setDisable(false);
        });

        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showCorrect();
                checkCorrect(player);
                eliminate.setDisable(true);
                dbpoints.setDisable(true);
                submit.setDisable(false);
            }
        });
        EventHandler<KeyEvent> event = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCharacter().equals(" ") && submit.isVisible()){
                    showCorrect();
                    checkCorrect(player);
                }
                if (!pane.isFocused()&&!anstext.isFocused()){
                    pane.requestFocus();
                }
            }
        };
        pane.addEventHandler(KeyEvent.ANY, event);
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Q:
                        if(eliminate.isVisible()){
                            removeWrong();
                        }
                        break;
                    case W:
                        if (dbpoints.isVisible()){
                            morep();
                        }
                        break;
                    case DIGIT1:
                        if (!rad3.isDisabled()) {
                            rad3.setSelected(true);
                            submit.setDisable(false);
                        }
                        break;
                    case DIGIT2:
                        if (!rad2.isDisabled()) {
                            rad2.setSelected(true);
                            submit.setDisable(false);
                        }
                        break;
                    case DIGIT3:
                        if (!rad1.isDisabled()) {
                            rad1.setSelected(true);
                            submit.setDisable(false);
                        }
                        break;
                    case DIGIT4:
                        if (!rad4.isDisabled()) {
                            rad4.setSelected(true);
                            submit.setDisable(false);
                        }
                        break;
                }
            }
        });

        for (int i = 0; i < 20; i++) {
            //decreaseTime(timeCountdown);
            Question q = new Question(server, rand);
            q.generateQuestion();
            questions.add(q);
        }
        User_name.textProperty().bind(new SimpleStringProperty(player.getName()));
        points.textProperty().setValue(player.getScore()+"");
//        points.textProperty().bind(new SimpleStringProperty(getPlayer().getScore()+""));
        it = questions.iterator();
        time = new SinglePlayerTimer(0.05, this);
        if (it.hasNext()) {
            curr = it.next();
            refreshQuestion();
            eliminate.setDisable(false);
            if(curr.getType() == 4)
                eliminate.setDisable(true);
            dbpoints.setDisable(false);
            time.start();
        }
    }

    /**
     * advanceNextQuestion is a method used to advance through the questions, verifying if there are other questions left.
     * If there aren't questions left, the leaderboard si displayed and timer is stopped.
     * @param timer
     */
    public void advanceNextQuestion(SinglePlayerTimer timer) {
        if (it.hasNext())
            {curr = it.next();
            }
        else {
            server.addPlayer(player);
            timer.stop();
            if (!mainCtrl.exitScreen()){
                mainCtrl.showLeaderboard(player);
            }
            else {
                mainCtrl.leaveQuestion(8,0, player);
            }
            onLeave();
        }
    }

    /**
     * hasNextQuestion is a method used to verify if there is one more question
     * @return true or false if there are questions left
     */
    public boolean hasNextQuestion() {
        return it.hasNext();
    }

    /**
     * refreshQuestions is a method used to modify the labels and buttons at the beginning of a new question, depending
     * on the type of that question.
     */
    public void refreshQuestion() {
        if(curr.getType() == 4){
            eliminate.setDisable(true);
        }
        else
            eliminate.setDisable(false);
        question.textProperty().bind(new SimpleStringProperty(curr.getQuestionText()));
        submit.setVisible(true);
        if(question_index==0)
            reset();
        question_index++;
        questioncounter.setText(question_index+"/20");
       // eliminate.setDisable(false);
        submit.setDisable(true);
        dbpoints.setDisable(false);
        //if(curr.getType() != 4)

        if (curr.getType() != 4) {
            imageEstimate.setVisible(false);
            imageGTE1.setVisible(false);
            imageGTE2.setVisible(false);
            group.getToggles().forEach(t -> ((RadioButton)t).setDisable(false));
            rad1.setVisible(true);
            rad2.setVisible(true);
            rad3.setVisible(true);
            rad4.setVisible(true);
            ans1.setVisible(true);
            ans2.setVisible(true);
            ans3.setVisible(true);
            ans4.setVisible(true);
            ans1.textProperty().bind(new SimpleStringProperty(curr.getAnswers().get(0)));
            ans2.textProperty().bind(new SimpleStringProperty(curr.getAnswers().get(1)));
            ans3.textProperty().bind(new SimpleStringProperty(curr.getAnswers().get(2)));
            ans4.textProperty().bind(new SimpleStringProperty(curr.getAnswers().get(3)));
            anstext.setVisible(false);
            cans.setVisible(false);
            group.getToggles().forEach(t -> t.setSelected(false));
            ans1.setTextFill(Color.web("000000"));
            ans2.setTextFill(Color.web("000000"));
            ans3.setTextFill(Color.web("000000"));
            ans4.setTextFill(Color.web("000000"));

            if(curr.getType() == 1) {
                image1.setVisible(false);
                image2.setVisible(false);
                image3.setVisible(false);
                image4.setVisible(false);
                imageEstimate.setVisible(false);
                imageGTE1.setVisible(true);
                imageGTE2.setVisible(true);
                setImages(5, curr.getPathList().get(0));
                setImages(6, curr.getPathList().get(0));
            }
            else{
                imageGTE1.setVisible(false);
                imageGTE2.setVisible(false);
                image1.setVisible(true);
                image2.setVisible(true);
                image3.setVisible(true);
                image4.setVisible(true);
                for(int i = 1; i <= 4; i++){
                    setImages(i, curr.getPathList().get(i-1));
                }
            }

        } else {
            image1.setVisible(false);
            image2.setVisible(false);
            image3.setVisible(false);
            image4.setVisible(false);
            imageEstimate.setVisible(true);
            imageGTE1.setVisible(false);
            imageGTE2.setVisible(false);
            setImages(7, curr.getPathList().get(0));
            if(!anstext.getText().equals(""))
                anstext.setText("");
            anstext.setVisible(true);
            cans.setVisible(false);
            rad1.setVisible(false);
            rad2.setVisible(false);
            rad3.setVisible(false);
            rad4.setVisible(false);
            ans1.setVisible(false);
            ans2.setVisible(false);
            ans3.setVisible(false);
            ans4.setVisible(false);
        }
    }

    public void openAnswer() {
        checkCorrect(player);
        showCorrect();
    }

    /**
     * checkCorrect is a method used to verify if the selected answer is correct, calling the method for the question
     * counter and also displays the correct answer for the 5th question type.
     * @param player is the user which has saved the score and the name
     */
    public void checkCorrect(Player player) {
        long correct = curr.getCorrectAnswer();
        if (curr.getType() != 4) {
            int selected = -1;
            if (group.getSelectedToggle() != null){
                if (group.getSelectedToggle().equals(rad1)) selected = 2;
                else if (group.getSelectedToggle().equals(rad2)) selected = 1;
                else if (group.getSelectedToggle().equals(rad3)) selected = 0;
                else if (group.getSelectedToggle().equals(rad4)) selected = 3;
            }
            if (correct == selected) {
                //method to increment score
                setwrongtrue(true);
                setPoints(player);
            }
            else{
                setwrongtrue(false);
                removedouble();
            }
        } else {
            correct_ans = curr.getAnswers().get(0);
            addpoints5(player);
        }
    }

    /**
     * showCorrect is a method used to show the correct answer for the first 4 types of questions, modifying the colour
     * for the correct answer or making visible the label that contains the correct answer for the 5th type.
     */
    public void showCorrect() {
        submit.setVisible(false);
        if (curr.getType() != 4) {
            group.getToggles().forEach(t -> ((RadioButton) t).setDisable(true));
            for (int i = 0; i < 4; i++) {
                if (i == curr.getCorrectAnswer()) {
                    switch (i) {
                        case 0:
                            ans1.setTextFill(Color.web("#61ab61"));
                            break;
                        case 1:
                            ans2.setTextFill(Color.web("#61ab61"));
                            break;
                        case 2:
                            ans3.setTextFill(Color.web("#61ab61"));
                            break;
                        case 3:
                            ans4.setTextFill(Color.web("#61ab61"));
                            break;
                    }
                }
            }
        } else {
            cans.textProperty().setValue("The correct answer is " + curr.getAnswers().get(0));
            cans.setVisible(true);
        }
    }

    /**
     * morep is a method that makes the dbpoints button invisible to show that it was used.
     */
    public void morep() {
        dbpoints.setVisible(false);
        dbpoints.setText("first");
    }

    /**
     * backToMenu is a method associated with the leave button and if the player press it, he will leave the game
     */
    public void backToMenu(){
        mainCtrl.leaveQuestion(0, 0);
    }

    /**
     * onLeave is a method used to initialize again the timers and labels after leaving the game.
     */
    public void onLeave(){
        dbpoints.setVisible(true);
        dbpoints.setText("Double points");
        eliminate.setVisible(true);
        questions=new ArrayList<>();
        time.stop();
        question_index=0;
        timeCountdown = 20;
    }

    /**
     * reset is a method used to make the labels containing the correctness of questions(true or false) empty at the
     * beginning of the game.
     */
    public void reset(){
        Label[] labels= new Label[]{box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,box13,box14,box15,
                box16,box17,box18,box19,box20};
        for(int i=0;i<20;i++) {
            labels[i].setText("-");
            labels[i].setTextFill(Color.web("#000000"));
        }
    }

    /**
     * setwrongtrue is a method used to put V(true) or X(false) depending on the answer for the last question in the
     * label corresponding to the index number of the question.
     * @param answer true or false based on the last answer
     */
    public void setwrongtrue(boolean answer){
        Label[] labels= new Label[]{box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,box13,box14,box15,
                box16,box17,box18,box19,box20};
        if(answer==true) {
            labels[question_index-1].setText(Emotion.CHECK.toString());
            labels[question_index-1].setTextFill(Color.web("#61ab61"));
        }
        else {
            labels[question_index-1].setText(Emotion.WRONG.toString());
            labels[question_index-1].setTextFill(Color.web("#e86464"));
        }
    }

    /**
     * decreasTime is a function used to update the timebar after each second
     * @param timeRemaining is the remaining time in the timer
     * @param progress is a timebar property
     */
    public void decreaseTime(long timeRemaining, double progress){
        if(timeRemaining == 21){
            timeBar.setStyle("-fx-accent: #a8cbe6;");}
        if(timeRemaining <= 20 && timeRemaining >= 15){
            timeBar.setStyle("-fx-accent: #61ab61;");}
        else if(timeRemaining < 15 && timeRemaining >= 10){
            timeBar.setStyle("-fx-accent: #98d48a;");}
        else if(timeRemaining < 10 && timeRemaining >= 8){
            timeBar.setStyle("-fx-accent: #fff194;");}
        else if (timeRemaining < 8 && timeRemaining >= 5){
            timeBar.setStyle("-fx-accent: #fcce6a;");}
        else if(timeRemaining < 5 && timeRemaining >= 2){
            timeBar.setStyle("-fx-accent: #fcad5b;");}
        else if(timeRemaining < 2 && timeRemaining >= 0){
            timeBar.setStyle("-fx-accent: #e86464;");}
        timeBar.setProgress(progress);
    }

    /**
     * setImages is a method used to systematically set the images in the corresponding imageview
     * @param button the imageview to be receiving the image
     * @param path the path to the image
     */
    public void setImages(Integer button, String path){
        Image image = getImageByPath(path);

        switch(button){
            case 1:
                this.image1.setImage(image);
                break;
            case 2:
                this.image2.setImage(image);
                break;
            case 3:
                this.image3.setImage(image);
                break;
            case 4:
                this.image4.setImage(image);
                break;
            case 5:
                this.imageGTE1.setImage(image);
                break;
            case 6:
                this.imageGTE2.setImage(image);
                break;
            case 7:
                this.imageEstimate.setImage(image);
        }
    }

    /**
     * getImageByPath is a method used to retrieve the image from the server
     * @param path the path to the image
     * @return the image
     */
    public Image getImageByPath(String path){
       // Image image = new Image("/");
        return server.getImageByPath(path);
    }
}
