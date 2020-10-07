package dal.asd.dpl.teammanagement;

import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.database.LeagueDataDB;

public class Leagues {
	private String leagueName;
	private List<Conferences> conferenceList;
	private List<Players> freeAgents;
	private static List<Leagues> leagueList;
	
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

	public List<String> getLeagueNames(String teamName, ILeague object){
		List<String> leagueNames = new ArrayList<String>();
		leagueList = object.getLeagueData(teamName);
		for(int index = 0; index < leagueList.size(); index++) {
			leagueNames.add(leagueList.get(index).getLeagueName());
		}
		return leagueNames;
	}
	
	public boolean loadLeagueData(String leagueName) {
		boolean isValidTeam = false;
		for(int index = 0; index < leagueList.size(); index++) {
			if(leagueName.equals(leagueList.get(index).getLeagueName())) {
				isValidTeam = true;
			}
		}
		return isValidTeam;
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
	
	public boolean createTeam(Leagues league, ILeague object) {
		boolean isCreated = false, captain = false;
		String leagueName = league.getLeagueName();
		String conferenceName = "Empty", divisionName = "Empty", teamName = "Empty", generalManager = "Empty",
				headCoach = "Empty", playerName = "Empty", position = "Empty";
		List<Conferences> conferenceList = league.getConferenceList();
		List<Teams> teamList;
		List<Divisions> divisionList;
		List<Players> playerList;
		
		for(int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
			conferenceName = conferenceList.get(cIndex).getConferenceName();
			divisionList = conferenceList.get(cIndex).getDivisionList();
			
			for(int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				divisionName = divisionList.get(dIndex).getDivisionName();
				teamList = divisionList.get(dIndex).getTeamList();
				
				for(int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					teamName = teamList.get(tIndex).getTeamName();
					generalManager = teamList.get(tIndex).getGeneralManager();
					headCoach = teamList.get(tIndex).getHeadCoach();
					
					if(!teamList.get(tIndex).getPlayerList().isEmpty()) {
						playerList = teamList.get(tIndex).getPlayerList();
						
						for(int pIndex = 0; pIndex < playerList.size(); pIndex++) {
							playerName = playerList.get(pIndex).getPlayerName();
							position = playerList.get(pIndex).getPlayerPosition();
							captain = playerList.get(pIndex).getCaptain();
							isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName, 
									teamName, generalManager, headCoach, playerName, position, captain);
						}
					}
					else {
						isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName, 
								teamName, generalManager, headCoach, playerName, position, captain);
					}
				}
			}
		}
		conferenceName = divisionName = teamName = generalManager = headCoach = "Empty";
		playerList = league.getFreeAgents();
		if(!playerList.isEmpty()) {
			for(int index = 0; index < playerList.size(); index++) {
				playerName = playerList.get(index).getPlayerName();
				position = playerList.get(index).getPlayerPosition();
				captain = playerList.get(index).getCaptain();
				isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName, 
						teamName, generalManager, headCoach, playerName, position, captain);
			}
		}
		return isCreated;
	}
}
