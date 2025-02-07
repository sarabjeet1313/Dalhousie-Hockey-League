package dpl.SerializeDeserialize;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import dpl.DplConstants.FileConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Division;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class GameplayConfigSerializationDeserialization implements IGameplayConfigPersistance {

	URL url = getClass().getClassLoader().getResource(FileConstants.GAMEPLAY_CONFIG_SERIALIZATION_FILE.toString());
	FileReader reader = null;
	Gson gson = new Gson();

	@Override
	public GameplayConfig loadGameplayConfigData(String leagueName) throws IOException, NullPointerException {
		GameplayConfig tempConfig = null;
		try {
			reader = new FileReader(url.getFile());
			if (reader.read() == -1) {
				tempConfig = null;
			} else {
				JsonParser jParsor = new JsonParser();
				tempConfig = gson.fromJson(jParsor.parse(reader).toString(), GameplayConfig.class);
			}
		} catch (IOException e) {
			throw e;
		} catch (NullPointerException e) {
			throw e;
		} finally {
			reader.close();
		}
		return tempConfig;
	}

	@Override
	public boolean persistGameConfig(GameplayConfig config, String leagueName) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		try {
			Writer fileWriter = new FileWriter(url.getFile());
			gson.toJson(config, fileWriter);
			fileWriter.close();
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		}
		return isSerialized;
	}

}
