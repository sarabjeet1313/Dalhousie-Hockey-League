package dpl.PersistSerializeDeserialize;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.Manager;

public class ManagerSerializationDeserialization implements IManagerPersistance {

	@Override
	public boolean persistManagerInfo(Manager manager, String teamName, String leagueName) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		URL url = null;
		Writer fileWriter = null;
		try {
			url = getClass().getClassLoader().getResource(FileConstants.MANAGER_SERIALIZATION_FILE.toString());
			fileWriter = new FileWriter(url.getFile());
			gson.toJson(manager, fileWriter);
			fileWriter.close();
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		}
		return isSerialized;
	}
}
