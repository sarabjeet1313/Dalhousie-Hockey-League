package dal.asd.dpl.TeamManagement;

import java.util.List;

public class Division {
	
    private String divisionName;
    private List<Team> teamList;
    
	public Division(String divisionName, List<Team> teamList) {
		super();
		this.divisionName = divisionName;
		this.teamList = teamList;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}
    
	public boolean isValidDivisionName(String conferenceName, String divisionName, League league) {
		List<Conference> conferenceList =  league.getConferenceList();
		boolean isValid = false;
		for(int index = 0; index < conferenceList.size(); index++) {
			if(conferenceList.get(index).getConferenceName().equals(conferenceName)) {
				List<Division> divisionList = conferenceList.get(index).getDivisionList();
				
				for(int dIndex = 0 ; dIndex < divisionList.size(); dIndex++) {
					if(divisionList.get(dIndex).divisionName.equals(divisionName)) {
						isValid = true;
						break;
					}
				}
			}
		}
		return isValid;
	}
	
}
