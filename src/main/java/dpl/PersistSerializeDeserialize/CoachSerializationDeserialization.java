package dpl.PersistSerializeDeserialize;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;

public class CoachSerializationDeserialization implements ICoachPersistance {

	@Override
	public boolean persistCoaches(Coach coach, String teamName, String leagueName) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		Writer fileWriter = null;
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource(FileConstants.COACH_SERIALIZATION_FILE.toString());
			fileWriter = new FileWriter(url.getFile());
			gson.toJson(coach, fileWriter);
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
