package dal.asd.dpl.InitializeModels;

import dal.asd.dpl.TeamManagement.League;

public interface IInitializeLeagues {
	
	public boolean isEmptyString(String valueToCheck);
	
	public String truncateString(String inputString);
	
	public League parseAndInitializeModels();
	
}
