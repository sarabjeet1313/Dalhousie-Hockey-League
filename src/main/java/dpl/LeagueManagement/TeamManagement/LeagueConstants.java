package dpl.LeagueManagement.TeamManagement;

public enum LeagueConstants {

	LEAGUE_NAME("leagueName"),
	ROW_COUNT("rowCount"),
	SUCCESS("success");

	private final String leagueString;

	LeagueConstants(String leagueString) {
		this.leagueString = leagueString;
	}

	public String toString() {
		return leagueString;
	}
}
