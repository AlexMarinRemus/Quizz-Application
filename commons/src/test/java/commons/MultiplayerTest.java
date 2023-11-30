package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplayerTest {

    @Test
    public void testNull(){
        Multiplayer m = new Multiplayer();
        assertNotNull(m);
        assertNotNull(m.id);
        assertNotNull(m.name);
        assertNotNull(m.score);
        assertNotNull(m.emotion);
    }

    @Test
    public void testEmptyConstructor(){
        Multiplayer m = new Multiplayer();
        assertEquals("",m.name);
        assertEquals(0, m.score);
        assertEquals("",m.emotion);
    }

    @Test
    public void testNameConstructor(){
        Multiplayer m = new Multiplayer("testname");
        assertEquals("testname",m.name);
        assertEquals(0, m.score);
        assertEquals("",m.emotion);
    }

    @Test
    public void testFullConstructor(){
        Multiplayer m = new Multiplayer("testname", 3);
        assertEquals("testname",m.name);
        assertEquals(3, m.score);
        assertEquals("",m.emotion);
    }

    @Test
    public void testGetters(){
        Multiplayer m = new Multiplayer("testname", 3);
        assertEquals("testname",m.getName());
        assertEquals(3, m.getScore());
        assertEquals("",m.getEmotion());
        assertEquals(m.id, m.getId());
    }

    @Test
    public void testIncrement(){
        Multiplayer m = new Multiplayer("anna", 3);
        assertEquals(3, m.getScore());
        m.increment();
        assertEquals(4, m.getScore());
    }

    @Test
    public void testEquals(){
        Multiplayer m1 = new Multiplayer("anna", 3);
        Multiplayer m2 = new Multiplayer("anna", 3);
        Multiplayer m3 = new Multiplayer("bob", 3);
        Multiplayer m4 = new Multiplayer("anna", 4);
        assertEquals(m1, m1);
        assertEquals(m1,m2);
        assertNotEquals(m1,m3);
        assertEquals(m1,m4);
    }

    @Test
    public void testToString(){
        Multiplayer m1 = new Multiplayer("anna", 3);
        assertTrue(m1.toString().contains("name=anna"));
        assertTrue(m1.toString().contains("score=3"));
        assertTrue(m1.toString().contains("emotion="));
        assertTrue(m1.toString().contains("id="+m1.id));
    }

}
