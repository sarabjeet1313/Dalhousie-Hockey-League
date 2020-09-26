package dal.asd.dpl.teammanagement;

import java.util.List;

public class Leagues {
	private String leagueName;
	private List<Conferences> conferenceList;
	
	public Leagues(String leagueName, List<Conferences> conferenceList) {
		super();
		this.leagueName = leagueName;
		this.conferenceList = conferenceList;
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
	
	
}
