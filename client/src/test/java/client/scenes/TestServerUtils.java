package client.scenes;

import client.utils.IServerUtils;
import commons.Activity;

import java.util.ArrayList;
import java.util.List;

public class TestServerUtils implements IServerUtils {
    @Override
    public List<Activity> getActivities() {
        List<Activity> result = new ArrayList<>();
        result.add(new Activity("A", 150, ""));
        result.add(new Activity("B", 200, ""));
        result.add(new Activity("C", 136, ""));
        result.add(new Activity("D", 328, ""));
        result.add(new Activity("E", 442, ""));
        result.add(new Activity("F", 1500, ""));
        result.add(new Activity("G", 1430, ""));
        result.add(new Activity("H", 153, ""));
        result.add(new Activity("I", 167, ""));
        return result;
    }
}
