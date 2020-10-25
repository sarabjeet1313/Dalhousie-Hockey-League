package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
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
    void generateSchedule(Leagues league);
    boolean incrementCurrentDay();
    public Map< String, List<Map<String, String>>> getFinalSchedule();
}
