package server.api;

import commons.Multiplayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MultiplayerControllerTest {

    private TestMultiplayerRepository repo;
    private MultiplayerController sut;

    @BeforeEach
    public void setup() {
        repo = new TestMultiplayerRepository();
        sut = new MultiplayerController(repo);
    }

    @Test
    public void cannotAddNullMultiplayer() {
        var actual = sut.add(new Multiplayer(null, 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddEmptyName() {
        var actual = sut.add(new Multiplayer("", 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotGetNegId() {
        var actual = sut.getById(-2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        sut.add(new Multiplayer("name", 0));
        repo.calledMethods.contains("save");
    }

    @Test
    public void idIsRetrieved() {
        sut.getById(2);
        repo.calledMethods.contains("getById");
    }

    @Test
    public void getByIdTest() {
        Multiplayer p = new Multiplayer("anna", 3);
        sut.add(p);
        var actual = sut.getById(p.getId());
        assertEquals(p, actual.getBody());
    }

    @Test
    public void getAllTest() {
        Multiplayer p1 = new Multiplayer("anna", 3);
        Multiplayer p2 = new Multiplayer("bob", 2);
        sut.add(p1);
        sut.add(p2);
        List<Multiplayer> list = sut.getAll();
        assertEquals(2, list.size());
        assertTrue(list.contains(p1));
        assertTrue(list.contains(p2));
    }

    @Test
    public void addTest() {
        List<Multiplayer> list = sut.getAll();
        Multiplayer p = new Multiplayer("phillis", 15);
        assertFalse(list.contains(p));
        sut.add(p);
        assertTrue(list.contains(p));
    }

    @Test
    public void deleteAllPlayers() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        List<Multiplayer> list = sut.getAll();
        assertEquals(3, list.size());
        sut.deleteAllMultiplayers();
        list = sut.getAll();
        assertEquals(0, list.size());
    }

    @Test
    public void deletePlayerTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        List<Multiplayer> list = sut.getAll();
        assertEquals(3, list.size());
        sut.deleteMultiplayer(p1.id);
        list = sut.getAll();
        assertEquals(2, list.size());
        sut.deleteMultiplayer(p3.id);
        list = sut.getAll();
        assertEquals(1, list.size());
        assertTrue(list.contains(p2));
    }

    @Test
    public void addPlayerTest() {
        List<Multiplayer> list = sut.getAll();
        Multiplayer p = new Multiplayer("phillis", 15);
        assertFalse(list.contains(p));
        var expectedMultiplayer = sut.addPlayer(p);
        assertTrue(list.contains(p));
        assertEquals(expectedMultiplayer, p);
    }

    @Test
    public void getMultiplayersByGameIdTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        List<Multiplayer> list = sut.getAll();
        List <Multiplayer> playersFound = sut.getMultiplayersByGameId(p1.gameID).getBody();
        for (Multiplayer p: list) {
            if (p.getGameID() == p1.getGameID()) {
                assertTrue(playersFound.contains(p));
            }
        }
    }

    @Test
    public void setMultiplayersToGameIdTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        sut.setMultiplayersToGameId();
        assertTrue(sut.getById(p1.id).getBody().gameID == 1);
        assertTrue(sut.getById(p2.id).getBody().gameID == 1);
        assertTrue(sut.getById(p3.id).getBody().gameID == 1);
        Multiplayer p4 = new Multiplayer("phillis", 15);
        Multiplayer p5 = new Multiplayer("joe", 3);
        Multiplayer p6 = new Multiplayer("bart", 200);
        sut.add(p4);
        sut.add(p5);
        sut.add(p6);
        sut.setMultiplayersToGameId();
        assertEquals(sut.getById(p1.id).getBody().gameID, 1);
        assertEquals(sut.getById(p2.id).getBody().gameID,1);
        assertEquals(sut.getById(p3.id).getBody().gameID, 1);
        assertEquals(sut.getById(p4.id).getBody().gameID, 2);
        assertEquals(sut.getById(p5.id).getBody().gameID, 2);
        assertEquals(sut.getById(p6.id).getBody().gameID, 2);
    }

    @Test
    public void updateTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        p2.score = 70;
        p2.name = "jill";
        sut.update(p2);
        assertEquals(sut.getById(p2.id).getBody().name, p2.name);
        assertEquals(sut.getById(p2.id).getBody().score, p2.score);
    }

    @Test
    public void updateEmptyTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        p2.score = 70;
        p2.name = "";
        var actual = sut.update(p2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void updateNullTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        Multiplayer p2 = new Multiplayer("joe", 3);
        Multiplayer p3 = new Multiplayer("bart", 200);
        sut.add(p1);
        sut.add(p2);
        sut.add(p3);
        p2.score = 70;
        p2.name = null;
        var actual = sut.update(p2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void sendTest() {
        Multiplayer p1 = new Multiplayer("phillis", 15);
        assertEquals(p1, sut.send(p1));
    }

}
