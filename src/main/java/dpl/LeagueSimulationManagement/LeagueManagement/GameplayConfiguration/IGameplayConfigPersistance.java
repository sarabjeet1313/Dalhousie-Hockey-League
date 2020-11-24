package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

import java.io.IOException;
import java.sql.SQLException;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public interface IGameplayConfigPersistance {

	GameplayConfig loadGameplayConfigData(String leagueName) throws SQLException, IOException;

	boolean persistGameConfig(GameplayConfig config, String leagueName) throws SQLException, IOException;
}
