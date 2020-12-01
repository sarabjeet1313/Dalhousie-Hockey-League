package dpl.LeagueManagement.TeamManagement;

public enum DivisionConstants {
	DIVISION_NAME("divisionName"),
	DIVISIONS_MODEL("divisions"),
	DIVISIONS_ERROR("Please enter Division name. Null values are not accepted.");

	private final String divisionString;

	DivisionConstants(String divisionString) {
		this.divisionString = divisionString;
	}

	public String toString() {
		return divisionString;
	}

}
