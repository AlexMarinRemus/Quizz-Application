package client.scenes;

import client.utils.ServerUtils;
//import commons.Player;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;

public class ServerUtilsTest {

    ServerUtils util;

    @BeforeEach
    public void setup(){
        util = new ServerUtils();
    }

    //Server needs to be run for the test, idk how to implement that programmatically yet so it can't be tested
    //via gitlab pipeline
//    @Test
//    public void addAndDeletePlayerTest(){
//        Player p1 = new Player("testname", 2);
//        List<Player> plist = util.getPlayers();
//        assertFalse(plist.contains(p1));
//        p1 = util.addPlayer(p1);
//        plist = util.getPlayers();
//        assertTrue(plist.contains(p1));
//        boolean bol = util.deletePlayer(p1);
//        plist = util.getPlayers();
//        assertTrue(bol);
//        assertFalse(plist.contains(p1));
//    }

}
