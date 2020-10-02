package dal.asd.dpl.teammanagement;

import java.util.List;

import dal.asd.dpl.Database.LeagueDataDB;

public class Leagues {
	private String leagueName;
	private List<Conferences> conferenceList;
	private List<Players> freeAgents;
	
	public Leagues(String leagueName, List<Conferences> conferenceList, List<Players> freeAgents) {
		this.leagueName = leagueName;
		this.conferenceList = conferenceList;
		this.freeAgents = freeAgents;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public List<Conferences> getConferenceList() {
		return conferenceList;
	}

	public void setConferenceList(List<Conferences> conferenceList) {
		this.conferenceList = conferenceList;
	}

	public List<Players> getFreeAgents() {
		return freeAgents;
	}

	public void setFreeAgents(List<Players> freeAgents) {
		this.freeAgents = freeAgents;
	}

	public boolean getTeamData(String teamName, ILeague object) {
		boolean isValidTeam = false;
//		ILeague object = new LoadLeagueData();
//		ILeague object = obj;
		Leagues league = object.getLeagueData(teamName);
		if(league == null) {
			isValidTeam = true;
		}
		return !isValidTeam;
	}
	
	public boolean isValidLeagueName(String leagueName, ILeague object) {
		int rowCount = 0;
		boolean isValid = true;
//		ILeague object = new LeageMockData();
		rowCount = object.checkLeagueName(leagueName);
		if(rowCount > 0) {
			isValid = false;
		}
		return isValid;
	}
}
