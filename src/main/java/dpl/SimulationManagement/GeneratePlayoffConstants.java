package dpl.SimulationManagement;

public enum GeneratePlayoffConstants {

	SCHEDULING_PLAYOFF("Scheduling the playoff season round-1 for simulation."),
	PLAYOFF_SUCCESSFUL("Playoff season has been scheduled successfully."),
	SCHEDULING_ERROR("Error scheduling season, passed league object is null. Please check");

	private final String playoffString;

	GeneratePlayoffConstants(String playoffString) {
		this.playoffString = playoffString;
	}

	public String toString() {
		return playoffString	;
	}
}
