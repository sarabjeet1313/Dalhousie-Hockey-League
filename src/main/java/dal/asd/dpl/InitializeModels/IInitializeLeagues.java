package dal.asd.dpl.InitializeModels;

import dal.asd.dpl.TeamManagement.Leagues;

public interface IInitializeLeagues {
	
	public boolean isEmptyString(String valueToCheck);
	
	public String truncateString(String inputString);
	
	public Leagues parseAndInitializeModels();
	
}
