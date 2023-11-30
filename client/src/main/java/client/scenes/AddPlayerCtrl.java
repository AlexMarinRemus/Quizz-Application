package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AddPlayerCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField name;

    @FXML
    private TextField score;

    @Inject
    public AddPlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * we clear the fields and show the overview screen
     */
    public void cancel() {
        clearFields();
        mainCtrl.showPlayersOverview();
    }

    /**
     * we check to see if we entered the details for a new player right
     */
    public void ok() {
        try {
            server.addPlayer(getPlayer());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showPlayersOverview();
    }

    /**
     * creates a player from the entered score and name
     * @return the new player
     */
    private Player getPlayer() {
        var n = name.getText();
        var s = Integer.parseInt(score.getText());
        return new Player(n, s);
    }

    /**
     * clears the name and score fields
     */
    private void clearFields() {
        name.clear();
        score.clear();
    }

    /**
     * if enter - check it, otherwise cancel action
     * @param e the event that got registered with the pressing of the key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
}
