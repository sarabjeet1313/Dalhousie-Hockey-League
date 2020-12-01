package dpl.LeagueManagementTest.ScheduleTest;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MockSchedule implements ISchedule{

    private static MockSchedule instance;
    private Calendar calendar;
    private IUserOutput output;
    private Map< String, List<Map<String, String>>> schedule;
    private List<Map<String, String>> teamsList;
    private List<String> teamsScheduled;
    private List<String> teamsToBeScheduled;
    private int seasonType;
    private String currentDay;
    private String firstDay;
    private String lastDay;

    public static MockSchedule getInstance() {
        if (instance == null) {
            instance = new MockSchedule();
        }
        return instance;
    }

    public MockSchedule() {
        output = new CmdUserOutput();
        calendar = Calendar.getInstance();
        schedule = new HashMap<>();
        teamsList = new ArrayList<>();
        teamsScheduled = new ArrayList<>();
        teamsToBeScheduled = new ArrayList<>();
        teamsToBeScheduled.add("Brampton");
        this.teamsScheduled.add("Boston");
        this.teamsScheduled.add("Halifax");
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

    public void setSeasonType(int seasonType) {
        this.seasonType = seasonType;
    }

    public int getSeasonType(){
        return this.seasonType;
    }

    public String getCurrentDay() {
        return this.currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public String getFirstDay() {
        return this.firstDay;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }

    public String getLastDay() {
        return this.lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public void generateSchedule(League league) {
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
    }

    public boolean incrementCurrentDay() {
        if (currentDay.equals(lastDay)) {
            return false;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                calendar.setTime(dateFormat.parse(currentDay));
            } catch (ParseException e) {
                output.setOutput("Exception while getting current date in Playoff Schedule state");
                output.sendOutput();
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentDay = dateFormat.format(calendar.getTime());
            return true;
        }
    }

    public Map<String, List<Map<String, String>>> getFinalSchedule() {
        return schedule;
    }

    public void setFinalSchedule(Map<String, List<Map<String, String>>> schedule) {
        this.schedule = schedule;
    }

    public void setTeamsToBeScheduled(List<String> teamsToBeScheduled) {
        this.teamsToBeScheduled = teamsToBeScheduled;
    }

    public List<String> getTeamsToBeScheduled() {
        teamsToBeScheduled.add("Brampton");
        return teamsToBeScheduled;
    }

    public void setTeamsScheduled(List<String> teamsScheduled) {
        this.teamsScheduled.add("Boston");
        this.teamsScheduled.add("Halifax");
        this.teamsScheduled = teamsScheduled;
    }

    public List<String> getTeamsScheduled() {
        return this.teamsScheduled;
    }

    public void generateScheduleOnTheFly(List<String> teamsToCompete, String currentDay) {
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
    }

    public boolean anyUnplayedGame(String date) {
        return false;
    }
}
