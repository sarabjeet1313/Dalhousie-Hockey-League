package dal.asd.dpl.TeamManagement;

import java.util.List;

public class Conference {
	
	private String conferenceName;
	private List<Division> divisionList;
	
	public Conference(String conferenceName, List<Division> divisionList) {
		super();
		this.conferenceName = conferenceName;
		this.divisionList = divisionList;
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public List<Division> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<Division> divisionList) {
		this.divisionList = divisionList;
	}
	
	public boolean isValidConferenceName(String conferenceName, League league) {
		List<Conference> conferenceList =  league.getConferenceList();
		boolean isValid = false;
		for(int index = 0; index < conferenceList.size(); index++) {
			if(conferenceList.get(index).conferenceName.equals(conferenceName)) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
	
}
