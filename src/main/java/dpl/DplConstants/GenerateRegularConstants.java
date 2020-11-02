package dpl.DplConstants;

public enum GenerateRegularConstants {

	SCHEDULING_REGULAR("Scheduling the regular season for simulation."),
	PLAYOFF_SUCCESSFUL("Playoff season has been scheduled successfully."),
	SCHEDULING_ERROR("Error scheduling season, passed league object is null. Please check");

	private final String regularString;

	private GenerateRegularConstants(String regularString) {
		this.regularString = regularString;
	}

	public String toString() {
		return regularString	;
	}

}
