package dpl.LeagueManagement.Schedule;

import dpl.LeagueManagement.TeamManagement.League;

import java.sql.SQLException;
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

	void generateSchedule(League league)throws SQLException;

	boolean incrementCurrentDay();

	Map<String, List<Map<String, String>>> getFinalSchedule();

	void setFinalSchedule(Map<String, List<Map<String, String>>> schedule);

	void setTeamsToBeScheduled(List<String> teamsToBeScheduled);

	List<String> getTeamsToBeScheduled();

	void setTeamsScheduled(List<String> teamsScheduled);

	List<String> getTeamsScheduled();

	void generateScheduleOnTheFly(List<String> teamsToCompete, String currentDay);

	boolean anyUnplayedGame(String date);

}
