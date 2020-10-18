package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

public class Leagues {
	
	private String leagueName;
	private List<Conferences> conferenceList;
	private List<Player> freeAgents;
	private static List<Leagues> leagueList;
	
	public Leagues(String leagueName, List<Conferences> conferenceList, List<Player> freeAgents) {
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

	public List<Player> getFreeAgents() {
		return freeAgents;
	}

	public void setFreeAgents(List<Player> freeAgents) {
		this.freeAgents = freeAgents;
	}

	public List<String> getLeagueNames(String teamName, ILeague object){
		List<String> leagueNames = new ArrayList<String>();
		try {
			leagueList = object.getLeagueData(teamName);
		}catch (Exception e) {
			System.out.println("error "+ e.getMessage());
		}

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
		try {
			rowCount = object.checkLeagueName(leagueName);
		}catch (Exception e) {
			System.out.println("error "+ e.getMessage());
		}
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
		int age = 0, skating = 0, shooting = 0, checking = 0, saving = 0;
		List<Conferences> conferenceList = league.getConferenceList();
		List<Teams> teamList;
		List<Divisions> divisionList;
		List<Player> playerList;
		try {

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
								age = playerList.get(pIndex).getAge();
								skating = playerList.get(pIndex).getSkating();
								shooting = playerList.get(pIndex).getShooting();
								checking = playerList.get(pIndex).getChecking();
								saving = playerList.get(pIndex).getSaving();
								isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName,
										teamName, generalManager, headCoach, playerName, position, captain, age, skating, shooting, checking, saving);
								}
						}
						else {
							playerName = position = "Empty";
							isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName,
									teamName, generalManager, headCoach, playerName, position, captain, age, skating, shooting, checking, saving);
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
					age = playerList.get(index).getAge();
					skating = playerList.get(index).getSkating();
					shooting = playerList.get(index).getShooting();
					checking = playerList.get(index).getChecking();
					saving = playerList.get(index).getSaving();
					isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName,
							teamName, generalManager, headCoach, playerName, position, captain, age, skating, shooting, checking, saving);
					}
			}
		}catch (Exception e) {
			System.out.println("error "+ e.getMessage());
		}

		return isCreated;
	}
}
