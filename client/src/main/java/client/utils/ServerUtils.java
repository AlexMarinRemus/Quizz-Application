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
package client.utils;

import commons.Activity;
import commons.Multiplayer;
import commons.Player;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import javafx.scene.image.Image;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils implements IServerUtils{

    private static String defaultServer = "http://localhost:8080/";
    private static String SERVER = defaultServer;
    private static String ws = "ws://localhost:8080/websocket";

    /**
     * Default constructor for serverutils
     */
    public ServerUtils() {
    }

    /**
     * Sets server to default server (port 8080)
     */
    public void setToDefaultServer() {
        SERVER = defaultServer;
    }

    /**
     * Sets server to given URL
     * @param port the port that the server is turned to
     */
    public void setUrl (String port){
        SERVER = "http://localhost:"+port+"/";
    }

    /**
     * Backup method for getting quotes
     * @throws IOException
     */
    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Accesses the get mapping at the localhost:PORT/api/players path, which finds all of the player records in the
     * player repository
     * @return the list of players stored in the player repository
     */
    public List<Player> getPlayers() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/players") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Player>>() {});
    }

    /**
     * Accesses the put mapping at the localhost:PORT/api/players path, which adds a player to the player repository
     * @param player the singleplayer to be added to the player repository
     * @return the Player object that was stored in the database. Note: This Player object has a different id from
     * the player param, so in order to access the correct player in future, the returned object's id must be used.
     */
    public Player addPlayer(Player player) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/players") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON), Player.class);
    }

    /**
     * Accesses the delete mapping at the localhost:PORT/api/players/delete/ID path, deleting the player record
     * from the player repository corresponding to the specified Player object
     * @param player the Player object to be deleted from the player repository
     * @return true if action was successful, false otherwise
     */
    public boolean deletePlayer(Player player) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/players/delete/" + player.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Boolean.class);
    }

    /**
     * Access the get mapping at the localhost:PORT/api/players/count path, counting the number of entries
     * in the activities repository
     * @return the number of activities in the activities repository
     */
    public Integer getCount() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/count") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Integer>() {});
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * Long polling method that polls the player repository for any updates to it, and then adding the Player object
     * to the consumer
     * @param consumer
     */
    public void registerForUpdatesPlayers (Consumer <Player> consumer) {
        EXEC.submit(() -> {
            while(!Thread.interrupted()) {
                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("api/players/updates/players")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);
                if (res.getStatus() == 204) {
                    continue;
                }
                var p = res.readEntity(Player.class);
                consumer.accept(p);
            }
        });
    }

    /**
     * Stops the long polling method above
     */
    public void stop() {
        EXEC.shutdown();
        try {
            if (!EXEC.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                EXEC.shutdownNow();
            }
            EXEC.shutdownNow();
        } catch (InterruptedException e) {
            EXEC.shutdownNow();
        }
    }

    /**
     * Accesses the get mapping at the localhost:PORT/api/activities path, which finds all of the
     * activity records in the activities repository
     * @return the list of activities stored in the player repository
     */
    @Override
    public List<Activity> getActivities(){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Activity>>() {});
    }

    /**
     * Accesses the get mapping at the localhost:PORT/api/multiplayers path,
     * which finds all of the multiplayer records in the multiplayer repository
     * @return the list of multiplayers stored in the repository
     */
    public List<Multiplayer> getMultiplayers() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Multiplayer>>() {});
    }

    /**
     * Accesses the get mapping at the localhost:PORT/api/multiplayers/game/start, which takes
     * all players in the waiting room and changes their gameID to a new generated ID
     * @return the gameid of the new game
     */
    public Integer startNewGame() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers/game/start") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Integer>() {});
    }

    /**
     * Accesses the get mapping at the localhost:PORT/api/multiplayers path, which finds all of the multiplayer
     * records in the multiplayer repository that are in a certain game
     * @param id the gameid of the game to be accessed
     * @return the list of multiplayers in that game
     */
    public List<Multiplayer> getMultiplayersByGameID(long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers/game/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Multiplayer>>() {});
    }

    /**
     * Accesses the post mapping at the localhost:PORT/api/multiplayers/update/ID path, which updates
     * the record of a specified multiplayer in the multiplayers repository
     * @param multiplayer the Multiplayer object that is to be replaced
     * @return the Multiplayer object that has been replaced in the repository
     */
    public Multiplayer updateScore(Multiplayer multiplayer) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers/update") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(multiplayer, APPLICATION_JSON), Multiplayer.class);
    }

    /**
     * Accesses the put mapping at the localhost:PORT/api/multiplayers path, which adds a multiplayer to the multiplayer repository
     * @param multiplayer the multiplayer to be added to the player repository
     * @return the Multiplayer object that was stored in the database. Note: This object has a different id from
     * the param, so in order to access the correct multiplayer in future, the returned object's id must be used.
     */
    public Multiplayer addMultiplayer(Multiplayer multiplayer) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(multiplayer, APPLICATION_JSON), Multiplayer.class);
    }

    /**
     * Accesses the delete mapping at the localhost:PORT/api/multiplayers/deleteall path, deleting all of the
     * records in the multiplayer repository
     * @return true if action was successful, false otherwise
     */
    public boolean deleteMultiplayers() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers/deleteall") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Boolean.class);
    }

    /**
     * Accesses the delete mapping at the localhost:PORT/api/multiplayers/delete/ID path, deleting the multiplayer record
     * from the player repository corresponding to the specified id
     * @param id the id corresponding to the Multiplayer object to be deleted
     * @return true if action was successful, false otherwise
     */
    public boolean deleteMultiplayer(long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayers/delete/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Boolean.class);
    }

    /**
     * Accesses the get mapping at the localhost:PORT/api/activities/images/GROUPNUMBER/FILE, retrieving the generated
     * byte array and turning it into an image.
     * @param path the path to the image
     * @return the image
     */
    public Image getImageByPath(String path){
        //System.out.println(activity.getImage_path());
        return new Image(
                new ByteArrayInputStream(
                        ClientBuilder.newClient(new ClientConfig())
                                .target(SERVER).path("api/activities/" + path)
                                .request(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .get(byte[].class)
                )
        );
    }


    // websocket utils

    private StompSession session = connect(ws);

    /**
     * Initializes and connects the websockets to the front end
     * @param url the url that the websockets connect to
     * @return the StompSession that the websockets use
     */
    private StompSession connect(String url){
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try{
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        catch(ExecutionException e){
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException();
    }

    /**
     * Method that registers for messages from a certain destination and applies a consumer
     * @param dest the path for the destination of the registration
     * @param type the Class of the object being sent through the websocket
     * @param consumer the consumer handling the object from the websocket
     * @param <T>
     */
    public <T> void registerMessages(String dest, Class<T> type, Consumer<T> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println(payload);// TO DELETE
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Refreshes session connection of a specified url
     * @param url the url to be connected to upon refresh
     */
    public void refreshConnection(String url){
        session.disconnect();
        try {
            session = connect(url);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    /**
     * Method to send message containing an object to a specified destination
     * @param dest the destination the message is sent to
     * @param o the object being sent
     */
    public void send(String dest, Object o){
        session.send(dest, o);
    }

}