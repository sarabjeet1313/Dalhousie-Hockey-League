package dpl.LeagueSimulationManagement.LeagueManagement.InitializeModels;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public interface IInitializeLeagues {

	public boolean isEmptyString(String valueToCheck);

	public String truncateString(String inputString);

	public League parseAndInitializeModels();

}
