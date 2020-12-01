package dpl.LeagueManagement.TeamManagement;

public enum GeneralConstants {

	FORWARD("forward"),
	DEFENSE("defense"),
	GOALIE("goalie");

	private final String constantString;

	GeneralConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}
}
