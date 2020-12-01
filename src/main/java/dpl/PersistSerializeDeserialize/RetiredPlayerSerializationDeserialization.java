package dpl.PersistSerializeDeserialize;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import dpl.LeagueManagement.TeamManagement.IRetiredPlayerPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;

public class RetiredPlayerSerializationDeserialization implements IRetiredPlayerPersistance {

	@Override
	public void persistRetiredPlayers(Player player, String teamName, League league) throws IOException {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		List<Player> playerList = new ArrayList<>();
		FileReader reader = null;
		Writer fileWriter = null;
		JsonParser jParsor = null;
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource(FileConstants.RETIRED_PLAYER_SERIALIZATION_FILE.toString());
			reader = new FileReader(url.getFile());
			if (reader.read() == -1) {
				playerList.add(player);
			} else {
				jParsor = new JsonParser();
				playerList = gson.fromJson(jParsor.parse(reader).toString(), new TypeToken<List<Player>>() {
				}.getType());
				playerList.add(player);
			}
			fileWriter = new FileWriter(url.getFile());
			gson.toJson(playerList, fileWriter);
		} catch (IOException e) {
			throw e;
		} finally {
			fileWriter.close();
			reader.close();
		}
	}

}
