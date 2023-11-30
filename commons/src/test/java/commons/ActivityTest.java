package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    @Test
    public void testNull(){
        Activity a = new Activity();
        assertNotNull(a);
        assertNotNull(a.id);
        assertNotNull(a.title);
        assertNotNull(a.consumption_in_wh);
    }

    @Test
    public void emptyConstructor(){
        Activity a = new Activity();
        assertEquals(0, a.consumption_in_wh);
        assertEquals("", a.title);
        assertEquals("", a.source);
    }

    @Test
    public void fullConstructor(){
        Activity a = new Activity("title", 1, "source");
        assertEquals(1, a.consumption_in_wh);
        assertEquals("title", a.title);
        assertEquals("source", a.source);
    }

    @Test
    public void testGetters(){
        Activity a = new Activity("title", 1, "source");
        assertEquals(1, a.getConsumption_in_wh());
        assertEquals("title", a.getTitle());
    }

    @Test
    public void testID(){
        Activity.idgen = 1;
        Activity a1 = new Activity("title", 1, "source");
        Activity a2 = new Activity("title", 1, "source");
        Activity a3 = new Activity("title", 1, "source");
        assertEquals(1, a1.id);
        assertEquals(1, a1.getId());
        assertEquals(2, a2.id);
        assertEquals(2, a2.getId());
        assertEquals(3, a3.id);
        assertEquals(3, a3.getId());
    }

    @Test
    public void testEquals(){
        Activity a1 = new Activity("title", 1, "source");
        Activity a2 = new Activity("title", 1, "source");
        Activity a3 = new Activity("Other title", 1, "source");
        Activity a4 = new Activity("title", 2, "source");
        Activity a5 = new Activity("title", 1, "Other source");
        assertEquals(a1, a2);
        assertEquals(a1, a1);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);
        assertEquals(a1, a5); // source doesnt influence equality
    }

    @Test
    public void testToString(){
        Activity a1 = new Activity("title", 1, "source");
        assertTrue( a1.toString().contains("consumption_in_wh=1"));
        assertTrue( a1.toString().contains("title=title"));
        assertTrue( a1.toString().contains("source=source"));
        assertTrue( a1.toString().contains("id=" + a1.getId()));
    }

}
