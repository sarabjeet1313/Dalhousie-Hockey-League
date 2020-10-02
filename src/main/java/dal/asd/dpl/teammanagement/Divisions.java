package dal.asd.dpl.teammanagement;

import java.util.ArrayList;
import java.util.List;

public class Divisions {
    private String divisionName;
    private List<Teams> teamList;
    
	public Divisions(String divisionName, List<Teams> teamList) {
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

	public List<Teams> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Teams> teamList) {
		this.teamList = teamList;
	}
    
	public boolean isValidDivisionName(String conferenceName, String divisionName, Leagues league) {
		List<Conferences> conferenceList =  league.getConferenceList();
		boolean isValid = false;
		for(int index = 0; index < conferenceList.size(); index++) {
			if(conferenceList.get(index).getConferenceName().equals(conferenceName)) {
				List<Divisions> divisionList = conferenceList.get(index).getDivisionList();
				
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
