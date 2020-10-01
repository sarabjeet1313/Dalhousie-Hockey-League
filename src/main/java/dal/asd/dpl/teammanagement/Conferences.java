package dal.asd.dpl.teammanagement;

import java.util.List;

public class Conferences {
	private String conferenceName;
	private List<Divisions> divisionList;
	
	public Conferences(String conferenceName, List<Divisions> divisionList) {
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

	public List<Divisions> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<Divisions> divisionList) {
		this.divisionList = divisionList;
	}
	
	
	
}
