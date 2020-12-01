package dpl.LeagueManagement.TeamManagement;

public enum RosterManagementConstants {

	FORWARD("forward"),
	DEFENSE("defense"),
	GOALIE("goalie");

	private final String rosterString;

	RosterManagementConstants(String rosterString) {
		this.rosterString = rosterString;
	}

	public String toString() {
		return rosterString;
	}
}
