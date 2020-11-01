package dal.asd.dpl.TeamManagementTest;

import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.TeamManagement.League;

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
