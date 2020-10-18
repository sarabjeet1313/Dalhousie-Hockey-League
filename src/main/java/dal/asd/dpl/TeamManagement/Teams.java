package dal.asd.dpl.TeamManagement;

import java.util.List;

public class Teams {
    
    private String teamName;
    private String generalManager;
    private Coach headCoach;
    private List<Player> playerList;
    
    public Teams(String teamName, String generalManager, Coach headCoach, List<Player> playerList) {
		super();
		this.teamName = teamName;
		this.generalManager = generalManager;
		this.headCoach = headCoach;
		this.playerList = playerList;
	}
    
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getGeneralManager() {
		return generalManager;
	}
	
	public void setGeneralManager(String generalManager) {
		this.generalManager = generalManager;
	}
	
	public Coach getHeadCoach() {
		return headCoach;
	}
	
	public void setHeadCoach(Coach headCoach) {
		this.headCoach = headCoach;
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
	
	public boolean isValidTeamName(String conferenceName, String divisionName, String teamName, Leagues league) {
		List<Conferences> conferenceList =  league.getConferenceList();
		boolean isValid = false;
		for(int index = 0; index < conferenceList.size(); index++) {
			if(conferenceList.get(index).getConferenceName().equals(conferenceName)) {
				List<Divisions> divisionList = conferenceList.get(index).getDivisionList();
				
				for(int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					if(divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
						List<Teams> teamList = divisionList.get(dIndex).getTeamList();
						
						for(int tIndex = 0; tIndex < teamList.size(); tIndex++) {
							if(teamList.get(dIndex).teamName.equals(teamName)) {
								isValid = true;
								break;
							}
						}
					}
				}
			}
			break;
		}
		return isValid;
	}
    
}
