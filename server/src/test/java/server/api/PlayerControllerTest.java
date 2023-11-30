package server.api;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PlayerControllerTest {

    private TestPlayerRepository repo;
    private PlayerController sut;

    @BeforeEach
    public void setup() {
        repo = new TestPlayerRepository();
        sut = new PlayerController(repo);
    }

    @Test
    public void cannotAddNullPlayer() {
        var actual = sut.add(getPlayer(null, 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddEmptyName() {
        var actual = sut.add(getPlayer("", 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotGetNegId() {
        var actual = sut.getById(-2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        sut.add(getPlayer("name", 0));
        repo.calledMethods.contains("save");
    }

    @Test
    public void idIsRetrieved() {
        sut.getById(2);
        repo.calledMethods.contains("getById");
    }

    @Test
    public void getByIdTest() {
        Player p = getPlayer("anna", 3);
        sut.add(p);
        var actual = sut.getById(p.getId());
        assertEquals(p, actual.getBody());
    }

    @Test
    public void getAllTest() {
        Player p1 = getPlayer("anna", 3);
        Player p2 = getPlayer("bob", 2);
        sut.add(p1);
        sut.add(p2);
        List<Player> list = sut.getAll();
        assertEquals(2, list.size());
        assertTrue(list.contains(p1));
        assertTrue(list.contains(p2));
    }

    @Test
    public void addTest() {
        List<Player> list = sut.getAll();
        Player p = new Player("phillis", 15);
        assertFalse(list.contains(p));
        sut.add(p);
        assertTrue(list.contains(p));
    }
    public void delete() {
        Player p = getPlayer("anna", 3);
        sut.add(p);
        assertEquals(p, sut.getById(p.getId()).getBody());
        boolean delete = sut.deletePlayer(p.getId());
        assertTrue(delete);
        var actual = sut.getById(p.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        delete = sut.deletePlayer(p.getId());
        assertFalse(delete);
    }


    private static Player getPlayer(String name, int score) {
        return new Player(name, score);
    }

}
