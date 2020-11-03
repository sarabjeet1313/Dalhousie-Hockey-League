package dpl.LeagueSimulationManagementTest.LeagueManagementTest.InitializeModels;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;

public interface IInitializeLeagues {
	
	public boolean isEmptyString(String valueToCheck);
	
	public String truncateString(String inputString);
	
	public League parseAndInitializeModels();
	
}
