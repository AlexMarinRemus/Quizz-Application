package client.scenes;

import client.utils.ServerUtils;

import javax.inject.Inject;

public class Helpbutton {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public Helpbutton(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * goback is a function accessed from the leave button which opens the splash screen
     */
    public void goback() {
        mainCtrl.showOverview();
    }
}
