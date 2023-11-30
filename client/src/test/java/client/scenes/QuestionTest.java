package client.scenes;

import client.Question;
import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionTest {

    private TestRandom rand;
    private TestServerUtils server;

    @BeforeEach
    void setUp() {
        rand = new TestRandom();
        server = new TestServerUtils();
    }

    @Test
    void notInRange() {
        Question question = new Question(server, rand);
        Activity a = server.getActivities().get(1);
        List<Activity> activities = server.getActivities();
        int range = 100;
        assertTrue(question.NotInRange(activities, a, range));
    }


    @Test
    void inRangeCheck() {
        Question question = new Question(server, rand);
        Activity a = server.getActivities().get(5);
        Activity b = server.getActivities().get(6);
        List<Activity> activities = server.getActivities();
        int range = 100;
        assertTrue(question.InRangeCheck(activities, a, b, range));
    }

    @Test
    void inRangeGet() {
        Question question = new Question(server, rand);
        Activity a = server.getActivities().get(5);
        List<Activity> activities = server.getActivities();
        int range = 100;
        List<Activity> activitiesReturned = question.inRangeGet(activities, a, range);
        assertEquals("G", activitiesReturned.get(0).getTitle());
    }

    @Test
    void inRange() {
    }

    @Test
    void getQuestionText() {
        Question question = new Question(server, rand);
        assertEquals("",question.getQuestionText());
    }

    @Test
    void getType() {
        Question question = new Question(server, rand);
        assertEquals(-1,question.getType());
    }

    @Test
    void getAnswers() {
        Question question = new Question(server, rand);
        assertNotNull(question.getAnswers());
    }

    @Test
    void getCorrectAnswer() {
        Question question = new Question(server, rand);
        assertEquals(-1,question.getCorrectAnswer());
    }

    @Test
    void generateQuestion() {
    }

    @Test
    void estimate() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        rand.setFakeRandomNumber(new int[] {4, 442});
        question.estimate(activities);
        assertEquals("How much energy does e take?", question.getQuestionText());
        assertEquals(5, question.getCorrectAnswer());
        List<String> ans = new ArrayList<>();
        ans.add("442");
        assertEquals(ans, question.getAnswers());
    }

    @Test
    void equateEnergy() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        rand.setFakeRandomNumber(new int[] {1, 2, 3, 7, 4, 0, 6});
        question.equateEnergy(activities);
        assertEquals("Instead of b, you could try...", question.getQuestionText());
        assertEquals(2, question.getCorrectAnswer());
        List<String> ans = new ArrayList<>();
        ans.add("D");
        ans.add("E");
        ans.add("A");
        ans.add("G");
        assertEquals(ans, question.getAnswers());
    }

    @Test
    void choiceFromSelection() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        rand.setFakeRandomNumber(new int[] {1, 1, 0, 7, 3, 1});
        question.choiceFromSelection(activities);
        assertEquals("Which of the following activities takes 200 Wh energy?", question.getQuestionText());
        assertEquals(1, question.getCorrectAnswer());
        List<String> ans = new ArrayList<>();
        ans.add("A");
        ans.add("B");
        ans.add("H");
        ans.add("D");
        assertEquals(ans, question.getAnswers());
    }

    @Test
    void testInRangeGet() {
    }

    @Test
    void guessTheEnergy() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        rand.setFakeRandomNumber(new int[] {2, 3, 0, 122, 260, 189});
        question.guessTheEnergy(activities);
        assertEquals("How much energy does c take?", question.getQuestionText());
        assertEquals(3, question.getCorrectAnswer());
        List<String> ans = new ArrayList<>();
        ans.add("120Wh");
        ans.add("265Wh");
        ans.add("194Wh");
        ans.add("136Wh");
        assertEquals(ans,question.getAnswers());
    }

    @Test
    void comparisonMax() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        rand.setFakeRandomNumber(new int[] {0, 1, 0, 3, 3, 4, 3, 2});
        question.comparison(activities);
        assertEquals("Which requires the most energy?", question.getQuestionText());
        assertEquals(2, question.getCorrectAnswer());
        List<String> ans = new ArrayList<>();
        ans.add("C");
        ans.add("I");
        ans.add("B");
        ans.add("H");
        assertEquals(ans,question.getAnswers());
    }

    @Test
    void comparisonMin() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        rand.setFakeRandomNumber(new int[] {1, 7, 1, 3, 3, 4, 3, 2});
        question.comparison(activities);
        assertEquals("Which requires the least energy?", question.getQuestionText());
        assertEquals(0, question.getCorrectAnswer());
        List<String> ans = new ArrayList<>();
        ans.add("A");
        ans.add("I");
        ans.add("B");
        ans.add("H");
        assertEquals(ans,question.getAnswers());
    }

    @Test
    void testInRange() {
        Question question = new Question(server, rand);
        List<Activity> activities = server.getActivities();
        List<Activity> bounded = question.inRange(activities, 328, 100);
        List<Activity> ans = new ArrayList<>();
        ans.add(activities.get(0));
        ans.add(activities.get(1));
        ans.add(activities.get(2));
        ans.add(activities.get(3));
        ans.add(activities.get(4));
        ans.add(activities.get(7));
        ans.add(activities.get(8));
        assertEquals(ans,bounded);
    }

    @Test
    void testGetQuestionText() {
    }

    @Test
    void testGetType() {
    }

    @Test
    void testGetAnswers() {
    }

    @Test
    void testGetCorrectAnswer() {
    }
}