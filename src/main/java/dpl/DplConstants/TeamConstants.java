package dpl.DplConstants;

public enum TeamConstants {

	SUCCESS("success"),
	TEAM_NAME("teamName"),
	TEAMS("teams"),
	TEAM_ERROR("Please enter Team name. Null values are not accepted."),
	TEAM_OVER_FLOW("Team cannot have more than 20 players. Please correct the team size.");

	private final String teamString;

	TeamConstants(String teamString) {
		this.teamString = teamString;
	}

	public String toString() {
		return teamString;
	}
}
