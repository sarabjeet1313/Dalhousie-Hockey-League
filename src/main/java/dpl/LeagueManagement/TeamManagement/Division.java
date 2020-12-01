package dpl.LeagueManagement.TeamManagement;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Division {

	@Expose(serialize = true, deserialize = true)
	private String divisionName;
	@Expose(serialize = true, deserialize = true)
	private List<Team> teamList;

	public Division() {
		super();
	}

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
		List<Conference> conferenceList = league.getConferenceList();
		boolean isValid = Boolean.FALSE;
		for (int index = 0; index < conferenceList.size(); index++) {
			if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
				List<Division> divisionList = conferenceList.get(index).getDivisionList();

				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
						isValid = Boolean.TRUE;
						break;
					}
				}
			}
		}
		return isValid;
	}

}
