package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.GameplayConfig;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;

public class GamaplayConfigMockData implements IGameplayConfigPersistance{
	
	League league = new LeagueMockData().getTestData();
	
	@Override
	public GameplayConfig loadGameplayConfigData(String leagueName) {
		GameplayConfig config = league.getGameConfig();
		return config;
	}
	
	@Override
	public boolean persistGameConfig(GameplayConfig config, String leagueName) {
		boolean isValid = false;
		if(league.getGameConfig().getAging().getAverageRetirementAge() == config.getAging().getAverageRetirementAge()) {
			isValid = true;
		}
		return isValid;
	}
}
