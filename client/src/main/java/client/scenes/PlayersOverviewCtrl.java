package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayersOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Player> data;

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> colName;
    @FXML
    private TableColumn<Player, Integer> colScore;

    @Inject
    public PlayersOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * here we set the properties of the table and await for new players always so that they are shown
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name));
        colScore.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().score).asObject());

        server.registerForUpdatesPlayers(p -> {
            data.add(p);
        });
    }

    public void addPlayer() {
        mainCtrl.showAddPlayer();
    }

    /**
     * we fetch all players from the server again and update the database
     * used through long-polling
     */
    public void refresh() {
        var players = server.getPlayers();
        data = FXCollections.observableList(players);
        table.setItems(data);
    }

    /**
     * stops the server
     */
    public void stop() {
        server.stop();
    }
}
