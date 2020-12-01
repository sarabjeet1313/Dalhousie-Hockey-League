package dpl.PersistSerializeDeserialize;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;

public class LeagueSerializationDeserialization implements ILeaguePersistance {

	URL url = getClass().getClassLoader().getResource(FileConstants.LEAGUE_SERIALIZATION_FILE.toString());
	FileReader reader = null;
	Gson gson = new Gson();

	@Override
	public int checkLeagueName(League league) throws IOException {

		League tempLeague = null;
		int rowCount = 0;
		try {
			reader = new FileReader(url.getFile());
			if (reader.read() == -1) {
				reader.close();
				rowCount = 0;
			} else {
				JsonParser jParsor = new JsonParser();
				tempLeague = gson.fromJson(jParsor.parse(reader).toString(), League.class);
				if (tempLeague.getLeagueName().equals(league.getLeagueName())) {
					rowCount = 1;
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		return rowCount;
	}

	@Override
	public League loadLeagueData(String teamName) throws IOException, NullPointerException {
		League tempLeague = null;
		List<Conference> conferenceList = null;
		List<Division> divisionList = null;
		List<Team> teamList = null;
		boolean teamExists = Boolean.FALSE;
		try {
			reader = new FileReader(url.getFile());
			if (reader.read() == -1) {
				reader.close();
			} else {
				JsonParser jParsor = new JsonParser();
				tempLeague = gson.fromJson(jParsor.parse(reader).toString(), League.class);
				conferenceList = tempLeague.getConferenceList();
				for (int index = 0; index < conferenceList.size(); index++) {
					divisionList = conferenceList.get(index).getDivisionList();
					for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
						teamList = divisionList.get(dIndex).getTeamList();
						for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
							if (teamList.get(tIndex).getTeamName().equals(teamName)) {
								teamExists = Boolean.TRUE;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw e;
		} catch (NullPointerException e) {
			throw e;
		} finally {
			reader.close();
		}
		if (teamExists == Boolean.TRUE) {
			return tempLeague;
		}
		return null;
	}

	@Override
	public boolean persisitLeagueData(League league, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, Player player) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		try {
			reader = new FileReader(url.getFile());
			if (reader.read() == -1) {
				Writer fileWriter = new FileWriter(url.getFile());
				gson.toJson(league, fileWriter);
				fileWriter.close();
			}
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		}
		return isSerialized;
	}

	@Override
	public boolean updateLeagueData(League league, String teamName, Player player) throws IOException {
		boolean isSerialized = Boolean.FALSE;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		try {
			reader = new FileReader(url.getFile());
			if (reader.read() == -1) {
				Writer fileWriter = new FileWriter(url.getFile());
				gson.toJson(league, fileWriter);
				fileWriter.close();
			}
			isSerialized = Boolean.TRUE;
		} catch (IOException e) {
			isSerialized = Boolean.FALSE;
			throw e;
		}
		return isSerialized;
	}

}
