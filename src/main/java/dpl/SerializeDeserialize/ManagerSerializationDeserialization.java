package dpl.SerializeDeserialize;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import dpl.DplConstants.FileConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;

public class ManagerSerializationDeserialization implements IManagerPersistance {

	@Override
	public boolean persistManagerInfo(String managerName, String teamName, String leagueName) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		try {
			URL url = getClass().getClassLoader().getResource(FileConstants.MANAGER_SERIALIZATION_FILE.toString());
			Writer fileWriter = new FileWriter(url.getFile());
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.write(managerName + FileConstants.NEW_LINE);
			
			bufferWriter.close();
			fileWriter.close();
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		}
		return isSerialized;
	}
}
