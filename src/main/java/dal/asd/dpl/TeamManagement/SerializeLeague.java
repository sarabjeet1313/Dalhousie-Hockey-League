package dal.asd.dpl.TeamManagement;

import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.util.TeamManagementUtil;

public class SerializeLeague implements ISerialize {
	
	IUserOutput output = new CmdUserOutput();

	@Override
	public boolean serializeLeagueModel(League league) {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			URL url = getClass().getClassLoader().getResource(TeamManagementUtil.TEST_JSON.toString());
			Writer fileWriter = new FileWriter(url.getFile());
			gson.toJson(league, fileWriter);
			fileWriter.close();
			isSerialized = Boolean.TRUE;
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
			isSerialized = Boolean.FALSE;
		}
		return isSerialized;
	}

//	@Override
//	public League deSerializeLeagueModel(String filename) {
//		League league = null;
//		Gson gson = new Gson();
//		try {
//			URL url = getClass().getClassLoader().getResource(filename);
//			FileReader reader = new FileReader(url.getFile());
//			JsonParser jParsor = new JsonParser();
//			league = gson.fromJson(jParsor.parse(reader).toString(), League.class);
//		} catch (Exception e) {
//			output.setOutput(e.getMessage());
//			output.sendOutput();
//		}		
//		return league;
//	}
}
