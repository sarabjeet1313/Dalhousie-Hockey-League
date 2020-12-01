package dpl.LeagueManagement.TeamManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dpl.SystemConfig;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class SerializeLeague implements ISerialize {

	IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	@Override
	public boolean serializeLeagueModel(League league) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			URL url = getClass().getClassLoader().getResource(TeamManagementConstants.TEST_JSON.toString());
			Writer fileWriter = new FileWriter(url.getFile());
			gson.toJson(league, fileWriter);
			fileWriter.close();
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		}
		return isSerialized;
	}

}
