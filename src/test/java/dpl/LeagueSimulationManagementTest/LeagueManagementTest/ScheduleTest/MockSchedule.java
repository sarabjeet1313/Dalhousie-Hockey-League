package dpl.LeagueSimulationManagementTest.LeagueManagementTest.ScheduleTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockSchedule {

    private Map< String, List<Map<String, String>>> schedule;
    private List<Map<String, String>> teamsList;
    private List<String> teamsScheduled;
    private List<String> teamsToBeScheduled;

    public MockSchedule() {
        schedule = new HashMap<>();
        teamsList = new ArrayList<>();
        Map<String, String> teamsCompeting1 = new HashMap<>();
        teamsCompeting1.put("Boston", "Halifax");
        Map<String, String> teamsCompeting2 = new HashMap<>();
        teamsCompeting2.put("Boston", "Toronto");
        Map<String, String> teamsCompeting3 = new HashMap<>();
        teamsCompeting3.put("Boston", "Brampton");
        teamsList.add(teamsCompeting1);
        teamsList.add(teamsCompeting2);
        teamsList.add(teamsCompeting3);
        schedule.put("14-11-2020", teamsList);

        teamsScheduled = new ArrayList<String>();
        teamsScheduled.add("Boston");
        teamsScheduled.add("Halifax");

        teamsToBeScheduled = new ArrayList<>();
        teamsToBeScheduled.add("Brampton");
    }

    public Map< String, List<Map<String, String>>> getMockSchedule() {
        return schedule;
    }

    public List<String> getMockScheduledTeams() {
        return teamsScheduled;
    }

    public List<String> getMockToBeScheduledTeams() {
        return teamsToBeScheduled;
    }
}
