package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testNotNull(){
        Player p = new Player();
        assertNotNull(p);
        assertNotNull(p.name);
        assertNotNull(p.id);
    }

    @Test
    public void testEmptyConstructor(){
        Player p = new Player();
        assertEquals("", p.name);
        assertEquals(0, p.score);
    }

    @Test
    public void testNameConstructor(){
        Player p = new Player("Name");
        assertEquals("Name", p.name);
        assertEquals(0, p.score);
    }

    @Test
    public void testFullConstructor(){
        Player p = new Player("Name", 5);
        assertEquals("Name", p.name);
        assertEquals(5, p.score);
    }

    @Test
    public void testGetters(){
        Player p = new Player("Name", 5);
        assertEquals("Name", p.getName());
        assertEquals(5, p.getScore());
    }

    @Test
    public void testIncrement(){
        Player p = new Player("Name", 5);
        assertEquals(5, p.score);
        p.increment();
        assertEquals(6, p.score);
    }

    @Test
    public void testEquals(){
        Player p1 = new Player("Name", 5);
        Player p2 = new Player("Name", 5);
        Player p3 = new Player("Name", 6);
        Player p4 = new Player("OtherName", 5);
        assertEquals(p1, p2);
        assertEquals(p1, p1);
        assertNotEquals(p2, p3);
        assertNotEquals(p2, p4);
    }

    @Test
    public void testToString(){
        Player p = new Player("Name", 5);
        assertTrue(p.toString().contains("name=Name"));
        assertTrue(p.toString().contains("score=5"));
        assertTrue(p.toString().contains("id="+p.id));
    }


}
