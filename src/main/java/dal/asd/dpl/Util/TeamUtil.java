package dal.asd.dpl.Util;

public enum TeamUtil {

	TEAM_NAME("teamName");

	private final String teamString;

	private TeamUtil(String teamString) {
		this.teamString = teamString;
	}

	public String toString() {
		return teamString;
	}

}
