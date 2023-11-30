package client;

import client.utils.IServerUtils;
import com.google.inject.Inject;
import commons.Activity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Question{
    private transient IServerUtils server;
    private transient IRandom rand;

    private String questionText;
    private int type;
    private List<String> answers;
    private long correctAnswer;
     private List<String> pathList;
    /**
     * Creates a new answers list.
     */
    public Question() {
        answers = new ArrayList<String>();
        pathList = new ArrayList<String>();
    }

    /**
     * Constructor with parameters
     * @param server interface for getting the activities
     * @param rand interface for generating random numbers
     */
    @Inject
    public Question(IServerUtils server, IRandom rand) {
        this.server = server;
        this.rand = rand;
        this.answers = new ArrayList<>();
        this.type = -1;
        this.correctAnswer = -1;
        this.questionText = "";
        this.pathList = new ArrayList<>();
    }

    /**
     * Takes the activities from IServerUtils and generates a random number which will represent
     * the question type then calls the appropriate methods depending on the number.
     */
    public void generateQuestion() {
        pathList.clear();
        answers.clear();
        List<Activity> activities = server.getActivities();
        type = rand.nextInt(5);
        switch (type) {
            case 0:
                comparison(activities);
                break;
            case 1:
                guessTheEnergy(activities);
                break;
            case 2:
                choiceFromSelection(activities);
                break;
            case 3:
                equateEnergy(activities);
                break;
            case 4:
                estimate(activities);
                break;
            default:

        }
    }

    /**
     * Generates an Estimate type of question.
     * An Estimate type of question takes a random activity and asks the user to input a value
     * as close to the corresponding energy consumption as possible. The correct answer is saved
     * in string format and added to the list of answers. The path to the corresponding activity
     * is also added to the list of paths. The question text is saved separately as it will be
     * used later on.
     * @param activities the list of activities
     */
    public void estimate(List<Activity> activities) {

        int randomActivityNum = rand.nextInt(activities.size());
        questionText = "How much energy does " + activities.get(randomActivityNum).getTitle().toLowerCase() + " take?";
        int correctConsumption = activities.get(randomActivityNum).getConsumption_in_wh();
        String s = Integer.toString(correctConsumption);
        answers.add(s);
        correctAnswer = activities.get(randomActivityNum).getId();
        pathList.add(activities.get(randomActivityNum).getImage_path());
    }

    /**
     * Generates an Equate energy type of question.
     * This is a multiple choice type of question. Four activities are being generated, one of which is
     * the base activity. Amongst the other three activities there has to be one such that the difference
     * between its energy consumption and the base activity's energy consumption is in a specific
     * range. The other two activities must not meet this condition. The titles from each of the generated
     * activities are saved in the answers list in a random order. The image paths from each f the generated
     * activities are saved in the path list. The correct answer and question text are saved separately as
     * they will be used later on.
     * @param activities the list of activities
     */
    public void equateEnergy(List<Activity> activities) {
        int range = 100;
        int baseActivityNum;
        List<Activity> boundedActivities;

        do {
            baseActivityNum = rand.nextInt(activities.size());
            boundedActivities = inRangeGet(activities, activities.get(baseActivityNum), range);
        } while (boundedActivities.isEmpty() || !NotInRange(activities, activities.get(baseActivityNum), 100));

        questionText = "Instead of " + activities.get(baseActivityNum).getTitle().toLowerCase() + ", you could try...";
        int x = 0;
        int act;
        int pos = rand.nextInt(4);
        correctAnswer = pos;
        ArrayList<Activity> prev = new ArrayList<>();

        while (x < 4) {
            if(x == pos){
                int rightActivityNumber = rand.nextInt(boundedActivities.size());
                answers.add(boundedActivities.get(rightActivityNumber).getTitle());
                pathList.add(boundedActivities.get(rightActivityNumber).getImage_path());
                x++;
            }

            else {
                act = rand.nextInt(activities.size());
                Activity selectedActivity = activities.get(act);
                if (!InRangeCheck(activities, selectedActivity, activities.get(baseActivityNum), 100) &&
                        !prev.contains(selectedActivity)) {
                    answers.add(selectedActivity.getTitle());
                    pathList.add(selectedActivity.getImage_path());
                    prev.add(selectedActivity);
                    x++;
                }
            }
        }
    }

    /**
     * Generates a Choice from selection type of question.
     * This is a multiple choice type of question. A random activity is selected and the corresponding
     * title is being stored in the answers list. Three more random activities are being generated and
     * their corresponding titles are also saved in the answer list in an arbitrary order. The
     * corresponding image paths are saved in the path list. The purpose of this type of question is to
     * find the activity corresponding to the given energy consumption value in the question.
     * The correct answer and question text are saved separately as they will be used later on.
     * @param activities the list of activities
     */
    public void choiceFromSelection(List<Activity> activities) {
        int correctActivityNum = rand.nextInt(activities.size());
        Activity correctActivityData = activities.get(correctActivityNum);
        int pos = rand.nextInt(4);
        correctAnswer = pos;

        questionText = "Which of the following activities takes " + correctActivityData.getConsumption_in_wh() + " Wh energy?";

        int x = 0;
        int act;
        ArrayList<Activity> prev = new ArrayList<>();

        while(x < 4){
            if(pos == x){
                answers.add(correctActivityData.getTitle());
                pathList.add(correctActivityData.getImage_path());
                x++;
            }

            else{
                act = rand.nextInt(activities.size());
                if((activities.get(act).getConsumption_in_wh() != correctActivityData.getConsumption_in_wh()) && !prev.contains(activities.get(act))){
                    answers.add(activities.get(act).getTitle());
                    pathList.add(activities.get(act).getImage_path());
                    prev.add(activities.get(act));
                    x++;
                }
            }
        }
    }

    /**
     *  Checks if there exist at least three activities whose energy consumptions are not in a
     *  specific range.
     * @param activities the list of activities
     * @param origActivity the activity whose energy consumption is to be compared with
     * @param range the range
     * @return true if there are three activities that meet the requirements, false otherwise.
     */
    public boolean NotInRange(List<Activity> activities, Activity origActivity, int range){
        int count = 0;
        for (Activity a : activities) {
            if (!(a.getConsumption_in_wh() >= (origActivity.getConsumption_in_wh() - range) &&
                    a.getConsumption_in_wh() <= origActivity.getConsumption_in_wh() + range)) {
                count++;
            }
        }
        if(count >= 3)
            return true;

        return false;
    }

    /**
     * For two given activities, checks if the absolute difference between their corresponding energy
     * consumptions is within a specific range.
     * @param activities the list of activities
     * @param activityToBeChecked the generated activity that is being tested
     * @param activityHad the base activity that is being compared with
     * @param range the range
     * @return true if the difference is in the range, false otherwise.
     */
    public boolean InRangeCheck(List<Activity> activities, Activity activityToBeChecked, Activity activityHad, int range){
        if(Math.abs(activityToBeChecked.getConsumption_in_wh() - activityHad.getConsumption_in_wh()) <= range)
            return true;
        return false;
    }

    // gets all the activities in range

    /**
     * Gets all the activities for which the absolute difference between their energy consumption and the energy
     * consumption of the base activity is within a specific range.
     * @param activities the list of activities
     * @param origActivity the base activity
     * @param range the range
     * @return a list with all the activities that meet the requirement.
     */
    public List<Activity> inRangeGet(List<Activity> activities, Activity origActivity, int range) {
        List<Activity> result = new ArrayList<>();
        for (Activity a : activities) {
            if (a.getConsumption_in_wh() >= (origActivity.getConsumption_in_wh() - range) &&
                    a.getConsumption_in_wh() <= (origActivity.getConsumption_in_wh() + range) &&
                    a.getTitle() != origActivity.getTitle() ) {
                result.add(a);
            }
        }
        return result;
    }

    /**
     * Generates a Guess the energy type of question.
     * This is a multiple choice type of question. One base activity is being generated and its corresponding
     * energy consumption is stored in the answers list. Three more numbers are randomly generated such that
     * their values are close to the correct answer, but not equal to it. These are also added to the list of
     * answers in an arbitrary order in string format. All the image paths of the answers are added to the
     * path list. The correct answer and the question text are being stored separately as they will be used
     * later on.
     * @param activities the list of activities
     */
    public void guessTheEnergy(List<Activity> activities) {
        int range = 200, x = 0;
        int act = rand.nextInt(activities.size());
        questionText = "How much energy does " + activities.get(act).getTitle().toLowerCase() + " take?";
        int correct = activities.get(act).getConsumption_in_wh();
        pathList.add(activities.get(act).getImage_path());
        int pos = rand.nextInt(4);
        correctAnswer = pos;
        int rounded = rand.nextInt(4);
        List<Integer> prev = new ArrayList<>();

        int min;
        if (correct - range < 5) {
            min = 5;
        } else {
            min = correct - range;
        }
        int max = correct + range;

        while (x < 4) {
            if (x == pos) {
                answers.add(String.valueOf(correct) + "Wh");
                x++;
            } else {
                int ans = rand.nextInt(max - min) + min;
                if (x == rounded) {
                    ans = ans - (ans % 10);
                }
                if (!prev.contains(ans) && (ans < correct - 5 || ans > correct + 5)) {
                    answers.add(String.valueOf(ans) + "Wh");
                    prev.add(ans);
                    x++;
                }
            }
        }
    }

    /**
     * Generates a Comparison type of question.
     * This is a multiple choice type of question. There are two possible options, checking which activity
     * requires the most respectively the least energy. In both cases, a random activity is being generated, its
     * title being saved in the answers list. Then three more activities are generated such that the
     * difference between their energy consumption and the correct answer is in a certain range. The correct
     * answer is being updated every time a new max/min value is found, depending on the arbitrarily generated
     * question option. The image paths are stored in the path list.
     * The question text is also stored separately as it will be used later on.
     * @param activities the list of activities
     */
    public void comparison(List<Activity> activities) {
        int opt = rand.nextInt(2);
        List<Activity> sortedActivities = activities.stream()
                .sorted(Comparator.comparing(Activity::getConsumption_in_wh))
                .collect(Collectors.toList());

        List<Integer> prev = new ArrayList<>();
        int act;
        List<Activity> bounded;
        int x = 0;
        switch (opt) {
            case 0:
                questionText = "Which requires the most energy?";
                act = rand.nextInt(activities.size());
                bounded = inRange(sortedActivities, sortedActivities.get(act).getConsumption_in_wh(), 400);
                int max = -1;
                while (x < 4) {
                    act = rand.nextInt(bounded.size());
                    if (!prev.contains(bounded.get(act).consumption_in_wh)) {
                        answers.add(bounded.get(act).title);
                        pathList.add(bounded.get(act).getImage_path());
                        if (bounded.get(act).consumption_in_wh > max) {
                            max = bounded.get(act).consumption_in_wh;
                            correctAnswer = x;
                        }
                        prev.add(bounded.get(act).consumption_in_wh);
                        x++;
                    }
                }
                break;
            case 1:
                questionText = "Which requires the least energy?";
                act = rand.nextInt(activities.size());
                bounded = inRange(sortedActivities, sortedActivities.get(act).getConsumption_in_wh(), 400);
                int min = Integer.MAX_VALUE;
                while (x < 4) {
                    act = rand.nextInt(bounded.size());
                    if (!prev.contains(bounded.get(act).consumption_in_wh)) {
                        answers.add(bounded.get(act).title);
                        pathList.add(bounded.get(act).getImage_path());
                        if (bounded.get(act).consumption_in_wh < min) {
                            min = bounded.get(act).consumption_in_wh;
                            correctAnswer = x;
                        }
                        prev.add(bounded.get(act).consumption_in_wh);
                        x++;
                    }
                }
                break;

            default:
        }

    }

    /**
     * Gets a part of the activities whose energy consumptions are in a certain range in relation to the base
     * activity energy consumption.
     * @param activities the list of activities
     * @param orig the base activity energy consumption
     * @param range the range
     * @return a list of activities that meet the requirements
     */
    public List<Activity> inRange(List<Activity> activities, int orig, int range) {
        List<Activity> result = new ArrayList<>();
        for (Activity a : activities) {
            if (a.getConsumption_in_wh() >= (orig - range) && a.getConsumption_in_wh() <= orig + range) {
                result.add(a);
            }
        }
        if (result.size() < 7) {
            return inRange(activities, orig, range + 100);
        }
        return result;
    }

    /**
     * Get the question text.
     * @return the text of the question
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Get the question type.
     * @return the type of the question
     */
    public int getType() {
        return type;
    }

    /**
     * Get the answers list.
     * @return the list of answers
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Get the path list
     * @return the list of activity image paths
     */
    public List<String> getPathList(){
        return pathList;
    }

    /**
     * Set the question text.
     * @param text the question text
     */
    public void setQuestionText(String text) {this.questionText = text;}

    /**
     * Get the correct answer.
     * @return the correct answer
     */
    public long getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Set the question type
     * @param type the question type
     */
    public void setType(int type){ this.type = type;}
}
