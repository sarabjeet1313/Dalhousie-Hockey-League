package dpl.LeagueManagementTest.GameplayConfigurationTest;

import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class GameplayConfigMockData implements IGameplayConfigPersistance {

	League league = new LeagueMockData().getTestData();

	@Override
	public GameplayConfig loadGameplayConfigData(String leagueName) {
		GameplayConfig config = league.getGameConfig();
		return config;
	}

	@Override
	public boolean persistGameConfig(GameplayConfig config, String leagueName) {
		boolean isValid = false;
		if (league.getGameConfig().getAging().getAverageRetirementAge() == config.getAging()
				.getAverageRetirementAge()) {
			isValid = true;
		}
		return isValid;
	}
}
