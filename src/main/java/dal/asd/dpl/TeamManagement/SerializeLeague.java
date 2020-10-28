package dal.asd.dpl.TeamManagement;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;


public class SerializeLeague implements ISerialize{
	
	@Override
	public boolean serializeLeagueModel(League league) {
		boolean isSerialized = false;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			URL url = getClass().getClassLoader().getResource("test.json");
			Writer fileWriter =  new FileWriter(url.getFile());
			gson.toJson(league, fileWriter);
			fileWriter.close();
			isSerialized = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			isSerialized = false;
		}
		return isSerialized;
	}
	
	@Override
	public League deSerializeLeagueModel(String filename) {
		League league = null;
		Gson gson = new Gson();
		try {
			URL url = getClass().getClassLoader().getResource(filename);
			FileReader reader = new FileReader(url.getFile());
			JsonParser jParsor = new JsonParser();
			league = gson.fromJson(jParsor.parse(reader).toString(), League.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return league;
	}
}
