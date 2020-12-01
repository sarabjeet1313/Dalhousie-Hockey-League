package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;

public interface ILeaguePersistance {

	public boolean persisitLeagueData(League league, String conferenceName, String divisionName, String teamName,
									  String generalManager, String headCoach, Player player) throws IOException;

	public int checkLeagueName(League league) throws IOException;

	public League loadLeagueData(String teamName) throws IOException;

	public boolean updateLeagueData(League league, String teamName, Player player) throws IOException;

}
