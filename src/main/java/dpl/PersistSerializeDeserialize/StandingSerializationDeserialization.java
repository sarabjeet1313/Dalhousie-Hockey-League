package dpl.PersistSerializeDeserialize;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.Standing;

public class StandingSerializationDeserialization implements IStandingsPersistance {

	private int season;

	public void setSeason(int season) {
		this.season = season;
	}

	public int getSeason() {
		return season;
	}

	public boolean insertToStandings(Standing standing) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Writer fileWriter = null;
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource(FileConstants.STANDING_SERIALIZATION_FILE.toString());
			fileWriter = new FileWriter(url.getFile());
			gson.toJson(standing, fileWriter);
			fileWriter.close();
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		} finally {
			fileWriter.close();
		}
		return isSerialized;
	}
}
