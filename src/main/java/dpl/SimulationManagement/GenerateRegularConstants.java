package dpl.SimulationManagement;

public enum GenerateRegularConstants {

	SCHEDULING_REGULAR("Scheduling the regular season for simulation."),
	REGULAR_SUCCESSFUL("Regular season has been scheduled successfully."),
	SCHEDULING_ERROR("Error scheduling season, passed league object is null. Please check");

	private final String regularString;

	GenerateRegularConstants(String regularString) {
		this.regularString = regularString;
	}

	public String toString() {
		return regularString	;
	}

}
