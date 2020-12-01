package dpl.LeagueManagement.GameplayConfiguration;

import java.io.IOException;

public interface IGameplayConfigPersistance {

	GameplayConfig loadGameplayConfigData(String leagueName) throws IOException;

	boolean persistGameConfig(GameplayConfig config, String leagueName) throws IOException;
}
