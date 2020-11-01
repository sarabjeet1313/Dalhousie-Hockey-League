package dal.asd.dpl.Schedule;
import dal.asd.dpl.TeamManagement.League;
import java.util.List;
import java.util.Map;

public interface ISchedule {

    void setSeasonType(int seasonType);
    int getSeasonType();
    String getCurrentDay();
    void setCurrentDay(String currentDay);
    String getFirstDay();
    void setFirstDay(String firstDay);
    String getLastDay();
    void setLastDay(String lastDay);
    void generateSchedule(League league);
    boolean incrementCurrentDay();
    public Map< String, List<Map<String, String>>> getFinalSchedule();
    void setFinalSchedule(Map< String, List<Map<String, String>>> schedule);
    public void setTeamsToBeScheduled(List<String> teamsToBeScheduled);
    public List<String> getTeamsToBeScheduled();
    public void setTeamsScheduled(List<String> teamsScheduled);
    public List<String> getTeamsScheduled();
    public void generateScheduleOnTheFly(List<String> teamsToCompete, String currentDay);
    public boolean anyUnplayedGame(String date);

}
