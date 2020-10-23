package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import java.util.List;
import java.util.Map;

public interface ISchedule {

    String getCurrentDay();
    void setCurrentDay(String currentDay);
    String getFirstDay();
    void setFirstDay(String firstDay);
    String getLastDay();
    void setLastDay(String lastDay);
    void generateSchedule(Leagues league);
    boolean incrementCurrentDay();
}
