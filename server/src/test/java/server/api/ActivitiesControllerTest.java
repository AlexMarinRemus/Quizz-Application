package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ActivitiesControllerTest {

    private TestActivitiesRepository repo;
    private ActivitiesController sut;


    @BeforeEach
    public void setup() {
        repo = new TestActivitiesRepository();
        sut = new ActivitiesController(repo);

        repo.add(new Activity("Driving an electric car for 1 kilometer", 200, "https://www.virta.global/blog/ev-charging-101-how-much-electricity-does-an-electric-car-use"));
        repo.add(new Activity("Using a light bulb for 3 hours", 180, "https://blog.arcadia.com/electricity-costs-10-key-household-products/"));
    }

//    @AfterEach
//    public void end(){
//        repo.deleteAll();
//    }

    @Test
    public void cannotGetNegId() {
        var actual = sut.getById(-2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void idIsRetrieved() {
        sut.getById(2);
        repo.calledMethods.contains("getById");
    }

    @Test
    public void consumptionIsRetrieved() {
        sut.getByConsumption(200);
        repo.calledMethods.contains("getByConsumption");
    }

    @Test
    public void getByNegConsumption() {
        var actual = sut.getByConsumption(-2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void getByIdTest() {
        var actual = sut.getById(2);
        assertEquals("Using a light bulb for 3 hours", actual.getBody().title);
        assertEquals(2, actual.getBody().id);
        assertEquals(180, actual.getBody().consumption_in_wh);
    }

    @Test
    public void getByIdTestInvalid() {
        var actual = sut.getById(200);
        assertNull(actual.getBody());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void getByConsumptionTest() {
        var actual = sut.getByConsumption(180);
        assertEquals(1, actual.getBody().size());
        assertEquals("Using a light bulb for 3 hours", actual.getBody().get(0).title);
        assertEquals(2, actual.getBody().get(0).id);
        assertEquals(180, actual.getBody().get(0).consumption_in_wh);

        actual = sut.getByConsumption(200);
        assertEquals(1, actual.getBody().size());
        assertEquals("Driving an electric car for 1 kilometer", actual.getBody().get(0).title);
        assertEquals(1, actual.getBody().get(0).id);
        assertEquals(200, actual.getBody().get(0).consumption_in_wh);
    }

    @Test
    public void getByConsumptionTestInvalid() {
        var actuals = sut.getByConsumption(500);
        assertNotNull(actuals.getBody());
        assertEquals(0, actuals.getBody().size());
    }

}
