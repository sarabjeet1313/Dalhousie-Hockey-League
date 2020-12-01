package dpl.LeagueManagement.InitializeModels;

import dpl.LeagueManagement.TeamManagement.League;

public interface IInitializeLeagues {

	public boolean isEmptyString(String valueToCheck);

	public String truncateString(String inputString);

	public League parseAndInitializeModels();

}
