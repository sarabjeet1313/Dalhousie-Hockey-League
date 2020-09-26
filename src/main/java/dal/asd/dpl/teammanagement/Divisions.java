package dal.asd.dpl.teammanagement;

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
    
    
}
