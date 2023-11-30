package client.scenes;

import client.AnswerResponse;
import client.IRandom;
import client.MultiplayerGameManager;
import client.Question;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Multiplayer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiplayerScreen implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    Label timer;

    @FXML
    Button submit;

    @FXML
    private Label User;

    @FXML
    private Button smile;

    @FXML
    private Button heart;

    @FXML
    private Button angry;

    @FXML
    private Button sad;

    @FXML
    private Label player1;

    @FXML
    private Label emoji1;

    @FXML
    private Label player2;

    @FXML
    private Label emoji2;

    @FXML
    private Label player3;

    @FXML
    private Label emoji3;

    @FXML
    private Label player4;

    @FXML
    private Label emoji4;

    @FXML
    private Label player5;

    @FXML
    private Label emoji5;

    @FXML
    private Label player6;

    @FXML
    private Label emoji6;

    @FXML
    private Label player7;

    @FXML
    private Label emoji7;

    @FXML
    private Label player8;

    @FXML
    private Label emoji8;

    @FXML
    private Label player9;

    @FXML
    private Label emoji9;

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

//
//    @FXML
//    private Label User_name;

    @FXML
    private Button eliminate;

    @FXML
    private TextField anstext;

    @FXML
    private Button less;

    @FXML
    private ProgressBar timeBar;

    @FXML
    private Label question;

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

    private int question_index=0;
    private int lastAnswered = 0;

    private Question curr;
    private ToggleGroup group;
    //private int time=20;
    private Multiplayer player;
    @Inject
    private IRandom rand;
    private String correct_ans = "";
    private final ServerUtils server;
    private MultiplayerGameManager manager;
    private MultiplayerTimer mpTimer;
    List<Label> playersLabels = new ArrayList<>();
    private int opponentsNum = 0;
    private int playersAnswered = 0;
    private int playersLoaded = 0;
    private List<Multiplayer> players;

    @Inject
    public MultiplayerScreen (ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void init(MouseEvent mouseEvent) {
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*server.registerMessages("/topic/loaded", Boolean.class, b -> {
            playersLoaded++;
        });*/
        server.registerMessages("/topic/answer", AnswerResponse.class, r -> {

            if (players.contains(r.getPlayer())){
                for (int i = 0; i < playersLabels.size(); i++) {
                    if (playersLabels.get(i).getText().equals(r.getPlayer().getName())) {
                        playersLabels.get(i).setTextFill(r.isAnswer() ? Color.web("#61ab61") : Color.web("#e86464"));
                        playersAnswered++;
                    }
                }
            }
        });
        server.registerMessages("/topic/emojis", Multiplayer.class, r ->{
            if(players.contains(r)){
                Label[] emotions = {emoji1,emoji2,emoji3,emoji4,emoji5,emoji6,emoji7,emoji8,emoji9};
                Label[] users= {player1,player2,player3,player4,player5,player6,player7,player8,player9};
                Platform.runLater(() -> {
                    for(int i=0;i<9;i++){
                        String name=users[i].getText();
                        if(name.equals(r.getName())){
                            emotions[i].setText(r.getEmotion());
                        }
                    }
                });
            }
        });
        server.registerMessages("/topic/left", Multiplayer.class, p -> {
            if (players.contains(p)){
                for (int i = 0; i < opponentsNum; i++) {
                    if (playersLabels.get(i).getText().equals(p.getName())) {
                        playersLabels.get(i).setTextFill(Color.web("#ababab"));
                    }
                }
                if (!p.equals(player)) {
                    opponentsNum--;
                }
            }
        });
        server.registerMessages("/topic/lessTime", Multiplayer.class, r -> {
            if(players.contains(r)){
                if (!r.equals(player) && submit.isVisible()){
                    mpTimer.lessTime();
                }
            }
        });
    }

    /**
     * load is a method used to create the toggle group, to create the set of questions for the game, initializing the
     * labels and going from question to question, verifying if there exists other questions. It sets the initial status
     * of the buttons for submit and jokers. Also, the method calls a method which shows the correct answer.
     * @param pla is the user which has saved the score and the name
     * @param m this will handle all of the question generation / timing in a multiplayer game
     */
    public void load(Multiplayer pla, MultiplayerGameManager m) {
        /*server.send("/topic/loaded", true);
        if (playersLoaded < opponentsNum) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        manager = m;
        player = pla;
        manager.updateGameId();
        players = m.getPlayers();
        player.setGameID(m.getGameID());
        User.setText(pla.getName()+": ");
        hide();
        group = new ToggleGroup();
        rad1.setToggleGroup(group);
        rad2.setToggleGroup(group);
        rad3.setToggleGroup(group);
        rad4.setToggleGroup(group);
        curr = manager.getCurrent();
        refreshQuestion();
        showPlayers();

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
                if (curr.getType() != 4) {
                    showCorrect();
                    if (group.getSelectedToggle() != null) {
                        checkCorrect(player);
                    }
                } else {
                    showCorrect();
                    checkCorrect(player);
                }
                eliminate.setDisable(true);
               // less.setDisable(true);
                dbpoints.setDisable(true);
                submit.setDisable(false);
            }
        });
        mpTimer = new MultiplayerTimer(0.05, this);
        mpTimer.start();
    }



    /**
     * showPlayers is a method used to display the name of the players in the labels
     */
    public void showPlayers() {
        playersLabels.add(player1);
        playersLabels.add(player2);
        playersLabels.add(player3);
        playersLabels.add(player4);
        playersLabels.add(player5);
        playersLabels.add(player6);
        playersLabels.add(player7);
        playersLabels.add(player8);
        playersLabels.add(player9);
        playersLabels.forEach(label -> label.setTextFill(Color.web("000000")));
        int i = 0;
        int j = 0;
        for (; i < manager.getPlayers().size(); i++) {
            if (!manager.getPlayers().get(i).getName().equals(player.getName())) {
                playersLabels.get(j).setVisible(true);
                playersLabels.get(j).setText(manager.getPlayers().get(i).getName());
                opponentsNum++;
                j++;
            } else {
            }
        }
        while (j < 9) {
            playersLabels.get(j).setVisible(false);
            j++;
        }
        points.setText("0");
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
    public void addpoints5(Multiplayer player) {
        int input = Integer.parseInt(anstext.getText());
        int correct = Integer.parseInt(correct_ans);
        int score = Integer.parseInt(points.getText());
        if((input-correct)<=20 && (input-correct)>=0) {
            if(dbpoints.getText().equals("first")) {
                dbpoints.textProperty().setValue("used");
                score = Integer.parseInt(points.getText()) + 20 * (20 - input + correct);
            }
            else {
                score = Integer.parseInt(points.getText()) + 10 * (20 - input + correct);
            }
            setwrongtrue(true);
        }
        else if((correct-input)<=20 && (correct-input)>=0) {
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
        getPlayer().score = score;
        server.updateScore(player);
        server.getMultiplayersByGameID(manager.getGameID());
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
    public void setPoints(Multiplayer player){
        long score;
        if(dbpoints.getText().equals("first")) {
            dbpoints.setText("used");
            score = Integer.parseInt(points.getText()) + 2 * mpTimer.getTime() * 10;
        }
        else {
            score = Integer.parseInt(points.getText()) + mpTimer.getTime() * 10;
        }
        player.score = (int)score;
        server.updateScore(player);
        server.getMultiplayersByGameID(manager.getGameID());
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

    /**
     * getPlayer is a method used to return the player
     * @return the player who is actually playing the game, containing the name and score
     */
    public Multiplayer getPlayer() {
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

    public int getPlayersAnswered() {
        return playersAnswered;
    }

    public List<Label> getPlayersLabels() {
        return playersLabels;
    }

    public int getOpponentsNum() {
        return opponentsNum;
    }

    public ProgressBar getTimeBar(){return timeBar;}

    /**
     * refreshQuestions is a method used to modify the labels and buttons at the beginning of a new question, depending
     * on the type of that question.
     */
    public void refreshQuestion() {
        curr = manager.getCurrent();
        resetemoji();
        if (curr.getQuestionText().equals("Leaderboard")) {
            if(!mainCtrl.exitScreen()){
                mainCtrl.showmultiplayerLeaderboard(player);
            }
            else {
                mainCtrl.leaveQuestion(7,0, player);
            }
        } else {
            if(!mainCtrl.exitScreen()){
                mainCtrl.multiPlayerScreen();
            }
            else {
                mainCtrl.leaveQuestion(2,0);
            }

            if(curr.getType() == 4)
            eliminate.setDisable(true);
            else eliminate.setDisable(false);
                //eliminate.setDisable(false);
            less.setDisable(false);
            dbpoints.setDisable(false);
            submit.setDisable(true);

            question.textProperty().bind(new SimpleStringProperty(curr.getQuestionText()));
            submit.setVisible(true);

            for(Label l : playersLabels){
                if(!l.getTextFill().equals(Paint.valueOf("#ababab"))){
                    l.setTextFill(Color.web("000000"));
                }
            }

            playersAnswered = 0;
            if (question_index == 0)
                reset();

            if(question_index - lastAnswered == 3){
                leave();
                mainCtrl.showOverview();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You haven't answered the last 3 questions, you have been kicked out!\nPlease join the next game!");
                alert.show();
            }
            else{
                question_index++;
            }

            questioncounter.setText(question_index + "/20");
            if (curr.getType() != 4) {
                imageEstimate.setVisible(false);
                imageGTE1.setVisible(false);
                imageGTE2.setVisible(false);
                group.getToggles().forEach(t -> ((RadioButton) t).setDisable(false));
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
                if (!anstext.getText().equals(""))
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
    }

    public boolean isIntermediate(){
        if(question_index > 12){
            return false;
        }
        return true;
    }

    /**
     * checkCorrect is a method used to verify if the selected answer is correct, calling the method for the question
     * counter and also displays the correct answer for the 5th question type.
     * @param player is the user which has saved the score and the name
     */
    public void checkCorrect(Multiplayer player) {
        long correct = curr.getCorrectAnswer();
        if (curr.getType() != 4) {
            int selected = -1;
            if (group.getSelectedToggle().equals(rad1)) selected = 2;
            else if (group.getSelectedToggle().equals(rad2)) selected = 1;
            else if (group.getSelectedToggle().equals(rad3)) selected = 0;
            else if (group.getSelectedToggle().equals(rad4)) selected = 3;
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
            cans.textProperty().setValue("The correct answer is " +curr.getAnswers().get(0));
            cans.setVisible(true);
        }
    }

    /**
     * morep is a method that makes the dbpoints button invisible to show that it was used.
     */
    public void morep(ActionEvent event) {
        dbpoints.setVisible(false);
        dbpoints.setText("first");
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
        lastAnswered = question_index;
        if(answer==true) {
            AnswerResponse ar = new AnswerResponse(player);
            ar.setAnswer(true);
            server.send("/topic/answer", ar);
            labels[question_index-1].setText(Emotion.CHECK.toString());
            labels[question_index-1].setTextFill(Color.web("#61ab61"));
        }
        else {
            AnswerResponse ar = new AnswerResponse(player);
            ar.setAnswer(false);
            server.send("/topic/answer", ar);
            labels[question_index-1].setText(Emotion.WRONG.toString());
            labels[question_index-1].setTextFill(Color.web("#e86464"));
        }
    }

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
     * getManager() is a method used to return the Manager of the multiplayer game
     * @return a manager which will handle all of the question generation / timing in a multiplayer game
     */
    public MultiplayerGameManager getManager() {
        return manager;
    }

    /**
     * backToMenu is a method associated with the leave button and if the player press it, he will leave the game
     */
    public void backToMenu(){
        mainCtrl.leaveQuestion(2, 0);
    }

    /**
     * Resets the game settings to the original state
     */
    public void resetGameSetting() {
        dbpoints.setVisible(true);
        dbpoints.setText("Double points");
        eliminate.setVisible(true);
        less.setVisible(true);
        correct_ans = "";
        playersLabels = new ArrayList<>();
        //timeCountdown = 20;
        opponentsNum = 0;
        playersAnswered = 0;
        playersLoaded = 0;
        question_index=0;
        lastAnswered = 0;
        mpTimer.stop();
        playersLabels.forEach(label -> label.setTextFill(Color.web("000000")));
        players = new ArrayList<>();
        reset();
        resetemoji();
    }

    /**
     *leave is a method used for what happens when user leaves the game
     */
    public void leave() {
        server.send("/topic/left", player);
        resetGameSetting();
    }

    /**
     * lesstime is a method used for the less time joker. When the joker is used it send a message and makes the joker invisible
     */
    public void lesstime() {
        server.send("/topic/lessTime", player);
        less.setVisible(false);

    }

    /**
     * showemotions is the method associated with the emoji button, such that if you press it, then will appear 4 buttons
     * with 4 emojis on them
     */
    public void showemotions() {
        smile.setText(Emotion.HAPPY.toString());
        sad.setText(Emotion.SAD.toString());
        heart.setText(Emotion.HEART.toString());
        angry.setText(Emotion.ANGRY.toString());
        smile.setVisible(true);
        sad.setVisible(true);
        heart.setVisible(true);
        angry.setVisible(true);
    }

    /**
     * hide is a method used for making the buttons invisible after pressing the button associated with one of the emojis
     */
    public void hide(){
        smile.setVisible(false);
        sad.setVisible(false);
        heart.setVisible(false);
        angry.setVisible(false);
    }

    /**
     * smileeffect is a method associated with the button which has displayed on the smile emoji, making visible for
     * others that emoji
     */
    public void smileeffect() {
        getPlayer().emotion=Emotion.HAPPY.toString();
        server.send("/app/emojis",getPlayer());
        hide();
    }

    /**
     * hearteffect is a method associated with the button which has displayed on the heart emoji, making visible for
     * others that emoji
     */
    public void hearteffect() {
        getPlayer().emotion=(Emotion.HEART.toString());
        server.send("/app/emojis",getPlayer());
        hide();
    }

    /**
     * angryeffect is a method associated with the button which has displayed on the angry emoji, making visible for
     * others that emoji
     */
    public void angryeffect() {
        getPlayer().emotion=(Emotion.ANGRY.toString());
        server.send("/app/emojis",getPlayer());
        hide();
    }

    /**
     * sadeffect is a method associated with the button which has displayed on the sad emoji, making visible for
     * others that emoji
     */
    public void sadeffect() {
        getPlayer().emotion=(Emotion.SAD.toString());
        server.send("/app/emojis",getPlayer());
        hide();
    }

    /**
     * resetemoji is a method used to refresh the labels containing the emojis after each question
     */
    public void resetemoji(){
        Label[] emotions = {emoji1,emoji2,emoji3,emoji4,emoji5,emoji6,emoji7,emoji8,emoji9};
        for(int i=0;i<9;i++)
            emotions[i].setText("");
    }
    
    /**
     * Disable method for all the joker buttons after all the players have submitted their answers
     */
    public void disableAllJokers(){
        dbpoints.setDisable(true);
        eliminate.setDisable(true);
        less.setDisable(true);
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
        return server.getImageByPath(path);
    }
}
