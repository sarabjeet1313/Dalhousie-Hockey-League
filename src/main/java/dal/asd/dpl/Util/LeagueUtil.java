package dal.asd.dpl.Util;

public enum LeagueUtil {

	LEAGUE_NAME("leagueName"),
	ROW_COUNT("rowCount"),
	SUCCESS("success");

	private final String leagueString;

	private LeagueUtil(String leagueString) {
		this.leagueString = leagueString;
	}

	public String toString() {
		return leagueString;
	}	

}
