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
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import client.scenes.AddPlayerCtrl;
import client.scenes.PlayersOverviewCtrl;
import client.scenes.SinglePlayer;
import client.scenes.SplashScreen;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Injector;

//import client.scenes.AddQuoteCtrl;
//import client.scenes.QuoteOverviewCtrl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    public ServerUtils server;

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * Run at the beginning of the launching of a client-side application, this code initializes and loads all screens,
     * as well as setting up the injectors, and adding them all to the primary stage
     * @param primaryStage the stage that displays the application, that all screens are linked to
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        //how to set up a Question with dependency injection, use it's generating method
        Question q = INJECTOR.getInstance(Question.class);
        q.generateQuestion();
        q.generateQuestion();

        var overviewPlayer = FXML.load(PlayersOverviewCtrl.class, "client", "scenes", "PlayersOverview.fxml");
        var addPlayer = FXML.load(AddPlayerCtrl.class, "client", "scenes", "AddPlayer.fxml");
        var splash = FXML.load(SplashScreen.class, "client", "scenes", "splash_screen.fxml");
        var single = FXML.load(SinglePlayer.class, "client", "scenes", "singleplayer_screen.fxml");
        var multi = FXML.load(MultiplayerScreen.class, "client", "scenes", "multiplayer_screen.fxml");
        var wait = FXML.load(WaitingroomScene.class, "client", "scenes", "/waitingroom_scene.fxml");
        var help =FXML.load(Helpbutton.class,"client", "scenes", "helpbutton.fxml");
        var leaderboard = FXML.load(Leaderboard.class,"client", "scenes", "leaderboard_screen.fxml");
        var multiplayerLeaderboard = FXML.load(MultiplayerLeaderboard.class, "client", "scenes", "multiplayerLeaderboardScreen.fxml");
        var leave = FXML.load(LeaveGame.class,"client","scenes","leavebutton.fxml");

        var sp = single.getKey();

        INJECTOR.injectMembers(single.getKey());
        server = INJECTOR.getInstance(ServerUtils.class);
        var secondaryStage = new Stage();
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, secondaryStage, overviewPlayer, addPlayer, splash, single, multi, wait, help, leaderboard, multiplayerLeaderboard, leave);

        secondaryStage.setOnCloseRequest(e -> {
            overviewPlayer.getKey().stop();
        });
    }
}
