package dpl.TeamManagementTest;

import dpl.GameplayConfiguration.GameplayConfig;
import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.TeamManagement.League;

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
